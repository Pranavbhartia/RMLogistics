package com.nexera.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.config.SmtpSettings;
import com.nexera.core.service.SendGridEmailService;

@Component
public class SendGridEmailServiceImpl implements SendGridEmailService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(SendGridEmailServiceImpl.class);

	@Autowired
	private SmtpSettings smtpSettings;

	@Override
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
	        UndeliveredEmailException {

		prepareAndSend(emailEntity);

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

		// Create the mail session object
		Session session = createSession();
		LOG.debug("Preparing transport object for sending mail");
		Transport transport = null;
		try {
			transport = session.getTransport(SmtpSettings.MAIL_TRANSPORT);

			transport.connect(smtpSettings.getMailHost(),
			        smtpSettings.getMailPort(), smtpSettings.getSenderName(),
			        smtpSettings.getSenderPassword());
			LOG.trace("Connection successful");
			// Adding the recipients to address list
			Address[] addresses = createRecipientAddresses(emailEntity
			        .getRecipients());
			// Setting up new MimeMessage
			Message message = createMessage(emailEntity, session, addresses);

			transport.sendMessage(message, addresses);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new UndeliveredEmailException("Error sending email: ", e);
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					LOG.warn("Email transport is not closed in SendGridEmail implemnetation. Might cause a leak");

				}
			}
		}

		LOG.info("Mail sent successfully. Returning from method sendMail");
	}

	private Message createMessage(EmailVO emailEntity, Session session,
	        Address[] addresses) throws UnsupportedEncodingException,
	        MessagingException, InvalidInputException {

		LOG.debug("Creating message");
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailEntity.getSenderEmailId(),
		        emailEntity.getSenderName()));

		// Set the subject of mail
		message.setSubject(emailEntity.getSubject());

		// Set the mail body
		message.setContent(emailEntity.getBody(), "text/html");
		return message;
	}

	/**
	 * Method to create mail session
	 * 
	 * @return
	 */
	private Session createSession() {
		LOG.debug("Preparing session object for sending mail");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", SmtpSettings.MAIL_SMTP_AUTH);
		properties.put("mail.smtp.starttls.enable",
		        SmtpSettings.MAIL_SMTP_STARTTLS_ENABLE);
		Session mailSession = Session.getInstance(properties);
		LOG.debug("Returning the session object");
		return mailSession;
	}

	/**
	 * Method creates addresses from the recipient list
	 * 
	 * @param recipients
	 * @return
	 * @throws AddressException
	 */
	private Address[] createRecipientAddresses(List<EmailRecipientVO> recipients)
	        throws AddressException {
		LOG.debug("Creating recipient addresses");
		StringBuilder recipientsSb = new StringBuilder();
		int count = 0;
		for (EmailRecipientVO recipient : recipients) {
			if (count != 0) {
				recipientsSb.append(",");
			}
			LOG.debug("Adding recipient : " + recipient.getEmailID());
			recipientsSb.append(recipient.getEmailID());
			count++;
		}
		LOG.debug("Recipients are : " + recipientsSb);

		// Adding the recipients to address list
		Address[] addresses = InternetAddress.parse(recipientsSb.toString());
		return addresses;
	}
}
