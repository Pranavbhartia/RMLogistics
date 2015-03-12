package com.nexera.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Entity containing the smtp settings for sending mail
 */
@Component
public class SmtpSettings {

	@Value("${SMTP_HOST}")
	private String mailHost;
	@Value("${SMTP_PORT}")
	private int mailPort;
	@Value("${SENDGRID_USERNAME}")
	private String senderName;
	@Value("${SENDGRID_PASSWORD}")
	private String senderPassword;

	public static String MAIL_SMTP_AUTH = "true";
	public static String MAIL_SMTP_STARTTLS_ENABLE = "true";
	public static String MAIL_TRANSPORT = "smtp";

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public int getMailPort() {
		return mailPort;
	}

	public void setMailPort(int mailPort) {
		this.mailPort = mailPort;
	}

	public String getSenderName() {
		return senderName;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

	@Override
	public String toString() {
		return "SmtpSettings [mailHost=" + mailHost + ", mailPort=" + mailPort
		        + "]";
	}

}
