/**
 * 
 */
package com.nexera.util;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.newfi.nexera.vo.AuthenticateVO;


/**
 * @author Utsav
 *
 */
public class Utils
{
    private static final Logger LOG = Logger.getLogger( Utils.class );


    public static String getUserTicket( String userName, String passWord )
    {
        String url = "http://localhost:8181/authCall";
        LOG.debug( "Inside method getUserTicket " );
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
        String ticket = response.getBody();
        return ticket;
    }
}
