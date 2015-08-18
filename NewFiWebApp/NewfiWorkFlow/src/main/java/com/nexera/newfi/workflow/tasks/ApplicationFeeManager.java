package com.nexera.newfi.workflow.tasks;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.core.helper.SMSServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.TransactionService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ApplicationFeeManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private Utils utils;
	@Autowired
	TemplateService templateService;

	@Autowired
	SMSServiceHelper smsServiceHelper;

	@Autowired
	SendGridEmailService sendGridEmailService;

	@Value("${profile.url}")
	private String baseUrl;

	@Autowired
	private NotificationService notificationService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(ApplicationFeeManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		String returnStatus = null;
		String messageForNote = "";
		String subject = null;
		LOG.info("Execute Concrete Class for ApplicationFeeManager "
		        + objectMap);
		if (objectMap != null) {
			int loanId = Integer.parseInt(objectMap.get(
			        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
			String status = objectMap.get(
			        WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME)
			        .toString();
			LOG.info("Status for Application Fee Manager is " + status);
			if (status.equals(LoanStatus.APP_PAYMENT_SUCCESS)) {
				dismissAllPaymentAlerts(loanId);
				objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
				        CommonConstants.TEMPLATE_KEY_NAME_APPLICATION_FEE_PAID);
				subject = CommonConstants.SUBJECT_APPLICATION_FEE_PAID;
				createAlertToLockRates(objectMap);
				returnStatus = WorkItemStatus.COMPLETED.getStatus();
				messageForNote = LoanStatus.paymentSuccessStatusMessage;
			} else if (status.equals(LoanStatus.APP_PAYMENT_FAILURE)) {
				messageForNote = LoanStatus.paymentFailureStatusMessage;
				subject = CommonConstants.SUBJECT_APPLICATION_FEE_FAILED;
				return WorkItemStatus.NOT_STARTED.getStatus();
			} else if (status.equals(LoanStatus.APP_PAYMENT_PENDING)) {
				returnStatus = WorkItemStatus.STARTED.getStatus();
				// NEXNF-424 : Dont send Appriasl ordered email when app fee is
				// paid in newfi
				/*
				 * objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME
				 * , CommonConstants.TEMPLATE_KEY_NAME_APPRAISAL_ORDERED);
				 */
				subject = CommonConstants.SUBJECT_APPLICATION_FEE_PENDING;
				messageForNote = LoanStatus.paymentPendingStatusMessage;

			}
			if (status != null && !status.isEmpty()) {
				LOG.info("Making Milestone chagne for App Fee with" + status);
				loanService.saveLoanMilestone(loanId,
				        Milestones.APP_FEE.getMilestoneID(), status);
				/*
				 * makeANote(Integer.parseInt(objectMap.get(
				 * WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
				 * messageForNote);
				 */
				objectMap.put(
				        WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
				        messageForNote);
				objectMap.put(WorkflowDisplayConstants.PAYMENT_STATUS, status);
				sendEmail(objectMap, subject);
			}

		}
		return returnStatus;
	}

	@Override
	public Map<String, String[]> doTemplateSubstitutions(
	        Map<String, String[]> substitutions,
	        HashMap<String, Object> objectMap) {
		if (substitutions == null) {
			substitutions = new HashMap<String, String[]>();
		}

		String[] ary = new String[1];
		if (objectMap.get(WorkflowDisplayConstants.PAYMENT_STATUS) != null) {
			if (((String) objectMap
			        .get(WorkflowDisplayConstants.PAYMENT_STATUS))
			        .equalsIgnoreCase(LoanStatus.APP_PAYMENT_SUCCESS)) {

				LoanVO loanVO = loanService.getLoanByID(Integer
				        .parseInt(objectMap.get(
				                WorkflowDisplayConstants.LOAN_ID_KEY_NAME)
				                .toString()));
				LoanApplicationFee loanApplicationFee = transactionService
				        .findByLoan(loanVO);
				String appFee = "";
				if (loanApplicationFee != null) {
					 
					appFee = "$"+String.valueOf(String.format("%.2f", loanApplicationFee.getFee()));
				}

				substitutions.put("-amount-", new String[] { appFee });
				String paymentDate = "";
				if (loanApplicationFee != null
				        && loanApplicationFee.getPaymentDate() != null) {
					paymentDate = String.valueOf(utils.getDateAndTimeForDisplay(loanApplicationFee
					        .getPaymentDate()));
				}
				substitutions.put("-date-", new String[] { paymentDate });
				ary[0] = objectMap.get(
				        WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO)
				        .toString();
				substitutions.put("-message-", ary);
				
				substitutions.put("-customername-",new String[] { loanVO.getUser().getFirstName()+" "+loanVO.getUser().getLastName() });
				substitutions.put("-newfiloannumber-", new String[] { Integer.toString(loanVO.getId())});

				for (String key : objectMap.keySet()) {
					ary = new String[1];
					ary[0] = objectMap.get(key).toString();
					substitutions.put("-" + key + "-", ary);
				}
			} else if (((String) objectMap
			        .get(WorkflowDisplayConstants.PAYMENT_STATUS))
			        .equalsIgnoreCase(LoanStatus.APP_PAYMENT_PENDING)) {
				ary[0] = objectMap.get(
				        WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO)
				        .toString();
				substitutions.put("-message-", ary);
				substitutions.put("-appfeepaymentlink-",
				        new String[] { baseUrl });
				for (String key : objectMap.keySet()) {
					ary = new String[1];
					ary[0] = objectMap.get(key).toString();
					substitutions.put("-" + key + "-", ary);
				}
			}
		}
		return substitutions;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.info("Render State Info for Application Fee Manager" + inputMap);
		// First Check if Payment Made
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		return iWorkflowService.getRenderInfoForApplicationFee(loanId);

	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// This code can be removed from CHeck Status
		// Since The Transaction and App Fee tables are updated only by Batch
		// Batch will take care to update the WorkflowItem as correct status
		// Keeping it for an additional fall back - but not required
		LOG.info("Checking Status for Application Fee Manager" + inputMap);
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		int loanID = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = new LoanVO(loanID);
		LoanApplicationFee loanApplicationFee = transactionService
		        .findByLoan(loanVO);
		int workflowItemExecId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		if (loanApplicationFee != null
		        && loanApplicationFee.getPaymentDate() != null) {
			inputMap.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
			        LoanStatus.APP_PAYMENT_SUCCESS);
			// This means Brain Tree has approved the fee payment
			String params = Utils.convertMapToJson(inputMap);
			workflowService.saveParamsInExecTable(workflowItemExecId, params);

			engineTrigger.startWorkFlowItemExecution(workflowItemExecId);
			return WorkItemStatus.COMPLETED.toString();
		}
		// If Payment is done on : NewFiWeb and not done
		// we should say Pending Approval
		List<TransactionDetails> transList = transactionService
		        .getActiveTransactionsByLoan(new Loan(loanID));
		if (transList != null && !transList.isEmpty()) {
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.STARTED.getStatus());
			return WorkItemStatus.STARTED.getStatus();
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		BigDecimal newAppFee = new BigDecimal(inputMap.get(
		        WorkflowDisplayConstants.APP_FEE_CHANGE).toString());
		loanService.updateLoanAppFee(loanId, newAppFee);
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		User user = new User();
		user.setId(userId);
		String message = WorkflowConstants.APP_FEE_CHANGED_CONTENT
		        + newAppFee.toString();
		makeANote(loanId, message, user);
		return newAppFee.toString();
	}

	private void makeANote(int loanId, String message, User createdBy) {
		messageServiceHelper.generatePrivateMessage(loanId, message, createdBy,
		        false);
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.info("Creating a Reminder with input" + objectMap);
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		LoanVO loanVO = loanService.getLoanByID(loanId);
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_DISCLOSURE_STATUS;
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(loanVO.getLoanManagerWorkflowID());
		WorkflowItemMaster workflowItemMaster = workflowService
		        .getWorkflowByType(prevMilestoneKey);
		WorkflowItemExec prevMilestone = workflowService
		        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
		if (prevMilestone.getStatus().equals(
		        WorkItemStatus.COMPLETED.getStatus())) {
			LOG.info("The previous Milestone was completed");
			LoanApplicationFee loanApplicationFee = transactionService
			        .findByLoan(loanVO);
			if (loanApplicationFee.getPaymentDate() == null) {
				CreateReminderVo createReminderVo = new CreateReminderVo(
				        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_OVERDUE_TYPE,
				        loanId,
				        WorkflowConstants.APP_FEE_OVERDUE_NOTIFICATION_CONTENT);
				createReminderVo.setForCustomer(true);
				LOG.info("Creating a Reminder" + createReminderVo);
				iWorkflowService.sendReminder(createReminderVo,
				        workflowItemExecutionId, prevMilestone.getId());
				// And change the status of the workItem to Over Due;
				LOG.info("The previous Milestone was completed .. So creating this Milestone"
				        + LoanStatus.APP_PAYMENT_OVERDUE);
				loanService.updateNexeraMilestone(loanId,
				        Milestones.APP_FEE.getMilestoneID(),
				        LoanStatus.APP_PAYMENT_OVERDUE);
			}
		}
		return null;
	}

	private void dismissAllPaymentAlerts(int loanID) {
		notificationService.dismissReadNotifications(loanID,
		        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_OVERDUE_TYPE);
		notificationService.dismissReadNotifications(loanID,
		        MilestoneNotificationTypes.APP_FEE_NOTIFICATION_TYPE);
	}

	private void createAlertToLockRates(HashMap<String, Object> objectMap) {
		LOG.info("createAlertToLockRates" + objectMap);
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.LOCK_RATE_CUST_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		String notificationReminderContent = WorkflowConstants.LOCK_RATE_CUST_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, notificationReminderContent);
		createReminderVo.setForCustomer(true);
		iWorkflowService.createAlertOfType(createReminderVo);
	}
}
