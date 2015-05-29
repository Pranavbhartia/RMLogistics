package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.newfi.workflow.service.IWorkflowService;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class CreditScoreDisplayManager implements IWorkflowTaskExecutor {

	@Autowired
	private IWorkflowService iWorkflowService;
	@Autowired
	private Utils utils;

	private static final Logger LOG = LoggerFactory
	        .getLogger(CreditScoreDisplayManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Loan loan = new Loan();
		loan.setId(Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString()));
		int userID = (Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString()));
		map.put(WorkflowDisplayConstants.WORKFLOW_RENDERSTATE_STATUS_KEY,
		        iWorkflowService.getCreditDisplayScore(userID));
		// TODO confirm where credit score and url will be stored
		return utils.getJsonStringOfMap(map);
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method invokeAction");
		return null;
	}

	@Override
	public String updateReminder(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method updateReminder");
		return null;
	}

}
