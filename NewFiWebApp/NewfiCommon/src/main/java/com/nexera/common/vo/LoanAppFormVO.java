package com.nexera.common.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.nexera.common.commons.Utils;
import com.nexera.common.entity.CustomerBankAccountDetails;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerOtherAccountDetails;
import com.nexera.common.entity.CustomerRetirementAccountDetails;
import com.nexera.common.entity.CustomerSpouseBankAccountDetails;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.CustomerSpouseEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseOtherAccountDetails;
import com.nexera.common.entity.CustomerSpouseRetirementAccountDetails;
import com.nexera.common.entity.GovernmentQuestion;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.PurchaseDetails;
import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.SpouseGovernmentQuestions;
import com.nexera.common.entity.User;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.LoanTypeMasterEnum;

public class LoanAppFormVO implements Serializable {

	@Override
	public String toString() {
		return "LoanAppFormVO [id=" + id + ", isEmployed=" + isEmployed
		        + ", EmployedIncomePreTax=" + EmployedIncomePreTax
		        + ", EmployedAt=" + EmployedAt + ", EmployedSince="
		        + EmployedSince + ", hoaDues=" + hoaDues
		        + ", homeRecentlySold=" + homeRecentlySold + ", homeToSell="
		        + homeToSell + ", maritalStatus=" + maritalStatus
		        + ", ownsOtherProperty=" + ownsOtherProperty
		        + ", ispensionOrRetirement=" + ispensionOrRetirement
		        + ", receiveAlimonyChildSupport=" + receiveAlimonyChildSupport
		        + ", rentedOtherProperty=" + rentedOtherProperty
		        + ", secondMortgage=" + secondMortgage + ", isselfEmployed="
		        + isselfEmployed + ", selfEmployedIncome=" + selfEmployedIncome
		        + ", isssIncomeOrDisability=" + isssIncomeOrDisability
		        + ", isSpouseOnLoan=" + isSpouseOnLoan + ", spouseName="
		        + spouseName + ", monthlyRent=" + monthlyRent
		        + ", paySecondMortgage=" + paySecondMortgage
		        + ", selfEmployedMonthlyIncome=" + selfEmployedMonthlyIncome
		        + ", selfEmployedNoYear=" + selfEmployedNoYear
		        + ", childSupportAlimony=" + childSupportAlimony
		        + ", socialSecurityIncome=" + socialSecurityIncome
		        + ", ssDisabilityIncome=" + ssDisabilityIncome
		        + ", monthlyPension=" + monthlyPension + ", retirementIncome="
		        + retirementIncome + ", isCoborrowerPresent="
		        + isCoborrowerPresent + ", ssnProvided=" + ssnProvided
		        + ", cbSsnProvided=" + cbSsnProvided + ", emailQuote="
		        + emailQuote + ", skipMyAssets=" + skipMyAssets + ", user="
		        + user + ", propertyTypeMaster=" + propertyTypeMaster
		        + ", governmentquestion=" + governmentquestion
		        + ", refinancedetails=" + refinancedetails
		        + ", customerSpouseDetail=" + customerSpouseDetail
		        + ", spouseGovernmentQuestions=" + spouseGovernmentQuestions
		        + ", loanType=" + loanType + ", customerEmploymentIncome="
		        + customerEmploymentIncome + ", customerBankAccountDetails="
		        + customerBankAccountDetails + ", customerOtherAccountDetails="
		        + customerOtherAccountDetails
		        + ", customerRetirementAccountDetails="
		        + customerRetirementAccountDetails
		        + ", customerSpouseBankAccountDetails="
		        + customerSpouseBankAccountDetails
		        + ", customerSpouseEmploymentIncome="
		        + customerSpouseEmploymentIncome
		        + ", customerSpouseOtherAccountDetails="
		        + customerSpouseOtherAccountDetails
		        + ", customerSpouseRetirementAccountDetails="
		        + customerSpouseRetirementAccountDetails + ", loan=" + loan
		        + ", userEmploymentHistories=" + userEmploymentHistories
		        + ", loanAppFormCompletionStatus="
		        + loanAppFormCompletionStatus + ", purchaseDetails="
		        + purchaseDetails + ", loanMangerEmail=" + loanMangerEmail
		        + ", realtorEmail=" + realtorEmail + "]";
	}

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
	private Boolean isselfEmployed;
	private String selfEmployedIncome;
	private Boolean isssIncomeOrDisability;

	private Boolean isSpouseOnLoan;
	private String spouseName;
	private String monthlyRent;
	private Boolean paySecondMortgage;

	private String selfEmployedMonthlyIncome;
	private String selfEmployedNoYear;
	private String childSupportAlimony;
	private String socialSecurityIncome;
	private String ssDisabilityIncome;
	private String monthlyPension;
	private String retirementIncome;
	private Boolean isCoborrowerPresent;
	private Boolean ssnProvided;
	private Boolean cbSsnProvided;

	private Boolean emailQuote;

	private Boolean skipMyAssets;

	private UserVO user;
	private PropertyTypeMasterVO propertyTypeMaster;
	private GovernmentQuestionVO governmentquestion;
	private RefinanceVO refinancedetails;
	private CustomerSpouseDetailVO customerSpouseDetail;
	private SpouseGovernmentQuestionVO spouseGovernmentQuestions;
	// private CustomerEmploymentIncomeVO customerEmploymentIncome;

	private LoanTypeMasterVO loanType;
	private List<CustomerEmploymentIncomeVO> customerEmploymentIncome;

	private List<CustomerBankAccountDetailsVO> customerBankAccountDetails;
	private List<CustomerOtherAccountDetailsVO> customerOtherAccountDetails;
	private List<CustomerRetirementAccountDetailsVO> customerRetirementAccountDetails;
	private List<CustomerSpouseBankAccountDetailsVO> customerSpouseBankAccountDetails;
	private List<CustomerSpouseEmploymentIncomeVO> customerSpouseEmploymentIncome;
	private List<CustomerSpouseOtherAccountDetailsVO> customerSpouseOtherAccountDetails;
	private List<CustomerSpouseRetirementAccountDetailsVO> customerSpouseRetirementAccountDetails;

	private LoanVO loan;
	private List<UserEmploymentHistoryVO> userEmploymentHistories;
	private Float loanAppFormCompletionStatus;

	private PurchaseDetailsVO purchaseDetails;
	private String loanMangerEmail;
	private String realtorEmail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getHoaDues() {
		return hoaDues;
	}

	public void setHoaDues(Boolean hoaDues) {
		this.hoaDues = hoaDues;
	}

	public Boolean getHomeRecentlySold() {
		return homeRecentlySold;
	}

	public void setHomeRecentlySold(Boolean homeRecentlySold) {
		this.homeRecentlySold = homeRecentlySold;
	}

	public Boolean getHomeToSell() {
		return homeToSell;
	}

	public void setHomeToSell(Boolean homeToSell) {
		this.homeToSell = homeToSell;
	}

	public Boolean getIsCoborrowerPresent() {
		return isCoborrowerPresent;
	}

