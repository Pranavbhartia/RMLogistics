package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PropertyTypeMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private Date modifiedDate;
	private String propertyTypeCd;
	private List<LoanVO> loans;
	private List<LoanAppFormVO> loanAppForms;
	private List<LoanApplicationFeeMasterVO> loanApplicationFeeMasters;
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
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPropertyTypeCd() {
		return propertyTypeCd;
	}
	public void setPropertyTypeCd(String propertyTypeCd) {
		this.propertyTypeCd = propertyTypeCd;
	}
	public List<LoanVO> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}
	public List<LoanAppFormVO> getLoanAppForms() {
		return loanAppForms;
	}
	public void setLoanAppForms(List<LoanAppFormVO> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}
	public List<LoanApplicationFeeMasterVO> getLoanApplicationFeeMasters() {
		return loanApplicationFeeMasters;
	}
	public void setLoanApplicationFeeMasters(
			List<LoanApplicationFeeMasterVO> loanApplicationFeeMasters) {
		this.loanApplicationFeeMasters = loanApplicationFeeMasters;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	

}