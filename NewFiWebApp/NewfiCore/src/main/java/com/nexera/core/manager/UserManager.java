package com.nexera.core.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.utility.NexeraUtility;

@Component
@Scope(value = "prototype")
public class UserManager implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger("batchJobs");

	@Autowired
	private Utils utils;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private SendEmailService sendEmailService;

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

		if (user.getTokenGeneratedTime() != null) {
			Date userCreationDate = user.getCreatedDate();
			Calendar creationCalendar = Calendar.getInstance();
			creationCalendar.setTime(userCreationDate);
			int day = creationCalendar.get(Calendar.DAY_OF_MONTH);
			Date currentDate = utils.convertCurrentDateToUtc();
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(currentDate);
			int currentDay = creationCalendar.get(Calendar.DAY_OF_MONTH);
			if (day != currentDay) {
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
		}
	}

	public void fileInactivityAction(User user) {
		LOGGER.debug("Inside method fileInactivityAction ");
		Date lastLoginDateOfUser = user.getLastLoginDate();
		Date systemDate = utils.convertCurrentDateToUtc();
		long diff = systemDate.getTime() - lastLoginDateOfUser.getTime();
		long diffInDays = TimeUnit.MILLISECONDS.toDays(diff);
		if (diffInDays == 30) {
			try {
				sendUserInActiveEMail();
			} catch (InvalidInputException | UndeliveredEmailException e) {
				LOGGER.error("Exception caught " + e.getMessage());
				nexeraUtility.putExceptionMasterIntoExecution(exceptionMaster,
				        e.getMessage());
				nexeraUtility.sendExceptionEmail(e.getMessage());
			}
		}
	}

	private void sendUserInActiveEMail() throws InvalidInputException,
	        UndeliveredEmailException {
		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_FILE_INACTIVITY);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });

		substitutions.put("-url-", new String[] { baseUrl });
		if (user != null) {
			emailEntity.setSenderEmailId(user.getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Password Not Updated! Pelase Update.");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, user);
	}

	private void sendPasswordNotUpdatedEmail(User user)
	        throws InvalidInputException, UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_CREATED_ACCOUNT_PASSWORD_NOT_UPDATED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { user.getFirstName() + " "
		        + user.getLastName() });
		substitutions.put("-username-", new String[] { user.getEmailId() });
		String uniqueURL = baseUrl + "reset.do?reference="
		        + user.getEmailEncryptionToken();
		substitutions.put("-passwordurl-", new String[] { uniqueURL });

		if (user.getUsername() != null) {
			emailEntity.setSenderEmailId(user.getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(CommonConstants.SUBJECT_PASSWORD_NOT_UPADTED);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, user);
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
