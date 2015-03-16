package com.nexera.core.service;

import java.util.Map;
import org.springframework.stereotype.Component;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;

/**
 * @author karthik 
 * 		This is the class that has methods to make api calls to Sendgrid
 *      to send emails.
 */
@Component
public interface SendGridMailService {
	
	/**
	 * Send mail to a single email id
	 * @param recipientEmailId
	 * @param userName
	 * @param subject
	 * @param substitutions
	 * @param templateId
	 * @throws UndeliveredEmailException
	 * @throws InvalidInputException
	 */
	public void sendEmailUsingTemplate(String recipientEmailId,String userName,String subject, Map<String, String> substitutions, String templateId) throws UndeliveredEmailException, InvalidInputException;
	
	/**
	 * Send email to multiple recipients
	 * @param recipientIdList
	 * @param userNames
	 * @param subject
	 * @param substitutions
	 * @param templateId
	 * @throws UndeliveredEmailException
	 * @throws InvalidInputException
	 */
	public void sendEmailUsingTemplateToMultiple(String[] recipientIdList,String[] userNames,String subject,Map<String, String[]> substitutions, String templateId) throws UndeliveredEmailException, InvalidInputException;

}
