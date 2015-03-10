package com.nexera.core.service.impl;

import java.util.Map;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;

public class SendEmailServiceImpl implements SendEmailService {

	@Override
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendEmailWithBodyReplacements(EmailVO emailEntity,
			String templateName, Map<String, String> tokenMap)
			throws InvalidInputException, UndeliveredEmailException {
		// TODO Auto-generated method stub

	}

}
