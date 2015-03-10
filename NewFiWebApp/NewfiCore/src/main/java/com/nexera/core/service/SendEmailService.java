package com.nexera.core.service;

import java.util.Map;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;

/**
 * Interface for email sending utility
 */
public interface SendEmailService {

	/**
	 * Sends mail with subject and body provided as raw text
	 */
	public void sendMail(EmailVO emailEntity) throws InvalidInputException,
			UndeliveredEmailException;

	/**
	 * Sends mail with subject and body provided from templates and mail body
	 * replacements required
	 */
	public void sendEmailWithBodyReplacements(EmailVO emailEntity,
			String templateName, Map<String, String> tokenMap)
			throws InvalidInputException, UndeliveredEmailException;
}
