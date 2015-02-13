package com.nexera.newfi.core.lqb.broker.vo;

import java.util.Date;
import java.util.Map;

public class ResponseVO {
	
	private String status;
	private String responseCode;
	private Map<String,String> responseMap;
	private Date responseTime;
	private String errorCode;
	private String errorDescription;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public Map<String, String> getResponseMap() {
		return responseMap;
	}
	public void setResponseMap(Map<String, String> responseMap) {
		this.responseMap = responseMap;
	}
	public Date getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
