package com.nexera.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.EmailNotificationVo;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.MilestoneLoanTeamVO;
import com.nexera.common.vo.MilestoneNotificationVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.web.rest.util.RestUtil;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowItemExecVO;
import com.nexera.workflow.vo.WorkflowVO;

@RestController
@RequestMapping(value = "/workflow/")
public class WorkflowRestService {
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private EngineTrigger engineTrigger;
	@Autowired
	private LoanService loanService;
	@Autowired
	private NeedsListService needsListService;
	private static final Logger LOG = LoggerFactory
	        .getLogger(WorkflowRestService.class);

	@RequestMapping(value = "create/{loanID}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO createWorkflow(
	        @PathVariable int loanID) {
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
	public @ResponseBody CommonResponseVO getWorkflowDetails(
	        @PathVariable int loanID) {
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

	
	@RequestMapping(value = "/milestone/alert", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO getWorkflowItemStateInfo(
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

	@RequestMapping(value = "/milestone/addUserToLoanTeam", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO addUserToLoanTeam(
	        @RequestBody String milestoneAddTeamVOStr) {
		LOG.info("milestoneAddTeamVO----" + milestoneAddTeamVOStr);
		CommonResponseVO response = null;
		try {
			Gson gson = new Gson();
			MilestoneLoanTeamVO milestoneNoticationVO = gson.fromJson(
			        milestoneAddTeamVOStr, MilestoneLoanTeamVO.class);
			LOG.info("workflowItem ID" + milestoneNoticationVO.getMilestoneID());
			String stateInfo = "";// Make a call to Workflow Engine which will
			// call the renderStateInfo
			// to the work flow engine pass the loanId.. as Object[]..
			String params[] = new String[2];
			params[0] = String.valueOf(milestoneNoticationVO.getLoanID());
			params[1] = String.valueOf(milestoneNoticationVO.getUserID());
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("LoanId", milestoneNoticationVO.getLoanID());
			paramsMap.put("UserId", milestoneNoticationVO.getUserID());
			String jsonParam = Utils.convertMapToJson(paramsMap);
			workflowService.saveParamsInExecTable(
			        milestoneNoticationVO.getMilestoneID(), jsonParam);
			stateInfo = engineTrigger
			        .getRenderStateInfoOfItem(milestoneNoticationVO
			                .getMilestoneID());
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
			Object params[] = new Object[3];
			params[0] = "Sample Template";
			params[1] = emailNotificationVo.getEmailId();
			engineTrigger.startWorkFlowItemExecution(emailNotificationVo
			        .getMilestoneId());

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
	public @ResponseBody CommonResponseVO getWorkflowItems(
	        @PathVariable int workflowId) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {

			List<WorkflowItemExec> list = engineTrigger
			        .getWorkflowItemExecByWorkflowMasterExec(workflowId);
			List<WorkflowItemExecVO> volist = new ArrayList<WorkflowItemExecVO>();

			for (WorkflowItemExec workflowItem : list)

			{
				WorkflowItemExecVO workflowItemVO = new WorkflowItemExecVO();
				volist.add(workflowItemVO.convertToVO(workflowItem));
			}
			// List<WorkflowItemExecVO> list = prepareTestListForLoanManager();
			response = RestUtil.wrapObjectForSuccess(volist);
			LOG.debug("Response" + list.size());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "execute/{workflowItemId}", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO executeWorkflowItem(
	        @PathVariable int workflowItemId,
	        @RequestBody(required = false) String params) {
		LOG.info("workflowItemId----" + workflowItemId);
		CommonResponseVO response = null;
		try {
			// List<WorkflowItemExec> list =
			// workflowService.getWorkflowItemListByParentWorkflowExecItem(workflowId);

			workflowService.saveParamsInExecTable(workflowItemId, params);

			String result = engineTrigger
			        .startWorkFlowItemExecution(workflowItemId);

			response = RestUtil.wrapObjectForSuccess(result);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "renderstate/{workflowId}", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO getRenderStateInfoOfItem(
	        @PathVariable int workflowId,
	        @RequestBody(required = false) String params) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {
			
			workflowService.saveParamsInExecTable(workflowId, params);
			String result = engineTrigger.getRenderStateInfoOfItem(workflowId);
			response = RestUtil.wrapObjectForSuccess(result);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}

	@RequestMapping(value = "changestateofworkflowitemexec/{workflowId}", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO changeStateOfWorkflowItemExec(
	        @PathVariable int workflowId,
	        @RequestBody(required = false) String params) {
		LOG.info("workflowId----" + workflowId);
		CommonResponseVO response = null;
		try {
			String status = "3";
			String stat = params;
			engineTrigger.changeStateOfWorkflowItemExec(workflowId, status);
			response = RestUtil.wrapObjectForSuccess("Success");
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response = RestUtil.wrapObjectForFailure(null, "500",
			        e.getMessage());
		}
		return response;
	}
	
	@RequestMapping ( value = "getupdatedstatus", method = RequestMethod.GET)
    public @ResponseBody CommonResponseVO getUpdatedStatus(@RequestParam(value="items")String items,
    		@RequestParam(value="data",required=false)String data)
    {
        LOG.info( "getUpdatedStatus----" );
        CommonResponseVO response = null;
        ObjectMapper mapper=new ObjectMapper();
        TypeReference<List<Integer>> typeRef=new TypeReference<List<Integer>>() {};
		List<Integer> milestoneItems = null;
		try {
			milestoneItems = mapper.readValue(items, typeRef);
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        try {
        	HashMap<String, Object> map=new HashMap<String,Object>();
        	TypeReference<HashMap<String, Object>> typeRefList=new TypeReference<HashMap<String,Object>>(){};
        	map=mapper.readValue(data, typeRefList);
        	HashMap<Integer, String> resultStatus=new HashMap<Integer,String>();
        	for(Integer workflowItemExecId: milestoneItems){
        		System.out.println(workflowItemExecId+"----------------------");
        		map.put("workflowItemExecId", workflowItemExecId);
        		StringWriter sw=new StringWriter();
		    	mapper.writeValue(sw, map);
        		workflowService.saveParamsInExecTable(workflowItemExecId, sw.toString());
        		String result=engineTrigger.checkStatus(workflowItemExecId);
        		if(result!=null)
        			resultStatus.put(workflowItemExecId, result);
        	}
        	
            response = RestUtil.wrapObjectForSuccess( resultStatus );
        } catch ( Exception e ) {
            LOG.error( e.getMessage() );
            response = RestUtil.wrapObjectForFailure( null, "500", e.getMessage() );
        }
        return response;
    }
	

}