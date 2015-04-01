package com.nexera.common.vo;

import java.io.Serializable;

public class LoanTurnAroundTimeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer loanId;
	private Integer workItemMasterId;
	private Integer hours;

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getWorkItemMasterId() {
		return workItemMasterId;
	}

	public void setWorkItemMasterId(Integer workItemMasterId) {
		this.workItemMasterId = workItemMasterId;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

}
