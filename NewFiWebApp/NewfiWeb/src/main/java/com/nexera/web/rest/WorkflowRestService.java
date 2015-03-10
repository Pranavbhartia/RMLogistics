package com.nexera.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexera.common.vo.CommonResponseVO;
import com.nexera.web.rest.util.RestUtil;
import com.nexera.workflow.enums.WorkflowItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowItemExecVO;

@RestController
@RequestMapping(value = "/workflow/{workflowId}")
public class WorkflowRestService {

	private WorkflowService workflowService;
	private static final Logger LOG = LoggerFactory
			.getLogger(WorkflowRestService.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getWorkflowItems(
			@PathVariable int workflowId) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {
			// List<WorkflowItemExec> list =
			// workflowService.getWorkflowItemListByParentWorkflowExecItem(workflowId);
			List<WorkflowItemExecVO> list = new ArrayList<WorkflowItemExecVO>();
			int numberOrder = 1;
			WorkflowItemExecVO workflowItemExecVO = new WorkflowItemExecVO();
			workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED.getStatusValue());
			workflowItemExecVO.setSuccess(true);
			workflowItemExecVO.setId(numberOrder++);
			workflowItemExecVO.setDisplayContent("Make Initial Contact");

			list.add(workflowItemExecVO);

			workflowItemExecVO = new WorkflowItemExecVO();
			workflowItemExecVO.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			workflowItemExecVO.setSuccess(true);
			workflowItemExecVO.setDisplayContent("System Education");
			workflowItemExecVO.setId(numberOrder++);

			// make some child workflow items

			WorkflowItemExecVO childWorkflowItemExecVO = new WorkflowItemExecVO();
			childWorkflowItemExecVO.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO.setSuccess(true);
			childWorkflowItemExecVO.setId(numberOrder++);
			childWorkflowItemExecVO.setDisplayContent("Rates");
			childWorkflowItemExecVO.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO);
			
			
			WorkflowItemExecVO childWorkflowItemExecVO2 = new WorkflowItemExecVO();
			childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO2.setSuccess(true);
			childWorkflowItemExecVO2.setId(numberOrder++);
			childWorkflowItemExecVO2.setDisplayContent("Application");
			childWorkflowItemExecVO2.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO2);
			
			
			childWorkflowItemExecVO2 = new WorkflowItemExecVO();
			childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO2.setSuccess(true);
			childWorkflowItemExecVO2.setId(numberOrder++);
			childWorkflowItemExecVO2.setDisplayContent("Communication");
			childWorkflowItemExecVO2.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO2);
			
			
			childWorkflowItemExecVO2 = new WorkflowItemExecVO();
			childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO2.setSuccess(true);
			childWorkflowItemExecVO2.setId(numberOrder++);
			childWorkflowItemExecVO2.setDisplayContent("Needs List/ Documents");
			childWorkflowItemExecVO2.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO2);
			
			childWorkflowItemExecVO2 = new WorkflowItemExecVO();
			childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO2.setSuccess(true);
			childWorkflowItemExecVO2.setId(numberOrder++);
			childWorkflowItemExecVO2.setDisplayContent("Loan Progress");
			childWorkflowItemExecVO2.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO2);
			
			
			childWorkflowItemExecVO2 = new WorkflowItemExecVO();
			childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED.getStatusValue());
			childWorkflowItemExecVO2.setSuccess(true);
			childWorkflowItemExecVO2.setId(numberOrder++);
			childWorkflowItemExecVO2.setDisplayContent("Profile");
			childWorkflowItemExecVO2.setParentWorkflowItemExec(workflowItemExecVO);
			list.add(childWorkflowItemExecVO2);
			
			list.add(workflowItemExecVO);

			workflowItemExecVO = new WorkflowItemExecVO();
			workflowItemExecVO.setStatus(WorkflowItemStatus.IN_PROGRESS.getStatusValue());
			workflowItemExecVO.setSuccess(true);
			workflowItemExecVO.setId(numberOrder++);
			list.add(workflowItemExecVO);

			workflowItemExecVO = new WorkflowItemExecVO();
			workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED.getStatusValue());
			workflowItemExecVO.setSuccess(true);
			workflowItemExecVO.setId(numberOrder++);
			list.add(workflowItemExecVO);

			workflowItemExecVO = new WorkflowItemExecVO();
			workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED.getStatusValue());
			workflowItemExecVO.setSuccess(true);
			workflowItemExecVO.setId(numberOrder++);
			list.add(workflowItemExecVO);
			
			response = RestUtil.wrapObjectForSuccess(list);
			LOG.debug("Response" + list.size());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
					e.getMessage());
		}
		return response;
	}
}
