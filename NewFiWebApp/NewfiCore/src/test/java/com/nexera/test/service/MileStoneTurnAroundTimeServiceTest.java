package com.nexera.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.MileStoneTurnAroundTimeVO;
import com.nexera.core.service.MileStoneTurnAroundTimeService;
import com.nexera.workflow.Constants.WorkflowConstants;


@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class MileStoneTurnAroundTimeServiceTest {

	@Autowired
	MileStoneTurnAroundTimeService aroundTimeService;
	
	
	@Test
	public void testloadAllMileStoneTurnAround(){
		
			 List<MileStoneTurnAroundTimeVO> aroundTimeVOs=aroundTimeService.loadAllMileStoneTurnAround();
			 for (MileStoneTurnAroundTimeVO mileStoneTurnAroundTimeVO : aroundTimeVOs) {
				//System.out.println("id:"+mileStoneTurnAroundTimeVO.getId()+" name:"+mileStoneTurnAroundTimeVO.getName());
			}
		
	}
	
	@Test
	public void testsaveOrUpdateMileStoneTurnArounds(){
		List<MileStoneTurnAroundTimeVO> aroundTimeVOs=new ArrayList<MileStoneTurnAroundTimeVO>();
		MileStoneTurnAroundTimeVO aroundTimeVO=new MileStoneTurnAroundTimeVO();
		aroundTimeVO.setHours(12);
		aroundTimeVO.setName(WorkflowConstants.WORKFLOW_ITEM_INITIAL_CONTACT);
		aroundTimeVO.setWorkflowItemMasterId(1);
		MileStoneTurnAroundTimeVO aroundTimeVO1=new MileStoneTurnAroundTimeVO();
		aroundTimeVO1.setHours(24);
		aroundTimeVO1.setName(WorkflowConstants.WORKFLOW_ITEM_SYSTEM_EDU);
		aroundTimeVO1.setWorkflowItemMasterId(2);
		aroundTimeVOs.add(aroundTimeVO1);
		aroundTimeVOs.add(aroundTimeVO);
		aroundTimeService.saveOrUpdateMileStoneTurnArounds(aroundTimeVOs);
	}
}
