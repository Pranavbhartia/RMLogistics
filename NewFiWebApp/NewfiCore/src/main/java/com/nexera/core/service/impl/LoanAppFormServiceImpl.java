package com.nexera.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.GovernmentQuestion;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.GovernmentQuestionVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.PropertyTypeMasterVO;
import com.nexera.common.vo.RefinanceVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;

@Component
public class LoanAppFormServiceImpl implements LoanAppFormService {
	@Autowired
	private LoanAppFormDao loanAppFormDao;
	
	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LoanService loanService;
	
	@Override
	@Transactional
	public void save(LoanAppFormVO loaAppFormVO) {
		System.out.println("Inside 5");
		loanAppFormDao.saveOrUpdate(loaAppFormVO.convertToEntity());
	}

	/*@Override
	@Transactional
	public void create(LoanAppFormVO loaAppFormVO) {
		System.out.println("Inside 6");
		loanAppFormDao.save(loaAppFormVO.convertToEntity());

	}*/
	
	@Override
	@Transactional
	public LoanAppForm create(LoanAppFormVO loaAppFormVO) {
		LoanAppForm loanAppForm = loanAppFormDao.saveLoanAppFormWithDetails(loaAppFormVO.convertToEntity());
		return loanAppForm;
		/*LoanAppForm loanAppForm = null;
		if (loanAppFormID != null && loanAppFormID > 0)
			loanAppForm = loanAppFormDao.findLoanAppForm(loanAppFormID);
*/
		//return this.buildLoanAppFormVO(loanAppForm);
	}

	
	@Override
	@Transactional
	public LoanAppFormVO find(LoanAppFormVO loaAppFormVO) {
		
		
		LoanAppForm loanAppForm = loanAppFormDao.find(parseLoanAppFormVO(loaAppFormVO));		
		
		LoanAppFormVO  loanAppFormVO = convertToLoanAppFormVO(loanAppForm);
				
		return loanAppFormVO;
		
	}
	
	 private LoanAppForm parseLoanAppFormVO(LoanAppFormVO loaAppFormVO){
		
		 if(loaAppFormVO == null)
			 return null;
		 
		 LoanAppForm loanAppForm = new LoanAppForm ();
		 loanAppForm.setUser(userProfileService.parseUserModel(loaAppFormVO.getUser()));
		 loanAppForm.setLoan(loanService.parseLoanModel(loaAppFormVO.getLoan()));
		 
		 return loanAppForm;
		 
	 }
	 
	 private LoanAppFormVO convertToLoanAppFormVO(LoanAppForm loanAppForm){
			
		 if(loanAppForm == null)
			 return null;
		 
		 LoanAppFormVO loanAppFormVO = convertTOLoanAppFormVOCore(loanAppForm);
		 
		 
		 loanAppFormVO.setUser(userProfileService.convertTOUserVO(loanAppForm.getUser()));
		 loanAppFormVO.setLoan(loanService.convertIntoLoanVO(loanAppForm.getLoan()));
		 loanAppFormVO.setPropertyTypeMaster(convertTOPropertyTypeMasterVO(loanAppForm.getPropertyTypeMaster()));
		 
		 loanAppFormVO.setGovernmentquestion(convertTOGovernmentquestionVO(loanAppForm.getGovernmentquestion()));
		 loanAppFormVO.setRefinancedetails(convertTORefinancedetailsVO(loanAppForm.getRefinancedetails()));
		 loanAppFormVO.setLoanType(convertTOLoanTypeVO(loanAppForm.getLoanTypeMaster()));
		 
		 return loanAppFormVO;
		 
	 }


