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

	@Value("${sms.body}")
	private String smsBodyText;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(SMSServiceHelperImpl.class);

	@Override
	public String sendNotificationSMS(String carrierName, long phoneNumber) {
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
		if (send(maxLength, carrierEmailAddress)) {
			return "Message Successfully Sent ";
		} else {
			return "Message was not send ";
		}

	}

	public boolean send(int maxlength, String mailAddress) {
		String[] tos = new String[1];
		tos[0] = mailAddress;
		Email email = new Email();
		int msgLength;
		String subject = " ";
		LOGGER.debug("Calculating message Length ");
		msgLength = CommonConstants.SENDER_EMAIL_ID.length() + 1
		        + subject.length() + 1 + smsBodyText.length();
		if (msgLength > maxlength) {
			LOGGER.error("Message Length Too Long ");
			return false;
		}
		email.setText(smsBodyText);
		email.setFrom(CommonConstants.SENDER_EMAIL_ID);
		email.setSubject(subject);
		email.setTo(tos);
		if (sendGridEmailService.sendSMSEmail(email) == null) {
			return false;
		} else {
			return true;
		}
	}
}
