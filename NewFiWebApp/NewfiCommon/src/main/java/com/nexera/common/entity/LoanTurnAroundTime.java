package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "loanturnaroundtime")
public class LoanTurnAroundTime implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private WorkflowItemMaster workflowItemMaster;
	private Loan loan;
	private Integer hours;
	private Date createdDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_item_id")
	public WorkflowItemMaster getWorkflowItemMaster() {
		return workflowItemMaster;
	}

	public void setWorkflowItemMaster(WorkflowItemMaster workflowItemMaster) {
		this.workflowItemMaster = workflowItemMaster;
	}

	@Column
	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_id")
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	@Column(name = "created_date")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
