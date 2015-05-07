package com.nexera.web.rest;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.nexera.common.commons.Utils;
import com.nexera.common.entity.CustomerBankAccountDetails;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerOtherAccountDetails;
import com.nexera.common.entity.CustomerRetirementAccountDetails;
import com.nexera.common.entity.CustomerSpouseBankAccountDetails;
import com.nexera.common.entity.CustomerSpouseEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseOtherAccountDetails;
import com.nexera.common.entity.CustomerSpouseRetirementAccountDetails;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.CustomerBankAccountDetailsVO;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerRetirementAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseBankAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseRetirementAccountDetailsVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanLockRateVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/application/")
public class ApplicationFormRestService {
	private static final String SESSION_CACHE_KEY = "Cache-key";
	private static final Logger LOG = LoggerFactory
	        .getLogger(ApplicationFormRestService.class);
	@Autowired
	private LoanAppFormService loanAppFormService;
	
	@Autowired
	private LoanService loanService;
	@Autowired
	private NeedsListService needsListService;

	@Value("${muleUrlForLoan}")
	private String muleLoanUrl;

	// @RequestBody
	@RequestMapping(value = "/applyloan", method = RequestMethod.POST)
	public @ResponseBody String createApplication(String appFormData,
	        HttpServletRequest httpServletRequest) {

		Gson gson = new Gson();

		// LoanAppFormVO loaAppFormVO =
		// gson.fromJson(appFormData,LoanAppFormVO.class);

		try {

			System.out.println("appFormData is" + appFormData);

			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);
			System.out.println("loaAppFormVO.getId()" + loaAppFormVO.getId());
			HashMap<String, Integer> cache = new HashMap<String, Integer>();

			HttpSession httpSession = httpServletRequest.getSession();
			if (httpSession != null) {
				Object map = httpSession.getAttribute(SESSION_CACHE_KEY);
				if (map == null) {
					httpSession.setAttribute(SESSION_CACHE_KEY,
					        new HashMap<String, Integer>());
					map = httpSession.getAttribute(SESSION_CACHE_KEY);
				}
				cache = (HashMap<String, Integer>) map;
			}

			if (cache.get("loanAppFormId") != null
			        && cache.get("loanAppFormId") != loaAppFormVO.getId()) {
				System.out
				        .println("loan form id mismatch , setting cache to null");
				cache.clear();
			}

			if (cache.get("loanAppFormId") != null && loaAppFormVO.getId() == 0
			        && cache.get("loanAppFormId") != 0) {
				loaAppFormVO.setId(cache.get("loanAppFormId"));
			}

			System.out.println("cache.get(userId)" + cache.get("userId"));
			if (cache.get("userId") != null
			        && loaAppFormVO.getUser().getId() == 0
			        && cache.get("userId") != 0) {
				loaAppFormVO.getUser().setId(cache.get("userId"));
			}

			System.out.println("cache.get(customerId)"
			        + cache.get("customerId"));
			if (cache.get("userId") != null
			        && loaAppFormVO.getUser().getCustomerDetail().getId() == 0
			        && cache.get("customerId") != 0) {
				loaAppFormVO.getUser().getCustomerDetail()
				        .setId(cache.get("customerId"));
			}

			System.out.println("cache.get(customerSpouseDetail)"
			        + cache.get("customerSpouseDetail"));
			if (loaAppFormVO.getCustomerSpouseDetail() != null
			        && cache.get("customerSpouseDetail") != null
			        && loaAppFormVO.getCustomerSpouseDetail().getId() == 0
			        && cache.get("customerSpouseDetail") != 0) {
				loaAppFormVO.getCustomerSpouseDetail().setId(
				        cache.get("customerSpouseDetail"));
			}

			// Customer Income
			System.out.println("cache.get(customerEmploymentIncome0)"
			        + cache.get("customerEmploymentIncome0"));
			
			
			if (loaAppFormVO.getCustomerEmploymentIncome() != null) {
				System.out.println("##Inside customerEmploymentIncome0 ");
				Iterator<CustomerEmploymentIncomeVO> itr = loaAppFormVO
				        .getCustomerEmploymentIncome().iterator();
				int counter = 0;
				while (itr.hasNext()) {

					CustomerEmploymentIncomeVO customeremploymentincomeVO = itr
					        .next();
					System.out
					        .println("customeremploymentincomeVO.getEmployedAt()"
					                + customeremploymentincomeVO
					                        .getCustomerEmploymentIncome()
					                        .getEmployedAt());
					System.out.println("cache.get(customerEmploymentIncome"
					        + cache.get("customerEmploymentIncome" + counter
					                + ""));
					
					
					
					if (cache.get("customerEmploymentIncome" + counter) != null && cache.get("customerEmploymentIncome" + counter) !=0 && loaAppFormVO.getCustomerEmploymentIncome().get(counter)
			                .getCustomerEmploymentIncome().getId() == 0) {
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
				System.out.println("cache.get(customerBankAccountDetails0)"
			        + cache.get("customerBankAccountDetails0"));
			if (loaAppFormVO.getCustomerBankAccountDetails()!=null) {
				
				System.out.println("Inside customerBankAccountDetails0 "+loaAppFormVO.getCustomerBankAccountDetails().get(0)
		                .getCustomerBankAccountDetails().getId());

				Iterator<CustomerBankAccountDetailsVO> itr = loaAppFormVO
				        .getCustomerBankAccountDetails().iterator();
				int counter = 0;
				while (itr.hasNext()) {

					CustomerBankAccountDetailsVO customerBankAccountDetailsVO = itr
					        .next();
					System.out
					        .println("customerBankAccountDetailsVO.getAccountSubType()"
					                + customerBankAccountDetailsVO
					                        .getCustomerBankAccountDetails()
					                        .getAccountSubType());
					
					System.out.println("cache.get(customerBankAccountDetails"
					        + cache.get("customerBankAccountDetails" + counter
					                + ""));
					
					        
					        
					if (cache.get("customerBankAccountDetails" + counter) != null && cache.get("customerBankAccountDetails" + counter) != 0  && loaAppFormVO.getCustomerBankAccountDetails().get(counter)
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
					System.out
					        .println("customerBankAccountDetailsVO.getAccountSubType()"
					                + customerRetirementAccountDetailsVO
					                        .getCustomerRetirementAccountDetails()
					                        .getAccountSubType());
					
				
						        
					
			if (cache.get("customerRetirementAccountDetails" + counter) != null && cache.get("customerRetirementAccountDetails" + counter) != 0  && loaAppFormVO.getCustomerRetirementAccountDetails().get(counter)
						                .getCustomerRetirementAccountDetails().getId() == 0) {
					loaAppFormVO
					        .getCustomerRetirementAccountDetails()
					        .get(counter)
					        .getCustomerRetirementAccountDetails()
					        .setId(cache.get("customerRetirementAccountDetails"
					                + counter + ""));
			}
					counter++;
				}
			}

			// Customer Other Account

			if (loaAppFormVO.getCustomerOtherAccountDetails() !=null ) {
				Iterator<CustomerOtherAccountDetailsVO> itr = loaAppFormVO
				        .getCustomerOtherAccountDetails().iterator();
				int counter = 0;
				while (itr.hasNext()) {
					CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO = itr
					        .next();
					System.out
					        .println("customerOtherAccountDetailsVO.getAccountSubType()"
					                + customerOtherAccountDetailsVO
					                        .getCustomerOtherAccountDetails()
					                        .getAccountSubType());
					
				
					        
					        
					if (cache.get("customerOtherAccountDetails" + counter) != null && cache.get("customerOtherAccountDetails" + counter) != 0  && loaAppFormVO.getCustomerOtherAccountDetails().get(counter)
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
			
			
		
          /* System.out.println("## loaAppFormVO.getCustomerSpouseEmploymentIncome()"+ loaAppFormVO.getCustomerSpouseEmploymentIncome());
			 System.out.println("## loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0)"+ loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0));
			 System.out.println("## loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome()"+ loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome());
			 System.out.println("## loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getId()"+ loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getId());
        */    		 
             
             
			System.out.println("cache.get(customerSpouseEmploymentIncome0)"
			        + cache.get("customerSpouseEmploymentIncome0"));
			if (loaAppFormVO.getCustomerSpouseEmploymentIncome() != null) {
				Iterator<CustomerSpouseEmploymentIncomeVO> itr = loaAppFormVO
				        .getCustomerSpouseEmploymentIncome().iterator();
				int counter = 0;
				while (itr.hasNext()) {

					CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentincomeVO = itr
					        .next();
					System.out
					        .println("customerSpouseEmploymentincomeVO.getEmployedAt()"
					                + customerSpouseEmploymentincomeVO
					                        .getCustomerSpouseEmploymentIncome()
					                        .getEmployedAt());
					
					System.out.println("cache.get(customerSpouseEmploymentIncome"
					        + cache.get("customerSpouseEmploymentIncome" + counter
					                + ""));
					
					
					
						        
					if (cache.get("customerSpouseEmploymentIncome" + counter) != null && cache.get("customerSpouseEmploymentIncome" + counter) != 0  && loaAppFormVO.getCustomerSpouseEmploymentIncome().get(counter)
			                .getCustomerSpouseEmploymentIncome().getId() == 0) {
					loaAppFormVO
					        .getCustomerSpouseEmploymentIncome()
					        .get(counter)
					        .getCustomerSpouseEmploymentIncome()
					        .setId(cache.get("customerSpouseEmploymentIncome"
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
					System.out
					        .println("customerSpouseBankAccountDetailsVO.getAccountSubType()"
					                + customerSpouseBankAccountDetailsVO
					                        .getCustomerSpouseBankAccountDetails()
					                        .getAccountSubType());
					
					
					if (cache.get("customerSpouseBankAccountDetails" + counter) != null && cache.get("customerSpouseBankAccountDetails" + counter) != 0  && loaAppFormVO.getCustomerSpouseBankAccountDetails().get(counter)
			                .getCustomerSpouseBankAccountDetails().getId() == 0) {
					loaAppFormVO
					        .getCustomerSpouseBankAccountDetails()
					        .get(counter)
					        .getCustomerSpouseBankAccountDetails()
					        .setId(cache.get("customerSpouseBankAccountDetails"
					                + counter + ""));
					}
					counter++;
				}

			}

			// Customer Spouse Retirement Account

			if (loaAppFormVO.getCustomerSpouseRetirementAccountDetails() != null) {
				System.out
		        .println("$$ Inside getCustomerSpouseRetirementAccountDetails");
				Iterator<CustomerSpouseRetirementAccountDetailsVO> itr = loaAppFormVO
				        .getCustomerSpouseRetirementAccountDetails().iterator();
				int counter = 0;
				while (itr.hasNext()) {
					CustomerSpouseRetirementAccountDetailsVO customerSpouseRetirementAccountDetailsVO = itr
					        .next();
					
					 System.out
				        .println("$$$$getCustomerSpouseRetirementAccountDetails.getAccountSubType()"
				                + customerSpouseRetirementAccountDetailsVO
				                        .getCustomerSpouseRetirementAccountDetails()
				                        .getAccountSubType());
					 
					 
					
				   if(cache.get("customerSpouseRetirementAccountDetails"+counter) != null && loaAppFormVO.getCustomerSpouseRetirementAccountDetails()
	                .get(counter).getCustomerSpouseRetirementAccountDetails()
	                .getId() == 0
	        && cache.get("customerSpouseRetirementAccountDetails"+counter) != 0	){
				  
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

			if (loaAppFormVO.getCustomerSpouseOtherAccountDetails() != null ) {
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
					 if(cache.get("customerSpouseOtherAccountDetails"+counter) != null && loaAppFormVO.getCustomerSpouseOtherAccountDetails()
				                .get(counter).getCustomerSpouseOtherAccountDetails()
				                .getId() == 0
				        && cache.get("customerSpouseOtherAccountDetails"+counter) != 0	){
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

			System.out.println("cache.get(propertyTypeMasterId)"
			        + cache.get("propertyTypeMasterId"));
			if (cache.get("propertyTypeMasterId") != null
			        && loaAppFormVO.getPropertyTypeMaster().getId() == 0
			        && cache.get("propertyTypeMasterId") != 0) {
				loaAppFormVO.getPropertyTypeMaster().setId(
				        cache.get("propertyTypeMasterId"));
			}

			System.out.println("cache.get(governmentQuestionId)"
			        + cache.get("governmentQuestionId"));
			if (cache.get("governmentQuestionId") != null
			        && loaAppFormVO.getGovernmentquestion().getId() == 0
			        && cache.get("governmentQuestionId") != 0) {
				loaAppFormVO.getGovernmentquestion().setId(
				        cache.get("governmentQuestionId"));
			}

			System.out.println("cache.get(spouseGovernmentQuestionId)"
			        + cache.get("spouseGovernmentQuestionId"));
			if (cache.get("spouseGovernmentQuestionId") != null
			        && loaAppFormVO.getId() == 0
			        && cache.get("spouseGovernmentQuestionId") != 0) {
				loaAppFormVO.getSpouseGovernmentQuestions().setId(
				        cache.get("spouseGovernmentQuestionId"));
			}

			System.out.println("cache.get(refinanceDetailsId)"
			        + cache.get("refinanceDetailsId"));
			if (cache.get("refinanceDetailsId") != null
			        && loaAppFormVO.getRefinancedetails().getId() == 0
			        && cache.get("refinanceDetailsId") != 0) {
				loaAppFormVO.getRefinancedetails().setId(
				        cache.get("refinanceDetailsId"));
			}

			if (cache.get("purchaseDetails") != null && loaAppFormVO.getPurchaseDetails() != null &&loaAppFormVO.getPurchaseDetails().getId() == 0
			        && cache.get("purchaseDetails") != 0) {
				loaAppFormVO.getPurchaseDetails().setId(
				        cache.get("purchaseDetails"));
			}

			LoanAppForm loanAppForm = loanAppFormService.create(loaAppFormVO);

			
		
			

			cache.put("loanAppFormId", loanAppForm.getId());
			cache.put("userId", loanAppForm.getUser().getId());
			cache.put("customerId", loanAppForm.getUser().getCustomerDetail()
			        .getId());
			cache.put("propertyTypeMasterId", loanAppForm
			        .getPropertyTypeMaster().getId());
			System.out.println("cache propertyTypeMasterId"
			        + loanAppForm.getPropertyTypeMaster().getId());

			System.out.println("cache refinacne"
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
					//System.out.println("customerBankAccountDetails.getId()"
					  //      + customerBankAccountDetails.getId());
					
					
					System.out
			        .println("customerBankAccountDetails.getId()"
			                + counter
			                + "##"
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
					System.out.println("customerOtherAccountDetails.getId()"
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
					System.out.println("customerSpouseEmploymentincome.getId("
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

			return new Gson().toJson(loaAppFormVO);

		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(null);
		return null;
	}

	@RequestMapping(value = "{appFormId}", method = RequestMethod.POST)
	public @ResponseBody String updateApplication(@PathVariable int appFormId,
	        @RequestBody String appFormData) {

		Gson gson = new Gson();

		LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
		        LoanAppFormVO.class);

		loaAppFormVO.setId(appFormId);
		loanAppFormService.save(loaAppFormVO);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(null);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/createLoan", method = RequestMethod.POST)
	public @ResponseBody
	String createLoan(String appFormData, HttpServletRequest httpServletRequest) {
		System.out.println("Inside createLoan" + appFormData);
		Gson gson = new Gson();
		String lockRateData = null;
		LoanAppFormVO loaAppFormVO = null;
		try {
			loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);
			String loanNumber = invokeRest(prepareCreateLoanJson(
			        "DIRECT-REFI-MASTER TEMPLATE").toString());
			System.out.println("createLoanResponse is" + loanNumber);
			if (!"".equalsIgnoreCase(loanNumber)) {

				String response = invokeRest((saveLoan(loanNumber, loaAppFormVO))
				        .toString());
				System.out.println("Save Loan Response is " + response);
				if (null != loaAppFormVO.getLoan()) {
					LoanVO loan = loaAppFormVO.getLoan();
					loan.setLqbFileId(loanNumber);
					String loanAppFrm = gson.toJson(loaAppFormVO);
					createApplication(loanAppFrm, httpServletRequest);
				}
				// JSONObject jsonObject = new JSONObject(response);
				// LOG.info("Response Returned from save Loan Service is"+jsonObject.get("responseCode").toString());

				// Code for automating Needs List creation
				Integer loanId = loaAppFormVO.getLoan().getId();
				needsListService.createInitilaNeedsList(loanId);

				if (response != null) {
					lockRateData = loadLoanRateData(loanNumber);
					System.out.println("lockRateData" + lockRateData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			lockRateData = "error";
		}
		if (lockRateData == null || lockRateData == "error") {
			// code to send mail to user and loan manager
			if (loaAppFormVO != null && loaAppFormVO.getLoan() != null)
				loanService.sendNoproductsAvailableEmail(loaAppFormVO.getLoan()
			        .getId());
		}
		return lockRateData;

	}

	@RequestMapping(value = "/changeLoanAmount", method = RequestMethod.POST)
	public @ResponseBody
	String changeLoanAmount(String appFormData, HttpServletRequest httpServletRequest) {
		Gson gson = new Gson();
		String lockRateData = null;
		try {
			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);
			String loanNumber = loaAppFormVO.getLoan().getLqbFileId();
			System.out.println("createLoanResponse is" + loanNumber);
			if (!"".equalsIgnoreCase(loanNumber)) {

				String response = invokeRest((saveLoan(loanNumber, loaAppFormVO))
				        .toString());
				System.out.println("Save Loan Response is " + response);
				// JSONObject jsonObject = new JSONObject(response);
				// LOG.info("Response Returned from save Loan Service is"+jsonObject.get("responseCode").toString());

				String loanAppFrm = gson.toJson(loaAppFormVO);
				createApplication(loanAppFrm, httpServletRequest);
				
				if (response != null) {
					lockRateData = loadLoanRateData(loanNumber);
					System.out.println("lockRateData" + lockRateData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return lockRateData;

	}

	@RequestMapping(value = "/fetchLockRatedata/{loanNumber}", method = RequestMethod.POST)
	public @ResponseBody String fetchLockRatedata(
	        @PathVariable String loanNumber) {
		System.out.println("Inside fetchLockRatedata loanNumber" + loanNumber);

		String lockRateData = null;
		try {

			lockRateData = loadLoanRateData(loanNumber);
			System.out.println("lockRateData" + lockRateData);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return lockRateData;

	}

	@RequestMapping(value = "/lockLoanRate", method = RequestMethod.POST)
	public @ResponseBody String lockLoanRateLoan(String appFormData) {
		System.out.println("Inside lockLoanRateLoan" + appFormData);
		Gson gson = new Gson();
		String lockRateData = null;
		try {
			LoanLockRateVO loanLockRateVO = gson.fromJson(appFormData,
			        LoanLockRateVO.class);
			System.out.println("lockLoanRate is"
			        + loanLockRateVO.getIlpTemplateId());
			lockRateData = invokeRest(prepareLockLoanRateJson(loanLockRateVO)
			        .toString());
			System.out.println("lockLoanRate is" + lockRateData);
			if (!lockRateData.contains("status=\"Error\"")) {
				loanService.updateLoan(loanLockRateVO.getLoanId(), true,
				        loanLockRateVO.getRateVo());
				loanService.sendRateLocked(loanLockRateVO.getLoanId());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return lockRateData;

	}

	private String loadLoanRateData(String loanNumber) {
		Gson gson = new Gson();
		List<TeaserRateResponseVO> teaserRateList = null;
		List<TeaserRateResponseVO> lockLoanRateList = null;
		RateCalculatorRestService rateService = new RateCalculatorRestService();
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sLoanNumber", loanNumber);
			jsonChild.put("sXmlQueryMap", new JSONObject("{}"));
			jsonChild.put("format", 0);
			json.put("opName", "Load");
			json.put("loanVO", jsonChild);
			System.out.println("jsonMapObject load Loandata" + json);
			// JSONObject jsonObject = new
			// JSONObject(invokeRest(json.toString()));
			teaserRateList = rateService
			        .parseLqbResponse(retrievePricingDetails(invokeRest(json
			                .toString())));

			TeaserRateResponseVO teaserRateResponseVO = new TeaserRateResponseVO();
			teaserRateResponseVO.setLoanDuration("sample");
			teaserRateResponseVO.setLoanNumber(loanNumber);
			
			if(teaserRateList!=null)
			teaserRateList.add(0, teaserRateResponseVO);
			

		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("loadLoanRateData" + gson.toJson(teaserRateList));
		return gson.toJson(teaserRateList);

	}

	public JSONObject prepareCreateLoanJson(String templateName) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sTemplateName", templateName);
			json.put("opName", "Create");

			json.put("loanVO", jsonChild);

			System.out.println("jsonMapObject" + json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private String retrievePricingDetails(String xml) {
		String pricingResultXml = null;
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
			        .newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
			        xml)));
			NodeList nodeList = document.getDocumentElement().getChildNodes();

			NodeList nList = document.getElementsByTagName("field");

			for (int j = 0; j < nList.getLength(); j++) {
				Node nNode = nList.item(j);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if ("PricingResult".equalsIgnoreCase(eElement
					        .getAttribute("id"))) {

						if (eElement.getChildNodes().item(0) != null) {
							System.out.println("Pricing result is"
							        + eElement.getChildNodes().item(0)
							                .getTextContent());
							pricingResultXml = eElement.getChildNodes().item(0)
							        .getTextContent();
							break;
						}

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pricingResultXml;

	}

	public JSONObject prepareLockLoanRateJson(LoanLockRateVO loanLockRateVO) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sLoanNumber", loanLockRateVO.getsLoanNumber());
			jsonChild.put("IlpTemplateId", loanLockRateVO.getIlpTemplateId());
			jsonChild.put("requestedFee", loanLockRateVO.getRequestedFee());
			jsonChild.put("requestedRate", loanLockRateVO.getRequestedRate());
			json.put("opName", "LockLoanProgram");

			json.put("loanVO", jsonChild);

			System.out.println("jsonMapObject" + json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	private String invokeRest(String appFormData) {

		LOG.info("Invoking rest Service with Input " + appFormData);
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			String returnedResponse = restTemplate.postForObject(muleLoanUrl,
			        request, String.class);
			JSONObject jsonObject = new JSONObject(returnedResponse);
			LOG.info("Response Returned from Rest Service is"
			        + jsonObject.get("responseMessage").toString());
			// teaserRateList =
			// parseLqbResponse(jsonObject.get("responseMessage").toString());
			return jsonObject.get("responseMessage").toString();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in post entity");
			return "error";
		}

	}

	private JSONObject saveLoan(String loanNumber, LoanAppFormVO loanAppFormVO) {
		HashMap<String, String> hashmap = new HashMap();
		try {
			String condition = "";
			
			String loanPurpose ="";
		
			
			if ("PUR".equalsIgnoreCase(loanAppFormVO.getLoanType().getLoanTypeCd())) {
				loanPurpose = "0";
				hashmap.put("loanPurchasePrice", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPurchaseDetails().getHousePrice()));
				hashmap.put("prodCashOut", "0");
			} else if ("REFCO".equalsIgnoreCase(loanAppFormVO.getLoanType().getLoanTypeCd())){
				loanPurpose = "2";
				hashmap.put("loanPurchasePrice", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPropertyTypeMaster().getCurrentHomePrice()));
				
				hashmap.put("prodCashOut", Utils.unformatCurrencyField(loanAppFormVO
		                .getRefinancedetails().getCashTakeOut()));
			}else {
				loanPurpose = "1";
				hashmap.put("loanPurchasePrice", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPropertyTypeMaster().getCurrentHomePrice()));
				hashmap.put("prodCashOut", "0");
			}
			
			hashmap.put("loanPurpose", loanPurpose);
			
			
			

			if ("Purchase".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getDescription())) {
				hashmap.put("loanApprovedValue", Utils.unformatCurrencyField(loanAppFormVO.getPurchaseDetails().getHousePrice()));
				if(null!= loanAppFormVO.getPropertyTypeMaster()){
					hashmap.put("propertyState", loanAppFormVO.getPropertyTypeMaster().getPropState());
					hashmap.put("propertyStreetAddress", loanAppFormVO.getPropertyTypeMaster().getPropStreetAddress());
					hashmap.put("propertyCity", loanAppFormVO.getPropertyTypeMaster().getPropCity());
					hashmap.put("propertyZip", loanAppFormVO.getPropertyTypeMaster().getHomeZipCode());
					}
				hashmap.put("borrowerAddrYrs",getYearsSpent(loanAppFormVO.getUser().getCustomerDetail().getLivingSince()));	
			
			} else {

				hashmap.put("borrowerAddrYrs",getYearsSpent(loanAppFormVO.getPropertyTypeMaster().getPropertyPurchaseYear()));
				hashmap.put("loanApprovedValue", Utils.unformatCurrencyField(loanAppFormVO.getPropertyTypeMaster().getHomeWorthToday()));
				if(null != loanAppFormVO.getPropertyTypeMaster().getPropertyPurchaseYear() && loanAppFormVO.getPropertyTypeMaster().getPropertyPurchaseYear().indexOf(" ") >0)
				hashmap.put("propertyAcquiredYear",loanAppFormVO.getPropertyTypeMaster().getPropertyPurchaseYear().split(" ")[1]);
				hashmap.put("mortageRemaining",Utils.unformatCurrencyField(loanAppFormVO.getRefinancedetails().getCurrentMortgageBalance()));
				
				hashmap.put("propertyState", loanAppFormVO.getUser().getCustomerDetail().getAddressState());
				hashmap.put("propertyStreetAddress", loanAppFormVO.getUser().getCustomerDetail().getAddressStreet());
				hashmap.put("propertyCity", loanAppFormVO.getUser().getCustomerDetail().getAddressCity());
				hashmap.put("propertyZip",loanAppFormVO.getUser().getCustomerDetail().getAddressZipCode());
			
				
				
			}

			
			
			
			
			hashmap.put("applicantId", loanAppFormVO.getUser()
			        .getCustomerDetail().getSsn());
			hashmap.put("firstName", loanAppFormVO.getLoan().getUser()
			        .getFirstName());
			hashmap.put("middleName", loanAppFormVO.getLoan().getUser()
			        .getLastName());
			hashmap.put("lastName", loanAppFormVO.getLoan().getUser()
			        .getLastName());
			hashmap.put("dateOfBirth", new SimpleDateFormat("yyyy-MM-dd").format(new Date(loanAppFormVO.getUser()
			        .getCustomerDetail().getDateOfBirth())));
		
		
			
			
			hashmap.put("borrowerHomePhone", loanAppFormVO.getUser().getPhoneNumber());
			
			
			hashmap.put("alimonyName", "NONE");
			
			if(loanAppFormVO.getReceiveAlimonyChildSupport()){
			hashmap.put("alimonyPayment", loanAppFormVO.getChildSupportAlimony());
			}else{
				hashmap.put("alimonyPayment", "1000");
		
			}
			
			hashmap.put("jobExpenses", "1000");
			
			if (loanAppFormVO.getCustomerEmploymentIncome() != null
			        && loanAppFormVO.getCustomerEmploymentIncome().size() > 0) {
				hashmap.put("jobRelatedPayment", (Integer.parseInt(Utils.unformatCurrencyField(loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome()
				        .getEmployedIncomePreTax()))*12)+"");
				
			} else {
				hashmap.put("jobRelatedPayment", "1000");
			}
			
			hashmap.put("userSSNnumber", loanAppFormVO.getUser()
			        .getCustomerDetail().getSsn());
			hashmap.put("baseIncome", Utils.unformatCurrencyField(loanAppFormVO
			        .getCustomerEmploymentIncome().get(0)
			        .getCustomerEmploymentIncome().getEmployedIncomePreTax()));
			hashmap.put("ProdLckdDays", "30");
			
			if(null == loanAppFormVO.getUser()
	        .getCustomerDetail().getSsn() || "".equalsIgnoreCase(loanAppFormVO.getUser()
	        .getCustomerDetail().getSsn())){
				
				loanAppFormVO.setSsnProvided(false);
				
			}
			
			if(null == loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn() || "".equalsIgnoreCase(loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn())){
						
						loanAppFormVO.setCbSsnProvided(false);
						
			}
			
			
			if ("Purchase".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getDescription())) {
				hashmap.put("loanAmount", Utils.unformatCurrencyField(loanAppFormVO.getPurchaseDetails()
				        .getLoanAmount()));
			} else {

				hashmap.put("loanAmount", Utils.unformatCurrencyField(loanAppFormVO.getRefinancedetails()
				        .getCurrentMortgageBalance()));
			}
			hashmap.put("applicantCity", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressCity());
			hashmap.put("applicantState", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressState());
			hashmap.put("applicantZipCode", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressZipCode());
			if(null== loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressStreet() || "".equalsIgnoreCase(loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressStreet())){
				hashmap.put("applicantAddress", "undisclosed");
			}else{
			hashmap.put("applicantAddress", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressStreet());
			}

			if(null != loanAppFormVO.getCustomerEmploymentIncome() && loanAppFormVO.getCustomerEmploymentIncome().size()>0){
			hashmap.put("borrowerEmplrNm",loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedAt());
			hashmap.put("borrowerEmplrJobTitle",loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getJobTitle());
			//hashmap.put("borrowerEmplrMonthlyIncome",loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedIncomePreTax());
			hashmap.put("borrowerEmplrStartDate",loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedSince());
			}
			
			
			
			hashmap = getBorrowerGovernmentQuestion(hashmap, loanAppFormVO);
			
			//"condition": "coborrowerWithSSNBoth",
			
			
			if(loanAppFormVO.getIsCoborrowerPresent() == false && loanAppFormVO.getSsnProvided() == true){
				hashmap = getBorrowerCredit(hashmap);
				condition= "noCoBorrowerWithSSN";
			}
			
			if(loanAppFormVO.getIsCoborrowerPresent() == false && loanAppFormVO.getSsnProvided() == false){
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000");
				hashmap = appendBorrowerDefCredScore(hashmap);
				condition= "noCoBorrowerWithoutSSN";
			}
			
			if(loanAppFormVO.getIsCoborrowerPresent() == true && loanAppFormVO.getIsSpouseOnLoan()== true && loanAppFormVO.getSsnProvided() == true && loanAppFormVO.getCbSsnProvided() == true ){
				hashmap = getBorrowerCredit(hashmap);
				hashmap = appendSpouseCoBorrowerDetails(hashmap,loanAppFormVO);
				condition = "coborrowerIsWifeWithSSNBoth";
			}

			if(loanAppFormVO.getIsCoborrowerPresent() == true && loanAppFormVO.getIsSpouseOnLoan()== true && loanAppFormVO.getSsnProvided() == false && loanAppFormVO.getCbSsnProvided() == false ){
				
				hashmap = appendSpouseCoBorrowerDetails(hashmap,loanAppFormVO);
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000000");
				hashmap.put("userCoborrowerSSNnumber","000000000");
				hashmap= appendBorrowerDefCredScore(hashmap);
				hashmap = appendSpCBDefCredScore(hashmap);
				condition = "coborrowerIsWifeWithoutSSNBoth";
			}
			
			if(loanAppFormVO.getIsCoborrowerPresent() == true && loanAppFormVO.getIsSpouseOnLoan()== false && loanAppFormVO.getSsnProvided() == true && loanAppFormVO.getCbSsnProvided() == true ){
				hashmap = getBorrowerCredit(hashmap);
				hashmap = appendCoBorrowerDetails(hashmap,loanAppFormVO);
				hashmap = getCoBorrowerCredit(hashmap);
				condition = "coborrowerWithSSNBoth";
			}
		    
			if(loanAppFormVO.getIsCoborrowerPresent() == true && loanAppFormVO.getIsSpouseOnLoan()== false && loanAppFormVO.getSsnProvided() == false && loanAppFormVO.getCbSsnProvided() == false ){
				hashmap = appendCoBorrowerDetails(hashmap,loanAppFormVO);
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000000");
				hashmap.put("userCoborrowerSSNnumber","000000000");
				hashmap.put("ApplicantCoBorrowerId","000000000");
				hashmap= appendBorrowerDefCredScore(hashmap);
				hashmap=appendCBDefCredScore(hashmap);
				condition = "coborrowerWithoutSSNBoth";
			}
			
			
		   
			JSONObject jsonObject = new JSONObject(hashmap);

			JSONObject json = new JSONObject();
			JSONObject jsonChild = new JSONObject();
		  
		    
		    
			
			
			
			jsonChild.put("condition", condition);
			jsonChild.put("sLoanNumber", loanNumber);
			jsonChild.put("sDataContentMap", jsonObject);
			jsonChild.put("format", "0");

			json.put("opName", "Save");
			json.put("loanVO", jsonChild);

			System.out.println("jsonMapObject Save operation" + json);

			return json;

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	String getYearsSpent(String purchaseTime){
		int yeardiff = 0;
		int tempTime = 0;
		if(null !=purchaseTime && purchaseTime.indexOf(" ")>0){
		tempTime = Integer.parseInt(purchaseTime.split(" ")[1]);
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
        yeardiff = year - tempTime;
	
		return yeardiff+"";
	}
	
	HashMap<String, String> appendSpouseCoBorrowerDetails(HashMap<String, String> hashmap,LoanAppFormVO loanAppFormVO){
		if(loanAppFormVO.getCustomerSpouseDetail()!=null){
		hashmap.put("firstCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseName());	
		hashmap.put("middleCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseLastName());
		hashmap.put("lastCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseLastName());
		hashmap.put("dateOfCoborrowerBirth",new SimpleDateFormat("yyyy-MM-dd").format(new Date(loanAppFormVO.getCustomerSpouseDetail().getSpouseDateOfBirth())));
		hashmap.put("baseCoborrowerIncome","100000");
		hashmap.put("applicantCoborrowerAddress",loanAppFormVO.getCustomerSpouseDetail().getStreetAddress());
		hashmap.put("userCoborrowerSSNnumber",loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn());
		hashmap.put("applicationCoborrowerHomePhone", loanAppFormVO.getCustomerSpouseDetail().getSpouseSecPhoneNumber());
		if(null != loanAppFormVO.getCustomerSpouseEmploymentIncome() && loanAppFormVO.getCustomerSpouseEmploymentIncome().size()>0){
			hashmap.put( "applicationCoborrowerEmplrName",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedAt());
			hashmap.put( "applicationCoborrowerEmployementTitle",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getJobTitle());
			//hashmap.put( "applicationCoborrowerMontlyIncome",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedIncomePreTax());
			hashmap.put( "applicationCoborrowerEmploymentStartDate",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedSince());
						
         }
		
		
		
		
		}
		
		hashmap = getCoBorrowerGovernmentQuestion(hashmap,loanAppFormVO);
		return hashmap;
	}
	
	HashMap<String, String> appendBorrowerDefCredScore(HashMap<String, String> hashmap){
		
		hashmap.put("borrowerExperianScore", "800");
		hashmap.put("borrowerEquifaxScore", "800");
		hashmap.put("borrowerTransUnionScore", "800");
		return   hashmap; 
	}
	
	
	HashMap<String, String> appendSpCBDefCredScore(HashMap<String, String> hashmap){
	
		hashmap.put("ExperianCoborrowerWifeScore", "800");
		hashmap.put("EquifaxCoborrowerWifeScore", "800");
		hashmap.put("TransUnionCoborrowerWifeScore", "800");
		return   hashmap; 
	}
	
	
	HashMap<String, String> appendCBDefCredScore(HashMap<String, String> hashmap){
		
		hashmap.put("ExperianCoborrowerScore", "800");
		hashmap.put("EquifaxCoborrowerScore", "800");
		hashmap.put("TransUnionCoborrowerScore", "800");
		return   hashmap; 
	}
	
	   
    
    
	HashMap<String, String> appendCoBorrowerDetails(HashMap<String, String> hashmap,LoanAppFormVO loanAppFormVO){
		if(loanAppFormVO.getCustomerSpouseDetail()!=null){
		hashmap.put("firstCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseName());
		hashmap.put( "middleCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseLastName());
		hashmap.put( "lastCoborrowerName",loanAppFormVO.getCustomerSpouseDetail().getSpouseLastName());
		hashmap.put("dateOfCoborrowerBirth",new SimpleDateFormat("yyyy-MM-dd").format(new Date(loanAppFormVO.getCustomerSpouseDetail().getSpouseDateOfBirth())));
		hashmap.put( "baseCoborrowerIncome","100000");
		hashmap.put("applicantCoborrowerAddress",loanAppFormVO.getCustomerSpouseDetail().getStreetAddress());
		hashmap.put("userCoborrowerSSNnumber",loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn());
		hashmap.put( "ApplicantCoBorrowerId",loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn());
		hashmap.put( "alimonyCoborrowerName","NONE");
		//hashmap.put("alimonyCoborrowerPayment","1000");
		if(loanAppFormVO.getCustomerSpouseDetail().getChildSupportAlimony() != null && loanAppFormVO.getCustomerSpouseDetail().getChildSupportAlimony() != ""){
			hashmap.put("alimonyCoborrowerPayment", Utils.unformatCurrencyField(loanAppFormVO.getCustomerSpouseDetail().getChildSupportAlimony()));
			}else{
				hashmap.put("alimonyCoborrowerPayment", "1000");
		
			}
		
		
		hashmap.put( "jobCoborrowerExpenses","1000");
		//hashmap.put( "jobRelatedCoborrowerPayment","100000");
		
		if (loanAppFormVO.getCustomerSpouseEmploymentIncome() != null
		        && loanAppFormVO.getCustomerSpouseEmploymentIncome().size() > 0) {
				
			
			hashmap.put("jobRelatedCoborrowerPayment", (Integer.parseInt(Utils.unformatCurrencyField(loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome()
			        .getEmployedIncomePreTax()))*12)+"");
		} else {
			hashmap.put("jobRelatedCoborrowerPayment", "10000");
		}
		
		
		
		
		hashmap.put( "applicantCoborrowerCity",loanAppFormVO.getCustomerSpouseDetail().getCity());
		hashmap.put("applicantCoborrowerState",loanAppFormVO.getCustomerSpouseDetail().getState());
		hashmap.put( "applicantCoborrowerZipCode",loanAppFormVO.getCustomerSpouseDetail().getCity());	
		}
		
		
		hashmap.put("applicationCoborrowerHomePhone", loanAppFormVO.getCustomerSpouseDetail().getSpouseSecPhoneNumber());
		
		if(null != loanAppFormVO.getCustomerSpouseEmploymentIncome() && loanAppFormVO.getCustomerSpouseEmploymentIncome().size()>0){
		hashmap.put( "applicationCoborrowerEmplrName",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedAt());
		hashmap.put( "applicationCoborrowerEmployementTitle",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getJobTitle());
		//hashmap.put( "applicationCoborrowerMontlyIncome",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedIncomePreTax());
		hashmap.put( "applicationCoborrowerEmploymentStartDate",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedSince());
		
		}
		hashmap = getCoBorrowerGovernmentQuestion(hashmap,loanAppFormVO);
		return hashmap;
		
	}
	
	
	
	
	
	
	HashMap<String, String> getBorrowerCredit(HashMap<String, String> hashmap){
		
		hashmap.put("creditCardId", "eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("LOGIN_NAME", "testact");
		hashmap.put("PASSWORD", "1234nexera");
		hashmap.put("equifaxStatus", "Y");
		hashmap.put("experianStatus", "Y");
		hashmap.put("transunionStatus", "Y");
		
		return hashmap;
	}

	HashMap<String, String> getCoBorrowerCredit(HashMap<String, String> hashmap){
		hashmap.put("creditCoborrowerCardId","eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("equifaxCoborrowerStatus","Y");
		hashmap.put("experianCoborrowerStatus","Y");
		hashmap.put("LOGIN_Coborrower_NAME","testact");
		hashmap.put("PASS_COBORROWER_WORD","1234nexera");
		hashmap.put("transunionCoborrowerStatus","Y");
		
		return hashmap;
	}
	
	HashMap<String, String> getBorrowerGovernmentQuestion(HashMap<String, String> hashmap,LoanAppFormVO loanAppFormVO){
	
		
		if(null != loanAppFormVO.getGovernmentquestion()){
		
				
		hashmap.put("borrowerDecJudgment",returnYesNo(loanAppFormVO.getGovernmentquestion().isOutstandingJudgments()));
		hashmap.put("borrowerDecBankrupt",returnYesNo(loanAppFormVO.getGovernmentquestion().isBankrupt()));
		hashmap.put("borrowerDecForeclosure",returnYesNo(loanAppFormVO.getGovernmentquestion().isPropertyForeclosed()));
		hashmap.put("borrowerDecLawsuit",returnYesNo(loanAppFormVO.getGovernmentquestion().isLawsuit()));
		hashmap.put("borrowerDecObligated",returnYesNo(loanAppFormVO.getGovernmentquestion().isObligatedLoan()));
		hashmap.put("borrowerDecDelinquent",returnYesNo(loanAppFormVO.getGovernmentquestion().isFederalDebt()));
		hashmap.put("borrowerDecAlimony",returnYesNo(loanAppFormVO.getGovernmentquestion().isObligatedToPayAlimony()));
		hashmap.put("borrowerDecBorrowing",returnYesNo(loanAppFormVO.getGovernmentquestion().getIsDownPaymentBorrowed()));
		hashmap.put("borrowerDecEndorser",returnYesNo(loanAppFormVO.getGovernmentquestion().isEndorser()));
		hashmap.put("borrowerDecCitizen",returnYesNo(loanAppFormVO.getGovernmentquestion().isUSCitizen()));
		if(loanAppFormVO.getGovernmentquestion().isUSCitizen() == false){
		hashmap.put("borrowerDecResidency",returnYesNo(loanAppFormVO.getGovernmentquestion().getPermanentResidentAlien()));
		}else{
	    	 hashmap.put("applicationCoborrowerDecResidency","No");
	     }
		hashmap.put("borrowerDecOcc",returnYesNo(loanAppFormVO.getGovernmentquestion().isOccupyPrimaryResidence()));
		hashmap.put("borrowerDecPastOwnedPropT",getPropertyTypeEnum(loanAppFormVO.getGovernmentquestion().getTypeOfPropertyOwned()));
		hashmap.put("TitleTborrowerDecPastOwnedProperty",getPropertyTitleEnum(loanAppFormVO.getGovernmentquestion().getPropertyTitleStatus()));
		hashmap.put("borrowerNoFurnish",loanAppFormVO.getGovernmentquestion().getSkipOptionalQuestion()+"");
		hashmap.put("borrowerHispanicT",getEthnicityEnum(loanAppFormVO.getGovernmentquestion().getEthnicity()));
		hashmap=getBorrowerRace(hashmap,loanAppFormVO);
		if("male".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getSex()))
		hashmap.put("borrowerGender", "1");
		else
			hashmap.put("borrowerGender", "2");	
		}
			
		 
		return hashmap;
	}
	
	
	

	
	String getPropertyTypeEnum(String propertyType){
		if(null != propertyType){
			if("Your Primary Residence".equalsIgnoreCase(propertyType))
				return "1";
			else if("A Second Home".equalsIgnoreCase(propertyType))
				return "2";
				else if("An Investment Property".equalsIgnoreCase(propertyType))
					return "3";
				else
					return "0";
		}
		return "0";
	
		
	}
	
	
	
	
	
	String getPropertyTitleEnum(String propertyTitle){
		
		if(null !=propertyTitle){
			if("I was the sole title holder".equalsIgnoreCase(propertyTitle))
				return "1";
			else if("I held the title jointly with my spouse".equalsIgnoreCase(propertyTitle))
				return "2";
			else if("I held title jointly with another person".equalsIgnoreCase(propertyTitle))
				return "3";
			else 
				return "0";
		}
		
		return "0";
	}
	
	
	
	String getEthnicityEnum(String ethnicity){
	if("hispanic".equalsIgnoreCase(ethnicity))	 return "1";
	else if("latino".equalsIgnoreCase(ethnicity)) return "2";
	else return "0";
	}
	
	
	HashMap<String, String> getBorrowerRace(HashMap<String, String> hashmap,LoanAppFormVO loanAppFormVO){
		
		hashmap.put("borrowerIsAmericanIndian","false");
		hashmap.put("borrowerIsAsian","false");
		hashmap.put("borrowerIsBlack","false");
		hashmap.put("borrowerIsPacificIslander","false");
		hashmap.put("borrowerIsWhite","false");
		
		if("americanIndian".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getRace()))
			hashmap.put("borrowerIsAmericanIndian","true");
		else if("nativeHawaiian".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getRace()))
			hashmap.put("borrowerIsPacificIslander","true");
			else if("black".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getRace()))
				hashmap.put("borrowerIsBlack","true");
				else if("white".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getRace()))
					hashmap.put("borrowerIsWhite","true");
					else if("asian".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getRace()))
					hashmap.put("borrowerIsAsian","true");
					else return hashmap;
		            	
	    return hashmap;
	}
	
	String returnYesNo(boolean key){
		if(key)return "Yes";
		else return "No";
	}
	
	
	
	
HashMap<String, String> getCoBorrowerGovernmentQuestion(HashMap<String, String> hashmap,LoanAppFormVO loanAppFormVO){
	 
     
	
	
	if(null != loanAppFormVO.getSpouseGovernmentQuestions()){
     hashmap.put("applicationCoborrowerDecJudgment",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isOutstandingJudgments()));
     hashmap.put("applicationCoborrowerDecBankrupt",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isBankrupt()));
     hashmap.put("applicationCoborrowerDecForeclosure",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isPropertyForeclosed()));
     hashmap.put("applicationCoborrowerDecLawsuit",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isLawsuit()));
     hashmap.put("applicationCoborrowerDecObligated",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isObligatedLoan()));
     hashmap.put("applicationCoborrowerDecDelinquent",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isFederalDebt()));
     hashmap.put("applicationCoborrowerDecAlimony",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isObligatedToPayAlimony()));
     hashmap.put("applicationCoborrowerDecBorrowing",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().getIsDownPaymentBorrowed()));
     hashmap.put("applicationCoborrowerDecEndorser",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isEndorser()));
     hashmap.put("applicationCoborrowerDecCitizen",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isUSCitizen()));
     if(loanAppFormVO.getSpouseGovernmentQuestions().isUSCitizen() == false){
     hashmap.put("applicationCoborrowerDecResidency",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isPermanentResidentAlien()));
     }else{
    	 hashmap.put("applicationCoborrowerDecResidency","No");
     }
     hashmap.put("applicationCoborrowerDecOcc",returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions().isOccupyPrimaryResidence()));
     hashmap.put("applicationCoborrowerDecPastOwnedPropT",getPropertyTypeEnum(loanAppFormVO.getSpouseGovernmentQuestions().getTypeOfPropertyOwned()));
     hashmap.put("titleTApplicationCoborrowerDecPastOwnedProp",getPropertyTitleEnum(loanAppFormVO.getSpouseGovernmentQuestions().getPropertyTitleStatus()));
     hashmap.put("applicationCoborrowerNoFurnish",loanAppFormVO.getSpouseGovernmentQuestions().getSkipOptionalQuestion()+"");
     hashmap.put("borrowerHispanicT",getEthnicityEnum(loanAppFormVO.getSpouseGovernmentQuestions().getEthnicity()));
     hashmap=getBorrowerRace(hashmap,loanAppFormVO);
     if("male".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion().getSex()))
    		hashmap.put("applicationCoborrowerGender", "1");
    		else{
    			hashmap.put("applicationCoborrowerGender", "2");	
    		}
     
    
     
	}
		
      return hashmap;
	}
	
	

}
