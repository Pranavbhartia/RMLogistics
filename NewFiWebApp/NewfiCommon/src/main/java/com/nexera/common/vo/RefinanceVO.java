package com.nexera.common.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nexera.common.entity.RefinanceDetails;
import com.nexera.common.entity.User;

public class RefinanceVO {

	private int id;
	private String refinanceOption;
	private String currentMortgageBalance;
	private String currentMortgagePayment;
	private boolean includeTaxes;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRefinanceOption() {
		return refinanceOption;
	}
	public void setRefinanceOption(String refinanceOption) {
		this.refinanceOption = refinanceOption;
	}
	public String getCurrentMortgageBalance() {
		return currentMortgageBalance;
	}
	public void setCurrentMortgageBalance(String currentMortgageBalance) {
		this.currentMortgageBalance = currentMortgageBalance;
	}
	public String getCurrentMortgagePayment() {
		return currentMortgagePayment;
	}
	public void setCurrentMortgagePayment(String currentMortgagePayment) {
		this.currentMortgagePayment = currentMortgagePayment;
	}
	public boolean isIncludeTaxes() {
		return includeTaxes;
	}
	public void setIncludeTaxes(boolean includeTaxes) {
		this.includeTaxes = includeTaxes;
	}
	

	
	public RefinanceDetails convertToEntity() {
		RefinanceDetails refinance = new RefinanceDetails();

		

		return refinance;

	}
	
	
}
