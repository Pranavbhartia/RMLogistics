package com.nexera.workflow.task;


public interface IWorkflowTaskExecutor {

	public String execute(Object... objects);

	public Object renderState(Object input);

	public Object[] getParamsForExecute();
}
