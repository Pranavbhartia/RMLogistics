package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the useremail database table.
 * 
 */
@Entity
@NamedQuery(name="UserEmail.findAll", query="SELECT u FROM UserEmail u")
public class UserEmail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private String priority;
	private byte status;
	private String to;
	private byte[] tokenMap;
	private User user1;
	private User user2;
	private EmailTemplate emailtemplate;

	public UserEmail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public String getPriority() {
		return this.priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}


	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}


	public String getTo() {
		return this.to;
	}

	public void setTo(String to) {
		this.to = to;
	}


	@Lob
	@Column(name="token_map")
	public byte[] getTokenMap() {
		return this.tokenMap;
	}

	public void setTokenMap(byte[] tokenMap) {
		this.tokenMap = tokenMap;
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


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}


	//bi-directional many-to-one association to EmailTemplate
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="email_template")
	public EmailTemplate getEmailtemplate() {
		return this.emailtemplate;
	}

	public void setEmailtemplate(EmailTemplate emailtemplate) {
		this.emailtemplate = emailtemplate;
	}

}