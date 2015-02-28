package com.nexera.common.vo;

import java.util.List;

public class LoanCustomerVO {

	private String name;
	private String prof_image;
	private String time;
	private String phone_no;
	private String purpose;
	private String processor;
	private String credit_score;
	private String alert_count;
	private Integer userID;
	private Integer loanID;
	private List<AlertListVO> alerts;
	private List<NotesVO> notes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProf_image() {
		return prof_image;
	}

	public void setProf_image(String prof_image) {
		this.prof_image = prof_image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getCredit_score() {
		return credit_score;
	}

	public void setCredit_score(String credit_score) {
		this.credit_score = credit_score;
	}

	public String getAlert_count() {
		return alert_count;
	}

	public void setAlert_count(String alert_count) {
		this.alert_count = alert_count;
	}

	public List<AlertListVO> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<AlertListVO> alerts) {
		this.alerts = alerts;
	}

	public List<NotesVO> getNotes() {
		return notes;
	}

	public void setNotes(List<NotesVO> notes) {
		this.notes = notes;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getLoanID() {
		return loanID;
	}

	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}

}
