package com.nexera.common.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nexera.common.entity.InternalUserStateMapping;

@Component
public interface InternalUserStateMappingDao extends GenericDao {

	
	public void saveOrUpdateUserStates(List<InternalUserStateMapping> internalUserStateMappings);
	
	public List<InternalUserStateMapping> retrieveStatesForUser(Integer userId);
	
	public void deleteObj(InternalUserStateMapping internalUserStateMapping);
}
