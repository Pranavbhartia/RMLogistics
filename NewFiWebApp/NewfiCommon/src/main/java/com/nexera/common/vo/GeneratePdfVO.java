package com.nexera.common.vo;

import java.io.Serializable;

import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.common.vo.lqb.TeaserRateVO;

public class GeneratePdfVO implements Serializable{
	
	private LqbTeaserRateVo lqbTeaserRateUnderQuickQuote;
	private TeaserRateVO inputCustmerDetailUnderQuickQuote;
	private String loanProgram;
	private String RateAndApr;
	private Integer userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNo;
	private String pdfUrl;
	private String impounds;
	private TeaserRateResponseVO teaserRateVO;

	private String principalInterest;
	
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

	public TeaserRateVO getInputCustmerDetailUnderQuickQuote() {
		return inputCustmerDetailUnderQuickQuote;
	}

	public void setInputCustmerDetailUnderQuickQuote(TeaserRateVO inputCustmerDetailUnderQuickQuote) {
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

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public String getImpounds() {
		return impounds;
	}

	public void setImpounds(String impounds) {
		this.impounds = impounds;
	}

	public TeaserRateResponseVO getTeaserRateVO() {
		return teaserRateVO;
	}

	public void setTeaserRateVO(TeaserRateResponseVO teaserRateVO) {
		this.teaserRateVO = teaserRateVO;
		
	}
	public String getPrincipalInterest() {
		return principalInterest;
	}

	public void setPrincipalInterest(String principalInterest) {
		this.principalInterest = principalInterest;
	}
}
