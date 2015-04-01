package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.MileStoneTurnAroundTime;
import com.nexera.common.entity.WorkflowItemMaster;

public interface MileStoneTurnAroundTimeDao extends GenericDao {

	public List<WorkflowItemMaster> loadAllData();

	public void saveOrUpdateMileStoneTurnAroundTime(
			List<MileStoneTurnAroundTime> stoneTurnAroundTimes);

	public MileStoneTurnAroundTime loadByWorkItem(Integer workflowItemMasterId);
}
