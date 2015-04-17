package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.InternalUserStateMappingDao;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.User;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.core.service.InternalUserStateMappingService;

@Component
public class InternalUserStateMappingServiceImpl implements
		InternalUserStateMappingService {

	@Autowired
	private InternalUserStateMappingDao mappingDao;

	@Override

	public void saveOrUpdateUserStates(
			List<InternalUserStateMappingVO> internalUserStateMappings) {
		mappingDao
				.saveOrUpdateUserStates(fillListInternalUserStateVOToObj(internalUserStateMappings));
	}

	@Override
	@Transactional(readOnly = true)
	public List<InternalUserStateMappingVO> retrieveStatesForUser(Integer userId) {
		return fillListInternalUserStateObjToVO(mappingDao
				.retrieveStatesForUser(userId));
	}

	List<InternalUserStateMapping> fillListInternalUserStateVOToObj(
			List<InternalUserStateMappingVO> internalUserStateMappings) {
		List<InternalUserStateMapping> stateMappings = new ArrayList<InternalUserStateMapping>();
		InternalUserStateMapping stateMapping = null;
		for (InternalUserStateMappingVO internalUserStateMapping : internalUserStateMappings) {
			stateMapping = new InternalUserStateMapping();
			if (internalUserStateMapping.getId() != null)
				stateMapping.setId(internalUserStateMapping.getId());
			stateMapping.setStateLookup(new StateLookup(
					internalUserStateMapping.getStateId()));
			stateMapping
					.setUser(new User(internalUserStateMapping.getUserId()));
			if(!internalUserStateMapping.getIsChecked()){
					if( internalUserStateMapping.getId()!=null)
						mappingDao.deleteObj(stateMapping);
			}else{
				if(internalUserStateMapping.getIsChecked())
					stateMappings.add(stateMapping);
			}
		}
		return stateMappings;
	}

	List<InternalUserStateMappingVO> fillListInternalUserStateObjToVO(
			List<InternalUserStateMapping> internalUserStateMappings) {
		List<InternalUserStateMappingVO> stateMappings = new ArrayList<InternalUserStateMappingVO>();
		InternalUserStateMappingVO stateMapping = null;
		for (InternalUserStateMapping internalUserStateMapping : internalUserStateMappings) {
			stateMapping = new InternalUserStateMappingVO();
			stateMapping.setId(internalUserStateMapping.getId());
			stateMapping.setStateId(internalUserStateMapping.getStateLookup()
					.getId());
			stateMapping.setUserId(internalUserStateMapping.getUser().getId());
			stateMapping.setIsChecked(true);
			stateMappings.add(stateMapping);
		}
		return stateMappings;
	}
}
