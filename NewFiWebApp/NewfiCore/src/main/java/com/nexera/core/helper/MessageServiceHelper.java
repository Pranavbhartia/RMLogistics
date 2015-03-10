package com.nexera.core.helper;

import java.util.List;

import com.nexera.common.entity.User;
import com.nexera.common.vo.MessageVO;

public interface MessageServiceHelper {

	public void saveMessage(MessageVO messagesVO, String messageType);

	public void generateCommunicationLogMessage(int loanId, User loggedInUser,
            List<Integer> addedList, List<Integer> removedList);
	       
	

	
	
}
