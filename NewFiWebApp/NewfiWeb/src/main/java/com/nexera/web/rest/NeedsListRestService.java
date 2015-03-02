package com.nexera.web.rest;


import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.core.service.NeedsListService;


@RestController
@RequestMapping(value="/loanneeds/")
public class NeedsListRestService {

	@Autowired
	private NeedsListService needsListService;
	private static final Logger LOG = LoggerFactory
			.getLogger(NeedsListRestService.class);
	

	@RequestMapping(value = "{loanId}" , method=RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanNeeds(@PathVariable int loanId) {
		System.out.println(loanId+"-----------------------");
		CommonResponseVO response=new CommonResponseVO();
		try {
			HashMap<String, Object> loanNeeds=needsListService.getLoansNeedsList(loanId);
			response.setError(null);
			response.setResultObject(loanNeeds);
//			result.put("Error", null);
//			result.put("Result", loanNeeds);
		}catch(Exception e){
			LOG.error(e.getMessage());
			ErrorVO errorVo=new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage(e.getMessage());
			response.setError(errorVo);
			response.setResultObject(null);
		}
		return response; 
	}

	@RequestMapping(value = "{loanId}" , method=RequestMethod.POST)
	public @ResponseBody CommonResponseVO saveLoanNeeds(@RequestParam(required=true) int loanId,String needs) {
		CommonResponseVO response=new CommonResponseVO();
		try {
			
			ObjectMapper mapper=new ObjectMapper();
			TypeReference<List<Integer>> typeRef=new TypeReference<List<Integer>>() {};
			List<Integer> val=mapper.readValue(needs, typeRef);
			int result=needsListService.saveLoanNeeds(loanId, val);
			if(result==1){
				response.setError(null);
				response.setResultObject("Success");
			}
			else{
				ErrorVO errorVo=new ErrorVO();
				errorVo.setCode("500");
				errorVo.setMessage("Save need list failed");
				response.setError(errorVo);
				response.setResultObject(null);
			}
			
		}catch(Exception e){
			ErrorVO errorVo=new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage(e.getMessage());
			response.setError(errorVo);
			response.setResultObject(null);
			LOG.error(e.getMessage());
		}
		return response; 
	}
	@RequestMapping(value = "custom" , method=RequestMethod.GET)
	public @ResponseBody CommonResponseVO getCustomNeedsList() {
		CommonResponseVO response=new CommonResponseVO();
		try {
			List<ManagerNeedVo> customNeedsList=needsListService.getNeedsListMaster(true);
			response.setError(null);
			response.setResultObject(customNeedsList);
		}catch(Exception e){
			ErrorVO errorVo=new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage(e.getMessage());
			response.setError(errorVo);
			response.setResultObject(null);
			LOG.error(e.getMessage());
		}
		return response; 
	}
	@RequestMapping(value = "custom" , method=RequestMethod.POST)
	public @ResponseBody CommonResponseVO setCustomNeedsList(@RequestParam(required=true) String category,@RequestParam(required=true)String label,@RequestParam(required=true)String description) {
		CommonResponseVO response=new CommonResponseVO();
		try {
			User user=null;
			try{
				user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}catch(Exception e){
				//TODO return session expiry response
				user=new User();
				user.setId(1);
			}
			NeedsListMaster customNeed=NeedsListMaster.getCustomNeed(label, category, description,user);
			int needId=needsListService.saveCustomNeed(customNeed);
			response.setError(null);
			response.setResultObject(needId);
		}catch(Exception e){
			ErrorVO errorVo=new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage(e.getMessage());
			response.setError(errorVo);
			response.setResultObject(null);
			LOG.error(e.getMessage());
		}
		return response; 
	}
	
	/**
	 * @return the needsListService
	 */
	public NeedsListService getNeedsListService() {
		return needsListService;
	}



	/**
	 * @param needsListService the needsListService to set
	 */
	public void setNeedsListService(NeedsListService needsListService) {
		this.needsListService = needsListService;
	}
}
