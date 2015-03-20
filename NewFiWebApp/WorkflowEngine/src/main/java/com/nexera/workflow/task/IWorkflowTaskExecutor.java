package com.nexera.workflow.task;

import java.util.HashMap;

public interface IWorkflowTaskExecutor {

	public String execute(HashMap<String, Object> objectMap);

	public String renderStateInfo(HashMap<String, Object> inputMap);
	
	public String checkStatus(HashMap<String, Object> inputMap);

	public Object[] getParamsForExecute();
}
