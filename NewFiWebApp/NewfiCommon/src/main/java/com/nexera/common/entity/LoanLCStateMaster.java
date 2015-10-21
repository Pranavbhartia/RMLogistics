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

import com.nexera.common.enums.LoanLCStates;

/**
 * 
 * @author 
 *
 */

@Entity
@Table(name = "loanlcstatemaster")
@NamedQuery(name = "LoanLCStateMaster.findAll", query = "SELECT l FROM LoanLCStateMaster l")
public class LoanLCStateMaster {
    private int id;
    private String loanLCState;
    private List<Loan> loans;

    public LoanLCStateMaster() {
        // TODO Auto-generated constructor stub
    }

    public LoanLCStateMaster(LoanLCStates loanLCState) {
        // TODO Auto-generated constructor stub
        this.id = loanLCState.getLcStateID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "loanProgressStatus")
    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Column(name = "loanLCState")
    public String getLoanLCState() {
        return loanLCState;
    }

    public void setLoanLCState(String loanLCState) {
        this.loanLCState = loanLCState;
    }

}