package com.nexera.core.helper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.core.helper.SMSServiceHelper;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.utility.MobileCarriers;
import com.sendgrid.SendGrid.Email;

@Component
public class SMSServiceHelperImpl implements SMSServiceHelper {

	@Value("${sms.newfi.url}")
	private String smsNewfiUrl;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SMSServiceHelperImpl.class);

	@Override
	public String sendNotificationSMS(String carrierName, long phoneNumber,
	        String emailText) {
		LOGGER.debug("Inside method sendNotificationSMS ");
		String carrierEmailAddress = null;
		int maxLength = 140;
		if (carrierName.equalsIgnoreCase(MobileCarriers.AT_T_EMAIL_ADDRESS)) {
			maxLength = 140;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.ALLTEL_EMAIL_ADDRESS)) {

			maxLength = 300;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.NEXTEL_EMAIL_ADDRESS)) {
			maxLength = 280;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.SPRINT_EMAIL_ADDRESS)) {
			maxLength = 160;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.CINGULAR_EMAIL_ADDRES)) {
			maxLength = 150;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.BOOST_MOBILE_EMAIL_ADDRESS)) {

			maxLength = 500;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.T_MOBILE_EMAIL_ADDRESS)) {
			maxLength = 140;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.VERIZON_EMAIL_ADDRESS)) {
			maxLength = 160;
		} else if (carrierName
		        .equalsIgnoreCase(MobileCarriers.VIRGIN_MOBILE_EMAIL_ADDRESS)) {
			maxLength = 160;
		}

		carrierEmailAddress = String.valueOf(phoneNumber) + carrierName;
		if (send(maxLength, carrierEmailAddress, emailText)) {
			return "Message Successfully Sent ";
		} else {
			return "Message was not send ";
		}

	}

	public boolean send(int maxlength, String mailAddress, String emailText) {
		String[] tos = new String[1];
		tos[0] = mailAddress;
		Email email = new Email();
		int msgLength = 0;
		if (emailText == null) {
			emailText = "";
		}
		String subject = " ";
		LOGGER.debug("Calculating message Length ");

		msgLength = CommonConstants.SENDER_DEFAULT_USER_NAME.length()
		        + CommonConstants.SENDER_EMAIL_ID.length() + 1
		        + subject.length() + 1 + emailText.length()
		        + smsNewfiUrl.length() + 1;

		if (msgLength > maxlength) {
			LOGGER.error("Message Length Too Long ");
			return false;
		}
		email.setText(emailText + ". " + CommonConstants.SMS_DEFAULT_TEXT
		        + ". " + smsNewfiUrl);
		email.setFrom(CommonConstants.SENDER_DEFAULT_USER_NAME
		        + CommonConstants.SENDER_EMAIL_ID);
		email.setSubject(subject);
		email.setTo(tos);
		if (sendGridEmailService.sendSMSEmail(email) == null) {
			return false;
		} else {
			return true;
		}
	}
}
