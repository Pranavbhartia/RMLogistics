package com.nexera.common.dao;

import org.springframework.stereotype.Component;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
public interface StateLookupDao extends GenericDao {
	
	public StateLookup findStateLookupByStateCode(String stateCode) throws NoRecordsFetchedException;

}
