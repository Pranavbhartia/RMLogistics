package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable, UserDetails {
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
	private UserRole userRole;

	private List<CustomerDetail> customerDetails;
	private List<InternalUserDetail> internalUserDetails;
	private List<RealtorDetail> realtorDetails;
	private List<Loan> loans;
	private List<LoanAppForm> loanAppForms;
	private List<LoanNotification> loanNotifications;
	private List<LoanTeam> loanTeams;
	private List<UserEmail> userEmails;

	@Transient
	private boolean accountNonExpired = true;
	@Transient
	private boolean accountNonLocked = true;
	@Transient
	private boolean credentialsNonExpired = true;
	@Transient
	private boolean enabled = true;
	@Transient
	private GrantedAuthority[] authorities;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "status")
	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Column(name = "email_id")
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone_number")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "photo_image_url")
	public String getPhotoImageUrl() {
		return this.photoImageUrl;
	}

	public void setPhotoImageUrl(String photoImageUrl) {
		this.photoImageUrl = photoImageUrl;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<CustomerDetail> getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(List<CustomerDetail> customerDetails) {
		this.customerDetails = customerDetails;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<InternalUserDetail> getInternalUserDetails() {
		return internalUserDetails;
	}

	public void setInternalUserDetails(
			List<InternalUserDetail> internalUserDetails) {
		this.internalUserDetails = internalUserDetails;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<RealtorDetail> getRealtorDetails() {
		return realtorDetails;
	}

	public void setRealtorDetails(List<RealtorDetail> realtorDetails) {
		this.realtorDetails = realtorDetails;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<LoanAppForm> getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(List<LoanAppForm> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	@OneToMany(mappedBy="createdFor",fetch=FetchType.LAZY)
	public List<LoanNotification> getLoanNotifications() {
		return loanNotifications;
	}

	public void setLoanNotifications(List<LoanNotification> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<LoanTeam> getLoanTeams() {
		return loanTeams;
	}

	public void setLoanTeams(List<LoanTeam> loanTeams) {
		this.loanTeams = loanTeams;
	}

	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	public List<UserEmail> getUserEmails() {
		return userEmails;
	}

	public void setUserEmails(List<UserEmail> userEmails) {
		this.userEmails = userEmails;
	}

	// Spring security related methods
	public User(String emailId, String password, String firstName,
			String lastName) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		// TODO: This is currently hard coded, we need to determine this based
		// on the user type
		this.authorities = new GrantedAuthority[] { new GrantedAuthorityImpl(
				"ROLE_CUSTOMER") };
	}

	public User() {
		super();
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	// bi-directional many-to-one association to UserRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_role")
	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}