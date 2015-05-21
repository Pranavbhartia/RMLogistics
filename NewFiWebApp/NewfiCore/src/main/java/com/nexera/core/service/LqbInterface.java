package com.nexera.core.service;

import com.nexera.common.vo.LoanAppFormVO;


public interface LqbInterface {

	public String invokeRest(String appFormData);
	
	public String findSticket(LoanAppFormVO loaAppFormVO) throws Exception;

}
