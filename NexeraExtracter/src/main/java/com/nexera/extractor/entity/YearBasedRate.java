package com.nexera.extractor.entity;

public class YearBasedRate {

	
	private String point;
	private String fifteenYearRate;
	private String thirtyYearRate;
	private String fortyFiveYearRate;
	private String sixtyYearRate;
	
	
	
	@Override
	public String toString() {
		return "YearBasedRate [point=" + point + ", fifteenYearRate="
				+ fifteenYearRate + ", thirtyYearRate=" + thirtyYearRate
				+ ", fortyFiveYearRate=" + fortyFiveYearRate
				+ ", sixtyYearRate=" + sixtyYearRate + "]";
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getFifteenYearRate() {
		return fifteenYearRate;
	}
	public void setFifteenYearRate(String fifteenYearRate) {
		this.fifteenYearRate = fifteenYearRate;
	}
	public String getThirtyYearRate() {
		return thirtyYearRate;
	}
	public void setThirtyYearRate(String thirtyYearRate) {
		this.thirtyYearRate = thirtyYearRate;
	}
	public String getFortyFiveYearRate() {
		return fortyFiveYearRate;
	}
	public void setFortyFiveYearRate(String fortyFiveYearRate) {
		this.fortyFiveYearRate = fortyFiveYearRate;
	}
	public String getSixtyYearRate() {
		return sixtyYearRate;
	}
	public void setSixtyYearRate(String sixtyYearRate) {
		this.sixtyYearRate = sixtyYearRate;
	}
	
	
}