package com.nexera.web.rest;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.nexera.common.entity.CustomerBankAccountDetails;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerRetirementAccountDetails;
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
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/application/")
public class ApplicationFormRestService {
	private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormRestService.class);
	@Autowired
	private LoanAppFormService loanAppFormService;

	HashMap<String, Integer> cache = new HashMap<String, Integer>();
	
	
	
	//@RequestBody
	@RequestMapping(value = "/applyloan", method = RequestMethod.POST)
	public @ResponseBody String createApplication(String appFormData) {

		Gson gson = new Gson();


	//	LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,LoanAppFormVO.class);

		try{ 
			
			
			System.out.println("appFormData is"+appFormData);
			
			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,LoanAppFormVO.class);

			System.out.println(loaAppFormVO.toString());
			
			System.out.println("loaAppFormVO.getId()"+loaAppFormVO.getId());
			//System.out.println("loaAppFormVO.getUser().getId()"+loaAppFormVO.getUser().getId());
			//System.out.println("loaAppFormVO.getUser().getCustomerDetail().getId()"+loaAppFormVO.getUser().getCustomerDetail().getId());
			
			//System.out.println("loaAppFormVO.getPropertyTypeMaster().getId()"+loaAppFormVO.getPropertyTypeMaster().getId());
			//System.out.println("loaAppFormVO.getGovernmentquestion().getId()"+loaAppFormVO.getGovernmentquestion().getId());
			//System.out.println("loaAppFormVO.getRefinancedetails().getId()"+loaAppFormVO.getRefinancedetails().getId());
			
			
			System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome());
			//System.out.println("loaAppFormVO.getCustomerSpouseDetail().getMonthlyPension()"+loaAppFormVO.getCustomerSpouseDetail().getSpouseName());
			//System.out.println("loaAppFormVO.getCustomerSpouseDetail().getMonthlyPension()"+loaAppFormVO.getCustomerSpouseDetail().getMonthlyPension());
			//System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome().size());
			//System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getId());
			//System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedAt());
			//System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome().get(1).getCustomerEmploymentIncome().getId());
			//System.out.println("loaAppFormVO.getCustomerEmploymentIncome().()"+loaAppFormVO.getCustomerEmploymentIncome().get(1).getCustomerEmploymentIncome().getEmployedAt());
			
			
			
				
			
			System.out.println("cache.get(loanAppFormId)"+cache.get("loanAppFormId"));
			if(cache.get("loanAppFormId")!= null &&loaAppFormVO.getId()==0 && cache.get("loanAppFormId") !=0)
			{   
				loaAppFormVO.setId(cache.get("loanAppFormId"));
			}
			
			
				System.out.println("cache.get(userId)"+cache.get("userId"));
			if(cache.get("userId")!=null && loaAppFormVO.getUser().getId()==0 && cache.get("userId")!=0)
			{		
				loaAppFormVO.getUser().setId(cache.get("userId"));
			}		
					

			System.out.println("cache.get(customerId)"+cache.get("customerId"));
			if(cache.get("userId")!=null && loaAppFormVO.getUser().getCustomerDetail().getId()==0 && cache.get("customerId")!=0 )
			{
				loaAppFormVO.getUser().getCustomerDetail().setId(cache.get("customerId"));
			}
			
			System.out.println("cache.get(customerSpouseDetail)"+cache.get("customerSpouseDetail"));
			if(loaAppFormVO.getCustomerSpouseDetail()!=null &&cache.get("customerSpouseDetail")!=null && loaAppFormVO.getCustomerSpouseDetail().getId()==0 && cache.get("customerSpouseDetail")!=0 )
			{
				loaAppFormVO.getCustomerSpouseDetail().setId(cache.get("customerSpouseDetail"));
			}
			
			// Customer Income 
			System.out.println("cache.get(customerEmploymentIncome0)"+cache.get("customerEmploymentIncome0"));
			if(loaAppFormVO.getCustomerEmploymentIncome() !=null && cache.get("customerEmploymentIncome0")!=null && loaAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getId()==0 && cache.get("customerEmploymentIncome0")!=0 )
			{   System.out.println("Inside customerEmploymentIncome0 ");
					Iterator<CustomerEmploymentIncomeVO> itr = loaAppFormVO.getCustomerEmploymentIncome().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				
					
					CustomerEmploymentIncomeVO customeremploymentincomeVO= itr.next();
					System.out.println("customeremploymentincomeVO.getEmployedAt()"+customeremploymentincomeVO.getCustomerEmploymentIncome().getEmployedAt());		
					System.out.println("cache.get(customerEmploymentIncome"+cache.get("customerEmploymentIncome"+counter+""));
				if(cache.get("customerEmploymentIncome"+counter+"") !=null){
					loaAppFormVO.getCustomerEmploymentIncome().get(counter).getCustomerEmploymentIncome().setId(cache.get("customerEmploymentIncome"+counter+""));
				}
				counter++;
				}
			}
			
			// Customer Bank Account 
			
			if(cache.get("customerBankAccountDetails0")!=null && loaAppFormVO.getCustomerBankAccountDetails().get(0).getCustomerBankAccountDetails().getId()==0 && cache.get("customerBankAccountDetails0")!=0 )
			{
			
				Iterator<CustomerBankAccountDetailsVO> itr = loaAppFormVO.getCustomerBankAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				
					
					CustomerBankAccountDetailsVO customerBankAccountDetailsVO= itr.next();
					System.out.println("customerBankAccountDetailsVO.getAccountSubType()"+customerBankAccountDetailsVO.getCustomerBankAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerBankAccountDetails().get(counter).getCustomerBankAccountDetails().setId(cache.get("customerBankAccountDetails"+counter+""));
				counter++;
				}
			
			}
			
			// Customer Retirement Account 
			
			if(cache.get("customerRetirementAccountDetails0")!=null && loaAppFormVO.getCustomerRetirementAccountDetails().get(0).getCustomerRetirementAccountDetails().getId()==0 && cache.get("customerRetirementAccountDetails0")!=0 )
			{
				
				Iterator<CustomerRetirementAccountDetailsVO> itr = loaAppFormVO.getCustomerRetirementAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
					CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO= itr.next();
					System.out.println("customerBankAccountDetailsVO.getAccountSubType()"+customerRetirementAccountDetailsVO.getCustomerRetirementAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerRetirementAccountDetails().get(counter).getCustomerRetirementAccountDetails().setId(cache.get("customerRetirementAccountDetails"+counter+""));
				counter++;
				}
			}
			
			// Customer Other Account 
			
			if(cache.get("customerOtherAccountDetails0")!=null && loaAppFormVO.getCustomerOtherAccountDetails().get(0).getCustomerOtherAccountDetails().getId()==0 && cache.get("customerOtherAccountDetails0")!=0 )
			{
				Iterator<CustomerOtherAccountDetailsVO> itr = loaAppFormVO.getCustomerOtherAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
					CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO= itr.next();
					System.out.println("customerBankAccountDetailsVO.getAccountSubType()"+customerOtherAccountDetailsVO.getCustomerOtherAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerOtherAccountDetails().get(counter).getCustomerOtherAccountDetails().setId(cache.get("customerOtherAccountDetails"+counter+""));
				counter++;
				}
			
			
			}
		
				
			
			
			////Spouse Income Related Details 
			
			
			// Customer Spouse Income 
			System.out.println("cache.get(customerSpouseEmploymentIncome0)"+cache.get("customerSpouseEmploymentIncome0"));
			if(loaAppFormVO.getCustomerSpouseEmploymentIncome() !=null && cache.get("customerSpouseEmploymentIncome0")!=null && loaAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getId()==0 && cache.get("customerSpouseEmploymentIncome0")!=0 )
			{
					Iterator<CustomerSpouseEmploymentIncomeVO> itr = loaAppFormVO.getCustomerSpouseEmploymentIncome().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				
					
					CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentincomeVO= itr.next();
					System.out.println("customeremploymentincomeVO.getEmployedAt()"+customerSpouseEmploymentincomeVO.getCustomerSpouseEmploymentIncome().getEmployedAt());		
				loaAppFormVO.getCustomerSpouseEmploymentIncome().get(counter).getCustomerSpouseEmploymentIncome().setId(cache.get("customerSpouseEmploymentIncome"+counter+""));
				counter++;
				}
			}
			
			// Customer Spouse Bank Account 
			
			if(cache.get("customerSpouseBankAccountDetails0")!=null && loaAppFormVO.getCustomerSpouseBankAccountDetails().get(0).getCustomerSpouseBankAccountDetails().getId()==0 && cache.get("customerSpouseBankAccountDetails0")!=0 )
			{
			
				Iterator<CustomerSpouseBankAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseBankAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				
					
					CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO= itr.next();
					System.out.println("customerSpouseBankAccountDetailsVO.getAccountSubType()"+customerSpouseBankAccountDetailsVO.getCustomerSpouseBankAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerSpouseBankAccountDetails().get(counter).getCustomerSpouseBankAccountDetails().setId(cache.get("customerSpouseBankAccountDetails"+counter+""));
				counter++;
				}
			
			}
			
			// Customer Spouse Retirement Account 
			
			if(cache.get("customerSpouseRetirementAccountDetails0")!=null && loaAppFormVO.getCustomerSpouseRetirementAccountDetails().get(0).getCustomerSpouseRetirementAccountDetails().getId()==0 && cache.get("customerSpouseRetirementAccountDetails0")!=0 )
			{
				
				Iterator<CustomerSpouseRetirementAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseRetirementAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
					CustomerSpouseRetirementAccountDetailsVO customerSpouseRetirementAccountDetailsVO= itr.next();
					System.out.println("customerSpouseBankAccountDetailsVO.getAccountSubType()"+customerSpouseRetirementAccountDetailsVO.getCustomerSpouseRetirementAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerSpouseRetirementAccountDetails().get(counter).getCustomerSpouseRetirementAccountDetails().setId(cache.get("customerSpouseRetirementAccountDetails"+counter+""));
				counter++;
				}
			}
			
			// Customer Other Account 
			
			if(cache.get("customerSpouseOtherAccountDetails0")!=null && loaAppFormVO.getCustomerSpouseOtherAccountDetails().get(0).getCustomerSpouseOtherAccountDetails().getId()==0 && cache.get("customerSpouseOtherAccountDetails0")!=0 )
			{
				Iterator<CustomerSpouseOtherAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseOtherAccountDetails().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
					CustomerSpouseOtherAccountDetailsVO customerSpouseOtherAccountDetailsVO= itr.next();
					System.out.println("customerSpouseBankAccountDetailsVO.getAccountSubType()"+customerSpouseOtherAccountDetailsVO.getCustomerSpouseOtherAccountDetails().getAccountSubType());		
				loaAppFormVO.getCustomerSpouseOtherAccountDetails().get(counter).getCustomerSpouseOtherAccountDetails().setId(cache.get("customerSpouseOtherAccountDetails"+counter+""));
				counter++;
				}
			
			
			}
				
			
			System.out.println("cache.get(propertyTypeMasterId)"+cache.get("propertyTypeMasterId"));
			if(cache.get("propertyTypeMasterId") !=null && loaAppFormVO.getPropertyTypeMaster().getId()==0 && cache.get("propertyTypeMasterId") !=0){
				loaAppFormVO.getPropertyTypeMaster().setId(cache.get("propertyTypeMasterId"));
			}
			
					
			
				System.out.println("cache.get(governmentQuestionId)"+cache.get("governmentQuestionId"));
			if(cache.get("governmentQuestionId")!=null && loaAppFormVO.getGovernmentquestion().getId()==0 && cache.get("governmentQuestionId")!=0)
			{
				loaAppFormVO.getGovernmentquestion().setId(cache.get("governmentQuestionId"));
			}
		
		
			
			System.out.println("cache.get(spouseGovernmentQuestionId)"+cache.get("spouseGovernmentQuestionId"));
			if(cache.get("spouseGovernmentQuestionId")!=null && loaAppFormVO.getId()==0 && cache.get("spouseGovernmentQuestionId")!=0)
			{
				loaAppFormVO.getSpouseGovernmentQuestions().setId(cache.get("spouseGovernmentQuestionId"));
			}
					
			
				System.out.println("cache.get(refinanceDetailsId)"+cache.get("refinanceDetailsId"));
			if(cache.get("refinanceDetailsId")!=null && loaAppFormVO.getRefinancedetails().getId() == 0 &&  cache.get("refinanceDetailsId")!=0)
			{
				loaAppFormVO.getRefinancedetails().setId(cache.get("refinanceDetailsId"));
			}
				
			
			if(cache.get("purchaseDetails")!=null && loaAppFormVO.getPurchaseDetails().getId() == 0 &&  cache.get("purchaseDetails")!=0)
			{
				loaAppFormVO.getPurchaseDetails().setId(cache.get("purchaseDetails"));
			}
				
			
			
			System.out.println("Inside 4"+loaAppFormVO.getUser().getFirstName());
			System.out.println("Inside 4.1"+loaAppFormVO.getUser().getCustomerDetail().getAddressCity());
			
			LoanAppForm loanAppForm = loanAppFormService.create(loaAppFormVO);
			
					
			cache.put("loanAppFormId", loanAppForm.getId());
			cache.put("userId", loanAppForm.getUser().getId());
			cache.put("customerId", loanAppForm.getUser().getCustomerDetail().getId());
			cache.put("propertyTypeMasterId", loanAppForm.getPropertyTypeMaster().getId());
			System.out.println("cache propertyTypeMasterId"+loanAppForm.getPropertyTypeMaster().getId());
			
			System.out.println("cache refinacne"+loanAppForm.getRefinancedetails().getId());
			cache.put("governmentQuestionId", loanAppForm.getGovernmentquestion().getId());
			cache.put("refinanceDetailsId", loanAppForm.getRefinancedetails().getId());
			cache.put("purchaseDetails", loanAppForm.getPurchaseDetails().getId());
			
			if(loanAppForm.getCustomerspousedetail() !=null){
			cache.put("customerSpouseDetail", loanAppForm.getCustomerspousedetail().getId());
			
			
			LOG.info("cache.putcustomerSpouseDetail"+loanAppForm.getCustomerspousedetail().getId());
			}
			
		
			
			if(loanAppForm.getCustomerEmploymentIncome() !=null){
				
				
				Iterator<CustomerEmploymentIncome> itr = loanAppForm.getCustomerEmploymentIncome().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				CustomerEmploymentIncome customeremploymentincome= itr.next();
				System.out.println("customeremploymentincomeVO.getEmployedAt()"+customeremploymentincome.getEmployedAt());
				System.out.println("customeremploymentincomeVO.getCustomerEmploymentIncome().getId()"+customeremploymentincome.getId());
				cache.put("customerEmploymentIncome"+counter, customeremploymentincome.getId());
				counter++;
				}
					
			}
			
			
		  if(loanAppForm.getCustomerBankAccountDetails()!=null){  
			Iterator<CustomerBankAccountDetailsVO> itr = loaAppFormVO.getCustomerBankAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerBankAccountDetailsVO customerBankAccountDetailsVO= itr.next();
			System.out.println("customerBankAccountDetailsVO.getCustomerBankAccountDetails().getAccountSubType()"+customerBankAccountDetailsVO.getCustomerBankAccountDetails().getAccountSubType());		
			cache.put("customerBankAccountDetails"+counter, loaAppFormVO.getCustomerBankAccountDetails().get(counter).getCustomerBankAccountDetails().getId());
			counter++;
			}
			
		  }
		  
		  
			if(loanAppForm.getCustomerRetirementAccountDetails() !=null){
			
			Iterator<CustomerRetirementAccountDetailsVO> itr = loaAppFormVO.getCustomerRetirementAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO= itr.next();
			System.out.println("customerRetirementAccountDetailsVO.getCustomerRetirementAccountDetails().getAccountSubType()"+customerRetirementAccountDetailsVO.getCustomerRetirementAccountDetails().getAccountSubType());		
			cache.put("customerRetirementAccountDetails"+counter, loaAppFormVO.getCustomerRetirementAccountDetails().get(counter).getCustomerRetirementAccountDetails().getId());
			counter++;
			}
			}
			
			
			
			
			if(loanAppForm.getCustomerOtherAccountDetails() !=null){
			Iterator<CustomerOtherAccountDetailsVO> itr = loaAppFormVO.getCustomerOtherAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO= itr.next();
			System.out.println("customerOtherAccountDetailsVO.getCustomerOtherAccountDetails().getAccountSubType()"+customerOtherAccountDetailsVO.getCustomerOtherAccountDetails().getAccountSubType());		
			cache.put("customerOtherAccountDetails"+counter, loaAppFormVO.getCustomerOtherAccountDetails().get(counter).getCustomerOtherAccountDetails().getId());
			counter++;
			}
			}
			
			
			
			////Spouse Income Details 
			
			if(loanAppForm.getCustomerSpouseEmploymentIncome() !=null){
				
				
				Iterator<CustomerSpouseEmploymentIncomeVO> itr = loaAppFormVO.getCustomerSpouseEmploymentIncome().iterator();
				int counter = 0 ; 
				while(itr.hasNext()){
				CustomerSpouseEmploymentIncomeVO customerSpouseEmploymentincomeVO= itr.next();
				System.out.println("customerSpouseemploymentincomeVO.getEmployedAt()"+customerSpouseEmploymentincomeVO.getCustomerSpouseEmploymentIncome().getEmployedAt());		
				System.out.println("customerSpouseemploymentincomeVO.ID()"+customerSpouseEmploymentincomeVO.getCustomerSpouseEmploymentIncome().getId());
				cache.put("customerSpouseEmploymentIncome"+counter, loaAppFormVO.getCustomerSpouseEmploymentIncome().get(counter).getCustomerSpouseEmploymentIncome().getId());
				counter++;
				}
					
			}
			
			
		  if(loanAppForm.getCustomerSpouseBankAccountDetails()!=null){  
			Iterator<CustomerSpouseBankAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseBankAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerSpouseBankAccountDetailsVO customerSpouseBankAccountDetailsVO= itr.next();
			System.out.println("customerSpouseBankAccountDetailsVO.getCustomerSpouseBankAccountDetails().getAccountSubType()"+customerSpouseBankAccountDetailsVO.getCustomerSpouseBankAccountDetails().getAccountSubType());		
			cache.put("customerSpouseBankAccountDetails"+counter, loaAppFormVO.getCustomerSpouseBankAccountDetails().get(counter).getCustomerSpouseBankAccountDetails().getId());
			counter++;
			}
			
		  }
		  
		  
			if(loanAppForm.getCustomerSpouseRetirementAccountDetails() !=null){
			
			Iterator<CustomerSpouseRetirementAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseRetirementAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerSpouseRetirementAccountDetailsVO customerSpouseRetirementAccountDetailsVO= itr.next();
			System.out.println("customerSpouseRetirementAccountDetailsVO.getCustomerSpouseRetirementAccountDetails().getAccountSubType()"+customerSpouseRetirementAccountDetailsVO.getCustomerSpouseRetirementAccountDetails().getAccountSubType());		
			cache.put("customerSpouseRetirementAccountDetails"+counter, loaAppFormVO.getCustomerSpouseRetirementAccountDetails().get(counter).getCustomerSpouseRetirementAccountDetails().getId());
			counter++;
			}
			}
			
			
			
			
			if(loanAppForm.getCustomerSpouseOtherAccountDetails() !=null){
			Iterator<CustomerSpouseOtherAccountDetailsVO> itr = loaAppFormVO.getCustomerSpouseOtherAccountDetails().iterator();
			int counter = 0 ; 
			while(itr.hasNext()){
			CustomerSpouseOtherAccountDetailsVO customerSpouseOtherAccountDetailsVO= itr.next();
			System.out.println("customerSpouseOtherAccountDetailsVO.getCustomerSpouseOtherAccountDetails().getAccountSubType()"+customerSpouseOtherAccountDetailsVO.getCustomerSpouseOtherAccountDetails().getAccountSubType());		
			cache.put("customerSpouseOtherAccountDetails"+counter, loaAppFormVO.getCustomerSpouseOtherAccountDetails().get(counter).getCustomerSpouseOtherAccountDetails().getId());
			counter++;
			}
			}
			
			
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(null);
		return new Gson().toJson(responseVO);
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
public @ResponseBody String createLoan(String appFormData) {
	System.out.println("Inside createLoan"+appFormData);
	Gson gson = new Gson();
	String lockRateData = null;
	try{
	LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,LoanAppFormVO.class);
	String loanNumber = invokeRest(prepareCreateLoanJson("DIRECT-REFI-MASTER TEMPLATE").toString());
	System.out.println("createLoanResponse is"+loanNumber);
	if(!"".equalsIgnoreCase(loanNumber))
		{
		
		String response = invokeRest((saveLoan(loanNumber,loaAppFormVO)).toString());
		System.out.println("Save Loan Response is "+response);
		 //JSONObject jsonObject = new JSONObject(response);
		 //LOG.info("Response Returned from save Loan Service is"+jsonObject.get("responseCode").toString());
		
		 if(response != null){
			 lockRateData = loadLoanRateData(loanNumber);
			 System.out.println("lockRateData"+lockRateData);
		 }
		}
	}catch(Exception e){
		e.printStackTrace();
		return "error";
	}
	return lockRateData;
	
}



