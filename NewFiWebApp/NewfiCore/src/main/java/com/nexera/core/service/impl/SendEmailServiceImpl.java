package com.nexera.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;

@Component
public class SendEmailServiceImpl implements SendEmailService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(SendEmailServiceImpl.class);

	@Autowired
	SendGridEmailService sendGridEmailService;

	@Override
	public boolean sendMail(EmailVO emailEntity, boolean sync)
	        throws InvalidInputException, UndeliveredEmailException {

		if (!validateEmailVO(emailEntity)) {
			throw new InvalidInputException(
			        "Invalid fields set in email Entity: " + emailEntity);
		}

		if (emailEntity.getSenderEmailId() == null
		        || emailEntity.getSenderEmailId().isEmpty()) {
			LOG.info("No Sender email id specified, hence setting the default.");
			emailEntity.setSenderEmailId(CommonConstants.DEFAULT_FROM_ADDRESS);
		}

		if (sync) {
			sendGridEmailService.sendMail(emailEntity);
		} else {
			sendGridEmailService.sendAsyncMail(emailEntity);
		}

		return true;
	}

	private boolean validateEmailVO(EmailVO emailEntity) {

		if (emailEntity.getRecipients().isEmpty()
		        || emailEntity.getBody() == null
		        || emailEntity.getBody().isEmpty()) {
			return false;
		}
		return true;
	}

}
