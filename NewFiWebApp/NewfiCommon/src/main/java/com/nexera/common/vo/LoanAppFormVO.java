package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.GovernmentQuestion;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserEmploymentHistory;
import com.nexera.common.entity.WorkflowExec;

public class LoanAppFormVO implements Serializable {
	

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
	private Boolean isselfEmployed;
	private String  selfEmployedIncome;
	private Boolean isssIncomeOrDisability;
	private String  ssDisabilityIncome;
	private Boolean isSpouseOnLoan ;
	private String	spouseName;
	private Boolean paySecondMortgage;
	private UserVO user;
	private PropertyTypeMasterVO propertyTypeMaster;
	private GovernmentQuestionVO governmentquestion;
	private RefinanceVO refinancedetails;
	private LoanTypeMasterVO loanType;
	private LoanTypeMasterVO loanTypeMaster;
	private LoanVO loan;
	private List<UserEmploymentHistoryVO> userEmploymentHistories;
	


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

	public RefinanceVO getRefinancedetails() {
		return refinancedetails;
	}

	public void setRefinancedetails(RefinanceVO refinancedetails) {
		this.refinancedetails = refinancedetails;
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
		loanAppForm.setMonthlyPension(this.monthlyPension);
		loanAppForm.setReceiveAlimonyChildSupport(this.receiveAlimonyChildSupport);
		loanAppForm.setRentedOtherProperty(this.rentedOtherProperty);
		loanAppForm.setSecondMortgage(this.secondMortgage);
		loanAppForm.setPaySecondMortgage(this.paySecondMortgage);
		loanAppForm.setIsselfEmployed(this.isselfEmployed);
		loanAppForm.setSelfEmployedIncome(this.selfEmployedIncome);
		loanAppForm.setIsssIncomeOrDisability(this.isssIncomeOrDisability);
		loanAppForm.setSsDisabilityIncome(this.ssDisabilityIncome);
		loanAppForm.setIsSpouseOnLoan(this.isSpouseOnLoan);
		loanAppForm.setSpouseName(this.spouseName);
		
	

		//propertyTypeMaster.setId(1);
		
		loanAppForm.setPropertyTypeMaster(parseVOtoEntityPropertyTypeMaster(this.getPropertyTypeMaster()));
		loanAppForm.setGovernmentquestion(parseVOtoEntity(this.getGovernmentquestion()));
		loanAppForm.setRefinancedetails(parseVOtoEntityRefinance(this.getRefinancedetails()));

		loanAppForm.setUser(parseVOtoEntityUser(this.getUser()));


		loanAppForm.setLoan(this.getLoan().convertToEntity());
		
		
		
		
		

		return loanAppForm;
	}

	public GovernmentQuestion parseVOtoEntity(GovernmentQuestionVO governmentQuestionVO){
		
		if(governmentQuestionVO == null)
			return null;
		
		GovernmentQuestion governmentQuestion = new GovernmentQuestion();
		System.out.println(governmentQuestionVO.getId());
	    governmentQuestion.setId(governmentQuestionVO.getId());
		governmentQuestion.setOutstandingJudgments(governmentQuestionVO.isOutstandingJudgments());
		governmentQuestion.setBankrupt(governmentQuestionVO.isBankrupt());
		governmentQuestion.setPropertyForeclosed(governmentQuestionVO.isPropertyForeclosed());
		governmentQuestion.setLawsuit(governmentQuestionVO.isLawsuit());
		governmentQuestion.setObligatedLoan(governmentQuestionVO.isObligatedLoan());
		
		governmentQuestion.setFederalDebt(governmentQuestionVO.isFederalDebt());
		governmentQuestion.setObligatedToPayAlimony(governmentQuestionVO.isObligatedToPayAlimony());
		governmentQuestion.setEndorser(governmentQuestionVO.isEndorser());
		governmentQuestion.setUSCitizen(governmentQuestionVO.isUSCitizen());
		governmentQuestion.setOccupyPrimaryResidence(governmentQuestionVO.isOccupyPrimaryResidence());
		governmentQuestion.setOwnershipInterestInProperty(governmentQuestionVO.isOwnershipInterestInProperty());
		
		governmentQuestion.setEthnicity(governmentQuestionVO.getEthnicity());
		governmentQuestion.setRace(governmentQuestionVO.getRace());
		governmentQuestion.setSex(governmentQuestionVO.getSex());
		
		return governmentQuestion;
		
	}
	
