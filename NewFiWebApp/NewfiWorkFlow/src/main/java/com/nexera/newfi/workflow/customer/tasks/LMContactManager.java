package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class LMContactManager implements IWorkflowTaskExecutor {

	@Autowired
	private LoanService loanService;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private EngineTrigger engineTrigger;

	private static final Logger LOG = LoggerFactory
	        .getLogger(LMContactManager.class);

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		LOG.debug("Inside method execute");
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method renderStateInfo");
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		LOG.debug("Inside method checkStatus");
		int userId = Integer.parseInt(inputMap.get("userId").toString());
		UserVO userVo = new UserVO();
		userVo.setId(userId);
		LoanVO loanVo = loanService.getActiveLoanOfUser(userVo);
		int workflowId = loanVo.getLoanManagerWorkflowID();

		List<WorkflowItemExec> list = engineTrigger
		        .getWorkflowItemExecByWorkflowMasterExec(workflowId);
		for (WorkflowItemExec workflowItem : list) {
			if (workflowItem.getWorkflowItemMaster().getWorkflowItemType()
			        .equals("INITIAL_CONTACT")) {
				if (workflowItem.getStatus().trim()
				        .equals(WorkItemStatus.COMPLETED.getStatus())) {
					int workflowItemExecId = Integer.parseInt(inputMap.get(
					        "workflowItemExecId").toString());
					engineTrigger.changeStateOfWorkflowItemExec(
					        workflowItemExecId,
					        WorkItemStatus.COMPLETED.getStatus());
					return WorkItemStatus.COMPLETED.getStatus();
				} else
					break;
			}
		}
		// TODO CHeck LM
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