	public void setIsCoborrowerPresent(Boolean isCoborrowerPresent) {
		this.isCoborrowerPresent = isCoborrowerPresent;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Boolean getOwnsOtherProperty() {
		return ownsOtherProperty;
	}

	public void setOwnsOtherProperty(Boolean ownsOtherProperty) {
		this.ownsOtherProperty = ownsOtherProperty;
	}

	public Boolean getReceiveAlimonyChildSupport() {
		return receiveAlimonyChildSupport;
	}

	public void setReceiveAlimonyChildSupport(Boolean receiveAlimonyChildSupport) {
		this.receiveAlimonyChildSupport = receiveAlimonyChildSupport;
	}

	public Boolean getRentedOtherProperty() {
		return rentedOtherProperty;
	}

	public void setRentedOtherProperty(Boolean rentedOtherProperty) {
		this.rentedOtherProperty = rentedOtherProperty;
	}

	public Boolean getSecondMortgage() {
		return secondMortgage;
	}

	public void setSecondMortgage(Boolean secondMortgage) {
		this.secondMortgage = secondMortgage;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public PropertyTypeMasterVO getPropertyTypeMaster() {
		return propertyTypeMaster;
	}

	public void setPropertyTypeMaster(PropertyTypeMasterVO propertyTypeMaster) {
		this.propertyTypeMaster = propertyTypeMaster;
	}

	public LoanTypeMasterVO getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanTypeMasterVO loanType) {
		this.loanType = loanType;
	}

	public LoanVO getLoan() {
		return loan;
	}

	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}

	public List<UserEmploymentHistoryVO> getUserEmploymentHistories() {
		return userEmploymentHistories;
	}

	public void setUserEmploymentHistories(
	        List<UserEmploymentHistoryVO> userEmploymentHistories) {
		this.userEmploymentHistories = userEmploymentHistories;
	}

	public Boolean getIsSpouseOnLoan() {
		return isSpouseOnLoan;
	}

	public void setIsSpouseOnLoan(Boolean isSpouseOnLoan) {
		this.isSpouseOnLoan = isSpouseOnLoan;
	}

