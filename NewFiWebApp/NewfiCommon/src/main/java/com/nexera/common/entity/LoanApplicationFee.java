package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanapplicationfee database table.
 * 
 */
@Entity
@NamedQuery(name="LoanApplicationFee.findAll", query="SELECT l FROM LoanApplicationFee l")
public class LoanApplicationFee implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private double fee;
	private Date modifiedDate;
	private Date paymentDate;
	private String paymentType;
	private String transactionId;
	private String transactionMetadata;
	private Loan loanBean;
	private User user;

	public LoanApplicationFee() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}


	public double getFee() {
		return this.fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="payment_date")
	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}


	@Column(name="payment_type")
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	@Column(name="transaction_id")
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	@Column(name="transaction_metadata")
	public String getTransactionMetadata() {
		return this.transactionMetadata;
	}

	public void setTransactionMetadata(String transactionMetadata) {
		this.transactionMetadata = transactionMetadata;
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


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_user")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}