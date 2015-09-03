package com.nexera.common.vo;

import java.io.Serializable;

import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateVO;

public class GeneratePdfVO implements Serializable{
	
	private LqbTeaserRateVo lqbTeaserRateUnderQuickQuote;
	private TeaserRateVO inputCustmerDetailUnderQuickQuote;
	private String loanProgram;
	private String RateAndApr;
	private Integer userId;
	private String firstName;
	private String lastName;
	
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRateAndApr() {
		return RateAndApr;
	}

	public void setRateAndApr(String rateAndApr) {
		RateAndApr = rateAndApr;
	}

	public String getLoanProgram() {
		return loanProgram;
	}

	public void setLoanProgram(String loanProgram) {
		this.loanProgram = loanProgram;
	}

	public TeaserRateVO getLoanPurchaseDetailsUnderQuickQuote() {
		return inputCustmerDetailUnderQuickQuote;
	}

	public void setLoanPurchaseDetailsUnderQuickQuote(TeaserRateVO inputCustmerDetailUnderQuickQuote) {
		this.inputCustmerDetailUnderQuickQuote = inputCustmerDetailUnderQuickQuote;
	}

	public LqbTeaserRateVo getLqbTeaserRateUnderQuickQuote() {
		return lqbTeaserRateUnderQuickQuote;
	}

	public void setLqbTeaserRateUnderQuickQuote(LqbTeaserRateVo lqbTeaserRateUnderQuickQuote) {
		this.lqbTeaserRateUnderQuickQuote = lqbTeaserRateUnderQuickQuote;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
}