	public RefinanceDetails parseVOtoEntityRefinance(RefinanceVO refinanceVO){
		
		if(refinanceVO == null)
			return null;
		RefinanceDetails refinanceDetails = new RefinanceDetails();
		System.out.println("refinanceVO.getId()"+refinanceVO.getId());
		refinanceDetails.setId(refinanceVO.getId());
		refinanceDetails.setCurrentMortgageBalance(refinanceVO.getCurrentMortgageBalance());
		refinanceDetails.setRefinanceOption(refinanceVO.getRefinanceOption());
		refinanceDetails.setCurrentMortgagePayment(refinanceVO.getCurrentMortgagePayment());
		refinanceDetails.setIncludeTaxes(refinanceVO.isIncludeTaxes());
		
		return refinanceDetails;
		
	}
	
public PropertyTypeMaster parseVOtoEntityPropertyTypeMaster(PropertyTypeMasterVO propertyTypeMasterVO){
		
		if(propertyTypeMasterVO == null)
			return null;
		PropertyTypeMaster propertyTypeMaster = new PropertyTypeMaster();
		
		System.out.println("propertyTypeMasterVO.getId()"+propertyTypeMasterVO.getId());
		propertyTypeMaster.setId(propertyTypeMasterVO.getId());
		propertyTypeMaster.setPropertyTypeCd(propertyTypeMasterVO.getPropertyTypeCd());
		propertyTypeMaster.setResidenceTypeCd(propertyTypeMasterVO.getResidenceTypeCd());
		propertyTypeMaster.setPropertyTaxesPaid(propertyTypeMasterVO.getPropertyTaxesPaid());
		propertyTypeMaster.setPropertyInsuranceProvider(propertyTypeMasterVO.getPropertyInsuranceProvider());
		propertyTypeMaster.setPropertyInsuranceCost(propertyTypeMasterVO.getPropertyInsuranceCost());
		propertyTypeMaster.setPropertyPurchaseYear(propertyTypeMasterVO.getPropertyPurchaseYear());
		propertyTypeMaster.setHomeWorthToday(propertyTypeMasterVO.getHomeWorthToday());
		
		
		return propertyTypeMaster;
		
	}
	


public User parseVOtoEntityUser(UserVO userVO){
	
	if(userVO == null)
		return null;
	User user = new User();
	
	
	
	user.setId(userVO.getId());
	user.setFirstName(userVO.getFirstName());
	user.setLastName(userVO.getLastName());
	user.setEmailId(userVO.getEmailId());
	user.setUsername(userVO.getEmailId());
	
	if(userVO.getCustomerDetail() == null)
		return user;
	
	user.setCustomerDetail(parseVOtoEntityCustomerDetails(userVO.getCustomerDetail()));
	
	return user;
	
}
	
	


public CustomerDetail parseVOtoEntityCustomerDetails(CustomerDetailVO customerDetailVO){
	
	if(customerDetailVO == null)
		return null;
	CustomerDetail customerDetail = new CustomerDetail();
	customerDetail.setAddressCity(customerDetailVO.getAddressCity());
	customerDetail.setAddressState(customerDetailVO.getAddressState());
	customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
	if(customerDetailVO.getDateOfBirth() !=null)
		customerDetail.setDateOfBirth(new Date(customerDetailVO.getDateOfBirth()));
	
	customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
	customerDetail.setSsn(customerDetailVO.getSsn());
System.out.println("customerDetail.setId(customerDetailVO.getId())"+customerDetailVO.getId());
	customerDetail.setId(customerDetailVO.getId());
	
	
	return customerDetail;
	
}

	

	public Boolean getPaySecondMortgage() {
		return paySecondMortgage;
	}

	public void setPaySecondMortgage(Boolean paySecondMortgage) {
		this.paySecondMortgage = paySecondMortgage;
	}

	public LoanAppFormVO convertFromEntity(LoanAppForm loanAppEntity) {

		LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
		//loanAppFormVO.setEmployed(loanAppEntity.getEmployed());
		loanAppFormVO.setHoaDues(loanAppEntity.getHoaDues());
		loanAppFormVO.setMaritalStatus(loanAppEntity.getMaritalStatus());
		loanAppFormVO.setReceiveAlimonyChildSupport(loanAppEntity
				.getReceiveAlimonyChildSupport());
		return loanAppFormVO;
	}

}