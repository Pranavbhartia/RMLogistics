package com.nexera.common.entity;

import java.io.Serializable;
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

import org.hibernate.annotations.Type;

@Entity
@Table(name = "spousegovernmentquestion")
@NamedQuery(name = "SpouseGovernmentQuestions.findAll", query = "SELECT sg FROM SpouseGovernmentQuestions sg")
public class SpouseGovernmentQuestions implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
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
	private List<LoanAppForm> loanAppForms;
	
	public SpouseGovernmentQuestions(){
		
	}
	
	@Column(name = "isOutstandingJudgments")
	@Type(type = "org.hibernate.type.NumericBooleanType")public boolean isOutstandingJudgments() {
		return isOutstandingJudgments;
	}
	public void setOutstandingJudgments(boolean isOutstandingJudgments) {
		this.isOutstandingJudgments = isOutstandingJudgments;
	}
	
	@Column(name = "isBankrupt")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isBankrupt() {
		return isBankrupt;
	}
	public void setBankrupt(boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}
	
	@Column(name = "isPropertyForeclosed")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isPropertyForeclosed() {
		return isPropertyForeclosed;
	}
	public void setPropertyForeclosed(boolean isPropertyForeclosed) {
		this.isPropertyForeclosed = isPropertyForeclosed;
	}
	
	@Column(name = "isLawsuit")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isLawsuit() {
		return isLawsuit;
	}
	public void setLawsuit(boolean isLawsuit) {
		this.isLawsuit = isLawsuit;
	}
	
	@Column(name = "isObligatedLoan")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isObligatedLoan() {
		return isObligatedLoan;
	}
	public void setObligatedLoan(boolean isObligatedLoan) {
		this.isObligatedLoan = isObligatedLoan;
	}
	
	@Column(name = "isFederalDebt")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isFederalDebt() {
		return isFederalDebt;
	}
	public void setFederalDebt(boolean isFederalDebt) {
		this.isFederalDebt = isFederalDebt;
	}
	
	
	@Column(name = "isObligatedToPayAlimony")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isObligatedToPayAlimony() {
		return isObligatedToPayAlimony;
	}
	public void setObligatedToPayAlimony(boolean isObligatedToPayAlimony) {
		this.isObligatedToPayAlimony = isObligatedToPayAlimony;
	}
	
	
	@Column(name = "isEndorser")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isEndorser() {
		return isEndorser;
	}
	public void setEndorser(boolean isEndorser) {
		this.isEndorser = isEndorser;
	}
	
	
	@Column(name = "isUSCitizen")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isUSCitizen() {
		return isUSCitizen;
	}
	
	public void setUSCitizen(boolean isUSCitizen) {
		this.isUSCitizen = isUSCitizen;
	}
	
	
	@Column(name = "isOccupyPrimaryResidence")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isOccupyPrimaryResidence() {
		return isOccupyPrimaryResidence;
	}
	public void setOccupyPrimaryResidence(boolean isOccupyPrimaryResidence) {
		this.isOccupyPrimaryResidence = isOccupyPrimaryResidence;
	}
	
	
	@Column(name = "isOwnershipInterestInProperty")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isOwnershipInterestInProperty() {
		return isOwnershipInterestInProperty;
	}
	public void setOwnershipInterestInProperty(boolean isOwnershipInterestInProperty) {
		this.isOwnershipInterestInProperty = isOwnershipInterestInProperty;
	}
	
	@Column(name = "ethnicity")
	public String getEthnicity() {
		return ethnicity;
	}
	
	
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	@Column(name = "race")
	public String getRace() {
		return race;
	}
	
	
	public void setRace(String race) {
		this.race = race;
	}
	
	@Column(name = "sex")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
	// bi-directional many-to-one association to LoanAppForm
		@OneToMany(mappedBy = "spouseGovernmentQuestions",cascade = CascadeType.ALL)
		public List<LoanAppForm> getLoanappforms() {
			return this.loanAppForms;
		}

		public void setLoanappforms(List<LoanAppForm> loanappforms) {
			this.loanAppForms = loanappforms;
		}

		public LoanAppForm addLoanappform(LoanAppForm loanappform) {
			getLoanappforms().add(loanappform);
			loanappform.setSpouseGovernmentQuestions(this);

			return loanappform;
		}

		public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
			getLoanappforms().remove(loanappform);
			loanappform.setGovernmentquestion(null);

			return loanappform;
		}
	
	
}
