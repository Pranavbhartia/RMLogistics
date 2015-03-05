package com.nexera.common.vo.mongo;

public class MongoQueryVO {
	
	//Will always be passed.
	Long loanId;
	
	//Will always be passed.
	Long userId;
	
	//Will always be passed.
	Long roleId;
	
	//Can be null. Please ignore filtering on this column if it is null
	String messageType;
	
	//Page number, initially will be 0
	int pageNumber;
	
	//Number of records to be returned.
	int numberOfRecords;
	
	
	
	
}
