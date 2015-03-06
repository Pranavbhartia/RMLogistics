package com.nexera.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageQueryVO;
import com.nexera.core.service.MessageService;
import com.nexera.core.service.mongo.MongoMessageService;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/commlog/*")
public class CommunicationLogRestService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(CommunicationLogRestService.class);

	@Autowired
	MessageService messageService;
	
	@Autowired
	MongoMessageService mongoMessageService;
	
	

	@RequestMapping(value = "/{userID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getCommunicationLog(
	        @PathVariable Integer userID, MessageQueryVO queryVO) {

		CommonResponseVO response = null;

		MessageHierarchyVO hierarchyVO;
		try {
			hierarchyVO = messageService.getMessages(queryVO);
			response = RestUtil.wrapObjectForSuccess(hierarchyVO);
		} catch (FatalException | NonFatalException e) {
			// TODO Auto-generated catch block
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
			LOG.error("Error in retrieving communication log", e);

		}

		return response;
	}

	
	
	
	
	
	
}
