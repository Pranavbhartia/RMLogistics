package com.nexera.core.service;

import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.email.EmailVO;

/**
 * Interface for email sending utility
 */
public interface SendEmailService {

	/**
	 * Sends mail with subject and body provided as raw text, Set sync = false for delivering the mail asynchronously
	 */

	boolean sendMail(EmailVO emailEntity, boolean sync)
            throws InvalidInputException, UndeliveredEmailException;

}
