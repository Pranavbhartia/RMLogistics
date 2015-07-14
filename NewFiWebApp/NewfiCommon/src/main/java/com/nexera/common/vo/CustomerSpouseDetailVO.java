package com.nexera.common.vo;

import java.io.Serializable;

public class CustomerSpouseDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String spouseDateOfBirth;
	private String spouseSsn;
	private String spouseSecPhoneNumber;
	private String spouseName;
	private String spouseLastName;
	private boolean isSelfEmployed;
	private boolean isssIncomeOrDisability;
	private boolean ispensionOrRetirement;
	private String ssDisabilityIncome;
	private String experianScore;
	private String equifaxScore;
	private String transunionScore;
	private String currentHomePrice;
	private String currentHomeMortgageBalance;
	private String newHomeBudgetFromsale;
	private Boolean notApplicable;
	
	// Income page input
	private String selfEmployedIncome;
	private String selfEmployedNoYear; 
	private String childSupportAlimony;
	private String socialSecurityIncome;
	private String  disabilityIncome;
	private String monthlyPension;
	private String retirementIncome;
	private String streetAddress;
	private String state;
	private String city;
	private String zip;

	//My Assets
	private Boolean skipMyAssets;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}
	public void setSpouseDateOfBirth(String spouseDateOfBirth) {
		
		this.spouseDateOfBirth=spouseDateOfBirth;
	}


	public String getSpouseSsn() {
		return spouseSsn;
	}

	public void setSpouseSsn(String spouseSsn) {
		this.spouseSsn = spouseSsn;
	}

	public String getSpouseSecPhoneNumber() {
		return spouseSecPhoneNumber;
	}

	public void setSpouseSecPhoneNumber(String spouseSecPhoneNumber) {
		this.spouseSecPhoneNumber = spouseSecPhoneNumber;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public String getSpouseLastName() {
		return spouseLastName;
	}

	public void setSpouseLastName(String spouseLastName) {
		this.spouseLastName = spouseLastName;
	}

	public boolean isSelfEmployed() {
		return isSelfEmployed;
	}

	public void setSelfEmployed(boolean isSelfEmployed) {
		this.isSelfEmployed = isSelfEmployed;
	}

	public boolean isIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	public boolean isIspensionOrRetirement() {
		return ispensionOrRetirement;
	}

	public void setIspensionOrRetirement(boolean ispensionOrRetirement) {
		this.ispensionOrRetirement = ispensionOrRetirement;
	}

	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
	}

	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	public String getExperianScore() {
		return experianScore;
	}

	public void setExperianScore(String experianScore) {
		this.experianScore = experianScore;
	}

	public String getEquifaxScore() {
		return equifaxScore;
	}

	public void setEquifaxScore(String equifaxScore) {
		this.equifaxScore = equifaxScore;
	}

	public String getTransunionScore() {
		return transunionScore;
	}

	public void setTransunionScore(String transunionScore) {
		this.transunionScore = transunionScore;
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

	public String getSelfEmployedNoYear() {
		return selfEmployedNoYear;
	}

	public void setSelfEmployedNoYear(String selfEmployedNoYear) {
		this.selfEmployedNoYear = selfEmployedNoYear;
	}

	public String getChildSupportAlimony() {
		return childSupportAlimony;
	}

	public void setChildSupportAlimony(String childSupportAlimony) {
		this.childSupportAlimony = childSupportAlimony;
	}

	public String getSocialSecurityIncome() {
		return socialSecurityIncome;
	}

	public void setSocialSecurityIncome(String socialSecurityIncome) {
		this.socialSecurityIncome = socialSecurityIncome;
	}

	public String getDisabilityIncome() {
		return disabilityIncome;
	}

	public void setDisabilityIncome(String disabilityIncome) {
		this.disabilityIncome = disabilityIncome;
	}

	public String getRetirementIncome() {
		return retirementIncome;
	}

	public void setRetirementIncome(String retirementIncome) {
		this.retirementIncome = retirementIncome;
	}

	public Boolean getSkipMyAssets() {
		return skipMyAssets;
	}

	public void setSkipMyAssets(Boolean skipMyAssets) {
		this.skipMyAssets = skipMyAssets;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Boolean getNotApplicable() {
	    return notApplicable;
    }

	public void setNotApplicable(Boolean notApplicable) {
	    this.notApplicable = notApplicable;
    }
	
	
	
	
}
