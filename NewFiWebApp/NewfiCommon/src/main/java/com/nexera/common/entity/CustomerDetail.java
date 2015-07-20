package com.nexera.common.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nexera.common.vo.CustomerBankAccountDetailsVO;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerOtherAccountDetailsVO;
import com.nexera.common.vo.CustomerRetirementAccountDetailsVO;
import com.nexera.common.vo.CustomerSpouseDetailVO;

/**
 * The persistent class for the customerdetails database table.
 * 
 */
@Entity
@Table(name = "customerdetails")
@NamedQuery(name = "CustomerDetail.findAll", query = "SELECT c FROM CustomerDetail c")
public class CustomerDetail implements Serializable {

	private static final Logger LOG = LoggerFactory
	        .getLogger(CustomerDetail.class);
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
	private Boolean isselfEmployed;
	private String selfEmployedIncome;
	private Boolean isssIncomeOrDisability;
	private String ssDisabilityIncome;
	private Boolean ispensionOrRetirement;
	private String monthlyPension;
	private String livingSince;
	private String equifaxScore;
	private String experianScore;
	private String transunionScore;

	private String addressStreet;

	private Boolean selectedProperty;

	// private CustomerSpouseDetail customerSpouseDetail;

	// private CustomerEmploymentIncome customerEmploymentIncome;

	// private CustomerBankAccountDetails customerBankAccountDetails;

	private CustomerRetirementAccountDetails customerRetirementAccountDetails;

	private CustomerOtherAccountDetails customerOtherAccountDetails;

	private Boolean tutorialStatus;

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

	@Column(name = "tutorial_status")
	public Boolean getTutorialStatus() {
		return tutorialStatus;
	}

	public void setTutorialStatus(Boolean tutorialStatus) {
		this.tutorialStatus = tutorialStatus;
	}

	/*
	 * @OneToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "customerspousedetails") public CustomerSpouseDetail
	 * getCustomerSpouseDetail() { return customerSpouseDetail; }
	 * 
	 * public void setCustomerSpouseDetail(CustomerSpouseDetail
	 * customerSpouseDetail) { this.customerSpouseDetail = customerSpouseDetail;
	 * }
	 * 
	 * 
	 * 
	 * @OneToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "customeremploymentincome") public
	 * CustomerEmploymentIncome getCustomerEmploymentIncome() { return
	 * customerEmploymentIncome; }
	 * 
	 * public void setCustomerEmploymentIncome( CustomerEmploymentIncome
	 * customerEmploymentIncome) { this.customerEmploymentIncome =
	 * customerEmploymentIncome; }
	 * 
	 * 
	 * 
	 * 
	 * @OneToOne(fetch = FetchType.EAGER)
	 * 
	 * @JoinColumn(name = "customerbankaccountdetails") public
	 * CustomerBankAccountDetails getCustomerBankAccountDetails() { return
	 * customerBankAccountDetails; }
	 * 
	 * public void setCustomerBankAccountDetails( CustomerBankAccountDetails
	 * customerBankAccountDetails) { this.customerBankAccountDetails =
	 * customerBankAccountDetails; }
	 */

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_retirement_ac_details")
	public CustomerRetirementAccountDetails getCustomerRetirementAccountDetails() {
		return customerRetirementAccountDetails;
	}

