package com.nexera.common.dao;

import java.util.List;
import org.springframework.stereotype.Component;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.ZipCodeLookup;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
public interface StateLookupDao extends GenericDao {
	
	public StateLookup findStateLookupByStateCode(String stateCode) throws NoRecordsFetchedException;

	List<ZipCodeLookup> findZipCodesForStateID(Integer stateID);

}
