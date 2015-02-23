package com.nexera.newfi.exception;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(SQLException.class)
    public String handleSQLException(HttpRequest request, Exception ex){
        //logger.info("SQLException Occured:: URL="+request.getRequestURL());
        return "database_error";
    }
     
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException(){
        //logger.error("IOException handler executed");
        //returning 404 error code
    }
	
}
