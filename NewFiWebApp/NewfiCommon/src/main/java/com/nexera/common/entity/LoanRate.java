package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanrate database table.
 * 
 */
@Entity
@NamedQuery(name="LoanRate.findAll", query="SELECT l FROM LoanRate l")
public class LoanRate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Date lastCachedTime;
	private Date modifiedDate;
	private double value;
	private Loan loanBean;
	private User user;

	public LoanRate() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
	@Column(name="last_cached_time")
	public Date getLastCachedTime() {
		return this.lastCachedTime;
	}

	public void setLastCachedTime(Date lastCachedTime) {
		this.lastCachedTime = lastCachedTime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
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
	@JoinColumn(name="modified_user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}