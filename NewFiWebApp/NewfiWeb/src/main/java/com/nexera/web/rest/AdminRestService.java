package com.nexera.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.MileStoneTurnAroundTimeVO;
import com.nexera.common.vo.TemplateVO;
import com.nexera.core.service.MileStoneTurnAroundTimeService;
import com.nexera.core.service.TemplateService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/admin")
public class AdminRestService {

	@Autowired
	private MileStoneTurnAroundTimeService aroundTimeService;

	@Autowired
	private TemplateService templateService;

	@RequestMapping(value = "/retrieveTurnAround", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO loadAllMileStoneTurnAround() {

		List<MileStoneTurnAroundTimeVO> aroundTimeVOs;
		aroundTimeVOs = aroundTimeService.loadAllMileStoneTurnAround();
		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(aroundTimeVOs);

		return responseVO;
	}

	@RequestMapping(value = "/saveMileStoneTurnArounds", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO saveOrUpdateMileStoneTurnArounds(
	        @RequestBody String strAroundTimeVOs)
	        throws NoRecordsFetchedException {
		TypeToken<List<MileStoneTurnAroundTimeVO>> token = new TypeToken<List<MileStoneTurnAroundTimeVO>>() {
		};
		List<MileStoneTurnAroundTimeVO> aroundTimeVOs = new Gson().fromJson(
		        strAroundTimeVOs, token.getType());
		aroundTimeService.saveOrUpdateMileStoneTurnArounds(aroundTimeVOs);
		CommonResponseVO responseVO = new CommonResponseVO();
		return responseVO;
	}

	@RequestMapping(value = "/retrieveTemplates", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO loadAllTemplates() {

		List<TemplateVO> templateVOs = templateService.getListsTemplate();
		CommonResponseVO responseVO = RestUtil
		        .wrapObjectForSuccess(templateVOs);

		return responseVO;
	}

	@RequestMapping(value = "/saveTemplates", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO saveOrUpdateTemplates(
	        @RequestBody String strTemplateVOs)
	        throws NoRecordsFetchedException {
		TypeToken<List<TemplateVO>> token = new TypeToken<List<TemplateVO>>() {
		};
		List<TemplateVO> templateVOs = new Gson().fromJson(strTemplateVOs,
		        token.getType());
		templateService.saveOrUpdate(templateVOs);
		CommonResponseVO responseVO = new CommonResponseVO();
		return responseVO;
	}

}