	public String getSpouseName() {
		return spouseName;
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	public Boolean getIsEmployed() {
		return isEmployed;
	}

	public void setIsEmployed(Boolean isEmployed) {
		this.isEmployed = isEmployed;
	}

	public String getEmployedIncomePreTax() {
		return EmployedIncomePreTax;
	}

	public void setEmployedIncomePreTax(String employedIncomePreTax) {
		EmployedIncomePreTax = employedIncomePreTax;
	}

	public String getEmployedAt() {
		return EmployedAt;
	}

	public void setEmployedAt(String employedAt) {
		EmployedAt = employedAt;
	}

	public String getEmployedSince() {
		return EmployedSince;
	}

	public void setEmployedSince(String employedSince) {
		EmployedSince = employedSince;
	}

	public Boolean getIspensionOrRetirement() {
		return ispensionOrRetirement;
	}

	public void setIspensionOrRetirement(Boolean ispensionOrRetirement) {
		this.ispensionOrRetirement = ispensionOrRetirement;
	}

	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	public Boolean getIsselfEmployed() {
		return isselfEmployed;
	}

	public void setIsselfEmployed(Boolean isselfEmployed) {
		this.isselfEmployed = isselfEmployed;
	}

	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	public Boolean getIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(Boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
	}

	public GovernmentQuestionVO getGovernmentquestion() {
		return governmentquestion;
	}

	public void setGovernmentquestion(GovernmentQuestionVO governmentquestion) {
		this.governmentquestion = governmentquestion;
	}

	public String getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(String monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public String getSelfEmployedMonthlyIncome() {
		return selfEmployedMonthlyIncome;
	}

	public void setSelfEmployedMonthlyIncome(String selfEmployedMonthlyIncome) {
		this.selfEmployedMonthlyIncome = selfEmployedMonthlyIncome;
	}

	public String getChildSupportAlimony() {
		return childSupportAlimony;
	}

	public void setChildSupportAlimony(String childSupportAlimony) {
		this.childSupportAlimony = childSupportAlimony;
	}

	public String getSocialSecurityIncome() {
		return socialSecurityIncome;
	}

	public void setSocialSecurityIncome(String socialSecurityIncome) {
		this.socialSecurityIncome = socialSecurityIncome;
	}

	public String getRetirementIncome() {
		return retirementIncome;
	}

	public void setRetirementIncome(String retirementIncome) {
		this.retirementIncome = retirementIncome;
	}

	public String getSelfEmployedNoYear() {
		return selfEmployedNoYear;
	}

	public void setSelfEmployedNoYear(String selfEmployedNoYear) {
		this.selfEmployedNoYear = selfEmployedNoYear;
	}

	public RefinanceVO getRefinancedetails() {
		return refinancedetails;
	}

	public void setRefinancedetails(RefinanceVO refinancedetails) {
		this.refinancedetails = refinancedetails;
	}

	public Boolean getSsnProvided() {
		return ssnProvided;
	}

	public void setSsnProvided(Boolean ssnProvided) {
		this.ssnProvided = ssnProvided;
	}

	public Boolean getCbSsnProvided() {
		return cbSsnProvided;
	}

	public void setCbSsnProvided(Boolean cbSsnProvided) {
		this.cbSsnProvided = cbSsnProvided;
	}

	public PurchaseDetailsVO getPurchaseDetails() {
		return purchaseDetails;
	}

	public void setPurchaseDetails(PurchaseDetailsVO purchaseDetails) {
		this.purchaseDetails = purchaseDetails;
	}

	public Float getLoanAppFormCompletionStatus() {
		return loanAppFormCompletionStatus;
	}

	public void setLoanAppFormCompletionStatus(Float loanAppFormCompletionStatus) {
		this.loanAppFormCompletionStatus = loanAppFormCompletionStatus;
	}

	public CustomerSpouseDetailVO getCustomerSpouseDetail() {
		return customerSpouseDetail;
	}

	public void setCustomerSpouseDetail(
	        CustomerSpouseDetailVO customerSpouseDetail) {
		this.customerSpouseDetail = customerSpouseDetail;
	}

	public SpouseGovernmentQuestionVO getSpouseGovernmentQuestions() {
		return spouseGovernmentQuestions;
	}

	public void setSpouseGovernmentQuestions(
	        SpouseGovernmentQuestionVO spouseGovernmentQuestions) {
		this.spouseGovernmentQuestions = spouseGovernmentQuestions;
	}

	public List<CustomerEmploymentIncomeVO> getCustomerEmploymentIncome() {
		return customerEmploymentIncome;
	}

	public void setCustomerEmploymentIncome(
	        List<CustomerEmploymentIncomeVO> customerEmploymentIncome) {
		this.customerEmploymentIncome = customerEmploymentIncome;
	}

	public List<CustomerBankAccountDetailsVO> getCustomerBankAccountDetails() {
		return customerBankAccountDetails;
	}

	public void setCustomerBankAccountDetails(
	        List<CustomerBankAccountDetailsVO> customerBankAccountDetails) {
		this.customerBankAccountDetails = customerBankAccountDetails;
	}

	public List<CustomerOtherAccountDetailsVO> getCustomerOtherAccountDetails() {
		return customerOtherAccountDetails;
	}

	public void setCustomerOtherAccountDetails(
	        List<CustomerOtherAccountDetailsVO> customerOtherAccountDetails) {
		this.customerOtherAccountDetails = customerOtherAccountDetails;
	}

	public List<CustomerRetirementAccountDetailsVO> getCustomerRetirementAccountDetails() {
		return customerRetirementAccountDetails;
	}

	public void setCustomerRetirementAccountDetails(
	        List<CustomerRetirementAccountDetailsVO> customerRetirementAccountDetails) {
		this.customerRetirementAccountDetails = customerRetirementAccountDetails;
	}

	public List<CustomerSpouseBankAccountDetailsVO> getCustomerSpouseBankAccountDetails() {
		return customerSpouseBankAccountDetails;
	}

	public void setCustomerSpouseBankAccountDetails(
	        List<CustomerSpouseBankAccountDetailsVO> customerSpouseBankAccountDetails) {
		this.customerSpouseBankAccountDetails = customerSpouseBankAccountDetails;
	}

	public List<CustomerSpouseEmploymentIncomeVO> getCustomerSpouseEmploymentIncome() {
		return customerSpouseEmploymentIncome;
	}

	public void setCustomerSpouseEmploymentIncome(
	        List<CustomerSpouseEmploymentIncomeVO> customerSpouseEmploymentIncome) {
		this.customerSpouseEmploymentIncome = customerSpouseEmploymentIncome;
	}

	public List<CustomerSpouseOtherAccountDetailsVO> getCustomerSpouseOtherAccountDetails() {
		return customerSpouseOtherAccountDetails;
	}

	public void setCustomerSpouseOtherAccountDetails(
	        List<CustomerSpouseOtherAccountDetailsVO> customerSpouseOtherAccountDetails) {
		this.customerSpouseOtherAccountDetails = customerSpouseOtherAccountDetails;
	}

	public List<CustomerSpouseRetirementAccountDetailsVO> getCustomerSpouseRetirementAccountDetails() {
		return customerSpouseRetirementAccountDetails;
	}

	public void setCustomerSpouseRetirementAccountDetails(
	        List<CustomerSpouseRetirementAccountDetailsVO> customerSpouseRetirementAccountDetails) {
		this.customerSpouseRetirementAccountDetails = customerSpouseRetirementAccountDetails;
	}

	public Boolean getEmailQuote() {
		return emailQuote;
	}

	public void setEmailQuote(Boolean emailQuote) {
		this.emailQuote = emailQuote;
	}

	public LoanAppForm convertToEntity() {

		LoanAppForm loanAppForm = new LoanAppForm();

		loanAppForm.setId(this.id);

		loanAppForm.setIsEmployed(this.isEmployed);
		loanAppForm.setEmployedIncomePreTax(this.EmployedIncomePreTax);
		loanAppForm.setEmployedAt(this.EmployedAt);
		loanAppForm.setEmployedSince(this.EmployedSince);
		loanAppForm.setHoaDues(this.hoaDues);
		loanAppForm.setHomeRecentlySold(this.homeRecentlySold);
		loanAppForm.setHomeToSell(this.homeToSell);
		loanAppForm.setMaritalStatus(this.maritalStatus);
		loanAppForm.setOwnsOtherProperty(this.ownsOtherProperty);
		loanAppForm.setIspensionOrRetirement(this.ispensionOrRetirement);
		loanAppForm.setNotApplicable(this.getNotApplicable());
		loanAppForm
		        .setReceiveAlimonyChildSupport(this.receiveAlimonyChildSupport);
		loanAppForm.setRentedOtherProperty(this.rentedOtherProperty);
		loanAppForm.setSecondMortgage(this.secondMortgage);
		loanAppForm.setPaySecondMortgage(this.paySecondMortgage);
		loanAppForm.setIsselfEmployed(this.isselfEmployed);
		loanAppForm.setSelfEmployedIncome(this.selfEmployedIncome);
		loanAppForm.setIsssIncomeOrDisability(this.isssIncomeOrDisability);

		loanAppForm.setSsnProvided(this.ssnProvided);
		loanAppForm.setCbSsnProvided(this.cbSsnProvided);
		loanAppForm.setIsCoborrowerPresent(this.isCoborrowerPresent);
		loanAppForm.setIsSpouseOnLoan(this.isSpouseOnLoan);
		loanAppForm.setSpouseName(this.spouseName);
		loanAppForm.setMonthlyRent(this.getMonthlyRent());

		// User Income page : Starts
		loanAppForm.setMonthlyIncome(Utils
		        .convertToBigDecimal(this.selfEmployedMonthlyIncome));
		loanAppForm.setSelfEmployedNoYear(Utils
		        .convertToInteger(this.selfEmployedNoYear));
		loanAppForm.setChildSupportAlimony(Utils
		        .convertToBigDecimal(this.childSupportAlimony));
		loanAppForm.setSocialSecurityIncome(Utils
		        .convertToBigDecimal(this.socialSecurityIncome));
		loanAppForm.setSsDisabilityIncome(this.ssDisabilityIncome);
		loanAppForm.setMonthlyPension(this.monthlyPension);
		loanAppForm.setRetirementIncome(Utils
		        .convertToBigDecimal(this.retirementIncome));
		// User Income Page : Ends

		// Skip My Assets: Starts
		loanAppForm.setSkipMyAssets(this.skipMyAssets);

		// Ends

		loanAppForm
		        .setLoanAppFormCompletionStatus(this.loanAppFormCompletionStatus);
		loanAppForm.setMonthlyRent(this.monthlyRent);

		if (this.getLoan().getLoanType() == null) {
			/*
			 * if (this.getLoanType().getLoanTypeCd().equalsIgnoreCase("REF")) {
			 * loanAppForm.setLoanTypeMaster(new
			 * LoanTypeMaster(LoanTypeMasterEnum.REF)); } else {
			 * loanAppForm.setLoanTypeMaster(new
			 * LoanTypeMaster(LoanTypeMasterEnum.PUR)); } } else {
			 */
			loanAppForm.setLoanTypeMaster(new LoanTypeMaster(
			        LoanTypeMasterEnum.NONE));
		} else {
			loanAppForm.setLoanTypeMaster(this.getLoan().getLoanType()
			        .convertToEntity());

		}

		loanAppForm
		        .setPropertyTypeMaster(parseVOtoEntityPropertyTypeMaster(this
		                .getPropertyTypeMaster()));
		loanAppForm.setGovernmentquestion(parseVOtoEntity(this
		        .getGovernmentquestion()));
		loanAppForm
		        .setCustomerspousedetail(parseVOtoEntityCustomerSpouseDetail(this
		                .getCustomerSpouseDetail()));
		loanAppForm
		        .setSpouseGovernmentQuestions(parseVOtoEntitySpouseGovernmentQuestions(this
		                .getSpouseGovernmentQuestions()));

		loanAppForm
		        .setCustomerEmploymentIncome(parseVOtoEntityCustomerEmploymentIncome(this
		                .getCustomerEmploymentIncome()));
		loanAppForm
		        .setCustomerBankAccountDetails(parseVOtoEntityCustomerBankAccountDetails(this
		                .getCustomerBankAccountDetails()));
		loanAppForm
		        .setCustomerOtherAccountDetails(parseVOtoEntityCustomerOtherAccountDetails(this
		                .getCustomerOtherAccountDetails()));
		loanAppForm
		        .setCustomerRetirementAccountDetails(parseVOtoEntityCustomerRetirementAccountDetails(this
		                .getCustomerRetirementAccountDetails()));

		loanAppForm
		        .setCustomerSpouseBankAccountDetails(parseVOtoEntityCustomerSpouseBankAccountDetails(this
		                .getCustomerSpouseBankAccountDetails()));
		loanAppForm
		        .setCustomerSpouseEmploymentIncome(parseVOtoEntityCustomerSpouseEmploymentIncome(this
		                .getCustomerSpouseEmploymentIncome()));
		loanAppForm
		        .setCustomerSpouseOtherAccountDetails(parseVOtoEntityCustomerSpouseOtherAccountDetails(this
		                .getCustomerSpouseOtherAccountDetails()));
		loanAppForm
		        .setCustomerSpouseRetirementAccountDetails(parseVOtoEntityCustomerSpouseRetirementAccountDetails(this
		                .getCustomerSpouseRetirementAccountDetails()));

		// if(null != this.getUser().getCustomerEnagagement() && null!=
		// this.getRefinancedetails() &&
		// this.getUser().getCustomerEnagagement().getLoanType().equalsIgnoreCase("REF"))
		// this.getRefinancedetails().setRefinanceOption(this.getUser().getCustomerEnagagement().getRefinanceOption());

		loanAppForm.setRefinancedetails(parseVOtoEntityRefinance(this
		        .getRefinancedetails()));

		// Purchase Details
		// if (null!= this.getPurchaseDetails() &&
		// this.getUser().getCustomerEnagagement().getLoanType().equalsIgnoreCase("purchase"))
		// this.getPurchaseDetails().setLivingSituation(this.getUser().getCustomerEnagagement().getLivingSituation());

		loanAppForm.setPurchaseDetails(parseVOtoEntityPurchaseDetails(this
		        .getPurchaseDetails()));

		loanAppForm.setUser(User.convertFromVOToEntity(this.getUser()));

		if (null != this.getLoan() && null != this.getLoan().getLqbFileId()) {

			loanAppForm.setLoan(parseVOtoEntityLoan(this.getLoan()));
		} else {
			loanAppForm.setLoan(this.getLoan().convertToEntity());

		}

		/* save data in the purchase table */

		return loanAppForm;
	}

	public Loan parseVOtoEntityLoan(LoanVO loanVO) {

		Loan loan = new Loan();
		loan.setId(loanVO.getId());
		loan.setLqbFileId(loanVO.getLqbFileId());

		if (null != loanVO.getLoanType()) {

			if (loanVO.getLoanType().getLoanTypeCd().equalsIgnoreCase("REF")) {

				loan.setLoanType(new LoanTypeMaster(LoanTypeMasterEnum.REF));
			} else {

				loan.setLoanType(new LoanTypeMaster(LoanTypeMasterEnum.PUR));

			}
		} else {

			loan.setLoanType(new LoanTypeMaster(LoanTypeMasterEnum.NONE));
		}

		loan.setCreatedDate(loanVO.getCreatedDate());
		loan.setModifiedDate(loanVO.getModifiedDate());
		loan.setLoanEmailId(loanVO.getLoanEmailId());
		loan.setLoanProgressStatus(new LoanProgressStatusMaster(
		        LoanProgressStatusMasterEnum.NEW_LOAN));
		loan.setCustomerWorkflow(loanVO.getCustomerWorkflowID());
		loan.setLoanManagerWorkflow(loanVO.getLoanManagerWorkflowID());
		loan.setLockedRateData(loanVO.getLockedRateData());
		loan.setUser(User.convertFromVOToEntity(this.getUser()));

		return loan;

	}

	private PurchaseDetails parseVOtoEntityPurchaseDetails(
	        PurchaseDetailsVO purchaseDetailsVO) {

		PurchaseDetails purchaseDetails = new PurchaseDetails();
		if (purchaseDetailsVO == null)
			return purchaseDetails;

		purchaseDetails.setId(purchaseDetailsVO.getId());
		purchaseDetails.setLivingSituation(purchaseDetailsVO
		        .getLivingSituation());
		purchaseDetails.setBuyhomeZipPri(purchaseDetailsVO.getBuyhomeZipPri());
		purchaseDetails.setBuyhomeZipSec(purchaseDetailsVO.getBuyhomeZipSec());
		purchaseDetails.setBuyhomeZipTri(purchaseDetailsVO.getBuyhomeZipTri());
		purchaseDetails
		        .setEstimatedPrice(purchaseDetailsVO.getEstimatedPrice());
		purchaseDetails.setHousePrice(purchaseDetailsVO.getHousePrice());
		purchaseDetails.setLoanAmount(purchaseDetailsVO.getLoanAmount());

		purchaseDetails.setTaxAndInsuranceInLoanAmt(purchaseDetailsVO
		        .isTaxAndInsuranceInLoanAmt());

		return purchaseDetails;
	}

	public GovernmentQuestion parseVOtoEntity(
	        GovernmentQuestionVO governmentQuestionVO) {

		GovernmentQuestion governmentQuestion = new GovernmentQuestion();
		if (governmentQuestionVO == null)
			return governmentQuestion;

		// GovernmentQuestion governmentQuestion = new GovernmentQuestion();

		governmentQuestion.setId(governmentQuestionVO.getId());
		governmentQuestion.setOutstandingJudgments(governmentQuestionVO
		        .isOutstandingJudgments());
		governmentQuestion.setBankrupt(governmentQuestionVO.isBankrupt());
		governmentQuestion.setPropertyForeclosed(governmentQuestionVO
		        .isPropertyForeclosed());
		governmentQuestion.setLawsuit(governmentQuestionVO.isLawsuit());
		governmentQuestion.setObligatedLoan(governmentQuestionVO
		        .isObligatedLoan());

		governmentQuestion.setFederalDebt(governmentQuestionVO.isFederalDebt());
		governmentQuestion.setObligatedToPayAlimony(governmentQuestionVO
		        .isObligatedToPayAlimony());
		governmentQuestion.setEndorser(governmentQuestionVO.isEndorser());
		governmentQuestion.setUSCitizen(governmentQuestionVO.isUSCitizen());
		governmentQuestion.setPermanentResidentAlien(governmentQuestionVO
		        .getPermanentResidentAlien());
		governmentQuestion.setOccupyPrimaryResidence(governmentQuestionVO
		        .isOccupyPrimaryResidence());
		governmentQuestion.setOwnershipInterestInProperty(governmentQuestionVO
		        .isOwnershipInterestInProperty());

		governmentQuestion.setEthnicity(governmentQuestionVO.getEthnicity());
		governmentQuestion.setRace(governmentQuestionVO.getRace());
		governmentQuestion.setSex(governmentQuestionVO.getSex());
		governmentQuestion.setIsDownPaymentBorrowed(governmentQuestionVO
		        .getIsDownPaymentBorrowed());
		governmentQuestion.setSkipOptionalQuestion(governmentQuestionVO
		        .getSkipOptionalQuestion());
		governmentQuestion.setTypeOfPropertyOwned(governmentQuestionVO
		        .getTypeOfPropertyOwned());
		governmentQuestion.setPropertyTitleStatus(governmentQuestionVO
		        .getPropertyTitleStatus());

		return governmentQuestion;

	}

	public RefinanceDetails parseVOtoEntityRefinance(RefinanceVO refinanceVO) {

		RefinanceDetails refinanceDetails = new RefinanceDetails();
		if (refinanceVO == null)
			return refinanceDetails;
		// RefinanceDetails refinanceDetails = new RefinanceDetails();
		refinanceDetails.setId(refinanceVO.getId());
		refinanceDetails.setCurrentMortgageBalance(refinanceVO
		        .getCurrentMortgageBalance());
		refinanceDetails.setRefinanceOption(refinanceVO.getRefinanceOption());
		refinanceDetails.setCurrentMortgagePayment(refinanceVO
		        .getCurrentMortgagePayment());

		refinanceDetails.setIncludeTaxes(refinanceVO.isIncludeTaxes());
		refinanceDetails.setSecondMortageBalance(refinanceVO
		        .getSecondMortageBalance());
		refinanceDetails.setCashTakeOut(refinanceVO.getCashTakeOut());
		refinanceDetails.setMortgageyearsleft(refinanceVO
		        .getMortgageyearsleft());

		return refinanceDetails;

	}

	public PropertyTypeMaster parseVOtoEntityPropertyTypeMaster(
	        PropertyTypeMasterVO propertyTypeMasterVO) {

		PropertyTypeMaster propertyTypeMaster = new PropertyTypeMaster();
		if (propertyTypeMasterVO == null)
			return propertyTypeMaster;
		propertyTypeMaster.setId(propertyTypeMasterVO.getId());
		propertyTypeMaster.setPropertyTypeCd(propertyTypeMasterVO
		        .getPropertyTypeCd());
		propertyTypeMaster.setResidenceTypeCd(propertyTypeMasterVO
		        .getResidenceTypeCd());
		propertyTypeMaster.setPropertyTaxesPaid(propertyTypeMasterVO
		        .getPropertyTaxesPaid());
		propertyTypeMaster.setPropertyInsuranceProvider(propertyTypeMasterVO
		        .getPropertyInsuranceProvider());
		propertyTypeMaster.setPropertyInsuranceCost(propertyTypeMasterVO
		        .getPropertyInsuranceCost());
		propertyTypeMaster.setPropertyPurchaseYear(propertyTypeMasterVO
		        .getPropertyPurchaseYear());
		propertyTypeMaster.setHomeWorthToday(propertyTypeMasterVO
		        .getHomeWorthToday());
		propertyTypeMaster
		        .setHomeZipCode(propertyTypeMasterVO.getHomeZipCode());
		propertyTypeMaster.setPropTaxMonthlyOryearly(propertyTypeMasterVO
		        .getPropTaxMonthlyOryearly());
		propertyTypeMaster.setCurrentHomePrice(propertyTypeMasterVO
		        .getCurrentHomePrice());
		propertyTypeMaster.setCurrentHomeMortgageBalance(propertyTypeMasterVO
		        .getCurrentHomeMortgageBalance());
		propertyTypeMaster.setNewHomeBudgetFromsale(propertyTypeMasterVO
		        .getNewHomeBudgetFromsale());
		propertyTypeMaster.setPropInsMonthlyOryearly(propertyTypeMasterVO
		        .getPropInsMonthlyOryearly());
		propertyTypeMaster
		        .setHomeZipCode(propertyTypeMasterVO.getHomeZipCode());
		propertyTypeMaster.setPropStreetAddress(propertyTypeMasterVO
		        .getPropStreetAddress());
		propertyTypeMaster.setPropCity(propertyTypeMasterVO.getPropCity());
		propertyTypeMaster.setPropState(propertyTypeMasterVO.getPropState());

		return propertyTypeMaster;

	}

	public User parseVOtoEntityUser(UserVO userVO) {

		if (userVO == null)
			return null;
		User user = new User();

		user.setId(userVO.getId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setEmailId(userVO.getEmailId());
		user.setUsername(userVO.getEmailId());

		if (userVO.getCustomerDetail() == null)
			return user;

		user.setCustomerDetail(parseVOtoEntityCustomerDetails(userVO
		        .getCustomerDetail()));

		return user;

	}

	public CustomerDetail parseVOtoEntityCustomerDetails(
	        CustomerDetailVO customerDetailVO) {

		CustomerDetail customerDetail = new CustomerDetail();
		if (customerDetailVO == null)
			return null;
		// return customerDetail;
		// CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setAddressCity(customerDetailVO.getAddressCity());
		customerDetail.setAddressState(customerDetailVO.getAddressState());
		customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
		if (customerDetailVO.getDateOfBirth() != null)
			customerDetail.setDateOfBirth(new Date(customerDetailVO
			        .getDateOfBirth()));

		customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
		customerDetail.setSsn(customerDetailVO.getSsn());

		customerDetail.setId(customerDetailVO.getId());

		customerDetail.setIsselfEmployed(customerDetailVO.getIsselfEmployed());
		customerDetail.setSelfEmployedIncome(customerDetailVO
		        .getSelfEmployedIncome());
		customerDetail.setIsssIncomeOrDisability(customerDetailVO
		        .getIsssIncomeOrDisability());
		customerDetail.setSsDisabilityIncome(customerDetailVO
		        .getSsDisabilityIncome());
		customerDetail.setIspensionOrRetirement(customerDetailVO
		        .getIspensionOrRetirement());
		customerDetail.setMonthlyPension(customerDetailVO.getMonthlyPension());
		customerDetail.setLivingSince(customerDetailVO.getLivingSince());

		// customerDetail.setCustomerSpouseDetail(parseVOtoEntityCustomerSpouseDetail(customerDetailVO.getCustomerSpouseDetail()));
		// customerDetail.setCustomerEmploymentIncome(parseVOtoEntityCustomerEmploymentIncome(customerDetailVO.getCustomerEmploymentIncome()));
		// customerDetail.setCustomerBankAccountDetails(parseVOtoEntityCustomerBankAccountDetails(customerDetailVO.getCustomerBankAccountDetails()));
		// customerDetail.setCustomerRetirementAccountDetails(parseVOtoEntityCustomerRetirementAccountDetails(customerDetailVO.getCustomerRetirementAccountDetails()));
		// customerDetail.setCustomerOtherAccountDetails(parseVOtoEntityCustomerOtherAccountDetails(customerDetailVO.getCustomerOtherAccountDetails()));

		return customerDetail;

	}

	private List<CustomerEmploymentIncome> parseVOtoEntityCustomerEmploymentIncome(
	        List<CustomerEmploymentIncomeVO> customerEmploymentIncomeVOlist) {

		List<CustomerEmploymentIncome> customerEmploymentIncomeList = new ArrayList();

		if (null == customerEmploymentIncomeVOlist)
			return null;
		// return customerEmploymentIncome;

		Iterator<CustomerEmploymentIncomeVO> itr = customerEmploymentIncomeVOlist
		        .iterator();

		while (itr.hasNext()) {
			CustomerEmploymentIncome customerEmploymentIncome = new CustomerEmploymentIncome();
			CustomerEmploymentIncomeVO customeremploymentincomeVO = itr.next();

			if (null != customeremploymentincomeVO
			        .getCustomerEmploymentIncome()
			        && customeremploymentincomeVO.getCustomerEmploymentIncome()
			                .getId() != 0)
				customerEmploymentIncome.setId(customeremploymentincomeVO
				        .getCustomerEmploymentIncome().getId());
			// customerEmploymentIncome.setId(customeremploymentincomeVO.getCustomerEmploymentIncome().getId());
			customerEmploymentIncome.setJobTitle(customeremploymentincomeVO
			        .getCustomerEmploymentIncome().getJobTitle());

			customerEmploymentIncome.setEmployedAt(customeremploymentincomeVO
			        .getCustomerEmploymentIncome().getEmployedAt());
			customerEmploymentIncome
			        .setEmployedIncomePreTax(customeremploymentincomeVO
			                .getCustomerEmploymentIncome()
			                .getEmployedIncomePreTax());
			customerEmploymentIncome
			        .setEmployedSince(customeremploymentincomeVO
			                .getCustomerEmploymentIncome().getEmployedSince());
			if (customeremploymentincomeVO.getCustomerEmploymentIncome() != null
			        && customeremploymentincomeVO.getCustomerEmploymentIncome()
			                .getEmploymentLength() != null) {
				customerEmploymentIncome.setEmploymentLength(Double
				        .parseDouble(customeremploymentincomeVO
				                .getCustomerEmploymentIncome()
				                .getEmploymentLength()));
			}
			customerEmploymentIncomeList.add(customerEmploymentIncome);
		}
		return customerEmploymentIncomeList;
	}

	private List<CustomerBankAccountDetails> parseVOtoEntityCustomerBankAccountDetails(
	        List<CustomerBankAccountDetailsVO> customerBankAccountDetailsVO) {

		List<CustomerBankAccountDetails> customerBankAccountDetailsList = new ArrayList<CustomerBankAccountDetails>();

		if (null == customerBankAccountDetailsVO)
			return null;
		// return customerBankAccountDetails;

		Iterator<CustomerBankAccountDetailsVO> itr = customerBankAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerBankAccountDetails customerBankAccountDetails = new CustomerBankAccountDetails();
			CustomerBankAccountDetailsVO custBankAccDetVO = itr.next();

			// customerEmploymentIncome.setId(customeremploymentincomeVO.getCustomerEmploymentIncome().getId());

			if (null != custBankAccDetVO.getCustomerBankAccountDetails()
			        && custBankAccDetVO.getCustomerBankAccountDetails().getId() != 0)
				customerBankAccountDetails.setId(custBankAccDetVO
				        .getCustomerBankAccountDetails().getId());
			customerBankAccountDetails.setAccountSubType(custBankAccDetVO
			        .getCustomerBankAccountDetails().getAccountSubType());
			customerBankAccountDetails.setAmountfornewhome(custBankAccDetVO
			        .getCustomerBankAccountDetails().getAmountForNewHome());
			customerBankAccountDetails
			        .setCurrentaccountbalance(custBankAccDetVO
			                .getCustomerBankAccountDetails()
			                .getCurrentAccountBalance());

			customerBankAccountDetailsList.add(customerBankAccountDetails);
		}

		return customerBankAccountDetailsList;
	}

	private List<CustomerOtherAccountDetails> parseVOtoEntityCustomerOtherAccountDetails(
	        List<CustomerOtherAccountDetailsVO> customerOtherAccountDetailsVO) {

		List<CustomerOtherAccountDetails> customerOtherAccountDetailsList = new ArrayList<CustomerOtherAccountDetails>();
		if (null == customerOtherAccountDetailsVO)
			return null;
		// return customerOtherAccountDetails;

		Iterator<CustomerOtherAccountDetailsVO> itr = customerOtherAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerOtherAccountDetails customerOtherAccountDetails = new CustomerOtherAccountDetails();
			CustomerOtherAccountDetailsVO custOtherAccDetVO = itr.next();

			if (null != custOtherAccDetVO.getCustomerOtherAccountDetails()
			        && custOtherAccDetVO.getCustomerOtherAccountDetails()
			                .getId() != 0)
				customerOtherAccountDetails.setId(custOtherAccDetVO
				        .getCustomerOtherAccountDetails().getId());
			customerOtherAccountDetails.setAccountSubType(custOtherAccDetVO
			        .getCustomerOtherAccountDetails().getAccountSubType());
			customerOtherAccountDetails.setAmountfornewhome(custOtherAccDetVO
			        .getCustomerOtherAccountDetails().getAmountForNewHome());
			customerOtherAccountDetails
			        .setCurrentaccountbalance(custOtherAccDetVO
			                .getCustomerOtherAccountDetails()
			                .getCurrentAccountBalance());
			customerOtherAccountDetailsList.add(customerOtherAccountDetails);
		}

		return customerOtherAccountDetailsList;

	}

	private List<CustomerRetirementAccountDetails> parseVOtoEntityCustomerRetirementAccountDetails(
	        List<CustomerRetirementAccountDetailsVO> customerRetirementAccountDetailsVO) {

		List<CustomerRetirementAccountDetails> customerRetirementAccountDetailsList = new ArrayList<CustomerRetirementAccountDetails>();
		if (null == customerRetirementAccountDetailsVO)
			return null;

		Iterator<CustomerRetirementAccountDetailsVO> itr = customerRetirementAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerRetirementAccountDetails customerRetirementAccountDetails = new CustomerRetirementAccountDetails();
			CustomerRetirementAccountDetailsVO custRetAccDetVO = itr.next();

			if (null != custRetAccDetVO.getCustomerRetirementAccountDetails()
			        && custRetAccDetVO.getCustomerRetirementAccountDetails()
			                .getId() != 0)
				customerRetirementAccountDetails.setId(custRetAccDetVO
				        .getCustomerRetirementAccountDetails().getId());
			customerRetirementAccountDetails.setAccountSubType(custRetAccDetVO
			        .getCustomerRetirementAccountDetails().getAccountSubType());
			customerRetirementAccountDetails
			        .setAmountfornewhome(custRetAccDetVO
			                .getCustomerRetirementAccountDetails()
			                .getAmountForNewHome());
			customerRetirementAccountDetails
			        .setCurrentaccountbalance(custRetAccDetVO
			                .getCustomerRetirementAccountDetails()
			                .getCurrentAccountBalance());
			customerRetirementAccountDetailsList
			        .add(customerRetirementAccountDetails);
		}

		return customerRetirementAccountDetailsList;
	}

