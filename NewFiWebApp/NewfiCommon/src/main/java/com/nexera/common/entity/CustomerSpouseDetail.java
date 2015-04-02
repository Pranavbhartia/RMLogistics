package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
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
	private int id;
	private Date spouseDateOfBirth;
	private String spouseSsn;
	private String spouseSecPhoneNumber;
	private String spouseName;
	private boolean isSelfEmployed;
	private boolean isssIncomeOrDisability;
	private boolean ispensionOrRetirement;
	private String selfEmployedIncome;
	private String ssDisabilityIncome;
	private String monthlyPension;
	
	

	public CustomerSpouseDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "spousedateOfBirth")
	public Date getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}

	public void setSpouseDateOfBirth(Date spouseDateOfBirth) {
		this.spouseDateOfBirth = spouseDateOfBirth;
	}

	@Column(name = "spouseSsn")
	public String getSpouseSsn() {
		return spouseSsn;
	}

	public void setSpouseSsn(String spouseSsn) {
		this.spouseSsn = spouseSsn;
	}

	@Column(name = "spouseSecPhoneNumber")
	public String getSpouseSecPhoneNumber() {
		return spouseSecPhoneNumber;
	}

	public void setSpouseSecPhoneNumber(String spouseSecPhoneNumber) {
		this.spouseSecPhoneNumber = spouseSecPhoneNumber;
	}

	@Column(name = "spouseName")
	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	@Column(name = "isSelfEmployed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isSelfEmployed() {
		return isSelfEmployed;
	}

	public void setSelfEmployed(boolean isSelfEmployed) {
		this.isSelfEmployed = isSelfEmployed;
	}

	@Column(name = "isssIncomeOrDisability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	@Column(name = "ispensionOrRetirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isIspensionOrRetirement() {
		return ispensionOrRetirement;
	}

	public void setIspensionOrRetirement(boolean ispensionOrRetirement) {
		this.ispensionOrRetirement = ispensionOrRetirement;
	}

	@Column(name = "selfEmployedIncome")
	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	@Column(name = "ssDisabilityIncome")
	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
	}

	@Column(name = "monthlyPension")
	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	

}