package com.nexera.core.service.impl;

import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.core.service.SendGridMailService;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

/**
 * @author karthik 
 * 		This is the class that has methods to make api calls to Sendgrid
 *      to send emails.
 */

@Component
public class SendGridMailServiceImpl implements SendGridMailService,InitializingBean {
	
	@Value("${SENDGRID_USERNAME}")
	private String sendGridUserName;
	
	@Value("${SENDGRID_PASSWORD}")
	private String sendGridPassword;
	
	SendGrid sendGrid;
	
	private static final Logger LOG = LoggerFactory.getLogger(SendGridMailServiceImpl.class);
	
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
	@Override
	public void sendEmailUsingTemplate(String recipientEmailId,String userName,String subject, Map<String, String> substitutions, String templateId) throws UndeliveredEmailException, InvalidInputException {
		
		if( recipientEmailId == null || recipientEmailId.isEmpty()){
			LOG.error("sendEmailUsingTemplate : recipientEmailId is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : recipientEmailId is null or empty!");
		}
		if(userName == null || userName.isEmpty()){
			LOG.error("sendEmailUsingTemplate : userName is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : userName is null or empty!");
		}
		if(substitutions == null){
			LOG.error("sendEmailUsingTemplate : substitutions is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : substitutions is null or empty!");
		}
		if(templateId == null || templateId.isEmpty()){
			LOG.error("sendEmailUsingTemplate : templateId is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : templateId is null or empty!");
		}
		
		Response response = null;
		
		LOG.info("sendEmailUsingTemplate called to send email to : " + recipientEmailId);
		Email email = new Email();
		email.addTo(recipientEmailId);
		email.addToName(userName);
		email.setFromName(CommonConstants.SENDER_NAME);
		email.setFrom(CommonConstants.SENDER_EMAIL_ID);
		email.setSubject(subject);
		email.setText(CommonConstants.EMAIL_FOOTER);
		for(Entry<String, String> entry : substitutions.entrySet()){
			email.addSubstitution(entry.getKey(), new String[]{entry.getValue()});
		}
		email.addFilter("templates", "enable", "1");
		email.addFilter("templates", "template_id", templateId);
		LOG.debug("Email entity created. Sending email!");
		try {
			LOG.debug("Making sendgrid api call to send email");
			response = sendGrid.send(email);
		}
		catch (SendGridException e) {
			LOG.error("SendGridException caught while sending mail to : " + recipientEmailId + " for templateId : " + templateId + " message : " + e.getMessage());
			throw new UndeliveredEmailException("SendGridException caught while sending mail to : " + recipientEmailId + " for templateId : " + templateId + " message : " + e.getMessage());
		}
		LOG.info("Email api call status : " + response.getStatus() + " message : " + response.getMessage());
		
		if(response.getStatus() == true){
			LOG.debug("Api call successful. Email sent");
		}
		else{
			LOG.error("Mail not delivered. Reason : " + response.getMessage());
			throw new UndeliveredEmailException("Mail not delivered. Reason : " + response.getMessage());
		}
		
	}
	
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
	@Override
	public void sendEmailUsingTemplateToMultiple(String[] recipientIdList,String[] usernames,String subject, Map<String, String[]> substitutions, String templateId) throws UndeliveredEmailException, InvalidInputException {
		
		if( recipientIdList == null || recipientIdList.length == 0){
			LOG.error("sendEmailUsingTemplate : recipientEmailId is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : recipientEmailId is null or empty!");
		}
		if(usernames == null || usernames.length != recipientIdList.length){
			LOG.error("sendEmailUsingTemplate : userName is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : userName is null or empty!");
		}
		if(substitutions == null){
			LOG.error("sendEmailUsingTemplate : substitutions is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : substitutions is null or empty!");
		}
		if(templateId == null || templateId.isEmpty()){
			LOG.error("sendEmailUsingTemplate : templateId is null or empty!");
			throw new InvalidInputException("sendEmailUsingTemplate : templateId is null or empty!");
		}
		
		Response response = null;
		LOG.info("sendEmailUsingTemplateToMultiple called to send mails");
		Email email = new Email();
		email.addTo(recipientIdList);
		email.addToName(usernames);
		email.setFrom(CommonConstants.SENDER_EMAIL_ID);
		email.setFromName(CommonConstants.SENDER_NAME);
		email.setSubject(subject);
		email.setText(CommonConstants.EMAIL_FOOTER);
		
		for(Entry<String, String[]> entry : substitutions.entrySet()){
			email.addSubstitution(entry.getKey(), entry.getValue());
		}
		
		email.addFilter("templates", "enable", "1");
		email.addFilter("templates", "template_id", templateId);
		
		LOG.debug("Email entity has been initialised");
		
		try {
			LOG.debug("Making sendgrid api call");
			response = sendGrid.send(email);
			LOG.debug("Api call complete");
		}
		catch (SendGridException e) {
			LOG.error("SendGridException caught while sending mail  for templateId : " + templateId + " message : " + e.getMessage());
			throw new UndeliveredEmailException("SendGridException caught while sending mail  for templateId : " + templateId + " message : " + e.getMessage());
		}
		
		LOG.info("Email api call status : " + response.getStatus() + " message : " + response.getMessage());
		
		if(response.getStatus() == true){
			LOG.debug("Api call successful. Email sent");
		}
		else{
			LOG.error("Mail not delivered. Reason : " + response.getMessage());
			throw new UndeliveredEmailException("Mail not delivered. Reason : " + response.getMessage());
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		LOG.info("Settings Up sendGrid gateway");
		
		if(sendGrid == null){
			LOG.info("Initialising Sendgrid gateway with " + sendGridUserName + " and " + sendGridPassword);
			sendGrid = new SendGrid(sendGridUserName, sendGridPassword);
			LOG.info("Sendgrid gateway initialised!");
		}
	}


}
