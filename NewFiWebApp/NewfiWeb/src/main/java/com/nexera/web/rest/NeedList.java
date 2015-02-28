package com.nexera.web.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.core.service.NeedsListService;
import com.nexera.web.constants.JspLookup;
@RestController
public class NeedList {

	@Autowired
	private NeedsListService needsListService;
	
	

	@RequestMapping(value = "/getloanneeds" , method=RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> getLoanNeeds(@RequestParam(required=true) int loanId) {
		HashMap<String, Object> result=new HashMap<String,Object>();
		try {
			List<ManagerNeedVo> loanNeeds=needsListService.getLoansNeedsList(loanId);
			result.put("Error", null);
			result.put("Result", loanNeeds);
		}catch(Exception e){
			result.put("Error", e.getMessage());
		}
		return result; 
	}

	@RequestMapping(value = "/saveloanneeds" , method=RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> saveLoanNeeds(@RequestParam(required=true) int loanId,String needs) {
		HashMap<String, Object> result=new HashMap<String,Object>();
		try {
			
			ObjectMapper mapper=new ObjectMapper();
			TypeReference<List<Integer>> typeRef=new TypeReference<List<Integer>>() {};
			List<Integer> val=mapper.readValue(needs, typeRef);
			int response=needsListService.saveLoanNeeds(loanId, val);
			if(response==1){
				result.put("Error", null);
				result.put("Result", "Success");
			}
			else{
				result.put("Error", "Save Unsuccessful please try again");
				result.put("Result", "");
			}
			
		}catch(Exception e){
			result.put("Error", "Loan Needs List Can not be fetched");
		}
		return result; 
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
