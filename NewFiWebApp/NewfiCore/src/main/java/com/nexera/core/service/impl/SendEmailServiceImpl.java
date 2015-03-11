package com.nexera.core.service.impl;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.service.SendEmailService;

public class SendEmailServiceImpl implements SendEmailService {

	@Override
	public boolean sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException {
		// TODO Auto-generated method stub

		
		return true;
	}

}
