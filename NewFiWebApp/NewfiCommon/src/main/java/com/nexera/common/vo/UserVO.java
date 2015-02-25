package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean status;
	private String emailId;
	private String firstName;
	private String lastName;
	private String password;
	private String phoneNumber;
	private String photoImageUrl;
	private String username;
	private UserRoleVO userRole;

	private List<CustomerDetailVO> customerDetails;
	private List<InternalUserDetailVO> internalUserDetails;
	private List<RealtorDetailVO> realtorDetails;
	private List<LoanVO> loans;
	private List<LoanAppFormVO> loanAppForms;
	private List<LoanNotificationVO> loanNotifications;
	private List<LoanTeamVO> loanTeams;
	private List<UserEmailVO> userEmails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhotoImageUrl() {
		return photoImageUrl;
	}
	public void setPhotoImageUrl(String photoImageUrl) {
		this.photoImageUrl = photoImageUrl;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UserRoleVO getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleVO userRole) {
		this.userRole = userRole;
	}
	public List<CustomerDetailVO> getCustomerDetails() {
		return customerDetails;
	}
	public void setCustomerDetails(List<CustomerDetailVO> customerDetails) {
		this.customerDetails = customerDetails;
	}
	public List<InternalUserDetailVO> getInternalUserDetails() {
		return internalUserDetails;
	}
	public void setInternalUserDetails(
			List<InternalUserDetailVO> internalUserDetails) {
		this.internalUserDetails = internalUserDetails;
	}
	public List<RealtorDetailVO> getRealtorDetails() {
		return realtorDetails;
	}
	public void setRealtorDetails(List<RealtorDetailVO> realtorDetails) {
		this.realtorDetails = realtorDetails;
	}
	public List<LoanVO> getLoans() {
		return loans;
	}
	public void setLoans(List<LoanVO> loans) {
		this.loans = loans;
	}
	public List<LoanAppFormVO> getLoanAppForms() {
		return loanAppForms;
	}
	public void setLoanAppForms(List<LoanAppFormVO> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}
	public List<LoanNotificationVO> getLoanNotifications() {
		return loanNotifications;
	}
	public void setLoanNotifications(List<LoanNotificationVO> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}
	public List<LoanTeamVO> getLoanTeams() {
		return loanTeams;
	}
	public void setLoanTeams(List<LoanTeamVO> loanTeams) {
		this.loanTeams = loanTeams;
	}
	public List<UserEmailVO> getUserEmails() {
		return userEmails;
	}
	public void setUserEmails(List<UserEmailVO> userEmails) {
		this.userEmails = userEmails;
	}


}