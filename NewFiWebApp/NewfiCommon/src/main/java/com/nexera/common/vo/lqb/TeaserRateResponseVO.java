package com.nexera.common.vo.lqb;

import java.util.ArrayList;

public class TeaserRateResponseVO {

	private String loanDuration;
	private String loanNumber;
	private ArrayList<LqbTeaserRateVo> rateVO;
	private String responseTime;
	
	public String getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	

	public ArrayList<LqbTeaserRateVo> getRateVO() {
		return rateVO;
	}
	public void setRateVO(ArrayList<LqbTeaserRateVo> rateVO) {
		this.rateVO = rateVO;
	}
	public String getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	public String getResponseTime() {
	    return responseTime;
    }
	public void setResponseTime(String responseTime) {
	    this.responseTime = responseTime;
    }
	
}