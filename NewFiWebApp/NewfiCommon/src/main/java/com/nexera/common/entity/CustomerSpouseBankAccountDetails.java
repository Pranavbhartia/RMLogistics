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
* The persistent class for the CustomerSpouseBankAccountDetails database table.
* 
*/

@Entity
@Table(name = "customerspousebankaccountdetails")
@NamedQuery(name = "CustomerSpouseBankAccountDetails.findAll", query = "SELECT csba FROM CustomerSpouseBankAccountDetails csba")
public class CustomerSpouseBankAccountDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String accountSubType;
	private String   currentaccountbalance;
	private String   amountfornewhome;
	
	
	public CustomerSpouseBankAccountDetails() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
