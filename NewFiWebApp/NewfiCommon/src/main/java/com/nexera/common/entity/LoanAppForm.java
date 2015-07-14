package com.nexera.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
	private Boolean notApplicable;
	private Boolean receiveAlimonyChildSupport;
	private Boolean rentedOtherProperty;
	private Boolean secondMortgage;
	private Boolean paySecondMortgage;
	private Boolean isselfEmployed;
	private String selfEmployedIncome;
	private Boolean isssIncomeOrDisability;

	private BigDecimal monthlyIncome;
	private Integer selfEmployedNoYear;
	private BigDecimal childSupportAlimony;
	private BigDecimal socialSecurityIncome;
	private String ssDisabilityIncome;
	private String monthlyPension;
	private BigDecimal retirementIncome;
	private Boolean isCoborrowerPresent;
	private Boolean ssnProvided;
	private Boolean cbSsnProvided;

	private Boolean skipMyAssets;

	private Boolean isSpouseOnLoan;
	private String spouseName;
	private String monthlyRent;
	private User user;
	private PropertyTypeMaster propertyTypeMaster;
	private GovernmentQuestion governmentquestion;
	private CustomerSpouseDetail customerspousedetail;

	// private CustomerEmploymentIncome customerEmploymentIncome;
	private List<CustomerEmploymentIncome> customerEmploymentIncome;

	private List<CustomerBankAccountDetails> customerBankAccountDetails;
	private List<CustomerOtherAccountDetails> customerOtherAccountDetails;
	private List<CustomerRetirementAccountDetails> customerRetirementAccountDetails;
	private List<CustomerSpouseBankAccountDetails> customerSpouseBankAccountDetails;
	private List<CustomerSpouseEmploymentIncome> customerSpouseEmploymentIncome;
	private List<CustomerSpouseOtherAccountDetails> customerSpouseOtherAccountDetails;
	private List<CustomerSpouseRetirementAccountDetails> customerSpouseRetirementAccountDetails;

	private SpouseGovernmentQuestions spouseGovernmentQuestions;
	private RefinanceDetails refinancedetails;
	private LoanTypeMaster loanTypeMaster;
	private Loan loan;
	private List<UserEmploymentHistory> userEmploymentHistories;

	private Float loanAppFormCompletionStatus;

	private PurchaseDetails purchaseDetails;

	public LoanAppForm() {
	}

	@Column(name = "loan_app_completion_status")
	public Float getLoanAppFormCompletionStatus() {
		return loanAppFormCompletionStatus;
	}

	public void setLoanAppFormCompletionStatus(Float loanAppFormCompletionStatus) {
		this.loanAppFormCompletionStatus = loanAppFormCompletionStatus;
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

	@Column(name = "monthlyRent")
	public String getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(String monthlyRent) {
		this.monthlyRent = monthlyRent;
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

	@Column(name = "iscoborrower_present", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsCoborrowerPresent() {
		return isCoborrowerPresent;
	}

	public void setIsCoborrowerPresent(Boolean isCoborrowerPresent) {
		this.isCoborrowerPresent = isCoborrowerPresent;
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

	@Column(name = "ssn_provided")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSsnProvided() {
		return ssnProvided;
	}

	public void setSsnProvided(Boolean ssnProvided) {
		this.ssnProvided = ssnProvided;
	}

	@Column(name = "cb_ssn_provided")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getCbSsnProvided() {
		return cbSsnProvided;
	}

	public void setCbSsnProvided(Boolean cbSsnProvided) {
		this.cbSsnProvided = cbSsnProvided;
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

	// bi-directional many-to-one association to GovermentQuestions
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spousegov_quest")
	public SpouseGovernmentQuestions getSpouseGovernmentQuestions() {
		return spouseGovernmentQuestions;
	}

	public void setSpouseGovernmentQuestions(
	        SpouseGovernmentQuestions spouseGovernmentQuestions) {
		this.spouseGovernmentQuestions = spouseGovernmentQuestions;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ref_detail")
	public RefinanceDetails getRefinancedetails() {
		return refinancedetails;
	}

	public void setRefinancedetails(RefinanceDetails refinancedetails) {
		this.refinancedetails = refinancedetails;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.EAGER)
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchasedetails")
	public PurchaseDetails getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(PurchaseDetails purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerspousedetails")
	public CustomerSpouseDetail getCustomerspousedetail() {
		return customerspousedetail;
	}

	public void setCustomerspousedetail(
	        CustomerSpouseDetail customerspousedetail) {
		this.customerspousedetail = customerspousedetail;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerEmploymentIncome> getCustomerEmploymentIncome() {
		return customerEmploymentIncome;
	}

	public void setCustomerEmploymentIncome(
	        List<CustomerEmploymentIncome> customerEmploymentIncome) {
		this.customerEmploymentIncome = customerEmploymentIncome;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerBankAccountDetails> getCustomerBankAccountDetails() {
		return customerBankAccountDetails;
	}

	public void setCustomerBankAccountDetails(
	        List<CustomerBankAccountDetails> customerBankAccountDetails) {
		this.customerBankAccountDetails = customerBankAccountDetails;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerOtherAccountDetails> getCustomerOtherAccountDetails() {
		return customerOtherAccountDetails;
	}

	public void setCustomerOtherAccountDetails(
	        List<CustomerOtherAccountDetails> customerOtherAccountDetails) {
		this.customerOtherAccountDetails = customerOtherAccountDetails;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerRetirementAccountDetails> getCustomerRetirementAccountDetails() {
		return customerRetirementAccountDetails;
	}

	public void setCustomerRetirementAccountDetails(
	        List<CustomerRetirementAccountDetails> customerRetirementAccountDetails) {
		this.customerRetirementAccountDetails = customerRetirementAccountDetails;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerSpouseBankAccountDetails> getCustomerSpouseBankAccountDetails() {
		return customerSpouseBankAccountDetails;
	}

	public void setCustomerSpouseBankAccountDetails(
	        List<CustomerSpouseBankAccountDetails> customerSpouseBankAccountDetails) {
		this.customerSpouseBankAccountDetails = customerSpouseBankAccountDetails;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerSpouseEmploymentIncome> getCustomerSpouseEmploymentIncome() {
		return customerSpouseEmploymentIncome;
	}

	public void setCustomerSpouseEmploymentIncome(
	        List<CustomerSpouseEmploymentIncome> customerSpouseEmploymentIncome) {
		this.customerSpouseEmploymentIncome = customerSpouseEmploymentIncome;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerSpouseOtherAccountDetails> getCustomerSpouseOtherAccountDetails() {
		return customerSpouseOtherAccountDetails;
	}

	public void setCustomerSpouseOtherAccountDetails(
	        List<CustomerSpouseOtherAccountDetails> customerSpouseOtherAccountDetails) {
		this.customerSpouseOtherAccountDetails = customerSpouseOtherAccountDetails;
	}

	@OneToMany(mappedBy = "loanAppForms")
	public List<CustomerSpouseRetirementAccountDetails> getCustomerSpouseRetirementAccountDetails() {
		return customerSpouseRetirementAccountDetails;
	}

	public void setCustomerSpouseRetirementAccountDetails(
	        List<CustomerSpouseRetirementAccountDetails> customerSpouseRetirementAccountDetails) {
		this.customerSpouseRetirementAccountDetails = customerSpouseRetirementAccountDetails;
	}

	@Column(name = "monthly_income")
	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
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

	@Column(name = "notApplicable", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getNotApplicable() {
	    return notApplicable;
    }

	public void setNotApplicable(Boolean notApplicable) {
	    this.notApplicable = notApplicable;
    }

	/*
	 * @OneToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "cust_emp_income") public CustomerEmploymentIncome
	 * getCustomerEmploymentIncome() { return customerEmploymentIncome; }
	 * 
	 * public void setCustomerEmploymentIncome( CustomerEmploymentIncome
	 * customerEmploymentIncome) { this.customerEmploymentIncome =
	 * customerEmploymentIncome; }
	 */

}