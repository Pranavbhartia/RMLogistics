package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.vo.LoanApplicationReceiptVO;

import java.util.Date;

/**
 * The persistent class for the loanapplicationfee database table.
 * 
 */
@Entity
@Table(name="loanapplicationfee")
@NamedQuery(name = "LoanApplicationFee.findAll", query = "SELECT l FROM LoanApplicationFee l")
public class LoanApplicationFee implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Double fee;
	private Date modifiedDate;
	private Date paymentDate;
	private String paymentType;
	private String transactionMetadata;
	private Loan loan;
	private User modifiedBy;
    private TransactionDetails  transactionDetails;
	public LoanApplicationFee() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public Double getFee() {
		return this.fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "payment_date")
	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Column(name = "payment_type")
	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}


	@Column(name = "transaction_metadata")
	public String getTransactionMetadata() {
		return this.transactionMetadata;
	}

	public void setTransactionMetadata(String transactionMetadata) {
		this.transactionMetadata = transactionMetadata;
	}

	// bi-directional many-to-one association to Loan
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan")
	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_user")
	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id")
	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public static LoanApplicationReceiptVO convertEntityToVO(LoanApplicationFee applicationFee){
		LoanApplicationReceiptVO	applicationReceiptVO = new LoanApplicationReceiptVO();
		applicationReceiptVO.setCreatedAt(applicationFee.getPaymentDate());
		applicationReceiptVO.setCustomerName(applicationFee.getLoan()
				.getUser().getFirstName()
				+ " "
				+ applicationFee.getLoan().getUser().getLastName());
		applicationReceiptVO.setFee(applicationFee.getFee());
		applicationReceiptVO.setInvoice(applicationFee.getId() + "");
		applicationReceiptVO
				.setLoanId(applicationFee.getLoan().getId());
		applicationReceiptVO.setPaymentStatus(CommonConstants.SUCCESS_KEY);
		applicationReceiptVO.setTypeOfCard(applicationFee.getPaymentType());
		return applicationReceiptVO;
	}

}