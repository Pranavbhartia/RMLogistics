package com.nexera.common.vo;

import java.util.Date;

import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.QuoteDetails;

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
	private Boolean isCreated = false;
	private Boolean isDeleted = false;
	private String internalUserName;
	private Loan loan;

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loanVO) {
		this.loan = loanVO;
	}

	public String getInternalUserName() {
		return internalUserName;
	}

	public void setInternalUserName(String internalUserName) {
		this.internalUserName = internalUserName;
	}

	public Boolean getIsCreated() {
		return isCreated;
	}

	public void setIsCreated(Boolean isCreated) {
		this.isCreated = isCreated;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	/**
	 * @param quoteDetailsVO
	 * @return
	 */
	public static QuoteDetails convertVOToEntity(QuoteDetailsVO quoteDetailsVO) {

		QuoteDetails quoteDetails = new QuoteDetails();
		quoteDetails.setCreatedDate(quoteDetailsVO.getCreatedDate());
		quoteDetails.setEmailId(quoteDetailsVO.getEmailId());
		quoteDetails.setInputDetailsJson(quoteDetailsVO.getInputDetailsJson());
		QuoteCompositeKey quoteCompositeKey = new QuoteCompositeKey();
		quoteCompositeKey.setInternalUserId(quoteDetailsVO.getInternalUserId());
		quoteCompositeKey.setUserName(quoteDetailsVO.getProspectUsername());
		quoteDetails.setQuoteCompositeKey(quoteCompositeKey);
		quoteDetails.setLqbRateJson(quoteDetailsVO.getLqbRateJson());
		quoteDetails.setPdfUrl(quoteDetailsVO.getPdfUrl());
		quoteDetails.setPhoneNo(quoteDetailsVO.getPhoneNo());
		quoteDetails
		        .setProspectFirstName(quoteDetailsVO.getProspectFirstName());
		quoteDetails.setProspectLastName(quoteDetailsVO.getProspectLastName());
		quoteDetails.setIsCreated(quoteDetailsVO.getIsCreated());
		quoteDetails.setIsDeleted(quoteDetailsVO.getIsDeleted());
		quoteDetails.setId(quoteDetailsVO.getId());
		quoteDetails.setLoan(quoteDetailsVO.getLoan());
		return quoteDetails;
	}

	/**
	 * @param quoteDetails
	 * @return
	 */
	public static QuoteDetailsVO convertEntityToVO(QuoteDetails quoteDetails) {

		QuoteDetailsVO quoteDetailsVO = new QuoteDetailsVO();
		quoteDetailsVO.setCreatedDate(quoteDetails.getCreatedDate());
		quoteDetailsVO.setEmailId(quoteDetails.getEmailId());
		quoteDetailsVO.setInputDetailsJson(quoteDetails.getInputDetailsJson());
		quoteDetailsVO.setInternalUserId(quoteDetails.getQuoteCompositeKey()
		        .getInternalUserId());
		quoteDetailsVO.setLqbRateJson(quoteDetails.getLqbRateJson());
		quoteDetailsVO.setPdfUrl(quoteDetails.getPdfUrl());
		quoteDetailsVO.setPhoneNo(quoteDetails.getPhoneNo());
		quoteDetailsVO
		        .setProspectFirstName(quoteDetails.getProspectFirstName());
		quoteDetailsVO.setProspectLastName(quoteDetails.getProspectLastName());
		quoteDetailsVO.setIsCreated(quoteDetails.getIsCreated());
		quoteDetailsVO.setIsDeleted(quoteDetails.getIsDeleted());
		quoteDetailsVO.setProspectUsername(quoteDetails.getQuoteCompositeKey()
		        .getUserName());
		quoteDetailsVO.setId(quoteDetails.getId());
		quoteDetailsVO.setLoan(quoteDetails.getLoan());
		return quoteDetailsVO;
	}

}