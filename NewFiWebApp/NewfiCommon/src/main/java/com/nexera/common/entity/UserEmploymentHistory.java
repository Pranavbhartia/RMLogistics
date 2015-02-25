package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the useremploymenthistory database table.
 * 
 */
@Entity
@NamedQuery(name="UserEmploymentHistory.findAll", query="SELECT u FROM UserEmploymentHistory u")
public class UserEmploymentHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private String companyName;
	private Date endDate;
	private String role;
	private double salaryBeforeTax;
	private Date startDate;
	private LoanAppForm loanappform;
	private User userBean;

	public UserEmploymentHistory() {
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


	@Column(name="company_name")
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_date")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	@Column(name="salary_before_tax")
	public double getSalaryBeforeTax() {
		return this.salaryBeforeTax;
	}

	public void setSalaryBeforeTax(double salaryBeforeTax) {
		this.salaryBeforeTax = salaryBeforeTax;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_date")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	//bi-directional many-to-one association to LoanAppForm
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="loan_app_form")
	public LoanAppForm getLoanappform() {
		return this.loanappform;
	}

	public void setLoanappform(LoanAppForm loanappform) {
		this.loanappform = loanappform;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

}