package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class LoanStatusMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String loanStatusCd;
	private Date modifiedDate;
	private List<LoanVO> loans;
	private UserVO modifiedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLoanStatusCd() {
		return loanStatusCd;
	}
	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public List<LoanVO> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}