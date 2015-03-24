package com.nexera.common.vo;

import com.nexera.common.entity.GovernmentQuestion;

public class GovernmentQuestionVO {

	
	private boolean isOutstandingJudgments;
	private boolean isBankrupt;
	private boolean isPropertyForeclosed;
	private boolean isLawsuit;
	private boolean isObligatedLoan;
	private boolean isFederalDebt;
	private boolean isObligatedToPayAlimony;
	private boolean isEndorser;
	private boolean isUSCitizen;
	private boolean isOccupyPrimaryResidence;
	private boolean isOwnershipInterestInProperty;
	private String ethnicity;
	private String race;
	private String sex;
	
	public boolean isOutstandingJudgments() {
		return isOutstandingJudgments;
	}
	public void setOutstandingJudgments(boolean isOutstandingJudgments) {
		this.isOutstandingJudgments = isOutstandingJudgments;
	}
	public boolean isBankrupt() {
		return isBankrupt;
	}
	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}
	public boolean isPropertyForeclosed() {
		return isPropertyForeclosed;
	}
	public void setPropertyForeclosed(boolean isPropertyForeclosed) {
		this.isPropertyForeclosed = isPropertyForeclosed;
	}
	public boolean isLawsuit() {
		return isLawsuit;
	}
	public void setLawsuit(boolean isLawsuit) {
		this.isLawsuit = isLawsuit;
	}
	public boolean isObligatedLoan() {
		return isObligatedLoan;
	}
	public void setObligatedLoan(boolean isObligatedLoan) {
		this.isObligatedLoan = isObligatedLoan;
	}
	public boolean isFederalDebt() {
		return isFederalDebt;
	}
	public void setFederalDebt(boolean isFederalDebt) {
		this.isFederalDebt = isFederalDebt;
	}
	public boolean isObligatedToPayAlimony() {
		return isObligatedToPayAlimony;
	}
	public void setObligatedToPayAlimony(boolean isObligatedToPayAlimony) {
		this.isObligatedToPayAlimony = isObligatedToPayAlimony;
	}
	public boolean isEndorser() {
		return isEndorser;
	}
	public void setEndorser(boolean isEndorser) {
		this.isEndorser = isEndorser;
	}
	public boolean isUSCitizen() {
		return isUSCitizen;
	}
	public void setUSCitizen(boolean isUSCitizen) {
		this.isUSCitizen = isUSCitizen;
	}
	public boolean isOccupyPrimaryResidence() {
		return isOccupyPrimaryResidence;
	}
	public void setOccupyPrimaryResidence(boolean isOccupyPrimaryResidence) {
		this.isOccupyPrimaryResidence = isOccupyPrimaryResidence;
	}
	public boolean isOwnershipInterestInProperty() {
		return isOwnershipInterestInProperty;
	}
	public void setOwnershipInterestInProperty(boolean isOwnershipInterestInProperty) {
		this.isOwnershipInterestInProperty = isOwnershipInterestInProperty;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public GovernmentQuestion convertToEntity() {
		GovernmentQuestion governmentquestion = new GovernmentQuestion();

		

		return governmentquestion;

	}
	
	
	
}
