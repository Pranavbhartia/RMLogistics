package com.nexera.workflow.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.dao.WorkflowExecDao;
import com.nexera.workflow.dao.WorkflowItemExecDao;
import com.nexera.workflow.dao.WorkflowItemMasterDao;
import com.nexera.workflow.dao.WorkflowMasterDao;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@Component

public class WorkflowServiceImpl implements WorkflowService
{

    @Autowired
    WorkflowMasterDao workflowMasterDao;

    @Autowired
    WorkflowExecDao workflowExecDao;

    @Autowired
    WorkflowItemExecDao workflowItemExecDao;

    @Autowired
    WorkflowItemMasterDao workflowItemMasterDao;

    private static final Logger LOGGER = LoggerFactory.getLogger( WorkflowServiceImpl.class );


    

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#getWorkFlowByWorkFlowType()
	 */
	@Override
	@Transactional
	public WorkflowMaster getWorkFlowByWorkFlowType(String workflowType) {
		LOGGER.debug("Inside method getWorkFlowByWorkFlowType ");
		WorkflowMaster workflowMaster = workflowMasterDao
		        .findWorkflowByType(workflowType);
		return workflowMaster;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#setWorkflowIntoExecution(
	 * com.nexera.workflow.bean.WorkflowMaster)
	 */
	@Override
	@Transactional
	public WorkflowExec setWorkflowIntoExecution(WorkflowMaster workflowMaster) {
		LOGGER.debug("Inside method setWorkflowIntoExecution ");
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setCreatedByID(workflowMaster.getCreatedBy());
		workflowExec.setActive(true);
		workflowExec.setCreatedTime(new Date());
		workflowExec.setWorkflowMaster(workflowMaster);
		workflowExec.setStatus(WorkItemStatus.NOT_STARTED.getStatus());
		
		int id = (Integer) workflowExecDao.save(workflowExec);
		workflowExec.setId(id);
		return workflowExec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#setWorkflowItemIntoExecution
	 * (com.nexera.workflow.bean.WorkflowItemMaster)
	 */
	@Override
	@Transactional
	public WorkflowItemExec setWorkflowItemIntoExecution(
	        WorkflowExec parentWorkflow, WorkflowItemMaster workflowItemMaster,
	        WorkflowItemExec parentWorkflowItemExec) {
		LOGGER.debug("Inside method setWorkflowIntoExecution ");
		WorkflowItemExec workflowItemExec = new WorkflowItemExec();
		if (parentWorkflowItemExec != null)
			workflowItemExec.setParentWorkflowItemExec(parentWorkflowItemExec);
		workflowItemExec.setWorkflowItemMaster(workflowItemMaster);
		workflowItemExec.setParentWorkflow(parentWorkflow);
		workflowItemExec.setStatus(WorkItemStatus.NOT_STARTED.getStatus());
		workflowItemExec.setCreationDate(new Date());
		workflowItemExec.setClickable(workflowItemMaster.getClickable());
		workflowItemExec.setDisplayOrder(workflowItemMaster.getDisplayOrder());
		int id = (Integer) workflowItemExecDao.save(workflowItemExec);
		workflowItemExec.setId(id);
		return workflowItemExec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nexera.workflow.service.WorkflowService#
	 * getWorkflowItemMasterListByWorkflowMaster
	 * (com.nexera.workflow.bean.WorkflowMaster)
	 */
	@Override
	@Transactional
	public List<WorkflowItemMaster> getWorkflowItemMasterListByWorkflowMaster(
	        WorkflowMaster workflowMaster) {
		LOGGER.debug("Inside method getWorkflowItemMasterListByWorkflowMaster ");
		List<WorkflowItemMaster> workflowItemMasterList = workflowItemMasterDao
		        .getWorkflowItemMasterListByWorkflowMaster(workflowMaster);
		return workflowItemMasterList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nexera.workflow.service.WorkflowService#getWorkflowExecById(int)
	 */
	@Override
	@Transactional
	public WorkflowItemExec getWorkflowExecById(int workflowexecId) {
		LOGGER.debug("Inside method getWorkflowExecById ");
		WorkflowItemExec workflowItemExec = (WorkflowItemExec) workflowItemExecDao
		        .load(WorkflowItemExec.class, workflowexecId);
		return workflowItemExec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#updateWorkflowExecStatus(
	 * com.nexera.workflow.bean.WorkflowExec)
	 */
	@Override
	@Transactional
	public void updateWorkflowExecStatus(WorkflowExec parentWorkflow) {
		LOGGER.debug("Inside method updateWorkflowExecStatus ");
		workflowExecDao.saveOrUpdate(parentWorkflow);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#updateWorkflowItemExecutionStatus
	 * (com.nexera.workflow.bean.WorkflowItemExec)
	 */
	@Override
	@Transactional
	public void updateWorkflowItemExecutionStatus(
	        WorkflowItemExec workflowItemExecution) {
		LOGGER.debug("Inside method updateWorkflowItemExecutionStatus ");
		workflowItemExecDao.saveOrUpdate(workflowItemExecution);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nexera.workflow.service.WorkflowService#
	 * getWorkflowItemListByParentWorkflowExecItem
	 * (com.nexera.workflow.bean.WorkflowItemExec)
	 */
	@Override
	@Transactional
	public List<WorkflowItemExec> getWorkflowItemListByParentWorkflowExecItem(
	        WorkflowItemExec workflowItemExecution) {
		LOGGER.debug("Inside method getWorkflowItemListByparentWorkflowExecItem ");
		return workflowItemExecDao
		        .getWorkflowItemListByparentWorkflowExecItem(workflowItemExecution);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#checkIfOnSuccessOfAnotherItem
	 * (com.nexera.workflow.bean.WorkflowItemMaster)
	 */
	@Override
	@Transactional
	public Boolean checkIfOnSuccessOfAnotherItem(
	        WorkflowItemMaster workflowItemMaster) {
		LOGGER.debug("Inside method checkIfOnSuccessOfAnotherItem ");
		return workflowItemMasterDao
		        .checkIfOnSuccessOfAnotherItem(workflowItemMaster);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#getWorkflowExecFromId(int)
	 */
	@Override
	@Transactional
	public WorkflowExec getWorkflowExecFromId(int workflowexecId) {
		LOGGER.debug("Inside method getWorkflowExecFromId ");
		return (WorkflowExec) workflowExecDao.load(WorkflowExec.class,
		        workflowexecId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.workflow.service.WorkflowService#saveParamsInExecTable(java
	 * .lang.Integer, java.lang.String[])
	 */
	@Override
	@Transactional
	public void saveParamsInExecTable(Integer milestoneID, String params) {
		WorkflowItemExec workflowItemExec = (WorkflowItemExec) workflowItemExecDao
		        .load(WorkflowItemExec.class, milestoneID);
		if (workflowItemExec != null) {
			workflowItemExec.setParams(params);
			workflowItemExecDao.saveOrUpdate(workflowItemExec);
		}

	}


}
