package com.nexera.common.vo;

import java.util.Date;

public class QuoteDetailsVO {

	private Integer id;
	private String prospectFirstName;
	private String prospectLastName;
	private String prospectUsername;
	private Integer internalUserId;
	private String lqbRateJson;
	private String inputDetailsJson;
	private String pdfUrl;
	private Date createdDate;
	private String emailId;
	private String phoneNo;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProspectFirstName() {
		return prospectFirstName;
	}

	public void setProspectFirstName(String prospectFirstName) {
		this.prospectFirstName = prospectFirstName;
	}

	public String getProspectLastName() {
		return prospectLastName;
	}

	public void setProspectLastName(String prospectLastName) {
		this.prospectLastName = prospectLastName;
	}

	public String getProspectUsername() {
		return prospectUsername;
	}

	public void setProspectUsername(String prospectUsername) {
		this.prospectUsername = prospectUsername;
	}

	public Integer getInternalUserId() {
		return internalUserId;
	}

	public void setInternalUserId(Integer internalUserId) {
		this.internalUserId = internalUserId;
	}

	public String getLqbRateJson() {
		return lqbRateJson;
	}

	public void setLqbRateJson(String lqbRateJson) {
		this.lqbRateJson = lqbRateJson;
	}

	public String getInputDetailsJson() {
		return inputDetailsJson;
	}

	public void setInputDetailsJson(String inputDetailsJson) {
		this.inputDetailsJson = inputDetailsJson;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
