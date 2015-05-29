package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.User;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class BankConnectionManager implements IWorkflowTaskExecutor {
	@Autowired
	private LoanDao loanDao;
	@Autowired
	private ApplicationContext applicationContext;

	private static final Logger LOG = LoggerFactory
	        .getLogger(BankConnectionManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		int userId = Integer.parseInt(inputMap.get(
		        WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());
		LOG.debug("User found " + userId);
		User user = new User();
		user.setId(userId);
		Loan loan = loanDao.getActiveLoanOfUser(user);
		if (loan.getIsBankConnected()) {
			LOG.debug("Bank is connected to this loan " + loan.getId());
			int workflowItemExecId = Integer.parseInt(inputMap.get(
			        WorkflowDisplayConstants.WORKITEM_ID_KEY_NAME).toString());
			LOG.debug("Triggering workflowitem " + workflowItemExecId);
			EngineTrigger engineTrigger = applicationContext
			        .getBean(EngineTrigger.class);
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId,
			        WorkItemStatus.STARTED.toString());
			return WorkItemStatus.STARTED.toString();
		}
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
