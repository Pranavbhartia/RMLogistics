package com.nexera.web.rest.util;

import java.util.HashMap;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.CustomerBankAccountDetails;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerOtherAccountDetails;
import com.nexera.common.entity.CustomerRetirementAccountDetails;
import com.nexera.common.entity.CustomerSpouseBankAccountDetails;
import com.nexera.common.entity.CustomerSpouseEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseOtherAccountDetails;
import com.nexera.common.entity.CustomerSpouseRetirementAccountDetails;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.CustomerBankAccountDetailsVO;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerRetirementAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseBankAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseRetirementAccountDetailsVO;
import com.nexera.common.vo.LoanAppFormVO;



@Component
public class ApplicationPathUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationPathUtil.class);

	
public LoanAppFormVO setEntityIdFromCache(LoanAppFormVO loaAppFormVO,HashMap<String, Integer> cache){
		
		try{
		if (cache.get("loanAppFormId") != null
		        && cache.get("loanAppFormId") != loaAppFormVO.getId()) {
			LOG.debug("loan form id mismatch , setting cache to null");
			cache.clear();
		}

		if (cache.get("loanAppFormId") != null && loaAppFormVO.getId() == 0
		        && cache.get("loanAppFormId") != 0) {
			loaAppFormVO.setId(cache.get("loanAppFormId"));
		}

		LOG.debug("cache.get(userId)" + cache.get("userId"));
		if (cache.get("userId") != null
		        && loaAppFormVO.getUser().getId() == 0
		        && cache.get("userId") != 0) {
			loaAppFormVO.getUser().setId(cache.get("userId"));
		}

		LOG.debug("cache.get(customerId)"
		        + cache.get("customerId"));
		if (cache.get("userId") != null
		        && loaAppFormVO.getUser().getCustomerDetail().getId() == 0
		        && cache.get("customerId") != 0) {
			loaAppFormVO.getUser().getCustomerDetail()
			        .setId(cache.get("customerId"));
		}

		LOG.debug("cache.get(customerSpouseDetail)"
		        + cache.get("customerSpouseDetail"));
		if (loaAppFormVO.getCustomerSpouseDetail() != null
		        && cache.get("customerSpouseDetail") != null
		        && loaAppFormVO.getCustomerSpouseDetail().getId() == 0
		        && cache.get("customerSpouseDetail") != 0) {
			loaAppFormVO.getCustomerSpouseDetail().setId(
			        cache.get("customerSpouseDetail"));
		}

		// Customer Income
		LOG.debug("cache.get(customerEmploymentIncome0)"
		        + cache.get("customerEmploymentIncome0"));

		if (loaAppFormVO.getCustomerEmploymentIncome() != null) {
			LOG.debug("##Inside customerEmploymentIncome0 ");
			Iterator<CustomerEmploymentIncomeVO> itr = loaAppFormVO
			        .getCustomerEmploymentIncome().iterator();
			int counter = 0;
			while (itr.hasNext()) {

				CustomerEmploymentIncomeVO customeremploymentincomeVO = itr
				        .next();
				LOG.debug("cache.get(customerEmploymentIncome"
				        + cache.get("customerEmploymentIncome" + counter
				                + ""));

				if (cache.get("customerEmploymentIncome" + counter) != null
				        && cache.get("customerEmploymentIncome" + counter) != 0
				        && loaAppFormVO.getCustomerEmploymentIncome()
				                .get(counter).getCustomerEmploymentIncome()
				                .getId() == 0) {
					loaAppFormVO
					        .getCustomerEmploymentIncome()
					        .get(counter)
					        .getCustomerEmploymentIncome()
					        .setId(cache.get("customerEmploymentIncome"
					                + counter + ""));
				}
				counter++;
			}
		}

		// Customer Bank Account
		LOG.debug("cache.get(customerBankAccountDetails0)"
		        + cache.get("customerBankAccountDetails0"));
		if (loaAppFormVO.getCustomerBankAccountDetails() != null) {

			LOG.debug("Inside customerBankAccountDetails0 "
			        + loaAppFormVO.getCustomerBankAccountDetails().get(0)
			                .getCustomerBankAccountDetails().getId());

			Iterator<CustomerBankAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerBankAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {

				CustomerBankAccountDetailsVO customerBankAccountDetailsVO = itr
				        .next();
				LOG.debug("cache.get(customerBankAccountDetails"
				        + cache.get("customerBankAccountDetails" + counter
				                + ""));

				if (cache.get("customerBankAccountDetails" + counter) != null
				        && cache.get("customerBankAccountDetails" + counter) != 0
				        && loaAppFormVO.getCustomerBankAccountDetails()
				                .get(counter)
				                .getCustomerBankAccountDetails().getId() == 0) {
					loaAppFormVO
					        .getCustomerBankAccountDetails()
					        .get(counter)
					        .getCustomerBankAccountDetails()
					        .setId(cache.get("customerBankAccountDetails"
					                + counter + ""));
				}
				counter++;
			}

		}

		// Customer Retirement Account

		if (loaAppFormVO.getCustomerRetirementAccountDetails() != null) {

			Iterator<CustomerRetirementAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerRetirementAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO = itr
				        .next();

				if (cache.get("customerRetirementAccountDetails" + counter) != null
				        && cache.get("customerRetirementAccountDetails"
				                + counter) != 0
				        && loaAppFormVO
				                .getCustomerRetirementAccountDetails()
				                .get(counter)
				                .getCustomerRetirementAccountDetails()
				                .getId() == 0) {
					loaAppFormVO
					        .getCustomerRetirementAccountDetails()
					        .get(counter)
					        .getCustomerRetirementAccountDetails()
					        .setId(cache
					                .get("customerRetirementAccountDetails"
					                        + counter + ""));
				}
				counter++;
			}
		}

		// Customer Other Account

		if (loaAppFormVO.getCustomerOtherAccountDetails() != null) {
			Iterator<CustomerOtherAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerOtherAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO = itr
				        .next();

				if (cache.get("customerOtherAccountDetails" + counter) != null
				        && cache.get("customerOtherAccountDetails"
				                + counter) != 0
				        && loaAppFormVO.getCustomerOtherAccountDetails()
				                .get(counter)
				                .getCustomerOtherAccountDetails().getId() == 0) {
					loaAppFormVO
					        .getCustomerOtherAccountDetails()
					        .get(counter)
					        .getCustomerOtherAccountDetails()
					        .setId(cache.get("customerOtherAccountDetails"
					                + counter + ""));
				}
				counter++;
			}

		}

		// //Spouse Income Related Details

		// Customer Spouse Income

		

		LOG.debug("cache.get(customerSpouseEmploymentIncome0)"
		        + cache.get("customerSpouseEmploymentIncome0"));
		if (loaAppFormVO.getCustomerSpouseEmploymentIncome() != null) {
			Iterator<CustomerSpouseEmploymentIncomeVO> itr = loaAppFormVO
			        .getCustomerSpouseEmploymentIncome().iterator();
			int counter = 0;
			while (itr.hasNext()) {

				CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentincomeVO = itr
				        .next();
				
				LOG.debug("cache.get(customerSpouseEmploymentIncome"
				                + cache.get("customerSpouseEmploymentIncome"
				                        + counter + ""));

				if (cache.get("customerSpouseEmploymentIncome" + counter) != null
				        && cache.get("customerSpouseEmploymentIncome"
				                + counter) != 0
				        && loaAppFormVO.getCustomerSpouseEmploymentIncome()
				                .get(counter)
				                .getCustomerSpouseEmploymentIncome()
				                .getId() == 0) {
					loaAppFormVO
					        .getCustomerSpouseEmploymentIncome()
					        .get(counter)
					        .getCustomerSpouseEmploymentIncome()
					        .setId(cache
					                .get("customerSpouseEmploymentIncome"
					                        + counter + ""));
				}
				counter++;
			}
		}

		// Customer Spouse Bank Account

		if (loaAppFormVO.getCustomerSpouseBankAccountDetails() != null) {

			Iterator<CustomerSpouseBankAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerSpouseBankAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {

				CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO = itr
				        .next();

				if (cache.get("customerSpouseBankAccountDetails" + counter) != null
				        && cache.get("customerSpouseBankAccountDetails"
				                + counter) != 0
				        && loaAppFormVO
				                .getCustomerSpouseBankAccountDetails()
				                .get(counter)
				                .getCustomerSpouseBankAccountDetails()
				                .getId() == 0) {
					loaAppFormVO
					        .getCustomerSpouseBankAccountDetails()
					        .get(counter)
					        .getCustomerSpouseBankAccountDetails()
					        .setId(cache
					                .get("customerSpouseBankAccountDetails"
					                        + counter + ""));
				}
				counter++;
			}

		}

		
		// Customer Spouse Retirement Account

		if (loaAppFormVO.getCustomerSpouseRetirementAccountDetails() != null) {
			Iterator<CustomerSpouseRetirementAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerSpouseRetirementAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerSpouseRetirementAccountDetailsVO customerSpouseRetirementAccountDetailsVO = itr
				        .next();
				if (cache.get("customerSpouseRetirementAccountDetails"
				        + counter) != null
				        && loaAppFormVO
				                .getCustomerSpouseRetirementAccountDetails()
				                .get(counter)
				                .getCustomerSpouseRetirementAccountDetails()
				                .getId() == 0
				        && cache.get("customerSpouseRetirementAccountDetails"
				                + counter) != 0) {

					loaAppFormVO
					        .getCustomerSpouseRetirementAccountDetails()
					        .get(counter)
					        .getCustomerSpouseRetirementAccountDetails()
					        .setId(cache
					                .get("customerSpouseRetirementAccountDetails"
					                        + counter + ""));
				}
				counter++;
			}
		}

		// Customer Spouse Other Account

		if (loaAppFormVO.getCustomerSpouseOtherAccountDetails() != null) {
			Iterator<CustomerSpouseOtherAccountDetailsVO> itr = loaAppFormVO
			        .getCustomerSpouseOtherAccountDetails().iterator();
			int counter = 0;

			while (itr.hasNext()) {
				CustomerSpouseOtherAccountDetailsVO customerSpouseOtherAccountDetailsVO = itr
				        .next();
				System.out
				        .println("customerSpouseBankAccountDetailsVO.getAccountSubType()"
				                + customerSpouseOtherAccountDetailsVO
				                        .getCustomerSpouseOtherAccountDetails()
				                        .getAccountSubType());
				if (cache
				        .get("customerSpouseOtherAccountDetails" + counter) != null
				        && loaAppFormVO
				                .getCustomerSpouseOtherAccountDetails()
				                .get(counter)
				                .getCustomerSpouseOtherAccountDetails()
				                .getId() == 0
				        && cache.get("customerSpouseOtherAccountDetails"
				                + counter) != 0) {
					loaAppFormVO
					        .getCustomerSpouseOtherAccountDetails()
					        .get(counter)
					        .getCustomerSpouseOtherAccountDetails()
					        .setId(cache
					                .get("customerSpouseOtherAccountDetails"
					                        + counter + ""));
				}
				counter++;
			}

		}

		LOG.debug("cache.get(propertyTypeMasterId)"
		        + cache.get("propertyTypeMasterId"));
		if (cache.get("propertyTypeMasterId") != null
		        && loaAppFormVO.getPropertyTypeMaster().getId() == 0
		        && cache.get("propertyTypeMasterId") != 0) {
			loaAppFormVO.getPropertyTypeMaster().setId(
			        cache.get("propertyTypeMasterId"));
		}

		LOG.debug("cache.get(governmentQuestionId)"
		        + cache.get("governmentQuestionId"));
		if (cache.get("governmentQuestionId") != null
		        && loaAppFormVO.getGovernmentquestion().getId() == 0
		        && cache.get("governmentQuestionId") != 0) {
			loaAppFormVO.getGovernmentquestion().setId(
			        cache.get("governmentQuestionId"));
		}

		LOG.debug("cache.get(spouseGovernmentQuestionId)"
		        + cache.get("spouseGovernmentQuestionId"));
		if (cache.get("spouseGovernmentQuestionId") != null
		        && loaAppFormVO.getId() == 0
		        && cache.get("spouseGovernmentQuestionId") != 0) {
			loaAppFormVO.getSpouseGovernmentQuestions().setId(
			        cache.get("spouseGovernmentQuestionId"));
		}

		LOG.debug("cache.get(refinanceDetailsId)"
		        + cache.get("refinanceDetailsId"));
		if (cache.get("refinanceDetailsId") != null
		        && loaAppFormVO.getRefinancedetails().getId() == 0
		        && cache.get("refinanceDetailsId") != 0) {
			loaAppFormVO.getRefinancedetails().setId(
			        cache.get("refinanceDetailsId"));
		}

		if (cache.get("purchaseDetails") != null
		        && loaAppFormVO.getPurchaseDetails() != null
		        && loaAppFormVO.getPurchaseDetails().getId() == 0
		        && cache.get("purchaseDetails") != 0) {
			loaAppFormVO.getPurchaseDetails().setId(
			        cache.get("purchaseDetails"));
		}
		return loaAppFormVO;
		}catch(Exception e){
			return loaAppFormVO;
		}
	}
	
	public void setLocalCache(LoanAppForm loanAppForm,HashMap<String, Integer> cache){
		
		
		cache.put("loanAppFormId", loanAppForm.getId());
		cache.put("userId", loanAppForm.getUser().getId());
		cache.put("customerId", loanAppForm.getUser().getCustomerDetail()
		        .getId());
		cache.put("propertyTypeMasterId", loanAppForm
		        .getPropertyTypeMaster().getId());
		LOG.debug("cache propertyTypeMasterId"
		        + loanAppForm.getPropertyTypeMaster().getId());

		LOG.debug("cache refinacne"
		        + loanAppForm.getRefinancedetails().getId());
		cache.put("governmentQuestionId", loanAppForm
		        .getGovernmentquestion().getId());
		cache.put("refinanceDetailsId", loanAppForm.getRefinancedetails()
		        .getId());
		cache.put("purchaseDetails", loanAppForm.getPurchaseDetails()
		        .getId());

		if (loanAppForm.getCustomerspousedetail() != null) {
			cache.put("customerSpouseDetail", loanAppForm
			        .getCustomerspousedetail().getId());

			LOG.info("cache.putcustomerSpouseDetail"
			        + loanAppForm.getCustomerspousedetail().getId());
		}

		if (loanAppForm.getCustomerEmploymentIncome() != null) {

			LOG.info("Setting customer emplyment income");
			Iterator<CustomerEmploymentIncome> itr = loanAppForm
			        .getCustomerEmploymentIncome().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerEmploymentIncome customeremploymentincome = itr
				        .next();
				System.out
				        .println("customeremploymentincome.getEmployedAt()"
				                + counter + "##"
				                + customeremploymentincome.getEmployedAt());
				System.out
				        .println("customeremploymentincomeVO.getCustomerEmploymentIncome().getId()"
				                + counter
				                + "##"
				                + customeremploymentincome.getId());
				cache.put("customerEmploymentIncome" + counter,
				        customeremploymentincome.getId());
				counter++;
			}

		}

		if (loanAppForm.getCustomerBankAccountDetails() != null) {
			Iterator<CustomerBankAccountDetails> itr = loanAppForm
			        .getCustomerBankAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerBankAccountDetails customerBankAccountDetails = itr
				        .next();
				System.out
				        .println("customerBankAccountDetails.getAccountSubType()"
				                + customerBankAccountDetails
				                        .getAccountSubType());
				// LOG.debug("customerBankAccountDetails.getId()"
				// + customerBankAccountDetails.getId());

				LOG.debug("customerBankAccountDetails.getId()"
				        + counter + "##"
				        + customerBankAccountDetails.getId());

				cache.put("customerBankAccountDetails" + counter,
				        customerBankAccountDetails.getId());
				counter++;
			}

		}

		if (loanAppForm.getCustomerRetirementAccountDetails() != null) {

			Iterator<CustomerRetirementAccountDetails> itr = loanAppForm
			        .getCustomerRetirementAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerRetirementAccountDetails customerRetirementAccountDetails = itr
				        .next();
				System.out
				        .println("customerRetirementAccountDetails.getAccountSubType()"
				                + customerRetirementAccountDetails
				                        .getAccountSubType());
				System.out
				        .println("customerRetirementAccountDetails.getId()"
				                + customerRetirementAccountDetails.getId());
				cache.put("customerRetirementAccountDetails" + counter,
				        customerRetirementAccountDetails.getId());
				counter++;
			}
		}

		if (loanAppForm.getCustomerOtherAccountDetails() != null) {
			Iterator<CustomerOtherAccountDetails> itr = loanAppForm
			        .getCustomerOtherAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerOtherAccountDetails customerOtherAccountDetails = itr
				        .next();
				System.out
				        .println("customerOtherAccountDetails.getAccountSubType()"
				                + customerOtherAccountDetails
				                        .getAccountSubType());
				LOG.debug("customerOtherAccountDetails.getId()"
				        + customerOtherAccountDetails.getId());
				cache.put("customerOtherAccountDetails" + counter,
				        customerOtherAccountDetails.getId());
				counter++;
			}
		}

		// //Spouse Income Details

		if (loanAppForm.getCustomerSpouseEmploymentIncome() != null) {

			Iterator<CustomerSpouseEmploymentIncome> itr = loanAppForm
			        .getCustomerSpouseEmploymentIncome().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerSpouseEmploymentIncome customerSpouseEmploymentincome = itr
				        .next();
				System.out
				        .println("customerSpouseEmploymentincome.getEmployedAt()"
				                + customerSpouseEmploymentincome
				                        .getEmployedAt());
				LOG.debug("customerSpouseEmploymentincome.getId("
				        + customerSpouseEmploymentincome.getId());
				cache.put("customerSpouseEmploymentIncome" + counter,
				        customerSpouseEmploymentincome.getId());
				counter++;
			}

		}

		if (loanAppForm.getCustomerSpouseBankAccountDetails() != null) {
			Iterator<CustomerSpouseBankAccountDetails> itr = loanAppForm
			        .getCustomerSpouseBankAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails = itr
				        .next();
				System.out
				        .println("customerSpouseBankAccountDetails.getAccountSubType()"
				                + customerSpouseBankAccountDetails
				                        .getAccountSubType());
				System.out
				        .println("customerSpouseBankAccountDetails.getId()"
				                + customerSpouseBankAccountDetails.getId());
				cache.put("customerSpouseBankAccountDetails" + counter,
				        customerSpouseBankAccountDetails.getId());
				counter++;
			}

		}

		if (loanAppForm.getCustomerSpouseRetirementAccountDetails() != null) {

			Iterator<CustomerSpouseRetirementAccountDetails> itr = loanAppForm
			        .getCustomerSpouseRetirementAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerSpouseRetirementAccountDetails customerSpouseRetirementAccountDetails = itr
				        .next();
				System.out
				        .println("customerSpouseRetirementAccountDetails.getAccountSubType()"
				                + customerSpouseRetirementAccountDetails
				                        .getAccountSubType());
				System.out
				        .println("customerSpouseRetirementAccountDetails.getId()"
				                + customerSpouseRetirementAccountDetails
				                        .getId());
				cache.put("customerSpouseRetirementAccountDetails"
				        + counter,
				        customerSpouseRetirementAccountDetails.getId());
				counter++;
			}
		}

		if (loanAppForm.getCustomerSpouseOtherAccountDetails() != null) {
			Iterator<CustomerSpouseOtherAccountDetails> itr = loanAppForm
			        .getCustomerSpouseOtherAccountDetails().iterator();
			int counter = 0;
			while (itr.hasNext()) {
				CustomerSpouseOtherAccountDetails customerSpouseOtherAccountDetails = itr
				        .next();
				System.out
				        .println("customerSpouseOtherAccountDetails.getAccountSubType()"
				                + customerSpouseOtherAccountDetails
				                        .getAccountSubType());
				System.out
				        .println("customerSpouseOtherAccountDetails.getId()"
				                + customerSpouseOtherAccountDetails.getId());
				cache.put("customerSpouseOtherAccountDetails" + counter,
				        customerSpouseOtherAccountDetails.getId());
				counter++;
			}
		}
		
	}
	
}
