package com.nexera.common.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nexera.common.enums.LoanProgressStatusMasterEnum;

/**
 * 
 * @author rohit
 *
 */

@Entity
@Table(name = "loanprogressstatusmaster")
@NamedQuery(name = "LoanProgressStatusMaster.findAll", query = "SELECT l FROM LoanProgressStatusMaster l")
public class LoanProgressStatusMaster
{
    private int id;
    private String loanProgressStatus;
    private List<Loan> loans;
    
    public LoanProgressStatusMaster() {
	    // TODO Auto-generated constructor stub
    }
    
    public LoanProgressStatusMaster(LoanProgressStatusMasterEnum newLoan) {
	    // TODO Auto-generated constructor stub
    	this.id=newLoan.getStatusId();
    }
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId()
    {
        return id;
    }
    public void setId( int id )
    {
        this.id = id;
    }
    
    @OneToMany(mappedBy = "loanProgressStatus")
    public List<Loan> getLoans()
    {
        return loans;
    }
    public void setLoans( List<Loan> loans )
    {
        this.loans = loans;
    }
    
    @Column(name = "loan_progress_status")
    public String getLoanProgressStatus()
    {
        return loanProgressStatus;
    }
    public void setLoanProgressStatus( String loanProgressStatus )
    {
        this.loanProgressStatus = loanProgressStatus;
    }

}
