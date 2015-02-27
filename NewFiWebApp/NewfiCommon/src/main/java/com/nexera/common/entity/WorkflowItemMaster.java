package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the workflowitemmaster database table.
 * 
 */
@Entity
@Table(name = "workflowitemmaster")
@NamedQuery(name = "WorkflowItemMaster.findAll", query = "SELECT w FROM WorkflowItemMaster w")
public class WorkflowItemMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createdDate;
	private String description;
	private Boolean isLastTask;
	private Integer maxRunTime;
	private Date modifiedDate;
	private byte priority;
	private Integer startDelay;
	private WorkflowTaskConfigMaster task;
	private String workflowItemType;
	private List<WorkflowItemExec> workflowItems;
	private User createdBy;
	private User modifiedBy;
	private WorkflowItemMaster onSuccess;
	private List<WorkflowItemMaster> listOnSuccess;
	private WorkflowItemMaster onFailure;

	public WorkflowItemMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "is_last_task", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsLastTask() {
		return this.isLastTask;
	}

	public void setIsLastTask(Boolean isLastTask) {
		this.isLastTask = isLastTask;
	}

	@Column(name = "max_run_time")
	public Integer getMaxRunTime() {
		return this.maxRunTime;
	}

	public void setMaxRunTime(Integer maxRunTime) {
		this.maxRunTime = maxRunTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public byte getPriority() {
		return this.priority;
	}

	public void setPriority(byte priority) {
		this.priority = priority;
	}

	@Column(name = "start_delay")
	public Integer getStartDelay() {
		return this.startDelay;
	}

	public void setStartDelay(Integer startDelay) {
		this.startDelay = startDelay;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_task")
	public WorkflowTaskConfigMaster getTask() {
		return task;
	}

	public void setTask(WorkflowTaskConfigMaster task) {
		this.task = task;
	}

	@Column(name = "workflow_item_type")
	public String getWorkflowItemType() {
		return this.workflowItemType;
	}

	public void setWorkflowItemType(String workflowItemType) {
		this.workflowItemType = workflowItemType;
	}

	// bi-directional many-to-one association to WorkflowItem
	@OneToMany(mappedBy = "workflowItemMaster")
	public List<WorkflowItemExec> getWorkflowItems() {
		return this.workflowItems;
	}


	public void setWorkflowItems(List<WorkflowItemExec> workflowItems) {

		this.workflowItems = workflowItems;
	}

	public WorkflowItemExec addWorkflowItem(WorkflowItemExec workflowitem) {
		getWorkflowItems().add(workflowitem);
		workflowitem.setWorkflowItemMaster(this);

		return workflowitem;
	}

	public WorkflowItemExec removeWorkflowItem(WorkflowItemExec workflowitem) {
		getWorkflowItems().remove(workflowitem);
		workflowitem.setWorkflowItemMaster(null);

		return workflowitem;
	}

	// bi-directional many-to-one association to WorkflowItemMaster
	@OneToMany(mappedBy = "onSuccess")
	public List<WorkflowItemMaster> getListOnSuccess() {
		return this.listOnSuccess;
	}

	public void setListOnSuccess(List<WorkflowItemMaster> listOnSuccess) {
		this.listOnSuccess = listOnSuccess;
	}

	public WorkflowItemMaster addWorkflowitemmasters1(
			WorkflowItemMaster workflowitemmasters1) {
		getListOnSuccess().add(workflowitemmasters1);
		workflowitemmasters1.setOnSuccess(this);

		return workflowitemmasters1;
	}

	public WorkflowItemMaster removeWorkflowitemmasters1(
			WorkflowItemMaster workflowitemmasters1) {
		getListOnSuccess().remove(workflowitemmasters1);
		workflowitemmasters1.setOnSuccess(null);

		return workflowitemmasters1;
	}

	// bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "on_failure")
	public WorkflowItemMaster getOnFailure() {
		return onFailure;
	}

	public void setOnFailure(WorkflowItemMaster onFailure) {
		this.onFailure = onFailure;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by")
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	// bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "on_success")
	public WorkflowItemMaster getOnSuccess() {
		return onSuccess;
	}

	public void setOnSuccess(WorkflowItemMaster onSuccess) {
		this.onSuccess = onSuccess;
	}

}