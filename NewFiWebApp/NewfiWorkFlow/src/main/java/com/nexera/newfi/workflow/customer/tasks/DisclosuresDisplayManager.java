package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.nexera.workflow.task.IWorkflowTaskExecutor;
@Component
public class DisclosuresDisplayManager implements IWorkflowTaskExecutor{

	@Override
	public String execute(HashMap<String, Object> objectMap) {
		//Do Nothing
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
