package com.nexera.common.vo;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

public class UserEmailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createdDate;
	private String priority;
	private Boolean status;
	private String to;
	private byte[] tokenMap;
	private UserVO createdBy;
	private UserVO user;
	private EmailTemplateVO emailTemplate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public byte[] getTokenMap() {
		return tokenMap;
	}
	public void setTokenMap(byte[] tokenMap) {
		this.tokenMap = tokenMap;
	}
	public UserVO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserVO createdBy) {
		this.createdBy = createdBy;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
	public EmailTemplateVO getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(EmailTemplateVO emailTemplate) {
		this.emailTemplate = emailTemplate;
	}


}