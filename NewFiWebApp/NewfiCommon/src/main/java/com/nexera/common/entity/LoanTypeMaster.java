package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the loantypemaster database table.
 * 
 */
@Entity
@NamedQuery(name = "LoanTypeMaster.findAll", query = "SELECT l FROM LoanTypeMaster l")
public class LoanTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String loanTypeCd;
	private Date modifiedDate;
	private List<Loan> loans;
	private List<LoanAppForm> loanappforms;
	private List<LoanApplicationFeeMaster> loanApplicationFeeMasters;
	private List<LoanMilestoneMaster> loanmilestonemasters;
	private User modifiedBy;

	public LoanTypeMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "loan_type_cd")
	public String getLoanTypeCd() {
		return this.loanTypeCd;
	}

	public void setLoanTypeCd(String loanTypeCd) {
		this.loanTypeCd = loanTypeCd;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	// bi-directional many-to-one association to Loan
	@OneToMany(mappedBy = "loanType")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setLoanType(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setLoanType(null);

		return loan;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "loanTypeMaster")
	public List<LoanAppForm> getLoanappforms() {
		return this.loanappforms;
	}

	public void setLoanappforms(List<LoanAppForm> loanappforms) {
		this.loanappforms = loanappforms;
	}

	public LoanAppForm addLoanappform(LoanAppForm loanappform) {
		getLoanappforms().add(loanappform);
		loanappform.setLoanTypeMaster(this);

		return loanappform;
	}

	public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
		getLoanappforms().remove(loanappform);
		loanappform.setLoanTypeMaster(null);

		return loanappform;
	}

	// bi-directional many-to-one association to LoanApplicationFeeMaster
	@OneToMany(mappedBy = "loanTypeMaster")
	public List<LoanApplicationFeeMaster> getLoanapplicationfeemasters() {
		return this.loanApplicationFeeMasters;
	}

	public void setLoanapplicationfeemasters(
			List<LoanApplicationFeeMaster> loanapplicationfeemasters) {
		this.loanApplicationFeeMasters = loanapplicationfeemasters;
	}

	public LoanApplicationFeeMaster addLoanapplicationfeemaster(
			LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanapplicationfeemasters().add(loanapplicationfeemaster);
		loanapplicationfeemaster.setLoanTypeMaster(this);

		return loanapplicationfeemaster;
	}

	public LoanApplicationFeeMaster removeLoanapplicationfeemaster(
			LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanapplicationfeemasters().remove(loanapplicationfeemaster);
		loanapplicationfeemaster.setLoanTypeMaster(null);

		return loanapplicationfeemaster;
	}

	// bi-directional many-to-one association to LoanMilestoneMaster
	@OneToMany(mappedBy = "loanTypeMaster")
	public List<LoanMilestoneMaster> getLoanmilestonemasters() {
		return this.loanmilestonemasters;
	}

	public void setLoanmilestonemasters(
			List<LoanMilestoneMaster> loanmilestonemasters) {
		this.loanmilestonemasters = loanmilestonemasters;
	}

	public LoanMilestoneMaster addLoanMilestoneMaster(
			LoanMilestoneMaster loanmilestonemaster) {
		getLoanmilestonemasters().add(loanmilestonemaster);
		loanmilestonemaster.setLoanTypeMaster(this);

		return loanmilestonemaster;
	}

	public LoanMilestoneMaster removeLoanMilestoneMaster(
			LoanMilestoneMaster loanmilestonemaster) {
		getLoanmilestonemasters().remove(loanmilestonemaster);
		loanmilestonemaster.setLoanTypeMaster(null);

		return loanmilestonemaster;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}