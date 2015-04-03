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
* The persistent class for the CustomerRetirementAccountDetails database table.
* 
*/

@Entity
@Table(name = "customerretirementaccountdetails")
@NamedQuery(name = "CustomerRetirementAccountDetails.findAll", query = "SELECT cra FROM CustomerRetirementAccountDetails cra")
public class CustomerRetirementAccountDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String accountSubType;
	private String   currentaccountbalance;
	private String   amountfornewhome;
	
	
	public CustomerRetirementAccountDetails() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	@Column(name = "account_sub_type")
	public String getAccountSubType() {
		return accountSubType;
	}
	

	public void setAccountSubType(String accountSubType) {
		this.accountSubType = accountSubType;
	}
	
	@Column(name = "current_account_balance")
	public String getCurrentaccountbalance() {
		return currentaccountbalance;
	}
	public void setCurrentaccountbalance(String currentaccountbalance) {
		this.currentaccountbalance = currentaccountbalance;
	}
	
	@Column(name = "amount_for_new_home")
	public String getAmountfornewhome() {
		return amountfornewhome;
	}
	public void setAmountfornewhome(String amountfornewhome) {
		this.amountfornewhome = amountfornewhome;
	}
	
	
	
	
}
