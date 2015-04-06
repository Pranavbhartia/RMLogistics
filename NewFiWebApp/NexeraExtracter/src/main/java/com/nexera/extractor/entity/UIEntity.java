package com.nexera.extractor.entity;

import java.math.BigDecimal;

public class UIEntity {

	private BigDecimal rate;
	private String col1Points; // 15 days
	private String col2Points; // 30 days
	private String col3Points; // 45 days
	private String col4Points; // 60 days

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getCol1Points() {
		if (col1Points == null || col1Points.isEmpty()) {
			return "-";
		}
		return col1Points;
	}

	public void setCol1Points(String col1Points) {
		this.col1Points = col1Points;
	}

	public String getCol2Points() {
		if (col2Points == null || col2Points.isEmpty()) {
			return "-";
		}
		return col2Points;
	}

	public void setCol2Points(String col2Points) {

		this.col2Points = col2Points;
	}

	public String getCol3Points() {
		if (col3Points == null || col3Points.isEmpty()) {
			return "-";
		}
		return col3Points;
	}

	public void setCol3Points(String col3Points) {
		this.col3Points = col3Points;
	}

	public String getCol4Points() {
		if (col4Points == null || col4Points.isEmpty()) {
			return "-";
		}
		return col4Points;
	}

	public void setCol4Points(String col4Points) {
		this.col4Points = col4Points;
	}

}
