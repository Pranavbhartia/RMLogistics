package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
public class LoanNotificationVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte[] content;
	private Date createdDate;
	private Boolean dismissable;
	private String notificationType;
	private String priority;
	private Boolean read;
	private Date remindOn;
	private String title;
	private UserVO createdBy;
	private LoanVO loan;
	private UserVO createdFor;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
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
	public Date getRemindOn() {
		return remindOn;
	}
	public void setRemindOn(Date remindOn) {
		this.remindOn = remindOn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public UserVO getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserVO createdBy) {
		this.createdBy = createdBy;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public UserVO getCreatedFor() {
		return createdFor;
	}
	public void setCreatedFor(UserVO createdFor) {
		this.createdFor = createdFor;
	}

}