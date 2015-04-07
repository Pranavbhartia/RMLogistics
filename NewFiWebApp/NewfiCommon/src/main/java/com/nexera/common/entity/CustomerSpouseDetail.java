package com.nexera.common.entity;

import java.io.Serializable;
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
	private boolean isSelfEmployed;
	private boolean isssIncomeOrDisability;
	private boolean is_pension_or_retirement;
	private String selfEmployedIncome;
	private String ssDisabilityIncome;
	private String monthlyPension;
	private List<LoanAppForm> loanAppForms;
	private String equifaxScore;
	private String experianScore;
	private String transunionScore;

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

	@Column(name = "is_self_employed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isSelfEmployed() {
		return isSelfEmployed;
	}

	public void setSelfEmployed(boolean isSelfEmployed) {
		this.isSelfEmployed = isSelfEmployed;
	}

	@Column(name = "is_ssincome_or_disability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	@Column(name = "self_employed_income")
	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	@Column(name = "ss_disability_income")
	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
	}

	@Column(name = "monthly_pension")
	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	@Column(name = "is_pension_or_retirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isIs_pension_or_retirement() {
		return is_pension_or_retirement;
	}

	public void setIs_pension_or_retirement(boolean is_pension_or_retirement) {
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

}