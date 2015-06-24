/**
 * 
 */
package com.nexera.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.newfi.nexera.constants.NewFiConstants;
import com.newfi.nexera.manager.NewFiManager;
import com.newfi.nexera.vo.AuthenticateVO;


/**
 * @author Utsav
 *
 */
public class Utils
{
    private static final Logger LOG = Logger.getLogger( Utils.class );
    private ResourceBundle bundle = null;


    Utils()
    {
        bundle = ResourceBundle.getBundle( NewFiConstants.PROPERTY_FILE_NAME );
    }


    public void getUserTicket()
    {
        String newfiUsername = bundle.getString( NewFiConstants.NEWFI_USERNAME );
        String newfiPassword = bundle.getString( NewFiConstants.NEWFI_PASSWORD );
        String newfiUsernameBackup = bundle.getString( NewFiConstants.NEWFI_USERNAME_BACKUP );
        String newfiPasswordBackup = bundle.getString( NewFiConstants.NEWFI_PASSWORD_BACKUP );

        if ( NewFiManager.userTicket == null ) {
            LOG.info( "Generating the user ticket for the user " + newfiUsername );
            generateUserAuthenticationTicket( newfiUsername, newfiPassword );
            if ( NewFiManager.userTicket == null ) {
                LOG.info( "Valid ticket was not generated hence trying again with backup account sleeping for 2 sec before continuing "
                    + newfiUsernameBackup );
                try {
                    Thread.sleep( 2000 );
                } catch ( InterruptedException ie ) {
                    Thread.interrupted();
                }
                LOG.info( "Woke up. Trying now for user: " + newfiUsernameBackup );
                LOG.info( "Generating the user ticket for the user " + newfiUsernameBackup );
                generateUserAuthenticationTicket( newfiUsernameBackup, newfiPasswordBackup );
            }

        } else {
            long generationTime = NewFiManager.generationTime;
            long currentTime = System.currentTimeMillis();
            long differenceInMilliSeconds = currentTime - generationTime;
            if ( differenceInMilliSeconds >= 14340000 ) {
                LOG.info( "Ticket would have expired as time difference has gone beyond 3 hours and 59 minutes" );
                NewFiManager.userTicket = null;
                generateUserAuthenticationTicket( newfiUsername, newfiPassword );
                if ( NewFiManager.userTicket == null ) {
                    LOG.info( "Valid ticket was not generated hence trying again with backup account, sleeping for 2 sec before continuing "
                        + newfiUsernameBackup );
                    try {
                        Thread.sleep( 2000 );
                    } catch ( InterruptedException ie ) {
                        Thread.interrupted();
                    }
                    LOG.info( "Woke up. Trying now for user: " + newfiUsernameBackup );
                    generateUserAuthenticationTicket( newfiUsernameBackup, newfiPasswordBackup );
                    if ( NewFiManager.userTicket != null ) {
                        LOG.info( "Ticket generated " + NewFiManager.userTicket );
                    } else {
                        LOG.error( "Not able to generate ticket even after 2 attempts " );
                        // TODO Send Email to everyone
                    }
                } else {
                    LOG.info( "Ticket generated " + NewFiManager.userTicket );
                }

            } else {
                LOG.info( "The ticket has not yet expired, hence picking the ticket which was cached before "
                    + NewFiManager.userTicket );
            }
        }
    }


    public synchronized void generateUserAuthenticationTicket( String userName, String password )
    {
        String ticket = null;
        if ( NewFiManager.userTicket == null ) {
            String url = bundle.getString( NewFiConstants.AUTHENTICATION_URL );
            LOG.info( "Inside method getUserTicket " );
            AuthenticateVO authenticate = new AuthenticateVO();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType( MediaType.APPLICATION_JSON );
            headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON ) );
            ResponseEntity<String> response = new ResponseEntity<String>( headers, HttpStatus.OK );
            authenticate.setOpName( "GetUserAuthTicket" );
            authenticate.setUserName( userName );
            authenticate.setPassWord( password );
            Gson gson = new Gson();
            String jsonString = gson.toJson( authenticate );
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity( url, jsonString, String.class );
            ticket = response.getBody();
            if ( !ticket.contains( NewFiConstants.VALID_TICKET_CHECK ) ) {
                LOG.info( "Valid ticket not generated " );
                ticket = null;
            } else {
                NewFiManager.userTicket = ticket;
                NewFiManager.generationTime = System.currentTimeMillis();
            }
        }

    }


    public String readFileAsString( String fileName ) throws IOException
    {
        ClassLoader classLoader = Utils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream( fileName );
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream ) );
        char[] buf = new char[1024];
        int numRead = 0;
        while ( ( numRead = reader.read( buf ) ) != -1 ) {
            String readData = String.valueOf( buf, 0, numRead );
            fileData.append( readData );
        }
        reader.close();

        return fileData.toString();
    }


    public String applyMapOnString( Map<String, String> map, String fileData )
    {
        if ( map != null ) {
            Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
            while ( entries.hasNext() ) {
                Map.Entry<String, String> entry = entries.next();
                if ( fileData.contains( entry.getKey() ) ) {
                    if ( entry.getValue() == null ) {

                    } else {
                        fileData = fileData.replace( entry.getKey(), entry.getValue() );
                    }
                }
            }
        }
        return fileData;
    }


    /**
     * @param absolutePath
     * @return
     * @throws IOException
     */
    public String readFileAsStringFromPath( String absolutePath ) throws IOException
    {
        StringBuilder fileData = new StringBuilder( 1000 );
        BufferedReader reader = new BufferedReader( new FileReader( absolutePath ) );

        char[] buf = new char[1024];
        int numRead = 0;
        while ( ( numRead = reader.read( buf ) ) != -1 ) {
            String readData = String.valueOf( buf, 0, numRead );
            fileData.append( readData );
            buf = new char[1024];
        }

        reader.close();

        String returnStr = fileData.toString();
        return returnStr;
    }
}
