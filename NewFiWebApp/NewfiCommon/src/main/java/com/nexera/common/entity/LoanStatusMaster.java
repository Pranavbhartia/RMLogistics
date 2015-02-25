package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the loanstatusmaster database table.
 * 
 */
@Entity
@NamedQuery(name="LoanStatusMaster.findAll", query="SELECT l FROM LoanStatusMaster l")
public class LoanStatusMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String loanStatusCd;
	private Date modifiedDate;
	private List<Loan> loans;
	private User user;

	public LoanStatusMaster() {
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


	@Column(name="loan_status_cd")
	public String getLoanStatusCd() {
		return this.loanStatusCd;
	}

	public void setLoanStatusCd(String loanStatusCd) {
		this.loanStatusCd = loanStatusCd;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	//bi-directional many-to-one association to Loan
	@OneToMany(mappedBy="loanstatusmaster")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setLoanStatus(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setLoanStatus(null);

		return loan;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}