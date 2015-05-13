package com.nexera.common.vo;

import com.nexera.common.entity.RefinanceDetails;

public class RefinanceVO {

	private Integer id;
	private String refinanceOption;
	private String currentMortgageBalance;
	private String currentMortgagePayment;
	private Boolean includeTaxes;
	private String secondMortageBalance;
	private String mortgageyearsleft;
	private String cashTakeOut;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public Boolean isIncludeTaxes() {
		return includeTaxes;
	}
	public void setIncludeTaxes(Boolean includeTaxes) {
		this.includeTaxes = includeTaxes;
	}
	
	public String getSecondMortageBalance() {
		return secondMortageBalance;
	}
	public void setSecondMortageBalance(String secondMortageBalance) {
		this.secondMortageBalance = secondMortageBalance;
	}
	public String getMortgageyearsleft() {
		return mortgageyearsleft;
	}
	public void setMortgageyearsleft(String mortgageyearsleft) {
		this.mortgageyearsleft = mortgageyearsleft;
	}
	public String getCashTakeOut() {
		return cashTakeOut;
	}
	public void setCashTakeOut(String cashTakeOut) {
		this.cashTakeOut = cashTakeOut;
	}
	public RefinanceDetails convertToEntity() {
		RefinanceDetails refinance = new RefinanceDetails();

		

		return refinance;

	}
	
	
}
