package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

public class LoanMilestoneMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String milestoneValidator;
	private String name;
	private List<LoanVO> loans;
	private List<LoanMilestoneVO> loanMilestones;
	private LoanTypeMasterVO loanTypeMaster;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMilestoneValidator() {
		return milestoneValidator;
	}
	public void setMilestoneValidator(String milestoneValidator) {
		this.milestoneValidator = milestoneValidator;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<LoanVO> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}
	public List<LoanMilestoneVO> getLoanMilestones() {
		return loanMilestones;
	}
	public void setLoanMilestones(List<LoanMilestoneVO> loanMilestones) {
		this.loanMilestones = loanMilestones;
	}
	public LoanTypeMasterVO getLoanTypeMaster() {
		return loanTypeMaster;
	}
	public void setLoanTypeMaster(LoanTypeMasterVO loanTypeMaster) {
		this.loanTypeMaster = loanTypeMaster;
	}



}