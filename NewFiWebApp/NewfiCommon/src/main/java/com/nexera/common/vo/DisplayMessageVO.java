package com.nexera.common.vo;

import com.nexera.common.enums.DisplayMessageType;

/**
 * Entity for displaying message to the user
 */
public class DisplayMessageVO {

	private String message;
	private DisplayMessageType type;

	public DisplayMessageVO(String message, DisplayMessageType type) {
		this.message = message;
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public DisplayMessageType getType() {
		return type;
	}

	public void setType(DisplayMessageType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
