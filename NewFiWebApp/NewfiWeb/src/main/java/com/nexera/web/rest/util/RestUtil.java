package com.nexera.web.rest.util;

import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;

public class RestUtil {

	public static CommonResponseVO wrapObjectForSuccess(Object object) {
		CommonResponseVO responseVO = new CommonResponseVO();
		responseVO.setResultObject(object);
		return responseVO;
	}

	public static CommonResponseVO wrapObjectForFailure(Object object,
			String errorCode, String errorMessage) {
		CommonResponseVO responseVO = new CommonResponseVO();
		responseVO.setResultObject(object);

		ErrorVO errorVO = new ErrorVO();
		errorVO.setCode(errorCode);
		errorVO.setMessage(errorMessage);
		
		responseVO.setError(errorVO);

		return responseVO;
	}

}
