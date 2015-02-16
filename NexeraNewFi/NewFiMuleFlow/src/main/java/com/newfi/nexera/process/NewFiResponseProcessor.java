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
public class NewFiResponseProcessor implements Callable
{

    private static final Logger LOG = Logger.getLogger( NewFiResponseProcessor.class );


    /* (non-Javadoc)
     * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {
        LOG.debug( "Inside method onCall" );
        Gson gson = new Gson();
        MuleMessage message = eventContext.getMessage();
        String payload = message.getPayloadAsString();
        ResponseVO responseVO = new ResponseVO();
        responseVO.setResponseCode( "200" );
        responseVO.setStatus( "0" );
        responseVO.setResponseMessage( payload );
        String jsonString = gson.toJson( responseVO );
        message.setPayload( jsonString );
        return message;
    }

}
