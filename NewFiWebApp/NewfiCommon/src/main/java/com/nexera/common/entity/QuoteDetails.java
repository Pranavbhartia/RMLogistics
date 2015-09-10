package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.nexera.common.compositekey.QuoteCompositeKey;

@Entity
@Table(name="quotedetails")
@NamedQuery(name = "QuoteDetails.findAll", query = "SELECT qd FROM QuoteDetails qd")
public class QuoteDetails implements Serializable{
	private Integer id;
	private QuoteCompositeKey quoteCompositeKey;
	private String prospectFirstName;
	private String prospectLastName;
	private String emailId;
	private String phoneNo;
	private String lqbRateJson;
	private String inputDetailsJson;
	private String pdfUrl;
	private String rateAndApr;
	private String loanProgram;
	private Date createdDate;
	

	
	@Column(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@EmbeddedId
	public QuoteCompositeKey getQuoteCompositeKey() {
		return quoteCompositeKey;
	}
	public void setQuoteCompositeKey(QuoteCompositeKey quoteCompositeKey) {
		this.quoteCompositeKey = quoteCompositeKey;
	}
	
	@Column(name = "prospect_first_name")
	public String getProspectFirstName() {
		return prospectFirstName;
	}
	public void setProspectFirstName(String prospectFirstName) {
		this.prospectFirstName = prospectFirstName;
	}
	
	@Column(name = "prospect_last_name")
	public String getProspectLastName() {
		return prospectLastName;
	}
	public void setProspectLastName(String prospectLastName) {
		this.prospectLastName = prospectLastName;
	}
	
	
	@Column(name = "emailid")
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Column(name = "phoneno")
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	@Column(name = "lqb_rate_json")
	public String getLqbRateJson() {
		return lqbRateJson;
	}
	public void setLqbRateJson(String lqbRateJson) {
		this.lqbRateJson = lqbRateJson;
	}
	
	@Column(name = "input_details_json")
	public String getInputDetailsJson() {
		return inputDetailsJson;
	}
	public void setInputDetailsJson(String inputDetailsJson) {
		this.inputDetailsJson = inputDetailsJson;
	}
	
	@Column(name = "pdf_url")
	public String getPdfUrl() {
		return pdfUrl;
	}
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	@Column(name = "created_date")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name = "rateandapr")
	public String getRateAndApr() {
		return rateAndApr;
	}
	public void setRateAndApr(String rateAndApr) {
		this.rateAndApr = rateAndApr;
	}
	
	@Column(name = "loanprogram")
	public String getLoanProgram() {
		return loanProgram;
	}
	public void setLoanProgram(String loanProgram) {
		this.loanProgram = loanProgram;
	}
	
	
	
}
