package com.nexera.common.vo.lqb;

public class LQBDocumentVO {

	private String sTicket ;
	private String  sLNm ;
	private String documentType;
	private String notes;
	private String sDataContent ;
	
	
	
	@Override
	public String toString() {
		return "LQBDocumentVO [sTicket=" + sTicket + ", sLNm=" + sLNm
				+ ", documentType=" + documentType + ", notes=" + notes
				+ ", sDataContent=" + sDataContent + "]";
	}
	public String getsTicket() {
		return sTicket;
	}
	public void setsTicket(String sTicket) {
		this.sTicket = sTicket;
	}
	public String getsLNm() {
		return sLNm;
	}
	public void setsLNm(String sLNm) {
		this.sLNm = sLNm;
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
	
}
