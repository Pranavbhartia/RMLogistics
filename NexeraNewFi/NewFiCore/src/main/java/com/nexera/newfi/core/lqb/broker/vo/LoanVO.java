package com.nexera.newfi.core.lqb.broker.vo;

public class LoanVO {

	private String sTicket;

	private String sXmlData;

	private String sTemplateName;

	private String sLoanNumber;

	private String sDataContent;

	private String format;

	private String sXmlQuery;

	private String IlpTemplateId;

	private String requestedRate;

	private String requestedFee;

	public String getsTicket() {
		return sTicket;
	}

	public void setsTicket(String sTicket) {
		this.sTicket = sTicket;
	}

	public String getsXmlData() {
		return sXmlData;
	}

	public void setsXmlData(String sXmlData) {
		this.sXmlData = sXmlData;
	}

	public String getsTemplateName() {
		return sTemplateName;
	}

	public void setsTemplateName(String sTemplateName) {
		this.sTemplateName = sTemplateName;
	}

	public String getsLoanNumber() {
		return sLoanNumber;
	}

	public void setsLoanNumber(String sLoanNumber) {
		this.sLoanNumber = sLoanNumber;
	}

	public String getsDataContent() {
		return sDataContent;
	}

	public void setsDataContent(String sDataContent) {
		this.sDataContent = sDataContent;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getsXmlQuery() {
		return sXmlQuery;
	}

	public void setsXmlQuery(String sXmlQuery) {
		this.sXmlQuery = sXmlQuery;
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
	
	

}
