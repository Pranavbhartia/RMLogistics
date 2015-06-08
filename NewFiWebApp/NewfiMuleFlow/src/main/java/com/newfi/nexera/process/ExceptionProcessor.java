/**
 * 
 */
package com.newfi.nexera.process;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.newfi.nexera.vo.ResponseVO;


/**
 * @author Utsav
 *
 */
public class ExceptionProcessor implements Callable
{

    private static final Logger LOG = Logger.getLogger( NewFiResponseProcessor.class );


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {
        Gson gson = new Gson();
        LOG.info( "Inside method onCall " );
        MuleMessage message = eventContext.getMessage();
        ResponseVO responseVO = new ResponseVO();
        responseVO.setErrorCode( "500" );
        responseVO.setErrorDescription( message.getExceptionPayload().getMessage() );
        responseVO.setStatus( "1" );
        LOG.error( "Exception Response Received " + message.getExceptionPayload().getMessage() );
        String jsonString = gson.toJson( responseVO );
        return jsonString;
    }

}
