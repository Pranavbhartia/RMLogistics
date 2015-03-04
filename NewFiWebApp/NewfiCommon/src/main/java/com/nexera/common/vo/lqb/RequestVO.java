package com.nexera.common.vo.lqb;

import java.util.Date;

public class RequestVO {
	
	private String opName;
	private String inputDataType;
	private Object inputData;
	private Integer userID;
	private String callbackURL;
	private Date requestTime;
	
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getInputDataType() {
		return inputDataType;
	}
	public void setInputDataType(String inputDataType) {
		this.inputDataType = inputDataType;
	}
	public Object getInputData() {
		return inputData;
	}
	public void setInputData(Object inputData) {
		this.inputData = inputData;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getCallbackURL() {
		return callbackURL;
	}
	public void setCallbackURL(String callbackURL) {
		this.callbackURL = callbackURL;
	}
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	
	
}