@RequestMapping(value = "/lockLoanRate", method = RequestMethod.POST)
public @ResponseBody String lockLoanRateLoan(String appFormData) {
	System.out.println("Inside lockLoanRateLoan"+appFormData);
	Gson gson = new Gson();
	String lockRateData = null;
	try{
		LoanLockRateVO loanLockRateVO = gson.fromJson(appFormData,LoanLockRateVO.class);
		System.out.println("lockLoanRate is"+loanLockRateVO.getIlpTemplateId());
	String response = invokeRest(prepareLockLoanRateJson(loanLockRateVO).toString());
	System.out.println("lockLoanRate is"+response);
	
		
	}catch(Exception e){
		e.printStackTrace();
		return "error";
	}
	return lockRateData;
	
}



private String loadLoanRateData(String loanNumber)
{
	Gson gson = new Gson();
	List<TeaserRateResponseVO> teaserRateList = null;
	RateCalculatorRestService rateService = new RateCalculatorRestService();
	JSONObject json = new JSONObject();
	JSONObject jsonChild = new JSONObject();
	try {
		jsonChild.put("sLoanNumber",loanNumber);		
		jsonChild.put("sXmlQueryMap",new JSONObject("{}"));	
		jsonChild.put("format",0);	
		json.put("opName","Load" );
		json.put("loanVO", jsonChild); 
		System.out.println("jsonMapObject load Loandata"+json);		
		//JSONObject jsonObject = new JSONObject(invokeRest(json.toString()));
		teaserRateList = rateService.parseLqbResponse(retrievePricingDetails(invokeRest(json.toString())));
		
	} catch (JSONException e) {
		e.printStackTrace();
	}  
		
	System.out.println("loadLoanRateData"+gson.toJson(teaserRateList));
	return gson.toJson(teaserRateList);
	
}



