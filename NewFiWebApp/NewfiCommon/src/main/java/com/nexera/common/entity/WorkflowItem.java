package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the workflowitem database table.
 * 
 */
@Entity
@NamedQuery(name="WorkflowItem.findAll", query="SELECT w FROM WorkflowItem w")
public class WorkflowItem implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int completionTime;
	private Date creationDate;
	private Date modifiedDate;
	private byte[] params;
	private byte[] result;
	private Date startTime;
	private byte status;
	private byte success;
	private WorkflowItemMaster workflowitemmaster;
	private Workflow workflow;

	public WorkflowItem() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="completion_time")
	public int getCompletionTime() {
		return this.completionTime;
	}

	public void setCompletionTime(int completionTime) {
		this.completionTime = completionTime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_date")
	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Lob
	public byte[] getParams() {
		return this.params;
	}

	public void setParams(byte[] params) {
		this.params = params;
	}


	@Lob
	public byte[] getResult() {
		return this.result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}


	public byte getSuccess() {
		return this.success;
	}

	public void setSuccess(byte success) {
		this.success = success;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="workflow_item")
	public WorkflowItemMaster getWorkflowitemmaster() {
		return this.workflowitemmaster;
	}

	public void setWorkflowitemmaster(WorkflowItemMaster workflowitemmaster) {
		this.workflowitemmaster = workflowitemmaster;
	}


	//bi-directional many-to-one association to Workflow
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_workflow")
	public Workflow getWorkflow() {
		return this.workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

}