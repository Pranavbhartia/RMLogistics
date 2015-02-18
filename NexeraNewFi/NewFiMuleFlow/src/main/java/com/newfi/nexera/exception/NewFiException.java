/**
 * 
 */
package com.newfi.nexera.exception;

/**
 * @author Utsav
 *
 */
public class NewFiException extends Exception
{
    /** Class to customize expection. */
    private static final long serialVersionUID = 1L;


    /**
     * Instantiates a new beacon exception.
     */
    public NewFiException()
    {
        super( "NewFiException" );
    }


    /**
     * Instantiates a new beacon exception.
     * 
     * @param message
     *            the message
     */
    public NewFiException( String message )
    {
        super( message );
    }


    /**
     * Instantiates a new beacon exception.
     * 
     * @param throwable
     *            the throwable
     */
    public NewFiException( Throwable throwable )
    {
        super( throwable );
    }


    /**
     * Instantiates a new beacon exception.
     * 
     * @param message
     *            the message
     * @param throwable
     *            the throwable
     */
    public NewFiException( String message, Throwable throwable )
    {
        super( message, throwable );
    }
}
