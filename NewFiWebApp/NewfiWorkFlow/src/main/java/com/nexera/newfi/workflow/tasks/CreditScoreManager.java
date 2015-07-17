package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Template;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CreateReminderVo;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.CustomerSpouseDetailVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.SMSServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UserProfileService;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class CreditScoreManager extends NexeraWorkflowTask implements
        IWorkflowTaskExecutor {
	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private LoanService loanService;
	@Autowired
	UserProfileService userProfileService;

	@Autowired
	TemplateService templateService;

	@Autowired
	NeedsListService needsListService;

	@Autowired
	SMSServiceHelper smsServiceHelper;

	@Autowired
	SendEmailService sendEmailService;

	@Autowired
	Utils utils;
	private static final Logger LOG = LoggerFactory
	        .getLogger(CreditScoreManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Execute Concrete class : CreditScoreManager" + objectMap);
		/*
		 * makeANote( Integer.parseInt(objectMap.get(
		 * WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()),
		 * LoanStatus.creditScoreMessage)
		 */;
		objectMap.put(WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO,
		        LoanStatus.creditScoreMessage);
		objectMap.put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
		        CommonConstants.TEMPLATE_KEY_NAME_CREDIT_INFO);
		// sendEmail(objectMap, CommonConstants.SUBJECT_YOUR_CREDIT);
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public void sendEmail(HashMap<String, Object> objectMap, String subject) {
		if (objectMap != null) {
			LoanVO loanVO = loanService
			        .getLoanByID(Integer.parseInt(objectMap.get(
			                WorkflowDisplayConstants.LOAN_ID_KEY_NAME)
			                .toString()));
			String emailTemplateKey = objectMap.get(
			        WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
			        .toString();
			if (loanVO != null && emailTemplateKey != null) {
				String emailTemplate = "";				
				Template template = templateService
				        .getTemplateByKey(emailTemplateKey);
				if (template != null) {
					LOG.info("Send Email Template Found " + template.getValue());
					emailTemplate = template.getValue();
				}
				EmailVO emailEntity = new EmailVO();
				List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
				String[] names = new String[1];
				names[0] = loanVO.getUser().getFirstName();
				Map<String, String[]> substitutions = new HashMap<String, String[]>();
				substitutions.put("-name-", names);
				substitutions = doTemplateSubstitutions(substitutions,
				        objectMap);

				emailEntity.setSenderEmailId(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
				emailEntity.setSenderName(CommonConstants.SENDER_NAME);
				if (subject == null) {
					emailEntity.setSubject("Nexera Newfi Portal");
				} else {
					emailEntity.setSubject(subject);
				}
				emailEntity.setTokenMap(substitutions);
				emailEntity.setTemplateId(emailTemplate);
				List<String> ccList = new ArrayList<String>();
				ccList.add(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);

				emailEntity.setCCList(ccList);
				try {
					sendEmailService.sendEmailForCustomer(emailEntity,
					        loanVO.getId(), template);
				} catch (InvalidInputException e) {
					LOG.error("Exception caught " + e.getMessage());
				} catch (UndeliveredEmailException e) {
					LOG.error("Exception caught " + e.getMessage());
				}

			}
		}
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		LOG.debug("Render State Info " + inputMap);
		int userID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString()));
		int loanID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		// TO Remove------ start
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
		        iWorkflowService.getCreditDisplayScore(userID));
		map.put(WorkflowDisplayConstants.RESPONSE_URL_KEY,
		        needsListService.checkCreditReport(loanID));
		// TO Remove------ end

		return utils.getJsonStringOfMap(map);
	}

	@Override
	public Map<String, String[]> doTemplateSubstitutions(
	        Map<String, String[]> substitutions,
	        HashMap<String, Object> objectMap) {
		if (substitutions == null) {
			substitutions = new HashMap<String, String[]>();
		}
		LoanVO loanVO = loanService.getLoanByID(Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		if (loanVO != null) {
			UserVO userVO = loanVO.getUser();
			if (userVO != null) {
				CustomerDetailVO customerDetailVO = userVO.getCustomerDetail();
				if (customerDetailVO != null) {
					String equifax = customerDetailVO.getEquifaxScore();
					String experian = customerDetailVO.getExperianScore();
					String transunion = customerDetailVO.getTransunionScore();
					if (equifax != null && experian != null
					        && transunion != null) {
						int midScore = (Integer.parseInt(equifax)
						        + Integer.parseInt(experian) + Integer
						        .parseInt(transunion)) / 3;

						substitutions.put("-midcreditscore-",
						        new String[] { String.valueOf(midScore) });
						String creditScoreStringForBorrower = "Borrower One "
						        + userVO.getFirstName() + " : " + experian
						        + ", " + equifax + ", " + transunion;
						if (loanVO.getLoanAppForms() != null) {
							CustomerSpouseDetailVO customerSpouseDetailVO = loanVO
							        .getLoanAppForms().get(0)
							        .getCustomerSpouseDetail();
							if (customerSpouseDetailVO != null) {
								String spouseEquifax = customerSpouseDetailVO
								        .getEquifaxScore();
								String spouseExperian = customerSpouseDetailVO
								        .getExperianScore();
								String spouseTransunion = customerSpouseDetailVO
								        .getTransunionScore();
								if (spouseEquifax != null
								        && spouseExperian != null
								        && spouseTransunion != null) {
									creditScoreStringForBorrower = creditScoreStringForBorrower
									        + "\n Borrower Two "
									        + customerSpouseDetailVO
									                .getSpouseName()
									        + " : "
									        + experian
									        + ", "
									        + equifax
									        + ", "
									        + transunion;
								}
							}
						}
						substitutions.put("-creditscoreinformation-",
						        new String[] { creditScoreStringForBorrower });
					}

				}
			}
		}
		return substitutions;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// Do Nothing
		// The credit scores come from LQB via Batch only - So Batch will keep
		// the WorkItem status clean
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// Do Nothing
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {

		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.CREDIT_SCORE_NOTIFICATION_TYPE;
		int loanId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		int workflowItemExecutionId = Integer.parseInt(objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
		String prevMilestoneKey = WorkflowConstants.WORKFLOW_ITEM_1003_COMPLETE;
		String notificationReminderContent = WorkflowConstants.CREDIT_SCORE_NOTIFICATION_CONTENT;
		CreateReminderVo createReminderVo = new CreateReminderVo(
		        notificationType, loanId, workflowItemExecutionId,
		        prevMilestoneKey, notificationReminderContent);
		LOG.debug("Creating LM Reminder for ..Credit Score Manager for Prev MS :"
		        + prevMilestoneKey);
		iWorkflowService.updateLMReminder(createReminderVo);
		return null;
	}
}