public JSONObject prepareCreateLoanJson(String templateName)
{
	JSONObject json = new JSONObject();
	JSONObject jsonChild = new JSONObject();
	try {
		jsonChild.put("sTemplateName",templateName);		
		json.put("opName","Create" );
		
		json.put("loanVO", jsonChild); 
		
		System.out.println("jsonMapObject"+json);
	} catch (JSONException e) {
		e.printStackTrace();
	}  
		return json;
}


private String retrievePricingDetails(String xml)
{
	String pricingResultXml = null;
	try{
		
			DocumentBuilderFactory factory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			 Document document = builder.parse(new InputSource(new StringReader(xml)));
			    NodeList nodeList = document.getDocumentElement().getChildNodes();
			    
			    
			    NodeList nList = document.getElementsByTagName("field");
			 
			    for(int j = 0; j < nList.getLength(); j++){
			    	Node nNode = nList.item(j);
			    	
			    	 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
			 
						
						
						if("PricingResult".equalsIgnoreCase(eElement.getAttribute("id"))){
						
							if(eElement.getChildNodes().item(0)!=null){
								System.out.println("Pricing result is"+eElement.getChildNodes().item(0).getTextContent());
								pricingResultXml=eElement.getChildNodes().item(0).getTextContent();
								break;	
							}
							
						}
						
						
					}
			    }
			    
			    
			  
			    
			    
			    
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return pricingResultXml;
			
}



