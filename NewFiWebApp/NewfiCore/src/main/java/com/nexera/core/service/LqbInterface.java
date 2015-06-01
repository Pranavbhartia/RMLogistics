package com.nexera.core.service;

import java.util.HashMap;

import com.nexera.common.vo.LoanAppFormVO;

public interface LqbInterface {

	public HashMap<String, String> invokeRest(String appFormData);

	public void invalidateTeaserRateCache(String appFormData);

	public String findSticket(LoanAppFormVO loaAppFormVO) throws Exception;

}
