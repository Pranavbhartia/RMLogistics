package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the batchjobexecution database table.
 * 
 */
@Entity
@Table(name = "batchjobexecution")
@NamedQuery(name = "BatchJobExecution.findAll", query = "SELECT b FROM BatchJobExecution b")
public class BatchJobExecution implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Date dateLastRunEndtime;
	private Date dateLastRunStartTime;
	private BatchJobMaster batchJobMaster;

	public BatchJobExecution() {
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
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_run_endtime")
	public Date getDateLastRunEndtime() {
		return this.dateLastRunEndtime;
	}

	public void setDateLastRunEndtime(Date dateLastRunEndtime) {
		this.dateLastRunEndtime = dateLastRunEndtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_run_starttime")
	public Date getDateLastRunStartTime() {
		return this.dateLastRunStartTime;
	}

	public void setDateLastRunStartTime(Date dateLastRunStarttime) {
		this.dateLastRunStartTime = dateLastRunStarttime;
	}

	// bi-directional many-to-one association to BatchJobMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "batchjob_id")
	public BatchJobMaster getBatchJobMaster() {
		return batchJobMaster;
	}

	public void setBatchJobMaster(BatchJobMaster batchJobMaster) {
		this.batchJobMaster = batchJobMaster;
	}

}