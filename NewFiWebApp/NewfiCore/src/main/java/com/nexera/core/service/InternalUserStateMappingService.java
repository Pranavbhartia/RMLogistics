package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.InternalUserStateMappingVO;

public interface InternalUserStateMappingService {

	public void saveOrUpdateUserStates(
			List<InternalUserStateMappingVO> internalUserStateMappings);

	public List<InternalUserStateMappingVO> retrieveStatesForUser(Integer userId);

}
