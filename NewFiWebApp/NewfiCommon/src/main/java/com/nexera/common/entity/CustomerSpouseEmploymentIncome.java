package com.nexera.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the CustomerEmploymentIncome database table.
 * 
 */
@Entity
@Table(name = "customerspouseemploymentincome")
@NamedQuery(name = "CustomerSpouseEmploymentIncome.findAll", query = "SELECT csi FROM CustomerSpouseEmploymentIncome csi")
public class CustomerSpouseEmploymentIncome {

	private static final long serialVersionUID = 1L;
	private int id;
	private String employedIncomePreTax;
	private String employedAt;
	private String employedSince;
	private String jobTitle;
	private LoanAppForm loanAppForms;
	private Double employmentLength;

	public CustomerSpouseEmploymentIncome() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "employed_income_pre_tax")
	public String getEmployedIncomePreTax() {
		return employedIncomePreTax;
	}

	public void setEmployedIncomePreTax(String employedIncomePreTax) {
		this.employedIncomePreTax = employedIncomePreTax;
	}

	@Column(name = "employed_at")
	public String getEmployedAt() {
		return employedAt;
	}

	public void setEmployedAt(String employedAt) {
		this.employedAt = employedAt;
	}

	@Column(name = "employed_since")
	public String getEmployedSince() {
		return employedSince;
	}

	public void setEmployedSince(String employedSince) {
		this.employedSince = employedSince;
	}

	@ManyToOne
	@JoinColumn(name = "loanapp_formid")
	public LoanAppForm getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(LoanAppForm loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	@Column(name = "job_title")
	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	@Column(name = "empl_len")
	public Double getEmploymentLength() {
		return employmentLength;
	}

	public void setEmploymentLength(Double employmentLength) {
		this.employmentLength = employmentLength;
	}

}
