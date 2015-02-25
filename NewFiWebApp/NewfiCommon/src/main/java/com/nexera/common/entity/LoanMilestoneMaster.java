package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the loanmilestonemaster database table.
 * 
 */
@Entity
@NamedQuery(name="LoanMilestoneMaster.findAll", query="SELECT l FROM LoanMilestoneMaster l")
public class LoanMilestoneMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String milestoneValidator;
	private String name;
	private List<Loan> loans;
	private List<LoanMilestone> loanmilestones;
	private LoanTypeMaster loantypemaster;

	public LoanMilestoneMaster() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="milestone_validator")
	public String getMilestoneValidator() {
		return this.milestoneValidator;
	}

	public void setMilestoneValidator(String milestoneValidator) {
		this.milestoneValidator = milestoneValidator;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	//bi-directional many-to-one association to Loan
	@OneToMany(mappedBy="loanmilestonemaster")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setLoanmilestonemaster(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setLoanmilestonemaster(null);

		return loan;
	}


	//bi-directional many-to-one association to LoanMilestone
	@OneToMany(mappedBy="loanmilestonemaster")
	public List<LoanMilestone> getLoanmilestones() {
		return this.loanmilestones;
	}

	public void setLoanmilestones(List<LoanMilestone> loanmilestones) {
		this.loanmilestones = loanmilestones;
	}

	public LoanMilestone addLoanmilestone(LoanMilestone loanmilestone) {
		getLoanmilestones().add(loanmilestone);
		loanmilestone.setLoanmilestonemaster(this);

		return loanmilestone;
	}

	public LoanMilestone removeLoanmilestone(LoanMilestone loanmilestone) {
		getLoanmilestones().remove(loanmilestone);
		loanmilestone.setLoanmilestonemaster(null);

		return loanmilestone;
	}


	//bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan_type")
	public LoanTypeMaster getLoantypemaster() {
		return this.loantypemaster;
	}

	public void setLoantypemaster(LoanTypeMaster loantypemaster) {
		this.loantypemaster = loantypemaster;
	}

}