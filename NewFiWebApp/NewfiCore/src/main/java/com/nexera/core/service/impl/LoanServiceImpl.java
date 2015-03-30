package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Hibernate;
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
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanDetail;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanStatusMaster;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.entity.WorkflowExec;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.ExtendedLoanTeamVO;
import com.nexera.common.vo.HomeOwnersInsuranceMasterVO;
import com.nexera.common.vo.LoanCustomerVO;
import com.nexera.common.vo.LoanDashboardVO;
import com.nexera.common.vo.LoanDetailVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.common.vo.TitleCompanyMasterVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;

@Component
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanDao loanDao;

	@Autowired
	private Utils utils;

	@Autowired
	private LoanMilestoneDao loanMilestoneDao;

	@Autowired
	private LoanMilestoneMasterDao loanMilestoneMasterDao;

	@Autowired
	private UserProfileService userProfileService;

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
		return this.buildLoanVO(loan);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO getLoanByID(Integer loanID) {
		return this.buildLoanVO(loanDao.getLoanWithDetails(loanID));
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
				if (UserRolesEnum.LOANMANAGER.equalsName(user
				        .getInternalUserDetail().getInternalUserRoleMasterVO()
				        .getRoleName())) {
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

	private LoanVO buildLoanVO(Loan loan) {

		if (loan == null)
			return null;

		LoanVO loanVo = new LoanVO();
		loanVo.setId(loan.getId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setDeleted(loan.getDeleted());
		loanVo.setLoanEmailId(loan.getLoanEmailId());
		loanVo.setLqbFileId(loan.getLqbFileId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setModifiedDate(loan.getModifiedDate());
		loanVo.setName(loan.getName());
		if (loan.getLoanStatus() != null)
			loanVo.setStatus(loan.getLoanStatus().getLoanStatusCd());

		loanVo.setUser(User.convertFromEntityToVO(loan.getUser()));

		List<UserVO> loanTeam = new ArrayList<UserVO>();
		for (LoanTeam team : loan.getLoanTeam()) {
			UserVO userVo = User.convertFromEntityToVO(team.getUser());
			loanVo.setUser(userVo);
			loanTeam.add(userVo);
		}
		loanVo.setLoanTeam(loanTeam);

		loanVo.setLoanDetail(this.buildLoanDetailVO(loan.getLoanDetail()));
		if (loan.getCustomerWorkflow() != null) {
			loanVo.setCustomerWorkflowID(loan.getCustomerWorkflow().getId());
		}
		if (loan.getLoanManagerWorkflow() != null) {
			loanVo.setLoanManagerWorkflowID(loan.getLoanManagerWorkflow()
			        .getId());
		}

		loanVo.setIsBankConnected(loan.getIsBankConnected());
		loanVo.setIsRateLocked(loan.getIsRateLocked());
		loanVo.setSetSenderDomain(CommonConstants.SENDER_DOMAIN);
		loanVo.setLockedRate(loan.getLockedRate());
		return loanVo;

	}

	private List<LoanVO> buildLoanVOList(List<Loan> loanList) {

		if (loanList == null)
			return null;

		List<LoanVO> voList = new ArrayList<LoanVO>();
		for (Loan loan : loanList) {
			voList.add(this.buildLoanVO(loan));
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

	private LoanDetailVO buildLoanDetailVO(LoanDetail detail) {
		if (detail == null)
			return null;

		LoanDetailVO detailVO = new LoanDetailVO();
		detailVO.setId(detail.getId());
		detailVO.setDownPayment(detail.getDownPayment());
		detailVO.setLoanAmount(detail.getLoanAmount());
		detailVO.setRate(detail.getRate());

		return detailVO;

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
		List<Loan> loanList = loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), 1);
		loanList.addAll(loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), 2));

		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO retrieveDashboardForMyLoans(UserVO userVO) {

		// Get all loans this user has access to.
		int userRoleId = loanDao.retrieveUserRoleId(userVO);

		if (userRoleId == (UserRolesEnum.SYSTEM.getRoleId())) {

			List<Loan> loanList = loanDao.retrieveLoanForDashboardForAdmin(this
			        .parseUserModel(userVO));
			LoanDashboardVO loanDashboardVO = this
			        .buildLoanDashboardVoFromLoanList(loanList);

			return loanDashboardVO;

		}

		List<Loan> loanList = loanDao.retrieveLoanForDashboard(this
		        .parseUserModel(userVO));
		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanDashboardVO retrieveDashboardForArchiveLoans(UserVO userVO) {

		// Get declined , withdrawn and closed loans this user has access to.
		List<Loan> loanList = loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), 5);
		loanList.addAll(loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), 6));
		loanList.addAll(loanDao.retrieveLoanByProgressStatus(
		        this.parseUserModel(userVO), 7));
		LoanDashboardVO loanDashboardVO = this
		        .buildLoanDashboardVoFromLoanList(loanList);

		return loanDashboardVO;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanCustomerVO retrieveDashboard(UserVO userVO, LoanVO loanVO) {

		// Get all loans this user has access to.
		Loan loan = loanDao.retrieveLoanForDashboard(
		        this.parseUserModel(userVO), this.parseLoanModel(loanVO));
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

		LoanCustomerVO loanCustomerVO = new LoanCustomerVO();

		loanCustomerVO.setTime(loan.getCreatedDate().toString());
		loanCustomerVO.setName(user.getFirstName() + " " + user.getLastName());
		loanCustomerVO.setProf_image(user.getPhotoImageUrl());
		loanCustomerVO.setPhone_no(user.getPhoneNumber());
		loanCustomerVO.setLoanID(loan.getId());
		loanCustomerVO.setUserID(user.getId());
		if (user.getUserRole() != null)
			loanCustomerVO.setRole(user.getUserRole().getLabel());
		loanCustomerVO.setLoanInitiatedOn(loan.getCreatedDate());
		loanCustomerVO.setLastActedOn(loan.getModifiedDate());
		// TODO get these hard coded data from entity
		loanCustomerVO.setProcessor("Johny Tester");
		loanCustomerVO.setPurpose("Purchase TBD");
		loanCustomerVO.setAlert_count("3");
		loanCustomerVO.setCredit_score("732");

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
		Loan loan = (Loan) loanDao.load(Loan.class, loanID);

		Hibernate.initialize(loan.getCustomerWorkflow());
		WorkflowExec wFItem = loan.getCustomerWorkflow();
		if (loan.getCustomerWorkflow() == null) {
			wFItem = new WorkflowExec();
		}
		wFItem.setId(customerWorkflowID);
		loan.setCustomerWorkflow(wFItem);
		Hibernate.initialize(loan.getLoanManagerWorkflow());
		WorkflowExec wFItem1 = loan.getLoanManagerWorkflow();
		if (wFItem1 == null) {
			wFItem1 = new WorkflowExec();
		}
		wFItem1.setId(loanManagerWFID);
		loan.setLoanManagerWorkflow(wFItem1);
		loanDao.save(loan);
	}

	@Override
	@Transactional(readOnly = true)
	public LoanVO findWorkflowInfoById(int loanID) {
		Loan loan = loanDao.getLoanWorkflowDetails(loanID);
		LoanVO loanVO = null;
		if (loan != null) {
			loanVO = this.buildLoanVO(loan);
			return loanVO;
		}
		return loanVO;
	}

	@Override
	@Transactional
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
			User user = userProfileService.parseUserModel(loanVO.getUser());

			List<LoanStatusMaster> list = loanDao.getLoanStatusMaster(loanVO
			        .getLoanStatus());
			List<LoanTypeMaster> loanTypeList = loanDao.getLoanTypeMater(loanVO
			        .getLoanType());

			loan.setId(loanVO.getId());
			loan.setUser(user);
			loan.setCreatedDate(loanVO.getCreatedDate());
			loan.setDeleted(loanVO.getDeleted());
			loan.setLoanEmailId(loanVO.getLoanEmailId());
			loan.setLqbFileId(loanVO.getLqbFileId());
			loan.setModifiedDate(loanVO.getModifiedDate());
			loan.setName(loanVO.getName());
			if (list != null) {
				for (LoanStatusMaster loanStatusMaster : list) {
					loan.setLoanStatus(loanStatusMaster);
				}
			}
			if (loanTypeList != null) {
				for (LoanTypeMaster loanTypeMaster : loanTypeList) {
					loan.setLoanType(loanTypeMaster);
				}
			}
			loan.setLockedRate(loanVO.getLockedRate());
		} catch (Exception e) {

			LOG.error("Error in parse loan model", e);
		}

		return loan;
	}

	@Override
	@Transactional
	public LoanVO createLoan(LoanVO loanVO) {

		Loan loan = null;

		loan = completeLoanModel(loanVO);

		loanDao.save(loan);
		List<UserVO> userList = loanVO.getLoanTeam();

		if (userList != null) {
			userList.add(loanVO.getUser());

			for (UserVO userVO : userList) {
				User user = userProfileService.parseUserModel(userVO);
				loanDao.addToLoanTeam(loan, user, null);
			}
		} else {

			loanDao.addToLoanTeam(loan, loan.getUser(), null);

		}
		return this.buildLoanVO(loan);
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
		companyMaster.setAddedBy(userProfileService.parseUserModel(vo
		        .getAddedBy()));
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
		companyMaster.setAddedBy(userProfileService.parseUserModel(vo
		        .getAddedBy()));
		companyMaster.setPhoneNumber(vo.getPhoneNumber());
		companyMaster.setPrimaryContact(vo.getPrimaryContact());
		companyMaster.setFax(vo.getFax());

		return companyMaster;
	}

	@Override
	@Transactional(readOnly = true)
	public LoanNeedsList fetchByNeedId(Integer needId) {
		// TODO Auto-generated method stub
		return loanDao.fetchByNeedId(needId);
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
		        userProfileService.parseUserModel(addedBy));
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
		        userProfileService.parseUserModel(addedBy));
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
	public LoanVO convertIntoLoanVO(Loan loan) {
		if (loan == null)
			return null;

		LoanVO loanVo = new LoanVO();
		loanVo.setId(loan.getId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setDeleted(loan.getDeleted());
		loanVo.setLoanEmailId(loan.getLoanEmailId());
		loanVo.setLqbFileId(loan.getLqbFileId());
		loanVo.setCreatedDate(loan.getCreatedDate());
		loanVo.setModifiedDate(loan.getModifiedDate());
		loanVo.setName(loan.getName());
		if (loan.getLoanStatus() != null)
			loanVo.setStatus(loan.getLoanStatus().getLoanStatusCd());
		loanVo.setUser(User.convertFromEntityToVO(loan.getUser()));

		loanVo.setLoanDetail(this.buildLoanDetailVO(loan.getLoanDetail()));
		if (loan.getCustomerWorkflow() != null) {
			loanVo.setCustomerWorkflowID(loan.getCustomerWorkflow().getId());
		}
		if (loan.getLoanManagerWorkflow() != null) {
			loanVo.setLoanManagerWorkflowID(loan.getLoanManagerWorkflow()
			        .getId());
		}

		loanVo.setIsBankConnected(loan.getIsBankConnected());
		loanVo.setIsRateLocked(loan.getIsRateLocked());
		return loanVo;
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

}