public JSONObject prepareLockLoanRateJson(LoanLockRateVO loanLockRateVO)
{
	JSONObject json = new JSONObject();
	JSONObject jsonChild = new JSONObject();
	try {
		jsonChild.put("sLoanNumber",loanLockRateVO.getsLoanNumber());
		jsonChild.put("IlpTemplateId",loanLockRateVO.getIlpTemplateId());	
		jsonChild.put("requestedRate",loanLockRateVO.getRequestedFee());	
		jsonChild.put("requestedFee",loanLockRateVO.getRequestedRate());	
		json.put("opName","LockLoanProgram" );
		
		json.put("loanVO", jsonChild); 
		
		System.out.println("jsonMapObject"+json);
	} catch (JSONException e) {
		e.printStackTrace();
	}  
		return json;
}





private String invokeRest(String appFormData){
	
	LOG.info("Invoking rest Service with Input "+appFormData);
	   try { 
		    HttpHeaders headers = new HttpHeaders();
	        HttpEntity request= new HttpEntity(appFormData, headers );
	        RestTemplate restTemplate = new RestTemplate();
	        String returnedResponse = restTemplate.postForObject( "http://localhost:8282/loanCall", request, String.class);
	        JSONObject jsonObject = new JSONObject(returnedResponse);
	        LOG.info("Response Returned from Rest Service is"+jsonObject.get("responseMessage").toString());
	       // teaserRateList = parseLqbResponse(jsonObject.get("responseMessage").toString());
	        return jsonObject.get("responseMessage").toString();    
        
	   } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in post entity");
            return "error";
        }
	   
	   
}

