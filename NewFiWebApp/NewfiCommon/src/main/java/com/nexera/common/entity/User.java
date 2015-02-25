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
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte active;
	private String emailId;
	private String firstName;
	private String lastName;
	private String password;
	private String phoneNumber;
	private String photoImageUrl;
	private String username;
	/*
	private List<Auditlog> auditlogs;
	private List<CustomerDetail> customerdetails;
	private List<EmailTemplate> emailtemplates;
	private List<InternalUserDetail> internaluserdetails1;
	private List<InternalUserDetail> internaluserdetails2;
	private List<JobMaster> jobmasters;
	private List<Loan> loans;
	private List<LoanAppForm> loanappforms;
	private List<LoanApplicationFee> loanapplicationfees;
	private List<LoanApplicationFeeMaster> loanapplicationfeemasters;
	private List<LoanNeedsList> loanneedslists;
	private List<LoanNotification> loannotifications1;
	private List<LoanNotification> loannotifications2;
	private List<LoanRate> loanrates;
	private List<LoanSetting> loansettings;
	private List<LoanStatusMaster> loanstatusmasters;
	private List<LoanTeam> loanteams1;
	private List<LoanTeam> loanteams2;
	private List<LoanTypeMaster> loantypemasters;
	private List<NeedsListMaster> needslistmasters;
	private List<PropertyTypeMaster> propertytypemasters;
	private List<RealtorDetail> realtordetails;
	private UserRole userrole;
	private List<UserEmail> useremails1;
	private List<UserEmail> useremails2;
	private List<UserEmploymentHistory> useremploymenthistories;
	private List<Workflow> workflows;
	private List<WorkflowItemMaster> workflowitemmasters1;
	private List<WorkflowItemMaster> workflowitemmasters2;
	private List<WorkflowMaster> workflowmasters1;
	private List<WorkflowMaster> workflowmasters2;
*/
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}


	@Column(name="email_id")
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	@Column(name="first_name")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	@Column(name="last_name")
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


	@Column(name="phone_number")
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Column(name="photo_image_url")
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

