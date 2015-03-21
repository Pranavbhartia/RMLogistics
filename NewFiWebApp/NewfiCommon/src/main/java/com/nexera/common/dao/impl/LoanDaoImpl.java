package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.LoanStatusMaster;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.LoanTypeMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.vo.LoanStatusMasterVO;
import com.nexera.common.vo.LoanTypeMasterVO;

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
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
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
		return (Loan) criteria.uniqueResult();
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
	public Loan retrieveLoanForDashboard(User parseUserModel, Loan loan) {

		try {
			Loan loanForUser = null;
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.add(Restrictions.eq("user.id", parseUserModel.getId()));
			criteria.add(Restrictions.eq("loan", loan));
			LoanTeam loanTeam = (LoanTeam) criteria.uniqueResult();

			if (loanTeam != null) {

				Hibernate.initialize(loanTeam.getLoan());
				loanForUser = loanTeam.getLoan();

			}

			return loanForUser;
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
			Hibernate.initialize(loan.getLoanRates());
			Hibernate.initialize(loan.getUser());
			Hibernate.initialize(loan.getLoanStatus());
			Hibernate.initialize(loan.getLoanType());
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
			List<LoanTeam> team = criteria.list();

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
		Criteria criteria = session.createCriteria(LoanNeedsList.class)
		        .createAlias("loan", "loanList")
		        .add(Restrictions.eq("loanList.id", loanId));
		LOG.info("criteria : " + criteria);
		Integer result = (Integer) criteria.list().size();
		LOG.info("criteria result: " + result);
		return result;
	}

	@Override
	public Integer getTotalNeededItem(Integer loanId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class)
		        .createAlias("loan", "loanList")
		        .add(Restrictions.eq("loanList.id", loanId))
		        .createAlias("uploadFileId", "upload")
		        .add(Restrictions.isNotNull("upload.id"));
		LOG.info("criteria : " + criteria);
		Integer result = (Integer) criteria.list().size();
		LOG.info("criteria result: " + result);
		return result;
	}

	@Override
    public List<LoanStatusMaster> getLoanStatusMaster(LoanStatusMasterVO loanVO) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanStatusMaster.class);
		criteria.add(Restrictions.eq("id", loanVO.getId()));
		List<LoanStatusMaster> loanList = criteria.list();
	    return loanList;
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

	

	
}
