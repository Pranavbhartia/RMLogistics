package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the loanapplicationfeemaster database table.
 * 
 */
@Entity
@Table(name="loanapplicationfeemaster")
@NamedQuery(name = "LoanApplicationFeeMaster.findAll", query = "SELECT l FROM LoanApplicationFeeMaster l")
public class LoanApplicationFeeMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Double fee;
	private Date modifiedDate;
	private LoanTypeMaster loanTypeMaster;
	private User modifiedBy;
	private PropertyTypeMaster propertyTypeMaster;

	public LoanApplicationFeeMaster() {
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

	// bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "loan_type")
	public LoanTypeMaster getLoanTypeMaster() {
		return loanTypeMaster;
	}

	public void setLoanTypeMaster(LoanTypeMaster loanTypeMaster) {
		this.loanTypeMaster = loanTypeMaster;
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

	// bi-directional many-to-one association to PropertyTypeMaster
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "property_type")
	public PropertyTypeMaster getPropertyTypeMaster() {
		return propertyTypeMaster;
	}

	public void setPropertyTypeMaster(PropertyTypeMaster propertyTypeMaster) {
		this.propertyTypeMaster = propertyTypeMaster;
	}

}