/**
 * 
 */
package com.newfi.nexera.rest;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.newfi.nexera.bean.RestParameters;
import com.newfi.nexera.constants.NewFiConstants;
import com.newfi.nexera.constants.WebServiceOperations;
import com.newfi.nexera.manager.NewFiManager;
import com.nexera.util.Utils;


/**
 * @author Utsav
 *
 */
public class DocsRestInterceptor implements Callable
{

    private static final Logger LOG = Logger.getLogger( DocsRestInterceptor.class );


    /* (non-Javadoc)
     * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {

        LOG.debug( "Inside method onCall " );
        MuleMessage message = eventContext.getMessage();
        String payload = message.getPayloadAsString();
        Gson gson = new Gson();
        RestParameters restParameters = gson.fromJson( payload, RestParameters.class );

        message.setOutboundProperty( NewFiConstants.CONSTANT_OP_NAME, restParameters.getOpName() );

        if ( NewFiManager.userTicket == null ) {
            LOG.debug( "Getting the user ticket based on the username and password " );
            NewFiManager.userTicket = Utils.getUserTicket( "Nexera_RareMile", "Portal0262" );
            NewFiManager.generationTime = System.currentTimeMillis();
        } else {
            long generationTime = NewFiManager.generationTime;
            long currentTime = System.currentTimeMillis();
            long differenceInMilliSeconds = currentTime - generationTime;
            long differenceInHours = differenceInMilliSeconds / ( 60 * 60 * 1000 );
            if ( differenceInHours >= 3 ) {
                LOG.debug( "Ticket would have expired as time difference has gone beyond 4 hours " );
                NewFiManager.userTicket = Utils.getUserTicket( "Nexera_RareMile", "Portal0262" );
            }
        }
        Object[] inputParameters = getAllParameters( restParameters );
        message.setPayload( inputParameters );
        return message;
    }


    public Object[] getAllParameters( RestParameters restParameters ) throws IOException
    {
        LOG.debug( "Inside method getAllParameters" );
        Object[] inputParams = null;
        if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID ) ) {
            LOG.debug( "Operation Chosen Was OP_NAME_DOWNLOAD_EDCOS_PDF_BY_ID " );

            inputParams = new String[2];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getDocId();

        }
        return inputParams;
    }
}
