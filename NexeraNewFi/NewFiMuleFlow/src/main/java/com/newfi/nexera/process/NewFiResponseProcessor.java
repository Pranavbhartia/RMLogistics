/**
 * 
 */
package com.newfi.nexera.process;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;


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
        MuleMessage message = eventContext.getMessage();
        String payload = message.getPayloadAsString();
        return message;
    }

}
