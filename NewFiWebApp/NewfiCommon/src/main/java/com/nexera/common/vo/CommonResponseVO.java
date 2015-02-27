package com.nexera.common.vo;

public class CommonResponseVO {
	private ErrorVO error;
	private Object resultObject;

	public ErrorVO getError() {
		return error;
	}

	public void setError(ErrorVO error) {
		this.error = error;
	}

	public Object getResultObject() {
		return resultObject;
	}

	public void setResultObject(Object resultObject) {
		this.resultObject = resultObject;
	}

}
