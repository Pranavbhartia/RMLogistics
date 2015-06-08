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

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.newfi.nexera.manager.NewFiManager;
import com.newfi.nexera.vo.AuthenticateVO;


/**
 * @author Utsav
 *
 */
public class Utils
{
    private static final Logger LOG = Logger.getLogger( Utils.class );


    public synchronized String getUserTicket( String userName, String passWord )
    {
        String ticket = null;
        if ( NewFiManager.userTicket == null ) {
            String url = "http://localhost:8181/authCall";
            LOG.info( "Inside method getUserTicket " );
            AuthenticateVO authenticate = new AuthenticateVO();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType( MediaType.APPLICATION_JSON );
            headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON ) );
            ResponseEntity<String> response = new ResponseEntity<String>( headers, HttpStatus.OK );
            authenticate.setOpName( "GetUserAuthTicket" );
            authenticate.setUserName( userName );
            authenticate.setPassWord( passWord );
            Gson gson = new Gson();
            String jsonString = gson.toJson( authenticate );
            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.postForEntity( url, jsonString, String.class );
            ticket = response.getBody();
            if ( !ticket.contains( "EncryptedTicket" ) ) {
                LOG.info( "Valid ticket not generated " );
                ticket = null;
            }
            NewFiManager.generationTime = System.currentTimeMillis();
        } else {
            ticket = NewFiManager.userTicket;
        }
        return ticket;
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
