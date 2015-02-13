/**
 * 
 */
package com.newfi.nexera.rest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.newfi.nexera.bean.RestParameters;
import com.newfi.nexera.constants.NewFiConstants;
import com.newfi.nexera.constants.WebServiceOperations;
import com.newfi.nexera.vo.AuthenticateVO;


/**
 * @author Utsav
 *
 */
public class RestInterceptor implements Callable
{

    private static final Logger LOG = Logger.getLogger( RestInterceptor.class );


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
        return message;
    }


    public String[] getAllParameters( String opName, RestParameters restParameters )
    {
        LOG.debug( "Inside method getAllParameters" );
        String[] inputParams = null;

        if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_CREATE ) ) {
            LOG.debug( "Operation Chosen Was Create " );
            inputParams = new String[2];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_CREATE_LEAD ) ) {
            LOG.debug( "Operation Chosen Was CreateLead " );
            inputParams = new String[2];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_LOAD ) ) {
            LOG.debug( "Operation Chosen Was Load " );
            inputParams = new String[4];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_LOCK_LOAN_PROGRAM ) ) {
            LOG.debug( "Operation Chosen Was LockLoanProgram " );
            inputParams = new String[5];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_RUN_QUICK_PRICER ) ) {
            LOG.debug( "Operation Chosen Was RunQuickPricer " );
            inputParams = new String[2];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_SAVE ) ) {
            LOG.debug( "Operation Chosen Was Save " );
            inputParams = new String[4];
        } else if ( opName.equals( WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT ) ) {
            LOG.debug( "Operation Chosen Was UploadPDFDocument " );
            //TODO No Information Yet
            inputParams = new String[5];
        }
        return inputParams;
    }
}
