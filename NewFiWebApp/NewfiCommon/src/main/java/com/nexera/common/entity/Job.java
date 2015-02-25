package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the job database table.
 * 
 */
@Entity
@NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date endTime;
	private String params;
	private byte[] result;
	private Date startTime;
	private String status;
	private JobMaster jobMaster;

	public Job() {
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
	@Column(name = "end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	@Lob
	@Column(name="result")
	public byte[] getResult() {
		return this.result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// bi-directional many-to-one association to JobMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job")
	public JobMaster getJobMaster() {
		return jobMaster;
	}

	public void setJobMaster(JobMaster jobMaster) {
		this.jobMaster = jobMaster;
	}

}