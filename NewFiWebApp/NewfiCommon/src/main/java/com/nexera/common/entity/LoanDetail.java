package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the loandetails database table.
 * 
 */
@Entity
@Table(name = "loandetails")
@NamedQuery(name = "LoanDetail.findAll", query = "SELECT l FROM LoanDetail l")
public class LoanDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double downPayment;
	private Double emi;
	private Double loanAmount;
	private Double rate;
	private HomeOwnersInsuranceMaster homeOwnersInsurance;
	private TitleCompanyMaster titleCompany;

	public LoanDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "down_payment")
	public Double getDownPayment() {
		return this.downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Double getEmi() {
		return this.emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	@Column(name = "loan_amount")
	public Double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_owners_insurance")
	public HomeOwnersInsuranceMaster getHomeOwnersInsurance() {
		return homeOwnersInsurance;
	}

	public void setHomeOwnersInsurance(
	        HomeOwnersInsuranceMaster homeOwnersInsurance) {
		this.homeOwnersInsurance = homeOwnersInsurance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "title_company")
	public TitleCompanyMaster getTitleCompany() {
		return titleCompany;
	}

	public void setTitleCompany(TitleCompanyMaster titleCompany) {
		this.titleCompany = titleCompany;
	}

}