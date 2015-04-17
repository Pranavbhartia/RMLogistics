package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class CustomerSpouseDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private Long spouseDateOfBirth;
	private String spouseSsn;
	private String spouseSecPhoneNumber;
	private String spouseName;
	private boolean isSelfEmployed;
	private boolean isssIncomeOrDisability;
	private boolean ispensionOrRetirement;
	private String selfEmployedIncome;
	private String ssDisabilityIncome;
	private String monthlyPension;
	private String experianScore;
	private String equifaxScore;
	private String transunionScore;
	private String currentHomePrice;
	private String currentHomeMortgageBalance;
	private String newHomeBudgetFromsale;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}
	public void setSpouseDateOfBirth(Long spouseDateOfBirth) {
		
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
}
