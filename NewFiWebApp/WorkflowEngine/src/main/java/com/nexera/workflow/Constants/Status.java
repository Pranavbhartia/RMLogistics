package com.nexera.workflow.Constants;

public enum Status
{
    NOT_STARTED( "0" ), STARTED( "1" ), PENDING( "2" ), COMPLETED( "3" );

    private String status;


    Status( String status )
    {
        this.status = status;
    }


    public String getStatus()
    {
        return status;
    }
}
