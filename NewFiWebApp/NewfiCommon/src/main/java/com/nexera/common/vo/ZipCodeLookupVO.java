package com.nexera.common.vo;



public class ZipCodeLookupVO {

	private Integer id;
	private String zipcode;
	private String countyName;
	private String cityName;
	private StateLookupVO stateLookup;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public StateLookupVO getStateLookup() {
		return stateLookup;
	}
	public void setStateLookup(StateLookupVO stateLookup) {
		this.stateLookup = stateLookup;
	}
	
	
}
