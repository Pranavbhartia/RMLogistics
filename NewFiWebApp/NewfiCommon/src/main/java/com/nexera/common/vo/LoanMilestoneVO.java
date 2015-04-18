package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class LoanMilestoneVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte[] comments;
	private Date createdDate;
	private Date endDate;
	private Date startDate;
	private String status;
	private LoanMilestoneMasterVO loanMilestoneMaster;
	private LoanVO loan;
	private int order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getComments() {
		return comments;
	}

	public void setComments(byte[] comments) {
		this.comments = comments;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LoanMilestoneMasterVO getLoanMilestoneMaster() {
		return loanMilestoneMaster;
	}

	public void setLoanMilestoneMaster(LoanMilestoneMasterVO loanMilestoneMaster) {
		this.loanMilestoneMaster = loanMilestoneMaster;
	}

	public LoanVO getLoan() {
		return loan;
	}

	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
}