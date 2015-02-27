package com.nexera.workflow.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.manager.CacheManager;
import com.nexera.workflow.manager.WorkflowManager;
import com.nexera.workflow.service.WorkflowService;
import com.nexera.workflow.vo.WorkflowVO;


public class EngineTrigger
{

    private static final Logger LOGGER = LoggerFactory.getLogger( EngineTrigger.class );
    private ExecutorService executorService;
    @Autowired
    CacheManager cacheManager;

    @Autowired
    WorkflowService workflowService;


    public void triggerWorkFlow( String workflowJsonString )
    {
        LOGGER.debug( "Triggering a workflow " );

        Gson gson = new Gson();
        WorkflowVO workflowVO = gson.fromJson( workflowJsonString, WorkflowVO.class );
        if ( workflowVO != null ) {
            String workflowType = workflowVO.getWorkflowType();
            WorkflowMaster workflowMaster = workflowService.getWorkFlowByWorkFlowType( workflowType );
            if ( workflowMaster != null ) {
                LOGGER.debug( "Workflow found for this workflowtype " + workflowMaster.getWorkflowType() );
                if ( !cacheManager.isInitialized() ) {
                    executorService = cacheManager.initializePool();
                }
                WorkflowManager workflowManager = new WorkflowManager();
                workflowManager.setWorkflowMaster( workflowMaster );
                LOGGER.debug( "Putting the workflow into execution " );
                executorService.execute( workflowManager );

            }
        }

    }

}
