package com.nexera.common.vo;

import java.io.Serializable;

public class CustomerSpouseOtherAccountDetailsVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int      id;
	private String accountSubType;
	private String   currentAccountBalance;
	private String   amountForNewHome;
	private CustomerSpouseOtherAccountDetailsVO customerSpouseOtherAccountDetails;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountSubType() {
		return accountSubType;
	}
	public void setAccountSubType(String accountSubType) {
		this.accountSubType = accountSubType;
	}
	public String getCurrentAccountBalance() {
		return currentAccountBalance;
	}
	public void setCurrentAccountBalance(String currentAccountBalance) {
		this.currentAccountBalance = currentAccountBalance;
	}
	public String getAmountForNewHome() {
		return amountForNewHome;
	}
	public void setAmountForNewHome(String amountForNewHome) {
		this.amountForNewHome = amountForNewHome;
	}
	public CustomerSpouseOtherAccountDetailsVO getCustomerSpouseOtherAccountDetails() {
		return customerSpouseOtherAccountDetails;
	}
	public void setCustomerSpouseOtherAccountDetails(
			CustomerSpouseOtherAccountDetailsVO customerSpouseOtherAccountDetails) {
		this.customerSpouseOtherAccountDetails = customerSpouseOtherAccountDetails;
	}
	
	
}


