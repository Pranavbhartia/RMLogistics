package com.nexera.common.vo;

import java.util.List;

public class LeadsDashBoardVO {
	private List<QuoteDetailsVO> quoteDetails;
	
	public LoanDashboardVO LoanDetails;


	public LoanDashboardVO getLoanDetails() {
		return LoanDetails;
	}

	public void setLoanDetails(LoanDashboardVO loanDetails) {
		LoanDetails = loanDetails;
	}

	public List<QuoteDetailsVO> getQuoteDetails() {
		return quoteDetails;
	}

	public void setQuoteDetails(List<QuoteDetailsVO> quoteDetails) {
		this.quoteDetails = quoteDetails;
	}
}