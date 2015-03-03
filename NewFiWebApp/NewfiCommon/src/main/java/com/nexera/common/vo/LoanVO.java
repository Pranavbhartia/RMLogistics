package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nexera.common.entity.Loan;

public class LoanVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private Boolean deleted;
	private String loanEmailId;
	private Integer lqbFileId;
	private Date modifiedDate;
	private String name;
	private String status;
	private UserVO user;
	private LoanTypeMasterVO loanType;
	private LoanStatusMasterVO loanStatus;
	private PropertyTypeMasterVO propertyType;
	private LoanMilestoneMasterVO currentLoanMilestone;
	private List<LoanAppFormVO> loanAppForms;
	private List<LoanApplicationFeeVO> loanApplicationFees;
	private LoanDetailVO loanDetail;
	private List<LoanMilestoneVO> loanMilestones;
	private List<LoanNeedsListVO> loanNeedsLists;
	private List<LoanNotificationVO> loanNotifications;
	private List<LoanRateVO> loanRates;
	private List<LoanSettingVO> loanSettings;
	private List<UserVO> loanTeam;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getLoanEmailId() {
		return loanEmailId;
	}

	public void setLoanEmailId(String loanEmailId) {
		this.loanEmailId = loanEmailId;
	}

	public Integer getLqbFileId() {
		return lqbFileId;
	}

	public void setLqbFileId(Integer lqbFileId) {
		this.lqbFileId = lqbFileId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public LoanTypeMasterVO getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanTypeMasterVO loanType) {
		this.loanType = loanType;
	}

	public LoanStatusMasterVO getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatusMasterVO loanStatus) {
		this.loanStatus = loanStatus;
	}

	public PropertyTypeMasterVO getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyTypeMasterVO propertyType) {
		this.propertyType = propertyType;
	}

	public LoanMilestoneMasterVO getCurrentLoanMilestone() {
		return currentLoanMilestone;
	}

	public void setCurrentLoanMilestone(
			LoanMilestoneMasterVO currentLoanMilestone) {
		this.currentLoanMilestone = currentLoanMilestone;
	}

	public List<LoanAppFormVO> getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(List<LoanAppFormVO> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	public List<LoanApplicationFeeVO> getLoanApplicationFees() {
		return loanApplicationFees;
	}

	public void setLoanApplicationFees(
			List<LoanApplicationFeeVO> loanApplicationFees) {
		this.loanApplicationFees = loanApplicationFees;
	}

	public LoanDetailVO getLoanDetail() {
		return loanDetail;
	}

	public void setLoanDetail(LoanDetailVO loanDetail) {
		this.loanDetail = loanDetail;
	}

	public List<LoanMilestoneVO> getLoanMilestones() {
		return loanMilestones;
	}

	public void setLoanMilestones(List<LoanMilestoneVO> loanMilestones) {
		this.loanMilestones = loanMilestones;
	}

	public List<LoanNeedsListVO> getLoanNeedsLists() {
		return loanNeedsLists;
	}

	public void setLoanNeedsLists(List<LoanNeedsListVO> loanNeedsLists) {
		this.loanNeedsLists = loanNeedsLists;
	}

	public List<LoanNotificationVO> getLoanNotifications() {
		return loanNotifications;
	}

	public void setLoanNotifications(List<LoanNotificationVO> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	public List<LoanRateVO> getLoanRates() {
		return loanRates;
	}

	public void setLoanRates(List<LoanRateVO> loanRates) {
		this.loanRates = loanRates;
	}

	public List<LoanSettingVO> getLoanSettings() {
		return loanSettings;
	}

	public void setLoanSettings(List<LoanSettingVO> loanSettings) {
		this.loanSettings = loanSettings;
	}

	public List<UserVO> getLoanTeam() {
		return loanTeam;
	}

	public void setLoanTeam(List<UserVO> loanTeam) {
		this.loanTeam = loanTeam;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Loan convertToEntity() {

		Loan loan = new Loan();
		loan.setId(this.getId());
		return loan;

	}

}