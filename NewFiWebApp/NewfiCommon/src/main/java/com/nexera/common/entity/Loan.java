package com.nexera.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.vo.LoanDetailVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;

/**
 * The persistent class for the loan database table.
 * 
 */
@Entity
@Table(name = "loan")
@NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l")
public class Loan implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private Boolean deleted;
	private String loanEmailId;
	private String lqbFileId;
	private Date modifiedDate;
	private String name;
	private User user;
	private LoanTypeMaster loanType;
	private LoanProgressStatusMaster loanProgressStatus;

	private PropertyTypeMaster propertyType;
	private LoanMilestoneMaster currentLoanMilestone;
	private List<LoanAppForm> loanAppForms;
	private List<LoanApplicationFee> loanApplicationFees;
	private LoanDetail loanDetail;
	private List<LoanMilestone> loanMilestones;
	private List<LoanNeedsList> loanNeedsLists;
	private List<Notification> loanNotifications;
	private List<LoanRate> loanRates;
	private List<LoanSetting> loanSettings;
	private List<LoanTeam> loanTeam;
	private List<TransactionDetails> transactionDetails;
	private List<UploadedFilesList> uploadedFileList;
	private Integer customerWorkflow;
	private Integer loanManagerWorkflow;
	private String lockStatus;
	private Boolean isBankConnected;
	private String lockedRate;
	private BigDecimal appFee;
	private Long purchaseDocumentExpiryDate;
	private String lockedRateData;
	private Date lockExpirationDate;
	private Boolean rateLockRequested;
	private Double lqbLoanAmount;
	private Double lqbAppraisedValue;
	private Double ltv;
	private String paymentVendor;
	private LoanLCStateMaster loanLCStateMaster;
	private Date interview_date;

	
	public Date getInterview_date() {
		return interview_date;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "interview_date")
	public void setInterview_date(Date interview_date) {
		this.interview_date = interview_date;
	}

	public LoanLCStateMaster getLoanLCStateMaster() {
		return loanLCStateMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loanLCStateMaster")
	public void setLoanLCStateMaster(LoanLCStateMaster loanLCStateMaster) {
		this.loanLCStateMaster = loanLCStateMaster;
	}

	@Column(name = "bank_connected", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsBankConnected() {
		return isBankConnected;
	}

	public void setIsBankConnected(Boolean isBankConnected) {
		this.isBankConnected = isBankConnected;
	}

	public Loan() {
	}

	public Loan(Integer id) {
		this.id = id;
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

	@Column(name = "deleted", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
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
	public String getLqbFileId() {
		return lqbFileId;
	}

	public void setLqbFileId(String lqbFileId) {
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

	public void setLoanAppForms(List<LoanAppForm> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "loan")
	public List<TransactionDetails> getTransactionDetails() {
		return this.transactionDetails;
	}

	public void setTransactionDetails(
	        List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public LoanAppForm addLoanAppForm(LoanAppForm loanAppForm) {
		getLoanAppForms().add(loanAppForm);
		loanAppForm.setLoan(this);

		return loanAppForm;
	}

	public LoanAppForm removeLoanAppForm(LoanAppForm loanappform) {
		getLoanAppForms().remove(loanappform);
		loanappform.setLoan(null);

		return loanappform;
	}

	// bi-directional many-to-one association to LoanApplicationFee
	@OneToMany(mappedBy = "loan")
	public List<LoanApplicationFee> getLoanApplicationFees() {
		return this.loanApplicationFees;
	}

	public void setLoanApplicationFees(
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_detail")
	public LoanDetail getLoanDetail() {
		return loanDetail;
	}

	public void setLoanDetail(LoanDetail loanDetail) {
		this.loanDetail = loanDetail;
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
		loanMilestone.setLoan(this);

		return loanMilestone;
	}

	public LoanMilestone removeLoanmilestone(LoanMilestone loanMilestone) {
		getLoanMilestones().remove(loanMilestone);
		loanMilestone.setLoan(null);

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
	public List<Notification> getLoanNotifications() {
		return this.loanNotifications;
	}

	public void setLoanNotifications(List<Notification> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	public Notification addLoanNotification(Notification loanNotification) {
		getLoanNotifications().add(loanNotification);
		loanNotification.setLoan(this);

		return loanNotification;
	}

	public Notification removeLoanNotification(Notification loanNotification) {
		getLoanNotifications().remove(loanNotification);
		loanNotification.setLoan(null);

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
		loanRate.setLoan(this);

		return loanRate;
	}

	public LoanRate removeLoanrate(LoanRate loanRate) {
		getLoanRates().remove(loanRate);
		loanRate.setLoan(null);

		return loanRate;
	}

	// bi-directional many-to-one association to LoanProgressStatusMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_progress_status_master")
	public LoanProgressStatusMaster getLoanProgressStatus() {
		return loanProgressStatus;
	}

	public void setLoanProgressStatus(
	        LoanProgressStatusMaster loanProgressStatus) {
		this.loanProgressStatus = loanProgressStatus;
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
		loanSetting.setLoan(this);

		return loanSetting;
	}

	public LoanSetting removeLoanSetting(LoanSetting loanSetting) {
		getLoanSettings().remove(loanSetting);
		loanSetting.setLoan(null);

		return loanSetting;
	}

	// bi-directional many-to-one association to LoanTeam
	@OneToMany(mappedBy = "loan")
	public List<LoanTeam> getLoanTeam() {
		return this.loanTeam;
	}

	public void setLoanTeam(List<LoanTeam> loanTeam) {
		this.loanTeam = loanTeam;
	}

	public LoanTeam addLoanTeam(LoanTeam loanTeam) {
		getLoanTeam().add(loanTeam);
		loanTeam.setLoan(this);

		return loanTeam;
	}

	public LoanTeam removeLoanTeam(LoanTeam loanTeam) {
		getLoanTeam().remove(loanTeam);
		loanTeam.setLoan(null);

		return loanTeam;
	}

	@Column(name = "customer_workflow")
	public Integer getCustomerWorkflow() {
		return customerWorkflow;
	}

	public void setCustomerWorkflow(Integer customerWorkflow) {
		this.customerWorkflow = customerWorkflow;
	}

	@Column(name = "loan_manager_workflow")
	public Integer getLoanManagerWorkflow() {
		return loanManagerWorkflow;
	}

	public void setLoanManagerWorkflow(Integer loanManagerWorkflow) {
		this.loanManagerWorkflow = loanManagerWorkflow;
	}

	@Column(name = "locked_rate")
	public String getLockedRate() {
		return lockedRate;
	}

	public void setLockedRate(String lockedRate) {
		this.lockedRate = lockedRate;
	}

	public static LoanVO convertFromEntityToVO(Loan loan) {
		if (loan == null)
			return null;

		LoanVO loanVo = new LoanVO();
		loanVo.setId(loan.getId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setDeleted(loan.getDeleted());
		loanVo.setLoanEmailId(loan.getLoanEmailId());
		loanVo.setLqbFileId(loan.getLqbFileId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setModifiedDate(loan.getModifiedDate());
		loanVo.setAppFee(loan.getAppFee());
		loanVo.setName(loan.getName());
		loanVo.setLockExpirationDate(loan.getLockExpirationDate());
		loanVo.setRateLockRequested(loan.getRateLockRequested());
		loanVo.setLockedRateData(loan.getLockedRateData());
		loanVo.setPaymentVendor(loan.getPaymentVendor());
		loanVo.setPurchaseDocumentExpiryDate(loan
		        .getPurchaseDocumentExpiryDate());
		if (loan.getLoanProgressStatus() != null) {
			loanVo.setStatus(loan.getLoanProgressStatus()
			        .getLoanProgressStatus());
		}
		if (loan.getLoanType() != null) {
			loanVo.setLoanType(LoanTypeMaster.convertEntityToVO(loan
			        .getLoanType()));
		}

		loanVo.setUser(User.convertFromEntityToVO(loan.getUser()));
		List<UserVO> loanTeam = new ArrayList<UserVO>();
		if (null != loan.getLoanTeam()) {
			for (LoanTeam team : loan.getLoanTeam()) {
				UserVO userVo = User.convertFromEntityToVO(team.getUser());
				// loanVo.setUser(userVo);
				loanTeam.add(userVo);
			}
		}
		loanVo.setLoanTeam(loanTeam);
		loanVo.setLoanDetail(buildLoanDetailVO(loan.getLoanDetail()));
		if (loan.getCustomerWorkflow() != null) {
			loanVo.setCustomerWorkflowID(loan.getCustomerWorkflow());
		}
		if (loan.getLoanManagerWorkflow() != null) {
			loanVo.setLoanManagerWorkflowID(loan.getLoanManagerWorkflow());
		}

		loanVo.setIsBankConnected(loan.getIsBankConnected());
		loanVo.setLockStatus(loan.getLockStatus());
		loanVo.setSetSenderDomain(CommonConstants.SENDER_DOMAIN);
		loanVo.setLockedRate(loan.getLockedRate());
		if (loan.getLqbLoanAmount() != null) {
			loanVo.setLoanAmount(loan.getLqbLoanAmount());
		}
		if (loan.getLqbAppraisedValue() != null) {
			loanVo.setAppraisedValue(loan.getLqbAppraisedValue());
		}

		if (loan.getLtv() != null) {
			loanVo.setLtv(loan.getLtv());
		}
		return loanVo;
	}

	private static LoanDetailVO buildLoanDetailVO(LoanDetail detail) {
		if (detail == null)
			return null;

		LoanDetailVO detailVO = new LoanDetailVO();
		detailVO.setId(detail.getId());
		detailVO.setDownPayment(detail.getDownPayment());
		detailVO.setLoanAmount(detail.getLoanAmount());
		detailVO.setRate(detail.getRate());
		return detailVO;

	}

	@OneToMany(mappedBy = "loan")
	public List<UploadedFilesList> getUploadedFileList() {
		return uploadedFileList;
	}

	public void setUploadedFileList(List<UploadedFilesList> uploadedFileList) {
		this.uploadedFileList = uploadedFileList;
	}

	@Column(name = "app_fee")
	public BigDecimal getAppFee() {
		return appFee;
	}

	public void setAppFee(BigDecimal appFee) {
		this.appFee = appFee;
	}

	@Column(name = "purchase_document_expiry_date")
	public Long getPurchaseDocumentExpiryDate() {
		return purchaseDocumentExpiryDate;
	}

	public void setPurchaseDocumentExpiryDate(Long purchaseDocumentExpiryDate) {
		this.purchaseDocumentExpiryDate = purchaseDocumentExpiryDate;
	}

	@Column(name = "locked_rate_data")
	public String getLockedRateData() {
		return lockedRateData;
	}

	public void setLockedRateData(String lockedRateData) {
		this.lockedRateData = lockedRateData;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lock_expiration_date")
	public Date getLockExpirationDate() {
		return lockExpirationDate;
	}

	public void setLockExpirationDate(Date lockExpirationDate) {
		this.lockExpirationDate = lockExpirationDate;
	}

	@Column(name = "lock_status")
	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	@Column(name = "rate_lock_requested")
	public Boolean getRateLockRequested() {
		return rateLockRequested;
	}

	public void setRateLockRequested(Boolean rateLockRequested) {
		this.rateLockRequested = rateLockRequested;
	}

	@Column(name = "lqb_loan_amount")
	public Double getLqbLoanAmount() {
		return lqbLoanAmount;
	}

	public void setLqbLoanAmount(Double lqbLoanAmount) {
		this.lqbLoanAmount = lqbLoanAmount;
	}

	@Column(name = "lqb_appraised_val")
	public Double getLqbAppraisedValue() {
		return lqbAppraisedValue;
	}

	public void setLqbAppraisedValue(Double lqbAppraisedValue) {
		this.lqbAppraisedValue = lqbAppraisedValue;
	}

	@Column(name = "ltv")
	public Double getLtv() {
		return ltv;
	}

	public void setLtv(Double ltv) {
		this.ltv = ltv;
	}

	@Column(name = "payment_vendor")
	public String getPaymentVendor() {
		return paymentVendor;
	}

	public void setPaymentVendor(String paymentVendor) {
		this.paymentVendor = paymentVendor;
	}

}