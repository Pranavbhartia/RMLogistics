package com.nexera.common.vo;

import java.util.List;

public class MessageHierarchyVO {

	List<List<MessageVO>> messageVOs;

	public List<List<MessageVO>> getMessageVOs() {
	    return messageVOs;
    }
	public void setMessageVOs(List<List<MessageVO>> messageVOs) {
	    this.messageVOs = messageVOs;
    }

}
