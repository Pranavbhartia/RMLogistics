package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the customerdetails database table.
 * 
 */
@Entity
@Table(name = "customerdetails")
@NamedQuery(name = "CustomerDetail.findAll", query = "SELECT c FROM CustomerDetail c")
public class CustomerDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String addressCity;
	private String addressState;
	private String addressZipCode;
	private Date dateOfBirth;
	private String ssn;
	private Integer profileCompletionStatus;
	private String secEmailId;
	private String secPhoneNumber;
	private Integer subscriptionsStatus;
	

	public CustomerDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "address_city")
	public String getAddressCity() {
		return this.addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	@Column(name = "address_state")
	public String getAddressState() {
		return this.addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	@Column(name = "address_zip_code")
	public String getAddressZipCode() {
		return this.addressZipCode;
	}

	public void setAddressZipCode(String addressZipCode) {
		this.addressZipCode = addressZipCode;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Column(name = "ssn")
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Column(name = "profile_completion_status")
	public Integer getProfileCompletionStatus() {
		return profileCompletionStatus;
	}

	public void setProfileCompletionStatus(Integer profileCompletionStatus) {
		this.profileCompletionStatus = profileCompletionStatus;
	}

	@Column(name = "sec_email_id")
	public String getSecEmailId() {
		return this.secEmailId;
	}

	public void setSecEmailId(String secEmailId) {
		this.secEmailId = secEmailId;
	}

	@Column(name = "sec_phone_number")
	public String getSecPhoneNumber() {
		return this.secPhoneNumber;
	}

	public void setSecPhoneNumber(String secPhoneNumber) {
		this.secPhoneNumber = secPhoneNumber;
	}

	@Column(name = "subscriptionsStatus")
	public Integer getSubscriptionsStatus() {
		return subscriptionsStatus;
	}

	public void setSubscriptionsStatus(Integer subscriptionsStatus) {
		this.subscriptionsStatus = subscriptionsStatus;
	}

	

}