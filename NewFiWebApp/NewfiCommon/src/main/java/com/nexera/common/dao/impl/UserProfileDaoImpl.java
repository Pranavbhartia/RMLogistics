package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanProgressStatusMaster;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.UserRoleNameImageVO;

@Component
@Transactional
public class UserProfileDaoImpl extends GenericDaoImpl implements
        UserProfileDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User authenticateUser(String userName, String password)
	        throws NoRecordsFetchedException, DatabaseException {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("emailId", userName));
			criteria.add(Restrictions.eq("password", password));
			criteria.add(Restrictions.eq("password", password));
			Object obj = criteria.uniqueResult();
			if (obj == null) {
				throw new NoRecordsFetchedException(
				        DisplayMessageConstants.INVALID_USERNAME);
			}
			User user = (User) obj;
			Hibernate.initialize(user.getUserRole());
			return (User) obj;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in authenticateUser() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in authenticateUser() ",
			        hibernateException);
		}
	}

	@Override
	public User findByUserName(String userName)
	        throws NoRecordsFetchedException, DatabaseException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("emailId", userName));
			Object obj = criteria.uniqueResult();
			if (obj == null) {
				throw new NoRecordsFetchedException(
				        DisplayMessageConstants.INVALID_USERNAME);
			}
			User user = (User) obj;
			Hibernate.initialize(user.getUserRole());
			return (User) obj;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}

	}

	public User findByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userId));
		return (User) criteria.uniqueResult();
	}

	@Override
	public Integer updateUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id,usr.phoneNumber=:priPhoneNumber WHERE usr.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("priPhoneNumber", user.getPhoneNumber());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public Integer updateCustomerDetails(CustomerDetail customerDetail) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob,customerdetail.secPhoneNumber=:secPhoneNumber,customerdetail.secEmailId=:secEmailId,customerdetail.profileCompletionStatus=:profileStatus,customerdetail.mobileAlertsPreference=:mobileAlertsPreference WHERE customerdetail.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("city", customerDetail.getAddressCity());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("secPhoneNumber", customerDetail.getSecPhoneNumber());
		query.setParameter("secEmailId", customerDetail.getSecEmailId());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("profileStatus",
		        customerDetail.getProfileCompletionStatus());
		query.setParameter("mobileAlertsPreference",
		        customerDetail.getMobileAlertsPreference());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public Integer updateUser(String s3ImagePath, Integer userid) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.photoImageUrl = :imagePath WHERE usr.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("imagePath", s3ImagePath);
		query.setParameter("id", userid);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public List<User> getUsersList() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);

		return criteria.list();

	}

	@Override
	public List<User> searchUsers(User user) {

		Session session = sessionFactory.getCurrentSession();
		String searchQuery = "FROM User where ";

		if (user.getEmailId() != null)
			searchQuery += " email_id like '%" + user.getEmailId() + "%' or ";

		if (user.getFirstName() != null)
			user.setFirstName(user.getFirstName().toLowerCase());
		else
			user.setFirstName("");
		searchQuery += " lower(concat( first_name,',',last_name) ) like '%"
		        + user.getFirstName() + "%'";

		if (user.getUserRole() != null) {
			searchQuery += " and userRole=:userRole";
		}
		if (user.getInternalUserDetail() != null
		        && user.getInternalUserDetail().getInternaUserRoleMaster() != null) {
			searchQuery += " and (internalUserDetail IS NULL OR (internalUserDetail.internaUserRoleMaster=:internaUserRoleMaster))";
		}
		int MAX_RESULTS = 50;
		Query query = session.createQuery(searchQuery);

		if (user.getUserRole() != null) {
			query.setEntity("userRole", user.getUserRole());
		}

		if (user.getInternalUserDetail() != null
		        && user.getInternalUserDetail().getInternaUserRoleMaster() != null) {
			query.setEntity("internaUserRoleMaster", user
			        .getInternalUserDetail().getInternaUserRoleMaster());
		}

		query.setMaxResults(MAX_RESULTS);
		List<User> userList = query.list();

		return userList;
	}

	@Override
	public Integer saveUserWithDetails(User user) {
		if (null != user.getInternalUserDetail()
		        && user.getUserRole() != null
		        && user.getUserRole().getId() == UserRolesEnum.INTERNAL
		                .getRoleId()) {
			this.save(user.getInternalUserDetail());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != user.getRealtorDetail()
		        && user.getUserRole() != null
		        && user.getUserRole().getId() == UserRolesEnum.REALTOR
		                .getRoleId()) {
			this.save(user.getRealtorDetail());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != user.getCustomerDetail()
		        && user.getUserRole() != null
		        && user.getUserRole().getId() == UserRolesEnum.CUSTOMER
		                .getRoleId()) {
			this.save(user.getCustomerDetail());
			sessionFactory.getCurrentSession().flush();
		}
		return (Integer) this.save(user);
	}

	@Override
	public Integer competeUserProfile(User user) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id,usr.phoneNumber=:priPhoneNumber WHERE usr.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("priPhoneNumber", user.getPhoneNumber());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public Integer completeCustomerDetails(CustomerDetail customerDetail) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob,customerdetail.secPhoneNumber=:secPhoneNumber,customerdetail.secEmailId=:secEmailId,customerdetail.profileCompletionStatus=:profileStatus WHERE customerdetail.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("city", customerDetail.getAddressCity());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("secPhoneNumber", customerDetail.getSecPhoneNumber());
		query.setParameter("secEmailId", customerDetail.getSecEmailId());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("profileStatus",
		        customerDetail.getProfileCompletionStatus());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public Integer managerUpdateUserProfile(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id WHERE usr.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public Integer managerUpdateUCustomerDetails(CustomerDetail customerDetail) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob WHERE customerdetail.id = :id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("city", customerDetail.getAddressCity());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

	@Override
	public User findInternalUser(Integer userID) {
		User user = (User) this.load(User.class, userID);
		if (user != null) {
			Hibernate.initialize(user.getInternalUserDetail());
			System.out.println("Test  : loadInternalUser");
			if (user.getInternalUserDetail() != null)
				Hibernate.initialize(user.getInternalUserDetail()
				        .getInternaUserRoleMaster());

		}
		return user;

	}

	@Override
	@Transactional(readOnly = true)
	public String findUserRole(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException {
		User user = findByUserId(userID);
		UserRole role = user.getUserRole();
		String roleName = role.getRoleCd();
		UserRolesEnum rolesEnum = UserRolesEnum.valueOf(roleName);
		switch (rolesEnum) {
		case CUSTOMER:
			return UserRolesEnum.CUSTOMER.toString();
		case REALTOR:
			return UserRolesEnum.REALTOR.toString();
		case INTERNAL:
			return user.getInternalUserDetail().getInternaUserRoleMaster()
			        .getRoleDescription();

		default:
			return null;

		}

	}

	@Override
	@Transactional(readOnly = true)
	public String findUserRoleForMongo(Integer userID)
	        throws NoRecordsFetchedException, DatabaseException {
		User user = findByUserId(userID);
		UserRole role = user.getUserRole();
		String roleName = role.getRoleCd();
		UserRolesEnum rolesEnum = UserRolesEnum.valueOf(roleName);
		switch (rolesEnum) {
		case CUSTOMER:
			return UserRolesEnum.CUSTOMER.toString();
		case REALTOR:
			return UserRolesEnum.REALTOR.toString();
		case INTERNAL:
			return user.getInternalUserDetail().getInternaUserRoleMaster()
			        .getRoleName();

		default:
			return null;

		}

	}

	@Override
	@Transactional(readOnly = true)
	public UserRoleNameImageVO findUserDetails(int userID) {
		// TODO Auto-generated method stub
		UserRoleNameImageVO userVO = new UserRoleNameImageVO();
		User user = findByUserId(userID);
		userVO.setUserName(user.getFirstName() + " " + user.getLastName());
		userVO.setImgPath(user.getPhotoImageUrl());
		userVO.setUserID(userID);
		UserRolesEnum rolesEnum = UserRolesEnum.valueOf(user.getUserRole()
		        .getRoleCd());
		switch (rolesEnum) {
		case CUSTOMER:
			userVO.setUserRole(UserRolesEnum.CUSTOMER.toString());
			break;
		case REALTOR:
			userVO.setUserRole(UserRolesEnum.REALTOR.toString());
			break;
		default:
			userVO.setUserRole(user.getInternalUserDetail()
			        .getInternaUserRoleMaster().getRoleDescription());
			break;

		}
		return userVO;
	}

	@Override
	public List<UserRoleNameImageVO> finUserDetailsList(List<Long> roleList) {
		// TODO Auto-generated method stub

		List<UserRoleNameImageVO> imageVOs = new ArrayList<UserRoleNameImageVO>();
		for (Long userId : roleList) {
			imageVOs.add(findUserDetails(userId.intValue()));
		}
		return imageVOs;
	}

	public User saveUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		return user;
	}

	@Override
	public Integer saveCustomerDetails(User user) {
		if (null != user.getCustomerDetail()) {
			this.save(user.getCustomerDetail());
			sessionFactory.getCurrentSession().flush();
		}

		return (Integer) this.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getEmailAddress(List<Integer> list) {
		List<User> emailIds = new ArrayList<User>();

		if (list != null && !list.isEmpty()) {
			Session session = sessionFactory.getCurrentSession();
			String hql = "from User where id in (:ids)";
			Query qry = session.createQuery(hql);
			qry.setParameterList("ids", list);
			return qry.list();

		}

		return null;
	}

	@Override
	public List<User> fetchAllActiveUsers() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("status", true));
		criteria.add(Restrictions.eq("isProfileComplete", false));
		return criteria.list();
	}

	@Override
	public List<User> getLoanManagerForState(String stateName) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(StateLookup.class);
		criteria.add(Restrictions.eq("statecode", stateName));
		try {
			StateLookup lookup = (StateLookup) criteria.uniqueResult();

			criteria = session.createCriteria(InternalUserStateMapping.class);
			criteria.add(Restrictions.eq("stateLookup", lookup));
			List<InternalUserStateMapping> list = criteria.list();

			if (list == null || list.isEmpty()) {
				// No users found for this state
				return Collections.EMPTY_LIST;
			}
			// Check amongst the users who is free

			List<User> users = new ArrayList<User>();
			for (InternalUserStateMapping internalUserStateMapping : list) {
				User user = internalUserStateMapping.getUser();
				if (user.getInternalUserDetail().getActiveInternal()) {
					criteria = session.createCriteria(Loan.class);
					// Incorrect, this should look at loan team and not user
					criteria.add(Restrictions.eq("user", user));
					Criterion criteria2 = Restrictions.eq("loanProgressStatus",
					        new LoanProgressStatusMaster(
					                LoanProgressStatusMasterEnum.IN_PROGRESS));
					Criterion criteria3 = Restrictions.eq("loanProgressStatus",
					        new LoanProgressStatusMaster(
					                LoanProgressStatusMasterEnum.NEW_LOAN));

					criteria.add(Restrictions.disjunction().add(criteria2)
					        .add(criteria3));
					List<Loan> loanList = criteria.list();
					user.setLoans(loanList);
					users.add(user);
				}

			}
			return users;
		} catch (HibernateException exception) {
			LOG.warn("State not present in system: " + stateName);
			return Collections.EMPTY_LIST;
		}

		//
		// criteria = session.createCriteria(User.class);
		// // Criteria criteria = session.createCriteria(User.class);
		// criteria.add(Restrictions.eq("status", true));
		// UserRole role = new UserRole(UserRolesEnum.INTERNAL);
		// criteria.add(Restrictions.eq("userRole", role));
		// criteria.add(Restrictions.isNotNull("internalUserDetail"));
		// criteria.add(Restrictions
		// .eq("internaUserRoleMaster", new InternalUserRoleMaster(
		// InternalUserRolesEum.LM.getRoleId())));
		// InternalUserStateMapping internalUserStateMapping
		// // criteria.add(Restrictions.in("internalUserStateMappings", values)

	}
}
