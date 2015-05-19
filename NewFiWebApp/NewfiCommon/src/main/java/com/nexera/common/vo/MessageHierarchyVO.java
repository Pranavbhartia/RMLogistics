package com.nexera.common.vo;

import java.util.List;

public class MessageHierarchyVO {

	List<List<MessageVO>> messageVOs;
	private Boolean salesManagerPresent;

	public List<List<MessageVO>> getMessageVOs() {
		return messageVOs;
	}

	public void setMessageVOs(List<List<MessageVO>> messageVOs) {
		this.messageVOs = messageVOs;
	}

	public Boolean getSalesManagerPresent() {
		return salesManagerPresent;
	}

	public void setSalesManagerPresent(Boolean salesManagerPresent) {
		this.salesManagerPresent = salesManagerPresent;
	}

}
