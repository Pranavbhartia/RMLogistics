package com.nexera.common.vo.lqb;

import java.util.ArrayList;

public class TeaserRateResponseVO {

	private String loanDuration;
	private ArrayList<LqbTeaserRateVo> rateVO;
	
	
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
	
}