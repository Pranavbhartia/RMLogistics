package com.nexera.common.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.enums.LoanTypeMasterEnum;

public class LoanVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private Boolean deleted;
	private String loanEmailId;
	private String lqbFileId;
	private Date modifiedDate;
	private String name;
	private String status;
	private UserVO user;
	private LoanTypeMasterVO loanType;
	private String lmEmail;
	private String realtorEmail;
	private String creditReportUrl;
	private Boolean lqbInformationAvailable;

	private PropertyTypeMasterVO propertyType;
	private LoanMilestoneMasterVO currentLoanMilestone;
	private List<LoanAppFormVO> loanAppForms;
	private List<LoanApplicationFeeVO> loanApplicationFees;
	private LoanDetailVO loanDetail;
	private List<LoanMilestoneVO> loanMilestones;
	private List<LoanNeedsListVO> loanNeedsLists;
	private List<NotificationVO> loanNotifications;
	private List<LoanRateVO> loanRates;
	private List<LoanSettingVO> loanSettings;
	private List<UserVO> loanTeam;
	private ExtendedLoanTeamVO extendedLoanTeam;

	private String lockStatus;
	private Boolean isBankConnected;
	private String setSenderDomain;
	private String lockedRate;
	private BigDecimal appFee;
	private String lqbLoanStatus;

	private int customerWorkflowID;
	private int loanManagerWorkflowID;

	// Added to attach additional fields for Loan to display in Loan dashboard
	// UI related to credit score etc.

	private UserLoanStatus userLoanStatus;

	private Long purchaseDocumentExpiryDate;
	private String lqbUrl;
	private String lockedRateData;
	private Date lockExpirationDate;
	private Boolean rateLockRequested;

	private String userZipCode;

	public int getCustomerWorkflowID() {
		return customerWorkflowID;
	}

	public void setCustomerWorkflowID(int customerWorkflowID) {
		this.customerWorkflowID = customerWorkflowID;
	}

	public int getLoanManagerWorkflowID() {
		return loanManagerWorkflowID;
	}

	public void setLoanManagerWorkflowID(int loanManagerWorkflowID) {
		this.loanManagerWorkflowID = loanManagerWorkflowID;
	}

	public LoanVO(int loanId) {
		this.id = loanId;
	}

	public UserLoanStatus getUserLoanStatus() {
		return userLoanStatus;
	}

	public void setUserLoanStatus(UserLoanStatus userLoanStatus) {
		this.userLoanStatus = userLoanStatus;
	}

	public LoanVO() {
		// TODO Auto-generated constructor stub
	}

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

	public String getLqbFileId() {
		return lqbFileId;
	}

	public void setLqbFileId(String lqbFileId) {
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

	public String getLmEmail() {
		return lmEmail;
	}

	public void setLmEmail(String lmEmail) {
		this.lmEmail = lmEmail;
	}

	public String getRealtorEmail() {
		return realtorEmail;
	}

	public void setRealtorEmail(String realtorEmail) {
		this.realtorEmail = realtorEmail;
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

	public List<NotificationVO> getLoanNotifications() {
		return loanNotifications;
	}

	public void setLoanNotifications(List<NotificationVO> loanNotifications) {
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
		if (null != this.getLoanType()) {

			loan.setLoanType(this.getLoanType().convertToEntity());

		} else {

			loan.setLoanType(new LoanTypeMaster(LoanTypeMasterEnum.NONE));
		}

		/*
		 * System.out.println("this.getLqbFileId()"+this.getLqbFileId());
		 * loan.setLqbFileId(this.getLqbFileId());
		 * 
		 * 
		 * 
		 * if(this.getLoanType() !=null ){ if
		 * (this.getLoanType().getLoanTypeCd() .equalsIgnoreCase("REF")) {
		 * System.out.println("loan type is REF"); loan.setLoanType(new
		 * LoanTypeMaster(LoanTypeMasterEnum.REF)); } else {
		 * System.out.println("loan type is PUR"); loan.setLoanType(new
		 * LoanTypeMaster(LoanTypeMasterEnum.PUR));
		 * 
		 * } }else{ System.out.println("loan type is NONE");
		 * loan.setLoanType(new LoanTypeMaster(LoanTypeMasterEnum.NONE)); }
		 * 
		 * 
		 * 
		 * 
		 * loan.setUser(User.convertFromVOToEntity(this.getUser()));
		 */

		return loan;

	}

	public String getSetSenderDomain() {
		return setSenderDomain;
	}

	public void setSetSenderDomain(String setSenderDomain) {
		this.setSenderDomain = setSenderDomain;
	}

	public String getLockedRate() {
		return lockedRate;
	}

	public void setLockedRate(String lockedRate) {
		this.lockedRate = lockedRate;
	}

	public Boolean getIsBankConnected() {
		return isBankConnected;
	}

	public void setIsBankConnected(Boolean isBankConnected) {
		this.isBankConnected = isBankConnected;
	}

	public ExtendedLoanTeamVO getExtendedLoanTeam() {
		return extendedLoanTeam;
	}

	public void setExtendedLoanTeam(ExtendedLoanTeamVO extendedLoanTeam) {
		this.extendedLoanTeam = extendedLoanTeam;
	}

	public BigDecimal getAppFee() {
		return appFee;
	}

	public void setAppFee(BigDecimal appFee) {
		this.appFee = appFee;
	}

	public Long getPurchaseDocumentExpiryDate() {
		return purchaseDocumentExpiryDate;
	}

	public void setPurchaseDocumentExpiryDate(Long purchaseDocumentExpiryDate) {
		this.purchaseDocumentExpiryDate = purchaseDocumentExpiryDate;
	}

	public void setLqbUrl(String lqbUrl) {
		this.lqbUrl = lqbUrl;
	}

	public String getLqbUrl() {
		return lqbUrl;
	}

	public String getCreditReportUrl() {
		return creditReportUrl;
	}

	public void setCreditReportUrl(String creditReportUrl) {
		this.creditReportUrl = creditReportUrl;
	}

	public void setLqbInformationAvailable(Boolean lqbInformationAvailable) {
		this.lqbInformationAvailable = lqbInformationAvailable;
	}

	public Boolean getLqbInformationAvailable() {
		return lqbInformationAvailable;
	}

	public String getLockedRateData() {
		return lockedRateData;
	}

	public void setLockedRateData(String lockedRateData) {
		this.lockedRateData = lockedRateData;
	}

	public String getUserZipCode() {
		return userZipCode;
	}

	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}

	@Override
	public String toString() {
		return "LoanVO [id=" + id + ", createdDate=" + createdDate
		        + ", deleted=" + deleted + ", loanEmailId=" + loanEmailId
		        + ", lqbFileId=" + lqbFileId + ", modifiedDate=" + modifiedDate
		        + ", name=" + name + ", status=" + status + ", user=" + user
		        + ", loanType=" + loanType + ", lmEmail=" + lmEmail
		        + ", realtorEmail=" + realtorEmail + ", creditReportUrl="
		        + creditReportUrl + ", lqbInformationAvailable="
		        + lqbInformationAvailable + ", propertyType=" + propertyType
		        + ", currentLoanMilestone=" + currentLoanMilestone
		        + ", loanAppForms=" + loanAppForms + ", loanApplicationFees="
		        + loanApplicationFees + ", loanDetail=" + loanDetail
		        + ", loanMilestones=" + loanMilestones + ", loanNeedsLists="
		        + loanNeedsLists + ", loanNotifications=" + loanNotifications
		        + ", loanRates=" + loanRates + ", loanSettings=" + loanSettings
		        + ", loanTeam=" + loanTeam + ", extendedLoanTeam="
		        + extendedLoanTeam + ", lockStatus=" + lockStatus
		        + ", isBankConnected=" + isBankConnected + ", setSenderDomain="
		        + setSenderDomain + ", lockedRate=" + lockedRate + ", appFee="
		        + appFee + ", customerWorkflowID=" + customerWorkflowID
		        + ", loanManagerWorkflowID=" + loanManagerWorkflowID
		        + ", userLoanStatus=" + userLoanStatus
		        + ", purchaseDocumentExpiryDate=" + purchaseDocumentExpiryDate
		        + ", lqbUrl=" + lqbUrl + ", lockedRateData=" + lockedRateData
		        + ", userZipCode=" + userZipCode + "]";
	}

	public Date getLockExpirationDate() {
		return lockExpirationDate;
	}

	public void setLockExpirationDate(Date lockExpirationDate) {
		this.lockExpirationDate = lockExpirationDate;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Boolean getRateLockRequested() {
	    return rateLockRequested;
    }

	public void setRateLockRequested(Boolean rateLockRequested) {
	    this.rateLockRequested = rateLockRequested;
    }

	public String getLqbLoanStatus() {
	    return lqbLoanStatus;
    }

	public void setLqbLoanStatus(String lqbLoanStatus) {
	    this.lqbLoanStatus = lqbLoanStatus;
    }
}