package com.nexera.extractor.entity;

import java.util.List;
import java.util.Map;

public class RestResponse {

	Map<String, List<UIEntity>> data;
	long timestamp;

	public Map<String, List<UIEntity>> getData() {
		return data;
	}

	public void setData(Map<String, List<UIEntity>> data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
