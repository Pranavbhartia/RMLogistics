package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.nexera.common.vo.CustomerDetailVO;

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
	private Boolean mobileAlertsPreference;
	private Boolean isselfEmployed;
	private String selfEmployedIncome;
	private Boolean isssIncomeOrDisability;
	private String ssDisabilityIncome;
	private Boolean ispensionOrRetirement;
	private String monthlyPension;
	private String livingSince;
	
	
	
	
	
	
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

	@Column(name = "mobile_alert_preference", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getMobileAlertsPreference() {
		return mobileAlertsPreference;
	}

	public void setMobileAlertsPreference(Boolean mobileAlertsPreference) {
		this.mobileAlertsPreference = mobileAlertsPreference;
	}

	
	@Column(name = "isselfEmployed", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsselfEmployed() {
		return isselfEmployed;
	}

	public void setIsselfEmployed(Boolean isselfEmployed) {
		this.isselfEmployed = isselfEmployed;
	}

	@Column(name = "selfEmployedIncome")
	public String getSelfEmployedIncome() {
		return selfEmployedIncome;
	}

	public void setSelfEmployedIncome(String selfEmployedIncome) {
		this.selfEmployedIncome = selfEmployedIncome;
	}

	
	@Column(name = "isssIncomeOrDisability", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIsssIncomeOrDisability() {
		return isssIncomeOrDisability;
	}

	public void setIsssIncomeOrDisability(Boolean isssIncomeOrDisability) {
		this.isssIncomeOrDisability = isssIncomeOrDisability;
	}

	@Column(name = "ssDisabilityIncome")
	public String getSsDisabilityIncome() {
		return ssDisabilityIncome;
	}

	public void setSsDisabilityIncome(String ssDisabilityIncome) {
		this.ssDisabilityIncome = ssDisabilityIncome;
	}

	
	@Column(name = "ispensionOrRetirement", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getIspensionOrRetirement() {
		return ispensionOrRetirement;
	}

	public void setIspensionOrRetirement(Boolean ispensionOrRetirement) {
		this.ispensionOrRetirement = ispensionOrRetirement;
	}
	
	
	@Column(name = "monthlyPension")
	public String getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(String monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	@Column(name = "livingSince")
	public String getLivingSince() {
		return livingSince;
	}

	public void setLivingSince(String livingSince) {
		this.livingSince = livingSince;
	}

	public static CustomerDetailVO convertFromEntityToVO(
	        final CustomerDetail inputEntity) {
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		if (inputEntity != null) {
			customerDetailVO.setAddressCity(inputEntity.getAddressCity());
			customerDetailVO.setAddressState(inputEntity.getAddressState());
			customerDetailVO.setAddressZipCode(inputEntity.getAddressZipCode());
			if (null != inputEntity.getDateOfBirth()) {
				customerDetailVO.setDateOfBirth(inputEntity.getDateOfBirth()
				        .getTime());
			} else {
				// if the date of birth id null then ??
				customerDetailVO.setDateOfBirth(0l);
			}
			customerDetailVO.setId(inputEntity.getId());
			customerDetailVO.setProfileCompletionStatus(inputEntity
			        .getProfileCompletionStatus());
			customerDetailVO.setSecEmailId(inputEntity.getSecEmailId());
			customerDetailVO.setSecPhoneNumber(inputEntity.getSecPhoneNumber());
			customerDetailVO.setMobileAlertsPreference(inputEntity
			        .getMobileAlertsPreference());
		}
		return customerDetailVO;
	}

	public static CustomerDetail convertFromVOToEntity(
	        final CustomerDetailVO inputEntity) {
		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setSubscriptionsStatus(2);
		if (inputEntity != null) {
			customerDetail.setAddressCity(inputEntity.getAddressCity());
			customerDetail.setAddressState(inputEntity.getAddressState());
			customerDetail.setAddressZipCode(inputEntity.getAddressZipCode());
			if (null != inputEntity.getDateOfBirth()) {
				customerDetail.setDateOfBirth(new Date(inputEntity
				        .getDateOfBirth()));
			}
			customerDetail.setId(inputEntity.getId());
			customerDetail.setId(inputEntity.getId());
			customerDetail.setSubscriptionsStatus(inputEntity
			        .getSubscriptionsStatus());
			customerDetail.setSecEmailId(inputEntity.getSecEmailId());
			customerDetail.setSecPhoneNumber(inputEntity.getSecPhoneNumber());
			customerDetail.setMobileAlertsPreference(inputEntity
			        .getMobileAlertsPreference());
			if(null!=inputEntity.getProfileCompletionStatus()){
			customerDetail.setProfileCompletionStatus(inputEntity.getProfileCompletionStatus());}
		}
		return customerDetail;
	}

}