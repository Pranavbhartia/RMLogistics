package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loanapplicationfeemaster database table.
 * 
 */
@Entity
@NamedQuery(name="LoanApplicationFeeMaster.findAll", query="SELECT l FROM LoanApplicationFeeMaster l")
public class LoanApplicationFeeMaster implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private double fee;
	private Date modifiedDate;
	private LoanTypeMaster loantypemaster;
	private User user;
	private PropertyTypeMaster propertytypemaster;

	public LoanApplicationFeeMaster() {
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


	//bi-directional many-to-one association to LoanTypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan_type")
	public LoanTypeMaster getLoantypemaster() {
		return this.loantypemaster;
	}

	public void setLoantypemaster(LoanTypeMaster loantypemaster) {
		this.loantypemaster = loantypemaster;
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


	//bi-directional many-to-one association to PropertyTypeMaster
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="property_type")
	public PropertyTypeMaster getPropertytypemaster() {
		return this.propertytypemaster;
	}

	public void setPropertytypemaster(PropertyTypeMaster propertytypemaster) {
		this.propertytypemaster = propertytypemaster;
	}

}