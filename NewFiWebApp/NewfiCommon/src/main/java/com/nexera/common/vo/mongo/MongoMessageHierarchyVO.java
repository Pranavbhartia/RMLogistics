package com.nexera.common.vo.mongo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MongoMessageHierarchyVO {

	// A list of list of messages - only their ids
	// Each list in this is a 'thread' which is displayed on the UI
	private List<List<String>> messageIds;

	// This maps an ID to an actual message
	private Map<String, MongoMessagesVO> idMap = new HashMap<String, MongoMessagesVO>();

	/**
	 * @return
	 */
	public List<List<String>> getMessageIds() {
		return messageIds;
	}

	/**
	 * @param messageIds
	 */
	public void setMessageIds(List<List<String>> messageIds) {
		this.messageIds = messageIds;
	}

	/**
	 * @param id
	 * @param mmvo
	 */
	public void setMongoMessages(String id, MongoMessagesVO mmvo) {
		this.idMap.put(id, mmvo);
	}

	/**
	 * @param id
	 */
	public void getMongoMessagesVO(String id) {
		this.idMap.get(id);
	}

	@Override
	public String toString() {
		return "MongoMessageHierarchyVO [messageIds=" + messageIds + ", idMap="
				+ idMap + "]";
	}

}
