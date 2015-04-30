package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.StateLookupVO;
import com.nexera.common.vo.ZipCodeLookupVO;

public interface StateLookupService {

	public List<ZipCodeLookupVO> findZipCodesForStateID(Integer stateID);

	List<StateLookupVO> getStatesList();

	public String getStateCodeByZip(String addressZipCode);

}
