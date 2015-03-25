package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;
import com.nexera.newfi.workflow.WorkflowDisplayConstants;
import com.nexera.workflow.task.IWorkflowTaskExecutor;
@Component
public class ProfileManager implements IWorkflowTaskExecutor {
	@Autowired
	private UserProfileService userProfileService;
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		//This Task doesnt do anything - 
		//Only will update the status of the work item to success.
		return  WorkflowDisplayConstants.WORKFLOW_TASK_SUCCESS;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		Integer completionStatus = 0;
		int userID = Integer.parseInt(inputMap
		        .get(WorkflowDisplayConstants.USER_ID_KEY_NAME).toString());		
		UserVO userVO =  userProfileService.findUser(userID);
		if (userVO.getCustomerDetail()!= null)
		{
		 completionStatus = userVO.getCustomerDetail().getProfileCompletionStatus();
		}		
		return completionStatus.toString();
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
