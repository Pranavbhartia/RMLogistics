package com.nexera.extractor.entity;

import java.util.List;

public class FileProductPointRate {

	private String fileName;
	private List<ProductPointRate> productPointRate;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public List<ProductPointRate> getProductPointRate() {
		return productPointRate;
	}
	public void setProductPointRate(List<ProductPointRate> productPointRate) {
		this.productPointRate = productPointRate;
	}
	
}
