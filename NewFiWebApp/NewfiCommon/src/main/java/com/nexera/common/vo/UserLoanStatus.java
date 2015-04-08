package com.nexera.common.vo;

import java.util.Date;

public class UserLoanStatus {

	private String creditInformation;
	private String creditDecission;
	private String lockRate;
	private Date lockExpiryDate;
	private String loanPurpose;
	private String loanProgress;

	public String getCreditInformation() {
		return creditInformation;
	}

	public void setCreditInformation(String creditInformation) {
		this.creditInformation = creditInformation;
	}

	public String getCreditDecission() {
		return creditDecission;
	}

	public void setCreditDecission(String creditDecission) {
		this.creditDecission = creditDecission;
	}

	public String getLockRate() {
		return lockRate;
	}

	public void setLockRate(String lockRate) {
		this.lockRate = lockRate;
	}

	public Date getLockExpiryDate() {
		return lockExpiryDate;
	}

	public void setLockExpiryDate(Date lockExpiryDate) {
		this.lockExpiryDate = lockExpiryDate;
	}

	public String getLoanPurpose() {
		return loanPurpose;
	}

	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}

	public String getLoanProgress() {
		return loanProgress;
	}

	public void setLoanProgress(String loanProgress) {
		this.loanProgress = loanProgress;
	}

}
