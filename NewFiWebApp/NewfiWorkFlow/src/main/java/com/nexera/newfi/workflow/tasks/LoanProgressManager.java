package com.nexera.newfi.workflow.tasks;

import com.nexera.workflow.task.IWorkflowTaskExecutor;

public class LoanProgressManager implements IWorkflowTaskExecutor {

	public String execute(Object[] objects) {
	    // TODO Auto-generated method stub
	    return null; 
    }

	public String renderStateInfo(String[] inputs) {
		int loanId = Integer.parseInt(inputs[0]);
		//Call loan services to check if LQB is done.
		//if done..give the LQB Link
	    return "Click here to complete Application form";
    }

	public Object[] getParamsForExecute() {
	    // TODO Auto-generated method stub
	    return null;
    }

}