	private List<CustomerSpouseEmploymentIncome> parseVOtoEntityCustomerSpouseEmploymentIncome(
	        List<CustomerSpouseEmploymentIncomeVO> customerSpouseEmploymentIncomeVO) {

		List<CustomerSpouseEmploymentIncome> customerSpouseEmploymentIncomeList = new ArrayList();

		if (null == customerSpouseEmploymentIncomeVO)
			return null;
		// return customerEmploymentIncome;

		Iterator<CustomerSpouseEmploymentIncomeVO> itr = customerSpouseEmploymentIncomeVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome = new CustomerSpouseEmploymentIncome();

			CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentincomeVO = itr
			        .next();

			if (null != customerSpouseEmploymentincomeVO
			        .getCustomerSpouseEmploymentIncome()
			        && customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome().getId() != 0)
				customerSpouseEmploymentIncome
				        .setId(customerSpouseEmploymentincomeVO
				                .getCustomerSpouseEmploymentIncome().getId());
			customerSpouseEmploymentIncome
			        .setJobTitle(customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome().getJobTitle());
			customerSpouseEmploymentIncome
			        .setEmployedAt(customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome()
			                .getEmployedAt());
			customerSpouseEmploymentIncome
			        .setEmployedIncomePreTax(customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome()
			                .getEmployedIncomePreTax());
			customerSpouseEmploymentIncome
			        .setEmployedSince(customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome()
			                .getEmployedSince());
			if (customerSpouseEmploymentincomeVO
			        .getCustomerSpouseEmploymentIncome() != null
			        && customerSpouseEmploymentincomeVO
			                .getCustomerSpouseEmploymentIncome()
			                .getEmploymentLength() != null) {
				customerSpouseEmploymentIncome.setEmploymentLength(Double
				        .parseDouble(customerSpouseEmploymentincomeVO
				                .getCustomerSpouseEmploymentIncome()
				                .getEmploymentLength()));
			}
			customerSpouseEmploymentIncomeList
			        .add(customerSpouseEmploymentIncome);

		}
		return customerSpouseEmploymentIncomeList;
	}

	// Spouse Details Code below

	private List<CustomerSpouseOtherAccountDetails> parseVOtoEntityCustomerSpouseOtherAccountDetails(
	        List<CustomerSpouseOtherAccountDetailsVO> customerSpouseOtherAccountDetailsVO) {

		List<CustomerSpouseOtherAccountDetails> customerSpouseOtherAccountDetailsList = new ArrayList<CustomerSpouseOtherAccountDetails>();
		if (null == customerSpouseOtherAccountDetailsVO)
			return null;
		// return customerOtherAccountDetails;

		Iterator<CustomerSpouseOtherAccountDetailsVO> itr = customerSpouseOtherAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerSpouseOtherAccountDetails customerSpouseOtherAccountDetails = new CustomerSpouseOtherAccountDetails();
			CustomerSpouseOtherAccountDetailsVO custSpouseOtherAccDetVO = itr
			        .next();

			if (null != custSpouseOtherAccDetVO
			        .getCustomerSpouseOtherAccountDetails()
			        && custSpouseOtherAccDetVO
			                .getCustomerSpouseOtherAccountDetails().getId() != 0)
				customerSpouseOtherAccountDetails.setId(custSpouseOtherAccDetVO
				        .getCustomerSpouseOtherAccountDetails().getId());
			customerSpouseOtherAccountDetails
			        .setAccountSubType(custSpouseOtherAccDetVO
			                .getCustomerSpouseOtherAccountDetails()
			                .getAccountSubType());
			customerSpouseOtherAccountDetails
			        .setAmountfornewhome(custSpouseOtherAccDetVO
			                .getCustomerSpouseOtherAccountDetails()
			                .getAmountForNewHome());
			customerSpouseOtherAccountDetails
			        .setCurrentaccountbalance(custSpouseOtherAccDetVO
			                .getCustomerSpouseOtherAccountDetails()
			                .getCurrentAccountBalance());
			customerSpouseOtherAccountDetailsList
			        .add(customerSpouseOtherAccountDetails);
		}

		return customerSpouseOtherAccountDetailsList;

	}

	private List<CustomerSpouseRetirementAccountDetails> parseVOtoEntityCustomerSpouseRetirementAccountDetails(
	        List<CustomerSpouseRetirementAccountDetailsVO> customerSpouseRetirementAccountDetailsVO) {

		List<CustomerSpouseRetirementAccountDetails> customerSpouseRetirementAccountDetailsList = new ArrayList<CustomerSpouseRetirementAccountDetails>();
		if (null == customerSpouseRetirementAccountDetailsVO)
			return null;

		Iterator<CustomerSpouseRetirementAccountDetailsVO> itr = customerSpouseRetirementAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerSpouseRetirementAccountDetails customerSpouseRetirementAccountDetails = new CustomerSpouseRetirementAccountDetails();

			CustomerSpouseRetirementAccountDetailsVO custSpouseRetAccDetVO = itr
			        .next();

			if (null != custSpouseRetAccDetVO
			        .getCustomerSpouseRetirementAccountDetails()
			        && custSpouseRetAccDetVO
			                .getCustomerSpouseRetirementAccountDetails()
			                .getId() != 0)
				customerSpouseRetirementAccountDetails
				        .setId(custSpouseRetAccDetVO
				                .getCustomerSpouseRetirementAccountDetails()
				                .getId());

			customerSpouseRetirementAccountDetails
			        .setAccountSubType(custSpouseRetAccDetVO
			                .getCustomerSpouseRetirementAccountDetails()
			                .getAccountSubType());
			customerSpouseRetirementAccountDetails
			        .setAmountfornewhome(custSpouseRetAccDetVO
			                .getCustomerSpouseRetirementAccountDetails()
			                .getAmountForNewHome());
			customerSpouseRetirementAccountDetails
			        .setCurrentaccountbalance(custSpouseRetAccDetVO
			                .getCustomerSpouseRetirementAccountDetails()
			                .getCurrentAccountBalance());
			customerSpouseRetirementAccountDetailsList
			        .add(customerSpouseRetirementAccountDetails);

		}

		return customerSpouseRetirementAccountDetailsList;
	}

	private List<CustomerSpouseBankAccountDetails> parseVOtoEntityCustomerSpouseBankAccountDetails(
	        List<CustomerSpouseBankAccountDetailsVO> customerSpouseBankAccountDetailsVO) {

		List<CustomerSpouseBankAccountDetails> customerSpouseBankAccountDetailsList = new ArrayList<CustomerSpouseBankAccountDetails>();

		if (null == customerSpouseBankAccountDetailsVO)
			return null;
		// return customerBankAccountDetails;

		Iterator<CustomerSpouseBankAccountDetailsVO> itr = customerSpouseBankAccountDetailsVO
		        .iterator();

		while (itr.hasNext()) {
			CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails = new CustomerSpouseBankAccountDetails();
			CustomerSpouseBankAccountDetailsVO custSpouseBankAccDetVO = itr
			        .next();

			// customerEmploymentIncome.setId(customeremploymentincomeVO.getCustomerEmploymentIncome().getId());
			if (null != custSpouseBankAccDetVO
			        .getCustomerSpouseBankAccountDetails()
			        && custSpouseBankAccDetVO
			                .getCustomerSpouseBankAccountDetails().getId() != 0)
				customerSpouseBankAccountDetails.setId(custSpouseBankAccDetVO
				        .getCustomerSpouseBankAccountDetails().getId());
			customerSpouseBankAccountDetails
			        .setAccountSubType((custSpouseBankAccDetVO
			                .getCustomerSpouseBankAccountDetails()
			                .getAccountSubType()));
			customerSpouseBankAccountDetails
			        .setAmountfornewhome((custSpouseBankAccDetVO
			                .getCustomerSpouseBankAccountDetails()
			                .getAmountForNewHome()));
			customerSpouseBankAccountDetails
			        .setCurrentaccountbalance((custSpouseBankAccDetVO
			                .getCustomerSpouseBankAccountDetails()
			                .getCurrentAccountBalance()));

			customerSpouseBankAccountDetailsList
			        .add(customerSpouseBankAccountDetails);
		}

		return customerSpouseBankAccountDetailsList;
	}

	// //////////////////////////////////////

	private CustomerSpouseDetail parseVOtoEntityCustomerSpouseDetail(
	        CustomerSpouseDetailVO customerSpouseDetailVO) {

		CustomerSpouseDetail customerSpouseDetail = new CustomerSpouseDetail();
		if (null == customerSpouseDetailVO)
			return null;
		// return customerSpouseDetail;

		customerSpouseDetail.setId(customerSpouseDetailVO.getId());
		customerSpouseDetail.setSpouseName(customerSpouseDetailVO
		        .getSpouseName());

		customerSpouseDetail.setIs_pension_or_retirement(customerSpouseDetailVO
		        .isIspensionOrRetirement());
		customerSpouseDetail.setIsssIncomeOrDisability(customerSpouseDetailVO
		        .isIsssIncomeOrDisability());
		customerSpouseDetail.setSelfEmployed(customerSpouseDetailVO
		        .isSelfEmployed());
		customerSpouseDetail.setNotApplicable(customerSpouseDetailVO
		        .getNotApplicable());
		customerSpouseDetail.setSpouseLastName(customerSpouseDetailVO
		        .getSpouseLastName());
		customerSpouseDetail.setStreetAddress(customerSpouseDetailVO
		        .getStreetAddress());
		customerSpouseDetail.setState(customerSpouseDetailVO.getState());
		customerSpouseDetail.setCity(customerSpouseDetailVO.getCity());
		customerSpouseDetail.setZip(customerSpouseDetailVO.getZip());

		// income Start:
		customerSpouseDetail.setSelfEmployedIncome(Utils
		        .convertToBigDecimal(customerSpouseDetailVO
		                .getSelfEmployedIncome()));
		customerSpouseDetail.setSelfEmployedNoYear(Utils
		        .convertToInteger(customerSpouseDetailVO
		                .getSelfEmployedNoYear()));
		customerSpouseDetail.setChildSupportAlimony(Utils
		        .convertToBigDecimal(customerSpouseDetailVO
		                .getChildSupportAlimony()));
		customerSpouseDetail.setSocialSecurityIncome(Utils
		        .convertToBigDecimal(customerSpouseDetailVO
		                .getSocialSecurityIncome()));
		customerSpouseDetail.setDisabilityIncome(Utils
		        .convertToBigDecimal(customerSpouseDetailVO
		                .getDisabilityIncome()));
		customerSpouseDetail
		        .setMonthlyPension(Utils
		                .convertToBigDecimal(customerSpouseDetailVO
		                        .getMonthlyPension()));
		customerSpouseDetail.setRetirementIncome(Utils
		        .convertToBigDecimal(customerSpouseDetailVO
		                .getRetirementIncome()));

		// income ends

		// My Assets: Start

		customerSpouseDetail.setSkipMyAssets(customerSpouseDetailVO
		        .getSkipMyAssets());

		// My Assets:Ends

		if (customerSpouseDetailVO.getSpouseDateOfBirth() != null)
			customerSpouseDetail.setSpouseDateOfBirth(new Date(
			        customerSpouseDetailVO.getSpouseDateOfBirth()));
		customerSpouseDetail.setSpouseSecPhoneNumber(customerSpouseDetailVO
		        .getSpouseSecPhoneNumber());
		customerSpouseDetail.setCurrentHomePrice(customerSpouseDetailVO
		        .getCurrentHomePrice());
		customerSpouseDetail
		        .setCurrentHomeMortgageBalance(customerSpouseDetailVO
		                .getCurrentHomeMortgageBalance());
		customerSpouseDetail.setNewHomeBudgetFromsale(customerSpouseDetailVO
		        .getNewHomeBudgetFromsale());

		return customerSpouseDetail;
	}

	private SpouseGovernmentQuestions parseVOtoEntitySpouseGovernmentQuestions(
	        SpouseGovernmentQuestionVO spouseGovernmentQuestionsVO) {

		SpouseGovernmentQuestions spouseGovernmentQuestions = new SpouseGovernmentQuestions();
		if (spouseGovernmentQuestionsVO == null)
			return null;

		// GovernmentQuestion governmentQuestion = new GovernmentQuestion();

		spouseGovernmentQuestions.setId(spouseGovernmentQuestionsVO.getId());
		spouseGovernmentQuestions
		        .setOutstandingJudgments(spouseGovernmentQuestionsVO
		                .isOutstandingJudgments());
		spouseGovernmentQuestions.setBankrupt(spouseGovernmentQuestionsVO
		        .isBankrupt());
		spouseGovernmentQuestions
		        .setPropertyForeclosed(spouseGovernmentQuestionsVO
		                .isPropertyForeclosed());
		spouseGovernmentQuestions.setLawsuit(spouseGovernmentQuestionsVO
		        .isLawsuit());
		spouseGovernmentQuestions.setObligatedLoan(spouseGovernmentQuestionsVO
		        .isObligatedLoan());

		spouseGovernmentQuestions.setFederalDebt(spouseGovernmentQuestionsVO
		        .isFederalDebt());
		spouseGovernmentQuestions
		        .setObligatedToPayAlimony(spouseGovernmentQuestionsVO
		                .isObligatedToPayAlimony());
		spouseGovernmentQuestions.setEndorser(spouseGovernmentQuestionsVO
		        .isEndorser());
		spouseGovernmentQuestions.setUSCitizen(spouseGovernmentQuestionsVO
		        .isUSCitizen());

		if (spouseGovernmentQuestionsVO.isUSCitizen() != null
		        && "No".equalsIgnoreCase(spouseGovernmentQuestionsVO
		                .isUSCitizen().toString())
		        || spouseGovernmentQuestionsVO.isUSCitizen() == false) {
			spouseGovernmentQuestions
			        .setPermanentResidentAlien(spouseGovernmentQuestionsVO
			                .isPermanentResidentAlien());
		}

		spouseGovernmentQuestions
		        .setOccupyPrimaryResidence(spouseGovernmentQuestionsVO
		                .isOccupyPrimaryResidence());
		spouseGovernmentQuestions
		        .setOwnershipInterestInProperty(spouseGovernmentQuestionsVO
		                .isOwnershipInterestInProperty());

		spouseGovernmentQuestions.setEthnicity(spouseGovernmentQuestionsVO
		        .getEthnicity());
		spouseGovernmentQuestions
		        .setRace(spouseGovernmentQuestionsVO.getRace());
		spouseGovernmentQuestions.setSex(spouseGovernmentQuestionsVO.getSex());
		spouseGovernmentQuestions
		        .setIsDownPaymentBorrowed(spouseGovernmentQuestionsVO
		                .getIsDownPaymentBorrowed());
		spouseGovernmentQuestions
		        .setSkipOptionalQuestion(spouseGovernmentQuestionsVO
		                .getSkipOptionalQuestion());
		spouseGovernmentQuestions
		        .setTypeOfPropertyOwned(spouseGovernmentQuestionsVO
		                .getTypeOfPropertyOwned());
		spouseGovernmentQuestions
		        .setPropertyTitleStatus(spouseGovernmentQuestionsVO
		                .getPropertyTitleStatus());

		return spouseGovernmentQuestions;

	}

	public Boolean getPaySecondMortgage() {
		return paySecondMortgage;
	}

	public void setPaySecondMortgage(Boolean paySecondMortgage) {
		this.paySecondMortgage = paySecondMortgage;
	}

	public LoanAppFormVO convertFromEntity(LoanAppForm loanAppEntity) {

		LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
		// loanAppFormVO.setEmployed(loanAppEntity.getEmployed());
		loanAppFormVO.setHoaDues(loanAppEntity.getHoaDues());
		loanAppFormVO.setMaritalStatus(loanAppEntity.getMaritalStatus());
		loanAppFormVO.setReceiveAlimonyChildSupport(loanAppEntity
		        .getReceiveAlimonyChildSupport());
		return loanAppFormVO;
	}

	public String getLoanMangerEmail() {
		return loanMangerEmail;
	}

	public void setLoanMangerEmail(String loanMangerEmail) {
		this.loanMangerEmail = loanMangerEmail;
	}

	public String getRealtorEmail() {
		return realtorEmail;
	}

	public void setRealtorEmail(String realtorEmail) {
		this.realtorEmail = realtorEmail;
	}

	public Boolean getSkipMyAssets() {
		return skipMyAssets;
	}

	public void setSkipMyAssets(Boolean skipMyAssets) {
		this.skipMyAssets = skipMyAssets;
	}

	public Boolean getNotApplicable() {
		return notApplicable;
	}

	public void setNotApplicable(Boolean notApplicable) {
		this.notApplicable = notApplicable;
	}

}