package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the propertytypemaster database table.
 * 
 */
@Entity
@NamedQuery(name = "PropertyTypeMaster.findAll", query = "SELECT p FROM PropertyTypeMaster p")
public class PropertyTypeMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private Date modifiedDate;
	private String propertyTypeCd;
	private List<Loan> loans;
	private List<LoanAppForm> loanAppForms;
	private List<LoanApplicationFeeMaster> loanApplicationFeeMasters;
	private User modifiedBy;

	public PropertyTypeMaster() {
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "property_type_cd")
	public String getPropertyTypeCd() {
		return this.propertyTypeCd;
	}

	public void setPropertyTypeCd(String propertyTypeCd) {
		this.propertyTypeCd = propertyTypeCd;
	}

	// bi-directional many-to-one association to Loan
	@OneToMany(mappedBy = "propertyType")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setPropertyType(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setPropertyType(null);

		return loan;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "propertyTypeMaster")
	public List<LoanAppForm> getLoanappforms() {
		return this.loanAppForms;
	}

	public void setLoanappforms(List<LoanAppForm> loanappforms) {
		this.loanAppForms = loanappforms;
	}

	public LoanAppForm addLoanappform(LoanAppForm loanappform) {
		getLoanappforms().add(loanappform);
		loanappform.setPropertyTypeMaster(this);

		return loanappform;
	}

	public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
		getLoanappforms().remove(loanappform);
		loanappform.setPropertyTypeMaster(null);

		return loanappform;
	}

	// bi-directional many-to-one association to LoanApplicationFeeMaster
	@OneToMany(mappedBy = "propertyTypeMaster")
	public List<LoanApplicationFeeMaster> getLoanApplicationFeeMasters() {
		return this.loanApplicationFeeMasters;
	}

	public void setLoanApplicationFeeMasters(
			List<LoanApplicationFeeMaster> loanapplicationfeemasters) {
		this.loanApplicationFeeMasters = loanapplicationfeemasters;
	}

	public LoanApplicationFeeMaster addLoanApplicationfeemaster(
			LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanApplicationFeeMasters().add(loanapplicationfeemaster);
		loanapplicationfeemaster.setPropertyTypeMaster(this);

		return loanapplicationfeemaster;
	}

	public LoanApplicationFeeMaster removeLoanApplicationFeeMaster(
			LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanApplicationFeeMasters().remove(loanapplicationfeemaster);
		loanapplicationfeemaster.setPropertyTypeMaster(null);

		return loanapplicationfeemaster;
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