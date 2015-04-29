package com.nexera.core.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.User;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.utility.NexeraUtility;

@Component
@Scope(value = "prototype")
public class UserManager implements Runnable {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(UserManager.class);

	@Autowired
	private Utils utils;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	private User user;

	@Autowired
	private NexeraUtility nexeraUtility;

	private ExceptionMaster exceptionMaster;

	@Value("${profile.url}")
	private String baseUrl;

	@Override
	public void run() {
		LOGGER.debug("Inside method run");
		passwordNotUpdated(user);
	}

	public void passwordNotUpdated(User user) {
		Date userCreationDate = user.getCreatedDate();
		Date tokenGeerationDate = user.getTokenGeneratedTime();
		Date systemDate = utils.convertCurrentDateToUtc();
		if (tokenGeerationDate != null) {
			long secs = (systemDate.getTime() - userCreationDate.getTime()) / 1000;
			long hours = secs / 3600;
			if (hours == 48 || hours == 96) {
				try {
					sendPasswordNotUpdatedEmail(user);
				} catch (InvalidInputException e) {
					LOGGER.error("Exception caught " + e.getMessage());
					nexeraUtility.putExceptionMasterIntoExecution(
					        exceptionMaster, e.getMessage());
					nexeraUtility.sendExceptionEmail(e.getMessage());
				} catch (UndeliveredEmailException e) {
					LOGGER.error("Exception caught " + e.getMessage());
					nexeraUtility.putExceptionMasterIntoExecution(
					        exceptionMaster, e.getMessage());
					nexeraUtility.sendExceptionEmail(e.getMessage());
				}
			}

		} else {
			LOGGER.debug("User may have updated his password ");
		}
	}

	private void sendPasswordNotUpdatedEmail(User user)
	        throws InvalidInputException, UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_CREATED_ACCOUNT_PASSWORD_NOT_UPDATED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });
		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken();
		substitutions.put("-passwordurl-", new String[] { uniqueURL });

		recipientVO.setEmailID(user.getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		emailEntity.setSenderEmailId(CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Password Not Updated! Pelase Update.");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		sendGridEmailService.sendMail(emailEntity);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExceptionMaster getExceptionMaster() {
		return exceptionMaster;
	}

	public void setExceptionMaster(ExceptionMaster exceptionMaster) {
		this.exceptionMaster = exceptionMaster;
	}

}
