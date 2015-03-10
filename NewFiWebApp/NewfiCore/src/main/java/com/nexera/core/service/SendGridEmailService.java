package com.nexera.core.service;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;

public interface SendGridEmailService {
	
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException;

}
