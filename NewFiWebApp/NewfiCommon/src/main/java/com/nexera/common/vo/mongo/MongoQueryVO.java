package com.nexera.common.vo.mongo;

public class MongoQueryVO {
	
	//Will always be passed.
	Long loanId;
	
	//Will always be passed.
	Long userId;
	
	//Will always be passed.
	String roleName;
	
	//Can be null. Please ignore filtering on this column if it is null
	String messageType;
	
	//Page number, initially will be 0
	int pageNumber;
	
	//Number of records to be returned.
	int numberOfRecords;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	
}
