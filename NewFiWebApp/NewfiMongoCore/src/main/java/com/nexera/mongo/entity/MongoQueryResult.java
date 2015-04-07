package com.nexera.mongo.entity;

import java.util.List;

public class MongoQueryResult {
	private List<MongoMessageHeirarchy> messageHeirarchies;
	private long totalCount;

	public List<MongoMessageHeirarchy> getMessageHeirarchies() {
		return messageHeirarchies;
	}

	public void setMessageHeirarchies(
	        List<MongoMessageHeirarchy> messageHeirarchies) {
		this.messageHeirarchies = messageHeirarchies;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

}
