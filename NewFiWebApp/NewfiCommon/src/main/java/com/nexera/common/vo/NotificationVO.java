package com.nexera.common.vo;

import java.io.Serializable;
public class NotificationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String content;
	private Long createdDate;
	private Boolean dismissable;
	private String notificationType;
	private String priority;
	private Boolean read;
	private Long remindOn;
	private String title;
	private Integer createdByID;
	private Integer loanID;
	private Integer createdForID;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Long createdDate) {
		this.createdDate = createdDate;
	}
	public Boolean getDismissable() {
		return dismissable;
	}
	public void setDismissable(Boolean dismissable) {
		this.dismissable = dismissable;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Long getRemindOn() {
		return remindOn;
	}
	public void setRemindOn(Long remindOn) {
		this.remindOn = remindOn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCreatedByID() {
		return createdByID;
	}
	public void setCreatedByID(Integer createdByID) {
		this.createdByID = createdByID;
	}
	public Integer getLoanID() {
		return loanID;
	}
	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}
	public Integer getCreatedForID() {
		return createdForID;
	}
	public void setCreatedForID(Integer createdForID) {
		this.createdForID = createdForID;
	}

}