package com.nexera.common.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.HomeOwnersInsuranceMaster;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanApplicationFee;
import com.nexera.common.entity.LoanDetail;
import com.nexera.common.entity.LoanLCStateMaster;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.entity.TitleCompanyMaster;
import com.nexera.common.entity.TransactionDetails;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.enums.ActiveInternalEnum;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LoanLCStates;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.vo.LoanTypeMasterVO;
import com.nexera.common.vo.LoanUserSearchVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;

@Component
public class LoanDaoImpl extends GenericDaoImpl implements LoanDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanDaoImpl.class);

	@Override
	public List<Loan> getLoansOfUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("user", user));

		return criteria.list();
	}

	@Override
	public boolean addToLoanTeam(Loan loan, User user, User addedBy) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("loan", loan));
		LoanTeam loanTeam = (LoanTeam) criteria.uniqueResult();
		if (loanTeam != null) {
			loanTeam.setActive(true);
			loanTeam.setAssignedBy(addedBy);
			loanTeam.setAssignedOn(new Date());
			this.update(loanTeam);
			return true;
		}

		LoanTeam loanTeamNew = new LoanTeam();
		loanTeamNew.setUser(user);
		loanTeamNew.setAssignedBy(addedBy);
		loanTeamNew.setLoan(loan);
		loanTeamNew.setActive(true);
		loanTeamNew.setAssignedOn(new Date());

		Integer id = (Integer) this.save(loanTeamNew);

		if (id != null)
			return true;
		else
			return false;
	}

	@Override
	public boolean removeFromLoanTeam(Loan loan, User user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("loan", loan));
		LoanTeam loanTeam = (LoanTeam) criteria.uniqueResult();

		if (loanTeam != null) {
			loanTeam.setActive(false);
			this.update(loanTeam);
			return true;
		}

		return false;
	}

	@Override
	public List<User> retreiveLoanTeam(Loan loan) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("loan", loan));
		criteria.add(Restrictions.eq("active", true));
		List<LoanTeam> team = criteria.list();

		if (team != null && team.size() > 0) {
			List<User> userList = new ArrayList<User>();
			for (LoanTeam loanTeam : team) {
				User user = loanTeam.getUser();
				if (user != null)
					Hibernate.initialize(user.getUserRole());
				if (user.getInternalUserDetail() != null)
					Hibernate.initialize(user.getInternalUserDetail());
				if (user.getInternalUserDetail() != null) {
					Hibernate.initialize(user.getInternalUserDetail()
					        .getInternaUserRoleMaster());
				}
				userList.add(user);
			}

			return userList;
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<Loan> retreiveLoansAsManager(User loanManager) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", loanManager));
		List<LoanTeam> team = criteria.list();

		if (team != null && team.size() > 0) {
			List<Loan> loanList = new ArrayList<Loan>();
			for (LoanTeam loanTeam : team) {
				Loan loan = loanTeam.getLoan();
				loanList.add(loan);
			}
			return loanList;

		}

		return Collections.EMPTY_LIST;
	}

	@Override
	public LoanAppForm getLoanAppForm(Integer loanId) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanAppForm.class);
			Loan loan = new Loan();
			loan.setId(loanId);
			criteria.add(Restrictions.eq("loan", loan));
			LoanAppForm appForm = (LoanAppForm) criteria.uniqueResult();
			return appForm;
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in getLoanAppForm() ", hibernateException);
		}
	}

	@Override
	public Loan getActiveLoanOfUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("user", user));
		/*
		 * criteria.createAlias("loanStatus", "ls");
		 * criteria.add(Restrictions.eq("ls.loanStatusCd", "1"));
		 */
		Loan loan = (Loan) criteria.uniqueResult();
		if (loan != null) {
			Hibernate.initialize(loan.getLoanProgressStatus());
			Hibernate.initialize(loan.getLoanType());
		}

		return loan;
	}

	@Override
	public List<Loan> retrieveLoanForDashboard(User parseUserModel) {

		try {
			List<Loan> loanListForUser = new ArrayList<Loan>();
			Session session = sessionFactory.getCurrentSession();

			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			List<LoanTeam> loanTeamList = criteria.list();

			if (loanTeamList != null) {
				for (LoanTeam loanTeam : loanTeamList) {
					Hibernate.initialize(loanTeam.getLoan());
					if (loanTeam.getLoan() != null) {
						Hibernate.initialize(loanTeam.getLoan().getUser());
					}

					Loan loan = loanTeam.getLoan();
					loanListForUser.add(loan);

				}
			}

			return loanListForUser;
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}
	}

	@Override
	public List<Loan> retrieveLoanForDashboardForAdmin(User parseUserModel) {
		try {
			List<Loan> loanListForUser = new ArrayList<Loan>();
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Loan.class);

			loanListForUser = criteria.list();
			for (Loan loan : loanListForUser) {
				Hibernate.initialize(loan.getLoanTeam());
			}
			return loanListForUser;

		}

		catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}

	}

	@Override
	public List<Loan> retrieveLoanDetailsOnSearch(LoanUserSearchVO searchVO) {

		try {

			ArrayList<Integer> progressStatusList = new ArrayList<Integer>(
			        searchVO.getProgressStatus().length);
			for (int i = 0; i < searchVO.getProgressStatus().length; i++) {
				progressStatusList.add(Integer.valueOf(searchVO
				        .getProgressStatus()[i]));
			}

			List<Loan> loanListForSearchedUser = new ArrayList<Loan>();

			Session session = sessionFactory.getCurrentSession();

			String queryStr = "FROM Loan WHERE user.firstName like '"
			        + searchVO.getUserName()
			        + "%' AND loanProgressStatus.id IN (:ids)";
			Query query = session.createQuery(queryStr).setParameterList("ids",
			        progressStatusList);

			loanListForSearchedUser = query.list();

			return loanListForSearchedUser;

		}

		catch (HibernateException hibernateException) {
			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}
	}

	@Override
	public List<Loan> retrieveLoanByProgressStatus(User parseUserModel,
	        int[] loanProgressStatusIds) {

		try {
			List<Loan> loanListForUser = new ArrayList<Loan>();
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.createAlias("loan", "loan");
			criteria.addOrder(Order.desc("loan.modifiedDate"));
			// If the user is Sales manager, retrieve all loans
			parseUserModel = (User) this.load(User.class,
			        parseUserModel.getId());
			if (parseUserModel.getInternalUserDetail() != null) {
				if (InternalUserRolesEum.SM.getRoleId() != parseUserModel
				        .getInternalUserDetail().getInternaUserRoleMaster()
				        .getId()) {
					criteria.add(Restrictions.eq("user.id",
					        parseUserModel.getId()));
				}
			} else {
				criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			}

			List<LoanTeam> loanTeamList = criteria.list();
			List<Integer> loanIdList = new ArrayList<Integer>();
			int i = 0;
			if (loanTeamList != null) {
				for (LoanTeam loanTeam : loanTeamList) {
					Hibernate.initialize(loanTeam.getLoan());
					Loan loan = loanTeam.getLoan();
					if (loanIdList.contains(loan.getId())) {
						continue;
					}

					if (checkIfIdIsInList(loan.getLoanProgressStatus().getId(),
					        loanProgressStatusIds)) {
						loanListForUser.add(loan);
						loanIdList.add(loan.getId());
					}

				}
			}

			for (Loan loan : loanListForUser) {
				Hibernate.isInitialized(loan.getLoanProgressStatus());
				Hibernate.initialize(loan.getLoanLCStateMaster());
			}
			/*
			 * Collections.sort(loanListForUser, new Comparator<Loan>() { public
			 * int compare(Loan loan1, Loan loan2) { return
			 * loan1.getCreatedDate().compareTo(loan2.getCreatedDate()); } });
			 */
			return loanListForUser;
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}
	}

	@Override
	public List<Loan> retrieveLoanByProgressStatus(User parseUserModel,
	        int[] loanProgressStatusIds, int startLimit, int endLimit) {

		try {
			List<Loan> loanListForUser = new ArrayList<Loan>();
			Integer[] progressStatus = new Integer[loanProgressStatusIds.length];
			for (int i = 0; i < loanProgressStatusIds.length; i++) {
				progressStatus[i] = Integer.valueOf(loanProgressStatusIds[i]);
			}
			
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.createAlias("loan", "loan");
			criteria.setProjection(Projections.projectionList().add(
			        Projections.groupProperty("loan.id")));
			criteria.addOrder(Order.desc("loan.modifiedDate"));
			criteria.createAlias("loan.loanProgressStatus", "loanProgressStatus");
			criteria.add(Restrictions.in("loanProgressStatus.id", progressStatus));
			// If the user is Sales manager, retrieve all loans
			parseUserModel = (User) this.load(User.class,
			        parseUserModel.getId());
			if (parseUserModel.getInternalUserDetail() != null) {
				if (InternalUserRolesEum.SM.getRoleId() != parseUserModel
				        .getInternalUserDetail().getInternaUserRoleMaster()
				        .getId()) {
					criteria.add(Restrictions.eq("active", true));
					criteria.add(Restrictions.eq("user.id",
					        parseUserModel.getId()));
				}
			} else {
				criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			}
			criteria.setFirstResult(startLimit);
			criteria.setMaxResults(endLimit);
			List<Integer> loanTeamList = criteria.list();
/*			List<Integer> loanIdList = new ArrayList<Integer>();
			int i = 0;*/
			if (loanTeamList != null) {
				for (Integer loanTeam : loanTeamList) {

					Loan loan = (Loan) session.get(Loan.class, loanTeam);
					/*if (loanIdList.contains(loan.getId())) {
						continue;
					}
					if (checkIfIdIsInList(loan.getLoanProgressStatus().getId(),
					        loanProgressStatusIds)) {*/
						loanListForUser.add(loan);
						/*loanIdList.add(loan.getId());
					}*/

				}
			}

			for (Loan loan : loanListForUser) {
				Hibernate.isInitialized(loan.getLoanProgressStatus());
				if(loan.getLoanLCStateMaster() != null){
					Hibernate.initialize(loan.getLoanLCStateMaster());
				}
				
			}
			/*
			 * Collections.sort(loanListForUser, new Comparator<Loan>() { public
			 * int compare(Loan loan1, Loan loan2) { return
			 * loan1.getCreatedDate().compareTo(loan2.getCreatedDate()); } });
			 */
			return loanListForUser;
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}
	}

	private boolean checkIfIdIsInList(int id, int[] loanProgressStatusIds) {
		// TODO Auto-generated method stub
		for (int i : loanProgressStatusIds) {
			if (i == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Loan retrieveLoanForDashboard(UserVO parseUserModel, Loan loan) {

		try {
			Loan loanForUser = null;
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			if (parseUserModel.getInternalUserDetail() != null) {
				if (InternalUserRolesEum.SM.getRoleId() != parseUserModel
				        .getInternalUserDetail().getInternalUserRoleMasterVO()
				        .getId()) {
					criteria.add(Restrictions.eq("user.id",
					        parseUserModel.getId()));
				}
			} else {
				criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			}
			// criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			criteria.add(Restrictions.eq("loan", loan));
			List<LoanTeam> loanTeam = criteria.list();

			LoanTeam loanTeam2 = loanTeam.get(0);
			Hibernate.initialize(loanTeam2.getLoan());

			return loanTeam2.getLoan();
		} catch (HibernateException hibernateException) {

			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard(User parseUserModel,Loan loan) ",
			        hibernateException);
		}
	}

	@Override
	public Loan getLoanWithDetails(Integer loanID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("id", loanID));
		Loan loan = (Loan) criteria.uniqueResult();

		if (loan != null) {
			Hibernate.initialize(loan.getLoanDetail());
			Hibernate.initialize(loan.getLoanProgressStatus());
			Hibernate.initialize(loan.getLoanRates());
			Hibernate.initialize(loan.getUser());

			Hibernate.initialize(loan.getLoanType());
			Hibernate.initialize(loan.getLoanTeam());
		}

		return loan;
	}

	@Override
	public Loan getLoanWorkflowDetails(Integer loanID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("id", loanID));
		Loan loan = (Loan) criteria.uniqueResult();

		if (loan != null) {
			Hibernate.initialize(loan.getCurrentLoanMilestone());
			Hibernate.initialize(loan.getLoanManagerWorkflow());
		}

		return loan;
	}

	/**
     * 
     */
	@Override
	public List<LoanTeam> getLoanTeamList(Loan loan) {

		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.add(Restrictions.eq("loan.id", loan.getId()));
			criteria.add(Restrictions.eq("active", true));
			List<LoanTeam> team = criteria.list();

			if (team != null && !team.isEmpty()) {
				for (LoanTeam loanTeam : team) {
					Hibernate.initialize(loanTeam.getLoan());
					Hibernate.initialize(loanTeam.getLoan().getUser());
					if (loanTeam.getLoan().getUser() != null
					        && loanTeam.getLoan().getUser()
					                .getInternalUserDetail() != null) {

						Hibernate.initialize(loanTeam.getLoan().getUser()
						        .getInternalUserDetail());
					}
					Hibernate.initialize(loanTeam.getUser());
				}
			}
			return team;
		} catch (HibernateException hibernateException) {
			throw new DatabaseException(
			        "Exception caught in retrieveLoanForDashboard() ",
			        hibernateException);
		}

	}

	/**
     * 
     */
	@Override
	public int retrieveUserRoleId(UserVO userVO) {
		Session session = sessionFactory.getCurrentSession();
		User user;
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userVO.getId()));
		user = (User) criteria.uniqueResult();
		return user.getUserRole().getId();

	}

	@Override
	public List<Loan> getLoansForUser(Integer userID) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("user.id", userID));
		List<Loan> loanList = criteria.list();
		return loanList;
	}

	@Override
	public UploadedFilesList fetchUploadedFromLoanNeedId(Integer loanNeedId) {
		Session session = sessionFactory.getCurrentSession();
		LoanNeedsList loannNeedList = (LoanNeedsList) session.load(
		        LoanNeedsList.class, loanNeedId);
		return loannNeedList.getUploadFileId();
	}

	@Override
	public Integer getNeededItemsRequired(Integer loanId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
		        .createCriteria(LoanNeedsList.class)
		        .createAlias("loan", "loanList")
		        .createAlias("needsListMaster", "nlm")
		        .add(Restrictions.eq("loanList.id", loanId))
		        .add(Restrictions.ne("nlm.id", 40))
		        .add(Restrictions.ne("nlm.label",
		                CommonConstants.LQB_DOC_TYPE_CR));
		LOG.info("criteria : " + criteria);
		Integer result = criteria.list().size();
		LOG.info("criteria result: " + result);
		return result;
	}

	@Override
	public Integer getTotalNeededItem(Integer loanId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class)
		        .createAlias("loan", "loanList")
		        .add(Restrictions.eq("loanList.id", loanId))
		        .createAlias("needsListMaster", "nlm")
		        .add(Restrictions.ne("nlm.id", 40))
		        .createAlias("uploadFileId", "upload")
		        .add(Restrictions.isNotNull("upload.id"));
		LOG.info("criteria : " + criteria);
		Integer result = criteria.list().size();
		LOG.info("criteria result: " + result);
		return result;
	}

	@Override
	public List<LoanTypeMaster> getLoanTypeMater(
	        LoanTypeMasterVO loanTypeMaterVO) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTypeMaster.class);
		criteria.add(Restrictions.eq("id", loanTypeMaterVO.getId()));
		List<LoanTypeMaster> loanTypeList = criteria.list();
		return loanTypeList;
	}

	@Override
	public List<TitleCompanyMaster> findTitleCompanyByName(
	        TitleCompanyMaster titleCompany) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session
		        .createQuery("FROM TitleCompanyMaster where lower(name) like '%"
		                + titleCompany.getName() + "%'");
		@SuppressWarnings("unchecked")
		List<TitleCompanyMaster> companyList = query.list();
		//

		return companyList;
	}

	@Override
	public List<HomeOwnersInsuranceMaster> findHomeOwnInsByName(
	        HomeOwnersInsuranceMaster insurance) {

		Session session = sessionFactory.getCurrentSession();
		Query query = session
		        .createQuery("FROM HomeOwnersInsuranceMaster where lower(name) like '%"
		                + insurance.getName() + "%'");
		List<HomeOwnersInsuranceMaster> companyList = query.list();
		return companyList;
	}

	@Override
	public LoanNeedsList fetchByNeedId(Integer needId, Integer loanId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class);
		criteria.createAlias("needsListMaster", "needType");
		criteria.add(Restrictions.eq("needType.id", needId));
		Loan loan = new Loan(loanId);
		criteria.add(Restrictions.eq("needType.id", needId));
		criteria.add(Restrictions.eq("loan", loan));
		return (LoanNeedsList) criteria.uniqueResult();
	}

	@Override
	public HomeOwnersInsuranceMaster addHomeOwnInsCompany(
	        HomeOwnersInsuranceMaster homeOwnInsMaster) {
		this.save(homeOwnInsMaster);
		return homeOwnInsMaster;
	}

	@Override
	public LoanApplicationFee addLoanApplicationFee(LoanApplicationFee loanApplicationFee) {
		this.save(loanApplicationFee);
		return loanApplicationFee;
	}
	
	@Override
	public TransactionDetails updateTransactionDetails(TransactionDetails transactionDetails) {
		this.update(transactionDetails);
		return transactionDetails;
	}
	
	@Override
	public TitleCompanyMaster addTitleCompany(
	        TitleCompanyMaster titleCompanyMaster) {
		this.save(titleCompanyMaster);
		return titleCompanyMaster;
	}

	@Override
	public boolean addToLoanTeam(Loan loan,
	        HomeOwnersInsuranceMaster homeOwnersInsurance, User addedBy) {

		Loan loanFromDB = (Loan) this.load(Loan.class, loan.getId());
		if (loanFromDB == null)
			return false;

		Hibernate.initialize(loanFromDB.getLoanDetail());

		LoanDetail loanDetail = loanFromDB.getLoanDetail();
		if (loanDetail == null) {
			loanDetail = new LoanDetail();
			this.save(loanDetail);
			this.sessionFactory.getCurrentSession().flush();
			loanFromDB.setLoanDetail(loanDetail);
		}
		loanDetail.setHomeOwnersInsurance(homeOwnersInsurance);
		this.update(loanDetail);
		return true;
	}

	@Override
	public boolean addToLoanTeam(Loan loan, TitleCompanyMaster titleCompany,
	        User addedBy) {

		Loan loanFromDB = (Loan) this.load(Loan.class, loan.getId());
		if (loanFromDB == null)
			return false;

		Hibernate.initialize(loanFromDB.getLoanDetail());

		LoanDetail loanDetail = loanFromDB.getLoanDetail();
		if (loanDetail == null) {
			loanDetail = new LoanDetail();
			this.save(loanDetail);
			this.sessionFactory.getCurrentSession().flush();
			loanFromDB.setLoanDetail(loanDetail);
		}
		loanDetail.setTitleCompany(titleCompany);
		this.update(loanDetail);

		return true;
	}

	@Override
	public LoanMilestone findLoanMileStoneByLoan(Loan loan,
	        String loanMilestoneMAsterName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria masterCriteria = session
		        .createCriteria(LoanMilestoneMaster.class);
		masterCriteria.add(Restrictions.eq("name", loanMilestoneMAsterName));
		LoanMilestoneMaster loanMilestoneMaster = (LoanMilestoneMaster) masterCriteria
		        .uniqueResult();
		Criteria criteria = session.createCriteria(LoanMilestone.class);
		criteria.add(Restrictions.eq("loan", loan));
		criteria.add(Restrictions
		        .eq("loanMilestoneMaster", loanMilestoneMaster));
		criteria.addOrder(Order.desc("order"));
		criteria.addOrder(Order.desc("id"));
		List<LoanMilestone> milestones = criteria.list();

		LoanMilestone latestMS = null;
		if (milestones.size() > 0) {
			latestMS = milestones.get(0);
		}
		return latestMS;
	}
	
	@Override
	public LoanMilestone findLoanMileStoneByCriteria(
			LoanMilestone loanMilestoneCriteria) {
		Session session = sessionFactory.getCurrentSession();
		Criteria masterCriteria = session
		        .createCriteria(LoanMilestoneMaster.class);
		masterCriteria.add(Restrictions.eq("name", loanMilestoneCriteria.getLoanMilestoneMaster().getName()));
		LoanMilestoneMaster loanMilestoneMaster = (LoanMilestoneMaster) masterCriteria
		        .uniqueResult();
		Criteria criteria = session.createCriteria(LoanMilestone.class);
		criteria.add(Restrictions.eq("loan", loanMilestoneCriteria.getLoan()));
		criteria.add(Restrictions
		        .eq("loanMilestoneMaster", loanMilestoneMaster));
		criteria.add(Restrictions.eq("comments", loanMilestoneCriteria.getComments()));
		criteria.addOrder(Order.desc("order"));
		criteria.addOrder(Order.desc("id"));
		List<LoanMilestone> milestones = criteria.list();
		LoanMilestone latestMS = null;
		if (milestones.size() > 0) {
			latestMS = milestones.get(0);
		}
		return latestMS;
	}

	@Override
	public List<Loan> getAllActiveLoan() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		/*
		 * criteria.add(Restrictions.eq("user", user));
		 * 
		 * criteria.createAlias("loanStatus", "ls");
		 * criteria.add(Restrictions.eq("ls.loanStatusCd", "1"));
		 */
		return criteria.list();
	}

	/**
	 * Returns the loandetail entitiy linked to a loan.
	 * 
	 * @param loan
	 * @return
	 */
	@Override
	public LoanDetail findLoanDetailOfLoan(Loan loan) {

		loan = (Loan) this.load(Loan.class, loan.getId());

		if (loan == null)
			return null;
		Hibernate.initialize(loan.getLoanDetail());

		return loan.getLoanDetail();

	}

	@Override
	public HomeOwnersInsuranceMaster findHomeOwnersInsuranceCompanyOfLoan(
	        Loan loan) {

		LoanDetail detail = findLoanDetailOfLoan(loan);
		if (detail == null)
			return null;
		Hibernate.initialize(detail.getHomeOwnersInsurance());
		return detail.getHomeOwnersInsurance();
	}

	@Override
	public TitleCompanyMaster findTitleCompanyOfLoan(Loan loan) {
		LoanDetail detail = findLoanDetailOfLoan(loan);
		if (detail == null)
			return null;
		Hibernate.initialize(detail.getTitleCompany());
		return detail.getTitleCompany();
	}

	@Override
	public boolean removeFromLoanTeam(Loan loan,
	        HomeOwnersInsuranceMaster homeOwnIns) {
		LoanDetail detail = this.findLoanDetailOfLoan(loan);
		if (detail == null)
			return false;
		detail.setHomeOwnersInsurance(null);
		this.update(detail);
		return true;
	}

	@Override
	public boolean removeFromLoanTeam(Loan loan, TitleCompanyMaster titleCompany) {
		LoanDetail detail = this.findLoanDetailOfLoan(loan);
		if (detail == null)
			return false;
		detail.setTitleCompany(null);
		this.update(detail);
		return true;
	}

	@Override
	public void updateLoanEmail(int loanId, String generateLoanEmail) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.loanEmailId = :EMAIL WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("EMAIL", generateLoanEmail);
		query.setParameter("ID", loanId);
		query.executeUpdate();
		session.flush();
	}
	
	@Override
	public void updateAppraisalVendor(int loanID, String appraisalVendorNm) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.paymentVendor = :PAYMENT_VENDOR WHERE id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("PAYMENT_VENDOR", appraisalVendorNm);
		query.setParameter("ID", loanID);
		query.executeUpdate();
		session.flush();
	}
	
	@Override
	public void updateLoanLockDetails(int loanId, Date lockExpirationDate, String lockedRate, String lockStatus){
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.lockExpirationDate = :LOCK_EXPIRATION_DATE, loan.lockedRate = :LOCKED_RATE, loan.lockStatus = :LOCK_STATUS WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("LOCK_EXPIRATION_DATE", lockExpirationDate);
		query.setParameter("LOCKED_RATE", lockedRate);
		query.setParameter("LOCK_STATUS", lockStatus);
		query.setParameter("ID", loanId);
		query.executeUpdate();
		session.flush();
	}
	
	@Override
	public void updateLoanModifiedDate(int loanId, Date modifiedDate){
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.modifiedDate = :MODIFIED_DATE WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("MODIFIED_DATE", modifiedDate);
		query.setParameter("ID", loanId);
		query.executeUpdate();
		session.flush();
	}

	@Override
	public LoanNeedsList fetchLoanNeedsByFileId(UploadedFilesList uploadFileList) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class);
		criteria.add(Restrictions.eq("uploadFileId", uploadFileList));
		return (LoanNeedsList) criteria.uniqueResult();
	}

	@Override
	public List<LoanTeam> getLoanListBasedOnUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user.id", user.getId()));
		List<LoanTeam> loanList = criteria.list();
		return loanList;

	}

	@Override
	public boolean checkLoanDependency(User user) {

		/*
		 * 1.Get all loan teams this user is part of. 2.Check for each loan if
		 * the status of the loan is active 3. If the status is active, check if
		 * for all the loans there is at least one other internal Loan manager
		 * included in the team. The included team member should be active and
		 * should not be deleted
		 */

		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user.id", user.getId()));
		criteria.add(Restrictions.eq("active",true));
		
		List<LoanTeam> loanList = criteria.list();
		if (loanList == null || loanList.isEmpty()) {
			return true;
		}
		for (LoanTeam loanTeam : loanList) {
			Hibernate.initialize(loanTeam.getLoan());
			Hibernate.initialize(loanTeam.getUser());
			Loan loan = loanTeam.getLoan();

			if (loan.getLoanProgressStatus()
			        .getLoanProgressStatus()
			        .equals(LoanProgressStatusMasterEnum.IN_PROGRESS.toString())
			        || loan.getLoanProgressStatus()
			                .getLoanProgressStatus()
			                .equals(LoanProgressStatusMasterEnum.NEW_LOAN
			                        .toString())) {

				// Loan is active
				List<LoanTeam> activeTeamList = loan.getLoanTeam();
				boolean otherUserPresent = false;
				for (LoanTeam activeTeam : activeTeamList) {
					if (activeTeam.getUser().getInternalUserDetail() != null) {
						LOG.debug("the active Team is " + activeTeam.getUser().getId());
						if (activeTeam.getUser().getInternalUserDetail()
						        .getInternaUserRoleMaster().getId() == UserRolesEnum.LM
						        .getRoleId()) {
							if (!activeTeam.getUser().getId().equals(user.getId())
							        && activeTeam.getUser().getStatus() == 1
							        && activeTeam.getUser()
							                .getInternalUserDetail()
							                .getActiveInternal() == ActiveInternalEnum.ACTIVE) {
								LOG.debug("the other active LM in team  is " + activeTeam.getUser().getId());
								otherUserPresent = true;
							}
						}
					}
				}
				if (!otherUserPresent) {
					return false;
				}
				// Check if there are any other loan manager in this loan
			}

		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Loan> getLoanInActiveStatus() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
		        .createCriteria(LoanProgressStatusMaster.class);
		criteria.add(Restrictions.ne("loanProgressStatus", "CLOSED"));
		criteria.add(Restrictions.ne("loanProgressStatus", "WITHDRAWN"));
		criteria.add(Restrictions.ne("loanProgressStatus", "DECLINED"));

		List<LoanProgressStatusMaster> activeStatusList = criteria.list();

		criteria = session.createCriteria(Loan.class);
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < activeStatusList.size(); i++) {
			disjunction.add(Restrictions.eq("loanProgressStatus",
			        activeStatusList.get(i)));
		}
		criteria.add(disjunction);
		criteria.addOrder(Order.desc("modifiedDate"));
		return criteria.list();
	}

	@Override
	public void updateWorkFlowItems(int loanID, int customerWorkflowID,
	        int loanManagerWFID) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.customerWorkflow = :CWFID,loan.loanManagerWorkflow =:LMFID WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("CWFID", customerWorkflowID);
		query.setParameter("LMFID", loanManagerWFID);
		query.setParameter("ID", loanID);

		int result = query.executeUpdate();

	}

	@Override
	public void updateLoanAppFee(int loanId, BigDecimal newAppFee) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.appFee = :APPFEE WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("APPFEE", newAppFee);
		query.setParameter("ID", loanId);
		query.executeUpdate();
	}

	@Override
	public void setExpiryDateToPurchaseDocument(Integer loanId, Long date) {
		Session session = sessionFactory.getCurrentSession();
		Loan loan = (Loan) this.load(Loan.class, loanId);
		loan.setPurchaseDocumentExpiryDate(date);
		session.save(loan);

	}

	@Override
	public void updateLoanProgress(int loanId,
	        LoanProgressStatusMaster loanProgressValue) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.loanProgressStatus = :PROGRESS WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("PROGRESS", loanProgressValue);
		query.setParameter("ID", loanId);
		query.executeUpdate();		
	}

	@Override
	public void updateLoan(Integer loanId, Boolean rateLocked,
	        String lockedratedata) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.rateLockRequested = :ISRATELOCKED , loan.lockedRateData= :LOCKEDRATEDATA  WHERE loan.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("ISRATELOCKED", rateLocked);
		query.setParameter("ID", loanId);
		query.setParameter("LOCKEDRATEDATA", lockedratedata);
		query.executeUpdate();

	}

	@Override
	public LoanVO findLoanByLoanEmailId(String loanEmailId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("loanEmailId", loanEmailId));
		Loan loan = (Loan) criteria.uniqueResult();
		return Loan.convertFromEntityToVO(loan);
	}

	@Override
	public Boolean checkIfLoanHasSalesManager(Long loanId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("loan.id", loanId.intValue()));
		criteria.add(Restrictions.eq("active", Boolean.TRUE));
		criteria.createAlias("user.userRole", "ur");
		criteria.createAlias("user.internalUserDetail", "iud");
		criteria.createAlias("iud.internaUserRoleMaster", "irm");

		criteria.add(Restrictions.eq("ur.roleCd",
		        UserRolesEnum.INTERNAL.getName()));
		criteria.add(Restrictions.eq("irm.roleName", UserRolesEnum.SM.getName()));

		List<LoanTeam> loanTeam = criteria.list();
		if (loanTeam == null || loanTeam.isEmpty()) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public Loan findLoanByWorkflowExec(Integer workflowExecId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Loan.class);
		criteria.add(Restrictions.eq("customerWorkflow", workflowExecId));
		Loan loan = (Loan) criteria.uniqueResult();
		if (loan == null) {
			criteria = session.createCriteria(Loan.class);
			criteria.add(Restrictions.eq("loanManagerWorkflow", workflowExecId));
			loan = (Loan) criteria.uniqueResult();
		}

		return loan;
	}

	@Override
    public int updateStatusInLoanTeam(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE LoanTeam loan set loan.active = :status  WHERE user = :user";
		Query query = session.createQuery(hql);
		boolean status = false;
		if(user.getStatus() == CommonConstants.STATUS_ACTIVE){
			status = true;
		}else if(user.getStatus() == CommonConstants.STATUS_INACTIVE){
			status = false;
		}else if(user.getStatus() == CommonConstants.STATUS_IS_DELETE){
			status = false;
		}
		query.setParameter("status",status);
		query.setParameter("user", user);
		int rows=query.executeUpdate();
	    return rows;
    }

	@Override
    public Integer updateLQBAmounts(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.lqbLoanAmount = :lqbLoanAmount , loan.lqbAppraisedValue = :lqbAppraisedValue  WHERE loan.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("lqbLoanAmount",loan.getLqbLoanAmount());
		query.setParameter("lqbAppraisedValue", loan.getLqbAppraisedValue());
		query.setParameter("id", loan.getId());
		int rows=query.executeUpdate();
	    return rows;
    }

	@Override
    public Integer updateLtv(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.ltv = :ltv WHERE loan.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("ltv",loan.getLtv());
		query.setParameter("id", loan.getId());
		int rows=query.executeUpdate();
	    return rows;
    }
	
	@Override
	 public List<QuoteDetails> retrieveLoanForMyLeads(User parseUserModel,int startLimit, int endLimit) {

	  try {
	   
	   Session session = sessionFactory.getCurrentSession();
	   Criteria criteria = session.createCriteria(QuoteDetails.class);
	   criteria.addOrder(Order.desc("createdDate"));
	   // If the user is Sales manager, retrieve all loans
	   parseUserModel = (User) this.load(User.class,
	           parseUserModel.getId());
	   if (parseUserModel.getInternalUserDetail() != null) {
	    if (InternalUserRolesEum.SM.getRoleId() != parseUserModel
	            .getInternalUserDetail().getInternaUserRoleMaster()
	            .getId()) {
	     criteria.add(Restrictions.eq("quoteCompositeKey.internalUserId",
	             parseUserModel.getId()));
	    }
	   } else {
		   criteria.add(Restrictions.eq("quoteCompositeKey.internalUserId",
		             parseUserModel.getId()));
	   }
	   criteria.setFirstResult(startLimit);
	   criteria.setMaxResults(endLimit);
	   List<QuoteDetails> loanTeamList = criteria.list();
	   
	   return loanTeamList;
	  } catch (HibernateException hibernateException) {

	   throw new DatabaseException(
	           "Exception caught in retrieveLoanForDashboard() ",
	           hibernateException);
	  }
	 }

	@Override
	 public List<QuoteDetails> retrieveLoanForMyLeads(User parseUserModel) {

	  try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(QuoteDetails.class);
			criteria.addOrder(Order.desc("createdDate"));
			// If the user is Sales manager, retrieve all loans
			parseUserModel = (User) this.load(User.class,
			        parseUserModel.getId());
			if (parseUserModel.getInternalUserDetail() != null) {
				if (InternalUserRolesEum.SM.getRoleId() != parseUserModel
				        .getInternalUserDetail().getInternaUserRoleMaster()
				        .getId()) {
					criteria.add(Restrictions.eq(
					        "quoteCompositeKey.internalUserId",
					        parseUserModel.getId()));
				}
			} else {
				criteria.add(Restrictions.eq(
				        "quoteCompositeKey.internalUserId",
				        parseUserModel.getId()));
			}
			
			List<QuoteDetails> loanTeamList = criteria.list();

			return loanTeamList;
		  
	  } catch (HibernateException hibernateException) {

	   throw new DatabaseException(
	           "Exception caught in retrieveLoanForDashboard() ",
	           hibernateException);
	  }
	 }

	@Override
    public int updateLoanLCStateMaster(int loanID,
            LoanLCStates loanLCSStates) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.loanLCStateMaster = :loanLCStateMaster WHERE loan.id = :id";
		Query query = session.createQuery(hql);
		LoanLCStateMaster loanLCStateMaster = new LoanLCStateMaster();
		loanLCStateMaster.setId(loanLCSStates.getLcStateID());
		loanLCStateMaster.setLoanLCState(loanLCSStates.getLcStateKey());
		query.setParameter("loanLCStateMaster",loanLCStateMaster);
		query.setParameter("id", loanID);
		int rows=query.executeUpdate();
	    return rows;
    }

	@Override
    public int updateInterviewDateForLoan(int loanID, Date interviewDate) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Loan loan set loan.interviewDate = :interviewDate, loan.modifiedDate = :modifiedDate WHERE loan.id = :id";
		Query query = session.createQuery(hql);		
		query.setParameter("interviewDate",interviewDate);
		query.setParameter("modifiedDate", new Date());
		query.setParameter("id", loanID);
		int rows=query.executeUpdate();
	    return rows;
    }
	
	
	
}
