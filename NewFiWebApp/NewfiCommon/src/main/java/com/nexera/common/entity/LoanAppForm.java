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
	private Boolean isEmployed;
	private String EmployedIncomePreTax;
	private String EmployedAt;
	private String EmployedSince;
	private Boolean hoaDues;
	private Boolean homeRecentlySold;
	private Boolean homeToSell;
	private String maritalStatus;
	private Boolean ownsOtherProperty;
	private Boolean ispensionOrRetirement;
	private String monthlyPension;
	private Boolean receiveAlimonyChildSupport;
	private Boolean rentedOtherProperty;
	private Boolean secondMortgage;
	private Boolean paySecondMortgage;
	private Boolean isselfEmployed;
	private String  selfEmployedIncome;
	private Boolean isssIncomeOrDisability;
	private String  ssDisabilityIncome;
	private Boolean isSpouseOnLoan ;
	private String	spouseName;
	private User user;
	private PropertyTypeMaster propertyTypeMaster;
	private GovernmentQuestion governmentquestion;
	private RefinanceDetails refinancedetails;
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

	@Column(name = "receive_alimony_child_support", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getReceiveAlimonyChildSupport() {
		return receiveAlimonyChildSupport;
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

	@Column(name = "isspouseOnLoan")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsSpouseOnLoan() {
		return isSpouseOnLoan;
	}

	public void setIsSpouseOnLoan(Boolean isSpouseOnLoan) {
		this.isSpouseOnLoan = isSpouseOnLoan;
	}

	@Column(name = "spouse_name")
	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	
	
	

	// bi-directional many-to-one association to GovermentQuestions
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gov_quest")
	public GovernmentQuestion getGovernmentquestion() {
		return governmentquestion;
	}

	public void setGovernmentquestion(GovernmentQuestion governmentquestion) {
		this.governmentquestion = governmentquestion;
	}

	
	// bi-directional many-to-one association to Refinace

		@OneToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "ref_detail")
	public RefinanceDetails getRefinancedetails() {
		return refinancedetails;
	}

	public void setRefinancedetails(RefinanceDetails refinancedetails) {
		this.refinancedetails = refinancedetails;
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

	@Column(name = "isemployed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsEmployed() {
		return isEmployed;
	}

	public void setIsEmployed(Boolean isEmployed) {
		this.isEmployed = isEmployed;
	}

	@Column(name = "EmployedIncomePreTax")
	public String getEmployedIncomePreTax() {
		return EmployedIncomePreTax;
	}

	public void setEmployedIncomePreTax(String employedIncomePreTax) {
		EmployedIncomePreTax = employedIncomePreTax;
	}

	@Column(name = "EmployedAt")
	public String getEmployedAt() {
		return EmployedAt;
	}

	public void setEmployedAt(String employedAt) {
		EmployedAt = employedAt;
	}

	@Column(name = "EmployedSince")
	public String getEmployedSince() {
		return EmployedSince;
	}

	public void setEmployedSince(String employedSince) {
		EmployedSince = employedSince;
	}

	@Column(name = "ispensionOrRetirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIspensionOrRetirement() {
		return ispensionOrRetirement;
	}

	public void setIspensionOrRetirement(Boolean ispensionOrRetirement) {
		this.ispensionOrRetirement = ispensionOrRetirement;
	}

	@Column(name = "monthlyPension")
	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	@Column(name = "isselfEmployed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsselfEmployed() {
		return isselfEmployed;
	}

	public void setIsselfEmployed(Boolean isselfEmployed) {
		this.isselfEmployed = isselfEmployed;
	}

	@Column(name = "selfEmployedIncome")
	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	@Column(name = "isssIncomeOrDisability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(Boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	@Column(name = "ssDisabilityIncome")
	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
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