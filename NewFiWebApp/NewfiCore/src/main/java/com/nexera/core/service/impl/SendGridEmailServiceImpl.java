package com.nexera.core.service.impl;

import java.util.Properties;

import javax.mail.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.config.SmtpSettings;
import com.nexera.core.service.SendGridEmailService;

public class SendGridEmailServiceImpl implements SendGridEmailService {

	private static final Logger LOG = LoggerFactory
			.getLogger(SendGridEmailServiceImpl.class);

	@Autowired
	private SmtpSettings smtpSettings;

	@Override
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException {
		// TODO Auto-generated method stub

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
}
