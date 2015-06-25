package com.nexera.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexera.common.exception.BaseRestException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.StateLookupVO;
import com.nexera.common.vo.ZipCodeLookupVO;
import com.nexera.core.service.StateLookupService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/states/")
public class StateLookupRestServive {
	private static final Logger LOG = LoggerFactory
	        .getLogger(StateLookupRestServive.class);
	
	@Autowired 
	private StateLookupService stateLookupService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getStatesList(){
		
		LOG.info("Rest service getStatesList() called to fetch the states list");
		List<StateLookupVO> statesList = stateLookupService.getStatesList();
		
		CommonResponseVO responseVO = null;
		responseVO = RestUtil.wrapObjectForSuccess(statesList);
		
		return responseVO;
	}

	@RequestMapping(value = "/{stateID}/zipCode", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO getZipCodesForState(
	        @PathVariable(value = "stateID") Integer stateID) {

		LOG.info("Rest service getStatesList() called to fetch the states list");
		if(stateID ==null || stateID<1)
			throw new BaseRestException();

		List<ZipCodeLookupVO> zipCodeList = stateLookupService
		        .findZipCodesForStateID(stateID);

		CommonResponseVO responseVO = null;
		responseVO = RestUtil.wrapObjectForSuccess(zipCodeList);

		return responseVO;
	}

	@RequestMapping(value = "/zipCode", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO validateZipCode(String zipCode) {

		LOG.info("Rest service getStatesList() called to fetch the states list");
		if (zipCode == null || zipCode.length() < 1)
			throw new BaseRestException();

		String status = stateLookupService.validateZipCode(zipCode);

		CommonResponseVO responseVO = null;
		responseVO = RestUtil.wrapObjectForSuccess(status);

		return responseVO;
	}
}
