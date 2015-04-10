package com.nexera.common.vo;

import java.sql.Timestamp;
import java.util.Date;

public class LoanApplicationReceiptVO {

	private Integer loanApplicationFeeId;
	private String customerName;
	private String invoice;
	private Integer loanId;
	private Date createdAt;
	private String typeOfCard;
	private String paymentStatus;
	private Double fee;

	public Integer getLoanApplicationFeeId() {
		return loanApplicationFeeId;
	}

	public void setLoanApplicationFeeId(Integer loanApplicationFeeId) {
		this.loanApplicationFeeId = loanApplicationFeeId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getTypeOfCard() {
		return typeOfCard;
	}

	public void setTypeOfCard(String typeOfCard) {
		this.typeOfCard = typeOfCard;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

}
