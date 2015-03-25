package com.nexera.newfi.workflow.customer.tasks;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.task.IWorkflowTaskExecutor;

@Component
public class ProfilePhotoManager implements IWorkflowTaskExecutor {
	
	@Autowired
	private EngineTrigger engineTrigger;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Override
	public String execute(HashMap<String, Object> objectMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String renderStateInfo(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkStatus(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		int userId=Integer.parseInt(inputMap.get("userId").toString());
		UserVO userVo=userProfileService.findUser(userId);
		if(userVo.getPhotoImageUrl()!=null&&!userVo.getPhotoImageUrl().equals("")){
			int workflowItemExecId=Integer.parseInt(inputMap.get("workflowItemExecId").toString());
			engineTrigger.changeStateOfWorkflowItemExec(workflowItemExecId, "3");
			return "3";
		}
		return null;
	}

	@Override
	public String invokeAction(HashMap<String, Object> inputMap) {
		// TODO Auto-generated method stub
		return null;
	}

}
