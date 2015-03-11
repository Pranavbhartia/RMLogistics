package com.nexera.workflow.task;

public interface IWorkflowTaskExecutor {

	public String execute(Object[] inputs);

	public String renderStateInfo(String[] inputs);

	public Object[] getParamsForExecute();
}
