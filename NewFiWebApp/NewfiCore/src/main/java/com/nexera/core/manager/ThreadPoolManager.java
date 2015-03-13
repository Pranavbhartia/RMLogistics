/**
 * 
 */
package com.nexera.core.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author Utsav
 *
 */

@Component
public class ThreadPoolManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ThreadPoolManager.class );
    private ExecutorService executorService;
    private boolean initialized = false;
    private int threadPoolSize = 5;


    public ExecutorService initializePool()
    {
        LOGGER.debug( "Inside method initialize thread pool " );
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        executorService = Executors.newFixedThreadPool( threadPoolSize, threadFactory );

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
