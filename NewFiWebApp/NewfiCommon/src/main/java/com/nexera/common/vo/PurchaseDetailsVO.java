package com.nexera.common.vo;

import java.io.Serializable;

public class PurchaseDetailsVO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String livingSituation;
	private String  housePrice;
	private String  loanAmount;
	private boolean  isTaxAndInsuranceInLoanAmt;
	private String  estimatedPrice;
	private String  buyhomeZipPri;
	private String  buyhomeZipSec;
	private String  buyhomeZipTri;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLivingSituation() {
		return livingSituation;
	}
	public void setLivingSituation(String livingSituation) {
		this.livingSituation = livingSituation;
	}
	public String getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public boolean isTaxAndInsuranceInLoanAmt() {
		return isTaxAndInsuranceInLoanAmt;
	}
	public void setTaxAndInsuranceInLoanAmt(boolean isTaxAndInsuranceInLoanAmt) {
		this.isTaxAndInsuranceInLoanAmt = isTaxAndInsuranceInLoanAmt;
	}
	public String getEstimatedPrice() {
		return estimatedPrice;
	}
	public void setEstimatedPrice(String estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}
	public String getBuyhomeZipPri() {
		return buyhomeZipPri;
	}
	public void setBuyhomeZipPri(String buyhomeZipPri) {
		this.buyhomeZipPri = buyhomeZipPri;
	}
	public String getBuyhomeZipSec() {
		return buyhomeZipSec;
	}
	public void setBuyhomeZipSec(String buyhomeZipSec) {
		this.buyhomeZipSec = buyhomeZipSec;
	}
	public String getBuyhomeZipTri() {
		return buyhomeZipTri;
	}
	public void setBuyhomeZipTri(String buyhomeZipTri) {
		this.buyhomeZipTri = buyhomeZipTri;
	}
	
	
	
}
