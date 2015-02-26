package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the loannotification database table.
 * 
 */
@Entity
@NamedQuery(name = "LoanNotification.findAll", query = "SELECT l FROM LoanNotification l")
public class LoanNotification implements Serializable {
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
	private User createdBy;
	private Loan loan;
	private User createdFor;

	public LoanNotification() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Lob
	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getDismissable() {
		return this.dismissable;
	}

	public void setDismissable(Boolean dismissable) {
		this.dismissable = dismissable;
	}

	@Column(name = "notification_type")
	public String getNotificationType() {
		return this.notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getRead() {
		return this.read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remind_on")
	public Date getRemindOn() {
		return this.remindOn;
	}

	public void setRemindOn(Date remindOn) {
		this.remindOn = remindOn;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	// bi-directional many-to-one association to Loan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan")
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_for")
	public User getCreatedFor() {
		return createdFor;
	}

	public void setCreatedFor(User createdFor) {
		this.createdFor = createdFor;
	}

}