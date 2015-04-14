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
@Table(name = "governmentquestion")
@NamedQuery(name = "GovernmentQuestion.findAll", query = "SELECT g FROM GovernmentQuestion g")
public class GovernmentQuestion implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean isOutstandingJudgments;
	private Boolean isBankrupt;
	private Boolean isPropertyForeclosed;
	private Boolean isLawsuit;
	private Boolean isObligatedLoan;
	private Boolean isFederalDebt;
	private Boolean isObligatedToPayAlimony;
	private Boolean isEndorser;
	private Boolean isUSCitizen;
	private Boolean isOccupyPrimaryResidence;
	private Boolean isOwnershipInterestInProperty;
	private String ethnicity;
	private String race;
	private String sex;
	private List<LoanAppForm> loanAppForms;
	
	public GovernmentQuestion(){
		
	}
	
	@Column(name = "isOutstandingJudgments")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isOutstandingJudgments() {
		return isOutstandingJudgments;
	}

	public void setOutstandingJudgments(Boolean isOutstandingJudgments) {
		this.isOutstandingJudgments = isOutstandingJudgments;
	}
	
	@Column(name = "isBankrupt")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isBankrupt() {
		return isBankrupt;
	}

	public void setBankrupt(Boolean isBankrupt) {
		this.isBankrupt = isBankrupt;
	}
	
	@Column(name = "isPropertyForeclosed")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isPropertyForeclosed() {
		return isPropertyForeclosed;
	}

	public void setPropertyForeclosed(Boolean isPropertyForeclosed) {
		this.isPropertyForeclosed = isPropertyForeclosed;
	}
	
	@Column(name = "isLawsuit")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isLawsuit() {
		return isLawsuit;
	}

	public void setLawsuit(Boolean isLawsuit) {
		this.isLawsuit = isLawsuit;
	}
	
	@Column(name = "isObligatedLoan")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isObligatedLoan() {
		return isObligatedLoan;
	}

	public void setObligatedLoan(Boolean isObligatedLoan) {
		this.isObligatedLoan = isObligatedLoan;
	}
	
	@Column(name = "isFederalDebt")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isFederalDebt() {
		return isFederalDebt;
	}

	public void setFederalDebt(Boolean isFederalDebt) {
		this.isFederalDebt = isFederalDebt;
	}
	
	
	@Column(name = "isObligatedToPayAlimony")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isObligatedToPayAlimony() {
		return isObligatedToPayAlimony;
	}

	public void setObligatedToPayAlimony(Boolean isObligatedToPayAlimony) {
		this.isObligatedToPayAlimony = isObligatedToPayAlimony;
	}
	
	
	@Column(name = "isEndorser")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isEndorser() {
		return isEndorser;
	}

	public void setEndorser(Boolean isEndorser) {
		this.isEndorser = isEndorser;
	}
	
	
	@Column(name = "isUSCitizen")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isUSCitizen() {
		return isUSCitizen;
	}
	
	public void setUSCitizen(Boolean isUSCitizen) {
		this.isUSCitizen = isUSCitizen;
	}
	
	
	@Column(name = "isOccupyPrimaryResidence")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isOccupyPrimaryResidence() {
		return isOccupyPrimaryResidence;
	}

	public void setOccupyPrimaryResidence(Boolean isOccupyPrimaryResidence) {
		this.isOccupyPrimaryResidence = isOccupyPrimaryResidence;
	}
	
	
	@Column(name = "isOwnershipInterestInProperty")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean isOwnershipInterestInProperty() {
		return isOwnershipInterestInProperty;
	}

	public void setOwnershipInterestInProperty(
	        Boolean isOwnershipInterestInProperty) {
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
		@OneToMany(mappedBy = "governmentquestion",cascade = CascadeType.ALL)
		public List<LoanAppForm> getLoanappforms() {
			return this.loanAppForms;
		}

		public void setLoanappforms(List<LoanAppForm> loanappforms) {
			this.loanAppForms = loanappforms;
		}

		public LoanAppForm addLoanappform(LoanAppForm loanappform) {
			getLoanappforms().add(loanappform);
			loanappform.setGovernmentquestion(this);

			return loanappform;
		}

		public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
			getLoanappforms().remove(loanappform);
			loanappform.setGovernmentquestion(null);

			return loanappform;
		}
	
	
}
