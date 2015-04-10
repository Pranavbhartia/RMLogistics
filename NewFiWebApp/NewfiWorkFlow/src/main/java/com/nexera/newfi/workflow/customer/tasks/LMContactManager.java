package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;
import java.util.List;

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

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		return WorkItemStatus.COMPLETED.getStatus();
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		int userId = Integer.parseInt(inputMap.get("userId").toString());
		UserVO userVo = new UserVO();
		userVo.setId(userId);
		LoanVO loanVo = loanService.getActiveLoanOfUser(userVo);
		int workflowId = loanVo.getLoanManagerWorkflowID();
		// TODO In Engine trigger add a method to get execitem by type
		List<WorkflowItemExec> list = engineTrigger
				.getWorkflowItemExecByWorkflowMasterExec(workflowId);
		for (WorkflowItemExec workflowItem : list) {
			if (workflowItem.getWorkflowItemMaster().getWorkflowItemType()
					.equals("INITIAL_CONTACT")) {
				if (workflowItem.getStatus().trim().equals("3")) {
					int workflowItemExecId = Integer.parseInt(inputMap.get(
							"workflowItemExecId").toString());
					engineTrigger.changeStateOfWorkflowItemExec(
							workflowItemExecId, "3");
					return "3";
				} else
					break;
			}
		}
		// TODO CHeck LM
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public String updateReminder(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
