package com.nexera.common.vo;

import java.io.Serializable;

public class LoanLockRateVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sLoanNumber;
	private String IlpTemplateId;
	private String requestedRate;
	private String requestedFee;
	private Integer loanId;
	private String rateVo;
	
	public String getsLoanNumber() {
		return sLoanNumber;
	}
	public void setsLoanNumber(String sLoanNumber) {
		this.sLoanNumber = sLoanNumber;
	}
	public String getIlpTemplateId() {
		return IlpTemplateId;
	}
	public void setIlpTemplateId(String ilpTemplateId) {
		IlpTemplateId = ilpTemplateId;
	}
	public String getRequestedRate() {
		return requestedRate;
	}
	public void setRequestedRate(String requestedRate) {
		this.requestedRate = requestedRate;
	}
	public String getRequestedFee() {
		return requestedFee;
	}
	public void setRequestedFee(String requestedFee) {
		this.requestedFee = requestedFee;
	}
	public Integer getLoanId() {
		return loanId;
	}
	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}
	public String getRateVo() {
	    return rateVo;
    }
	public void setRateVo(String rateVo) {
	    this.rateVo = rateVo;
    }
	

	
	
}
