package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the workflow database table.
 * 
 */
@Entity
@NamedQuery(name="Workflow.findAll", query="SELECT w FROM Workflow w")
public class Workflow implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte active;
	private Date createdTime;
	private int currentExecutingItem;
	private Date executionCompleteTime;
	private Date lastUpdatedTime;
	private byte[] meta;
	private String status;
	private String summary;
	private WorkflowMaster workflowmaster;
	private User user;
	private List<WorkflowItem> workflowitems;

	public Workflow() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_time")
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	@Column(name="current_executing_item")
	public int getCurrentExecutingItem() {
		return this.currentExecutingItem;
	}

	public void setCurrentExecutingItem(int currentExecutingItem) {
		this.currentExecutingItem = currentExecutingItem;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="execution_complete_time")
	public Date getExecutionCompleteTime() {
		return this.executionCompleteTime;
	}

	public void setExecutionCompleteTime(Date executionCompleteTime) {
		this.executionCompleteTime = executionCompleteTime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_updated_time")
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


	//bi-directional many-to-one association to WorkflowMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="workflow")
	public WorkflowMaster getWorkflowmaster() {
		return this.workflowmaster;
	}

	public void setWorkflowmaster(WorkflowMaster workflowmaster) {
		this.workflowmaster = workflowmaster;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	//bi-directional many-to-one association to WorkflowItem
	@OneToMany(mappedBy="workflow")
	public List<WorkflowItem> getWorkflowitems() {
		return this.workflowitems;
	}

	public void setWorkflowitems(List<WorkflowItem> workflowitems) {
		this.workflowitems = workflowitems;
	}

	public WorkflowItem addWorkflowitem(WorkflowItem workflowitem) {
		getWorkflowitems().add(workflowitem);
		workflowitem.setWorkflow(this);

		return workflowitem;
	}

	public WorkflowItem removeWorkflowitem(WorkflowItem workflowitem) {
		getWorkflowitems().remove(workflowitem);
		workflowitem.setWorkflow(null);

		return workflowitem;
	}

}