/*
	//bi-directional many-to-one association to AppSetting
	@OneToMany(mappedBy="user")
	public List<AppSetting> getAppsettings() {
		return this.appsettings;
	}

	public void setAppsettings(List<AppSetting> appsettings) {
		this.appsettings = appsettings;
	}

	public AppSetting addAppsetting(AppSetting appsetting) {
		getAppsettings().add(appsetting);
		appsetting.setUser(this);

		return appsetting;
	}

	public AppSetting removeAppsetting(AppSetting appsetting) {
		getAppsettings().remove(appsetting);
		appsetting.setUser(null);

		return appsetting;
	}


	//bi-directional many-to-one association to Auditlog
	@OneToMany(mappedBy="userBean")
	public List<Auditlog> getAuditlogs() {
		return this.auditlogs;
	}

	public void setAuditlogs(List<Auditlog> auditlogs) {
		this.auditlogs = auditlogs;
	}

	public Auditlog addAuditlog(Auditlog auditlog) {
		getAuditlogs().add(auditlog);
		auditlog.setUserBean(this);

		return auditlog;
	}

	public Auditlog removeAuditlog(Auditlog auditlog) {
		getAuditlogs().remove(auditlog);
		auditlog.setUserBean(null);

		return auditlog;
	}


	//bi-directional many-to-one association to CustomerDetail
	@OneToMany(mappedBy="userBean")
	public List<CustomerDetail> getCustomerdetails() {
		return this.customerdetails;
	}

	public void setCustomerdetails(List<CustomerDetail> customerdetails) {
		this.customerdetails = customerdetails;
	}

	public CustomerDetail addCustomerdetail(CustomerDetail customerdetail) {
		getCustomerdetails().add(customerdetail);
		customerdetail.setUserBean(this);

		return customerdetail;
	}

	public CustomerDetail removeCustomerdetail(CustomerDetail customerdetail) {
		getCustomerdetails().remove(customerdetail);
		customerdetail.setUserBean(null);

		return customerdetail;
	}


	//bi-directional many-to-one association to EmailTemplate
	@OneToMany(mappedBy="user")
	public List<EmailTemplate> getEmailtemplates() {
		return this.emailtemplates;
	}

	public void setEmailtemplates(List<EmailTemplate> emailtemplates) {
		this.emailtemplates = emailtemplates;
	}

	public EmailTemplate addEmailtemplate(EmailTemplate emailtemplate) {
		getEmailtemplates().add(emailtemplate);
		emailtemplate.setUser(this);

		return emailtemplate;
	}

	public EmailTemplate removeEmailtemplate(EmailTemplate emailtemplate) {
		getEmailtemplates().remove(emailtemplate);
		emailtemplate.setUser(null);

		return emailtemplate;
	}


	//bi-directional many-to-one association to InternalUserDetail
	@OneToMany(mappedBy="user1")
	public List<InternalUserDetail> getInternaluserdetails1() {
		return this.internaluserdetails1;
	}

	public void setInternaluserdetails1(List<InternalUserDetail> internaluserdetails1) {
		this.internaluserdetails1 = internaluserdetails1;
	}

	public InternalUserDetail addInternaluserdetails1(InternalUserDetail internaluserdetails1) {
		getInternaluserdetails1().add(internaluserdetails1);
		internaluserdetails1.setUser1(this);

		return internaluserdetails1;
	}

	public InternalUserDetail removeInternaluserdetails1(InternalUserDetail internaluserdetails1) {
		getInternaluserdetails1().remove(internaluserdetails1);
		internaluserdetails1.setUser1(null);

		return internaluserdetails1;
	}


	//bi-directional many-to-one association to InternalUserDetail
	@OneToMany(mappedBy="user2")
	public List<InternalUserDetail> getInternaluserdetails2() {
		return this.internaluserdetails2;
	}

	public void setInternaluserdetails2(List<InternalUserDetail> internaluserdetails2) {
		this.internaluserdetails2 = internaluserdetails2;
	}

	public InternalUserDetail addInternaluserdetails2(InternalUserDetail internaluserdetails2) {
		getInternaluserdetails2().add(internaluserdetails2);
		internaluserdetails2.setUser2(this);

		return internaluserdetails2;
	}

	public InternalUserDetail removeInternaluserdetails2(InternalUserDetail internaluserdetails2) {
		getInternaluserdetails2().remove(internaluserdetails2);
		internaluserdetails2.setUser2(null);

		return internaluserdetails2;
	}


	//bi-directional many-to-one association to JobMaster
	@OneToMany(mappedBy="user")
	public List<JobMaster> getJobmasters() {
		return this.jobmasters;
	}

	public void setJobmasters(List<JobMaster> jobmasters) {
		this.jobmasters = jobmasters;
	}

	public JobMaster addJobmaster(JobMaster jobmaster) {
		getJobmasters().add(jobmaster);
		jobmaster.setUser(this);

		return jobmaster;
	}

	public JobMaster removeJobmaster(JobMaster jobmaster) {
		getJobmasters().remove(jobmaster);
		jobmaster.setUser(null);

		return jobmaster;
	}


	//bi-directional many-to-one association to Loan
	@OneToMany(mappedBy="userBean")
	public List<Loan> getLoans() {
		return this.loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public Loan addLoan(Loan loan) {
		getLoans().add(loan);
		loan.setUserBean(this);

		return loan;
	}

	public Loan removeLoan(Loan loan) {
		getLoans().remove(loan);
		loan.setUserBean(null);

		return loan;
	}


	//bi-directional many-to-one association to LoanAppForm
	@OneToMany(mappedBy="userBean")
	public List<LoanAppForm> getLoanappforms() {
		return this.loanappforms;
	}

	public void setLoanappforms(List<LoanAppForm> loanappforms) {
		this.loanappforms = loanappforms;
	}

	public LoanAppForm addLoanappform(LoanAppForm loanappform) {
		getLoanappforms().add(loanappform);
		loanappform.setUserBean(this);

		return loanappform;
	}

	public LoanAppForm removeLoanappform(LoanAppForm loanappform) {
		getLoanappforms().remove(loanappform);
		loanappform.setUserBean(null);

		return loanappform;
	}


	//bi-directional many-to-one association to LoanApplicationFee
	@OneToMany(mappedBy="user")
	public List<LoanApplicationFee> getLoanapplicationfees() {
		return this.loanapplicationfees;
	}

	public void setLoanapplicationfees(List<LoanApplicationFee> loanapplicationfees) {
		this.loanapplicationfees = loanapplicationfees;
	}

	public LoanApplicationFee addLoanapplicationfee(LoanApplicationFee loanapplicationfee) {
		getLoanapplicationfees().add(loanapplicationfee);
		loanapplicationfee.setUser(this);

		return loanapplicationfee;
	}

	public LoanApplicationFee removeLoanapplicationfee(LoanApplicationFee loanapplicationfee) {
		getLoanapplicationfees().remove(loanapplicationfee);
		loanapplicationfee.setUser(null);

		return loanapplicationfee;
	}


	//bi-directional many-to-one association to LoanApplicationFeeMaster
	@OneToMany(mappedBy="user")
	public List<LoanApplicationFeeMaster> getLoanapplicationfeemasters() {
		return this.loanapplicationfeemasters;
	}

	public void setLoanapplicationfeemasters(List<LoanApplicationFeeMaster> loanapplicationfeemasters) {
		this.loanapplicationfeemasters = loanapplicationfeemasters;
	}

	public LoanApplicationFeeMaster addLoanapplicationfeemaster(LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanapplicationfeemasters().add(loanapplicationfeemaster);
		loanapplicationfeemaster.setUser(this);

		return loanapplicationfeemaster;
	}

	public LoanApplicationFeeMaster removeLoanapplicationfeemaster(LoanApplicationFeeMaster loanapplicationfeemaster) {
		getLoanapplicationfeemasters().remove(loanapplicationfeemaster);
		loanapplicationfeemaster.setUser(null);

		return loanapplicationfeemaster;
	}


	//bi-directional many-to-one association to LoanNeedsList
	@OneToMany(mappedBy="user")
	public List<LoanNeedsList> getLoanneedslists() {
		return this.loanneedslists;
	}

	public void setLoanneedslists(List<LoanNeedsList> loanneedslists) {
		this.loanneedslists = loanneedslists;
	}

	public LoanNeedsList addLoanneedslist(LoanNeedsList loanneedslist) {
		getLoanneedslists().add(loanneedslist);
		loanneedslist.setUser(this);

		return loanneedslist;
	}

	public LoanNeedsList removeLoanneedslist(LoanNeedsList loanneedslist) {
		getLoanneedslists().remove(loanneedslist);
		loanneedslist.setUser(null);

		return loanneedslist;
	}


	//bi-directional many-to-one association to LoanNotification
	@OneToMany(mappedBy="user1")
	public List<LoanNotification> getLoannotifications1() {
		return this.loannotifications1;
	}

	public void setLoannotifications1(List<LoanNotification> loannotifications1) {
		this.loannotifications1 = loannotifications1;
	}

	public LoanNotification addLoannotifications1(LoanNotification loannotifications1) {
		getLoannotifications1().add(loannotifications1);
		loannotifications1.setUser1(this);

		return loannotifications1;
	}

	public LoanNotification removeLoannotifications1(LoanNotification loannotifications1) {
		getLoannotifications1().remove(loannotifications1);
		loannotifications1.setUser1(null);

		return loannotifications1;
	}


	//bi-directional many-to-one association to LoanNotification
	@OneToMany(mappedBy="user2")
	public List<LoanNotification> getLoannotifications2() {
		return this.loannotifications2;
	}

	public void setLoannotifications2(List<LoanNotification> loannotifications2) {
		this.loannotifications2 = loannotifications2;
	}

	public LoanNotification addLoannotifications2(LoanNotification loannotifications2) {
		getLoannotifications2().add(loannotifications2);
		loannotifications2.setUser2(this);

		return loannotifications2;
	}

	public LoanNotification removeLoannotifications2(LoanNotification loannotifications2) {
		getLoannotifications2().remove(loannotifications2);
		loannotifications2.setUser2(null);

		return loannotifications2;
	}


	//bi-directional many-to-one association to LoanRate
	@OneToMany(mappedBy="user")
	public List<LoanRate> getLoanrates() {
		return this.loanrates;
	}

	public void setLoanrates(List<LoanRate> loanrates) {
		this.loanrates = loanrates;
	}

	public LoanRate addLoanrate(LoanRate loanrate) {
		getLoanrates().add(loanrate);
		loanrate.setUser(this);

		return loanrate;
	}

	public LoanRate removeLoanrate(LoanRate loanrate) {
		getLoanrates().remove(loanrate);
		loanrate.setUser(null);

		return loanrate;
	}


	//bi-directional many-to-one association to LoanSetting
	@OneToMany(mappedBy="user")
	public List<LoanSetting> getLoansettings() {
		return this.loansettings;
	}

	public void setLoansettings(List<LoanSetting> loansettings) {
		this.loansettings = loansettings;
	}

	public LoanSetting addLoansetting(LoanSetting loansetting) {
		getLoansettings().add(loansetting);
		loansetting.setUser(this);

		return loansetting;
	}

	public LoanSetting removeLoansetting(LoanSetting loansetting) {
		getLoansettings().remove(loansetting);
		loansetting.setUser(null);

		return loansetting;
	}


	//bi-directional many-to-one association to LoanStatusMaster
	@OneToMany(mappedBy="user")
	public List<LoanStatusMaster> getLoanstatusmasters() {
		return this.loanstatusmasters;
	}

	public void setLoanstatusmasters(List<LoanStatusMaster> loanstatusmasters) {
		this.loanstatusmasters = loanstatusmasters;
	}

	public LoanStatusMaster addLoanstatusmaster(LoanStatusMaster loanstatusmaster) {
		getLoanstatusmasters().add(loanstatusmaster);
		loanstatusmaster.setUser(this);

		return loanstatusmaster;
	}

	public LoanStatusMaster removeLoanstatusmaster(LoanStatusMaster loanstatusmaster) {
		getLoanstatusmasters().remove(loanstatusmaster);
		loanstatusmaster.setUser(null);

		return loanstatusmaster;
	}


	//bi-directional many-to-one association to LoanTeam
	@OneToMany(mappedBy="user1")
	public List<LoanTeam> getLoanteams1() {
		return this.loanteams1;
	}

	public void setLoanteams1(List<LoanTeam> loanteams1) {
		this.loanteams1 = loanteams1;
	}

	public LoanTeam addLoanteams1(LoanTeam loanteams1) {
		getLoanteams1().add(loanteams1);
		loanteams1.setUser1(this);

		return loanteams1;
	}

	public LoanTeam removeLoanteams1(LoanTeam loanteams1) {
		getLoanteams1().remove(loanteams1);
		loanteams1.setUser1(null);

		return loanteams1;
	}


	//bi-directional many-to-one association to LoanTeam
	@OneToMany(mappedBy="user2")
	public List<LoanTeam> getLoanteams2() {
		return this.loanteams2;
	}

	public void setLoanteams2(List<LoanTeam> loanteams2) {
		this.loanteams2 = loanteams2;
	}

	public LoanTeam addLoanteams2(LoanTeam loanteams2) {
		getLoanteams2().add(loanteams2);
		loanteams2.setUser2(this);

		return loanteams2;
	}

	public LoanTeam removeLoanteams2(LoanTeam loanteams2) {
		getLoanteams2().remove(loanteams2);
		loanteams2.setUser2(null);

		return loanteams2;
	}


	//bi-directional many-to-one association to LoanTypeMaster
	@OneToMany(mappedBy="user")
	public List<LoanTypeMaster> getLoantypemasters() {
		return this.loantypemasters;
	}

	public void setLoantypemasters(List<LoanTypeMaster> loantypemasters) {
		this.loantypemasters = loantypemasters;
	}

	public LoanTypeMaster addLoantypemaster(LoanTypeMaster loantypemaster) {
		getLoantypemasters().add(loantypemaster);
		loantypemaster.setUser(this);

		return loantypemaster;
	}

	public LoanTypeMaster removeLoantypemaster(LoanTypeMaster loantypemaster) {
		getLoantypemasters().remove(loantypemaster);
		loantypemaster.setUser(null);

		return loantypemaster;
	}


	//bi-directional many-to-one association to NeedsListMaster
	@OneToMany(mappedBy="user")
	public List<NeedsListMaster> getNeedslistmasters() {
		return this.needslistmasters;
	}

	public void setNeedslistmasters(List<NeedsListMaster> needslistmasters) {
		this.needslistmasters = needslistmasters;
	}

	public NeedsListMaster addNeedslistmaster(NeedsListMaster needslistmaster) {
		getNeedslistmasters().add(needslistmaster);
		needslistmaster.setUser(this);

		return needslistmaster;
	}

	public NeedsListMaster removeNeedslistmaster(NeedsListMaster needslistmaster) {
		getNeedslistmasters().remove(needslistmaster);
		needslistmaster.setUser(null);

		return needslistmaster;
	}


	//bi-directional many-to-one association to PropertyTypeMaster
	@OneToMany(mappedBy="user")
	public List<PropertyTypeMaster> getPropertytypemasters() {
		return this.propertytypemasters;
	}

	public void setPropertytypemasters(List<PropertyTypeMaster> propertytypemasters) {
		this.propertytypemasters = propertytypemasters;
	}

	public PropertyTypeMaster addPropertytypemaster(PropertyTypeMaster propertytypemaster) {
		getPropertytypemasters().add(propertytypemaster);
		propertytypemaster.setUser(this);

		return propertytypemaster;
	}

	public PropertyTypeMaster removePropertytypemaster(PropertyTypeMaster propertytypemaster) {
		getPropertytypemasters().remove(propertytypemaster);
		propertytypemaster.setUser(null);

		return propertytypemaster;
	}


	//bi-directional many-to-one association to RealtorDetail
	@OneToMany(mappedBy="userBean")
	public List<RealtorDetail> getRealtordetails() {
		return this.realtordetails;
	}

	public void setRealtordetails(List<RealtorDetail> realtordetails) {
		this.realtordetails = realtordetails;
	}

	public RealtorDetail addRealtordetail(RealtorDetail realtordetail) {
		getRealtordetails().add(realtordetail);
		realtordetail.setUserBean(this);

		return realtordetail;
	}

	public RealtorDetail removeRealtordetail(RealtorDetail realtordetail) {
		getRealtordetails().remove(realtordetail);
		realtordetail.setUserBean(null);

		return realtordetail;
	}


	//bi-directional many-to-one association to UserRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_role")
	public UserRole getUserrole() {
		return this.userrole;
	}

	public void setUserrole(UserRole userrole) {
		this.userrole = userrole;
	}


	//bi-directional many-to-one association to UserEmail
	@OneToMany(mappedBy="user1")
	public List<UserEmail> getUseremails1() {
		return this.useremails1;
	}

	public void setUseremails1(List<UserEmail> useremails1) {
		this.useremails1 = useremails1;
	}

	public UserEmail addUseremails1(UserEmail useremails1) {
		getUseremails1().add(useremails1);
		useremails1.setUser1(this);

		return useremails1;
	}

	public UserEmail removeUseremails1(UserEmail useremails1) {
		getUseremails1().remove(useremails1);
		useremails1.setUser1(null);

		return useremails1;
	}


	//bi-directional many-to-one association to UserEmail
	@OneToMany(mappedBy="user2")
	public List<UserEmail> getUseremails2() {
		return this.useremails2;
	}

	public void setUseremails2(List<UserEmail> useremails2) {
		this.useremails2 = useremails2;
	}

	public UserEmail addUseremails2(UserEmail useremails2) {
		getUseremails2().add(useremails2);
		useremails2.setUser2(this);

		return useremails2;
	}

	public UserEmail removeUseremails2(UserEmail useremails2) {
		getUseremails2().remove(useremails2);
		useremails2.setUser2(null);

		return useremails2;
	}


	//bi-directional many-to-one association to UserEmploymentHistory
	@OneToMany(mappedBy="userBean")
	public List<UserEmploymentHistory> getUseremploymenthistories() {
		return this.useremploymenthistories;
	}

	public void setUseremploymenthistories(List<UserEmploymentHistory> useremploymenthistories) {
		this.useremploymenthistories = useremploymenthistories;
	}

	public UserEmploymentHistory addUseremploymenthistory(UserEmploymentHistory useremploymenthistory) {
		getUseremploymenthistories().add(useremploymenthistory);
		useremploymenthistory.setUserBean(this);

		return useremploymenthistory;
	}

	public UserEmploymentHistory removeUseremploymenthistory(UserEmploymentHistory useremploymenthistory) {
		getUseremploymenthistories().remove(useremploymenthistory);
		useremploymenthistory.setUserBean(null);

		return useremploymenthistory;
	}


	//bi-directional many-to-one association to Workflow
	@OneToMany(mappedBy="user")
	public List<Workflow> getWorkflows() {
		return this.workflows;
	}

	public void setWorkflows(List<Workflow> workflows) {
		this.workflows = workflows;
	}

	public Workflow addWorkflow(Workflow workflow) {
		getWorkflows().add(workflow);
		workflow.setUser(this);

		return workflow;
	}

	public Workflow removeWorkflow(Workflow workflow) {
		getWorkflows().remove(workflow);
		workflow.setUser(null);

		return workflow;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@OneToMany(mappedBy="user1")
	public List<WorkflowItemMaster> getWorkflowitemmasters1() {
		return this.workflowitemmasters1;
	}

	public void setWorkflowitemmasters1(List<WorkflowItemMaster> workflowitemmasters1) {
		this.workflowitemmasters1 = workflowitemmasters1;
	}

	public WorkflowItemMaster addWorkflowitemmasters1(WorkflowItemMaster workflowitemmasters1) {
		getWorkflowitemmasters1().add(workflowitemmasters1);
		workflowitemmasters1.setUser1(this);

		return workflowitemmasters1;
	}

	public WorkflowItemMaster removeWorkflowitemmasters1(WorkflowItemMaster workflowitemmasters1) {
		getWorkflowitemmasters1().remove(workflowitemmasters1);
		workflowitemmasters1.setUser1(null);

		return workflowitemmasters1;
	}


	//bi-directional many-to-one association to WorkflowItemMaster
	@OneToMany(mappedBy="user2")
	public List<WorkflowItemMaster> getWorkflowitemmasters2() {
		return this.workflowitemmasters2;
	}

	public void setWorkflowitemmasters2(List<WorkflowItemMaster> workflowitemmasters2) {
		this.workflowitemmasters2 = workflowitemmasters2;
	}

	public WorkflowItemMaster addWorkflowitemmasters2(WorkflowItemMaster workflowitemmasters2) {
		getWorkflowitemmasters2().add(workflowitemmasters2);
		workflowitemmasters2.setUser2(this);

		return workflowitemmasters2;
	}

	public WorkflowItemMaster removeWorkflowitemmasters2(WorkflowItemMaster workflowitemmasters2) {
		getWorkflowitemmasters2().remove(workflowitemmasters2);
		workflowitemmasters2.setUser2(null);

		return workflowitemmasters2;
	}


	//bi-directional many-to-one association to WorkflowMaster
	@OneToMany(mappedBy="user1")
	public List<WorkflowMaster> getWorkflowmasters1() {
		return this.workflowmasters1;
	}

	public void setWorkflowmasters1(List<WorkflowMaster> workflowmasters1) {
		this.workflowmasters1 = workflowmasters1;
	}

	public WorkflowMaster addWorkflowmasters1(WorkflowMaster workflowmasters1) {
		getWorkflowmasters1().add(workflowmasters1);
		workflowmasters1.setUser1(this);

		return workflowmasters1;
	}

	public WorkflowMaster removeWorkflowmasters1(WorkflowMaster workflowmasters1) {
		getWorkflowmasters1().remove(workflowmasters1);
		workflowmasters1.setUser1(null);

		return workflowmasters1;
	}


	//bi-directional many-to-one association to WorkflowMaster
	@OneToMany(mappedBy="user2")
	public List<WorkflowMaster> getWorkflowmasters2() {
		return this.workflowmasters2;
	}

	public void setWorkflowmasters2(List<WorkflowMaster> workflowmasters2) {
		this.workflowmasters2 = workflowmasters2;
	}

	public WorkflowMaster addWorkflowmasters2(WorkflowMaster workflowmasters2) {
		getWorkflowmasters2().add(workflowmasters2);
		workflowmasters2.setUser2(this);

		return workflowmasters2;
	}

	public WorkflowMaster removeWorkflowmasters2(WorkflowMaster workflowmasters2) {
		getWorkflowmasters2().remove(workflowmasters2);
		workflowmasters2.setUser2(null);

		return workflowmasters2;
	}
	*/
	//Spring security related methods

	public User(String emailId, String password, String firstName, String lastName) {
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

}