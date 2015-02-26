package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class EmailTemplateVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String description;
	private String from;
	private Date modifiedDate;
	private String name;
	private UserVO modifiedBy;
	private List<UserEmailVO> userEmails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public List<UserEmailVO> getUserEmails() {
		return userEmails;
	}
	public void setUserEmails(List<UserEmailVO> userEmails) {
		this.userEmails = userEmails;
	}


}