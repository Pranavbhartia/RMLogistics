package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * The persistent class for the CustomerEmploymentIncome database table.
 * 
 */
@Entity
@Table(name = "customeremploymentincome")
@NamedQuery(name = "CustomerEmploymentIncome.findAll", query = "SELECT ci FROM CustomerEmploymentIncome ci")
public class CustomerEmploymentIncome implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer      id;
	private String   employedIncomePreTax;
	private String   employedAt;
	private String   employedSince; 
	
	
	public CustomerEmploymentIncome() {
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@Column(name = "employed_income_pre_tax")
	public String getEmployedIncomePreTax() {
		return employedIncomePreTax;
	}
	


	public void setEmployedIncomePreTax(String employedIncomePreTax) {
		this.employedIncomePreTax = employedIncomePreTax;
	}
	@Column(name = "employed_at")
	public String getEmployedAt() {
		return employedAt;
	}
	
	
	public void setEmployedAt(String employedAt) {
		this.employedAt = employedAt;
	}
	
	
	@Column(name = "employed_since")
	public String getEmployedSince() {
		return employedSince;
	}
	public void setEmployedSince(String employedSince) {
		this.employedSince = employedSince;
	}
	
	
	
}
