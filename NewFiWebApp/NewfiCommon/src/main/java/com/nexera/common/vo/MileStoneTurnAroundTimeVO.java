package com.nexera.common.vo;

public class MileStoneTurnAroundTimeVO {

	private Integer id;
	private String name;
	private Integer workflowItemMasterId;
	private Integer hours;
	private Integer createdby;
	private Integer modifiedby;
	private Integer orderBy;
	
	

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWorkflowItemMasterId() {
		return workflowItemMasterId;
	}

	public void setWorkflowItemMasterId(Integer workflowItemMasterId) {
		this.workflowItemMasterId = workflowItemMasterId;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Integer createdby) {
		this.createdby = createdby;
	}

	public Integer getModifiedby() {
		return modifiedby;
	}

	public void setModifiedby(Integer modifiedby) {
		this.modifiedby = modifiedby;
	}

}
