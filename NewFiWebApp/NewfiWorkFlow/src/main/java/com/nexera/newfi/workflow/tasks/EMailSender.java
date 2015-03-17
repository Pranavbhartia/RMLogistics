package com.nexera.newfi.workflow.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;


@Component
public class EMailSender implements IWorkflowTaskExecutor {
	private static final Logger LOG = LoggerFactory
	        .getLogger(EMailSender.class);
	@Autowired
	private SendEmailService sendEmailService;
	
	public String execute(HashMap<String, Object> objectMap) {
		// Call the Email Sender here.
		if (objectMap != null) {
			String emailTemplate = objectMap.get(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME).toString();
			LOG.debug("Template name is " + emailTemplate); 
			String recipient=objectMap.get(WorkflowDisplayConstants.EMAIL_RECIPIENT_KEY_NAME).toString();
			
			EmailVO emailEntity = new EmailVO();
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
			emailRecipientVO.setEmailID(recipient);
			recipients.add(emailRecipientVO);
			emailEntity.setSenderEmailId("web@newfi.com");
			emailEntity.setRecipients(recipients);
			//emailEntity.setBody(emailTemplate);
			emailEntity.setSenderName("Newfi System");
			emailEntity.setSubject("Nexera Newfi Portal");
			emailEntity.setTemplateBased(true);
			emailEntity.setTemplateName(emailTemplate);
			try {
				sendEmailService.sendMail(emailEntity, false);
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UndeliveredEmailException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {

		return "";
	}

}
