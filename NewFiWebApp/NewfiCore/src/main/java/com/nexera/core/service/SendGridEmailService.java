package com.nexera.core.service;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;
import com.sendgrid.SendGrid.Email;

public interface SendGridEmailService {

	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
	        UndeliveredEmailException;

	void sendAsyncMail(EmailVO emailEntity);

	public String sendSMSEmail(Email email);

}
