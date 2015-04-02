package com.nexera.common.vo;

import java.io.Serializable;

public class CustomerSpouseEmploymentIncomeVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private int      id;
	private String   employedIncomePreTax;
	private String   employedAt;
	private String   employedSince; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmployedIncomePreTax() {
		return employedIncomePreTax;
	}
	public void setEmployedIncomePreTax(String employedIncomePreTax) {
		this.employedIncomePreTax = employedIncomePreTax;
	}
	public String getEmployedAt() {
		return employedAt;
	}
	public void setEmployedAt(String employedAt) {
		this.employedAt = employedAt;
	}
	public String getEmployedSince() {
		return employedSince;
	}
	public void setEmployedSince(String employedSince) {
		this.employedSince = employedSince;
	}
	
	
	
}
