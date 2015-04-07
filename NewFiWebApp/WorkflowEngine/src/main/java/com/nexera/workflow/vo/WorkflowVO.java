/**
 * 
 */
package com.nexera.workflow.vo;

/**
 * @author Utsav
 *
 */
public class WorkflowVO {

	private Integer loanID;

	public WorkflowVO() {
		// TODO Auto-generated constructor stub
	}

	public WorkflowVO(int lnId) {
		this.loanID = lnId;
	}

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
	public String getWorkflowType() {
		return workflowType;
	}

	/**
	 * @param workflowType
	 *            the workflowType to set
	 */
	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}

}
