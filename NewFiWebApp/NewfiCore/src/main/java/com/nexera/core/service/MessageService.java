package com.nexera.core.service;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageHierarchyVO;
import com.nexera.common.vo.MessageVO;
import com.nexera.common.vo.MessageQueryVO;

public interface MessageService {

public String saveMessage(MessageVO messagesVO,String messageType,boolean sendEmail) throws FatalException,NonFatalException;
	
	public MessageHierarchyVO getMessages(MessageQueryVO QueryVO) throws FatalException,NonFatalException;

	
}
