package com.nexera.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.enums.MessageTypeEnum;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageQueryVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.MessageService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/commlog/*")
public class CommunicationLogRestService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(CommunicationLogRestService.class);

	@Autowired
	MessageService messageService;

	@RequestMapping(value = "/{userID}/{loanID}/{pageNumber}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getCommunicationLog(
	        @PathVariable Integer userID, @PathVariable Integer loanID,@PathVariable Integer pageNumber) {

		CommonResponseVO response = null;

		MessageHierarchyVO hierarchyVO;
		try {
			MessageQueryVO queryVO = new MessageQueryVO();
			queryVO.setLoanId(new Long(loanID));
			queryVO.setPageNumber(pageNumber);
			queryVO.setUserId(new Long(userID));
			queryVO.setNumberOfRecords(CommonConstants.PAGINATION_SIZE);
			hierarchyVO = messageService.getMessages(queryVO);
			response = RestUtil.wrapObjectForSuccess(hierarchyVO);
		} catch (FatalException | NonFatalException e) {
			
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error("Error in retrieving communication log", e);

		}

		return response;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO saveCommunicationLog(
	        @RequestBody String messageVOString) {

		CommonResponseVO response = null;

		try {
			MessageVO messageVO = new Gson().fromJson(messageVOString,
			        MessageVO.class);
			messageService.saveMessage(messageVO,
			        MessageTypeEnum.NOTE.toString());
		} catch (FatalException | NonFatalException e) {
			// TODO Auto-generated catch block
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error("Error in retrieving communication log", e);

		}

		return response;
	}

	@RequestMapping(value = "/test/{userID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO testSaveCommunicationLog(
	        @PathVariable Integer userID, MessageVO messageVO) {

		CommonResponseVO response = new CommonResponseVO();

		MessageVO messageVO2 = new MessageVO(true);

		response.setResultObject(messageVO2);

		return response;
	}

}
