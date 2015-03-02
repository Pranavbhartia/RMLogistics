package com.nexera.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/application/")
public class ApplicationFormRestService {

	@Autowired
	private LoanAppFormService loanAppFormService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	public @ResponseBody String createApplication(
			@RequestBody String appFormData) {

		Gson gson = new Gson();

		LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
				LoanAppFormVO.class);
		
		loanAppFormService.create(loaAppFormVO);

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
