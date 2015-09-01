package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the loanmilestone database table.
 * 
 */
@Entity
@Table(name = "loanmilestone")
@NamedQuery(name = "LoanMilestone.findAll", query = "SELECT l FROM LoanMilestone l")
public class LoanMilestone implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Date statusUpdateTime;
	private String status;
	private LoanMilestoneMaster loanMilestoneMaster;
	private Loan loan;
	private Integer order;
	private Date statusInsertTime;

	public LoanMilestone() {
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// bi-directional many-to-one association to LoanMilestoneMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "milestone")
	public LoanMilestoneMaster getLoanMilestoneMaster() {
		return loanMilestoneMaster;
	}

	public void setLoanMilestoneMaster(LoanMilestoneMaster loanMilestoneMaster) {
		this.loanMilestoneMaster = loanMilestoneMaster;
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_update_time")
	public Date getStatusUpdateTime() {
		return statusUpdateTime;
	}

	public void setStatusUpdateTime(Date statusUpdateTime) {
		this.statusUpdateTime = statusUpdateTime;
	}

	@Column(name = "milestone_order")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "status_insert_time")
	public Date getStatusInsertTime() {
		return statusInsertTime;
	}

	public void setStatusInsertTime(Date statusInsertTime) {
		this.statusInsertTime = statusInsertTime;
	}

}