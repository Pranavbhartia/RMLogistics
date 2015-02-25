package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the loandetails database table.
 * 
 */
@Entity
@Table(name="loandetails")
@NamedQuery(name="LoanDetail.findAll", query="SELECT l FROM LoanDetail l")
public class LoanDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private double downPayment;
	private double emi;
	private double loanAmount;
	private double rate;
	private Loan loanBean;

	public LoanDetail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="down_payment")
	public double getDownPayment() {
		return this.downPayment;
	}

	public void setDownPayment(double downPayment) {
		this.downPayment = downPayment;
	}


	public double getEmi() {
		return this.emi;
	}

	public void setEmi(double emi) {
		this.emi = emi;
	}


	@Column(name="loan_amount")
	public double getLoanAmount() {
		return this.loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}


	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}


	//bi-directional many-to-one association to Loan
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan")
	public Loan getLoanBean() {
		return this.loanBean;
	}

	public void setLoanBean(Loan loanBean) {
		this.loanBean = loanBean;
	}

}