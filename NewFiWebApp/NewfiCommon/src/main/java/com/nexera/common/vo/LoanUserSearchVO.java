package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoanUserSearchVO implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(LoanUserSearchVO.class);
	private static final long serialVersionUID = 1L;	
	
	Integer[] progressStatus;
	
	String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer[] getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(Integer[] progressStatus) {
		this.progressStatus = progressStatus;
	}
	

}
