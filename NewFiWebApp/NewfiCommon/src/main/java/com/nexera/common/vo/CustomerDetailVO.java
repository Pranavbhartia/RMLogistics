package com.nexera.common.vo;

import java.io.Serializable;

import com.nexera.common.entity.CustomerDetail;

public class CustomerDetailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String addressCity;
	private String addressState;
	private String addressZipCode;
	private Long dateOfBirth;
	private Integer profileCompletionStatus;
	private String ssn;
	private String secEmailId;
	private String secPhoneNumber;
	private Integer subscriptionsStatus;
	private Boolean mobileAlertsPreference;

	private UserVO user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressState() {
		return addressState;
	}

	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}

	public String getAddressZipCode() {
		return addressZipCode;
	}

	public void setAddressZipCode(String addressZipCode) {
		this.addressZipCode = addressZipCode;
	}

	public Long getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getProfileCompletionStatus() {
		return profileCompletionStatus;
	}

	public void setProfileCompletionStatus(Integer profileCompletionStatus) {
		this.profileCompletionStatus = profileCompletionStatus;
	}

	public String getSecEmailId() {
		return secEmailId;
	}

	public void setSecEmailId(String secEmailId) {
		this.secEmailId = secEmailId;
	}

	public String getSecPhoneNumber() {
		return secPhoneNumber;
	}

	public void setSecPhoneNumber(String secPhoneNumber) {
		this.secPhoneNumber = secPhoneNumber;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public Integer getSubscriptionsStatus() {
		return subscriptionsStatus;
	}

	public void setSubscriptionsStatus(Integer subscriptionsStatus) {
		this.subscriptionsStatus = subscriptionsStatus;
	}

	public Boolean getMobileAlertsPreference() {
		return mobileAlertsPreference;
	}

	public void setMobileAlertsPreference(Boolean mobileAlertsPreference) {
		this.mobileAlertsPreference = mobileAlertsPreference;
	}

	public static CustomerDetailVO convertFromEntityToVO(
	        CustomerDetail customerDetail) {

		if (customerDetail == null)
			return null;

		CustomerDetailVO customerDetailVO = new CustomerDetailVO();

		customerDetailVO.setId(customerDetail.getId());
		customerDetailVO.setAddressCity(customerDetail.getAddressCity());
		customerDetailVO.setAddressState(customerDetail.getAddressState());
		customerDetailVO.setAddressZipCode(customerDetail.getAddressZipCode());
		if (null != customerDetail.getDateOfBirth())
			customerDetailVO.setDateOfBirth(customerDetail.getDateOfBirth()
			        .getTime());
		customerDetailVO.setProfileCompletionStatus(customerDetail
		        .getProfileCompletionStatus());
		customerDetailVO.setSsn(customerDetail.getSsn());
		customerDetailVO.setSecEmailId(customerDetail.getSecEmailId());
		customerDetailVO.setSecPhoneNumber(customerDetail.getSecPhoneNumber());
		customerDetailVO.setSubscriptionsStatus(customerDetail
		        .getSubscriptionsStatus());

		return customerDetailVO;

	}


}