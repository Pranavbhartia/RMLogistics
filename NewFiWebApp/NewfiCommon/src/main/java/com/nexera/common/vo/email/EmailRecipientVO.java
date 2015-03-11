package com.nexera.common.vo.email;

import com.nexera.common.enums.EmailRecipientTypeEnum;

/**
 * 
 * @author Balaji
 *
 *         Purpose of this class is to contain the details of a email recipient
 *         We have the recipient type(to,cc or bcc)
 *         Email ID of the recipient and the recipient's name if exists
 */
public class EmailRecipientVO {

	private EmailRecipientTypeEnum recipientTypeEnum;
	private String emailID;
	private String recipientName;

	public EmailRecipientTypeEnum getRecipientTypeEnum() {
		return recipientTypeEnum;
	}

	public void setRecipientTypeEnum(EmailRecipientTypeEnum recipientTypeEnum) {
		this.recipientTypeEnum = recipientTypeEnum;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

}
