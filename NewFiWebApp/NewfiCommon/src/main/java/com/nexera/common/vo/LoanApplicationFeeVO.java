package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class LoanApplicationFeeVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Double fee;
	private Date modifiedDate;
	private Date paymentDate;
	private String paymentType;
	private String transactionId;
	private String transactionMetadata;
	private LoanVO loan;
	private UserVO modifiedBy;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionMetadata() {
		return transactionMetadata;
	}
	public void setTransactionMetadata(String transactionMetadata) {
		this.transactionMetadata = transactionMetadata;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}