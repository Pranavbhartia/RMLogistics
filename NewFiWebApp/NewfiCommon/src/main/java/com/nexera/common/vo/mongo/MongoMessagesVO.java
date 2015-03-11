package com.nexera.common.vo.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MongoMessagesVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Will be null when we try to insert. Needs to be populated when we
	// retrieve
	private String id;

	// Contains the actual text that needs to be stored. Will not be null
	private String body;

	// Contains the list of string references to some URLs which is associated
	// with this message, can be null
	// Information about the links this message has access to
	private FileVO[] links;

	// This will help us retrieve the message based on a messageType. For ex: If
	// I want to know all the messages of a particular type. I will pass this in
	// the query parameter
	private String messageType;

	// Date containing the created timestamp, It will be UTC based storage, I
	// dont think it will matter to u.
	private Date createdDate;

	// Created By user, will be 0 for System generated alerts.
	private Long createdBy;

	// Role of the user creating it. Will be 0 for System generated alerts
	private String roleName;

	// List of roles who have access to this message. Can be null, for messages
	// targeted to users
	private List<String> roleList;

	// List of users who have access to this message. Can be null, for messages
	// targeted to roles.
	private List<Long> userList;

	// Loan ID associated with this message
	private int loanId;

	// Parent ID for this message
	private String parentId;

	public static class FileVO {

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public FileVO[] getLinks() {
		return links;
	}

	public void setLinks(FileVO[] links) {
		this.links = links;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public List<Long> getUserList() {
		return userList;
	}

	public void setUserList(List<Long> userList) {
		this.userList = userList;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
    public String toString() {
	    return "MongoMessagesVO [id=" + id + ", body=" + body +  ", messageType=" + messageType
	            + ", createdDate=" + createdDate + ", createdBy=" + createdBy
	            + ", roleName=" + roleName + ", roleList=" + roleList
	            + ", userList=" + userList + ", loanId=" + loanId
	            + ", parentId=" + parentId + "]";
    }

}
