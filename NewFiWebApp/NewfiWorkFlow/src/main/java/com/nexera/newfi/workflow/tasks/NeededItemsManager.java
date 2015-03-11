package com.nexera.newfi.workflow.tasks;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.core.service.NeedsListService;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class NeededItemsManager implements IWorkflowTaskExecutor {

	@Autowired
	NeedsListService needsListService;

	public String execute(Object... objects) {

		return null;
	}

	public Object renderStateInfo(Object... inputs) {
		int loanId = Integer.parseInt(inputs[0].toString());
		// Make a service call to get the number of needed items' assigned against the 
		return "4 out of 10";
	}

	public Object[] getParamsForExecute() {
		// TODO Auto-generated method stub
		return null;
	}

}
