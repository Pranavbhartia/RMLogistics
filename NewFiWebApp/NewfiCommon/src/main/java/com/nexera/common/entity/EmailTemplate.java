package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the emailtemplate database table.
 * 
 */
@Entity
@NamedQuery(name = "EmailTemplate.findAll", query = "SELECT e FROM EmailTemplate e")
public class EmailTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String description;
	private String from;
	private Date modifiedDate;
	private String name;
	private User user;
	private List<UserEmail> userEmails;

	public EmailTemplate() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// bi-directional many-to-one association to UserEmail
	@OneToMany(mappedBy = "emailtemplate")
	public List<UserEmail> getUserEmails() {
		return this.userEmails;
	}

	public void setUserEmails(List<UserEmail> userEmails) {
		this.userEmails = userEmails;
	}

	public UserEmail addUseremail(UserEmail userEmail) {
		getUserEmails().add(userEmail);
		userEmail.setEmailtemplate(this);

		return userEmail;
	}

	public UserEmail removeUseremail(UserEmail userEmail) {
		getUserEmails().remove(userEmail);
		userEmail.setEmailtemplate(null);

		return userEmail;
	}

}