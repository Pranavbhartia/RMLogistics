package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class UserEmploymentHistoryVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String comments;
	private String companyName;
	private Date endDate;
	private String role;
	private Double salaryBeforeTax;
	private Date startDate;
	private LoanAppFormVO loanAppForm;
	private UserVO user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Double getSalaryBeforeTax() {
		return salaryBeforeTax;
	}
	public void setSalaryBeforeTax(Double salaryBeforeTax) {
		this.salaryBeforeTax = salaryBeforeTax;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public LoanAppFormVO getLoanAppForm() {
		return loanAppForm;
	}
	public void setLoanAppForm(LoanAppFormVO loanAppForm) {
		this.loanAppForm = loanAppForm;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}

}