package com.nexera.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the customerdetails database table.
 * 
 */
@Entity
@Table(name = "customerspousedetails")
@NamedQuery(name = "CustomerSpouseDetail.findAll", query = "SELECT c FROM CustomerSpouseDetail c")
public class CustomerSpouseDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date spouseDateOfBirth;
	private String spouseSsn;
	private String spouseSecPhoneNumber;
	private String spouseName;
	private String spouseLastName;
	private Boolean isSelfEmployed;
	private Boolean isssIncomeOrDisability;
	private Boolean is_pension_or_retirement;
	
	private List<LoanAppForm> loanAppForms;
	private String equifaxScore;
	private String experianScore;
	private String transunionScore;
	private String currentHomePrice;
	private String currentHomeMortgageBalance;
	private String newHomeBudgetFromsale;
	private String streetAddress;
	private String state;
	private String city;
	private String zip;
	private Boolean notApplicable;
	
	// Income page input
	private BigDecimal selfEmployedIncome;
	private Integer selfEmployedNoYear; 
	private BigDecimal childSupportAlimony;
	private BigDecimal socialSecurityIncome;
	private BigDecimal  disabilityIncome;
	private BigDecimal monthlyPension;
	private BigDecimal retirementIncome;

	//skip my assests
	private Boolean skipMyAssets;
	
	public CustomerSpouseDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "spouse_date_of_birth")
	public Date getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}

	public void setSpouseDateOfBirth(Date spouseDateOfBirth) {
		this.spouseDateOfBirth = spouseDateOfBirth;
	}

	@Column(name = "spouse_ssn")
	public String getSpouseSsn() {
		return spouseSsn;
	}

	public void setSpouseSsn(String spouseSsn) {
		this.spouseSsn = spouseSsn;
	}

	@Column(name = "spouse_sec_phone_number")
	public String getSpouseSecPhoneNumber() {
		return spouseSecPhoneNumber;
	}

	public void setSpouseSecPhoneNumber(String spouseSecPhoneNumber) {
		this.spouseSecPhoneNumber = spouseSecPhoneNumber;
	}

	@Column(name = "spouse_name")
	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	@Column(name = "spouse_last_name")
	public String getSpouseLastName() {
		return spouseLastName;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}

	@Column(name = "is_self_employed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isSelfEmployed() {
		return isSelfEmployed;
	}

	public void setSelfEmployed(Boolean isSelfEmployed) {
		this.isSelfEmployed = isSelfEmployed;
	}

	@Column(name = "is_ssincome_or_disability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(Boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	@Column(name = "self_employed_income")
	public BigDecimal getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(BigDecimal selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}
	
	@Column(name = "monthly_pension")
	public BigDecimal getMonthlyPension() {
		return monthlyPension;
	}
	public void setMonthlyPension(BigDecimal monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	@Column(name = "disability_income")
	public BigDecimal getDisabilityIncome() {
		return disabilityIncome;
	}

	
	public void setDisabilityIncome(BigDecimal disabilityIncome) {
		this.disabilityIncome = disabilityIncome;
	}

	
	@Column(name = "is_pension_or_retirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isIs_pension_or_retirement() {
		return is_pension_or_retirement;
	}

	public void setIs_pension_or_retirement(Boolean is_pension_or_retirement) {
		this.is_pension_or_retirement = is_pension_or_retirement;
	}

	@OneToMany(mappedBy = "customerspousedetail", cascade = CascadeType.ALL)
	public List<LoanAppForm> getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(List<LoanAppForm> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	@Column(name = "equifax_score")
	public String getEquifaxScore() {
		return equifaxScore;
	}

	public void setEquifaxScore(String equifaxScore) {
		this.equifaxScore = equifaxScore;
	}

	@Column(name = "experian_score")
	public String getExperianScore() {
		return experianScore;
	}

	public void setExperianScore(String experianScore) {
		this.experianScore = experianScore;
	}

	@Column(name = "transunion_score")
	public String getTransunionScore() {
		return transunionScore;
	}

	public void setTransunionScore(String transunionScore) {
		this.transunionScore = transunionScore;
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

	@Column(name = "self_employed_no_year")
	public Integer getSelfEmployedNoYear() {
		return selfEmployedNoYear;
	}

	public void setSelfEmployedNoYear(Integer selfEmployedNoYear) {
		this.selfEmployedNoYear = selfEmployedNoYear;
	}

	@Column(name = "child_support_alimony")
	public BigDecimal getChildSupportAlimony() {
		return childSupportAlimony;
	}

	public void setChildSupportAlimony(BigDecimal childSupportAlimony) {
		this.childSupportAlimony = childSupportAlimony;
	}

	@Column(name = "social_security_income")
	public BigDecimal getSocialSecurityIncome() {
		return socialSecurityIncome;
	}

	public void setSocialSecurityIncome(BigDecimal socialSecurityIncome) {
		this.socialSecurityIncome = socialSecurityIncome;
	}

	@Column(name = "retirement_income")
	public BigDecimal getRetirementIncome() {
		return retirementIncome;
	}

	public void setRetirementIncome(BigDecimal retirementIncome) {
		this.retirementIncome = retirementIncome;
	}
	
	@Column(name = "skip_my_assets")
	public Boolean getSkipMyAssets() {
		return skipMyAssets;
	}

	public void setSkipMyAssets(Boolean skipMyAssets) {
		this.skipMyAssets = skipMyAssets;
	}

	@Column(name = "street_address")
	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "zip")
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "notApplicable")
	public Boolean getNotApplicable() {
	    return notApplicable;
    }

	public void setNotApplicable(Boolean notApplicable) {
	    this.notApplicable = notApplicable;
    }
}