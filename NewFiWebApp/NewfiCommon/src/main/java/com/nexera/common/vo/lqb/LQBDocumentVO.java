package com.nexera.common.vo.lqb;

public class LQBDocumentVO {

	private String sTicket ;
	private String  sLoanNumber ;
	private String documentType;
	private String notes;
	private String sDataContent ;
	
	private Integer loanId;
	private String fileName;
	
	
	
	public String getsTicket() {
		return sTicket;
	}
	public void setsTicket(String sTicket) {
		this.sTicket = sTicket;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getsDataContent() {
		return sDataContent;
	}
	public void setsDataContent(String sDataContent) {
		this.sDataContent = sDataContent;
	}
	public String getsLoanNumber() {
		return sLoanNumber;
	}
	public void setsLoanNumber(String sLoanNumber) {
		this.sLoanNumber = sLoanNumber;
	}
	public Integer getLoanId() {
	    return loanId;
    }
	public void setLoanId(Integer loanId) {
	    this.loanId = loanId;
    }
	public String getFileName() {
	    return fileName;
    }
	public void setFileName(String fileName) {
	    this.fileName = fileName;
    }
	
}
