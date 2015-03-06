package com.nexera.common.vo;

import java.util.ArrayList;
import java.util.List;

public class MessageVO {

	public MessageVO() {
		this.createdUser = new MessageUserVO();
		this.links = new ArrayList<MessageVO.FileVO>();
		this.otherUsers = new ArrayList<MessageVO.MessageUserVO>();
	}

	// Mongo ID
	private String id;

	private String loanId;

	// Mongo parent ID
	private String parentId;

	// Actual text
	private String message;

	// information about user who created this message
	private MessageUserVO createdUser;

	// information about users who have access to this message
	private List<MessageUserVO> otherUsers;

	// Information about the links this message has access to
	private List<FileVO> links;

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

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public List<MessageUserVO> getOtherUsers() {
		return otherUsers;
	}

	public void setOtherUsers(List<MessageUserVO> otherUsers) {
		this.otherUsers = otherUsers;
	}

	public List<FileVO> getLinks() {
		return links;
	}

	public void setLinks(List<FileVO> links) {
		this.links = links;
	}

	public List<MessageVO> getMessageVOs() {
		return messageVOs;
	}

	public void setMessageVOs(List<MessageVO> messageVOs) {
		this.messageVOs = messageVOs;
	}

}
