package com.nexera.common.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageVO {

	public MessageVO() {
		this.createdUser = new MessageUserVO();
		this.links = new ArrayList<MessageVO.FileVO>();
		this.otherUsers = new ArrayList<MessageVO.MessageUserVO>();
	}

	public MessageVO(boolean forTest) {
		this.createdUser = new MessageUserVO();
		this.links = new ArrayList<MessageVO.FileVO>();
		this.otherUsers = new ArrayList<MessageVO.MessageUserVO>();
		MessageUserVO obj = new MessageUserVO();
		obj.setImgUrl("test");
		this.otherUsers.add(obj);
	}

	// Mongo ID
	private String id;

	// Loan Id for the message
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

	private String createdDate;

	private Date sortOrderDate;

	// Self reference to the message structure to take care of recursive nesting
	MessageVO messageVO;

	public class MessageUserVO {

		public MessageUserVO() {

		}

		private Integer userID;
		private String userName;
		private String imgUrl;
		private String roleName;

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

		public String getRoleName() {
			return roleName;
		}

		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}

		@Override
		public String toString() {
			return "MessageUserVO [userID=" + userID + ", userName=" + userName
			        + ", imgUrl=" + imgUrl + ", roleName=" + roleName + "]";
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

		@Override
		public String toString() {
			return "FileVO [fileName=" + fileName + ", url=" + url + "]";
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public void setLinks(List<FileVO> links) {
		this.links = links;
	}

	public MessageVO getMessageVO() {
		return messageVO;
	}

	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

	public MessageUserVO createNewUserVO() {
		// TODO Auto-generated method stub
		return new MessageUserVO();
	}

	public Date getSortOrderDate() {
		return sortOrderDate;
	}

	public void setSortOrderDate(Date sortOrderDate) {
		this.sortOrderDate = sortOrderDate;
	}

	@Override
	public String toString() {

		return "MessageVO [id=" + id + ", loanId=" + loanId + ", parentId="
		        + parentId + ", message=" + message + ", createdUser="
		        + createdUser + ", otherUsers=" + otherUsers + ", createdDate="
		        + createdDate + "]";
	}

}