private JSONObject saveLoan(String loanNumber,LoanAppFormVO loanAppFormVO)
{
	HashMap<String, String> hashmap = new HashMap();
	try {
	hashmap.put("loanPurpose", "1");
	hashmap.put("loanPurchasePrice", "400,000.00");
	hashmap.put("loanApprovedValue", "400,000.00");
	hashmap.put("applicantId", loanAppFormVO.getUser().getCustomerDetail().getSsn());
	hashmap.put("firstName", loanAppFormVO.getLoan().getUser().getFirstName());
	hashmap.put("middleName",loanAppFormVO.getLoan().getUser().getLastName());
	hashmap.put("lastName",loanAppFormVO.getLoan().getUser().getLastName());
	hashmap.put("dob",loanAppFormVO.getUser().getCustomerDetail().getDateOfBirth().toString());
	hashmap.put("PropertyState", loanAppFormVO.getUser().getCustomerDetail().getAddressState());
	hashmap.put("alimonyName", "NONE");
	hashmap.put("alimonyPayment", "1000");
	hashmap.put("jobExpenses", "100");
	if(loanAppFormVO.getCustomerEmploymentIncome()!=null && loanAppFormVO.getCustomerEmploymentIncome().size()>0){
	hashmap.put("jobRelatedPayment", loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedIncomePreTax());
	}else{
		hashmap.put("jobRelatedPayment", "1000");
	}
    hashmap.put("userSSNnumber",  loanAppFormVO.getUser().getCustomerDetail().getSsn());
	hashmap.put("baseIncome",  loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedIncomePreTax());
	hashmap.put("ProdLckdDays", "30");	
	if("Purchase".equalsIgnoreCase(loanAppFormVO.getLoanType().getDescription())){
		hashmap.put("loanAmount",  loanAppFormVO.getPurchaseDetails().getLoanAmount());
	}else{
		
		hashmap.put("loanAmount",  loanAppFormVO.getRefinancedetails().getCurrentMortgageBalance());
	}
	hashmap.put("applicantCity", loanAppFormVO.getUser().getCustomerDetail().getAddressCity());
	hashmap.put("applicantState",loanAppFormVO.getUser().getCustomerDetail().getAddressState() );
	hashmap.put("applicantZipCode", loanAppFormVO.getUser().getCustomerDetail().getAddressZipCode());
	hashmap.put("creditCardId", "eb228885-b484-404a-99ff-b28511dd3e38");
	hashmap.put("LOGIN_NAME", "testact");
	hashmap.put("PASSWORD", "1234nexera");
	hashmap.put("equifaxStatus", "Y");
	hashmap.put("experianStatus", "Y");
	hashmap.put("transunionStatus", "Y");
	hashmap.put("applicantAddress", "888Appleroad");
	
	
	
	JSONObject jsonObject = new JSONObject(hashmap);
	
	JSONObject json = new JSONObject();
	JSONObject jsonChild = new JSONObject();
	
		jsonChild.put("sLoanNumber",loanNumber);
		jsonChild.put("sDataContentMap",jsonObject);	
		jsonChild.put("format","0");
		
		
		json.put("opName","Save" );		
		json.put("loanVO", jsonChild); 
		
		System.out.println("jsonMapObject Save operation"+json);
		
		return json;
		
		
	} catch (JSONException e) {
		e.printStackTrace();
		return null;
	}  
	
	
	
	
}


}
