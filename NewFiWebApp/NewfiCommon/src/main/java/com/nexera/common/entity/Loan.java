package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the loan database table.
 * 
 */
@Entity
@NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private Boolean deleted;
	private String loanEmailId;
	private Integer lqbFileId;
	private Date modifiedDate;
	private String name;
	private User user;
	private LoanTypeMaster loanType;
	private LoanStatusMaster loanStatus;
	private PropertyTypeMaster propertyType;
	private LoanMilestoneMaster currentLoanMilestone;
	private List<LoanAppForm> loanAppForms;
	private List<LoanApplicationFee> loanApplicationFees;
	private List<LoanDetail> loanDetails;
	private List<LoanMilestone> loanMilestones;
	private List<LoanNeedsList> loanNeedsLists;
	private List<LoanNotification> loanNotifications;
	private List<LoanRate> loanRates;
	private List<LoanSetting> loanSettings;
	private List<LoanTeam> loanTeam;

	public Loan() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	@Column(name = "loan_email_id")
	public String getLoanEmailId() {
		return this.loanEmailId;
	}

	public void setLoanEmailId(String loanEmailId) {
		this.loanEmailId = loanEmailId;
	}

	@Column(name = "lqb_file_id")
	public Integer getLqbFileId() {
		return lqbFileId;
	}

	public void setLqbFileId(Integer lqbFileId) {
		this.lqbFileId = lqbFileId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_type")
	public LoanTypeMaster getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanTypeMaster loanType) {
		this.loanType = loanType;
	}

	// bi-directional many-to-one association to LoanStatusMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_status")
	public LoanStatusMaster getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatusMaster loanStatus) {
		this.loanStatus = loanStatus;
	}

	// bi-directional many-to-one association to PropertyTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_type")
	public PropertyTypeMaster getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyTypeMaster propertyType) {
		this.propertyType = propertyType;
	}

	// bi-directional many-to-one association to LoanMilestoneMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_milestone")
	public LoanMilestoneMaster getCurrentLoanMilestone() {
		return currentLoanMilestone;
	}

	public void setCurrentLoanMilestone(LoanMilestoneMaster currentLoanMilestone) {
		this.currentLoanMilestone = currentLoanMilestone;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "loan")
	public List<LoanAppForm> getLoanAppForms() {
		return this.loanAppForms;
	}

	public void setLoanappforms(List<LoanAppForm> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	public LoanAppForm addLoanappform(LoanAppForm loanAppForm) {
		getLoanAppForms().add(loanAppForm);
		loanAppForm.setLoan(this);

		return loanAppForm;
	}

	public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
		getLoanAppForms().remove(loanappform);
		loanappform.setLoan(null);

		return loanappform;
	}

	// bi-directional many-to-one association to LoanApplicationFee
	@OneToMany(mappedBy = "loan")
	public List<LoanApplicationFee> getLoanApplicationFees() {
		return this.loanApplicationFees;
	}

	public void setLoanapplicationfees(
			List<LoanApplicationFee> loanApplicationFees) {
		this.loanApplicationFees = loanApplicationFees;
	}

	public LoanApplicationFee addLoanApplicationFee(
			LoanApplicationFee loanApplicationFee) {
		getLoanApplicationFees().add(loanApplicationFee);
		loanApplicationFee.setLoan(this);

		return loanApplicationFee;
	}

	public LoanApplicationFee removeLoanApplicationfee(
			LoanApplicationFee loanApplicationFee) {
		getLoanApplicationFees().remove(loanApplicationFee);
		loanApplicationFee.setLoan(null);

		return loanApplicationFee;
	}

	// bi-directional many-to-one association to LoanDetail
	@OneToMany(mappedBy = "loan")
	public List<LoanDetail> getLoanDetails() {
		return this.loanDetails;
	}

	public void setLoanDetails(List<LoanDetail> loanDetails) {
		this.loanDetails = loanDetails;
	}

	public LoanDetail addLoandetail(LoanDetail loanDetail) {
		getLoanDetails().add(loanDetail);
		loanDetail.setLoanBean(this);

		return loanDetail;
	}

	public LoanDetail removeLoandetail(LoanDetail loanDetail) {
		getLoanDetails().remove(loanDetail);
		loanDetail.setLoanBean(null);

		return loanDetail;
	}

	// bi-directional many-to-one association to LoanMilestone
	@OneToMany(mappedBy = "loan")
	public List<LoanMilestone> getLoanMilestones() {
		return this.loanMilestones;
	}

	public void setLoanMilestones(List<LoanMilestone> loanMilestones) {
		this.loanMilestones = loanMilestones;
	}

	public LoanMilestone addLoanmilestone(LoanMilestone loanMilestone) {
		getLoanMilestones().add(loanMilestone);
		loanMilestone.setLoanBean(this);

		return loanMilestone;
	}

	public LoanMilestone removeLoanmilestone(LoanMilestone loanMilestone) {
		getLoanMilestones().remove(loanMilestone);
		loanMilestone.setLoanBean(null);

		return loanMilestone;
	}

	// bi-directional many-to-one association to LoanNeedsList
	@OneToMany(mappedBy = "loan")
	public List<LoanNeedsList> getLoanNeedsLists() {
		return this.loanNeedsLists;
	}

	public void setLoanNeedsLists(List<LoanNeedsList> loanNeedsLists) {
		this.loanNeedsLists = loanNeedsLists;
	}

	public LoanNeedsList addLoanNeedsList(LoanNeedsList loanNeedsList) {
		getLoanNeedsLists().add(loanNeedsList);
		loanNeedsList.setLoan(this);

		return loanNeedsList;
	}

	public LoanNeedsList removeLoanNeedsList(LoanNeedsList loanNeedsList) {
		getLoanNeedsLists().remove(loanNeedsList);
		loanNeedsList.setLoan(null);

		return loanNeedsList;
	}

	// bi-directional many-to-one association to LoanNotification
	@OneToMany(mappedBy = "loan")
	public List<LoanNotification> getLoanNotifications() {
		return this.loanNotifications;
	}

	public void setLoanNotifications(List<LoanNotification> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	public LoanNotification addLoanNotification(
			LoanNotification loanNotification) {
		getLoanNotifications().add(loanNotification);
		loanNotification.setLoanBean(this);

		return loanNotification;
	}

	public LoanNotification removeLoanNotification(
			LoanNotification loanNotification) {
		getLoanNotifications().remove(loanNotification);
		loanNotification.setLoanBean(null);

		return loanNotification;
	}

	// bi-directional many-to-one association to LoanRate
	@OneToMany(mappedBy = "loan")
	public List<LoanRate> getLoanRates() {
		return this.loanRates;
	}

	public void setLoanRates(List<LoanRate> loanRates) {
		this.loanRates = loanRates;
	}

	public LoanRate addLoanrate(LoanRate loanRate) {
		getLoanRates().add(loanRate);
		loanRate.setLoanBean(this);

		return loanRate;
	}

	public LoanRate removeLoanrate(LoanRate loanRate) {
		getLoanRates().remove(loanRate);
		loanRate.setLoanBean(null);

		return loanRate;
	}

	// bi-directional many-to-one association to LoanSetting
	@OneToMany(mappedBy = "loan")
	public List<LoanSetting> getLoanSettings() {
		return this.loanSettings;
	}

	public void setLoanSettings(List<LoanSetting> loanSettings) {
		this.loanSettings = loanSettings;
	}

	public LoanSetting addLoanSetting(LoanSetting loanSetting) {
		getLoanSettings().add(loanSetting);
		loanSetting.setLoanBean(this);

		return loanSetting;
	}

	public LoanSetting removeLoanSetting(LoanSetting loanSetting) {
		getLoanSettings().remove(loanSetting);
		loanSetting.setLoanBean(null);

		return loanSetting;
	}

	// bi-directional many-to-one association to LoanTeam
	@OneToMany(mappedBy = "loan")
	public List<LoanTeam> getLoanTeams() {
		return this.loanTeam;
	}

	public void setLoanTeams(List<LoanTeam> loanTeam) {
		this.loanTeam = loanTeam;
	}

	public LoanTeam addLoanTeam(LoanTeam loanTeam) {
		getLoanTeams().add(loanTeam);
		loanTeam.setLoanBean(this);

		return loanTeam;
	}

	public LoanTeam removeLoanTeam(LoanTeam loanTeam) {
		getLoanTeams().remove(loanTeam);
		loanTeam.setLoanBean(null);

		return loanTeam;
	}

}