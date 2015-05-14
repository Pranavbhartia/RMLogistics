package com.nexera.common.vo;

import java.io.Serializable;

public class UpdatePasswordVO implements Serializable {

	private static final long serialVersionUID = 1L;
	int userId;
	String emailID;
	String newPassword;
	boolean verifyEmailPath;

	public boolean isVerifyEmailPath() {
		return verifyEmailPath;
	}

	public void setVerifyEmailPath(boolean verifyEmailPath) {
		this.verifyEmailPath = verifyEmailPath;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
