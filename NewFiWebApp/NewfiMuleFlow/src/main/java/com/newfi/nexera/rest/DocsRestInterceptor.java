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

    private Utils utils;


    public DocsRestInterceptor()
    {}


    /*
     * (non-Javadoc)
     * 
     * @see
     * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
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
        if ( restParameters.getLoanVO().getsTicket() == null || restParameters.getLoanVO().getsTicket().equalsIgnoreCase( "" ) ) {
            utils.getUserTicket();
        }
        Object[] inputParameters = getAllParameters( restParameters );
        if ( inputParameters != null ) {
            LOG.info( "PARAMETERS PASSED TO LQB FROM MULE " );
            int count = 0;
            for ( Object param : inputParameters ) {
                LOG.info( ++count + "  " + param );
            }
        }
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


    public Utils getUtils()
    {
        return utils;
    }


    public void setUtils( Utils utils )
    {
        this.utils = utils;
    }
}
