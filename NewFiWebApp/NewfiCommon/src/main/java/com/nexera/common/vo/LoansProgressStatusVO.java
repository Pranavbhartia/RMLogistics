package com.nexera.common.vo;

import java.io.Serializable;

/**
 * 
 * @author rohit
 *
 */
public class LoansProgressStatusVO implements Serializable
{
    private static final long serialVersionUID = 1L;
    private Integer newProspects;
    private Integer totalLeads;
    private Integer newLoans;
    private Integer inProgress;
    private Integer closed;
    private Integer withdrawn;
    private Integer declined;
    
    public Integer getNewProspects()
    {
        return newProspects;
    }
    public void setNewProspects( Integer newProspects )
    {
        this.newProspects = newProspects;
    }
    public Integer getTotalLeads()
    {
        return totalLeads;
    }
    public void setTotalLeads( Integer totalLeads )
    {
        this.totalLeads = totalLeads;
    }
    public Integer getNewLoans()
    {
        return newLoans;
    }
    public void setNewLoans( Integer newLoans )
    {
        this.newLoans = newLoans;
    }
    public Integer getInProgress()
    {
        return inProgress;
    }
    public void setInProgress( Integer inProgress )
    {
        this.inProgress = inProgress;
    }
    public Integer getClosed()
    {
        return closed;
    }
    public void setClosed( Integer closed )
    {
        this.closed = closed;
    }
    public Integer getWithdrawn()
    {
        return withdrawn;
    }
    public void setWithdrawn( Integer withdrawn )
    {
        this.withdrawn = withdrawn;
    }
    public Integer getDeclined()
    {
        return declined;
    }
    public void setDeclined( Integer declined )
    {
        this.declined = declined;
    }
    
}
