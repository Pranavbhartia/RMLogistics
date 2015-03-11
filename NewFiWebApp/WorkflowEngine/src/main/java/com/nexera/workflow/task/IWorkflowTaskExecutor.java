package com.nexera.workflow.task;

public interface IWorkflowTaskExecutor {

	public String execute(Object... objects);

	public Object renderStateInfo(Object... inputs);

	public Object[] getParamsForExecute();
}
