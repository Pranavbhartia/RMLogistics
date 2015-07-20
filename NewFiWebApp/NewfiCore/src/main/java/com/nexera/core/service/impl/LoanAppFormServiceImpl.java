package com.nexera.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerBankAccountDetails;
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
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.PurchaseDetails;
import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.SpouseGovernmentQuestions;
import com.nexera.common.entity.User;
import com.nexera.common.entity.ZipCodeLookup;
import com.nexera.common.vo.CustomerBankAccountDetailsVO;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerRetirementAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseBankAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseDetailVO;
import com.nexera.common.vo.CustomerSpouseEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseRetirementAccountDetailsVO;
import com.nexera.common.vo.GovernmentQuestionVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.PropertyTypeMasterVO;
import com.nexera.common.vo.PurchaseDetailsVO;
import com.nexera.common.vo.RefinanceVO;
import com.nexera.common.vo.SpouseGovernmentQuestionVO;
import com.nexera.common.vo.ZipCodeLookupVO;
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

	@Autowired
	private UserProfileDao userProfileDao;
	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanAppFormServiceImpl.class);

	@Override
	@Transactional
	public void save(LoanAppFormVO loaAppFormVO) {
		loanAppFormDao.saveOrUpdate(loaAppFormVO.convertToEntity());
	}

	/*
	 * @Override
	 * 
	 * @Transactional public void create(LoanAppFormVO loaAppFormVO) {
	 * System.out.println("Inside 6");
	 * loanAppFormDao.save(loaAppFormVO.convertToEntity());
	 * 
	 * }
	 */

	@Override
	@Transactional
	public LoanAppForm create(LoanAppFormVO loaAppFormVO) {
		LOG.info("in create func of loanapp form..................");
		if (null != loaAppFormVO.getPropertyTypeMaster().getHomeZipCode()
		        && loaAppFormVO.getPropertyTypeMaster().getHomeZipCode() != "") {

			zipcodeLookup(loaAppFormVO);
		}

		LoanAppForm loanAppForm = loanAppFormDao
		        .saveLoanAppFormWithDetails(loaAppFormVO.convertToEntity());
		// updating the user table coloum phone number
		userProfileDao.UpdateUserProfile(loaAppFormVO.getUser()
		        .getPhoneNumber(), loaAppFormVO.getUser().getId());

		return loanAppForm;
		/*
		 * LoanAppForm loanAppForm = null; if (loanAppFormID != null &&
		 * loanAppFormID > 0) loanAppForm =
		 * loanAppFormDao.findLoanAppForm(loanAppFormID);
		 */
		// return this.buildLoanAppFormVO(loanAppForm);
	}

	public void zipcodeLookup(LoanAppFormVO loanAppFormVO) {
		LOG.info("Inside method zipcodeLookup");
		try {
			ZipCodeLookupVO zipCodeLookup = ZipCodeLookup
			        .converToVo(findByZipCode(loanAppFormVO
			                .getPropertyTypeMaster().getHomeZipCode()));
			PropertyTypeMasterVO propertyTypeMasterVO = loanAppFormVO
			        .getPropertyTypeMaster();
			propertyTypeMasterVO.setPropState(zipCodeLookup.getStateLookup()
			        .getStateCode());
			propertyTypeMasterVO.setPropCity(zipCodeLookup.getCityName());
			loanAppFormVO.setPropertyTypeMaster(propertyTypeMasterVO);

		} catch (Exception e) {
			LOG.error("Error in zipcodeLookup ", e);

		}

	}

	@Override
	@Transactional
	public LoanAppFormVO find(LoanAppFormVO loaAppFormVO) {

		LoanAppForm loanAppForm = loanAppFormDao
		        .find(parseLoanAppFormVO(loaAppFormVO));

		LoanAppFormVO loanAppFormVO = convertToLoanAppFormVO(loanAppForm);

		return loanAppFormVO;

	}

	private LoanAppForm parseLoanAppFormVO(LoanAppFormVO loaAppFormVO) {

		if (loaAppFormVO == null)
			return null;

		LoanAppForm loanAppForm = new LoanAppForm();
		loanAppForm.setUser(User.convertFromVOToEntity(loaAppFormVO.getUser()));
		loanAppForm.setLoan(loanService.parseLoanModel(loaAppFormVO.getLoan()));

		return loanAppForm;

	}

	private LoanAppFormVO convertToLoanAppFormVO(LoanAppForm loanAppForm) {

		if (loanAppForm == null)
			return null;

		LoanAppFormVO loanAppFormVO = convertTOLoanAppFormVOCore(loanAppForm);

		loanAppFormVO
		        .setUser(User.convertFromEntityToVO(loanAppForm.getUser()));
		loanAppFormVO
		        .setLoan(Loan.convertFromEntityToVO(loanAppForm.getLoan()));
		loanAppFormVO
		        .setPropertyTypeMaster(convertTOPropertyTypeMasterVO(loanAppForm
		                .getPropertyTypeMaster()));
		loanAppFormVO.setNotApplicable(loanAppForm.getNotApplicable());
		loanAppFormVO
		        .setGovernmentquestion(convertTOGovernmentquestionVO(loanAppForm
		                .getGovernmentquestion()));

		loanAppFormVO
		        .setSpouseGovernmentQuestions(convertTOSpouseGovernmentquestionVO(loanAppForm
		                .getSpouseGovernmentQuestions()));

		loanAppFormVO
		        .setRefinancedetails(convertTORefinancedetailsVO(loanAppForm
		                .getRefinancedetails()));
		loanAppFormVO.setLoanType(convertTOLoanTypeVO(loanAppForm
		        .getLoanTypeMaster()));
		loanAppFormVO.setPurchaseDetails(convertTOPurchaseDetails(loanAppForm
		        .getPurchaseDetails()));

		// Customer details Start
		loanAppFormVO
		        .setCustomerEmploymentIncome(convertTOCustomerEmploymentIncomeList(loanAppForm
		                .getCustomerEmploymentIncome()));
		loanAppFormVO
		        .setCustomerBankAccountDetails(convertTOCustomerBankAccountDetails(loanAppForm
		                .getCustomerBankAccountDetails()));
		loanAppFormVO
		        .setCustomerRetirementAccountDetails(convertTOCustomerRetirementAccountDetails(loanAppForm
		                .getCustomerRetirementAccountDetails()));
		loanAppFormVO
		        .setCustomerOtherAccountDetails(convertTOCustomerOtherAccountDetails(loanAppForm
		                .getCustomerOtherAccountDetails()));
		// Ends

		// spouse Bank detials setting in VO Start

		loanAppFormVO
		        .setCustomerSpouseDetail(convertTOCustomerSpouseDetail(loanAppForm
		                .getCustomerspousedetail()));
		loanAppFormVO
		        .setCustomerSpouseEmploymentIncome(convertTOCustomerSpouseEmploymentIncomeList(loanAppForm
		                .getCustomerSpouseEmploymentIncome()));
		loanAppFormVO
		        .setCustomerSpouseBankAccountDetails(convertTOSpouseBankAccountList(loanAppForm
		                .getCustomerSpouseBankAccountDetails()));
		loanAppFormVO
		        .setCustomerSpouseRetirementAccountDetails(convertTOSpouseRetirementAccountList(loanAppForm
		                .getCustomerSpouseRetirementAccountDetails()));
		loanAppFormVO
		        .setCustomerSpouseOtherAccountDetails(convertTOSpouseOtherAccountList(loanAppForm
		                .getCustomerSpouseOtherAccountDetails()));

		// Ends

		return loanAppFormVO;

	}

	private List<CustomerOtherAccountDetailsVO> convertTOCustomerOtherAccountDetails(
	        List<CustomerOtherAccountDetails> customerOtherAccountDetailslist) {

		if (customerOtherAccountDetailslist == null
		        || customerOtherAccountDetailslist.size() == 0)
			return null;

		List<CustomerOtherAccountDetailsVO> customerOtherAccountDetailsVOList = new ArrayList<CustomerOtherAccountDetailsVO>();

		CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO = null;
		CustomerOtherAccountDetails customerOtherAccountDetails = null;
		for (int i = 0; i < customerOtherAccountDetailslist.size(); i++) {

			customerOtherAccountDetails = customerOtherAccountDetailslist
			        .get(i);

			customerOtherAccountDetailsVO = convertTOCustomerBankAccountDetailsVO(customerOtherAccountDetails);
			customerOtherAccountDetailsVOList
			        .add(customerOtherAccountDetailsVO);
		}

		return customerOtherAccountDetailsVOList;
	}

	private CustomerOtherAccountDetailsVO convertTOCustomerBankAccountDetailsVO(
	        CustomerOtherAccountDetails customerOtherAccountDetails) {

		if (null == customerOtherAccountDetails)
			return null;
		CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO = new CustomerOtherAccountDetailsVO();
		customerOtherAccountDetailsVO
		        .setId(customerOtherAccountDetails.getId());
		customerOtherAccountDetailsVO
		        .setAccountSubType(customerOtherAccountDetails
		                .getAccountSubType());
		customerOtherAccountDetailsVO
		        .setAmountForNewHome(customerOtherAccountDetails
		                .getAmountfornewhome());
		customerOtherAccountDetailsVO
		        .setCurrentAccountBalance(customerOtherAccountDetails
		                .getCurrentaccountbalance());

		CustomerOtherAccountDetailsVO customerOtherAccountDetailsVOTemp = new CustomerOtherAccountDetailsVO();
		customerOtherAccountDetailsVOTemp
		        .setCustomerOtherAccountDetails(customerOtherAccountDetailsVO);

		return customerOtherAccountDetailsVOTemp;
	}

	private List<CustomerRetirementAccountDetailsVO> convertTOCustomerRetirementAccountDetails(
	        List<CustomerRetirementAccountDetails> customerRetirementAccountDetailsList) {

		if (customerRetirementAccountDetailsList == null
		        || customerRetirementAccountDetailsList.size() == 0)
			return null;

		List<CustomerRetirementAccountDetailsVO> customerRetirementAccountDetailsVOList = new ArrayList<CustomerRetirementAccountDetailsVO>();

		CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO = null;
		CustomerRetirementAccountDetails customerRetirementAccountDetails = null;
		for (int i = 0; i < customerRetirementAccountDetailsList.size(); i++) {

			customerRetirementAccountDetails = customerRetirementAccountDetailsList
			        .get(i);

			customerRetirementAccountDetailsVO = convertTOCustomerBankAccountDetailsVO(customerRetirementAccountDetails);
			customerRetirementAccountDetailsVOList
			        .add(customerRetirementAccountDetailsVO);
		}

		return customerRetirementAccountDetailsVOList;
	}

	private CustomerRetirementAccountDetailsVO convertTOCustomerBankAccountDetailsVO(
	        CustomerRetirementAccountDetails customerRetirementAccountDetails) {

		if (null == customerRetirementAccountDetails)
			return null;
		CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO = new CustomerRetirementAccountDetailsVO();

		customerRetirementAccountDetailsVO
		        .setId(customerRetirementAccountDetails.getId());
		customerRetirementAccountDetailsVO
		        .setAccountSubType(customerRetirementAccountDetails
		                .getAccountSubType());
		customerRetirementAccountDetailsVO
		        .setAmountForNewHome(customerRetirementAccountDetails
		                .getAmountfornewhome());
		customerRetirementAccountDetailsVO
		        .setCurrentAccountBalance(customerRetirementAccountDetails
		                .getCurrentaccountbalance());

		CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsTemp = new CustomerRetirementAccountDetailsVO();
		customerRetirementAccountDetailsTemp
		        .setCustomerRetirementAccountDetails(customerRetirementAccountDetailsVO);

		return customerRetirementAccountDetailsTemp;
	}

	private List<CustomerBankAccountDetailsVO> convertTOCustomerBankAccountDetails(
	        List<CustomerBankAccountDetails> customerBankAccountDetailsList) {

		if (customerBankAccountDetailsList == null
		        || customerBankAccountDetailsList.size() == 0)
			return null;

		List<CustomerBankAccountDetailsVO> customerBankAccountDetailsVOList = new ArrayList<CustomerBankAccountDetailsVO>();

		CustomerBankAccountDetailsVO customerBankAccountDetailsVO = null;
		CustomerBankAccountDetails customerBankAccountDetails = null;
		for (int i = 0; i < customerBankAccountDetailsList.size(); i++) {

			customerBankAccountDetails = customerBankAccountDetailsList.get(i);

			customerBankAccountDetailsVO = convertTOCustomerBankAccountDetailsVO(customerBankAccountDetails);
			customerBankAccountDetailsVOList.add(customerBankAccountDetailsVO);
		}

		return customerBankAccountDetailsVOList;
	}

	private CustomerBankAccountDetailsVO convertTOCustomerBankAccountDetailsVO(
	        CustomerBankAccountDetails customerBankAccountDetails) {

		if (null == customerBankAccountDetails)
			return null;
		CustomerBankAccountDetailsVO customerBankAccountDetailsVO = new CustomerBankAccountDetailsVO();
		customerBankAccountDetailsVO.setId(customerBankAccountDetails.getId());
		customerBankAccountDetailsVO
		        .setAccountSubType(customerBankAccountDetails
		                .getAccountSubType());
		customerBankAccountDetailsVO
		        .setAmountForNewHome(customerBankAccountDetails
		                .getAmountfornewhome());
		customerBankAccountDetailsVO
		        .setCurrentAccountBalance(customerBankAccountDetails
		                .getCurrentaccountbalance());

		CustomerBankAccountDetailsVO customerBankAccountDetailsVOTemp = new CustomerBankAccountDetailsVO();
		customerBankAccountDetailsVOTemp
		        .setCustomerBankAccountDetails(customerBankAccountDetailsVO);

		return customerBankAccountDetailsVOTemp;
	}

	private List<CustomerSpouseOtherAccountDetailsVO> convertTOSpouseOtherAccountList(
	        List<CustomerSpouseOtherAccountDetails> spouseOtherAccountDetailsList) {

		if (spouseOtherAccountDetailsList == null
		        || spouseOtherAccountDetailsList.size() == 0)
			return null;

		List<CustomerSpouseOtherAccountDetailsVO> spouseRetirementAccountDetailsVOList = new ArrayList<CustomerSpouseOtherAccountDetailsVO>();

		CustomerSpouseOtherAccountDetailsVO spouseOtherAccountDetailsVO = null;
		CustomerSpouseOtherAccountDetails spouseOtherAccountDetails = null;
		for (int i = 0; i < spouseOtherAccountDetailsList.size(); i++) {

			spouseOtherAccountDetails = spouseOtherAccountDetailsList.get(i);

			spouseOtherAccountDetailsVO = convertTOSpouseOtherAccountDetailsVO(spouseOtherAccountDetails);
			spouseRetirementAccountDetailsVOList
			        .add(spouseOtherAccountDetailsVO);
		}

		return spouseRetirementAccountDetailsVOList;
	}

	private CustomerSpouseOtherAccountDetailsVO convertTOSpouseOtherAccountDetailsVO(
	        CustomerSpouseOtherAccountDetails spouseOtherAccountDetails) {

		if (null == spouseOtherAccountDetails)
			return null;

		CustomerSpouseOtherAccountDetailsVO spouseOtherAccountDetailsVO = new CustomerSpouseOtherAccountDetailsVO();

		spouseOtherAccountDetailsVO.setId(spouseOtherAccountDetails.getId());
		spouseOtherAccountDetailsVO.setAccountSubType(spouseOtherAccountDetails
		        .getAccountSubType());
		spouseOtherAccountDetailsVO
		        .setAmountForNewHome(spouseOtherAccountDetails
		                .getAmountfornewhome());
		spouseOtherAccountDetailsVO
		        .setCurrentAccountBalance(spouseOtherAccountDetails
		                .getCurrentaccountbalance());

		CustomerSpouseOtherAccountDetailsVO spouseOtherAccountDetailsVOTemp = new CustomerSpouseOtherAccountDetailsVO();
		spouseOtherAccountDetailsVOTemp
		        .setCustomerSpouseOtherAccountDetails(spouseOtherAccountDetailsVO);

		return spouseOtherAccountDetailsVOTemp;
	}

	private List<CustomerSpouseRetirementAccountDetailsVO> convertTOSpouseRetirementAccountList(
	        List<CustomerSpouseRetirementAccountDetails> spouseRetirementAccountDetailsList) {

		if (spouseRetirementAccountDetailsList == null
		        || spouseRetirementAccountDetailsList.size() == 0)
			return null;

		List<CustomerSpouseRetirementAccountDetailsVO> spouseRetirementAccountDetailsVOList = new ArrayList<CustomerSpouseRetirementAccountDetailsVO>();

		CustomerSpouseRetirementAccountDetailsVO customerSpouseBankAccountDetailsVO = null;
		CustomerSpouseRetirementAccountDetails spouseRetirementAccountDetails = null;
		for (int i = 0; i < spouseRetirementAccountDetailsList.size(); i++) {

			spouseRetirementAccountDetails = spouseRetirementAccountDetailsList
			        .get(i);

			customerSpouseBankAccountDetailsVO = convertTOSpouseRetirementAccountVO(spouseRetirementAccountDetails);
			spouseRetirementAccountDetailsVOList
			        .add(customerSpouseBankAccountDetailsVO);
		}

		return spouseRetirementAccountDetailsVOList;
	}

	private CustomerSpouseRetirementAccountDetailsVO convertTOSpouseRetirementAccountVO(
	        CustomerSpouseRetirementAccountDetails spouseRetirementAccountDetails) {

		if (null == spouseRetirementAccountDetails)
			return null;

		CustomerSpouseRetirementAccountDetailsVO spouseRetirementAccountDetailsVO = new CustomerSpouseRetirementAccountDetailsVO();

		spouseRetirementAccountDetailsVO.setId(spouseRetirementAccountDetails
		        .getId());
		spouseRetirementAccountDetailsVO
		        .setAccountSubType(spouseRetirementAccountDetails
		                .getAccountSubType());
		spouseRetirementAccountDetailsVO
		        .setAmountForNewHome(spouseRetirementAccountDetails
		                .getAmountfornewhome());
		spouseRetirementAccountDetailsVO
		        .setCurrentAccountBalance(spouseRetirementAccountDetails
		                .getCurrentaccountbalance());

		CustomerSpouseRetirementAccountDetailsVO spouseRetirementAccountDetailsVOTemp = new CustomerSpouseRetirementAccountDetailsVO();
		spouseRetirementAccountDetailsVOTemp
		        .setCustomerSpouseRetirementAccountDetails(spouseRetirementAccountDetailsVO);

		return spouseRetirementAccountDetailsVOTemp;
	}

	private List<CustomerSpouseBankAccountDetailsVO> convertTOSpouseBankAccountList(
	        List<CustomerSpouseBankAccountDetails> customerSpouseBankAccountDetailslist) {

		if (customerSpouseBankAccountDetailslist == null
		        || customerSpouseBankAccountDetailslist.size() == 0)
			return null;

		List<CustomerSpouseBankAccountDetailsVO> customerSpouseBankAccountDetailsVOList = new ArrayList<CustomerSpouseBankAccountDetailsVO>();
		CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO = null;
		CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails = null;
		for (int i = 0; i < customerSpouseBankAccountDetailslist.size(); i++) {

			customerSpouseBankAccountDetails = customerSpouseBankAccountDetailslist
			        .get(i);

			customerSpouseBankAccountDetailsVO = convertTOCustomerEmploymentIncomeVO(customerSpouseBankAccountDetails);
			customerSpouseBankAccountDetailsVOList
			        .add(customerSpouseBankAccountDetailsVO);
		}

		return customerSpouseBankAccountDetailsVOList;
	}

	private CustomerSpouseBankAccountDetailsVO convertTOCustomerEmploymentIncomeVO(
	        CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails) {

		if (null == customerSpouseBankAccountDetails)
			return null;

		CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO = new CustomerSpouseBankAccountDetailsVO();
		customerSpouseBankAccountDetailsVO
		        .setId(customerSpouseBankAccountDetails.getId());
		customerSpouseBankAccountDetailsVO
		        .setAccountSubType(customerSpouseBankAccountDetails
		                .getAccountSubType());
		customerSpouseBankAccountDetailsVO
		        .setAmountForNewHome(customerSpouseBankAccountDetails
		                .getAmountfornewhome());
		customerSpouseBankAccountDetailsVO
		        .setCurrentAccountBalance(customerSpouseBankAccountDetails
		                .getCurrentaccountbalance());

		CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVOTemp = new CustomerSpouseBankAccountDetailsVO();
		customerSpouseBankAccountDetailsVOTemp
		        .setCustomerSpouseBankAccountDetails(customerSpouseBankAccountDetailsVO);

		return customerSpouseBankAccountDetailsVOTemp;
	}

	private CustomerSpouseDetailVO convertTOCustomerSpouseDetail(
	        CustomerSpouseDetail customerspousedetail) {
		if (null == customerspousedetail)
			return null;
		CustomerSpouseDetailVO customerSpouseDetailVO = new CustomerSpouseDetailVO();
		customerSpouseDetailVO.setId(customerspousedetail.getId());

		if (null != customerspousedetail.getSpouseDateOfBirth()) {

			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			customerSpouseDetailVO.setSpouseDateOfBirth(df
			        .format(customerspousedetail.getSpouseDateOfBirth()));
		}
		customerSpouseDetailVO.setNotApplicable(customerspousedetail
		        .getNotApplicable());
		customerSpouseDetailVO
		        .setSpouseSsn(customerspousedetail.getSpouseSsn());
		customerSpouseDetailVO.setSpouseSecPhoneNumber(customerspousedetail
		        .getSpouseSecPhoneNumber());
		customerSpouseDetailVO.setSpouseName(customerspousedetail
		        .getSpouseName());
		customerSpouseDetailVO.setSelfEmployed(customerspousedetail
		        .isSelfEmployed());
		customerSpouseDetailVO.setIsssIncomeOrDisability(customerspousedetail
		        .isIsssIncomeOrDisability());
		customerSpouseDetailVO.setIspensionOrRetirement(customerspousedetail
		        .isIs_pension_or_retirement());

		customerSpouseDetailVO.setSpouseLastName(customerspousedetail
		        .getSpouseLastName());
		customerSpouseDetailVO.setStreetAddress(customerspousedetail
		        .getStreetAddress());
		customerSpouseDetailVO.setState(customerspousedetail.getState());
		customerSpouseDetailVO.setCity(customerspousedetail.getCity());
		customerSpouseDetailVO.setZip(customerspousedetail.getZip());

		// income : start
		if (null != customerspousedetail.getSelfEmployedIncome())
			customerSpouseDetailVO.setSelfEmployedIncome(customerspousedetail
			        .getSelfEmployedIncome().toString());

		if (null != customerspousedetail.getSelfEmployedNoYear())
			customerSpouseDetailVO.setSelfEmployedNoYear(customerspousedetail
			        .getSelfEmployedNoYear().toString());

		if (null != customerspousedetail.getChildSupportAlimony())
			customerSpouseDetailVO.setChildSupportAlimony(customerspousedetail
			        .getChildSupportAlimony().toString());

		if (null != customerspousedetail.getSocialSecurityIncome())
			customerSpouseDetailVO.setSocialSecurityIncome(customerspousedetail
			        .getSocialSecurityIncome().toString());

		if (null != customerspousedetail.getDisabilityIncome())
			customerSpouseDetailVO.setDisabilityIncome(customerspousedetail
			        .getDisabilityIncome().toString());

		if (null != customerspousedetail.getMonthlyPension())
			customerSpouseDetailVO.setMonthlyPension(customerspousedetail
			        .getMonthlyPension().toString());

		if (null != customerspousedetail.getRetirementIncome())
			customerSpouseDetailVO.setRetirementIncome(customerspousedetail
			        .getRetirementIncome().toString());

		// income ends:
		customerSpouseDetailVO.setExperianScore(customerspousedetail
		        .getExperianScore());
		customerSpouseDetailVO.setEquifaxScore(customerspousedetail
		        .getEquifaxScore());
		customerSpouseDetailVO.setTransunionScore(customerspousedetail
		        .getTransunionScore());
		customerSpouseDetailVO.setCurrentHomePrice(customerspousedetail
		        .getCurrentHomePrice());
		customerSpouseDetailVO
		        .setCurrentHomeMortgageBalance(customerspousedetail
		                .getCurrentHomeMortgageBalance());
		customerSpouseDetailVO.setNewHomeBudgetFromsale(customerspousedetail
		        .getNewHomeBudgetFromsale());

		// skip my assest
		customerSpouseDetailVO.setSkipMyAssets(customerspousedetail
		        .getSkipMyAssets());

		return customerSpouseDetailVO;
	}

	private List<CustomerSpouseEmploymentIncomeVO> convertTOCustomerSpouseEmploymentIncomeList(
	        List<CustomerSpouseEmploymentIncome> customerSpouseEmploymentIncomelist) {

		if (customerSpouseEmploymentIncomelist == null
		        || customerSpouseEmploymentIncomelist.size() == 0)
			return null;

		List<CustomerSpouseEmploymentIncomeVO> customerSpouseEmploymentIncomeVOlist = new ArrayList<CustomerSpouseEmploymentIncomeVO>();

		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVO = null;
		CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome = null;
		for (int i = 0; i < customerSpouseEmploymentIncomelist.size(); i++) {

			customerSpouseEmploymentIncome = customerSpouseEmploymentIncomelist
			        .get(i);

			customerSpouseEmploymentIncomeVO = convertTOCustomerSpouseEmploymentIncomeVO(customerSpouseEmploymentIncome);
			customerSpouseEmploymentIncomeVOlist
			        .add(customerSpouseEmploymentIncomeVO);
		}

		return customerSpouseEmploymentIncomeVOlist;
	}

	private CustomerSpouseEmploymentIncomeVO convertTOCustomerSpouseEmploymentIncomeVO(
	        CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome) {

		if (null == customerSpouseEmploymentIncome)
			return null;

		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVO = new CustomerSpouseEmploymentIncomeVO();
		CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentIncomeVOTemp = new CustomerSpouseEmploymentIncomeVO();

		customerSpouseEmploymentIncomeVOTemp
		        .setId(customerSpouseEmploymentIncome.getId());
		customerSpouseEmploymentIncomeVOTemp
		        .setJobTitle(customerSpouseEmploymentIncome.getJobTitle());
		customerSpouseEmploymentIncomeVOTemp
		        .setEmployedSince(customerSpouseEmploymentIncome
		                .getEmployedSince());
		customerSpouseEmploymentIncomeVOTemp
		        .setEmployedAt(customerSpouseEmploymentIncome.getEmployedAt());
		customerSpouseEmploymentIncomeVOTemp
		        .setEmployedIncomePreTax(customerSpouseEmploymentIncome
		                .getEmployedIncomePreTax());

		customerSpouseEmploymentIncomeVO
		        .setCustomerSpouseEmploymentIncome(customerSpouseEmploymentIncomeVOTemp);
		return customerSpouseEmploymentIncomeVO;
	}

	private List<CustomerEmploymentIncomeVO> convertTOCustomerEmploymentIncomeList(
	        List<CustomerEmploymentIncome> customerEmploymentIncomelist) {

		if (customerEmploymentIncomelist == null
		        || customerEmploymentIncomelist.size() == 0)
			return null;

		List<CustomerEmploymentIncomeVO> customerEmploymentIncomeVOlist = new ArrayList<CustomerEmploymentIncomeVO>();
		CustomerEmploymentIncomeVO customerEmploymentIncomeVO = null;
		CustomerEmploymentIncome customerEmploymentIncome = null;
		for (int i = 0; i < customerEmploymentIncomelist.size(); i++) {

			customerEmploymentIncome = customerEmploymentIncomelist.get(i);

			customerEmploymentIncomeVO = convertTOCustomerEmploymentIncomeVO(customerEmploymentIncome);
			customerEmploymentIncomeVOlist.add(customerEmploymentIncomeVO);
		}

		return customerEmploymentIncomeVOlist;
	}

	private CustomerEmploymentIncomeVO convertTOCustomerEmploymentIncomeVO(
	        CustomerEmploymentIncome customerEmploymentIncome) {

		if (null == customerEmploymentIncome)
			return null;

		CustomerEmploymentIncomeVO customerEmploymentIncomeVO = new CustomerEmploymentIncomeVO();
		CustomerEmploymentIncomeVO customerEmploymentITemp = new CustomerEmploymentIncomeVO();

		customerEmploymentITemp.setId(customerEmploymentIncome.getId());
		customerEmploymentITemp.setJobTitle(customerEmploymentIncome
		        .getJobTitle());
		customerEmploymentITemp.setEmployedSince(customerEmploymentIncome
		        .getEmployedSince());
		customerEmploymentITemp.setEmploymentLength(String.valueOf(customerEmploymentIncome
		        .getEmploymentLength()));
		customerEmploymentITemp.setEmployedAt(customerEmploymentIncome
		        .getEmployedAt());
		customerEmploymentITemp
		        .setEmployedIncomePreTax(customerEmploymentIncome
		                .getEmployedIncomePreTax());
		customerEmploymentIncomeVO
		        .setCustomerEmploymentIncome(customerEmploymentITemp);
		return customerEmploymentIncomeVO;
	}

	private PurchaseDetailsVO convertTOPurchaseDetails(
	        PurchaseDetails purchaseDetails) {

		if (purchaseDetails == null) {
			return null;
		}
		PurchaseDetailsVO purchaseDetailsVO = new PurchaseDetailsVO();
		purchaseDetailsVO.setId(purchaseDetails.getId());
		purchaseDetailsVO.setBuyhomeZipPri(purchaseDetails.getBuyhomeZipPri());
		purchaseDetailsVO.setBuyhomeZipSec(purchaseDetails.getBuyhomeZipSec());
		purchaseDetailsVO.setBuyhomeZipTri(purchaseDetails.getBuyhomeZipTri());
		purchaseDetailsVO
		        .setEstimatedPrice(purchaseDetails.getEstimatedPrice());
		purchaseDetailsVO.setHousePrice(purchaseDetails.getHousePrice());
		purchaseDetailsVO.setLivingSituation(purchaseDetails
		        .getLivingSituation());
		purchaseDetailsVO.setLoanAmount(purchaseDetails.getLoanAmount());
		purchaseDetailsVO.setTaxAndInsuranceInLoanAmt(purchaseDetails
		        .isTaxAndInsuranceInLoanAmt());

		return purchaseDetailsVO;
	}

	private LoanAppFormVO convertTOLoanAppFormVOCore(LoanAppForm loanAppForm) {

		if (null == loanAppForm)
			return null;

		LoanAppFormVO loanAppFormVO = new LoanAppFormVO();

		loanAppFormVO.setId(loanAppForm.getId());
		loanAppFormVO.setIsEmployed(loanAppForm.getIsEmployed());
		loanAppFormVO.setEmployedIncomePreTax(loanAppForm
		        .getEmployedIncomePreTax());
		loanAppFormVO.setSsnProvided(loanAppForm.getSsnProvided());
		loanAppFormVO.setCbSsnProvided(loanAppForm.getCbSsnProvided());
		loanAppFormVO.setEmployedAt(loanAppForm.getEmployedAt());
		loanAppFormVO.setEmployedSince(loanAppForm.getEmployedSince());
		loanAppFormVO.setHoaDues(loanAppForm.getHoaDues());
		loanAppFormVO.setHomeRecentlySold(loanAppForm.getHomeRecentlySold());
		loanAppFormVO.setHomeToSell(loanAppForm.getHomeToSell());
		loanAppFormVO.setMaritalStatus(loanAppForm.getMaritalStatus());
		loanAppFormVO.setOwnsOtherProperty(loanAppForm.getOwnsOtherProperty());
		loanAppFormVO.setIspensionOrRetirement(loanAppForm
		        .getIspensionOrRetirement());
		loanAppFormVO.setMonthlyPension(loanAppForm.getMonthlyPension());
		loanAppFormVO.setReceiveAlimonyChildSupport(loanAppForm
		        .getReceiveAlimonyChildSupport());
		loanAppFormVO.setRentedOtherProperty(loanAppForm
		        .getRentedOtherProperty());
		loanAppFormVO.setSecondMortgage(loanAppForm.getSecondMortgage());
		loanAppFormVO.setIsselfEmployed(loanAppForm.getIsselfEmployed());
		loanAppFormVO
		        .setSelfEmployedIncome(loanAppForm.getSelfEmployedIncome());
		loanAppFormVO.setIsssIncomeOrDisability(loanAppForm
		        .getIsssIncomeOrDisability());
		loanAppFormVO
		        .setSsDisabilityIncome(loanAppForm.getSsDisabilityIncome());
		loanAppFormVO.setIsSpouseOnLoan(loanAppForm.getIsSpouseOnLoan());
		loanAppFormVO.setIsCoborrowerPresent(loanAppForm
		        .getIsCoborrowerPresent());
		loanAppFormVO.setSpouseName(loanAppForm.getSpouseName());
		loanAppFormVO.setPaySecondMortgage(loanAppForm.getPaySecondMortgage());
		loanAppFormVO.setLoanAppFormCompletionStatus(loanAppForm
		        .getLoanAppFormCompletionStatus());
		loanAppFormVO.setMonthlyRent(loanAppForm.getMonthlyRent());

		// Customer Income details: Start
		if (null != loanAppForm.getMonthlyIncome())
			loanAppFormVO.setSelfEmployedMonthlyIncome(loanAppForm
			        .getMonthlyIncome().toString());
		if (null != loanAppForm.getSelfEmployedNoYear())
			loanAppFormVO.setSelfEmployedNoYear(loanAppForm
			        .getSelfEmployedNoYear().toString());
		if (null != loanAppForm.getChildSupportAlimony())
			loanAppFormVO.setChildSupportAlimony(loanAppForm
			        .getChildSupportAlimony().toString());
		if (null != loanAppForm.getSocialSecurityIncome())
			loanAppFormVO.setSocialSecurityIncome(loanAppForm
			        .getSocialSecurityIncome().toString());
		if (null != loanAppForm.getSsDisabilityIncome())
			loanAppFormVO.setSsDisabilityIncome(loanAppForm
			        .getSsDisabilityIncome());
		if (null != loanAppForm.getMonthlyPension())
			loanAppFormVO.setMonthlyPension(loanAppForm.getMonthlyPension());
		if (null != loanAppForm.getRetirementIncome())
			loanAppFormVO.setRetirementIncome(loanAppForm.getRetirementIncome()
			        .toString());

		// Customer Income Detaisl : ends

		// skip my assets: Start
		loanAppFormVO.setSkipMyAssets(loanAppForm.getSkipMyAssets());

		// skip my assets : Ends

		return loanAppFormVO;

	}

	private PropertyTypeMasterVO convertTOPropertyTypeMasterVO(
	        PropertyTypeMaster propertyTypeMaster) {

		if (propertyTypeMaster == null) {
			return null;
		}

		PropertyTypeMasterVO propertyTypeMasterVO = new PropertyTypeMasterVO();
		propertyTypeMasterVO.setId(propertyTypeMaster.getId());
		propertyTypeMasterVO
		        .setDescription(propertyTypeMaster.getDescription());
		propertyTypeMasterVO.setModifiedDate(propertyTypeMaster
		        .getModifiedDate());
		propertyTypeMasterVO.setPropertyTypeCd(propertyTypeMaster
		        .getPropertyTypeCd());
		propertyTypeMasterVO.setResidenceTypeCd(propertyTypeMaster
		        .getResidenceTypeCd());
		propertyTypeMasterVO.setPropertyTaxesPaid(propertyTypeMaster
		        .getPropertyTaxesPaid());
		propertyTypeMasterVO.setPropertyInsuranceProvider(propertyTypeMaster
		        .getPropertyInsuranceProvider());
		propertyTypeMasterVO.setPropertyInsuranceCost(propertyTypeMaster
		        .getPropertyInsuranceCost());
		propertyTypeMasterVO.setPropertyPurchaseYear(propertyTypeMaster
		        .getPropertyPurchaseYear());
		propertyTypeMasterVO.setHomeWorthToday(propertyTypeMaster
		        .getHomeWorthToday());
		propertyTypeMasterVO.setCurrentHomePrice(propertyTypeMaster
		        .getCurrentHomePrice());
		propertyTypeMasterVO.setCurrentHomeMortgageBalance(propertyTypeMaster
		        .getCurrentHomeMortgageBalance());
		propertyTypeMasterVO.setNewHomeBudgetFromsale(propertyTypeMaster
		        .getNewHomeBudgetFromsale());
		propertyTypeMasterVO.setPropTaxMonthlyOryearly(propertyTypeMaster
		        .getPropTaxMonthlyOryearly());
		propertyTypeMasterVO.setPropInsMonthlyOryearly(propertyTypeMaster
		        .getPropInsMonthlyOryearly());
		propertyTypeMasterVO.setPropStreetAddress(propertyTypeMaster
		        .getPropStreetAddress());
		propertyTypeMasterVO.setPropCity(propertyTypeMaster.getPropCity());
		propertyTypeMasterVO.setPropState(propertyTypeMaster.getPropState());
		propertyTypeMasterVO
		        .setHomeZipCode(propertyTypeMaster.getHomeZipCode());
		return propertyTypeMasterVO;

	}

	private GovernmentQuestionVO convertTOGovernmentquestionVO(
	        GovernmentQuestion governmentquestion) {

		if (null == governmentquestion)
			return null;

		GovernmentQuestionVO governmentQuestionVO = new GovernmentQuestionVO();

		governmentQuestionVO.setId(governmentquestion.getId());
		governmentQuestionVO.setOutstandingJudgments(governmentquestion
		        .isOutstandingJudgments());
		governmentQuestionVO.setBankrupt(governmentquestion.isBankrupt());
		governmentQuestionVO.setObligatedToPayAlimony(governmentquestion
		        .isObligatedToPayAlimony());
		governmentQuestionVO.setPropertyForeclosed(governmentquestion
		        .isPropertyForeclosed());
		governmentQuestionVO.setLawsuit(governmentquestion.isLawsuit());
		governmentQuestionVO.setObligatedLoan(governmentquestion
		        .isObligatedLoan());
		governmentQuestionVO.setFederalDebt(governmentquestion.isFederalDebt());
		governmentQuestionVO.setEndorser(governmentquestion.isEndorser());
		governmentQuestionVO.setUSCitizen(governmentquestion.isUSCitizen());

		governmentQuestionVO.setPermanentResidentAlien(governmentquestion
		        .getPermanentResidentAlien());
		governmentQuestionVO.setOccupyPrimaryResidence(governmentquestion
		        .isOccupyPrimaryResidence());
		governmentQuestionVO.setOwnershipInterestInProperty(governmentquestion
		        .isOwnershipInterestInProperty());
		governmentQuestionVO.setEthnicity(governmentquestion.getEthnicity());
		governmentQuestionVO.setRace(governmentquestion.getRace());
		governmentQuestionVO.setSex(governmentquestion.getSex());
		governmentQuestionVO.setIsDownPaymentBorrowed(governmentquestion
		        .getIsDownPaymentBorrowed());
		governmentQuestionVO.setPropertyTitleStatus(governmentquestion
		        .getPropertyTitleStatus());
		governmentQuestionVO.setTypeOfPropertyOwned(governmentquestion
		        .getTypeOfPropertyOwned());
		governmentQuestionVO.setSkipOptionalQuestion(governmentquestion
		        .getSkipOptionalQuestion());

		return governmentQuestionVO;
	}

	private SpouseGovernmentQuestionVO convertTOSpouseGovernmentquestionVO(
	        SpouseGovernmentQuestions spouseGovernmentquestions) {

		if (null == spouseGovernmentquestions)
			return null;

		SpouseGovernmentQuestionVO spouseGovernmentQuestionVO = new SpouseGovernmentQuestionVO();

		spouseGovernmentQuestionVO.setId(spouseGovernmentquestions.getId());
		spouseGovernmentQuestionVO
		        .setOutstandingJudgments(spouseGovernmentquestions
		                .isOutstandingJudgments());
		spouseGovernmentQuestionVO.setBankrupt(spouseGovernmentquestions
		        .isBankrupt());
		spouseGovernmentQuestionVO
		        .setPropertyForeclosed(spouseGovernmentquestions
		                .isPropertyForeclosed());
		spouseGovernmentQuestionVO.setLawsuit(spouseGovernmentquestions
		        .isLawsuit());
		spouseGovernmentQuestionVO.setObligatedLoan(spouseGovernmentquestions
		        .isObligatedLoan());
		spouseGovernmentQuestionVO
		        .setObligatedToPayAlimony(spouseGovernmentquestions
		                .isObligatedToPayAlimony());
		spouseGovernmentQuestionVO.setFederalDebt(spouseGovernmentquestions
		        .isFederalDebt());
		spouseGovernmentQuestionVO.setEndorser(spouseGovernmentquestions
		        .isEndorser());
		spouseGovernmentQuestionVO.setUSCitizen(spouseGovernmentquestions
		        .isUSCitizen());

		spouseGovernmentQuestionVO
		        .setPermanentResidentAlien(spouseGovernmentquestions
		                .isPermanentResidentAlien());

		spouseGovernmentQuestionVO
		        .setOccupyPrimaryResidence(spouseGovernmentquestions
		                .isOccupyPrimaryResidence());
		spouseGovernmentQuestionVO
		        .setOwnershipInterestInProperty(spouseGovernmentquestions
		                .isOwnershipInterestInProperty());
		spouseGovernmentQuestionVO.setEthnicity(spouseGovernmentquestions
		        .getEthnicity());
		spouseGovernmentQuestionVO.setRace(spouseGovernmentquestions.getRace());
		spouseGovernmentQuestionVO.setSex(spouseGovernmentquestions.getSex());
		spouseGovernmentQuestionVO
		        .setIsDownPaymentBorrowed(spouseGovernmentquestions
		                .getIsDownPaymentBorrowed());
		spouseGovernmentQuestionVO
		        .setPropertyTitleStatus(spouseGovernmentquestions
		                .getPropertyTitleStatus());
		spouseGovernmentQuestionVO
		        .setTypeOfPropertyOwned(spouseGovernmentquestions
		                .getTypeOfPropertyOwned());
		spouseGovernmentQuestionVO
		        .setSkipOptionalQuestion(spouseGovernmentquestions
		                .getSkipOptionalQuestion());

		return spouseGovernmentQuestionVO;
	}

	private RefinanceVO convertTORefinancedetailsVO(
	        RefinanceDetails refinancedetails) {

		if (refinancedetails == null)
			return null;
		RefinanceVO refinanceVO = new RefinanceVO();
		refinanceVO.setId(refinancedetails.getId());
		refinanceVO.setRefinanceOption(refinancedetails.getRefinanceOption());
		refinanceVO.setCurrentMortgageBalance(refinancedetails
		        .getCurrentMortgageBalance());
		refinanceVO.setCurrentMortgagePayment(refinancedetails
		        .getCurrentMortgagePayment());
		refinanceVO.setIncludeTaxes(refinancedetails.isIncludeTaxes());
		refinanceVO.setSecondMortageBalance(refinancedetails
		        .getSecondMortageBalance());
		refinanceVO.setMortgageyearsleft(refinancedetails
		        .getMortgageyearsleft());
		refinanceVO.setCashTakeOut(refinancedetails.getCashTakeOut());

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
	public LoanAppForm findByLoan(Loan loan) {
		return loanAppFormDao.findByLoan(loan);
	}

	@Override
	@Transactional
	public LoanAppForm findByuserID(int userid) {
		return loanAppFormDao.findByuserID(userid);
	}

	@Override
	@Transactional
	public ZipCodeLookup findByZipCode(String zipCode) throws Exception {
		return loanAppFormDao.findByZipCode(zipCode);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanAppFormVO getLoanAppFormByLoan(Loan loan) {
		LoanAppForm loanAppForm = loanAppFormDao.findByLoan(loan);
		if (loanAppForm != null) {
			return convertToLoanAppFormVO(loanAppForm);
		} else {
			return null;
		}
	}

}
