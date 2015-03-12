package com.nexera.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.EmailNotificationVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MilestoneNotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.web.rest.util.RestUtil;
import com.nexera.workflow.Constants.WorkflowConstants;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.enums.WorkflowItemStatus;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

@RestController
@RequestMapping(value = "/workflow/")
public class WorkflowRestService {
	private WorkflowService workflowService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private LoanService loanService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(WorkflowRestService.class);

	@RequestMapping(value = "create/{loanID}", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO createWorkflow(@PathVariable int loanID) {
		LOG.debug("Loan ID for this workflow is " + loanID);
		CommonResponseVO response = null;
		try {
			WorkflowVO workflowVO = new WorkflowVO();
			LOG.debug("Putting loan manager workflow into execution ");
			workflowVO
			        .setWorkflowType(WorkflowConstants.LOAN_MANAGER_WORKFLOW_TYPE);
			Gson gson = new Gson();
			int loanManagerWFID = engineTrigger.triggerWorkFlow(gson
			        .toJson(workflowVO));
			LOG.debug("Putting customer workflow into execution ");
			workflowVO
			        .setWorkflowType(WorkflowConstants.CUSTOMER_WORKFLOW_TYPE);
			int customerWFID = engineTrigger.triggerWorkFlow(gson
			        .toJson(workflowVO));
			loanService.saveWorkflowInfo(loanID, customerWFID, loanManagerWFID);
			String successMessage = "The Loan " + loanID
			        + " has been put into execution ";
			response = RestUtil.wrapObjectForSuccess(successMessage);
			LOG.debug("Response" + response);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "details/{loanID}", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO getWorkflowDetails(@PathVariable int loanID) {
		LOG.debug("Loan ID for this workflow is " + loanID);
		CommonResponseVO response = null;
		try {
			LoanVO loanVO = loanService.findWorkflowInfoById(loanID);
			LOG.debug("Putting loan manager workflow into execution ");
			
			response = RestUtil.wrapObjectForSuccess(loanVO);
			LOG.debug("Response" + response);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}


	// workflow/3/milestone/state/workflowIte
	@RequestMapping(value = "{loanId}/milestone/", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO getWorkflowItemStateInfo(@PathVariable int loanId,
	        @RequestParam(value = "workflowItemId") Integer workflowItemId) {
		LOG.info("workflowItemExecId----" + workflowItemId);
		CommonResponseVO response = null;
		try {
			LOG.info("loanId----" + loanId);
			/*
			 * String stateInfo = "";// Make a call to Workflow Engine which
			 * will call the renderStateInfo // to the work flow engine pass the
			 * loanId.. as Object[]..
			 */
			String[] params = new String[1];
			params[0] = String.valueOf(loanId);
			String stateInfo = engineTrigger.getRenderStateInfoOfItem(
			        workflowItemId, params);
			response = RestUtil.wrapObjectForSuccess(stateInfo);
			LOG.debug("Response" + response);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "/milestone/alert", method = RequestMethod.POST)
	public @ResponseBody
	CommonResponseVO getWorkflowItemStateInfo(
	        @RequestBody String milestoneNotificationStr) {
		LOG.info("milestoneNotificationStr----" + milestoneNotificationStr);
		CommonResponseVO response = null;
		try {
			Gson gson = new Gson();
			MilestoneNotificationVO milestoneNoticationVO = gson.fromJson(
			        milestoneNotificationStr, MilestoneNotificationVO.class);
			LOG.info("workflowItem ID" + milestoneNoticationVO.getMilestoneId());
			String stateInfo = "";// Make a call to Workflow Engine which will
			// call the renderStateInfo
			// to the work flow engine pass the loanId.. as Object[]..
			response = RestUtil.wrapObjectForSuccess(stateInfo);
			LOG.debug("Response" + response);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}
	@RequestMapping(value = "/milestone/sendMail", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO sendMail(

	@RequestBody String milestoneNotificationStr) {

		LOG.info("milestoneNotificationStr----" + milestoneNotificationStr);
		CommonResponseVO response = null;
		try {

			Gson gson = new Gson();
			EmailNotificationVo emailNotificationVo = gson.fromJson(
			        milestoneNotificationStr, EmailNotificationVo.class);
			LOG.info("workflowItem ID" + emailNotificationVo.getMilestoneId());
			
			String stateInfo = "";// Make a call to Workflow Engine which will
			                      // call the renderStateInfo
			// to the work flow engine pass the loanId.. as Object[]..
			Object params[]=new Object[3];
			params[0]=emailNotificationVo.getEmailId();
			params[1]=emailNotificationVo.getParams();
			engineTrigger.startWorkFlowItemExecution(emailNotificationVo.getMilestoneId(),params);
			
			response = RestUtil.wrapObjectForSuccess(stateInfo);
			LOG.debug("Response" + response);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "{workflowId}", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO getWorkflowItems(@PathVariable int workflowId) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {
			// List<WorkflowItemExec> list =
			// workflowService.getWorkflowItemListByParentWorkflowExecItem(workflowId);
			List<WorkflowItemExecVO> list = prepareTestListForLoanManager();
			response = RestUtil.wrapObjectForSuccess(list);
			LOG.debug("Response" + list.size());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}
	
	@RequestMapping(value = "customer/{workflowId}", method = RequestMethod.GET)
	public @ResponseBody
	CommonResponseVO prepareTestListForCustomer(@PathVariable int workflowId) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {
			// List<WorkflowItemExec> list =
			// workflowService.getWorkflowItemListByParentWorkflowExecItem(workflowId);
			List<WorkflowItemExecVO> list = prepareTestListForCustomer();
			response = RestUtil.wrapObjectForSuccess(list);
			LOG.debug("Response" + list.size());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}
private List<WorkflowItemExecVO> prepareTestListForLoanManager ()
{
	List<WorkflowItemExecVO> list = new ArrayList<WorkflowItemExecVO>();
	int numberOrder = 1;
	WorkflowItemExecVO workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setId(numberOrder++);
	workflowItemExecVO.setDisplayContent("Make Initial Contact");
	workflowItemExecVO.setStateInfo("Schedule an Alert");
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("System Education");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	// make some child workflow items
	WorkflowItemExecVO childWorkflowItemExecVO = new WorkflowItemExecVO();
	childWorkflowItemExecVO.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO.setSuccess(true);
	childWorkflowItemExecVO.setId(numberOrder++);
	childWorkflowItemExecVO.setDisplayContent("Rates");
	childWorkflowItemExecVO
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO);
	WorkflowItemExecVO childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Application");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Communication");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Needs List/ Documents");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Loan Progress");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Profile");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.IN_PROGRESS
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("1003 Complete");
	workflowItemExecVO
	        .setDisplayContent("Click here to apply application");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Credit Bureau");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Credit Score");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("AUS");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Loan Manager Decision");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("QC");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Needed Items");
	workflowItemExecVO.setStateInfo("4/10 Completed");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Add Team");
	workflowItemExecVO.setStateInfo("Click here to add a Team Member");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO
	        .setDisplayContent("Disclosures / Intent to Proceed");
	workflowItemExecVO.setStateInfo("Click to add Disclosures");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Application Fee");
	workflowItemExecVO
	        .setStateInfo("Click here to edit Application Fee");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Appraisal");
	workflowItemExecVO
	        .setStateInfo("Click here to edit start Appraisal");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Lock Your Rate");
	workflowItemExecVO.setStateInfo("Click here to lock your rate");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Underwriting status");
	workflowItemExecVO.setStateInfo("Pending");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Loan Closure Status");
	workflowItemExecVO.setStateInfo("Closing Status");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	return list;
}

private List<WorkflowItemExecVO> prepareTestListForCustomer ()
{
	List<WorkflowItemExecVO> list = new ArrayList<WorkflowItemExecVO>();
	int numberOrder = 1;
	WorkflowItemExecVO workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setId(numberOrder++);
	workflowItemExecVO.setDisplayContent("My Profile");
	workflowItemExecVO.setStateInfo("");
	list.add(workflowItemExecVO);
	
	// make some child workflow items
	WorkflowItemExecVO childWorkflowItemExecVO = new WorkflowItemExecVO();
	childWorkflowItemExecVO.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO.setSuccess(true);
	childWorkflowItemExecVO.setId(numberOrder++);
	childWorkflowItemExecVO.setDisplayContent("Account");
	childWorkflowItemExecVO
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO);
	WorkflowItemExecVO childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Online Application");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Photo");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	
	
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("SMS Texting Preferences");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.IN_PROGRESS
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Application Status ");
	workflowItemExecVO
	        .setStateInfo("40%");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Connect your online application");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	
	
	childWorkflowItemExecVO2 = new WorkflowItemExecVO();
	childWorkflowItemExecVO2.setStatus(WorkflowItemStatus.COMPLETED
	        .getStatusValue());
	childWorkflowItemExecVO2.setSuccess(true);
	childWorkflowItemExecVO2.setId(numberOrder++);
	childWorkflowItemExecVO2.setDisplayContent("Contact your Loan Manager");
	childWorkflowItemExecVO2
	        .setParentWorkflowItemExec(workflowItemExecVO);
	list.add(childWorkflowItemExecVO2);
	
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Credit Status");
	workflowItemExecVO.setStateInfo("EQ 786~ TU-289 ~ Ex - 121");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Team");
	workflowItemExecVO.setStateInfo("Add Team Members To Team");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Application Fee");
	workflowItemExecVO.setStateInfo("Click to pay");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Lock Rate");
	workflowItemExecVO.setStateInfo("Click to lock Rate");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Appraisal");
	workflowItemExecVO.setStateInfo("Status ..");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Underwriting");
	workflowItemExecVO.setStateInfo("Pending.");
	workflowItemExecVO.setId(numberOrder++);
	list.add(workflowItemExecVO);
	
	workflowItemExecVO = new WorkflowItemExecVO();
	workflowItemExecVO.setStatus(WorkflowItemStatus.NOT_STARTED
	        .getStatusValue());
	workflowItemExecVO.setSuccess(true);
	workflowItemExecVO.setDisplayContent("Closing Status");
	workflowItemExecVO.setStateInfo("Download Payment Coupon.");
	workflowItemExecVO.setId(numberOrder++);
	
	list.add(workflowItemExecVO);
	return list;
}
}