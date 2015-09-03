package com.nexera.core.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendGridEmailService;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

@Component
public class SendGridEmailServiceImpl implements SendGridEmailService,
        InitializingBean {

	private static final Logger LOG = LoggerFactory
	        .getLogger(SendGridEmailServiceImpl.class);

	@Value("${SENDGRID_USERNAME}")
	private String sendGridUserName;

	@Value("${SENDGRID_PASSWORD}")
	private String sendGridPassword;

	private SendGrid sendGrid;

	@Override
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
	        UndeliveredEmailException {

		prepareAndSend(emailEntity);

	}

	@Override
	public String sendSMSEmail(Email email) {

		Response response = null;
		sendGrid = new SendGrid(sendGridUserName, sendGridPassword);
		LOG.debug("Making sendgrid api call");
		try {
			response = sendGrid.send(email);
		} catch (SendGridException e) {
			LOG.error("Error while sending sms " + e.getMessage());
			return null;
		}
		if (response == null)
			return null;
		else {
			if (response.getStatus()) {
				LOG.debug("Response received " + response.getMessage());
				return response.getMessage();
			} else {
				return null;
			}
		}

	}

	@Override
	public String sendExceptionEmail(Email email) {

		Response response = null;
		sendGrid = new SendGrid(sendGridUserName, sendGridPassword);
		LOG.debug("Making sendgrid api call");
		try {
			response = sendGrid.send(email);
		} catch (SendGridException e) {
			LOG.error("Error while sending sms " + e.getMessage());
			return null;
		}
		if (response == null)
			return null;
		else {
			if (response.getStatus()) {
				LOG.debug("Response received " + response.getMessage());
				return response.getMessage();
			} else {
				return null;
			}
		}

	}

	@Async
	@Override
	public void sendAsyncMail(EmailVO emailEntity) {
		try {
			prepareAndSend(emailEntity);
		} catch (InvalidInputException | UndeliveredEmailException e) {
			LOG.error("Error sending email: " + emailEntity, e);
		}
	}

	private void prepareAndSend(EmailVO emailEntity)
	        throws InvalidInputException, UndeliveredEmailException {

		if (emailEntity.getTokenMap() == null
		        || emailEntity.getTokenMap().isEmpty()) {
			LOG.error("sendEmailUsingTemplate : substitutions is null or empty!");
			throw new InvalidInputException(
			        "sendEmailUsingTemplate : substitutions is null or empty!");
		}
		if (emailEntity.getTemplateId() == null
		        || emailEntity.getTemplateId().isEmpty()) {
			LOG.error("sendEmailUsingTemplate : templateId is null or empty!");
			throw new InvalidInputException(
			        "sendEmailUsingTemplate : templateId is null or empty!");
		}
		if (emailEntity.getSenderName() == null
		        || emailEntity.getSenderName().isEmpty()) {
			LOG.error("sendEmailUsingTemplate : senderName is null or empty!");
			throw new InvalidInputException(
			        "sendEmailUsingTemplate : senderName is null or empty!");
		}
		if (emailEntity.getSenderEmailId() == null
		        || emailEntity.getSenderEmailId().isEmpty()) {
			LOG.error("sendEmailUsingTemplate : sender email id is null or empty!");
			throw new InvalidInputException(
			        "sendEmailUsingTemplate : sender email id is null or empty!");
		}
		if (emailEntity.getSubject() == null
		        || emailEntity.getSubject().isEmpty()) {
			LOG.error("sendEmailUsingTemplate : subject is null or empty!");
			throw new InvalidInputException(
			        "sendEmailUsingTemplate : subject is null or empty!");
		}

		Response response = null;
		LOG.info("sendEmailUsingTemplateToMultiple called to send mails");
		Email email = new Email();

		List<String> recipientEmailIdsList = new ArrayList<>();

		for (EmailRecipientVO recipientVO : emailEntity.getRecipients()) {
			if (recipientVO.getEmailID() == null
			        || recipientVO.getEmailID().isEmpty()) {
				LOG.error("sendEmailUsingTemplate : recipient email id is null or empty!");
				throw new InvalidInputException(
				        "sendEmailUsingTemplate : recipient email id is null or empty!");
			}
			recipientEmailIdsList.add(recipientVO.getEmailID());
			if (emailEntity.getCCList() != null) {
				for (Iterator<String> iterator = emailEntity.getCCList()
				        .iterator(); iterator.hasNext();) {
					String emailId = iterator.next();
					if (recipientVO.getEmailID().equalsIgnoreCase(emailId)) {
						LOG.debug("This email id already exist in recepient list, hence removing it from CC list");
						iterator.remove();
					}
				}
			}
		}

		email.addTo(recipientEmailIdsList
		        .toArray(new String[recipientEmailIdsList.size()]));

		if (emailEntity.getCCList() != null
		        && !emailEntity.getCCList().isEmpty()) {
			String[] ccEmails = new String[emailEntity.getCCList().size()];
			for (int i = 0; i < emailEntity.getCCList().size(); i++) {
				ccEmails[i] = emailEntity.getCCList().get(i);
			}
			email.setCc(ccEmails);
		}

		if (emailEntity.getRecipients() == null
		        || emailEntity.getRecipients().size() == 0) {
			if (emailEntity.getCCList() == null
			        || emailEntity.getCCList().size() == 0) {
				LOG.error("sendEmailUsingTemplate : recipientEmailId is null or empty!");
				throw new InvalidInputException(
				        "sendEmailUsingTemplate : recipientEmailId is null or empty!");
			} else {
				LOG.debug("Recepeints not found, but CC list exist, hence adding the CC list entries to the recepient list");
				email.setCc(new String[0]);
				String[] recepientEmails = new String[emailEntity.getCCList()
				        .size()];
				for (int i = 0; i < emailEntity.getCCList().size(); i++) {
					recepientEmails[i] = emailEntity.getCCList().get(i);
				}

				email.setTo(recepientEmails);
			}
		}

		email.setFrom(emailEntity.getSenderEmailId());
		email.setFromName(emailEntity.getSenderName());
		email.setSubject(emailEntity.getSubject());

		if (emailEntity.getBody() == null || emailEntity.getBody().isEmpty()) {

			email.setText(CommonConstants.EMAIL_EMPTY_FOOTER);
			if (!emailEntity.isDisableHtml()) {
				email.setHtml(CommonConstants.EMAIL_EMPTY_FOOTER);
			}
		} else {
			email.setText(emailEntity.getBody());
			if (!emailEntity.isDisableHtml()) {
				email.setHtml(emailEntity.getBody());
			}
		}
		if (emailEntity.getAttachmentStream() != null) {
			try {
				ByteArrayOutputStream arrayOutputStream = emailEntity
				        .getAttachmentStream();
				InputStream inputStream = new ByteArrayInputStream(
				        arrayOutputStream.toByteArray());
				email.addAttachment(emailEntity.getFileName(),
				        inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOG.error("Exception caught " + e.getMessage());
			}
		}

		int totalNumberOfRecipients = recipientEmailIdsList.size();
		if (emailEntity.getCCList() != null
		        && !emailEntity.getCCList().isEmpty()) {
			totalNumberOfRecipients = totalNumberOfRecipients
			        + emailEntity.getCCList().size();
		}

		for (Entry<String, String[]> entry : emailEntity.getTokenMap()
		        .entrySet()) {
			String[] newArray = entry.getValue();
			String[] valueArray = new String[totalNumberOfRecipients];
			valueArray[0] = newArray[0];
			for (int i = 1; i < totalNumberOfRecipients; i++) {
				String[] addArray = entry.getValue();
				valueArray[i] = addArray[0];
			}
			email.addSubstitution(entry.getKey(), valueArray);
		}

		email.addFilter("templates", "enable", "1");
		email.addFilter("templates", "template_id", emailEntity.getTemplateId());

		LOG.debug("Email entity has been initialised");

		try {
			LOG.debug("Making sendgrid api call");
			response = sendGrid.send(email);
			LOG.debug("Api call complete");
		} catch (SendGridException e) {
			LOG.error("SendGridException caught while sending mail  for templateId : "
			        + emailEntity.getTemplateId()
			        + " message : "
			        + e.getMessage());
			throw new UndeliveredEmailException(
			        "SendGridException caught while sending mail  for templateId : "
			                + emailEntity.getTemplateId() + " message : "
			                + e.getMessage());
		}

		LOG.info("Email api call status : " + response.getStatus()
		        + " message : " + response.getMessage());

		if (response.getStatus() == true) {
			LOG.debug("Api call successful. Email sent");
		} else {
			LOG.error("Mail not delivered. Reason : " + response.getMessage());
			throw new UndeliveredEmailException("Mail not delivered. Reason : "
			        + response.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("Settings Up sendGrid gateway");

		if (sendGrid == null) {
			LOG.info("Initialising Sendgrid gateway with " + sendGridUserName
			        + " and " + sendGridPassword);
			sendGrid = new SendGrid(sendGridUserName, sendGridPassword);
			LOG.info("Sendgrid gateway initialised!");
		}
	}

}
