package com.nexera.core.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.LoanMilestoneDao;
import com.nexera.common.dao.LoanMilestoneMasterDao;
import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.dao.LoanTurnAroundTimeDao;
import com.nexera.common.entity.CustomerDetail;
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
import com.nexera.common.entity.PropertyTypeMaster;
import com.nexera.common.entity.PurchaseDetails;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.entity.WorkflowItemMaster;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanTurnAroundTimeVO;
import com.nexera.common.vo.LoanUserSearchVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.MileStoneTurnAroundTimeVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserLoanStatus;
import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.TeamAssignmentHelper;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.MileStoneTurnAroundTimeService;
import com.nexera.core.service.UserProfileService;

@Component
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private Utils utils;

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
	private LoanAppFormService loanAppFormService;

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
		return Loan.convertFromEntityToVO(loan);
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
		loan.setIsRateLocked(loanVO.getIsRateLocked());
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

		loanCustomerVO.setTime(utils.getDateInUserLocaleFormatted(loan
		        .getCreatedDate()));
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
						        .getRoleId()) {
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
			loanCustomerVO.setCredit_score(utils
			        .constrtCreditScore(customerDetail));

		} else {
			loanCustomerVO.setCredit_score("-");
		}

		loanCustomerVO.setFirstName(user.getFirstName());
		loanCustomerVO.setLastName(user.getLastName());
		loanCustomerVO.setEmailId(user.getEmailId());

		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		if (customerDetail != null) {
			customerDetailVO.setAddressCity(customerDetail.getAddressCity());
			customerDetailVO.setAddressState(customerDetail.getAddressState());
			customerDetailVO.setAddressZipCode(customerDetail
			        .getAddressZipCode());
			if (null != customerDetail.getDateOfBirth())
				customerDetailVO.setDateOfBirth(customerDetail.getDateOfBirth()
				        .getTime());
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
		addDefaultLoanTeam(loanVO, loanId);
		loanDao.updateLoanEmail(loanId, utils.generateLoanEmail(loanId));

		// Invoking the workflow activities to trigger
		loan.setId(loanId);
		loanVO.setId(loanId);
		return loanVO;
	}

	private void addDefaultLoanTeam(LoanVO loanVO, int loanId) {
		// TODO Auto-generated method stub
		User user = User.convertFromVOToEntity(loanVO.getUser());
		List<LoanTeam> loanTeam = new ArrayList<LoanTeam>();

		LoanTeam e = new LoanTeam();
		e.setUser(user);
		Loan loan = new Loan(loanId);
		e.setLoan(loan);
		e.setActive(Boolean.TRUE);
		e.setAssignedOn(new Date(System.currentTimeMillis()));
		loanTeam.add(e);

		/*
		 * TODO: Get the state from the loan app form and pass it to the method
		 * below
		 */
		UserVO defaultUser = assignmentHelper.getDefaultLoanManager("CA");

		if (defaultUser != null) {
			LoanTeam defaultLanManager = new LoanTeam();
			LOG.debug("default Loan manager is: " + defaultUser);
			defaultLanManager.setUser(User.convertFromVOToEntity(defaultUser));
			defaultLanManager.setLoan(loan);
			defaultLanManager.setActive(Boolean.TRUE);
			defaultLanManager
			        .setAssignedOn(new Date(System.currentTimeMillis()));
			loanTeam.add(defaultLanManager);
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
		return this
		        .buildHomeOwnersInsuranceMasterVO((HomeOwnersInsuranceMaster) loanDao
		                .load(HomeOwnersInsuranceMaster.class,
		                        homeOwnersInsurance.getId()));
	}

	@Override
	@Transactional
	public TitleCompanyMasterVO addToLoanTeam(LoanVO loan,
	        TitleCompanyMasterVO titleCompany, UserVO addedBy) {

		loanDao.addToLoanTeam(this.parseLoanModel(loan),
		        this.parseTitleCompanyMaster(titleCompany),
		        User.convertFromVOToEntity(addedBy));
		return this.buildTitleCompanyMasterVO(((TitleCompanyMaster) loanDao
		        .load(TitleCompanyMaster.class, titleCompany.getId())));
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

		if (loan.getLoanAppForms() == null
		        || loan.getLoanAppForms().size() <= 0) {
			LOG.error("No loanappform record found for loan id : " + loanId);
			throw new NoRecordsFetchedException(
			        "No loanappform record found for loan id : " + loanId);

		}
		if (loan.getLoanAppForms().get(0).getPurchaseDetails() == null) {
			LOG.error("No purchase details record found for loanappform id : "
			        + loan.getLoanAppForms().get(0).getId());
			throw new NoRecordsFetchedException(
			        "No purchase details record found for loanappform id : "
			                + loan.getLoanAppForms().get(0).getId());
		}
		if (loan.getPropertyType() == null) {
			LOG.error("No property type record found for loan id : " + loanId);
			throw new NoRecordsFetchedException(
			        "No property type record found for loan id : " + loanId);
		}

		LoanAppForm loanAppForm = loan.getLoanAppForms().get(0);
		PropertyTypeMaster propertyTypeMaster = loan.getPropertyType();
		PurchaseDetails purchaseDetails = loanAppForm.getPurchaseDetails();

		// Check if the required fields are available in purchaseDetails and
		// propertyType

		if (purchaseDetails.getLoanAmount() == null
		        || purchaseDetails.getLoanAmount().isEmpty()) {
			throw new NoRecordsFetchedException(
			        "Loan amount property not found in purchase details for loan id : "
			                + loanId);
		}

		if (propertyTypeMaster.getPropertyTypeCd() == null
		        || propertyTypeMaster.getPropertyTypeCd().isEmpty()) {
			throw new NoRecordsFetchedException(
			        "Property type cd property not found in property type for loan id : "
			                + loanId);
		}

		if (Integer.parseInt(purchaseDetails.getLoanAmount()) <= CommonConstants.LOAN_AMOUNT_THRESHOLD) {
			if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.SINGLE_FAMILY_RESIDENCE_VALUE)) {
				return CommonConstants.CSFPR;
			} else if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.MULTI_FAMILY_RESIDENCE_VALUE)) {
				return CommonConstants.CMF;
			} else if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.INVESTMENT_VALUE)) {
				/* For Investment change the column Name */
				return CommonConstants.CINV;
			} else {
				throw new InvalidInputException(
				        "Invalid property type for loan id : " + loanId
				                + " Givern property type : "
				                + propertyTypeMaster.getPropertyTypeCd());
			}
		} else {
			if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.SINGLE_FAMILY_RESIDENCE_VALUE)) {
				return CommonConstants.JSFPR;
			} else if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.MULTI_FAMILY_RESIDENCE_VALUE)) {
				return CommonConstants.JMF;
			} else if (propertyTypeMaster.getPropertyTypeCd().equals(
			        CommonConstants.INVESTMENT_VALUE)) {
				return CommonConstants.JINV;
			} else {
				throw new InvalidInputException(
				        "Invalid property type for loan id : " + loanId
				                + " Givern property type : "
				                + propertyTypeMaster.getPropertyTypeCd());
			}
		}
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
			loanStatus.setCreditInformation(utils
			        .constrtCreditScore(customerDetail));

		} else {
			loanStatus.setCreditInformation("-");
		}
		// TODO: Currently hard coding
		loanStatus.setCreditDecission("N.A");
		if (loan.getLockedRate() != null) {
			loanStatus.setLockRate(loan.getLockedRate().toString());
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
}
