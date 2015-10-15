package com.nexera.common.vo;

import java.io.Serializable;

public class LoanDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Double downPayment;
	private Double emi;
	private Double loanAmount;
	private Double rate;
	private LoanVO loan;
	private String paymentVendor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Double getEmi() {
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public LoanVO getLoan() {
		return loan;
	}

	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}

	public String getPaymentVendor() {
		return paymentVendor;
	}

	public void setPaymentVendor(String paymentVendor) {
		this.paymentVendor = paymentVendor;
	}
}