package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loannotification database table.
 * 
 */
@Entity
@NamedQuery(name="LoanNotification.findAll", query="SELECT l FROM LoanNotification l")
public class LoanNotification implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte[] content;
	private Date createdDate;
	private byte dismissable;
	private String notificationType;
	private String priority;
	private byte read;
	private Date remindOn;
	private String title;
	private User user1;
	private Loan loanBean;
	private User user2;

	public LoanNotification() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@Column(name="created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public byte getDismissable() {
		return this.dismissable;
	}

	public void setDismissable(byte dismissable) {
		this.dismissable = dismissable;
	}


	@Column(name="notification_type")
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


	public byte getRead() {
		return this.read;
	}

	public void setRead(byte read) {
		this.read = read;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="remind_on")
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


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to Loan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan")
	public Loan getLoanBean() {
		return this.loanBean;
	}

	public void setLoanBean(Loan loanBean) {
		this.loanBean = loanBean;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_for")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

}