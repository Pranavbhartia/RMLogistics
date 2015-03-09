package com.nexera.core.helper;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageVO;

public interface MessageServiceHelper {

	public void saveMessage(MessageVO messagesVO, String messageType)
	        ;
	
}
