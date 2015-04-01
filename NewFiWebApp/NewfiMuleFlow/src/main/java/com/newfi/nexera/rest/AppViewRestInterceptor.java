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
import com.newfi.nexera.vo.AppViewVO;


/**
 * @author Utsav
 *
 */
public class AppViewRestInterceptor implements Callable
{

    private static final Logger LOG = Logger.getLogger( AppViewRestInterceptor.class );


    /* (non-Javadoc)
     * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {
        MuleMessage message = eventContext.getMessage();
        String payload = message.getPayloadAsString();
        Gson gson = new Gson();
        AppViewVO appViewVO = new AppViewVO();
        appViewVO = gson.fromJson( payload, AppViewVO.class );
        message.setOutboundProperty( NewFiConstants.CONSTANT_OP_NAME, appViewVO.getOpName() );
        String[] inputParameters = getArrayForOperation( appViewVO );
        message.setPayload( inputParameters );
        return message;
    }


    private String[] getArrayForOperation( AppViewVO appViewVO )
    {
        LOG.debug( "Inside method getArrayForOperation" );
        String[] inputParams = null;
        if ( appViewVO.getOpName().equals( WebServiceOperations.OP_NAME_APP_VIEW_GET_PML_LOAN_URL ) ) {
            LOG.debug( "Operation Chosen Was GetPmlLoanUrl " );
            inputParams = new String[2];
            inputParams[0] = appViewVO.getsTicket();
            inputParams[1] = appViewVO.getsLoanName();
        } else if ( appViewVO.getOpName().equals( WebServiceOperations.OP_NAME_APP_VIEW_GET_PML_PIPELINE_URL ) ) {
            LOG.debug( "Operation Chosen Was GetPmlPipelineUrl " );
            inputParams = new String[1];
            inputParams[0] = appViewVO.getsTicket();
        } else if ( appViewVO.getOpName().equals( WebServiceOperations.OP_NAME_APP_VIEW_GET_VIEW_DESKTOP_URL ) ) {
            LOG.debug( "Operation Chosen Was GetViewDesktopUrl " );
            inputParams = new String[1];
            inputParams[0] = appViewVO.getsTicket();
        } else if ( appViewVO.getOpName().equals( WebServiceOperations.OP_NAME_APP_VIEW_GET_VIEW_LOAN_URL ) ) {
            LOG.debug( "Operation Chosen Was GetViewLoanUrl " );
            inputParams = new String[2];
            inputParams[0] = appViewVO.getsTicket();
            inputParams[1] = appViewVO.getsLoanName();
        }
        return inputParams;
    }

}
