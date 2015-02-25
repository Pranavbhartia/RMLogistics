package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the workflowmaster database table.
 * 
 */
@Entity
@NamedQuery(name="WorkflowMaster.findAll", query="SELECT w FROM WorkflowMaster w")
public class WorkflowMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date createdDate;
	private String description;
	private Date modifiedDate;
	private String name;
	private String workflowType;
	private List<Workflow> workflows;
	private User user1;
	private User user2;
	private WorkflowItemMaster workflowitemmaster;

	public WorkflowMaster() {
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


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
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


	@Column(name="workflow_type")
	public String getWorkflowType() {
		return this.workflowType;
	}

	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}


	//bi-directional many-to-one association to Workflow
	@OneToMany(mappedBy="workflowmaster")
	public List<Workflow> getWorkflows() {
		return this.workflows;
	}

	public void setWorkflows(List<Workflow> workflows) {
		this.workflows = workflows;
	}

	public Workflow addWorkflow(Workflow workflow) {
		getWorkflows().add(workflow);
		workflow.setWorkflowmaster(this);

		return workflow;
	}

	public Workflow removeWorkflow(Workflow workflow) {
		getWorkflows().remove(workflow);
		workflow.setWorkflowmaster(null);

		return workflow;
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
	@JoinColumn(name="start_with")
	public WorkflowItemMaster getWorkflowitemmaster() {
		return this.workflowitemmaster;
	}

	public void setWorkflowitemmaster(WorkflowItemMaster workflowitemmaster) {
		this.workflowitemmaster = workflowitemmaster;
	}

}