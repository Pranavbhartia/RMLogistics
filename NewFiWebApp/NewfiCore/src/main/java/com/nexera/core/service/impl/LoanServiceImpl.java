package com.nexera.core.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.LoanMilestoneDao;
import com.nexera.common.dao.LoanMilestoneMasterDao;
import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.dao.LoanTurnAroundTimeDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTurnAroundTime;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.entity.WorkflowItemMaster;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.LoanTypeMasterEnum;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.Milestones;
import com.nexera.common.enums.MobileCarriersEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanLockRateVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanUserSearchVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.MileStoneTurnAroundTimeVO;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserLoanStatus;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailRecipientVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.helper.TeamAssignmentHelper;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.MileStoneTurnAroundTimeService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.StateLookupService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.WorkflowCoreService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.enums.WorkItemStatus;
import com.nexera.workflow.service.WorkflowService;

@Component
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private Utils utils;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	WorkflowCoreService workflowCoreService;
	@Autowired
	private TemplateService templateService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private LoanNeedListDao loanNeedListDao;

	@Autowired
	private LoanMilestoneDao loanMilestoneDao;

	@Autowired
	private LoanMilestoneMasterDao loanMilestoneMasterDao;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private MileStoneTurnAroundTimeService aroundTimeService;

	@Autowired
	private LoanTurnAroundTimeDao loanTurnAroundTimeDao;

	@Autowired
	private TeamAssignmentHelper assignmentHelper;

	@Autowired
	private NeedsListService needListService;

	@Autowired
	private UploadedFilesListService uploadedFilesListService;

	@Autowired
	private LoanAppFormService loanAppFormService;
	@Autowired
	private StateLookupService stateLookupService;
	@Autowired
	public MessageServiceHelper messageServiceHelper;
	@Autowired
	private NotificationService notificationService;

	@Value("${profile.url}")
	private String systemBaseUrl;

	@Value("${profile.url}")
	private String baseUrl;

	@Value("${lqb.defaulturl}")
	private String lqbDefaultUrl;

	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<LoanVO> getLoansOfUser(UserVO user) {

		List<Loan> list = loanDao.getLoansOfUser(this.parseUserModel(user));
		return this.buildLoanVOList(list);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO getActiveLoanOfUser(UserVO user) {

		Loan loan = loanDao.getActiveLoanOfUser(this.parseUserModel(user));
		if (loan != null) {
			return Loan.convertFromEntityToVO(loan);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO getLoanByID(Integer loanID) {
		return Loan.convertFromEntityToVO(loanDao.getLoanWithDetails(loanID));
	}

	@Override
	@Transactional(readOnly = true)
	public Loan fetchLoanById(Integer loanID) {
		return (Loan) loanDao.load(Loan.class, loanID);
	}

	@Override
	@Transactional()
	public boolean addToLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = this.parseLoanModel(loan);
		User userModel = this.parseUserModel(user);
		user = userProfileService.loadInternalUser(user.getId());
		if (user.getUserRole() != null
		        && user.getUserRole().getRoleCd()
		                .equals(UserRolesEnum.REALTOR.getName())) {
			notificationService.dismissReadNotifications(loan.getId(),
			        MilestoneNotificationTypes.TEAM_ADD_NOTIFICATION_TYPE);
		}
		return loanDao.addToLoanTeam(loanModel, userModel,
		        utils.getLoggedInUser());
	}

	@Override
	@Transactional()
	public boolean removeFromLoanTeam(LoanVO loan, UserVO user) {

		Loan loanModel = this.parseLoanModel(loan);
		User userModel = this.parseUserModel(user);
		return loanDao.removeFromLoanTeam(loanModel, userModel);
	}

	@Override
	@Transactional
	public boolean removeFromLoanTeam(LoanVO loan,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance) {

		return loanDao.removeFromLoanTeam(this.parseLoanModel(loan),
		        this.parseHomeOwnInsMaster(homeOwnersInsurance));
	}

	@Override
	@Transactional
	public boolean removeFromLoanTeam(LoanVO loan,
	        TitleCompanyMasterVO titleCompany) {
		return loanDao.removeFromLoanTeam(this.parseLoanModel(loan),
		        this.parseTitleCompanyMaster(titleCompany));
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserVO> retreiveLoanTeam(LoanVO loanVO) {

		List<User> team = loanDao.retreiveLoanTeam(this.parseLoanModel(loanVO));

		return userProfileService.buildUserVOList(team);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserVO> retreiveLoanManagers(LoanVO loanVO) {
		List<UserVO> managerList = new ArrayList<UserVO>();
		List<UserVO> team = retreiveLoanTeam(loanVO);
		for (UserVO user : team) {
			if (null != user.getInternalUserDetail()) {
				if (UserRolesEnum.LM.equalsName(user.getInternalUserDetail()
				        .getInternalUserRoleMasterVO().getRoleName())) {
					managerList.add(user);
				}
			}
		}
		return managerList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoanVO> retreiveLoansAsManager(UserVO loanManager) {

		User manager = this.parseUserModel(loanManager);

		return this.buildLoanVOList(loanDao.retreiveLoansAsManager(manager));
	}

	@Override
	public Loan parseLoanModel(LoanVO loanVO) {

		if (loanVO == null)
			return null;

		Loan loan = new Loan();
		loan.setId(loanVO.getId());
		loan.setCreatedDate(loanVO.getCreatedDate());
		loan.setDeleted(loanVO.getDeleted());
		loan.setLoanEmailId(loanVO.getLoanEmailId());
		loan.setLqbFileId(loanVO.getLqbFileId());
		loan.setModifiedDate(loanVO.getModifiedDate());
		loan.setName(loanVO.getName());
		loan.setIsBankConnected(loanVO.getIsBankConnected());
		loan.setLockStatus(loanVO.getLockStatus());
		return loan;

	}

	private List<LoanVO> buildLoanVOList(List<Loan> loanList) {

		if (loanList == null)
			return null;

		List<LoanVO> voList = new ArrayList<LoanVO>();
		for (Loan loan : loanList) {
			voList.add(Loan.convertFromEntityToVO(loan));
		}

		return voList;
	}

	private User parseUserModel(UserVO userVO) {

		if (userVO == null)
			return null;

		User user = new User();

		user.setId(userVO.getId());
		if (userVO.getMobileAlertsPreference() != null) {
			user.setMobileAlertsPreference(userVO.getMobileAlertsPreference());
		}
		if (userVO.getCarrierInfo() != null) {
			user.setCarrierInfo(userVO.getCarrierInfo());
		}
		return user;
	}

	private LoanTeamVO buildLoanTeamVO(LoanTeam loanTeam) {
		if (loanTeam == null)
			return null;

		LoanTeamVO loanTeamVO = new LoanTeamVO();
		loanTeamVO.setId(loanTeam.getId());
		loanTeamVO.setUser(User.convertFromEntityToVO(loanTeam.getUser()));
		loanTeamVO.setActive(loanTeam.getActive());
		return loanTeamVO;

	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO retrieveDashboard(UserVO userVO) {

		// Get all loans this user has access to.
		List<Loan> loanList = loanDao.retrieveLoanForDashboard(this
		        .parseUserModel(userVO));
		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO retrieveDashboardForWorkLoans(UserVO userVO) {

		// Get new prospect and lead loans this user has access to.
		List<Loan> loanList = loanDao
		        .retrieveLoanByProgressStatus(
		                this.parseUserModel(userVO),
		                new int[] {
		                        LoanProgressStatusMasterEnum.NEW_LOAN
		                                .getStatusId(),
		                        LoanProgressStatusMasterEnum.IN_PROGRESS
		                                .getStatusId() });
		;
		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	private boolean checkIfUserIsSalesManager() {

		UserRolesEnum userRole = utils.getLoggedInUserRole();
		if (userRole.getRoleId() == (InternalUserRolesEum.SM.getRoleId())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO retrieveDashboardForArchiveLoans(UserVO userVO) {

		// Get declined , withdrawn and closed loans this user has access to.
		List<Loan> loanList = loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), new int[] {
		                LoanProgressStatusMasterEnum.SMCLOSED.getStatusId(),
		                LoanProgressStatusMasterEnum.WITHDRAWN.getStatusId(),
		                LoanProgressStatusMasterEnum.DECLINED.getStatusId() });

		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanCustomerVO retrieveDashboard(UserVO userVO, LoanVO loanVO) {

		// Get all loans this user has access to.
		Loan loan = loanDao.retrieveLoanForDashboard(userVO,
		        this.parseLoanModel(loanVO));
		LoanCustomerVO loanCustomerVO = this.buildLoanCustomerVoFromUser(loan);

		return loanCustomerVO;
	}

	/**
	 * it returns dashboardVO from list of loans
	 * 
	 * @param loanList
	 * @return
	 */
	private LoanDashboardVO buildLoanDashboardVoFromLoanList(List<Loan> loanList) {

		LoanDashboardVO loanDashboardVO = new LoanDashboardVO();
		List<LoanCustomerVO> loanCustomerVoList = new ArrayList<LoanCustomerVO>();

		if (loanList != null) {
			for (Loan loan : loanList) {
				LoanCustomerVO loanCustomerVO = this
				        .buildLoanCustomerVoFromUser(loan);
				loanCustomerVoList.add(loanCustomerVO);
			}
		}

		loanDashboardVO.setCustomers(loanCustomerVoList);
		// set no of loans as num_found
		loanDashboardVO.setNum_found(loanList.size());

		return loanDashboardVO;
	}

	/**
	 * return loanCustomerVo from loan
	 * 
	 * @param loan
	 * @return
	 */
	private LoanCustomerVO buildLoanCustomerVoFromUser(Loan loan) {

		User user = loan.getUser();
		CustomerDetail customerDetail = user.getCustomerDetail();
		List<LoanTeam> loanTeamList = loan.getLoanTeam();
		LoanCustomerVO loanCustomerVO = new LoanCustomerVO();

		loanCustomerVO.setTime(utils.getDateAndTimeForUserDashboard(loan
		        .getModifiedDate()));
		
		loanCustomerVO.setName(user.getFirstName() + " " + user.getLastName());
		loanCustomerVO.setProf_image(user.getPhotoImageUrl());
		loanCustomerVO.setPhone_no(user.getPhoneNumber());
		loanCustomerVO.setLoanID(loan.getId());
		loanCustomerVO.setUserID(user.getId());
		if (user.getUserRole() != null)
			loanCustomerVO.setRole(user.getUserRole().getLabel());
		loanCustomerVO.setLoanInitiatedOn(loan.getCreatedDate());
		loanCustomerVO.setLastActedOn(loan.getModifiedDate());
		loanCustomerVO.setLoanStatus(loan.getLoanProgressStatus()
		        .getLoanProgressStatus());

		loanCustomerVO.setLqbFileId(loan.getLqbFileId());
		/*
		 * TODO: Check if the logged in user is a Sales Manager. and show the
		 * name of the loan manager instead of processor.
		 */

		boolean processorPresent = Boolean.FALSE;
		boolean loanManagerPresent = Boolean.FALSE;
		String loanManagerList = "";
		if (loan.getLoanTeam() != null) {
			loanTeamList = loan.getLoanTeam();
			for (LoanTeam loanTeam : loanTeamList) {
				User loanUser = loanTeam.getUser();
				if (loanUser.getInternalUserDetail() != null) {
					InternalUserRoleMaster internalUserRoleMaster = loanUser
					        .getInternalUserDetail().getInternaUserRoleMaster();
					if (internalUserRoleMaster != null
					        && internalUserRoleMaster.getId() == InternalUserRolesEum.PC
					                .getRoleId()) {
						loanCustomerVO.setProcessor(loanUser.getFirstName()
						        + " " + loanUser.getLastName());
						processorPresent = Boolean.TRUE;
					} else if (checkIfUserIsSalesManager()) {
						if (loanUser.getInternalUserDetail()
						        .getInternaUserRoleMaster().getId() == InternalUserRolesEum.LM
						        .getRoleId() && loanTeam.getActive()!=null && loanTeam.getActive()) {
							loanManagerList = loanManagerList
							        + loanUser.getFirstName() + " "
							        + loanUser.getLastName() + ",";
							loanManagerPresent = Boolean.TRUE;
							processorPresent = Boolean.TRUE;

						}

					}
				}

			}

		}
		if (!processorPresent) {
			loanCustomerVO.setProcessor("-");
		}
		if (loanManagerPresent) {
			if (loanManagerList.endsWith(",")) {
				loanManagerList = loanManagerList.substring(0,
				        loanManagerList.length() - 1);
			}

			loanCustomerVO.setProcessor(loanManagerList);
		}

		loanCustomerVO.setPurpose(loan.getLoanType().getDescription());
		loanCustomerVO.setAlert_count("3");
		if (customerDetail != null) {
			// constructCreditScore(customerDetail.get);
			loanCustomerVO.setCredit_score(utils.constrtClickableCreditScore(
			        customerDetail, loan.getId()));

		} else {
			loanCustomerVO.setCredit_score("-");
		}

		loanCustomerVO.setFirstName(user.getFirstName());
		loanCustomerVO.setLastName(user.getLastName());
		loanCustomerVO.setEmailId(user.getEmailId());
		if (user.getMobileAlertsPreference() != null) {
			loanCustomerVO.setMobileAlertsPreference(user
			        .getMobileAlertsPreference());
		}
		if (user.getCarrierInfo() != null) {
			MobileCarriersEnum mobileCarrier = MobileCarriersEnum
			        .getCarrierNameForEmail(user.getCarrierInfo());
			loanCustomerVO.setCarrierInfo(mobileCarrier.getCarrierName());
		}
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		if (customerDetail != null) {
			customerDetailVO.setAddressCity(customerDetail.getAddressCity());
			customerDetailVO.setAddressState(customerDetail.getAddressState());
			customerDetailVO
			        .setAddressStreet(customerDetail.getAddressStreet());
			customerDetailVO.setAddressZipCode(customerDetail
			        .getAddressZipCode());

			if (null != customerDetail.getDateOfBirth()) {
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				String date = df.format(customerDetail.getDateOfBirth());
				customerDetailVO.setDateOfBirth(date);
			}
			customerDetailVO.setId(customerDetail.getId());
		}
		loanCustomerVO.setCustomerDetail(customerDetailVO);

		return loanCustomerVO;
	}

	/**
	 * return getLoanTeamListForLoan from loan
	 * 
	 * @param loan
	 * @return LoanTeamListVO
	 */
	@Override
	@Transactional(readOnly = true)
	public LoanTeamListVO getLoanTeamListForLoan(LoanVO loanVO) {

		LoanTeamListVO loanTeamListVO = new LoanTeamListVO();
		List<LoanTeamVO> loanTeamVOList = new ArrayList<LoanTeamVO>();
		List<LoanTeam> loanTeamList = loanDao.getLoanTeamList(this
		        .parseLoanModel(loanVO));
		if (loanTeamList == null)
			return null;

		for (LoanTeam loanTeam : loanTeamList) {
			LoanTeamVO loanTeamVO = this.buildLoanTeamVO(loanTeam);
			loanTeamVOList.add(loanTeamVO);
		}
		loanTeamListVO.setLoanTeamList(loanTeamVOList);

		return loanTeamListVO;

	}

	/**
	 * return getLoanTeamListForLoan from userVO
	 * 
	 * @param userVO
	 * @return LoanTeamListVO
	 */
	@Override
	@Transactional(readOnly = true)
	public LoansProgressStatusVO getLoansProgressForUser(Integer userId) {

		List<Loan> loanList = loanDao
		        .retrieveLoanForDashboard(new User(userId));
		LoansProgressStatusVO loansProgressStatusVO = this
		        .getLoansProgressStatusVoFromLoanList(loanList);

		return loansProgressStatusVO;

	}

	/**
	 * return LoansProgressStatusVO from loanList
	 * 
	 * @param loanList
	 * @return
	 */
	private LoansProgressStatusVO getLoansProgressStatusVoFromLoanList(
	        List<Loan> loanList) {

		LoansProgressStatusVO loansProgressStatusVO = new LoansProgressStatusVO();
		int newProspects, totalLeads, newLoans, inProgress, closed, withdrawn, declined;
		newProspects = totalLeads = newLoans = inProgress = closed = withdrawn = declined = 0;

		for (Loan loan : loanList) {
			int id = loan.getLoanProgressStatus().getId();

			if (id == 1)
				newProspects++;
			else if (id == 2)
				totalLeads++;
			else if (id == 3)
				newLoans++;
			else if (id == 4)
				inProgress++;
			else if (id == 5)
				closed++;
			else if (id == 6)
				withdrawn++;
			else if (id == 7)
				declined++;
		}

		loansProgressStatusVO.setClosed(closed);
		loansProgressStatusVO.setDeclined(declined);
		loansProgressStatusVO.setInProgress(inProgress);
		loansProgressStatusVO.setNewLoans(newLoans);
		loansProgressStatusVO.setTotalLeads(totalLeads);
		loansProgressStatusVO.setNewProspects(newProspects);
		loansProgressStatusVO.setWithdrawn(withdrawn);

		return loansProgressStatusVO;

	}

	@Override
	@Transactional(readOnly = true)
	public UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId) {
		LOG.info("in method fetchUploadedFromLoanNeedId for loanNeedId :  "
		        + loanNeedId);
		return loanDao.fetchUploadedFromLoanNeedId(loanNeedId);
	}

	@Override
	@Transactional
	public void saveWorkflowInfo(int loanID, int customerWorkflowID,
	        int loanManagerWFID) {
		// Loan loan = (Loan) loanDao.load(Loan.class, loanID);
		//
		// Hibernate.initialize(loan.getCustomerWorkflow());
		//
		// loan.setCustomerWorkflow(customerWorkflowID);
		//
		// loan.setLoanManagerWorkflow(loanManagerWFID);
		loanDao.updateWorkFlowItems(loanID, customerWorkflowID, loanManagerWFID);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO findWorkflowInfoById(int loanID) {
		Loan loan = loanDao.getLoanWorkflowDetails(loanID);
		LoanVO loanVO = null;
		if (loan != null) {
			loanVO = Loan.convertFromEntityToVO(loan);
			return loanVO;
		}
		return loanVO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Loan> getAllLoans() {
		List<Loan> loanList = loanDao.loadAll(Loan.class);
		return loanList;

	}

	@Override
	@Transactional
	public Loan completeLoanModel(LoanVO loanVO) {
		if (loanVO == null)
			return null;
		Loan loan = new Loan();
		try {
			User user = User.convertFromVOToEntity(loanVO.getUser());
			LOG.info("getCustomerDetail in LoanServiceIMPL "
			        + user.getCustomerDetail());
			// LOG.info("getCustomerDetail in LoanServiceIMPL "+user.getCustomerDetail().getCustomerEmploymentIncome());
			// Always, the loan state will be new Loan
			loan.setLoanProgressStatus(new LoanProgressStatusMaster(
			        LoanProgressStatusMasterEnum.NEW_LOAN));

			loan.setLoanType(LoanTypeMaster.convertVoToEntity(loanVO
			        .getLoanType()));

			loan.setId(loanVO.getId());
			loan.setUser(user);
			loan.setCreatedDate(new Date(System.currentTimeMillis()));
			loan.setDeleted(loanVO.getDeleted());
			loan.setLoanEmailId(loanVO.getLoanEmailId());
			loan.setLqbFileId(loanVO.getLqbFileId());
			loan.setModifiedDate(loanVO.getModifiedDate());
			loan.setName(loanVO.getName());
			// loan.setCreatedDate(new Date(System.currentTimeMillis()));

			loan.setLockedRate(loanVO.getLockedRate());
		} catch (Exception e) {

			LOG.error("Error in parse loan model", e);
		}

		return loan;
	}

	private int getId(UserVO defaultUser) {
		if (defaultUser == null) {
			return 0;
		}
		return defaultUser.getId();
	}

	@Override
	@Transactional
	public LoanVO createLoan(LoanVO loanVO) {

		Loan loan = null;
		// TODO: Also pass the LQBId
		loan = completeLoanModel(loanVO);

		int loanId = (int) loanDao.save(loan);
		LOG.info("Saving turn around time for loan" + loanId);
		saveAllLoanTurnAroundTimeForLoan(loanId);
		LOG.info("Saved turn around time");
		addDefaultLoanTeam(loanVO, loanId);

		LOG.info("Added team");
		loanVO.setLoanEmailId(utils.generateLoanEmail(loanVO.getUser()
		        .getUsername()));
		loanDao.updateLoanEmail(loanId, loanVO.getLoanEmailId());

		LOG.info("Added Loan Email");
		// Invoking the workflow activities to trigger
		loan.setId(loanId);
		loanVO.setId(loanId);
		return loanVO;
	}

	private void updateLoanTeamList(List<LoanTeam> loanTeam, User user,
	        int loanId) {
		for (LoanTeam loanTeam2 : loanTeam) {
			if (loanTeam2.getUser().getEmailId().equals(user.getEmailId())) {
				// User already added in the team
				return;
			}

		}
		LoanTeam e = new LoanTeam();
		e.setUser(user);
		Loan loan = new Loan(loanId);
		e.setLoan(loan);
		e.setActive(Boolean.TRUE);
		e.setAssignedOn(new Date(System.currentTimeMillis()));

		loanTeam.add(e);
	}

	private void addDefaultLoanTeam(LoanVO loanVO, int loanId) {
		// TODO Auto-generated method stub

		// Add the user being registered to the team
		User user = User.convertFromVOToEntity(loanVO.getUser());
		List<LoanTeam> loanTeam = new ArrayList<LoanTeam>();
		updateLoanTeamList(loanTeam, user, loanId);
		// Check if realtor email is valid

		if (loanVO.getRealtorEmail() != null) {
			LOG.debug("User is from realtor referal path");
			User realtor = userProfileService.findUserByMail(loanVO
			        .getRealtorEmail());

			if (realtor != null && realtor.getId() != 0) {
				LOG.debug("Adding realtor to loan: " + loanId + "realtor id: "
				        + realtor.getId());
				// User is valid. Update the loan team
				updateLoanTeamList(loanTeam, realtor, loanId);
				if (realtor.getRealtorDetail() != null
				        && realtor.getRealtorDetail().getDefaultLoanManager() != null) {
					// If the realtor has a defaul loan manager, assign him as
					// well
					updateLoanTeamList(loanTeam, realtor.getRealtorDetail()
					        .getDefaultLoanManager(), loanId);
				}

			}
		}
		boolean defaultManagerAdded = Boolean.FALSE;
		if (loanVO.getLmEmail() != null) {
			LOG.debug("LM email id from registration path: "
			        + loanVO.getLmEmail());
			// Check if loanmanageremail is valid
			userProfileService.findUserByMail(loanVO.getLmEmail());
			User loanManager = userProfileService.findUserByMail(loanVO
			        .getLmEmail());

			if (loanManager != null && loanManager.getId() != 0) {
				LOG.debug("Adding LM to loan: " + loanId + "LM id: "
				        + loanManager.getId());
				// User is valid. Update the loan team
				updateLoanTeamList(loanTeam, loanManager, loanId);
				defaultManagerAdded = Boolean.TRUE;
			}

		}

		LOG.debug("Was a loan manager added for loan: " + loanId + " : "
		        + defaultManagerAdded);
		if (!defaultManagerAdded) {
			LOG.debug("USer has come from customer engagement path, assigning loan managr based on zip code entered: "
			        + loanVO.getUserZipCode() + " for loan " + loanId);
			String stateName = stateLookupService.getStateCodeByZip(loanVO
			        .getUserZipCode());
			LOG.debug("State name returned from lookup: " + stateName
			        + " for loan " + loanId);
			UserVO defaultUser = assignmentHelper
			        .getDefaultLoanManager(stateName);

			if (defaultUser != null) {
				LOG.debug("This user will be assigned to team: "
				        + defaultUser.getId() + " for loan " + loanId);
				updateLoanTeamList(loanTeam,
				        User.convertFromVOToEntity(defaultUser), loanId);
			} else {
				LOG.error("No Loan manager was assigned to team with loan object: "
				        + loanVO);
			}

		}
		if (LOG.isDebugEnabled()) {
			if (loanTeam != null) {
				for (LoanTeam loanTeam2 : loanTeam) {
					if (loanTeam2.getUser() != null) {
						LOG.debug("This user will be added in the loan: "
						        + loanTeam2.getUser().getId()
						        + " for the loan " + loanId);
					}

				}
			}
		}
		loanDao.saveAll(loanTeam);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TitleCompanyMasterVO> findTitleCompanyByName(
	        TitleCompanyMasterVO titleCompanyVO) {
		// TODO Auto-generated method stub
		if (titleCompanyVO == null || titleCompanyVO.getName() == null)
			return Collections.EMPTY_LIST;

		return buildTitleCompanyMasterVO(loanDao
		        .findTitleCompanyByName(parseTitleCompanyMaster(titleCompanyVO)));
	}

	@Override
	@Transactional(readOnly = true)
	public TitleCompanyMasterVO findTitleCompanyOfLoan(LoanVO loan) {

		return this.buildTitleCompanyMasterVO(loanDao
		        .findTitleCompanyOfLoan(this.parseLoanModel(loan)));

	}

	@Override
	@Transactional(readOnly = true)
	public HomeOwnersInsuranceMasterVO findHomeOwnersInsuranceCompanyOfLoan(
	        LoanVO loan) {

		return this
		        .buildHomeOwnersInsuranceMasterVO(loanDao
		                .findHomeOwnersInsuranceCompanyOfLoan(this
		                        .parseLoanModel(loan)));

	}

	public List<TitleCompanyMasterVO> buildTitleCompanyMasterVO(
	        List<TitleCompanyMaster> masterList) {
		if (masterList == null)
			return null;
		List<TitleCompanyMasterVO> companyMasterVOs = new ArrayList<TitleCompanyMasterVO>();
		for (TitleCompanyMaster master : masterList) {
			companyMasterVOs.add(buildTitleCompanyMasterVO(master));
		}
		return companyMasterVOs;
	}

	public TitleCompanyMasterVO buildTitleCompanyMasterVO(
	        TitleCompanyMaster master) {
		if (master == null)
			return null;
		TitleCompanyMasterVO companyMasterVO = new TitleCompanyMasterVO();
		companyMasterVO.setId(master.getId());
		companyMasterVO.setName(master.getName());
		companyMasterVO.setEmailID(master.getEmailID());
		companyMasterVO.setAddress(master.getAddress());
		companyMasterVO.setAddedBy(User.convertFromEntityToVO(master
		        .getAddedBy()));
		companyMasterVO.setPhoneNumber(master.getPhoneNumber());
		companyMasterVO.setPrimaryContact(master.getPrimaryContact());
		companyMasterVO.setFax(master.getFax());

		return companyMasterVO;

	}

	public TitleCompanyMaster parseTitleCompanyMaster(TitleCompanyMasterVO vo) {
		if (vo == null)
			return null;
		TitleCompanyMaster companyMaster = new TitleCompanyMaster();
		companyMaster.setId(vo.getId());
		companyMaster.setName(vo.getName());
		companyMaster.setEmailID(vo.getEmailID());
		companyMaster.setAddress(vo.getAddress());
		companyMaster.setAddedBy(User.convertFromVOToEntity(vo.getAddedBy()));
		companyMaster.setPhoneNumber(vo.getPhoneNumber());
		companyMaster.setPrimaryContact(vo.getPrimaryContact());
		companyMaster.setFax(vo.getFax());

		return companyMaster;
	}

	@Override
	@Transactional(readOnly = true)
	public List<HomeOwnersInsuranceMasterVO> findHomeOwnInsByName(
	        HomeOwnersInsuranceMasterVO homeOwnInsVO) {
		// TODO Auto-generated method stub
		if (homeOwnInsVO == null || homeOwnInsVO.getName() == null)
			return Collections.EMPTY_LIST;

		return buildHomeOwnersInsuranceMasterVO(loanDao
		        .findHomeOwnInsByName(parseHomeOwnInsMaster(homeOwnInsVO)));
	}

	public List<HomeOwnersInsuranceMasterVO> buildHomeOwnersInsuranceMasterVO(
	        List<HomeOwnersInsuranceMaster> masterList) {
		if (masterList == null)
			return null;
		List<HomeOwnersInsuranceMasterVO> companyMasterVOs = new ArrayList<HomeOwnersInsuranceMasterVO>();
		for (HomeOwnersInsuranceMaster master : masterList) {
			companyMasterVOs.add(buildHomeOwnersInsuranceMasterVO(master));
		}
		return companyMasterVOs;
	}

	public HomeOwnersInsuranceMasterVO buildHomeOwnersInsuranceMasterVO(
	        HomeOwnersInsuranceMaster master) {
		if (master == null)
			return null;
		HomeOwnersInsuranceMasterVO companyMasterVO = new HomeOwnersInsuranceMasterVO();
		companyMasterVO.setId(master.getId());
		companyMasterVO.setName(master.getName());
		companyMasterVO.setEmailID(master.getEmailID());
		companyMasterVO.setAddress(master.getAddress());

		companyMasterVO.setAddedBy(User.convertFromEntityToVO(master
		        .getAddedBy()));
		companyMasterVO.setPhoneNumber(master.getPhoneNumber());
		companyMasterVO.setPrimaryContact(master.getPrimaryContact());
		companyMasterVO.setFax(master.getFax());

		return companyMasterVO;

	}

	public HomeOwnersInsuranceMaster parseHomeOwnInsMaster(
	        HomeOwnersInsuranceMasterVO vo) {
		if (vo == null)
			return null;
		HomeOwnersInsuranceMaster companyMaster = new HomeOwnersInsuranceMaster();
		companyMaster.setId(vo.getId());
		companyMaster.setName(vo.getName());
		companyMaster.setEmailID(vo.getEmailID());
		companyMaster.setAddedBy(User.convertFromVOToEntity(vo.getAddedBy()));
		companyMaster.setPhoneNumber(vo.getPhoneNumber());
		companyMaster.setPrimaryContact(vo.getPrimaryContact());
		companyMaster.setFax(vo.getFax());

		return companyMaster;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanNeedsList fetchByNeedId(Integer needId, Integer loanId) {
		// TODO Auto-generated method stub
		return loanDao.fetchByNeedId(needId, loanId);
	}

	@Override
	@Transactional
	public HomeOwnersInsuranceMasterVO addHomeOwnInsCompany(
	        HomeOwnersInsuranceMasterVO vo) {
		return this.buildHomeOwnersInsuranceMasterVO(loanDao
		        .addHomeOwnInsCompany(this.parseHomeOwnInsMaster(vo)));
	}

	@Override
	@Transactional
	public TitleCompanyMasterVO addTitleCompany(TitleCompanyMasterVO vo) {
		// Send the Email here.
		return this.buildTitleCompanyMasterVO(loanDao.addTitleCompany(this
		        .parseTitleCompanyMaster(vo)));
	}

	@Override
	@Transactional
	public HomeOwnersInsuranceMasterVO addToLoanTeam(LoanVO loan,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance, UserVO addedBy) {

		loanDao.addToLoanTeam(this.parseLoanModel(loan),
		        this.parseHomeOwnInsMaster(homeOwnersInsurance),
		        User.convertFromVOToEntity(addedBy));
		// Send the email Here.
		HomeOwnersInsuranceMasterVO homeOwnersInsuranceMasterVO = this
		        .buildHomeOwnersInsuranceMasterVO((HomeOwnersInsuranceMaster) loanDao
		                .load(HomeOwnersInsuranceMaster.class,
		                        homeOwnersInsurance.getId()));

		sendHomeInsuranceEmail((Loan) loanDao.load(Loan.class, loan.getId()),
		        homeOwnersInsuranceMasterVO);
		return homeOwnersInsuranceMasterVO;
	}

	private void sendTitleCompanyEmailVO(Loan loanVO,
	        TitleCompanyMasterVO titleCompanyMasterVO) {

		if (loanVO != null) {
			String emailTemplateKey = CommonConstants.TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_TITLE_COMPANY;

			String subject = CommonConstants.SUBJECT_TITLE_COMPANY;
			Template template = templateService
			        .getTemplateByKey(emailTemplateKey);
			if (template == null) {
				LOG.error("Send Email could not be found Template Found ");
				return;
			}
			EmailVO emailEntity = new EmailVO();
			String[] names = new String[1];
			names[0] = titleCompanyMasterVO.getName();
			Map<String, String[]> substitutions = new HashMap<String, String[]>();
			substitutions.put("-name-", names);

			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
			emailEntity.setSenderName(CommonConstants.SENDER_NAME);
			emailEntity.setSubject(subject);
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
			emailRecipientVO.setEmailID(titleCompanyMasterVO.getEmailID());
			emailRecipientVO.setRecipientName(titleCompanyMasterVO.getName());
			recipients.add(emailRecipientVO);
			emailEntity.setRecipients(recipients);
			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(template.getValue());
			emailEntity.setBody("---");
			if (loanVO.getUser() != null
			        && loanVO.getUser().getUsername() != null) {
				List<String> ccList = new ArrayList<String>();
				ccList.add(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
				emailEntity.setCCList(ccList);
			}
			try {
				sendEmailService.sendMail(emailEntity, true);
			} catch (InvalidInputException e) {
				LOG.error("Exception Caught " + e.getMessage());
			} catch (UndeliveredEmailException e) {
				LOG.error("Exception Caught " + e.getMessage());
			}
		}
	}

	private void sendHomeInsuranceEmail(Loan loanVO,
	        HomeOwnersInsuranceMasterVO homeOwnersInsurance) {

		if (loanVO != null) {
			String emailTemplateKey = CommonConstants.TEMPLATE_KEY_NAME_WELCOME_TO_NEWFI_HOME_OWNER_INSURANCE_COMPANY;

			String subject = CommonConstants.SUBJECT_HOME_INSUR_COMPANY;
			Template template = templateService
			        .getTemplateByKey(emailTemplateKey);
			if (template == null) {
				LOG.error("Send Email could not be found Template Found ");
				return;
			}
			EmailVO emailEntity = new EmailVO();
			String[] names = new String[1];
			names[0] = homeOwnersInsurance.getName();
			Map<String, String[]> substitutions = new HashMap<String, String[]>();
			substitutions.put("-name-", names);

			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
			emailEntity.setSenderName(CommonConstants.SENDER_NAME);
			emailEntity.setSubject(subject);
			List<EmailRecipientVO> recipients = new ArrayList<EmailRecipientVO>();
			EmailRecipientVO emailRecipientVO = new EmailRecipientVO();
			emailRecipientVO.setEmailID(homeOwnersInsurance.getEmailID());
			emailRecipientVO.setRecipientName(homeOwnersInsurance.getName());
			recipients.add(emailRecipientVO);
			emailEntity.setRecipients(recipients);
			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(template.getValue());
			emailEntity.setBody("---");
			if (loanVO.getUser() != null
			        && loanVO.getUser().getUsername() != null) {
				List<String> ccList = new ArrayList<String>();
				ccList.add(loanVO.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
				emailEntity.setCCList(ccList);
			}
			try {
				sendEmailService.sendMail(emailEntity, true);
			} catch (InvalidInputException e) {
				LOG.error("Exception Caught " + e.getMessage());
			} catch (UndeliveredEmailException e) {
				LOG.error("Exception Caught " + e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public TitleCompanyMasterVO addToLoanTeam(LoanVO loan,
	        TitleCompanyMasterVO titleCompany, UserVO addedBy) {

		loanDao.addToLoanTeam(this.parseLoanModel(loan),
		        this.parseTitleCompanyMaster(titleCompany),
		        User.convertFromVOToEntity(addedBy));

		TitleCompanyMasterVO titleCompanyMasterVO = this
		        .buildTitleCompanyMasterVO(((TitleCompanyMaster) loanDao.load(
		                TitleCompanyMaster.class, titleCompany.getId())));
		sendTitleCompanyEmailVO((Loan) loanDao.load(Loan.class, loan.getId()),
		        titleCompanyMasterVO);
		return titleCompanyMasterVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanMilestone findLoanMileStoneByLoan(Loan loan,
	        String loanMilestoneMAsterName) {
		return loanDao.findLoanMileStoneByLoan(loan, loanMilestoneMAsterName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Loan> getAllActiveLoan() {
		return loanDao.getAllActiveLoan();
	}

	@Override
	@Transactional(readOnly = true)
	public List<LoanMilestoneMaster> getLoanMilestoneByLoanType(
	        LoanTypeMaster loanTypeMaster) {
		return loanMilestoneMasterDao.findByLoanType(loanTypeMaster);
	}

	@Override
	@Transactional
	public LoanMilestone saveLoanMilestone(LoanMilestone loanMilestone) {
		int loanMilestoneId = (Integer) loanMilestoneDao.save(loanMilestone);
		loanMilestone.setId(loanMilestoneId);
		return loanMilestone;
	}

	@Override
	@Transactional
	public void updateLoanMilestone(LoanMilestone loanMilestone) {

		loanMilestoneDao.update(loanMilestone);
	}

	@Override
	@Transactional(readOnly = true)
	public ExtendedLoanTeamVO findExtendedLoanTeam(LoanVO loanVO) {
		ExtendedLoanTeamVO extendedLoanTeamVO = new ExtendedLoanTeamVO();
		List<UserVO> team = this.retreiveLoanTeam(loanVO);
		extendedLoanTeamVO.setUsers(team);
		TitleCompanyMasterVO titleCompanyMaster = this
		        .findTitleCompanyOfLoan(loanVO);
		extendedLoanTeamVO.setTitleCompany(titleCompanyMaster);
		HomeOwnersInsuranceMasterVO homeOwnersInsuranceMaster = this
		        .findHomeOwnersInsuranceCompanyOfLoan(loanVO);
		extendedLoanTeamVO.setHomeOwnInsCompany(homeOwnersInsuranceMaster);
		return extendedLoanTeamVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanTurnAroundTimeVO retrieveTurnAroundTimeByLoan(Integer loanId,
	        Integer workFlowItemId) {
		LoanTurnAroundTime aroundTime = loanTurnAroundTimeDao
		        .loadLoanTurnAroundByLoanAndWorkitem(loanId, workFlowItemId);
		if (aroundTime != null) {
			LoanTurnAroundTimeVO aroundTimeVO = new LoanTurnAroundTimeVO();
			aroundTimeVO.setHours(aroundTime.getHours());
			aroundTimeVO.setLoanId(loanId);
			aroundTimeVO.setWorkItemMasterId(workFlowItemId);
			return aroundTimeVO;
		} else
			return null;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanNeedsList fetchLoanNeedByFileId(
	        UploadedFilesList uploadedFileList) {
		return loanDao.fetchLoanNeedsByFileId(uploadedFileList);
	}

	@Override
	public void updateLoanNeedList(LoanNeedsList loanNeedList) {
		loanNeedListDao.update(loanNeedList);

	}

	@Override
	@Transactional
	public void saveAllLoanTurnAroundTimeForLoan(Integer loanId) {
		List<MileStoneTurnAroundTimeVO> aroundTimeVOs = aroundTimeService
		        .loadAllMileStoneTurnAround();
		List<LoanTurnAroundTime> turnAroundTimes = new ArrayList<LoanTurnAroundTime>();
		LoanTurnAroundTime aroundTime = null;
		for (MileStoneTurnAroundTimeVO mileStoneTurnAroundTimeVO : aroundTimeVOs) {
			if (mileStoneTurnAroundTimeVO.getHours() != null) {
				aroundTime = new LoanTurnAroundTime();
				aroundTime.setCreatedDate(new Date());
				aroundTime.setHours(mileStoneTurnAroundTimeVO.getHours());
				aroundTime.setLoan(new Loan(loanId));
				aroundTime.setWorkflowItemMaster(new WorkflowItemMaster(
				        mileStoneTurnAroundTimeVO.getWorkflowItemMasterId()));
				turnAroundTimes.add(aroundTime);
			}
		}

		loanTurnAroundTimeDao.saveAllLoanTurnAroundTimeForLoan(turnAroundTimes);
	}

	@Override
	@Transactional
	public void assignNeedsToLoan(LoanNeedsList loanNeedsList) {
		loanNeedListDao.saveOrUpdate(loanNeedsList);

	}

	@Override
	@Transactional
	public LoanNeedsList findLoanNeedsList(Loan loan,
	        NeedsListMaster needsListMaster) {
		return loanNeedListDao.findLoanNeedsList(loan, needsListMaster);
	}

	@Transactional
	@Override
	public int getApplicationFee(int loanId) throws NoRecordsFetchedException,
	        InvalidInputException {

		Loan loan = (Loan) loanDao.load(Loan.class, loanId);
		if (loan.getAppFee() != null) {
			LOG.debug("Loan Manager has specified a loan app fee. overriding the rate calculation fee");
			return loan.getAppFee().intValueExact();
		} else {
			return CommonConstants.DEFAULT_APPLICATION_FEE;
		}
	}

	@Override
	@Transactional
	public void updateLoan(Integer loanId, Boolean rateLocked, String rateVo) {
		loanDao.updateLoan(loanId, rateLocked, rateVo);

	}

	@Override
	@Transactional
	public void updateLoan(Loan loan) {
		loanDao.update(loan);

	}

	@Override
	@Transactional
	public List<Loan> getLoansInActiveStatus() {
		return loanDao.getLoanInActiveStatus();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<LoanMilestoneMaster> getLoanMilestoneMasterList() {
		return loanMilestoneMasterDao.loadAll(LoanMilestoneMaster.class);
	}

	@Override
	@Transactional(readOnly = true)
	public UserLoanStatus getUserLoanStaus(LoanVO loan) {

		UserLoanStatus loanStatus = new UserLoanStatus();

		loanStatus.setLoanPurpose(loan.getLoanType().getDescription());

		CustomerDetail customerDetail = userProfileService
		        .getCustomerDetail(loan.getUser().getId());

		if (customerDetail != null) {
			// constructCreditScore(customerDetail.get);
			loanStatus.setCreditInformation(utils.constrtClickableCreditScore(
			        customerDetail, loan.getId()));

		} else {
			loanStatus.setCreditInformation("-");
		}

		if (loan.getLockedRate() != null) {
			loanStatus.setLockRate(loan.getLockedRate());
		} else {
			loanStatus.setLockRate("Not Locked");
		}
		// TODO: We do not know how to get this information
		loanStatus.setLockExpiryDate(null);

		return loanStatus;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO searchUsers(LoanUserSearchVO searchVO) {

		List<Loan> loanList = loanDao.retrieveLoanDetailsOnSearch(searchVO);
		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional
	public void updateLoanAppFee(int loanId, BigDecimal newAppFee) {
		loanDao.updateLoanAppFee(loanId, newAppFee);
	}

	@Override
	@Transactional
	public LoanAppFormVO retrieveLoanAppForm(UserVO userVO) {
		LoanVO loanVO = getActiveLoanOfUser(userVO);
		if (null != loanVO) {
			userVO.setDefaultLoanId(loanVO.getId());
			LoanAppFormVO loanAppFormVO = new LoanAppFormVO();
			loanAppFormVO.setUser(userVO);
			loanAppFormVO.setLoan(loanVO);
			loanAppFormVO = loanAppFormService.find(loanAppFormVO);
			LOG.info("inside LoanServiceImpl retrive LoanAppForm"
			        + loanAppFormVO.getRefinancedetails().getId());
			LOG.info("inside LoanServiceImpl retrive LoanAppForm"
			        + loanAppFormVO.getPropertyTypeMaster().getId());

			return loanAppFormVO;
		}
		return null;
	}

	@Override
	@Transactional
	public void setExpiryDateToPurchaseDocument(Integer loanId, Long date) {
		loanDao.setExpiryDateToPurchaseDocument(loanId, date);
	}

	@Override
	@Transactional
	public String saveLoanMilestone(int loanId, int masterMileStoneId,
	        String comments) {
		String status = null;
		try {
			Loan loan = new Loan(loanId);
			LoanMilestone mileStone = new LoanMilestone();
			mileStone.setLoan(loan);
			LoanMilestoneMaster loanMilestoneMaster = new LoanMilestoneMaster();
			loanMilestoneMaster.setId(masterMileStoneId);
			mileStone.setLoanMilestoneMaster(loanMilestoneMaster);
			mileStone.setComments(comments);
			mileStone.setStatusUpdateTime(new Date());
			this.saveLoanMilestone(mileStone);
			status = WorkItemStatus.COMPLETED.getStatus();
			return status;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return status;
		}
	}

	@Override
	@Transactional
	public void saveLoanProgress(int loanId,
	        LoanProgressStatusMaster progressValue) {
		loanDao.updateLoanProgress(loanId, progressValue);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO wrapperCallForDashboard(Integer loanID) {
		Loan loan = this.fetchLoanById(loanID);
		String lqbLoanId = loan.getLqbFileId();
		LoanVO loanVO = Loan.convertFromEntityToVO(loan);
		LOG.info("--" + LoanTypeMasterEnum.PUR.toString());
		if (loanVO.getLoanType().getLoanTypeCd()
		        .equals(LoanTypeMasterEnum.PUR.toString())) {
			UploadedFilesList file = needListService
			        .fetchPurchaseDocumentBasedOnPurchaseContract(loanID);
			loanVO.getLoanType().setUploadedFiles(
			        uploadedFilesListService.buildUpdateFileVo(file));
		}

		if (loanVO != null) {
			loanVO.setLoanTeam(this.retreiveLoanTeam(loanVO));
			loanVO.setExtendedLoanTeam(this.findExtendedLoanTeam(loanVO));
			UserLoanStatus loanStatus = this.getUserLoanStaus(loanVO);

			LoanMilestone loanMilestone = findLoanMileStoneByLoan(loan,
			        Milestones.LM_DECISION.getMilestoneKey());
			if (loanMilestone != null) {
				loanStatus.setCreditDecission(loanMilestone.getComments());
			}
			loanVO.setUserLoanStatus(loanStatus);
			String lqbUrl;
			if (lqbLoanId == null || lqbLoanId.isEmpty()) {
				loanVO.setLqbInformationAvailable(Boolean.FALSE);
				loanVO.setLqbUrl("-");
			} else {
				lqbUrl = userProfileService.getLQBUrl(utils.getLoggedInUser()
				        .getId(), loanID);

				if (lqbUrl != null && lqbUrl.equals(lqbDefaultUrl)) {
					loanVO.setLqbInformationAvailable(Boolean.FALSE);
				} else {
					loanVO.setLqbInformationAvailable(Boolean.TRUE);
					loanVO.setLqbUrl(lqbUrl);
				}

			}

			String docId = needListService.checkCreditReport(loanID);
			if (docId != null && !docId.isEmpty()) {
				loanVO.setCreditReportUrl(systemBaseUrl
				        + CommonConstants.FILE_DOWNLOAD_SERVLET + docId
				        + CommonConstants.THUMBNAIL_PARAM);
			} else {
				loanVO.setCreditReportUrl("");
			}

		}
		return loanVO;
	}

	@Override
	@Transactional
	public void sendApplicationFinishedEmail(Loan loan) {
		if (loan != null) {
			LoanAppForm loanAppForm = loan.getLoanAppForms().get(0);
			if (loanAppForm != null) {
				Float appCompletionStatus = loanAppForm
				        .getLoanAppFormCompletionStatus();
				if (appCompletionStatus == 100.00) {
					try {
						sendAppFinishedEmail(loan);
					} catch (InvalidInputException | UndeliveredEmailException e) {
						LOG.error("Exception caught " + e.getMessage());
					}
				}
			}
		}
	}

	private void sendAppFinishedEmail(Loan loan) throws InvalidInputException,
	        UndeliveredEmailException {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_APPLICATION_FINISHED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loan.getUser()
		        .getFirstName() + " " + loan.getUser().getLastName() });
		substitutions.put("-url-", new String[] { baseUrl });
		recipientVO.setEmailID(loan.getUser().getEmailId());
		emailEntity.setRecipients(new ArrayList<EmailRecipientVO>(Arrays
		        .asList(recipientVO)));
		if (loan.getUser() != null) {
			emailEntity.setSenderEmailId(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Password Not Updated! Pelase Update.");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		sendGridEmailService.sendMail(emailEntity);
	}

	@Override
	@Transactional(readOnly = true)
	public void sendRateLocked(Integer loanID) throws InvalidInputException,
	        UndeliveredEmailException {
		LoanVO loan = getLoanByID(loanID);
		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_RATES_LOCKED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loan.getUser()
		        .getFirstName() + " " + loan.getUser().getLastName() });
		substitutions.put(
		        "-rate-",
		        new String[] { loan.getLockedRate() != null ? loan
		                .getLockedRate() : "" });
		substitutions.put("-rateexpirationdate-", new String[] { " " });

		if (loan.getUser() != null) {
			emailEntity.setSenderEmailId(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Rates Locked");
		emailEntity.setTokenMap(substitutions);

		emailEntity.setTemplateId(template.getValue());
		messageServiceHelper
		        .generatePrivateMessage(loanID,
		                LoanStatus.ratesLockedRequested,
		                utils.getLoggedInUser(), false);
		sendEmailService.sendEmailForCustomer(emailEntity, loan.getId(),
		        template);

		// TODO send mail to LM ............... change template id and
		// substitutions
		emailEntity = new EmailVO();
		template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_RATES_LOCKED);
		// We create the substitutions map
		substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loan.getUser()
		        .getFirstName() + " " + loan.getUser().getLastName() });
		substitutions.put(
		        "-rate-",
		        new String[] { loan.getLockedRate() != null ? loan
		                .getLockedRate() : "" });
		substitutions.put("-rateexpirationdate-", new String[] { " " });

		if (loan.getUser() != null) {
			emailEntity.setSenderEmailId(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Rates Locked");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		String loanManagerUsername = null;
		LoanTeamListVO loanTeamList = getLoanTeamListForLoan(loan);
		for (LoanTeamVO loanTeam : loanTeamList.getLoanTeamList()) {
			if (loanTeam.getUser() != null) {
				if (loanTeam.getUser().getInternalUserDetail() != null) {
					if (loanTeam.getUser().getInternalUserDetail()
					        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
					        .getRoleId()) {
						loanManagerUsername = loanTeam.getUser().getUsername();
					}
				}
			}
		}
		List<String> loanManagerccList = new ArrayList<String>();
		if (loanManagerUsername != null) {
			loanManagerccList.add(CommonConstants.SENDER_DEFAULT_USER_NAME
			        + "-" + loanManagerUsername + "-" + loan.getId()
			        + CommonConstants.SENDER_EMAIL_ID);

			emailEntity.setCCList(loanManagerccList);
		}

		sendEmailService.sendEmailForLoanManagers(emailEntity, loan.getId(),
		        template);
	}

	@Override
	@Transactional(readOnly = true)
	public void sendRateLockRequested(Integer loanID,
	        LoanLockRateVO loanLockRateVO) throws InvalidInputException,
	        UndeliveredEmailException {
		// Change Template and substitutations for rate lock requested
		LoanVO loan = getLoanByID(loanID);
		EmailVO emailEntity = new EmailVO();
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_RATE_LOCK_REQUESTED);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { loan.getUser()
		        .getFirstName() + " " + loan.getUser().getLastName() });
		substitutions.put("-rate-", new String[] { loanLockRateVO
		        .getRequestedRate() != null ? loanLockRateVO.getRequestedRate()
		        : "" });
		substitutions.put("-rateexpirationdate-", new String[] { " " });
		String loanManagerName = null;
		List<UserVO> loanManagersList = new ArrayList<UserVO>();
		LoanTeamListVO loanTeamListVO = getLoanTeamListForLoan(loan);
		if (loanTeamListVO != null) {
			if (loanTeamListVO.getLoanTeamList() != null) {
				for (LoanTeamVO loanTeam : loanTeamListVO.getLoanTeamList()) {
					if (loanTeam.getUser() != null) {
						if (loanTeam.getUser().getInternalUserDetail() != null) {
							if (loanTeam.getUser().getInternalUserDetail()
							        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
							        .getRoleId()) {
								loanManagersList.add(loanTeam.getUser());
							}
						}
					}
				}
			}
		}
		int count = 0;
		if (loanManagersList.size() == 1) {
			loanManagerName = loanManagersList.get(0).getFirstName() + " "
			        + loanManagersList.get(0).getLastName();
		} else {
			for (UserVO userVO : loanManagersList) {
				if (utils.getLoggedInUser() != null) {
					if (userVO.getId() == utils.getLoggedInUser().getId()) {
						loanManagerName = userVO.getFirstName() + " "
						        + userVO.getLastName();
						count = count + 1;
					}
				} else {
					loanManagerName = userVO.getFirstName() + " "
					        + userVO.getLastName();
				}
			}
		}
		if (count == 0) {
			loanManagerName = loanManagersList.get(0).getFirstName() + " "
			        + loanManagersList.get(0).getLastName();
		}
		substitutions
		        .put("-loanmanagername-", new String[] { loanManagerName });

		if (loan.getUser() != null) {
			emailEntity.setSenderEmailId(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);
		} else {
			emailEntity
			        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
			                + CommonConstants.SENDER_EMAIL_ID);
		}
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Rates Lock Requested");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());

		/*
		 * messageServiceHelper .generatePrivateMessage(loanID,
		 * LoanStatus.ratesLockedRequested, utils.getLoggedInUser(), false);
		 */

		String loanManagerUsername = null;
		LoanTeamListVO loanTeamList = getLoanTeamListForLoan(loan);
		for (LoanTeamVO loanTeam : loanTeamList.getLoanTeamList()) {
			if (loanTeam.getUser() != null) {
				if (loanTeam.getUser().getInternalUserDetail() != null) {
					if (loanTeam.getUser().getInternalUserDetail()
					        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
					        .getRoleId()) {
						loanManagerUsername = loanTeam.getUser().getUsername();
					}
				}
			}
		}
		List<String> loanManagerccList = new ArrayList<String>();
		if (loanManagerUsername != null) {
			loanManagerccList.add(CommonConstants.SENDER_DEFAULT_USER_NAME
			        + "-" + loanManagerUsername + "-" + loan.getId()
			        + CommonConstants.SENDER_EMAIL_ID);

			emailEntity.setCCList(loanManagerccList);
			sendEmailService.sendEmailForLoanManagers(emailEntity,
			        loan.getId(), template);
			emailEntity.setCCList(null);
		}
		List<String> ccList = new ArrayList<String>();
		ccList.add(loan.getUser().getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);

		sendEmailService.sendEmailForCustomer(emailEntity, loan.getId(),
		        template);

	}

	@Override
	@Transactional(readOnly = true)
	public void sendNoproductsAvailableEmail(Integer loanId) {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();
		LoanVO loan = getLoanByID(loanId);
		if (loan != null) {
			Template template = templateService
			        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE);
			// We create the substitutions map
			Map<String, String[]> substitutions = new HashMap<String, String[]>();
			substitutions.put("-name-", new String[] { loan.getUser()
			        .getFirstName() + " " + loan.getUser().getLastName() });
			recipientVO.setEmailID(loan.getUser().getEmailId());
			if (loan.getUser() != null) {
				emailEntity.setSenderEmailId(loan.getUser().getUsername()
				        + CommonConstants.SENDER_EMAIL_ID);
			} else {
				emailEntity
				        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
				                + CommonConstants.SENDER_EMAIL_ID);
			}
			emailEntity.setSenderName(CommonConstants.SENDER_NAME);
			emailEntity.setSubject("No Products Available");
			emailEntity.setTokenMap(substitutions);
			emailEntity.setTemplateId(template.getValue());
			List<String> ccList = new ArrayList<String>();
			ccList.add(loan.getUser().getUsername()
			        + CommonConstants.SENDER_EMAIL_ID);

			emailEntity.setCCList(ccList);
			try {

				sendEmailService.sendEmailForCustomer(emailEntity, loanId,
				        template);
			} catch (InvalidInputException e) {
				LOG.error("Mail send failed--" + e);
			} catch (UndeliveredEmailException e) {
				LOG.error("Mail send failed--" + e);
			}

			EmailVO loanManagerEmailEntity = new EmailVO();
			Template loanManagerTemplate = templateService
			        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE_LOAN_MANAGER);
			// We create the substitutions map
			Map<String, String[]> loanManagerSubstitutions = new HashMap<String, String[]>();
			loanManagerSubstitutions.put("-customername-", new String[] { loan
			        .getUser().getFirstName() });
			if (loan.getUser() != null) {
				loanManagerEmailEntity.setSenderEmailId(loan.getUser()
				        .getUsername() + CommonConstants.SENDER_EMAIL_ID);
			} else {
				loanManagerEmailEntity
				        .setSenderEmailId(CommonConstants.SENDER_DEFAULT_USER_NAME
				                + CommonConstants.SENDER_EMAIL_ID);
			}
			loanManagerEmailEntity.setSenderName(CommonConstants.SENDER_NAME);
			loanManagerEmailEntity.setSubject("No Products Available");

			loanManagerEmailEntity
			        .setTemplateId(loanManagerTemplate.getValue());

			String loanManagerUsername = null;
			String loanManagerName = null;
			LoanTeamListVO loanTeamList = getLoanTeamListForLoan(loan);
			for (LoanTeamVO loanTeam : loanTeamList.getLoanTeamList()) {
				if (loanTeam.getUser() != null) {
					if (loanTeam.getUser().getInternalUserDetail() != null) {
						if (loanTeam.getUser().getInternalUserDetail()
						        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
						        .getRoleId()) {
							loanManagerUsername = loanTeam.getUser()
							        .getUsername();
							loanManagerName = loanTeam.getUser().getFirstName()
							        + " " + loanTeam.getUser().getLastName();
						}
					}
				}
			}

			loanManagerSubstitutions.put("-name-",
			        new String[] { loanManagerName });
			loanManagerEmailEntity.setTokenMap(loanManagerSubstitutions);
			List<String> loanManagerccList = new ArrayList<String>();
			if (loanManagerUsername != null) {
				loanManagerccList.add(CommonConstants.SENDER_DEFAULT_USER_NAME
				        + "-" + loanManagerUsername + "-" + loan.getId()
				        + CommonConstants.SENDER_EMAIL_ID);

				loanManagerEmailEntity.setCCList(loanManagerccList);
			}

			try {
				sendEmailService.sendEmailForLoanManagers(
				        loanManagerEmailEntity, loanId, loanManagerTemplate);
			} catch (InvalidInputException e) {
				LOG.error("Mail send failed--" + e);
			} catch (UndeliveredEmailException e) {
				LOG.error("Mail send failed--" + e);
			}
		}
	}

	@Override
	@Transactional(readOnly = true)
	public void sendNoproductsAvailableEmail(UserVO userVO, int loanID) {

		EmailVO emailEntity = new EmailVO();
		EmailRecipientVO recipientVO = new EmailRecipientVO();

		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE);
		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] { userVO.getFirstName() + " "
		        + userVO.getLastName() });
		recipientVO.setEmailID(userVO.getEmailId());
		emailEntity.setSenderEmailId(userVO.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);

		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("No Products Available");
		emailEntity.setTokenMap(substitutions);
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();

		ccList.add(userVO.getUsername() + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		try {
			sendEmailService.sendUnverifiedEmailToCustomer(emailEntity, userVO,
			        template);
		} catch (InvalidInputException e) {
			LOG.error("Mail send failed--" + e);
		} catch (UndeliveredEmailException e) {
			LOG.error("Mail send failed--" + e);
		}

		EmailVO loanManagerEmailEntity = new EmailVO();
		Template loanManagerTemplate = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NO_PRODUCTS_AVAILABLE_LOAN_MANAGER);
		// We create the substitutions map
		Map<String, String[]> loanManagerSubstitutions = new HashMap<String, String[]>();
		loanManagerSubstitutions.put("-customername-",
		        new String[] { userVO.getFirstName() });

		loanManagerEmailEntity.setSenderEmailId(userVO.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);

		loanManagerEmailEntity.setSenderName(CommonConstants.SENDER_NAME);
		loanManagerEmailEntity.setSubject("No Products Available");

		loanManagerEmailEntity.setTemplateId(loanManagerTemplate.getValue());
		String loanManagerName = null;
		String loanManagerUsername = null;
		LoanVO loanVO = getActiveLoanOfUser(userVO);
		if (loanVO != null) {
			LoanTeamListVO loanTeamList = getLoanTeamListForLoan(loanVO);
			for (LoanTeamVO loanTeam : loanTeamList.getLoanTeamList()) {
				if (loanTeam.getUser() != null) {
					if (loanTeam.getUser().getInternalUserDetail() != null) {
						if (loanTeam.getUser().getInternalUserDetail()
						        .getInternalUserRoleMasterVO().getId() == InternalUserRolesEum.LM
						        .getRoleId()) {
							loanManagerUsername = loanTeam.getUser()
							        .getUsername();
							loanManagerName = loanTeam.getUser().getFirstName()
							        + " " + loanTeam.getUser().getLastName();
						}
					}
				}
			}

			loanManagerSubstitutions.put("-name-",
			        new String[] { loanManagerName });
			loanManagerEmailEntity.setTokenMap(loanManagerSubstitutions);
			List<String> loanManagerccList = new ArrayList<String>();
			if (loanManagerUsername != null) {
				loanManagerccList.add(CommonConstants.SENDER_DEFAULT_USER_NAME
				        + "-" + loanManagerUsername + "-" + loanID
				        + CommonConstants.SENDER_EMAIL_ID);

				loanManagerEmailEntity.setCCList(loanManagerccList);
			}
		}
		try {
			sendEmailService.sendEmailForLoanManagers(loanManagerEmailEntity,
			        loanID, loanManagerTemplate);
		} catch (InvalidInputException e) {
			LOG.error("Mail send failed--" + e);
		} catch (UndeliveredEmailException e) {
			LOG.error("Mail send failed--" + e);
		}

	}

	@Override
	@Transactional
	public void createAlertForAgentAddition(int loanId) {
		LOG.debug("Inside method createAlertForAgentAddition ");
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.TEAM_ADD_NOTIFICATION_TYPE;

		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		if (notificationList.size() == 0) {
			LOG.debug("Creating new notification for "
			        + notificationType.getNotificationTypeName());
			LoanVO loanVO = new LoanVO(loanId);
			boolean agentFound = false;
			ExtendedLoanTeamVO extendedLoanTeamVO = findExtendedLoanTeam(loanVO);
			if (extendedLoanTeamVO != null
			        && extendedLoanTeamVO.getUsers() != null) {
				for (UserVO userVO : extendedLoanTeamVO.getUsers()) {
					if (userVO.getUserRole() != null
					        && userVO
					                .getUserRole()
					                .getRoleCd()
					                .equalsIgnoreCase(
					                        UserRolesEnum.REALTOR.getName())) {
						LOG.debug("Agent found ");
						agentFound = true;
						break;

					}
				}
			}
			if (!agentFound) {
				LOG.debug("Not able to find any agent associated ");
				NotificationVO notificationVO = new NotificationVO(loanId,
				        notificationType.getNotificationTypeName(),
				        WorkflowConstants.AGENT_ADD_NOTIFICATION_CONTENT);
				LOG.debug("Creating new notification to add agent ");
				notificationService.createNotificationAsync(notificationVO);
			}
		}
	}

	@Override
	@Transactional
	public void createAlertForAgent(int loanId) {
		LOG.debug("Inside method createAlertForAgentAddition ");
		MilestoneNotificationTypes notificationType = MilestoneNotificationTypes.TEAM_ADD_NOTIFICATION_TYPE;

		List<NotificationVO> notificationList = notificationService
		        .findNotificationTypeListForLoan(loanId,
		                notificationType.getNotificationTypeName(), true);
		if (notificationList.size() == 0) {
			LOG.debug("Creating new notification for "
			        + notificationType.getNotificationTypeName());
			LoanVO loanVO = new LoanVO(loanId);
			LOG.debug("Not able to find any agent associated ");
			NotificationVO notificationVO = new NotificationVO(loanId,
			        notificationType.getNotificationTypeName(),
			        WorkflowConstants.AGENT_ADD_NOTIFICATION_CONTENT);
			LOG.debug("Creating new notification to add agent ");
			notificationService.createNotificationAsync(notificationVO);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO getLoanByLoanEmailId(String loanEmailId) {

		return loanDao.findLoanByLoanEmailId(loanEmailId);
	}

	@Override
	public Boolean checkIfLoanHasSalesManager(Long loanId) {
		// TODO Auto-generated method stub
		return loanDao.checkIfLoanHasSalesManager(loanId);
	}

	@Override
	@Transactional(readOnly = true)
	public Loan getLoanByWorkflowItemExecId(int workflowItemExecId) {
		LOG.debug("Inside method getLoanByWorkflowItemExecId");
		Loan loan = null;
		WorkflowItemExec workflowItemExec = workflowService
		        .getWorkflowItemExecByID(workflowItemExecId);
		if (workflowItemExec != null) {
			WorkflowExec workflowExec = workflowItemExec.getParentWorkflow();
			if (workflowExec != null) {
				loan = findLoanByWorkflowExec(workflowExec);
			}
		}
		return loan;
	}

	@Transactional(readOnly = true)
	public Loan findLoanByWorkflowExec(WorkflowExec workflowExec) {
		LOG.debug("Inside method findLoanByWorkflowExec");
		return loanDao.findLoanByWorkflowExec(workflowExec.getId());
	}

	@Override
	@Transactional
	public void saveCreditScoresForBorrower(Map<String, String> creditScoreMap,
	        Loan loan, ExceptionMaster exceptionMaster) {
		LOG.debug("Inside method saveCreditScores ");
		LoanVO loanVO = getLoanByID(loan.getId());
		if (loanVO != null) {
			UserVO user = loanVO.getUser();
			if (user != null) {
				CustomerDetailVO customerDetailVO = user.getCustomerDetail();
				if (customerDetailVO != null) {
					if (creditScoreMap != null && !creditScoreMap.isEmpty()) {
						String borrowerEquifaxScore = creditScoreMap
						        .get(CoreCommonConstants.SOAP_XML_BORROWER_EQUIFAX_SCORE);
						customerDetailVO.setEquifaxScore(borrowerEquifaxScore);
						String borrowerExperianScore = creditScoreMap
						        .get(CoreCommonConstants.SOAP_XML_BORROWER_EXPERIAN_SCORE);
						customerDetailVO
						        .setExperianScore(borrowerExperianScore);
						String borrowerTransunionScore = creditScoreMap
						        .get(CoreCommonConstants.SOAP_XML_BORROWER_TRANSUNION_SCORE);
						customerDetailVO
						        .setTransunionScore(borrowerTransunionScore);

						LOG.debug("Updating customer details ");
						// If all these scores were avialble then invooke
						// concrete class
						CustomerDetail customerDetail = CustomerDetail
						        .convertFromVOToEntity(customerDetailVO);
						userProfileService.updateCustomerScore(customerDetail);
						if (isCreditScoreValid(customerDetail)) {
							// Then invoke Concreate class to mark all As GREEn
							Map<String, Object> objectMap = new HashMap<String, Object>();
							objectMap
							        .put(WorkflowDisplayConstants.EMAIL_TEMPLATE_KEY_NAME,
							                CommonConstants.TEMPLATE_KEY_NAME_CREDIT_INFO);
							workflowCoreService
							        .completeWorkflowItem(
							                loan,
							                objectMap,
							                WorkflowConstants.WORKFLOW_ITEM_CREDIT_SCORE);
						}

					} else {
						LOG.error("Credit Scores Not Found For This Loan ");
						if (exceptionMaster != null) {
							nexeraUtility.putExceptionMasterIntoExecution(
							        exceptionMaster,
							        "Credit Score Not Found for this loan ");
							nexeraUtility
							        .sendExceptionEmail("Credit score not found for this loan "
							                + loan.getId());
						}
					}
				} else {
					LOG.error("Customer Details Not Found With this user id ");
					if (exceptionMaster != null) {
						nexeraUtility.putExceptionMasterIntoExecution(
						        exceptionMaster,
						        "Customer details not found for this user id "
						                + loan.getUser().getId());
						nexeraUtility
						        .sendExceptionEmail("Customer Details Not Found for this user "
						                + loan.getUser().getFirstName()
						                + " "
						                + loan.getUser().getLastName());
					}

				}
			}
		}
	}

	// Checks if Credit Score is available for all 3 bureaus and all are NOT 800
	private boolean isCreditScoreValid(CustomerDetail customerDetail) {
		boolean creditScoreValid = false;
		// All 3 are available but all are 800
		if (customerDetail.getEquifaxScore() != null
		        && customerDetail.getExperianScore() != null
		        && customerDetail.getTransunionScore() != null
		        && customerDetail.getEquifaxScore().equalsIgnoreCase(
		                CommonConstants.DEFAULT_CREDIT_SCORE)
		        && customerDetail.getExperianScore().equalsIgnoreCase(
		                CommonConstants.DEFAULT_CREDIT_SCORE)
		        && customerDetail.getTransunionScore().equalsIgnoreCase(
		                CommonConstants.DEFAULT_CREDIT_SCORE)) {
			creditScoreValid = false;
		}
		// All 3 are available and have values
		else if (customerDetail.getEquifaxScore() != null
		        && customerDetail.getExperianScore() != null
		        && customerDetail.getTransunionScore() != null) {
			creditScoreValid = true;
		}
		return creditScoreValid;
	}
}