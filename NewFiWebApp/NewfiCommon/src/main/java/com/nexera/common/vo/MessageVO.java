package com.nexera.common.vo;

import java.util.List;

public class MessageVO {

	// Mongo ID
	private String id;

	// Actual text
	private String message;

	// information about user who created this message
	private MessageUserVO createdUser;

	// information about users who have access to this message
	private MessageUserVO[] otherUsers;

	// Information about the links this message has access to
	private FileVO[] links;

	// Self reference to the message structure to take care of recursive nesting
	List<MessageVO> messageVOs;

	public class MessageUserVO {

		private Integer userID;
		private String userName;
		private String imgUrl;
		public Integer getUserID() {
			return userID;
		}
		public void setUserID(Integer userID) {
			this.userID = userID;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
		
		
	}

	public class FileVO {

		private String fileName;
		private String url;
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}

		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageUserVO getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(MessageUserVO createdUser) {
		this.createdUser = createdUser;
	}

	public MessageUserVO[] getOtherUsers() {
		return otherUsers;
	}

	public void setOtherUsers(MessageUserVO[] otherUsers) {
		this.otherUsers = otherUsers;
	}

	public FileVO[] getLinks() {
		return links;
	}

	public void setLinks(FileVO[] links) {
		this.links = links;
	}

	public List<MessageVO> getMessageVOs() {
		return messageVOs;
	}

	public void setMessageVOs(List<MessageVO> messageVOs) {
		this.messageVOs = messageVOs;
	}
	

}
