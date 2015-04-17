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
	private String homeZipCode;
	private List<LoanVO> loans;
	private List<LoanAppFormVO> loanAppForms;
	private List<LoanApplicationFeeMasterVO> loanApplicationFeeMasters;
	private UserVO modifiedBy;
	private String propTaxMonthlyOryearly;
	private String currentHomePrice;
	private String currentHomeMortgageBalance;
	private String newHomeBudgetFromsale;
	private String propInsMonthlyOryearly;

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

	public String getHomeZipCode() {
		return homeZipCode;
	}

	public void setHomeZipCode(String homeZipCode) {
		this.homeZipCode = homeZipCode;
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

	public String getPropTaxMonthlyOryearly() {
		return propTaxMonthlyOryearly;
	}

	public void setPropTaxMonthlyOryearly(String propTaxMonthlyOryearly) {
		this.propTaxMonthlyOryearly = propTaxMonthlyOryearly;
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

	public String getCurrentHomePrice() {
		return currentHomePrice;
	}

	public void setCurrentHomePrice(String currentHomePrice) {
		this.currentHomePrice = currentHomePrice;
	}

	public String getCurrentHomeMortgageBalance() {
		return currentHomeMortgageBalance;
	}

	public void setCurrentHomeMortgageBalance(String currentHomeMortgageBalance) {
		this.currentHomeMortgageBalance = currentHomeMortgageBalance;
	}

	public String getNewHomeBudgetFromsale() {
		return newHomeBudgetFromsale;
	}

	public void setNewHomeBudgetFromsale(String newHomeBudgetFromsale) {
		this.newHomeBudgetFromsale = newHomeBudgetFromsale;
	}

	public String getPropInsMonthlyOryearly() {
		return propInsMonthlyOryearly;
	}

	public void setPropInsMonthlyOryearly(String propInsMonthlyOryearly) {
		this.propInsMonthlyOryearly = propInsMonthlyOryearly;
	}

}