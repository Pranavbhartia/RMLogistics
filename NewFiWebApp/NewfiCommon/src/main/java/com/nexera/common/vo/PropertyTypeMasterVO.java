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
	private String residenceTypeCd;
	private String propertyTaxesPaid;
	private String propertyInsuranceProvider;
	private String propertyInsuranceCost;
	private String propertyPurchaseYear;
	private String homeWorthToday;
	
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
	
	public String getResidenceTypeCd() {
		return residenceTypeCd;
	}
	public void setResidenceTypeCd(String residenceTypeCd) {
		this.residenceTypeCd = residenceTypeCd;
	}
	public String getPropertyTaxesPaid() {
		return propertyTaxesPaid;
	}
	public void setPropertyTaxesPaid(String propertyTaxesPaid) {
		this.propertyTaxesPaid = propertyTaxesPaid;
	}
	public String getPropertyInsuranceProvider() {
		return propertyInsuranceProvider;
	}
	public void setPropertyInsuranceProvider(String propertyInsuranceProvider) {
		this.propertyInsuranceProvider = propertyInsuranceProvider;
	}
	public String getPropertyInsuranceCost() {
		return propertyInsuranceCost;
	}
	public void setPropertyInsuranceCost(String propertyInsuranceCost) {
		this.propertyInsuranceCost = propertyInsuranceCost;
	}
	public String getPropertyPurchaseYear() {
		return propertyPurchaseYear;
	}
	public void setPropertyPurchaseYear(String propertyPurchaseYear) {
		this.propertyPurchaseYear = propertyPurchaseYear;
	}

	public String getHomeWorthToday() {
		return homeWorthToday;
	}
	public void setHomeWorthToday(String homeWorthToday) {
		this.homeWorthToday = homeWorthToday;
	}
}