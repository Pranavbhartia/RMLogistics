package com.nexera.core.helper.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.MessageVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.service.MessageService;

public class MessageServiceHelperImpl implements MessageServiceHelper {

	private static final Logger LOG = LoggerFactory
	        .getLogger(MessageServiceHelperImpl.class);

	@Autowired
	MessageService messageService;

	@Override
	@Async
	public void saveMessage(MessageVO messagesVO, String messageType) {
		LOG.debug("Helper save message called");
		String messageID;
		try {
			messageID = messageService.saveMessage(messagesVO, messageType);
			LOG.debug("Helper save message succeeded. With messageID: "
			        + messageID);
		} catch (FatalException | NonFatalException e) {
			LOG.error("error while saving the message: " + messagesVO);
			//TODO: Write in Error table
		}

	}

}
