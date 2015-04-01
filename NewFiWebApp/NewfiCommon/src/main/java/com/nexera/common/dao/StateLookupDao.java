package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.ZipCodeLookup;

public interface StateLookupDao extends GenericDao{

	List<ZipCodeLookup> findZipCodesForStateID(Integer stateID);

}
