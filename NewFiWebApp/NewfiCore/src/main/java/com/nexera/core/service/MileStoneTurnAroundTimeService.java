package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.MileStoneTurnAroundTimeVO;

public interface MileStoneTurnAroundTimeService {
	public List<MileStoneTurnAroundTimeVO> loadAllMileStoneTurnAround();

	public void saveOrUpdateMileStoneTurnArounds(
			List<MileStoneTurnAroundTimeVO> aroundTimeVOs);
}
