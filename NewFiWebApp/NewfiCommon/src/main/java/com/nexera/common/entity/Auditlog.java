package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the auditlog database table.
 * 
 */
@Entity
@NamedQuery(name = "Auditlog.findAll", query = "SELECT a FROM Auditlog a")
public class Auditlog implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date auditDate;
	private String auditType;
	private String content;
	private String operation;
	private User user;

	public Auditlog() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "audit_date")
	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name = "audit_type")
	public String getAuditType() {
		return this.auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}