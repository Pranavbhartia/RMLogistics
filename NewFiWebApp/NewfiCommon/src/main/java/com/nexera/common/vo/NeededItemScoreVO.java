package com.nexera.common.vo;

public class NeededItemScoreVO {

	private Integer neededItemRequired;
	private Integer totalSubmittedItem;
	
	
	
	public Integer getNeededItemRequired() {
		return neededItemRequired;
	}
	public void setNeededItemRequired(Integer neededItemRequired) {
		this.neededItemRequired = neededItemRequired;
	}
	
	@Override
	public String toString() {
		return "NeededItemScoreVO [neededItemRequired=" + neededItemRequired
				+ ", totalNeededItem=" + totalSubmittedItem + "]";
	}
	public Integer getTotalSubmittedItem() {
		return totalSubmittedItem;
	}
	public void setTotalSubmittedItem(Integer totalSubmittedItem) {
		this.totalSubmittedItem = totalSubmittedItem;
	}
	
	
}
