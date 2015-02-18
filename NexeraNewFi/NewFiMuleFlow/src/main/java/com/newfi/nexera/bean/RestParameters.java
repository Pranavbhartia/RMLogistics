/**
 * 
 */
package com.newfi.nexera.bean;

import com.newfi.nexera.vo.LoanVO;


/**
 * @author Utsav
 *
 */
public class RestParameters
{
    public String opName;

    private LoanVO loanVO;


    /**
     * @return the opName
     */
    public String getOpName()
    {
        return opName;
    }


    /**
     * @param opName the opName to set
     */
    public void setOpName( String opName )
    {
        this.opName = opName;
    }


    /**
     * @return the loanVO
     */
    public LoanVO getLoanVO()
    {
        return loanVO;
    }


    /**
     * @param loanVO the loanVO to set
     */
    public void setLoanVO( LoanVO loanVO )
    {
        this.loanVO = loanVO;
    }


}
