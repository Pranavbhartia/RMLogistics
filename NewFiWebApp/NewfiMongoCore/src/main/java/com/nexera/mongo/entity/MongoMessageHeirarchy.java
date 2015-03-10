/**
 * 
 */
package com.nexera.mongo.entity;

import java.util.Date;
import java.util.List;

/**
 * This is the entity being stored into MongoDB
 * 
 * 
 * @author Samarth Bhargav
 * 
 */
public class MongoMessageHeirarchy {
	
	private String id;
	private List<String> messages;
	private List<String> roleList;
	private List<Long> userList;
	private Long loanId;
	private String messageType;
	private Date date;

	public MongoMessageHeirarchy() {

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
