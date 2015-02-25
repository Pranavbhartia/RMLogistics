package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the loansetting database table.
 * 
 */
@Entity
@NamedQuery(name="LoanSetting.findAll", query="SELECT l FROM LoanSetting l")
public class LoanSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String dataType;
	private Date modifiedDate;
	private String value;
	private User user;
	private Loan loanBean;

	public LoanSetting() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="data_type")
	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="modified_by")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
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