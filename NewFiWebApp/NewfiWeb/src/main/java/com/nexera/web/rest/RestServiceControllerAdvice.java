package com.nexera.web.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexera.common.exception.BaseRestException;
import com.nexera.common.exception.ErrorCode;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.web.rest.util.RestUtil;

@ControllerAdvice
public class RestServiceControllerAdvice {

	/**
	 * The rest services can throw BaseRestException type of exception to return
	 * a neat error response
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BaseRestException.class)
	@ResponseBody
	public CommonResponseVO handleBaseRestException(HttpServletRequest req,
			BaseRestException e) {

		String errorCode = "500";
		String errorMessage = "Internal error Occurred";

		if (e.getErrorCode() != null) {
			ErrorCode err = e.getErrorCode();
			if (err.getErrorCode() > 0)
				errorCode = String.valueOf(err.getErrorCode());
			if (err.getMessage() != null)
				errorMessage = err.getMessage();
		}

		return RestUtil.wrapObjectForFailure(null, errorCode, errorMessage);

	}

	/**
	 * This method will handle generic runtime exceptions which were missed to
	 * being caught and handled.
	 * 
	 * @param req
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public CommonResponseVO handleGeneralException(HttpServletRequest req,
			Exception e) {

		String errorCode = "500";
		String errorMessage = "Internal error Occurred";

		return RestUtil.wrapObjectForFailure(null, errorCode, errorMessage);

	}
}