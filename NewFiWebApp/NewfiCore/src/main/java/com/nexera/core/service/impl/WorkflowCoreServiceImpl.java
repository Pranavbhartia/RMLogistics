package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.Milestones;
import com.nexera.common.vo.LoanMilestoneVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

@Component
public class WorkflowCoreServiceImpl implements WorkflowCoreService {

	@Autowired
	LoanService loanService;

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private WorkflowService workflowService;

	@Override
	public List<LoanMilestoneVO> getMilestones(int loanId) {

		
		List<LoanMilestoneVO> milestonesList = new ArrayList<LoanMilestoneVO>();
		Loan loan = new Loan(loanId);
		LoanMilestone mileStone = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.DOCS_OUT.getMilestoneKey());
		if (mileStone!= null)
		milestonesList.add(LoanMilestoneVO.convertFromEntityToVO(mileStone));
		LoanMilestone mileStone1 = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.LOAN_APPROVED.getMilestoneKey());
		if (mileStone1!= null)
			milestonesList.add(LoanMilestoneVO.convertFromEntityToVO(mileStone1));
		LoanMilestone mileStone3 = loanService.findLoanMileStoneByLoan(loan,
		        Milestones.PRE_QUAL.getMilestoneKey());
		if (mileStone3!= null)
			milestonesList.add(LoanMilestoneVO.convertFromEntityToVO(mileStone3));
		return milestonesList;
	}
	@Override
	@Transactional
	public void createWorkflow(WorkflowVO workflowVO) throws Exception {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		Map<String, Integer> map = engineTrigger.triggerWorkFlow();

		loanService.saveWorkflowInfo(workflowVO.getLoanID(),
		        map.get(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE),
		        map.get(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE));

	}

	@Override
	public void changeWorkItemState(WorkflowItemExecVO workItem) {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		engineTrigger.changeStateOfWorkflowItemExec(workItem.getId(),
		        workItem.getStatus());

	}

	@Override
	public List<WorkflowItemExecVO> getWorkflowItems(int workflowId) {
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		List<WorkflowItemExec> list = engineTrigger
		        .getWorkflowItemExecByWorkflowMasterExec(workflowId);
		List<WorkflowItemExecVO> volist = new ArrayList<WorkflowItemExecVO>();
		for (WorkflowItemExec workflowItem : list) {
			WorkflowItemExecVO workflowItemVO = new WorkflowItemExecVO();
			volist.add(workflowItemVO.convertToVO(workflowItem));
		}
		return volist;

	}

	public void completeWorkflowItem(Loan loan, Map<String, Object> map,
	        String workflowName) {
		int wfExecID = loan.getLoanManagerWorkflow();
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(wfExecID);
		WorkflowItemMaster workflowItemMaster = workflowService
		        .getWorkflowByType(workflowName);
		WorkflowItemExec workItemRef = workflowService
		        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
		// Create Map here.
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loan.getId());
		String params = Utils.convertMapToJson(map);
		workflowService.saveParamsInExecTable(workItemRef.getId(), params);
		EngineTrigger engineTrigger = applicationContext
		        .getBean(EngineTrigger.class);
		engineTrigger.startWorkFlowItemExecution(workItemRef.getId());

	}

}
