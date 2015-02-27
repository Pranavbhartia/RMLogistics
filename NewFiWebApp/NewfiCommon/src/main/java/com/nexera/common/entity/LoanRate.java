package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the loanrate database table.
 * 
 */
@Entity
@Table(name = "loanrate")
@NamedQuery(name = "LoanRate.findAll", query = "SELECT l FROM LoanRate l")
public class LoanRate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Date lastCachedTime;
	private Date modifiedDate;
	private Double value;
	private Loan loan;
	private User modifiedBy;

	public LoanRate() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_cached_time")
	public Date getLastCachedTime() {
		return this.lastCachedTime;
	}

	public void setLastCachedTime(Date lastCachedTime) {
		this.lastCachedTime = lastCachedTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double value) {
		this.value = value;
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
	@JoinColumn(name = "modified_user")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}