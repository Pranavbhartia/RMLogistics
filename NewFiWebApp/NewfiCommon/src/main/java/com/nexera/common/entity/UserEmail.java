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
	private Integer id;
	private Date createdDate;
	private String priority;
	private Boolean status;
	private String to;
	private byte[] tokenMap;
	private User createdBy;
	private User user;
	private EmailTemplate emailTemplate;

	public UserEmail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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


	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
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
	@JoinColumn(name="user")
	public User getUser() {
		return this.user;
	}

	public void setUser2(User user) {
		this.user = user;
	}


	//bi-directional many-to-one association to EmailTemplate
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="email_template")
	public EmailTemplate getEmailTemplate() {
		return this.emailTemplate;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	public User getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

}