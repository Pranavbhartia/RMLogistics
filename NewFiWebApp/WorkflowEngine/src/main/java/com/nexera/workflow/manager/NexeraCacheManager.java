/**
 * 
 */
package com.nexera.workflow.manager;

import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.workflow.Constants.WorkflowConstants;


/**
 * @author Utsav
 *
 */
@Component
public class NexeraCacheManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NexeraCacheManager.class );
    private ResourceBundle bundle = null;
    private ExecutorService executorService;
    private boolean initialized = false;


    public NexeraCacheManager()
    {
        bundle = ResourceBundle.getBundle( WorkflowConstants.PROPERTY_FILE_NAME );
    }


    public ExecutorService initializePool()
    {
        LOGGER.debug( "Inside method initialize thread pool " );
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        //String poolSizeString = bundle.getString( WorkflowConstants.POOL_SIZE );
        executorService = Executors.newFixedThreadPool( Integer.parseInt( "5" ), threadFactory );

        setInitialized( true );
        return executorService;
    }


    /**
     * @return the initialized
     */
    public boolean isInitialized()
    {
        return initialized;
    }


    /**
     * @param initialized the initialized to set
     */
    public void setInitialized( boolean initialized )
    {
        this.initialized = initialized;
    }
}
