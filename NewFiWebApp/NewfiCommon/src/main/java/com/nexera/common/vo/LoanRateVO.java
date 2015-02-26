package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
public class LoanRateVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Date lastCachedTime;
	private Date modifiedDate;
	private Double value;
	private LoanVO loan;
	private UserVO modifiedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getLastCachedTime() {
		return lastCachedTime;
	}
	public void setLastCachedTime(Date lastCachedTime) {
		this.lastCachedTime = lastCachedTime;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


}