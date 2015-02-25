package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the loanappform database table.
 * 
 */
@Entity
@NamedQuery(name="LoanAppForm.findAll", query="SELECT l FROM LoanAppForm l")
public class LoanAppForm implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean employed;
	private Boolean hoaDues;
	private Boolean homeRecentlySold;
	private Boolean homeToSell;
	private Boolean maritalStatus;
	private Boolean ownsOtherProperty;
	private Boolean pensionOrRetirement;
	private Boolean receiveAlimonyChildSupport;
	private Boolean rentedOtherProperty;
	private Boolean secondMortgage;
	private Boolean selfEmployed;
	private Boolean ssIncomeOrDisability;
	private User userBean;
	private PropertyTypeMaster propertytypemaster;
	private LoanTypeMaster loantypemaster;
	private Loan loanBean;
	private List<UserEmploymentHistory> userEmploymentHistories;

	public LoanAppForm() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Boolean getEmployed() {
		return this.employed;
	}

	public void setEmployed(Boolean employed) {
		this.employed = employed;
	}


	@Column(name="hoa_dues")
	public Boolean getHoaDues() {
		return this.hoaDues;
	}

	public void setHoaDues(Boolean hoaDues) {
		this.hoaDues = hoaDues;
	}


	@Column(name="home_recently_sold")
	public Boolean getHomeRecentlySold() {
		return this.homeRecentlySold;
	}

	public void setHomeRecentlySold(Boolean homeRecentlySold) {
		this.homeRecentlySold = homeRecentlySold;
	}


	@Column(name="home_to_sell")
	public Boolean getHomeToSell() {
		return this.homeToSell;
	}

	public void setHomeToSell(Boolean homeToSell) {
		this.homeToSell = homeToSell;
	}


	@Column(name="marital_status")
	public Boolean getMaritalStatus() {
		return this.maritalStatus;
	}

	public void setMaritalStatus(Boolean maritalStatus) {
		this.maritalStatus = maritalStatus;
	}


	@Column(name="owns_other_property")
	public Boolean getOwnsOtherProperty() {
		return this.ownsOtherProperty;
	}

	public void setOwnsOtherProperty(Boolean ownsOtherProperty) {
		this.ownsOtherProperty = ownsOtherProperty;
	}


	@Column(name="pension_or_retirement")
	public Boolean getPensionOrRetirement() {
		return this.pensionOrRetirement;
	}

	public void setPensionOrRetirement(Boolean pensionOrRetirement) {
		this.pensionOrRetirement = pensionOrRetirement;
	}


	@Column(name="receive_alimony_child_support")
	public Boolean getReceiveAlimonyChildSupport() {
		return this.receiveAlimonyChildSupport;
	}

	public void setReceiveAlimonyChildSupport(Boolean receiveAlimonyChildSupport) {
		this.receiveAlimonyChildSupport = receiveAlimonyChildSupport;
	}


	@Column(name="rented_other_property")
	public Boolean getRentedOtherProperty() {
		return this.rentedOtherProperty;
	}

	public void setRentedOtherProperty(Boolean rentedOtherProperty) {
		this.rentedOtherProperty = rentedOtherProperty;
	}


	@Column(name="second_mortgage")
	public Boolean getSecondMortgage() {
		return this.secondMortgage;
	}

	public void setSecondMortgage(Boolean secondMortgage) {
		this.secondMortgage = secondMortgage;
	}


	@Column(name="self_employed")
	public Boolean getSelfEmployed() {
		return this.selfEmployed;
	}

	public void setSelfEmployed(Boolean selfEmployed) {
		this.selfEmployed = selfEmployed;
	}


	@Column(name="ss_income_or_disability")
	public Boolean getSsIncomeOrDisability() {
		return this.ssIncomeOrDisability;
	}

	public void setSsIncomeOrDisability(Boolean ssIncomeOrDisability) {
		this.ssIncomeOrDisability = ssIncomeOrDisability;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}


	//bi-directional many-to-one association to PropertyTypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="property_type")
	public PropertyTypeMaster getPropertytypemaster() {
		return this.propertytypemaster;
	}

	public void setPropertytypemaster(PropertyTypeMaster propertytypemaster) {
		this.propertytypemaster = propertytypemaster;
	}


	//bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan_type")
	public LoanTypeMaster getLoantypemaster() {
		return this.loantypemaster;
	}

	public void setLoantypemaster(LoanTypeMaster loantypemaster) {
		this.loantypemaster = loantypemaster;
	}


	//bi-directional many-to-one association to Loan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan")
	public Loan getLoanBean() {
		return this.loanBean;
	}

	public void setLoanBean(Loan loanBean) {
		this.loanBean = loanBean;
	}


	//bi-directional many-to-one association to UserEmploymentHistory
	@OneToMany(mappedBy="loanappform")
	public List<UserEmploymentHistory> getUseremploymenthistories() {
		return this.userEmploymentHistories;
	}

	public void setUseremploymenthistories(List<UserEmploymentHistory> useremploymenthistories) {
		this.userEmploymentHistories = useremploymenthistories;
	}

	public UserEmploymentHistory addUseremploymenthistory(UserEmploymentHistory useremploymenthistory) {
		getUseremploymenthistories().add(useremploymenthistory);
		useremploymenthistory.setLoanappform(this);

		return useremploymenthistory;
	}

	public UserEmploymentHistory removeUseremploymenthistory(UserEmploymentHistory useremploymenthistory) {
		getUseremploymenthistories().remove(useremploymenthistory);
		useremploymenthistory.setLoanappform(null);

		return useremploymenthistory;
	}

}