package com.nexera.core.service.impl;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendGridEmailService;

public class SendGridEmailServiceImpl implements SendGridEmailService {
	@Override
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException {
		// TODO Auto-generated method stub

	}
}
