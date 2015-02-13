/**
 * 
 */
package com.newfi.nexera.process;

import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.newfi.nexera.manager.NewFiManager;


/**
 * @author Utsav
 *
 */
public class NewFiTicketProcessor implements Callable
{

    /* (non-Javadoc)
     * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {
        MuleMessage message = eventContext.getMessage();
        String recievedTicket = message.getPayloadAsString();
        NewFiManager.userTicket = recievedTicket;
        return message;
    }

}
