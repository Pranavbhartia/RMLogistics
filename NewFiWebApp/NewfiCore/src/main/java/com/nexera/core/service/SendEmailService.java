package com.nexera.core.service;

import com.nexera.common.entity.Template;
import com.nexera.common.entity.User;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailVO;

/**
 * Interface for email sending utility
 */
public interface SendEmailService {

	/**
	 * Sends mail with subject and body provided as raw text, Set sync = false
	 * for delivering the mail asynchronously
	 */

	boolean sendMail(EmailVO emailEntity, boolean sync)
	        throws InvalidInputException, UndeliveredEmailException;

	boolean sendEmailForTeam(EmailVO emailEntity, int loanId, Template template)
	        throws InvalidInputException, UndeliveredEmailException;

	boolean sendEmailForInternalUsersAndSM(EmailVO emailEntity, int loanId,
	        Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	boolean sendEmailForCustomer(EmailVO emailEntity, int loanId,
	        Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	boolean sendEmailForCustomer(EmailVO emailEntity, UserVO userVO,
	        Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	boolean sendEmailForCustomer(EmailVO emailEntity, User user,
	        Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	boolean sendEmailForLoanManagers(EmailVO emailEntity, int loanId,
	        Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	public void sendSMS(UserVO user, String emailText);

	public void sendSMS(User user, String emailText);

	public boolean sendUnverifiedEmailToCustomer(EmailVO emailEntity,
	        User user, Template template) throws InvalidInputException,
	        UndeliveredEmailException;

	public boolean sendUnverifiedEmailToCustomer(EmailVO emailEntity,
	        UserVO userVO, Template template) throws InvalidInputException,
	        UndeliveredEmailException;
}
