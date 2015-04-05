/**
 * 
 */
package com.newfi.nexera.process;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;


/**
 * @author Utsav
 *
 */
public class NewFiDocsResponseProcessor implements Callable
{

    private static final Logger LOG = Logger.getLogger( NewFiDocsResponseProcessor.class );


    /* (non-Javadoc)
     * @see org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
     */
    @Override
    public Object onCall( MuleEventContext eventContext ) throws Exception
    {
        LOG.debug( "Inside method onCall" );

        MuleMessage message = eventContext.getMessage();
        byte[] pay = (byte[]) message.getPayload();
        message.setPayload( pay );
        return message;
    }


    public static String removeUTFCharacters( String data )
    {
        Pattern p = Pattern.compile( "\\\\u(\\p{XDigit}{4})" );
        Matcher m = p.matcher( data );
        StringBuffer buf = new StringBuffer( data.length() );
        while ( m.find() ) {
            String ch = String.valueOf( (char) Integer.parseInt( m.group( 1 ), 16 ) );
            m.appendReplacement( buf, Matcher.quoteReplacement( ch ) );
        }
        m.appendTail( buf );
        return buf.toString();
    }

}
