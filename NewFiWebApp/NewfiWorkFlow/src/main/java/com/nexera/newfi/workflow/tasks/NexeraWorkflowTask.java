package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Template;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.helper.SMSServiceHelper;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;

@Component
public abstract class NexeraWorkflowTask {
	@Autowired
	public SendGridEmailService sendGridEmailService;
	@Autowired
	public MessageServiceHelper messageServiceHelper;
	@Autowired
	private LoanService loanService;

	@Autowired
	private SMSServiceHelper smsServiceHelper;

	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private TemplateService templateService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(NexeraWorkflowTask.class);

	public void sendEmail(HashMap<String, Object> objectMap, String subject) {
		if (objectMap != null) {
			LoanVO loanVO = loanService
			        .getLoanByID(Integer.parseInt(objectMap.get(
			                WorkflowDisplayConstants.LOAN_ID_KEY_NAME)
			                .toString()));
			if (loanVO != null) {
				String emailTemplateKey = objectMap.get(
				        WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME)
				        .toString();
				String emailTemplate = WorkflowDisplayConstants.EMAIL_TEMPLATE_DEFAULT_ID;
				Template template = templateService
				        .getTemplateByKey(emailTemplateKey);
				if (template != null) {
					LOG.info("Send Email Template Found " + template.getValue());
					emailTemplate = template.getValue();
				}
				EmailVO emailEntity = new EmailVO();
				String[] names = new String[1];
				names[0] = loanVO.getUser().getFirstName();

				Map<String, String[]> substitutions = new HashMap<String, String[]>();
				substitutions.put("-name-", names);
				substitutions = doTemplateSubstitutions(substitutions,
				        objectMap);
				emailEntity.setSenderEmailId("web@newfi.com");
				emailEntity.setSenderName("Newfi System");
				if (subject == null) {
					emailEntity.setSubject("Nexera Newfi Portal");
				} else {
					emailEntity.setSubject(subject);
				}
				emailEntity.setTokenMap(substitutions);
				emailEntity.setTemplateId(emailTemplate);
				try {
					sendEmailService.sendEmailForTeam(emailEntity,
					        loanVO.getId());
				} catch (InvalidInputException e) {
					LOG.error("Exception Caught " + e.getMessage());
				} catch (UndeliveredEmailException e) {
					LOG.error("Exception Caught " + e.getMessage());
				}

				// Sending sms to user now
				if (loanVO.getUser() != null) {
						if (loanVO.getUser().getCarrierInfo() != null) {
							if (loanVO.getUser().getPhoneNumber() != null
						        && !loanVO.getUser().getPhoneNumber()
							                .equalsIgnoreCase("")) {
								LOG.info("Sending SMS "
								        + Long.valueOf(loanVO.getUser()
								                .getPhoneNumber()));
								smsServiceHelper.sendNotificationSMS(loanVO
								        .getUser().getCarrierInfo(), Long
								        .valueOf(loanVO.getUser()
								                .getPhoneNumber()));
							}
						}

				}
			}
		}
	}

	public Map<String, String[]> doTemplateSubstitutions(
	        Map<String, String[]> substitutions,
	        HashMap<String, Object> objectMap) {
		if (substitutions == null) {
			substitutions = new HashMap<String, String[]>();
		}
		String[] ary = new String[1];
		ary[0] = objectMap.get(
		        WorkflowDisplayConstants.WORKITEM_EMAIL_STATUS_INFO).toString();
		substitutions.put("-message-", ary);
		for (String key : objectMap.keySet()) {
			ary = new String[1];
			ary[0] = objectMap.get(key).toString();
			substitutions.put("-" + key + "-", ary);
		}
		return substitutions;
	}

	public EmailRecipientVO getReceipientVO(LoanTeamVO teamMember) {

		return getReceipientVO(teamMember.getUser().getEmailId(), teamMember
		        .getUser().getFirstName(), teamMember.getUser().getLastName());
	}

	protected EmailRecipientVO getReceipientVO(String emailID,
	        String firstName, String lastName) {
		EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
		emailRecipientVO.setEmailID(emailID);
		emailRecipientVO.setRecipientName(firstName + " " + lastName);
		return emailRecipientVO;
	}

	public void makeANote(int loanId, String message) {
		messageServiceHelper.generateWorkflowMessage(loanId, message, false);
	}

}
