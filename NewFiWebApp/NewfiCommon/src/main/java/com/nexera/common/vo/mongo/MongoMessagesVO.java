package com.nexera.common.vo.mongo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MongoMessagesVO implements Serializable {

	private static final long serialVersionUID = 1L;

	// Will be null when we try to insert. Needs to be populated when we
	// retrieve
	private String id;

	// Cotnains the actual text that needs to be stored. Will not be null
	private String body;

	// Contains the list of string references to some URLs which is associated
	// with this message, can be null
	private List<String> relatedLinks;

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
	private Integer roleId;

	// List of roles who have access to this message. Can be null, for messages
	// targeted to users
	private List<Integer> roleList;

	// List of users who have access to this message. Can be null, for messages
	// targeted to roles.
	private List<Integer> userList;

	// Loan ID associated with this message
	private Long loanId;

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

	public List<String> getRelatedLinks() {
		return relatedLinks;
	}

	public void setRelatedLinks(List<String> relatedLinks) {
		this.relatedLinks = relatedLinks;
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

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Integer> roleList) {
		this.roleList = roleList;
	}

	public List<Integer> getUserList() {
		return userList;
	}

	public void setUserList(List<Integer> userList) {
		this.userList = userList;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}



}