	private LoanAppFormVO convertTOLoanAppFormVOCore(LoanAppForm loanAppForm){
		 
		 
		 LoanAppFormVO loanAppFormVO = new  LoanAppFormVO ();
		 
		 loanAppFormVO.setId(loanAppForm.getId());
		 loanAppFormVO.setIsEmployed(loanAppForm.getIsEmployed());
		 loanAppFormVO.setEmployedIncomePreTax(loanAppForm.getEmployedIncomePreTax());
		 loanAppFormVO.setEmployedAt(loanAppForm.getEmployedAt());
		 loanAppFormVO.setEmployedSince(loanAppForm.getEmployedSince());
		 loanAppFormVO.setHoaDues(loanAppForm.getHoaDues());
		 loanAppFormVO.setHomeRecentlySold(loanAppForm.getHomeRecentlySold());
		 loanAppFormVO.setHomeToSell(loanAppForm.getHomeToSell());
		 loanAppFormVO.setMaritalStatus(loanAppForm.getMaritalStatus());
		 loanAppFormVO.setOwnsOtherProperty(loanAppForm.getOwnsOtherProperty());
		 loanAppFormVO.setIspensionOrRetirement(loanAppForm.getIspensionOrRetirement());
		 loanAppFormVO.setMonthlyPension(loanAppForm.getMonthlyPension());
		 loanAppFormVO.setReceiveAlimonyChildSupport(loanAppForm.getReceiveAlimonyChildSupport());
		 loanAppFormVO.setRentedOtherProperty(loanAppForm.getRentedOtherProperty());
		 loanAppFormVO.setSecondMortgage(loanAppForm.getSecondMortgage());
		 loanAppFormVO.setIsselfEmployed(loanAppForm.getIsselfEmployed());
		 loanAppFormVO.setSelfEmployedIncome(loanAppForm.getSelfEmployedIncome());
		 loanAppFormVO.setIsssIncomeOrDisability(loanAppForm.getIsssIncomeOrDisability());
		 loanAppFormVO.setSsDisabilityIncome(loanAppForm.getSsDisabilityIncome());
		 loanAppFormVO.setIsSpouseOnLoan(loanAppForm.getIsSpouseOnLoan());
		 loanAppFormVO.setSpouseName(loanAppForm.getSpouseName());
		 loanAppFormVO.setPaySecondMortgage(loanAppForm.getPaySecondMortgage());
		 loanAppFormVO.setLoanAppFormCompletionStatus(loanAppForm.getLoanAppFormCompletionStatus());
		 
		 return loanAppFormVO;
		 
	 }

	 
	 private PropertyTypeMasterVO convertTOPropertyTypeMasterVO(PropertyTypeMaster propertyTypeMaster){
		 
		 PropertyTypeMasterVO propertyTypeMasterVO = new PropertyTypeMasterVO();
		 propertyTypeMasterVO.setId(propertyTypeMaster.getId());
		 propertyTypeMasterVO.setDescription(propertyTypeMaster.getDescription());
		 propertyTypeMasterVO.setModifiedDate(propertyTypeMaster.getModifiedDate());
		 propertyTypeMasterVO.setPropertyTypeCd(propertyTypeMaster.getPropertyTypeCd());
		 propertyTypeMasterVO.setResidenceTypeCd(propertyTypeMaster.getResidenceTypeCd());
		 propertyTypeMasterVO.setPropertyTaxesPaid(propertyTypeMaster.getPropertyInsuranceProvider());
		 propertyTypeMasterVO.setPropertyInsuranceProvider(propertyTypeMaster.getPropertyInsuranceProvider());
		 propertyTypeMasterVO.setPropertyInsuranceCost(propertyTypeMaster.getPropertyPurchaseYear());
		 propertyTypeMasterVO.setPropertyPurchaseYear(propertyTypeMaster.getPropertyPurchaseYear());
		 propertyTypeMasterVO.setHomeWorthToday(propertyTypeMaster.getHomeWorthToday());
		 
		 return propertyTypeMasterVO;
		 
	 }

	 
	 private GovernmentQuestionVO convertTOGovernmentquestionVO(GovernmentQuestion governmentquestion) {
	  
		 GovernmentQuestionVO governmentQuestionVO = new GovernmentQuestionVO();
		 
		 governmentQuestionVO.setId(governmentquestion.getId());
		 governmentQuestionVO.setOutstandingJudgments(governmentquestion.isOutstandingJudgments());
		 governmentQuestionVO.setBankrupt(governmentquestion.isBankrupt());
		 governmentQuestionVO.setPropertyForeclosed(governmentquestion.isPropertyForeclosed());
		 governmentQuestionVO.setLawsuit(governmentquestion.isLawsuit());
		 governmentQuestionVO.setObligatedLoan(governmentquestion.isObligatedLoan());
		 governmentQuestionVO.setFederalDebt(governmentquestion.isFederalDebt());
		 governmentQuestionVO.setEndorser(governmentquestion.isEndorser());
		 governmentQuestionVO.setUSCitizen(governmentquestion.isUSCitizen());
		 governmentQuestionVO.setOccupyPrimaryResidence(governmentquestion.isOccupyPrimaryResidence());
		 governmentQuestionVO.setOwnershipInterestInProperty(governmentquestion.isOwnershipInterestInProperty());
		 governmentQuestionVO.setEthnicity(governmentquestion.getEthnicity());
		 governmentQuestionVO.setRace(governmentquestion.getRace());
		 governmentQuestionVO.setSex(governmentquestion.getSex());
		 	 
	    return governmentQuestionVO;
    }

      
	 private RefinanceVO convertTORefinancedetailsVO( RefinanceDetails refinancedetails) {
		   
		 if (refinancedetails == null)
			 return null;
		 RefinanceVO refinanceVO = new RefinanceVO();
		 refinanceVO.setId(refinancedetails.getId());
		 refinanceVO.setRefinanceOption(refinancedetails.getRefinanceOption());
		 refinanceVO.setCurrentMortgageBalance(refinancedetails.getCurrentMortgageBalance());
		 refinanceVO.setCurrentMortgagePayment(refinancedetails.getCurrentMortgagePayment());
		 refinanceVO.setIncludeTaxes(refinancedetails.isIncludeTaxes());
		  
		 return refinanceVO;
	    }

	 

		private LoanTypeMasterVO convertTOLoanTypeVO(LoanTypeMaster loanTypeMaster) {
		    
			if (loanTypeMaster == null)
				return null;
			LoanTypeMasterVO loanTypeMasterVO = new LoanTypeMasterVO();
			loanTypeMasterVO.setId(loanTypeMaster.getId());
			loanTypeMasterVO.setDescription(loanTypeMaster.getDescription());
			loanTypeMasterVO.setLoanTypeCd(loanTypeMaster.getLoanTypeCd());
			loanTypeMasterVO.setModifiedDate(loanTypeMaster.getModifiedDate());
			
		    return loanTypeMasterVO;
	    }

	@Override
	@Transactional
	public LoanAppForm findByLoan(Loan loan){
		return loanAppFormDao.findByLoan(loan);
	}

	@Override
	@Transactional
	public LoanAppForm findByuserID(int userid) {
		return loanAppFormDao.findByuserID(userid);
	}

}