	public void setCustomerRetirementAccountDetails(
	        CustomerRetirementAccountDetails customerRetirementAccountDetails) {
		this.customerRetirementAccountDetails = customerRetirementAccountDetails;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_other_ac_details")
	public CustomerOtherAccountDetails getCustomerOtherAccountDetails() {
		return customerOtherAccountDetails;
	}

	public void setCustomerOtherAccountDetails(
	        CustomerOtherAccountDetails customerOtherAccountDetails) {
		this.customerOtherAccountDetails = customerOtherAccountDetails;
	}

	public static CustomerDetailVO convertFromEntityToVO(
	        final CustomerDetail inputEntity) {
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		if (inputEntity != null) {
			customerDetailVO.setAddressCity(inputEntity.getAddressCity());
			customerDetailVO.setAddressStreet(inputEntity.getAddressStreet());
			customerDetailVO.setAddressState(inputEntity.getAddressState());
			customerDetailVO.setAddressZipCode(inputEntity.getAddressZipCode());
			if (null != inputEntity.getDateOfBirth()) {

				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				customerDetailVO.setDateOfBirth(df.format(inputEntity
				        .getDateOfBirth()));
			} else {
				// if the date of birth id null then ??
				customerDetailVO.setDateOfBirth(null);
			}
			customerDetailVO.setId(inputEntity.getId());
			customerDetailVO.setProfileCompletionStatus(inputEntity
			        .getProfileCompletionStatus());
			customerDetailVO.setSecEmailId(inputEntity.getSecEmailId());
			customerDetailVO.setSecPhoneNumber(inputEntity.getSecPhoneNumber());

			customerDetailVO.setLivingSince(inputEntity.getLivingSince());
			customerDetailVO.setTransunionScore(inputEntity
			        .getTransunionScore());
			customerDetailVO.setEquifaxScore(inputEntity.getEquifaxScore());
			customerDetailVO.setExperianScore(inputEntity.getExperianScore());

			// customerDetailVO.setCustomerSpouseDetail(convertFromEntityToVO(inputEntity.getCustomerSpouseDetail()));
			// System.out.println("inputEntity.getCustomerSpouseDetail()"+inputEntity.getCustomerEmploymentIncome());
			// customerDetailVO.setCustomerEmploymentIncome(convertFromEntityToVO(inputEntity.getCustomerEmploymentIncome()));
			// customerDetailVO.setCustomerBankAccountDetails(convertFromEntityToVO(inputEntity.getCustomerBankAccountDetails()));
			customerDetailVO
			        .setCustomerRetirementAccountDetails(convertFromEntityToVO(inputEntity
			                .getCustomerRetirementAccountDetails()));
			customerDetailVO
			        .setCustomerOtherAccountDetails(convertFromEntityToVO(inputEntity
			                .getCustomerOtherAccountDetails()));

			customerDetailVO.setTutorialStatus(inputEntity.getTutorialStatus());

		}
		return customerDetailVO;
	}

	private static CustomerOtherAccountDetailsVO convertFromEntityToVO(
	        CustomerOtherAccountDetails customerOtherAccountDetails) {

		CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO = new CustomerOtherAccountDetailsVO();
		if (null == customerOtherAccountDetails) {
			// changed this as customer spouse detail should be be saved during
			// registration
			return null;
			// return customerOtherAccountDetailsVO;
		}
		customerOtherAccountDetailsVO
		        .setId(customerOtherAccountDetails.getId());
		customerOtherAccountDetailsVO
		        .setAccountSubType(customerOtherAccountDetails
		                .getAccountSubType());
		customerOtherAccountDetailsVO
		        .setCurrentAccountBalance(customerOtherAccountDetails
		                .getCurrentaccountbalance());
		customerOtherAccountDetailsVO
		        .setAmountForNewHome(customerOtherAccountDetails
		                .getAmountfornewhome());

		return customerOtherAccountDetailsVO;
	}

	private static CustomerRetirementAccountDetailsVO convertFromEntityToVO(
	        CustomerRetirementAccountDetails customerRetirementAccountDetails) {

		CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO = new CustomerRetirementAccountDetailsVO();
		if (null == customerRetirementAccountDetails) {
			// changed this as customer spouse detail should be be saved during
			// registration
			return null;
			// return customerRetirementAccountDetailsVO;
		}
		customerRetirementAccountDetailsVO
		        .setId(customerRetirementAccountDetails.getId());
		customerRetirementAccountDetailsVO
		        .setAccountSubType(customerRetirementAccountDetails
		                .getAccountSubType());
		customerRetirementAccountDetailsVO
		        .setCurrentAccountBalance(customerRetirementAccountDetails
		                .getCurrentaccountbalance());
		customerRetirementAccountDetailsVO
		        .setAmountForNewHome(customerRetirementAccountDetails
		                .getAmountfornewhome());

		return customerRetirementAccountDetailsVO;
	}

	private static CustomerBankAccountDetailsVO convertFromEntityToVO(
	        CustomerBankAccountDetails customerBankAccountDetails) {

		CustomerBankAccountDetailsVO customerBankAccountDetailsVO = new CustomerBankAccountDetailsVO();
		if (null == customerBankAccountDetails) {
			// changed this as customer spouse detail should be be saved during
			// registration
			return null;
			// return customerBankAccountDetailsVO;
		}
		customerBankAccountDetailsVO.setId(customerBankAccountDetails.getId());
		customerBankAccountDetailsVO
		        .setAccountSubType(customerBankAccountDetails
		                .getAccountSubType());
		customerBankAccountDetailsVO
		        .setAmountForNewHome(customerBankAccountDetails
		                .getAmountfornewhome());
		customerBankAccountDetailsVO
		        .setCurrentAccountBalance(customerBankAccountDetails
		                .getCurrentaccountbalance());

		return customerBankAccountDetailsVO;
	}

	private static CustomerEmploymentIncomeVO convertFromEntityToVO(
	        CustomerEmploymentIncome customerEmploymentIncome) {

		CustomerEmploymentIncomeVO customerEmploymentIncomeVO = new CustomerEmploymentIncomeVO();
		if (null == customerEmploymentIncome) {
			// changed this as customer spouse detail should be be saved during
			// registration
			return null;
			// return customerEmploymentIncomeVO;
		}

		customerEmploymentIncomeVO.setId(customerEmploymentIncome.getId());
		customerEmploymentIncomeVO.setEmployedAt(customerEmploymentIncome
		        .getEmployedAt());
		customerEmploymentIncomeVO
		        .setEmployedIncomePreTax(customerEmploymentIncome
		                .getEmployedIncomePreTax());
		customerEmploymentIncomeVO.setEmployedSince(customerEmploymentIncome
		        .getEmployedSince());
		customerEmploymentIncomeVO.setEmploymentLength(String
		        .valueOf(customerEmploymentIncome.getEmploymentLength()));

		return customerEmploymentIncomeVO;
	}

	private static CustomerSpouseDetailVO convertFromEntityToVO(
	        CustomerSpouseDetail customerSpouseDetail) {

		CustomerSpouseDetailVO customerSpouseDetailVO = new CustomerSpouseDetailVO();
		if (customerSpouseDetail == null) {
			// changed this as customer spouse detail should be be saved during
			// registration
			return null;
			// return customerSpouseDetailVO;

		}
		customerSpouseDetailVO.setId(customerSpouseDetail.getId());
		customerSpouseDetailVO.setSpouseName(customerSpouseDetail
		        .getSpouseName());

		return customerSpouseDetailVO;
	}

	public static CustomerDetail convertFromVOToEntity(
	        final CustomerDetailVO inputEntity) {
		if (inputEntity == null) {
			return null;
		}
		CustomerDetail customerDetail = new CustomerDetail();

		customerDetail.setSubscriptionsStatus(2);
		if (inputEntity != null) {

			customerDetail.setAddressCity(inputEntity.getAddressCity());
			customerDetail.setAddressStreet(inputEntity.getAddressStreet());
			customerDetail.setAddressState(inputEntity.getAddressState());
			customerDetail.setAddressZipCode(inputEntity.getAddressZipCode());
			if (null != inputEntity.getDateOfBirth()) {
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				try {
					customerDetail.setDateOfBirth(df.parse(inputEntity
					        .getDateOfBirth()));
				} catch (ParseException e) {
					LOG.error("Parse exception for DOB of user: "
					        + inputEntity.getId());
				}
			}

			customerDetail.setId(inputEntity.getId());
			customerDetail.setTransunionScore(inputEntity.getTransunionScore());
			customerDetail.setEquifaxScore(inputEntity.getEquifaxScore());
			customerDetail.setExperianScore(inputEntity.getExperianScore());
			customerDetail.setSubscriptionsStatus(inputEntity
			        .getSubscriptionsStatus());
			customerDetail.setSecEmailId(inputEntity.getSecEmailId());
			customerDetail.setSecPhoneNumber(inputEntity.getSecPhoneNumber());
			customerDetail.setLivingSince(inputEntity.getLivingSince());

			if (null != inputEntity.getProfileCompletionStatus()) {
				customerDetail.setProfileCompletionStatus(inputEntity
				        .getProfileCompletionStatus());

				// customerDetail.setCustomerBankAccountDetails(convertFromVOToEntity(inputEntity.getCustomerBankAccountDetails()));
				// customerDetail.setCustomerEmploymentIncome(convertFromVOToEntity(inputEntity.getCustomerEmploymentIncome()));
				customerDetail
				        .setCustomerOtherAccountDetails(convertFromVOToEntity(inputEntity
				                .getCustomerOtherAccountDetails()));
				customerDetail
				        .setCustomerRetirementAccountDetails(convertFromVOToEntity(inputEntity
				                .getCustomerRetirementAccountDetails()));

			}

			// selected Property : start
			customerDetail.setSelectedProperty(inputEntity
			        .getSelectedProperty());

		}
		return customerDetail;
	}

	private static CustomerOtherAccountDetails convertFromVOToEntity(
	        CustomerOtherAccountDetailsVO customerOtherAccountDetailsVO) {

		CustomerOtherAccountDetails customerOtherAccountDetails = new CustomerOtherAccountDetails();
		if (null == customerOtherAccountDetailsVO)
			return null;
		// return customerOtherAccountDetails;

		customerOtherAccountDetails
		        .setId(customerOtherAccountDetailsVO.getId());
		customerOtherAccountDetails
		        .setAccountSubType(customerOtherAccountDetailsVO
		                .getAccountSubType());
		customerOtherAccountDetails
		        .setCurrentaccountbalance(customerOtherAccountDetailsVO
		                .getCurrentAccountBalance());
		customerOtherAccountDetails
		        .setAmountfornewhome(customerOtherAccountDetailsVO
		                .getAmountForNewHome());

		return customerOtherAccountDetails;
	}

	private static CustomerRetirementAccountDetails convertFromVOToEntity(
	        CustomerRetirementAccountDetailsVO customerRetirementAccountDetailsVO) {

		CustomerRetirementAccountDetails customerRetirementAccountDetails = new CustomerRetirementAccountDetails();
		if (null == customerRetirementAccountDetailsVO)
			return null;
		// return customerRetirementAccountDetails;

		customerRetirementAccountDetails
		        .setId(customerRetirementAccountDetailsVO.getId());
		customerRetirementAccountDetails
		        .setAccountSubType(customerRetirementAccountDetailsVO
		                .getAccountSubType());
		customerRetirementAccountDetails
		        .setCurrentaccountbalance(customerRetirementAccountDetailsVO
		                .getCurrentAccountBalance());
		customerRetirementAccountDetails
		        .setAmountfornewhome(customerRetirementAccountDetailsVO
		                .getAmountForNewHome());

		return customerRetirementAccountDetails;
	}

	private static CustomerBankAccountDetails convertFromVOToEntity(
	        CustomerBankAccountDetailsVO customerBankAccountDetailsVO) {

		CustomerBankAccountDetails customerBankAccountDetails = new CustomerBankAccountDetails();
		if (null == customerBankAccountDetailsVO)
			return null;
		// return customerBankAccountDetails;

		customerBankAccountDetails.setId(customerBankAccountDetailsVO.getId());
		customerBankAccountDetails
		        .setAccountSubType(customerBankAccountDetailsVO
		                .getAccountSubType());
		customerBankAccountDetails
		        .setAmountfornewhome(customerBankAccountDetailsVO
		                .getAmountForNewHome());
		customerBankAccountDetails
		        .setCurrentaccountbalance(customerBankAccountDetailsVO
		                .getCurrentAccountBalance());

		return customerBankAccountDetails;
	}

	private static CustomerEmploymentIncome convertFromVOToEntity(
	        CustomerEmploymentIncomeVO customerEmploymentIncomeVO) {

		CustomerEmploymentIncome customerEmploymentIncome = new CustomerEmploymentIncome();
		if (null == customerEmploymentIncomeVO)
			return null;
		// return customerEmploymentIncome;

		customerEmploymentIncome.setId(customerEmploymentIncomeVO.getId());
		customerEmploymentIncome.setEmployedAt(customerEmploymentIncomeVO
		        .getEmployedAt());
		customerEmploymentIncome
		        .setEmployedIncomePreTax(customerEmploymentIncomeVO
		                .getEmployedIncomePreTax());
		customerEmploymentIncome.setEmployedSince(customerEmploymentIncomeVO
		        .getEmployedSince());
		if (customerEmploymentIncomeVO.getEmploymentLength() != null) {
			customerEmploymentIncome.setEmploymentLength(Double
			        .parseDouble(customerEmploymentIncomeVO
			                .getEmploymentLength()));
		}
		return customerEmploymentIncome;
	}

	@Column(name = "equifax_score")
	public String getEquifaxScore() {
		return equifaxScore;
	}

	public void setEquifaxScore(String equifaxScore) {
		this.equifaxScore = equifaxScore;
	}

	@Column(name = "experian_score")
	public String getExperianScore() {
		return experianScore;
	}

	public void setExperianScore(String experianScore) {
		this.experianScore = experianScore;
	}

	@Column(name = "transunion_score")
	public String getTransunionScore() {
		return transunionScore;
	}

	public void setTransunionScore(String transunionScore) {
		this.transunionScore = transunionScore;
	}

	@Column(name = "address_street")
	public String getAddressStreet() {
		return addressStreet;
	}

	public void setAddressStreet(String addressStreet) {
		this.addressStreet = addressStreet;
	}

	@Column(name = "is_selected_property")
	public Boolean getSelectedProperty() {
		return selectedProperty;
	}

	public void setSelectedProperty(Boolean selectedProperty) {
		this.selectedProperty = selectedProperty;
	}

}