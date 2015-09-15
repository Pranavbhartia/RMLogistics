package com.nexera.common.vo;

public class DashboardCriteriaVO {

	private Integer userID;
	private String  orderByType;
	private String  columnName;
	private Integer startLimit;
	private Integer endLimit;
	
	public Integer getStartLimit() {
		return startLimit;
	}
	public void setStartLimit(Integer startLimit) {
		this.startLimit = startLimit;
	}
	public Integer getEndLimit() {
		return endLimit;
	}
	public void setEndLimit(Integer endLimit) {
		this.endLimit = endLimit;
	}
	public String getOrderByType() {
		return orderByType;
	}
	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer getUserID() {
	    return userID;
    }
	public void setUserID(Integer userID) {
	    this.userID = userID;
    }
}
