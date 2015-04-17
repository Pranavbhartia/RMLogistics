package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the propertytypemaster database table.
 * 
 */
@Entity
@Table(name = "propertytypemaster")
@NamedQuery(name = "PropertyTypeMaster.findAll", query = "SELECT p FROM PropertyTypeMaster p")
public class PropertyTypeMaster implements Serializable {
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
	private String propTaxMonthlyOryearly;
	private String currentHomePrice;
	private String currentHomeMortgageBalance;
	private String newHomeBudgetFromsale;
	private String propInsMonthlyOryearly;
	private List<Loan> loans;
	private List<LoanAppForm> loanAppForms;
	private List<LoanApplicationFeeMaster> loanApplicationFeeMasters;
	private User modifiedBy;

	public PropertyTypeMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "property_type_cd")
	public String getPropertyTypeCd() {
		return this.propertyTypeCd;
	}

	public void setPropertyTypeCd(String propertyTypeCd) {
		this.propertyTypeCd = propertyTypeCd;
	}

	@Column(name = "homeZipCode")
	public String getHomeZipCode() {
		return homeZipCode;
	}

	public void setHomeZipCode(String homeZipCode) {
		this.homeZipCode = homeZipCode;
	}

	@Column(name = "residence_type_cd")
	public String getResidenceTypeCd() {
		return residenceTypeCd;
	}

	public void setResidenceTypeCd(String residenceTypeCd) {
		this.residenceTypeCd = residenceTypeCd;
	}

	@Column(name = "property_tax")
	public String getPropertyTaxesPaid() {
		return propertyTaxesPaid;
	}

	public void setPropertyTaxesPaid(String propertyTaxesPaid) {
		this.propertyTaxesPaid = propertyTaxesPaid;
	}

	@Column(name = "prop_tax_mon_yrly")
	public String getPropTaxMonthlyOryearly() {
		return propTaxMonthlyOryearly;
	}

	public void setPropTaxMonthlyOryearly(String propTaxMonthlyOryearly) {
		this.propTaxMonthlyOryearly = propTaxMonthlyOryearly;
	}

	@Column(name = "propertyInsuranceProvider")
	public String getPropertyInsuranceProvider() {
		return propertyInsuranceProvider;
	}

	public void setPropertyInsuranceProvider(String propertyInsuranceProvider) {
		this.propertyInsuranceProvider = propertyInsuranceProvider;
	}

	@Column(name = "propertyInsuranceCost")
	public String getPropertyInsuranceCost() {
		return propertyInsuranceCost;
	}

	public void setPropertyInsuranceCost(String propertyInsuranceCost) {
		this.propertyInsuranceCost = propertyInsuranceCost;
	}

	@Column(name = "propertyPurchaseYear")
	public String getPropertyPurchaseYear() {
		return propertyPurchaseYear;
	}

	public void setPropertyPurchaseYear(String propertyPurchaseYear) {
		this.propertyPurchaseYear = propertyPurchaseYear;
	}

	@Column(name = "homeWorthToday")
	public String getHomeWorthToday() {
		return homeWorthToday;
	}

	public void setHomeWorthToday(String homeWorthToday) {
		this.homeWorthToday = homeWorthToday;
	}

	// bi-directional many-to-one association to Loan
	@OneToMany(mappedBy = "propertyType")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setPropertyType(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setPropertyType(null);

		return loan;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "propertyTypeMaster")
	public List<LoanAppForm> getLoanappforms() {
		return this.loanAppForms;
	}

	public void setLoanappforms(List<LoanAppForm> loanappforms) {
		this.loanAppForms = loanappforms;
	}

	public LoanAppForm addLoanappform(LoanAppForm loanappform) {
		getLoanappforms().add(loanappform);
		loanappform.setPropertyTypeMaster(this);

		return loanappform;
	}

	public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
		getLoanappforms().remove(loanappform);
		loanappform.setPropertyTypeMaster(null);

		return loanappform;
	}

	// bi-directional many-to-one association to LoanApplicationFeeMaster
	@OneToMany(mappedBy = "propertyTypeMaster")
	public List<LoanApplicationFeeMaster> getLoanApplicationFeeMasters() {
		return this.loanApplicationFeeMasters;
	}

	public void setLoanApplicationFeeMasters(
	        List<LoanApplicationFeeMaster> loanapplicationfeemasters) {
		this.loanApplicationFeeMasters = loanapplicationfeemasters;
	}

	public LoanApplicationFeeMaster addLoanApplicationfeemaster(
	        LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanApplicationFeeMasters().add(loanapplicationfeemaster);
		loanapplicationfeemaster.setPropertyTypeMaster(this);

		return loanapplicationfeemaster;
	}

	public LoanApplicationFeeMaster removeLoanApplicationFeeMaster(
	        LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanApplicationFeeMasters().remove(loanapplicationfeemaster);
		loanapplicationfeemaster.setPropertyTypeMaster(null);

		return loanapplicationfeemaster;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "current_home_price")
	public String getCurrentHomePrice() {
		return currentHomePrice;
	}

	public void setCurrentHomePrice(String currentHomePrice) {
		this.currentHomePrice = currentHomePrice;
	}

	@Column(name = "current_home_mortgage_balance")
	public String getCurrentHomeMortgageBalance() {
		return currentHomeMortgageBalance;
	}

	public void setCurrentHomeMortgageBalance(String currentHomeMortgageBalance) {
		this.currentHomeMortgageBalance = currentHomeMortgageBalance;
	}

	@Column(name = "newhome_budget_fromsale")
	public String getNewHomeBudgetFromsale() {
		return newHomeBudgetFromsale;
	}

	public void setNewHomeBudgetFromsale(String newHomeBudgetFromsale) {
		this.newHomeBudgetFromsale = newHomeBudgetFromsale;
	}

	@Column(name = "prop_ins_mon_yrly")
	public String getPropInsMonthlyOryearly() {
		return propInsMonthlyOryearly;
	}

	public void setPropInsMonthlyOryearly(String propInsMonthlyOryearly) {
		this.propInsMonthlyOryearly = propInsMonthlyOryearly;
	}

}