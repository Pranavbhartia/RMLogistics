/**
 * 
 */
package com.newfi.nexera.rest;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.newfi.nexera.constants.NewFiConstants;
import com.newfi.nexera.constants.WebServiceOperations;
import com.newfi.nexera.vo.AuthenticateVO;

/**
 * @author Utsav
 *
 */
public class AuthenticationRestInterceptor implements Callable {

	private static final Logger LOG = Logger
	        .getLogger(AuthenticationRestInterceptor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		MuleMessage message = eventContext.getMessage();
		String payload = message.getPayloadAsString();
		Gson gson = new Gson();
		AuthenticateVO authenticateVO = new AuthenticateVO();
		authenticateVO = gson.fromJson(payload, AuthenticateVO.class);
		message.setOutboundProperty(NewFiConstants.CONSTANT_OP_NAME,
		        authenticateVO.getOpName());
		LOG.info("Authentication object passed " + authenticateVO);
		String[] inputParameters = getArrayForOperation(authenticateVO);
		message.setPayload(inputParameters);
		return message;
	}

	private String[] getArrayForOperation(AuthenticateVO authenticateVO) {
		LOG.debug("Inside method getAllParameters");
		String[] inputParams = null;
		if (authenticateVO.getOpName().equals(
		        WebServiceOperations.OP_NAME_AUTH_GET_USER_AUTH_TICET)) {
			LOG.debug("Operation Chosen Was GetUserAuthTicket ");
			inputParams = new String[2];
			inputParams[0] = authenticateVO.getUserName();
			inputParams[1] = authenticateVO.getPassWord();
		} else if (authenticateVO.getOpName().equals(
		        WebServiceOperations.OP_NAME_AUTH_GET_PML_USER_AUTH_TICET)) {
			LOG.debug("Operation Chosen Was GetUserAuthTicket ");
			inputParams = new String[3];
			inputParams[0] = authenticateVO.getUserName();
			inputParams[1] = authenticateVO.getPassWord();
			inputParams[2] = authenticateVO.getCustomerCode();
		}
		return inputParams;
	}

}
