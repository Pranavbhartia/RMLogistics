package com.nexera.common.vo.lqb;

import java.util.Date;

public class ModifiedLoanListResponseVO {
	private String loanName;
	private String oldName;
	private Boolean valid;
	private String borrowerName;
	private String borrowerSSN;
	private Date lastModified;
	private String loanStatus;

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getBorrowerSSN() {
		return borrowerSSN;
	}

	public void setBorrowerSSN(String borrowerSSN) {
		this.borrowerSSN = borrowerSSN;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
}
