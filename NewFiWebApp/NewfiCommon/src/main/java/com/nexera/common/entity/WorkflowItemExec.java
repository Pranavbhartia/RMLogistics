package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;

/**
 * The persistent class for the workflowitem database table.
 * 
 */
@Entity
@Table(name = "workflowitemexec")
@NamedQuery(name = "WorkflowItemExec.findAll", query = "SELECT w FROM WorkflowItemExec w")
public class WorkflowItemExec implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date creationDate;
	private Date modifiedDate;
	private Date startTime;
	private Date endTime;
	private Boolean status;
	private Boolean success;
	private WorkflowItemMaster workflowItemMaster;
	private WorkflowExec parentWorkflow;

	public WorkflowItemExec() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "creation_date")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getSuccess() {
		return this.success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	// bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_item_master")
	public WorkflowItemMaster getWorkflowItemMaster() {
		return this.workflowItemMaster;
	}

	public void setWorkflowItemMaster(WorkflowItemMaster workflowItemMaster) {
		this.workflowItemMaster = workflowItemMaster;
	}

	// bi-directional many-to-one association to Workflow
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_workflow")
	public WorkflowExec getParentWorkflow() {
		return parentWorkflow;
	}

	public void setParentWorkflow(WorkflowExec parentWorkflow) {
		this.parentWorkflow = parentWorkflow;
	}

}