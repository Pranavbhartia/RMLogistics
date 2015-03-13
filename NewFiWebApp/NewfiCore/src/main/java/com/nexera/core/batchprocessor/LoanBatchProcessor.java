package com.nexera.core.batchprocessor;

import java.util.List;
import java.util.concurrent.ExecutorService;

import org.apache.catalina.core.ApplicationContext;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.core.manager.ThreadManager;
import com.nexera.core.manager.ThreadPoolManager;
import com.nexera.core.service.LoanService;
import com.nexera.core.utility.LoanStatusMaster;


@DisallowConcurrentExecution
public class LoanBatchProcessor extends QuartzJobBean
{

    private static final Logger LOGGER = LoggerFactory.getLogger( LoanBatchProcessor.class );

    @Autowired
    private ThreadPoolManager threadPoolManager;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ThreadManager threadManager;

    private ExecutorService executorService;


    @Override
    protected void executeInternal( JobExecutionContext arg0 ) throws JobExecutionException
    {

        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext( this );
        LOGGER.debug( "Triggering the Quartz Schedular " );

        if ( !threadPoolManager.isInitialized() ) {
            LOGGER.debug( "Creating a fixed size thread pool " );
            executorService = threadPoolManager.initializePool();
        }

        List<Loan> loanList = loanService.getAllLoans();
        if ( loanList != null ) {
            for ( Loan loan : loanList ) {
                LoanProgressStatusMaster loanProgessStatus = loan.getLoanProgressStatus();
                String loanStatus = loanProgessStatus.getLoanProgressStatus();
                if ( ( !loanStatus.equalsIgnoreCase( LoanStatusMaster.STATUS_CLOSED ) )
                    && ( !loanStatus.equalsIgnoreCase( LoanStatusMaster.STATUS_WITHDRAW ) )
                    && ( !loanStatus.equalsIgnoreCase( LoanStatusMaster.STATUS_DECLINED ) ) ) {
                    threadManager.setLoan( loan );
                    executorService.execute( threadManager );
                }
            }
        }
    }


}
