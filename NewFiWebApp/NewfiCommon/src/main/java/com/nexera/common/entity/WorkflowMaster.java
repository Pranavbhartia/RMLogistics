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

/**
 * The persistent class for the workflowmaster database table.
 * 
 */
@Entity
@Table(name = "workflowmaster")
@NamedQuery(name = "WorkflowMaster.findAll", query = "SELECT w FROM WorkflowMaster w")
public class WorkflowMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createdDate;
	private String description;
	private Date modifiedDate;
	private String name;
	private String workflowType;
	private Integer createdBy;
	private Integer modifiedBy;
	private WorkflowItemMaster startWithWorkflow;
	private List<WorkflowItemMaster> workflowItemMasterList;

	public WorkflowMaster() {
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

	@Column(name = "workflow_type")
	public String getWorkflowType() {
		return this.workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

	// bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "start_with")
	public WorkflowItemMaster getStartWithWorkflow() {
		return startWithWorkflow;
	}

	public void setStartWithWorkflow(WorkflowItemMaster startWithWorkflow) {
		this.startWithWorkflow = startWithWorkflow;
	}

	@Column(name = "created_by")
	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "modified_by")
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@OneToMany(mappedBy = "parentWorkflowMaster")
	public List<WorkflowItemMaster> getWorkflowItemMasterList() {
		return workflowItemMasterList;
	}

	public void setWorkflowItemMasterList(
			List<WorkflowItemMaster> workflowItemMasterList) {
		this.workflowItemMasterList = workflowItemMasterList;
	}

}