package com.nexera.newfi.workflow.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.vo.NeededItemScoreVO;
import com.nexera.core.service.NeedsListService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class NeededItemsManager implements IWorkflowTaskExecutor {

	@Autowired
	NeedsListService needsListService;

	public String execute(Object[] objects) {

		return null;
	}

	public String renderStateInfo(String[] inputs) {
		int loanId = Integer.parseInt(inputs[0].toString());
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
