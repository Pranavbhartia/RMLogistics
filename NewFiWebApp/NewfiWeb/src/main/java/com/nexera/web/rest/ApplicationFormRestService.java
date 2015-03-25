package com.nexera.web.rest;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/application/")
public class ApplicationFormRestService {

	@Autowired
	private LoanAppFormService loanAppFormService;

	HashMap<String, Integer> cache = new HashMap<String, Integer>();
	
	
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody String createApplication(@RequestBody String appFormData) {

		Gson gson = new Gson();


	//	LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,LoanAppFormVO.class);

		try{ 
			
			
			
			
			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
		
				LoanAppFormVO.class);

			
			System.out.println("loaAppFormVO.getId()"+loaAppFormVO.getId());
			System.out.println("loaAppFormVO.getUser().getId()"+loaAppFormVO.getUser().getId());
			System.out.println("loaAppFormVO.getUser().getCustomerDetail().getId()"+loaAppFormVO.getUser().getCustomerDetail().getId());
			
			//System.out.println("loaAppFormVO.getPropertyTypeMaster().getId()"+loaAppFormVO.getPropertyTypeMaster().getId());
			//System.out.println("loaAppFormVO.getGovernmentquestion().getId()"+loaAppFormVO.getGovernmentquestion().getId());
			//System.out.println("loaAppFormVO.getRefinancedetails().getId()"+loaAppFormVO.getRefinancedetails().getId());
			
			
			
			
			
			
			
			
				
			
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
			
		
				System.out.println("cache.get(propertyTypeMasterId)"+cache.get("propertyTypeMasterId"));
			if(cache.get("propertyTypeMasterId") !=null && loaAppFormVO.getPropertyTypeMaster().getId()==0 && cache.get("propertyTypeMasterId") !=0){
				loaAppFormVO.getPropertyTypeMaster().setId(cache.get("propertyTypeMasterId"));
			}
			
					
			
				System.out.println("cache.get(governmentQuestionId)"+cache.get("governmentQuestionId"));
			if(cache.get("governmentQuestionId")!=null && loaAppFormVO.getGovernmentquestion().getId()==0 && cache.get("governmentQuestionId")!=0)
			{
				loaAppFormVO.getGovernmentquestion().setId(cache.get("governmentQuestionId"));
			}
		
		
					
			
				System.out.println("cache.get(refinanceDetailsId)"+cache.get("refinanceDetailsId"));
			if(cache.get("refinanceDetailsId")!=null && loaAppFormVO.getRefinancedetails().getId() == 0 &&  cache.get("refinanceDetailsId")!=0)
			{
				loaAppFormVO.getRefinancedetails().setId(cache.get("refinanceDetailsId"));
			}
				
			
			
			System.out.println("Inside 4"+loaAppFormVO.getUser().getFirstName());
			System.out.println("Inside 4.1"+loaAppFormVO.getUser().getCustomerDetail().getAddressCity());
			LoanAppForm loanAppForm = loanAppFormService.create(loaAppFormVO);
			System.out.println("loanAppForm.getId()"+loanAppForm.getId());
			System.out.println("loanAppForm.getUser().getId()"+loanAppForm.getUser().getId());
			System.out.println("loanAppForm.getUser().getCustomerDetail().getId()"+loanAppForm.getUser().getCustomerDetail().getId());
			System.out.println("loanAppForm.getPropertyTypeMaster().getId()"+loanAppForm.getPropertyTypeMaster().getId());
			System.out.println("loanAppForm.getGovernmentquestion().getId()"+loanAppForm.getGovernmentquestion().getId());
			System.out.println("loanAppForm.getRefinancedetails().getId()"+loanAppForm.getRefinancedetails().getId());
			
			cache.put("loanAppFormId", loanAppForm.getId());
			cache.put("userId", loanAppForm.getUser().getId());
			cache.put("customerId", loanAppForm.getUser().getCustomerDetail().getId());
			cache.put("propertyTypeMasterId", loanAppForm.getPropertyTypeMaster().getId());
			cache.put("governmentQuestionId", loanAppForm.getGovernmentquestion().getId());
			cache.put("refinanceDetailsId", loanAppForm.getRefinancedetails().getId());
			
			
		
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
}
