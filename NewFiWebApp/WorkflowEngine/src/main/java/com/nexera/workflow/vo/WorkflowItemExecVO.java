package com.nexera.workflow.vo;

import java.util.Date;

import com.nexera.workflow.bean.WorkflowItemExec;

public class WorkflowItemExecVO {
	private Integer id;
	private Date creationDate;
	private Date modifiedDate;
	private Date startTime;
	private Date endTime;
	private String status;
	private Boolean success;
	private String displayContent;
	private WorkflowItemExecVO parentWorkflowItemExec;
	public String stateInfo;
	private boolean clickable;
	
	public String workflowItemType;

	public String getWorkflowItemType() {
		return workflowItemType;
	}

	public void setWorkflowItemType(String workflowItemType) {
		this.workflowItemType = workflowItemType;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public WorkflowItemExecVO getParentWorkflowItemExec() {
		return parentWorkflowItemExec;
	}

	public void setParentWorkflowItemExec(
	        WorkflowItemExecVO parentWorkflowItemExec) {
		this.parentWorkflowItemExec = parentWorkflowItemExec;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDisplayContent() {
		return displayContent;
	}

	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}

	public WorkflowItemExecVO convertToVO(WorkflowItemExec entity) {
		WorkflowItemExecVO workflowItemExecVO = new WorkflowItemExecVO();
		workflowItemExecVO.setCreationDate(entity.getCreationDate());
		workflowItemExecVO.setEndTime(entity.getEndTime());
		workflowItemExecVO.setId(entity.getId());
		workflowItemExecVO.setModifiedDate(entity.getModifiedDate());
		workflowItemExecVO.setStatus(entity.getStatus());
		workflowItemExecVO.setDisplayContent(entity.getWorkflowItemMaster()
		        .getDescription());
		
		if (entity.getParentWorkflowItemExec() != null) {
			workflowItemExecVO.setParentWorkflowItemExec(this
			        .convertToVO(entity.getParentWorkflowItemExec()));
			
		}
		workflowItemExecVO.setWorkflowItemType(entity.getWorkflowItemMaster().getWorkflowItemType());
		return workflowItemExecVO;
	}

	public boolean isClickable() {
	    return clickable;
    }

	public void setClickable(boolean clickable) {
	    this.clickable = clickable;
    }

}
