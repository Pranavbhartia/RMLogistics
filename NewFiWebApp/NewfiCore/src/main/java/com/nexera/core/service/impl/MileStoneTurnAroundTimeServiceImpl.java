package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.MileStoneTurnAroundTimeDao;
import com.nexera.common.entity.MileStoneTurnAroundTime;
import com.nexera.common.entity.User;
import com.nexera.common.entity.WorkflowItemMaster;
import com.nexera.common.vo.MileStoneTurnAroundTimeVO;
import com.nexera.core.service.MileStoneTurnAroundTimeService;

@Component
public class MileStoneTurnAroundTimeServiceImpl implements
		MileStoneTurnAroundTimeService {

	@Autowired
	private MileStoneTurnAroundTimeDao aroundTimeDao;

	@Override
	@Transactional
	public List<MileStoneTurnAroundTimeVO> loadAllMileStoneTurnAround() {

		return buildMileStoneTurnAroundTimeListVO(aroundTimeDao.loadAllData());

	}

	@Override
	@Transactional
	public void saveOrUpdateMileStoneTurnArounds(
			List<MileStoneTurnAroundTimeVO> aroundTimeVOs) {
		aroundTimeDao
				.saveOrUpdateMileStoneTurnAroundTime(buildMileStoneTurnAroundTimeList(aroundTimeVOs));
	}

	public List<MileStoneTurnAroundTimeVO> buildMileStoneTurnAroundTimeListVO(
			List<WorkflowItemMaster> aroundTimes) {
		List<MileStoneTurnAroundTimeVO> aroundTimeVOs = new ArrayList<MileStoneTurnAroundTimeVO>();
		MileStoneTurnAroundTimeVO aroundTimeVO = null;
		for (WorkflowItemMaster aroundTime : aroundTimes) {
			aroundTimeVO = new MileStoneTurnAroundTimeVO();
			MileStoneTurnAroundTime mileStoneTurnAroundTime = aroundTimeDao
					.loadByWorkItem(aroundTime.getId());
			if (mileStoneTurnAroundTime != null) {
				aroundTimeVO.setHours(mileStoneTurnAroundTime.getHours());
				aroundTimeVO.setId(mileStoneTurnAroundTime.getId());

				aroundTimeVO.setCreatedby(mileStoneTurnAroundTime
						.getCreatedBy().getId());
				aroundTimeVO.setModifiedby(mileStoneTurnAroundTime
						.getModifiedBy().getId());
			}
			aroundTimeVO.setName(aroundTime.getDescription());
			aroundTimeVO.setWorkflowItemMasterId(aroundTime.getId());
			aroundTimeVO.setOrderBy(aroundTime.getDisplayTurnAroundOrder());
			aroundTimeVOs.add(aroundTimeVO);
		}
		return aroundTimeVOs;
	}

	public List<MileStoneTurnAroundTime> buildMileStoneTurnAroundTimeList(
			List<MileStoneTurnAroundTimeVO> aroundTimes) {
		List<MileStoneTurnAroundTime> aroundTimeVOs = new ArrayList<MileStoneTurnAroundTime>();
		MileStoneTurnAroundTime aroundTimeVO = null;
		for (MileStoneTurnAroundTimeVO aroundTime : aroundTimes) {

			aroundTimeVO = new MileStoneTurnAroundTime();
			aroundTimeVO.setHours(aroundTime.getHours());
			if (aroundTime.getId() != null)
				aroundTimeVO.setId(aroundTime.getId());
			aroundTimeVO.setWorkflowItemMaster(new WorkflowItemMaster(
					aroundTime.getWorkflowItemMasterId()));
			aroundTimeVO.setCreatedBy(new User(aroundTime.getCreatedby()));
			aroundTimeVO.setModifiedBy(new User(aroundTime.getModifiedby()));
			aroundTimeVO.setCreatedDate(new Date());
			aroundTimeVO.setModifiedDate(new Date());
			aroundTimeVOs.add(aroundTimeVO);
		}
		return aroundTimeVOs;
	}
}
