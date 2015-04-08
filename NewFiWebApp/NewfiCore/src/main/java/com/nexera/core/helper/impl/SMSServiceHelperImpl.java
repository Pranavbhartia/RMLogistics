package com.nexera.core.helper.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.core.helper.SMSServiceHelper;
import com.nexera.core.utility.MobileCarriers;

@Component
public class SMSServiceHelperImpl implements SMSServiceHelper {

	@Value("${sms.mail.host}")
	private String mailHost;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SMSServiceHelperImpl.class);

	@Override
	public String sendNotificationSMS(String carrierName, long phoneNumber,
	        String textMessage) {
		LOGGER.debug("Inside method sendNotificationSMS ");
		String carrierEmailAddress = null;
		int maxLength = 0;
		if (carrierName.equalsIgnoreCase(MobileCarriers.CARRIER_NAME_AT_T)) {
			carrierEmailAddress = MobileCarriers.AT_T_EMAIL_ADDRESS;
			maxLength = 140;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_ALLTEL)) {
			carrierEmailAddress = MobileCarriers.ALLTEL_EMAIL_ADDRESS;
			maxLength = 300;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_NEXTEL)) {
			carrierEmailAddress = MobileCarriers.NEXTEL_EMAIL_ADDRESS;
			maxLength = 280;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_SPRINT)) {
			carrierEmailAddress = MobileCarriers.SPRINT_EMAIL_ADDRESS;
			maxLength = 160;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_BOOST_MOBILE)) {
			carrierEmailAddress = MobileCarriers.BOOST_MOBILE_EMAIL_ADDRESS;
			maxLength = 500;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_CINGULAR)) {
			carrierEmailAddress = MobileCarriers.CINGULAR_EMAIL_ADDRES;
			maxLength = 150;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_SPRINT)) {
			carrierEmailAddress = MobileCarriers.SPRINT_EMAIL_ADDRESS;
			maxLength = 160;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_T_MOBILE)) {
			carrierEmailAddress = MobileCarriers.T_MOBILE_EMAIL_ADDRESS;
			maxLength = 140;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_VERIZON)) {
			carrierEmailAddress = MobileCarriers.VERIZON_EMAIL_ADDRESS;
			maxLength = 160;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CARRIER_NAME_VIRGIN_MOBILE)) {
			carrierEmailAddress = MobileCarriers.VIRGIN_MOBILE_EMAIL_ADDRESS;
			maxLength = 160;
		}

		carrierEmailAddress = String.valueOf(phoneNumber) + carrierEmailAddress;
		if (send(maxLength, carrierEmailAddress, textMessage)) {
			return "Message Successfully Sent ";
		} else {
			return "Message was not send ";
		}

	}

	public boolean send(int maxlength, String mailAddress, String textMessage) {

		int msgLength;
		final String username = "myusername@gmail.com";
		final String password = "mypassword";
		String fromAddress = CommonConstants.SENDER_EMAIL_ID;
		String subject = "";
		LOGGER.debug("Calculating message Length ");
		msgLength = fromAddress.length() + 1 + subject.length() + 1
		        + textMessage.length();
		if (msgLength > maxlength) {
			LOGGER.error("Message Length Too Long ");
			return false;
		}
		Properties props = System.getProperties();

		if (mailHost != null) {
			props.put("mail.smtp.host", mailHost);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
		}

		Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(username, password);
	                }
	            });

		try {

			Message msg = new MimeMessage(session);

			if (fromAddress != null) {
				msg.setFrom(new InternetAddress(fromAddress));
			}
			msg.setSubject(subject);
			msg.setText(textMessage);

			// Add Recipient
			msg.setRecipients(Message.RecipientType.TO,
			        InternetAddress.parse(mailAddress, false));

			msg.setSentDate(new Date());

			Transport.send(msg);

			return true;
		} catch (MessagingException mex) {
			LOGGER.error("Exception caught while sending message");
			return false;
		}

	}
}
