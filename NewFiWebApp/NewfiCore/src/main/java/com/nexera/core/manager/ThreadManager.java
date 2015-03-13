package com.nexera.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.core.utility.LoanStatusMaster;


@Component
@Scope ( value = "prototype")
public class ThreadManager implements Runnable
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ThreadManager.class );

    private Loan loan;


    @Override
    public void run()
    {
        LOGGER.debug( "Inside method run " );
        LoanProgressStatusMaster loanProgressStatusMaster = loan.getLoanProgressStatus();
        if ( loanProgressStatusMaster.getLoanProgressStatus().equalsIgnoreCase( LoanStatusMaster.STATUS_NEW_LOAN ) ) {
            //TODO Invoke a lqb service
        } else if ( loanProgressStatusMaster.getLoanProgressStatus().equalsIgnoreCase( LoanStatusMaster.STATUS_NEW_PROJECT ) ) {
            //TODO Invoke a lqb service
        } else if ( loanProgressStatusMaster.getLoanProgressStatus().equalsIgnoreCase( LoanStatusMaster.STATUS_LEAD ) ) {
            //TODO Invoke a lqb service
        } else if ( loanProgressStatusMaster.getLoanProgressStatus().equalsIgnoreCase( LoanStatusMaster.STATUS_IN_PROGRESS ) ) {
            //TODO Invoke a lqb service
        }

    }


    public Loan getLoan()
    {
        return loan;
    }


    public void setLoan( Loan loan )
    {
        this.loan = loan;
    }

}
