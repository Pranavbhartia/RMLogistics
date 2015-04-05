package com.nexera.extractor.entity;

public class ProductPointRate {

	
	private String rate;
	private String point;
	private String seperator;
	private String product;
	
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "ProductPointRate [rate=" + rate + ", point=" + point
				+ ", product=" + product + "]";
	}
	public String getSeperator() {
		return seperator;
	}
	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}
	
		
}
