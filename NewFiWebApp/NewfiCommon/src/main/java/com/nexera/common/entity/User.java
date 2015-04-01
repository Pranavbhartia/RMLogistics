package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.UserVO;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "user")
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

	private CustomerDetail customerDetail;
	private InternalUserDetail internalUserDetail;
	private RealtorDetail realtorDetail;
	private List<Loan> loans;
	private List<LoanAppForm> loanAppForms;
	private List<Notification> loanNotifications;
	private List<LoanTeam> loanTeams;
	private List<UserEmail> userEmails;
	private List<TransactionDetails> transactionDetails;

	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;
	private GrantedAuthority[] authorities;
	private Locale userLocale;
	private String minutesOffset;
	private Boolean isProfileComplete;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "status", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_detail")
	public CustomerDetail getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetail customerDetail) {
		this.customerDetail = customerDetail;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "internal_user_detail")
	public InternalUserDetail getInternalUserDetail() {
		return internalUserDetail;
	}

	public void setInternalUserDetail(InternalUserDetail internalUserDetail) {
		this.internalUserDetail = internalUserDetail;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "realtor_detail")
	public RealtorDetail getRealtorDetail() {
		return realtorDetail;
	}

	public void setRealtorDetail(RealtorDetail realtorDetail) {
		this.realtorDetail = realtorDetail;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<LoanAppForm> getLoanAppForms() {
		return loanAppForms;
	}

	public void setLoanAppForms(List<LoanAppForm> loanAppForms) {
		this.loanAppForms = loanAppForms;
	}

	@OneToMany(mappedBy = "createdFor", fetch = FetchType.LAZY)
	public List<Notification> getLoanNotifications() {
		return loanNotifications;
	}

	public void setLoanNotifications(List<Notification> loanNotifications) {
		this.loanNotifications = loanNotifications;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<LoanTeam> getLoanTeams() {
		return loanTeams;
	}

	public void setLoanTeams(List<LoanTeam> loanTeams) {
		this.loanTeams = loanTeams;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
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

	public User(Integer userId) {
		// TODO Auto-generated constructor stub
		this.id = userId;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Transient
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Transient
	public String getMinutesOffset() {
		return minutesOffset;
	}

	public void setMinutesOffset(String minutesOffset) {
		this.minutesOffset = minutesOffset;
	}

	@Transient
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public void setAuthorities(GrantedAuthority[] authorities) {
		this.authorities = authorities;
	}

	@Transient
	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

	// bi-directional many-to-one association to UserRole
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_role")
	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	// bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy = "user")
	public List<TransactionDetails> getTransactionDetails() {
		return this.transactionDetails;
	}

	public void setTransactionDetails(
	        List<TransactionDetails> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	@Column(name = "is_profile_complete", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsProfileComplete() {
		return isProfileComplete;
	}

	public void setIsProfileComplete(Boolean isProfileComplete) {
		this.isProfileComplete = isProfileComplete;
	}

	public static UserVO convertFromEntityToVO(final User user) {
		UserVO userVO = new UserVO();
		if (user != null) {
			userVO.setFirstName(user.getFirstName());
			userVO.setLastName(user.getLastName());
			userVO.setPhoneNumber(user.getPhoneNumber());
			userVO.setPhotoImageUrl(user.getPhotoImageUrl());
			userVO.setPassword(user.getPassword());
			userVO.setId(user.getId());
			userVO.setUserRole(UserRole.convertFromEntityToVO(user
			        .getUserRole()));
			userVO.setEmailId(user.getEmailId());
			userVO.setDisplayName(user.firstName + " " + user.lastName);
			userVO.setEmailId(user.getEmailId());
			userVO.setDisplayName(user.getFirstName() + " "
			        + user.getLastName());
			userVO.setCustomerDetail(CustomerDetail.convertFromEntityToVO(user
			        .getCustomerDetail()));
			userVO.setInternalUserDetail(InternalUserDetail
			        .convertFromEntityToVO(user.getInternalUserDetail()));

		}
		return userVO;
	}

	public static User convertFromVOToEntity(UserVO userVO) {

		if (userVO == null)
			return null;
		User userModel = new User();

		userModel.setId(userVO.getId());
		userModel.setFirstName(userVO.getFirstName());
		userModel.setLastName(userVO.getLastName());
		
		userModel.setUsername(userVO.getEmailId());
		userModel.setEmailId(userVO.getEmailId());
		
//		if (userVO.getEmailId() != null) {
//			userModel.setUsername(userVO.getEmailId().split(":")[0]);
//			userModel.setEmailId(userVO.getEmailId().split(":")[0]);
//		}
		//userModel.setPassword(userVO.getPassword());

		userModel.setStatus(true);

		userModel.setPhoneNumber(userVO.getPhoneNumber());
		userModel.setPhotoImageUrl(userVO.getPhotoImageUrl());

		userModel.setUserRole(UserRole.convertFromVOToEntity(userVO
		        .getUserRole()));
		if (userModel.getUserRole().getId() == UserRolesEnum.CUSTOMER
		        .getRoleId()) {
			
			userModel.setCustomerDetail(CustomerDetail
			        .convertFromVOToEntity(userVO.getCustomerDetail()));
		}
		userModel.setInternalUserDetail(InternalUserDetail
		        .convertFromVOToEntity(userVO.getInternalUserDetail()));
		if (userModel.getUserRole().getId() == UserRolesEnum.REALTOR
		        .getRoleId()) {
			userModel.setRealtorDetail(RealtorDetail
			        .convertFromVOToEntity(userVO.getRealtorDetail()));
		}

		return userModel;
	}

}