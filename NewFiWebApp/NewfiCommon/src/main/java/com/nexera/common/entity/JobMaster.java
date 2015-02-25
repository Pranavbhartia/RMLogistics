package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the jobmaster database table.
 * 
 */
@Entity
@NamedQuery(name = "JobMaster.findAll", query = "SELECT j FROM JobMaster j")
public class JobMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean active;
	private String description;
	private String jobType;
	private Date modifiedDate;
	private String name;
	private Integer repeatInterval;
	private String repeatIntervalScheme;
	private String taskName;
	private List<Job> jobs;
	private User user;

	public JobMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "active")
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "repeat_interval")
	public Integer getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(Integer repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "job_type")
	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
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

	@Column(name = "repeat_interval_scheme")
	public String getRepeatIntervalScheme() {
		return this.repeatIntervalScheme;
	}

	public void setRepeatIntervalScheme(String repeatIntervalScheme) {
		this.repeatIntervalScheme = repeatIntervalScheme;
	}

	@Column(name = "task_name")
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	// bi-directional many-to-one association to Job
	@OneToMany(mappedBy = "jobmaster")
	public List<Job> getJobs() {
		return this.jobs;
	}

	public void setJobs(List<Job> jobs) {
		this.jobs = jobs;
	}

	public Job addJob(Job job) {
		getJobs().add(job);
		job.setJobmaster(this);

		return job;
	}

	public Job removeJob(Job job) {
		getJobs().remove(job);
		job.setJobmaster(null);

		return job;
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

}