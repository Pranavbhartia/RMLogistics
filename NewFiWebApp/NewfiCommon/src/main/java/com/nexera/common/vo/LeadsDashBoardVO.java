package com.nexera.common.vo;

import java.util.Date;
import java.util.List;

public class LeadsDashBoardVO implements Comparable<LeadsDashBoardVO>{

	private String name;
	private String prof_image;
	private String time;
	private String phone_no;
	private String purpose;
	private String processor;
	private String credit_score;
	private String alert_count;
	private String role;
	private Date loanInitiatedOn;
	private Date lastActedOn;
	private Integer userID;
	private Integer loanID;
	private String lqbFileId;
	private String lockedRateData;
	private String loanStatus;
	private String lqbLoanStatus;
	private List<AlertListVO> alerts;
	private List<NotesVO> notes;
		
	private String firstName;
	private String lastName;
	private String emailId;
	private String carrierInfo;
	private Boolean mobileAlertsPreference;
	private CustomerDetailVO customerDetail;
	private Date userLastLoginTime;
	
	private QuoteDetailsVO quoteDetailsVO;

	public QuoteDetailsVO getQuoteDetailsVO() {
		return quoteDetailsVO;
	}

	public void setQuoteDetailsVO(QuoteDetailsVO quoteDetailsVO) {
		this.quoteDetailsVO = quoteDetailsVO;
	}

	public Date getUserLastLoginTime() {
		return userLastLoginTime;
	}

	public void setUserLastLoginTime(Date userLastLoginTime) {
		this.userLastLoginTime = userLastLoginTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProf_image() {
		return prof_image;
	}

	public void setProf_image(String prof_image) {
		this.prof_image = prof_image;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public String getCredit_score() {
		return credit_score;
	}

	public void setCredit_score(String credit_score) {
		this.credit_score = credit_score;
	}

	public String getAlert_count() {
		return alert_count;
	}

	public void setAlert_count(String alert_count) {
		this.alert_count = alert_count;
	}

	public List<AlertListVO> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<AlertListVO> alerts) {
		this.alerts = alerts;
	}

	public List<NotesVO> getNotes() {
		return notes;
	}

	public void setNotes(List<NotesVO> notes) {
		this.notes = notes;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getLoanID() {
		return loanID;
	}

	public void setLoanID(Integer loanID) {
		this.loanID = loanID;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getLoanInitiatedOn() {
		return loanInitiatedOn;
	}

	public void setLoanInitiatedOn(Date loanInitiatedOn) {
		this.loanInitiatedOn = loanInitiatedOn;
	}

	public Date getLastActedOn() {
		return lastActedOn;
	}

	public void setLastActedOn(Date lastActedOn) {
		this.lastActedOn = lastActedOn;
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

	public CustomerDetailVO getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetailVO customerDetail) {
		this.customerDetail = customerDetail;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getLoanStatus() {
	    return loanStatus;
    }

	public void setLoanStatus(String loanStatus) {
	    this.loanStatus = loanStatus;
    }

	public String getLqbFileId() {
	    return lqbFileId;
    }

	public void setLqbFileId(String lqbFileId) {
	    this.lqbFileId = lqbFileId;
    }

	public String getCarrierInfo() {
	    return carrierInfo;
    }

	public void setCarrierInfo(String carrierInfo) {
	    this.carrierInfo = carrierInfo;
    }

	public Boolean getMobileAlertsPreference() {
	    return mobileAlertsPreference;
    }

	public void setMobileAlertsPreference(Boolean mobileAlertsPreference) {
	    this.mobileAlertsPreference = mobileAlertsPreference;
    }

	public String getLockedRateData() {
	    return lockedRateData;
    }

	public void setLockedRateData(String lockedRateData) {
	    this.lockedRateData = lockedRateData;
    }

	public String getLqbLoanStatus() {
	    return lqbLoanStatus;
    }

	public void setLqbLoanStatus(String lqbLoanStatus) {
	    this.lqbLoanStatus = lqbLoanStatus;
    }

	
	@Override
    public int compareTo(LeadsDashBoardVO o) {
		if (getLastActedOn() == null || o.getLastActedOn() == null)
		      return 0;
		 return getLastActedOn().compareTo(o.getLastActedOn());
    }

	
}


