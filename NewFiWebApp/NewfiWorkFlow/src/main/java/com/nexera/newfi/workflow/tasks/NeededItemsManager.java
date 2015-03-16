package com.nexera.newfi.workflow.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.core.service.NeedsListService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class NeededItemsManager implements IWorkflowTaskExecutor {

	@Autowired
	NeedsListService needsListService;

	public String execute(HashMap<String, Object> objectMap) {

		return null;
	}

	public String renderStateInfo(HashMap<String, Object> inputMap) {
		int loanId = Integer.parseInt(inputMap.get(WorkflowDisplayConstants.LOAN_ID_KEY_NAME).toString());
		// Make a service call to get the number of needed items' assigned
		// against the
		NeededItemScoreVO neededItemScoreVO = needsListService
		        .getNeededItemsScore(loanId);
		return neededItemScoreVO.getTotalSubmittedItem() + " out of "
		        + neededItemScoreVO.getNeededItemRequired();
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

}
