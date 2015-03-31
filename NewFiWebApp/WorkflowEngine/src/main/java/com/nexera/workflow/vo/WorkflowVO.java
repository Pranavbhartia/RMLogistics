/**
 * 
 */
package com.nexera.workflow.vo;

/**
 * @author Utsav
 *
 */
public class WorkflowVO
{

	private Integer loanID;

	public Integer getLoanID() {
		return loanID;
	}

	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}

	private String workflowType;


    /**
     * @return the workflowType
     */
    public String getWorkflowType()
    {
        return workflowType;
    }


    /**
     * @param workflowType the workflowType to set
     */
    public void setWorkflowType( String workflowType )
    {
        this.workflowType = workflowType;
    }


}
