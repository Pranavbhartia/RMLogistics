package com.nexera.common.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.enums.DisplayMessageType;
import com.nexera.common.vo.DisplayMessage;

/**
 * Utility class for preparing message to display to the user
 */
@Component
public final class MessageUtils {

	private static final Logger LOG = LoggerFactory.getLogger(MessageUtils.class);

	@Autowired
	private PropertyFileReader propertyFileReader;

	/**
	 * Method to get the display message for an error code
	 * 
	 * @param errorCode
	 * @param displayMessageType
	 * @return
	 */
	public DisplayMessage getDisplayMessage(String errorCode, DisplayMessageType displayMessageType) {
		/**
		 * If no error code is set, set it as the general error of the application
		 */
		if (errorCode == null || errorCode.isEmpty()) {
			errorCode = DisplayMessageConstants.GENERAL_ERROR;
		}
		LOG.info("Getting display message for the errorCode : " + errorCode + " and message type : " + displayMessageType.getName());

		String errorMessage = propertyFileReader.getProperty(CommonConstants.MESSAGE_PROPERTIES_FILE, errorCode);
		DisplayMessage displayMessage = new DisplayMessage(errorMessage, displayMessageType);

		LOG.info("Returning message to be displayed : " + displayMessage.getMessage());
		return displayMessage;
	}
}
