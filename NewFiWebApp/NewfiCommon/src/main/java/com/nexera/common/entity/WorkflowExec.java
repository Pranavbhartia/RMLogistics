package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the workflow database table.
 * 
 */
@Entity
@Table(name = "workflowexec")
@NamedQuery(name = "WorkflowExec.findAll", query = "SELECT w FROM WorkflowExec w")
public class WorkflowExec implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean active;
	private Date createdTime;
	private WorkflowItemExec currentExecutingItem;
	private Date executionCompleteTime;
	private Date lastUpdatedTime;
	private byte[] meta;
	private String status;
	private String summary;
	private WorkflowMaster workflowMaster;
	private Integer createdByID;
	private List<WorkflowItemExec> workflowItems;

	public WorkflowExec() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_executing_item")
	public WorkflowItemExec getCurrentExecutingItem() {
		return this.currentExecutingItem;
	}

	public void setCurrentExecutingItem(WorkflowItemExec currentExecutingItem) {
		this.currentExecutingItem = currentExecutingItem;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "execution_complete_time")
	public Date getExecutionCompleteTime() {
		return this.executionCompleteTime;
	}

	public void setExecutionCompleteTime(Date executionCompleteTime) {
		this.executionCompleteTime = executionCompleteTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_updated_time")
	public Date getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	@Lob
	public byte[] getMeta() {
		return this.meta;
	}

	public void setMeta(byte[] meta) {
		this.meta = meta;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	// bi-directional many-to-one association to WorkflowMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_master")
	public WorkflowMaster getWorkflowMaster() {
		return this.workflowMaster;
	}

	public void setWorkflowMaster(WorkflowMaster workflowmaster) {
		this.workflowMaster = workflowmaster;
	}

	@Column(name = "created_by")
	public Integer getCreatedByID() {
		return createdByID;
	}

	public void setCreatedByID(Integer createdByID) {
		this.createdByID = createdByID;
	}

	// bi-directional many-to-one association to WorkflowItem
	@OneToMany(mappedBy = "parentWorkflow")
	public List<WorkflowItemExec> getWorkflowItems() {
		return this.workflowItems;
	}

	public void setWorkflowItems(List<WorkflowItemExec> workflowitems) {
		this.workflowItems = workflowitems;
	}

	public WorkflowItemExec addWorkflowItem(WorkflowItemExec workflowitem) {
		getWorkflowItems().add(workflowitem);
		workflowitem.setParentWorkflow(this);

		return workflowitem;
	}

	public WorkflowItemExec removeWorkflowItem(WorkflowItemExec workflowitem) {
		getWorkflowItems().remove(workflowitem);
		workflowitem.setParentWorkflow(null);

		return workflowitem;
	}

}