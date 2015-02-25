package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanmilestone database table.
 * 
 */
@Entity
@NamedQuery(name="LoanMilestone.findAll", query="SELECT l FROM LoanMilestone l")
public class LoanMilestone implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte[] comments;
	private Date createdDate;
	private Date endDate;
	private Date startDate;
	private String status;
	private LoanMilestoneMaster loanmilestonemaster;
	private Loan loanBean;

	public LoanMilestone() {
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
	public byte[] getComments() {
		return this.comments;
	}

	public void setComments(byte[] comments) {
		this.comments = comments;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	//bi-directional many-to-one association to LoanMilestoneMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="milestone")
	public LoanMilestoneMaster getLoanmilestonemaster() {
		return this.loanmilestonemaster;
	}

	public void setLoanmilestonemaster(LoanMilestoneMaster loanmilestonemaster) {
		this.loanmilestonemaster = loanmilestonemaster;
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

}