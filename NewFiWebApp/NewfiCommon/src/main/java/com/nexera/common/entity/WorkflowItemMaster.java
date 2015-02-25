package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the workflowitemmaster database table.
 * 
 */
@Entity
@NamedQuery(name="WorkflowItemMaster.findAll", query="SELECT w FROM WorkflowItemMaster w")
public class WorkflowItemMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private String description;
	private byte isLastTask;
	private int maxRunTime;
	private Date modifiedDate;
	private byte priority;
	private int startDelay;
	private String taskName;
	private String workflowItemType;
	private List<WorkflowItem> workflowitems;
	private User user1;
	private User user2;
	private WorkflowItemMaster workflowitemmaster1;
	private List<WorkflowItemMaster> workflowitemmasters1;
	private WorkflowItemMaster workflowitemmaster2;
	private List<WorkflowItemMaster> workflowitemmasters2;
	private List<WorkflowMaster> workflowmasters;

	public WorkflowItemMaster() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
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


	@Column(name="is_last_task")
	public byte getIsLastTask() {
		return this.isLastTask;
	}

	public void setIsLastTask(byte isLastTask) {
		this.isLastTask = isLastTask;
	}


	@Column(name="max_run_time")
	public int getMaxRunTime() {
		return this.maxRunTime;
	}

	public void setMaxRunTime(int maxRunTime) {
		this.maxRunTime = maxRunTime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
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


	@Column(name="start_delay")
	public int getStartDelay() {
		return this.startDelay;
	}

	public void setStartDelay(int startDelay) {
		this.startDelay = startDelay;
	}


	@Column(name="task_name")
	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	@Column(name="workflow_item_type")
	public String getWorkflowItemType() {
		return this.workflowItemType;
	}

	public void setWorkflowItemType(String workflowItemType) {
		this.workflowItemType = workflowItemType;
	}


	//bi-directional many-to-one association to WorkflowItem
	@OneToMany(mappedBy="workflowitemmaster")
	public List<WorkflowItem> getWorkflowitems() {
		return this.workflowitems;
	}

	public void setWorkflowitems(List<WorkflowItem> workflowitems) {
		this.workflowitems = workflowitems;
	}

	public WorkflowItem addWorkflowitem(WorkflowItem workflowitem) {
		getWorkflowitems().add(workflowitem);
		workflowitem.setWorkflowitemmaster(this);

		return workflowitem;
	}

	public WorkflowItem removeWorkflowitem(WorkflowItem workflowitem) {
		getWorkflowitems().remove(workflowitem);
		workflowitem.setWorkflowitemmaster(null);

		return workflowitem;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by")
	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by")
	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="on_success")
	public WorkflowItemMaster getWorkflowitemmaster1() {
		return this.workflowitemmaster1;
	}

	public void setWorkflowitemmaster1(WorkflowItemMaster workflowitemmaster1) {
		this.workflowitemmaster1 = workflowitemmaster1;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@OneToMany(mappedBy="workflowitemmaster1")
	public List<WorkflowItemMaster> getWorkflowitemmasters1() {
		return this.workflowitemmasters1;
	}

	public void setWorkflowitemmasters1(List<WorkflowItemMaster> workflowitemmasters1) {
		this.workflowitemmasters1 = workflowitemmasters1;
	}

	public WorkflowItemMaster addWorkflowitemmasters1(WorkflowItemMaster workflowitemmasters1) {
		getWorkflowitemmasters1().add(workflowitemmasters1);
		workflowitemmasters1.setWorkflowitemmaster1(this);

		return workflowitemmasters1;
	}

	public WorkflowItemMaster removeWorkflowitemmasters1(WorkflowItemMaster workflowitemmasters1) {
		getWorkflowitemmasters1().remove(workflowitemmasters1);
		workflowitemmasters1.setWorkflowitemmaster1(null);

		return workflowitemmasters1;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="on_failure")
	public WorkflowItemMaster getWorkflowitemmaster2() {
		return this.workflowitemmaster2;
	}

	public void setWorkflowitemmaster2(WorkflowItemMaster workflowitemmaster2) {
		this.workflowitemmaster2 = workflowitemmaster2;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@OneToMany(mappedBy="workflowitemmaster2")
	public List<WorkflowItemMaster> getWorkflowitemmasters2() {
		return this.workflowitemmasters2;
	}

	public void setWorkflowitemmasters2(List<WorkflowItemMaster> workflowitemmasters2) {
		this.workflowitemmasters2 = workflowitemmasters2;
	}

	public WorkflowItemMaster addWorkflowitemmasters2(WorkflowItemMaster workflowitemmasters2) {
		getWorkflowitemmasters2().add(workflowitemmasters2);
		workflowitemmasters2.setWorkflowitemmaster2(this);

		return workflowitemmasters2;
	}

	public WorkflowItemMaster removeWorkflowitemmasters2(WorkflowItemMaster workflowitemmasters2) {
		getWorkflowitemmasters2().remove(workflowitemmasters2);
		workflowitemmasters2.setWorkflowitemmaster2(null);

		return workflowitemmasters2;
	}


	//bi-directional many-to-one association to WorkflowMaster
	@OneToMany(mappedBy="workflowitemmaster")
	public List<WorkflowMaster> getWorkflowmasters() {
		return this.workflowmasters;
	}

	public void setWorkflowmasters(List<WorkflowMaster> workflowmasters) {
		this.workflowmasters = workflowmasters;
	}

	public WorkflowMaster addWorkflowmaster(WorkflowMaster workflowmaster) {
		getWorkflowmasters().add(workflowmaster);
		workflowmaster.setWorkflowitemmaster(this);

		return workflowmaster;
	}

	public WorkflowMaster removeWorkflowmaster(WorkflowMaster workflowmaster) {
		getWorkflowmasters().remove(workflowmaster);
		workflowmaster.setWorkflowitemmaster(null);

		return workflowmaster;
	}

}