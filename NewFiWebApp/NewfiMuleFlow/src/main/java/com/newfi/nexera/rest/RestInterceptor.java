/**
 * 
 */
package com.newfi.nexera.rest;

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
        if ( NewFiManager.userTicket == null ) {
            LOG.debug( "Getting the user ticket based on the username and password " );
            NewFiManager.userTicket = Utils.getUserTicket( "Nexera_RareMile", "Portal0262" );
            NewFiManager.generationTime = System.currentTimeMillis();
        } else {
            long generationTime = NewFiManager.generationTime;
            long currentTime = System.currentTimeMillis();
            long differenceInMilliSeconds = currentTime - generationTime;
            long differenceInHours = differenceInMilliSeconds / ( 60 * 60 * 1000 );
            if ( differenceInHours >= 4 ) {
                LOG.debug( "Ticket would have expired as time difference has gone beyond 4 hours " );
                NewFiManager.userTicket = Utils.getUserTicket( "Nexera_RareMile", "Portal0262" );
            }
        }
        String[] inputParameters = getAllParameters( restParameters );
        message.setPayload( inputParameters );
        return message;
    }


    public String[] getAllParameters( RestParameters restParameters )
    {
        LOG.debug( "Inside method getAllParameters" );
        String[] inputParams = null;
        if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_CREATE ) ) {
            LOG.debug( "Operation Chosen Was Create " );
            inputParams = new String[2];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsTemplateName();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_CREATE_LEAD ) ) {
            LOG.debug( "Operation Chosen Was CreateLead " );
            inputParams = new String[2];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsTemplateName();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_LOAD ) ) {
            LOG.debug( "Operation Chosen Was Load " );
            inputParams = new String[4];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
            inputParams[2] = restParameters.getLoanVO().getsXmlQuery();
            inputParams[3] = restParameters.getLoanVO().getFormat();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_LOCK_LOAN_PROGRAM ) ) {
            LOG.debug( "Operation Chosen Was LockLoanProgram " );
            inputParams = new String[5];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
            inputParams[2] = restParameters.getLoanVO().getIlpTemplateId();
            inputParams[3] = restParameters.getLoanVO().getRequestedRate();
            inputParams[4] = restParameters.getLoanVO().getRequestedFee();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_RUN_QUICK_PRICER ) ) {
            LOG.debug( "Operation Chosen Was RunQuickPricer " );
            inputParams = new String[2];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsXmlData();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_SAVE ) ) {
            LOG.debug( "Operation Chosen Was Save " );
            inputParams = new String[4];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
            inputParams[2] = restParameters.getLoanVO().getsDataContent();
            inputParams[3] = restParameters.getLoanVO().getFormat();
        } else if ( restParameters.getOpName().equals( WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT ) ) {
            LOG.debug( "Operation Chosen Was UploadPDFDocument " );

            inputParams = new String[5];
            inputParams[0] = NewFiManager.userTicket;
            inputParams[1] = restParameters.getLoanVO().getsLoanNumber();
            inputParams[2] = restParameters.getLoanVO().getDocumentType();
            inputParams[3] = restParameters.getLoanVO().getNotes();
            inputParams[4] = restParameters.getLoanVO().getsDataContent();
        }
        return inputParams;
    }
}
