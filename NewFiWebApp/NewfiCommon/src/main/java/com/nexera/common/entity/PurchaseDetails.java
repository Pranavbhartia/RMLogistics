package com.nexera.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "purchasedetails")
@NamedQuery(name = "PurchaseDetails.findAll", query = "SELECT l FROM PurchaseDetails l")
public class PurchaseDetails {
	
	private Integer id;
	private String livingSituation;
	private String  housePrice;
	private String  loanAmount;
	private boolean  isTaxAndInsuranceInLoanAmt;
	private String  estimatedPrice;
	private String  buyhomeZipPri;
	private String  buyhomeZipSec;
	private String  buyhomeZipTri;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "livingSituation")
	public String getLivingSituation() {
		return livingSituation;
	}
	public void setLivingSituation(String livingSituation) {
		this.livingSituation = livingSituation;
	}
	
	@Column(name = "housePrice")
	public String getHousePrice() {
		return housePrice;
	}
	public void setHousePrice(String housePrice) {
		this.housePrice = housePrice;
	}
	
	@Column(name = "loanAmount")
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	
	
	@Column(name = "isTaxAndInsuranceInLoanAmt", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isTaxAndInsuranceInLoanAmt() {
		return isTaxAndInsuranceInLoanAmt;
	}
	public void setTaxAndInsuranceInLoanAmt(boolean isTaxAndInsuranceInLoanAmt) {
		this.isTaxAndInsuranceInLoanAmt = isTaxAndInsuranceInLoanAmt;
	}
	
	@Column(name = "estimatedPrice")
	public String getEstimatedPrice() {
		return estimatedPrice;
	}
	public void setEstimatedPrice(String estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}
	
	@Column(name = "buyhomeZip1")
	public String getBuyhomeZipPri() {
		return buyhomeZipPri;
	}
	public void setBuyhomeZipPri(String buyhomeZipPri) {
		this.buyhomeZipPri = buyhomeZipPri;
	}
	
	@Column(name = "buyhomeZip2")
	public String getBuyhomeZipSec() {
		return buyhomeZipSec;
	}
	public void setBuyhomeZipSec(String buyhomeZipSec) {
		this.buyhomeZipSec = buyhomeZipSec;
	}
	
	@Column(name = "buyhomeZip3")
	public String getBuyhomeZipTri() {
		return buyhomeZipTri;
	}
	public void setBuyhomeZipTri(String buyhomeZipTri) {
		this.buyhomeZipTri = buyhomeZipTri;
	}
	
	
	

}
