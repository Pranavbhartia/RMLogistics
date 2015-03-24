package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.List;

/**
 * The persistent class for the loanappform database table.
 * 
 */
@Entity
@Table(name = "loanappform")
@NamedQuery(name = "LoanAppForm.findAll", query = "SELECT l FROM LoanAppForm l")
public class LoanAppForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean employed;
	private Boolean hoaDues;
	private Boolean homeRecentlySold;
	private Boolean homeToSell;
	private String maritalStatus;
	private Boolean ownsOtherProperty;
	private Boolean pensionOrRetirement;
	private Boolean receiveAlimonyChildSupport;
	private Boolean rentedOtherProperty;
	private Boolean secondMortgage;
	private Boolean paySecondMortgage;
	private Boolean selfEmployed;
	private Boolean ssIncomeOrDisability;
	private User user;
	private PropertyTypeMaster propertyTypeMaster;
	private LoanTypeMaster loanTypeMaster;
	private Loan loan;
	private List<UserEmploymentHistory> userEmploymentHistories;
	private WorkflowExec customerWorkflow;
	private WorkflowExec loanManagerWorkflow;
	private int loanAppFormCompletionStatus;

	@Column(name = "loan_app_completion_status")
	public int getLoanAppFormCompletionStatus() {
		return loanAppFormCompletionStatus;
	}

	public void setLoanAppFormCompletionStatus(int loanAppFormCompletionStatus) {
		this.loanAppFormCompletionStatus = loanAppFormCompletionStatus;
	}

	public LoanAppForm() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getEmployed() {
		return this.employed;
	}
	

	public void setEmployed(Boolean employed) {
		this.employed = employed;
	}

	@Column(name = "hoa_dues", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getHoaDues() {
		return this.hoaDues;
	}

	public void setHoaDues(Boolean hoaDues) {
		this.hoaDues = hoaDues;
	}

	@Column(name = "home_recently_sold", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getHomeRecentlySold() {
		return this.homeRecentlySold;
	}

	public void setHomeRecentlySold(Boolean homeRecentlySold) {
		this.homeRecentlySold = homeRecentlySold;
	}

	@Column(name = "home_to_sell", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getHomeToSell() {
		return this.homeToSell;
	}

	public void setHomeToSell(Boolean homeToSell) {
		this.homeToSell = homeToSell;
	}

	@Column(name = "marital_status")
	public String getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@Column(name = "owns_other_property", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getOwnsOtherProperty() {
		return this.ownsOtherProperty;
	}

	public void setOwnsOtherProperty(Boolean ownsOtherProperty) {
		this.ownsOtherProperty = ownsOtherProperty;
	}

	@Column(name = "pension_or_retirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getPensionOrRetirement() {
		return this.pensionOrRetirement;
	}

	public void setPensionOrRetirement(Boolean pensionOrRetirement) {
		this.pensionOrRetirement = pensionOrRetirement;
	}

	@Column(name = "receive_alimony_child_support", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getReceiveAlimonyChildSupport() {
		return this.receiveAlimonyChildSupport;
	}

	public void setReceiveAlimonyChildSupport(Boolean receiveAlimonyChildSupport) {
		this.receiveAlimonyChildSupport = receiveAlimonyChildSupport;
	}

	@Column(name = "rented_other_property", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getRentedOtherProperty() {
		return this.rentedOtherProperty;
	}

	public void setRentedOtherProperty(Boolean rentedOtherProperty) {
		this.rentedOtherProperty = rentedOtherProperty;
	}

	@Column(name = "second_mortgage", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSecondMortgage() {
		return this.secondMortgage;
	}

	public void setSecondMortgage(Boolean secondMortgage) {
		this.secondMortgage = secondMortgage;
	}

	@Column(name = "pay_sec_mortgage", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getPaySecondMortgage() {
		return paySecondMortgage;
	}

	public void setPaySecondMortgage(Boolean paySecondMortgage) {
		this.paySecondMortgage = paySecondMortgage;
	}

	@Column(name = "self_employed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSelfEmployed() {
		return this.selfEmployed;
	}

	public void setSelfEmployed(Boolean selfEmployed) {
		this.selfEmployed = selfEmployed;
	}

	@Column(name = "ss_income_or_disability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSsIncomeOrDisability() {
		return this.ssIncomeOrDisability;
	}

	public void setSsIncomeOrDisability(Boolean ssIncomeOrDisability) {
		this.ssIncomeOrDisability = ssIncomeOrDisability;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// bi-directional many-to-one association to PropertyTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_type")
	public PropertyTypeMaster getPropertyTypeMaster() {
		return propertyTypeMaster;
	}

	public void setPropertyTypeMaster(PropertyTypeMaster propertyTypeMaster) {
		this.propertyTypeMaster = propertyTypeMaster;
	}

	// bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_type")
	public LoanTypeMaster getLoanTypeMaster() {
		return loanTypeMaster;
	}

	public void setLoanTypeMaster(LoanTypeMaster loanTypeMaster) {
		this.loanTypeMaster = loanTypeMaster;
	}

	// bi-directional many-to-one association to Loan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan")
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	// bi-directional many-to-one association to UserEmploymentHistory
	@OneToMany(mappedBy = "loanAppForm")
	public List<UserEmploymentHistory> getUserEmploymentHistories() {
		return this.userEmploymentHistories;
	}

	public void setUserEmploymentHistories(
			List<UserEmploymentHistory> userEmploymentHistories) {
		this.userEmploymentHistories = userEmploymentHistories;
	}

	public UserEmploymentHistory addUserEmploymentHistory(
			UserEmploymentHistory userEmploymentHistory) {
		getUserEmploymentHistories().add(userEmploymentHistory);
		userEmploymentHistory.setLoanAppForm(this);

		return userEmploymentHistory;
	}

	public UserEmploymentHistory removeUserEmploymentHistory(
			UserEmploymentHistory userEmploymentHistory) {
		getUserEmploymentHistories().remove(userEmploymentHistory);
		userEmploymentHistory.setLoanAppForm(null);

		return userEmploymentHistory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_workflow")
	public WorkflowExec getCustomerWorkflow() {
		return customerWorkflow;
	}

	public void setCustomerWorkflow(WorkflowExec customerWorkflow) {
		this.customerWorkflow = customerWorkflow;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_manager_workflow")
	public WorkflowExec getLoanManagerWorkflow() {
		return loanManagerWorkflow;
	}

	public void setLoanManagerWorkflow(WorkflowExec loanManagerWorkflow) {
		this.loanManagerWorkflow = loanManagerWorkflow;
	}

}