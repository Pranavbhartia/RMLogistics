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
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ManagerNeedVo;
import com.nexera.core.service.NeedsListService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/loanneeds/")
public class NeedsListRestService {

	@Autowired
	private NeedsListService needsListService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(NeedsListRestService.class);

	@RequestMapping(value = "{loanId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getLoanNeeds(@PathVariable int loanId) {
		System.out.println(loanId + "-----------------------");
		CommonResponseVO response = null;
		try {
			HashMap<String, Object> loanNeeds = needsListService
			        .getLoansNeedsList(loanId);
			response = RestUtil.wrapObjectForSuccess(loanNeeds);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "{loanId}", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO saveLoanNeeds(
	        @PathVariable int loanId, String needs) {
		CommonResponseVO response = null;
		try {

			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Integer>> typeRef = new TypeReference<List<Integer>>() {
			};
			List<Integer> val = mapper.readValue(needs, typeRef);
			int result = needsListService.saveLoanNeeds(loanId, val);
			if (result == 1) {
				response = RestUtil.wrapObjectForSuccess("Success");
			} else {
				response = RestUtil.wrapObjectForFailure(null, "500",
				        "Save need list failed");
			}

		} catch (Exception e) {
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "custom", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getCustomNeedsList() {
		CommonResponseVO response = null;
		try {
			List<ManagerNeedVo> customNeedsList = needsListService
			        .getNeedsListMaster(true);
			response = RestUtil.wrapObjectForSuccess(customNeedsList);
		} catch (Exception e) {
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error(e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "custom", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO setCustomNeedsList(
	        @RequestParam(required = true) String category,
	        @RequestParam(required = true) String label,
	        @RequestParam(required = true) String description,
	        @RequestParam(required = true) String lqbDocumentType) {
		CommonResponseVO response = null;
		try {
			User user = null;
			try {
				user = (User) SecurityContextHolder.getContext()
				        .getAuthentication().getPrincipal();
			} catch (Exception e) {
				// TODO return session expiry response
				user = new User();
				user.setId(1);
			}
			LOG.info("To check if need exist calling checkNeedExist service method with parametrs:"+label+","+category+","+description+","+user+","+lqbDocumentType);
			Boolean isExist = needsListService.checkNeedExist(label, category, description, user, lqbDocumentType);
			if(!isExist){
				NeedsListMaster customNeed = NeedsListMaster.getCustomNeed(label,
				        category, description, user,lqbDocumentType);
				int needId = needsListService.saveCustomNeed(customNeed);
				response = RestUtil.wrapObjectForSuccess(needId);
			}else {
				response = RestUtil.wrapObjectForFailure(null, "500",
				        ErrorConstants.NEED_EXIST_ERROR);
			}
			
		} catch (Exception e) {
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error(e.getMessage());
		}
		return response;
	}

}
