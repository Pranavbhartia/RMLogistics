package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerSpouseDetail;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.RealtorDetail;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.ActiveInternalEnum;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.LoanProgressStatusMasterEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserRoleNameImageVO;
import com.nexera.common.vo.UserVO;

@Component
@Transactional
public class UserProfileDaoImpl extends GenericDaoImpl implements
        UserProfileDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileDaoImpl.class);

	private static String GMT = "GMT";

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

			Object obj = criteria.uniqueResult();
			if (obj == null) {
				throw new NoRecordsFetchedException(
				        DisplayMessageConstants.INVALID_USERNAME);
			}
			User user = (User) obj;
			Hibernate.initialize(user.getUserRole());
			Hibernate.initialize(user.getInternalUserDetail());
			if (user.getInternalUserStateMappings() != null) {
				Hibernate.initialize(user.getInternalUserStateMappings());
			}
			return user;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in authenticateUser() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in authenticateUser() ",
			        hibernateException);
		}
	}

	@Override
	@Transactional(readOnly = true)
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
			Hibernate.initialize(user.getRealtorDetail());
			return (User) obj;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public User findByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userId));

		User user = (User) criteria.uniqueResult();
		if (user == null) {
			// No records found
			return null;
		}
		Hibernate.initialize(user.getInternalUserDetail());
		Hibernate.initialize(user.getUserRole());
		Hibernate.initialize(user.getRealtorDetail());
		Hibernate.initialize(user.getInternalUserStateMappings());
		Hibernate.initialize(user.getCustomerDetail());
		return user;
	}

	@Override
	public Integer updateUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id,usr.phoneNumber=:priPhoneNumber,usr.mobileAlertsPreference=:mobileAlertsPreference,usr.carrierInfo=:carrierInfo WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("priPhoneNumber", user.getPhoneNumber());
		query.setParameter("mobileAlertsPreference",
		        user.getMobileAlertsPreference());
		query.setParameter("carrierInfo", user.getCarrierInfo());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer updateCustomerDetails(CustomerDetail customerDetail) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set "
		        + "customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob,customerdetail.secPhoneNumber=:secPhoneNumber,customerdetail.secEmailId=:secEmailId,customerdetail.profileCompletionStatus=:profileStatus "
		        + "WHERE customerdetail.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("city", customerDetail.getAddressCity());
		// query.setParameter("street", customerDetail.getAddressStreet());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("secPhoneNumber", customerDetail.getSecPhoneNumber());
		query.setParameter("secEmailId", customerDetail.getSecEmailId());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("profileStatus",
		        customerDetail.getProfileCompletionStatus());
		/*
		 * query.setParameter("mobileAlertsPreference",
		 * customerDetail.getMobileAlertsPreference());
		 */
		query.setParameter("id", customerDetail.getId());
		// query.setParameter("carrierInfo", customerDetail.getCarrierInfo());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer updateCustomerScore(CustomerDetail customerDetail) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.equifaxScore = :equifax,customerdetail.experianScore =:experian,customerdetail.transunionScore=:transunion WHERE customerdetail.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("equifax", customerDetail.getEquifaxScore());
		query.setParameter("experian", customerDetail.getExperianScore());
		query.setParameter("transunion", customerDetail.getTransunionScore());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer updateCustomerSpouseScore(
	        CustomerSpouseDetail customerSpouseDetail) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerSpouseDetail customerSpouseDetail set customerSpouseDetail.equifaxScore = :equifax,customerSpouseDetail.experianScore =:experian,customerSpouseDetail.transunionScore=:transunion WHERE customerSpouseDetail.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("equifax", customerSpouseDetail.getEquifaxScore());
		query.setParameter("experian", customerSpouseDetail.getExperianScore());
		query.setParameter("transunion",
		        customerSpouseDetail.getTransunionScore());
		query.setParameter("id", customerSpouseDetail.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer updatePhotoURL(String s3ImagePath, Integer userid) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.photoImageUrl = :imagePath WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("imagePath", s3ImagePath);
		query.setParameter("id", userid);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer updateTokenDetails(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.emailEncryptionToken = :emailEncryptionToken , usr.tokenGeneratedTime =:tokenGeneratedTime WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("emailEncryptionToken",
		        user.getEmailEncryptionToken());
		query.setParameter("tokenGeneratedTime", user.getTokenGeneratedTime());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public List<User> getUsersList() {

		String searchQueryCustomer = "FROM User where internalUserDetail IS NULL";
		String searchQueryInternalUser = "FROM User where internalUserDetail.activeInternal!=:DELETED";

		Session session = sessionFactory.getCurrentSession();

		Query queryCustomer = session.createQuery(searchQueryCustomer);
		Query queryInternalUser = session.createQuery(searchQueryInternalUser);

		queryInternalUser.setParameter("DELETED", ActiveInternalEnum.DELETED);
		
		List<User> userList = queryCustomer.list();
		userList.addAll(queryInternalUser.list());

		return userList;

	}

	@Override
	public boolean changeUserPassword(UpdatePasswordVO updatePasswordVO)
	        throws DatabaseException, HibernateException {
		int rowEffected;

		Session session = sessionFactory.getCurrentSession();

		String hql = "UPDATE User usr set usr.password ='"
		        + updatePasswordVO.getNewPassword() + "' where usr.id="
		        + updatePasswordVO.getUserId();
		Query query = session.createQuery(hql);

		rowEffected = query.executeUpdate();

		if (rowEffected == 0)
			return false;

		else

			return true;

	}

	@Override
	public void verifyEmail(int userID) throws DatabaseException,
	        HibernateException {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.emailVerified = :emailVerified WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("emailVerified", Boolean.TRUE);
		query.setParameter("id", userID);
		query.executeUpdate();

	}

	@Override
	public List<User> searchUsers(User user) {

		Session session = sessionFactory.getCurrentSession();
		String searchQuery = "FROM User where ";

		if (user.getEmailId() != null)
			searchQuery += " emailId like '" + user.getEmailId() + "%' or ";

		if (user.getFirstName() != null) {
			user.setFirstName(user.getFirstName().toLowerCase());
		} else {
			user.setFirstName("");
		}
		searchQuery += " lower(concat( firstName,lastName) ) like '"
		        + user.getFirstName() + "%'";

		if (user.getUserRole() != null) {
			searchQuery += " and userRole=:userRole";
			// session.save(user.getUserRole());
		}
		if (user.getInternalUserDetail() != null
		        && user.getInternalUserDetail().getInternaUserRoleMaster() != null) {
			searchQuery += " and (internalUserDetail IS NULL OR (internalUserDetail.internaUserRoleMaster=:internaUserRoleMaster))";
		}

		searchQuery += " and status=" + CommonConstants.STATUS_ACTIVE;

		
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

		Integer userId = (Integer) this.save(user);

		// Check if username is unique in user table, if not, append userID

		String newUserName = user.getUsername().split("@")[0];
		newUserName = newUserName.replaceAll("\\.", "_");
		newUserName = newUserName.replaceAll("\\+", "_");
		if (this.checkUserNameIsUnique(newUserName) == null) {
			user.setUsername(newUserName);
		} else {
			user.setUsername(newUserName + "_" + userId);
		}

		this.update(user);
		return userId;
	}

	private Object checkUserNameIsUnique(String newUserName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("username", newUserName));
		Object obj = criteria.uniqueResult();
		return obj;

	}

	@Override
	public Integer competeUserProfile(User user) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id,usr.phoneNumber=:priPhoneNumber WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("priPhoneNumber", user.getPhoneNumber());
		query.setParameter("id", user.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer completeCustomerDetails(CustomerDetail customerDetail) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.addressStreet = :street,customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob,customerdetail.secPhoneNumber=:secPhoneNumber,customerdetail.secEmailId=:secEmailId,customerdetail.profileCompletionStatus=:profileStatus WHERE customerdetail.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("city", customerDetail.getAddressCity());
		query.setParameter("street", customerDetail.getAddressStreet());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("secPhoneNumber", customerDetail.getSecPhoneNumber());
		query.setParameter("secEmailId", customerDetail.getSecEmailId());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("profileStatus",
		        customerDetail.getProfileCompletionStatus());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer managerUpdateUserProfile(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.firstName = :first_name,usr.lastName =:last_name,usr.emailId=:email_id,phoneNumber=:phoneNumber WHERE usr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("first_name", user.getFirstName());
		query.setParameter("last_name", user.getLastName());
		query.setParameter("email_id", user.getEmailId());
		query.setParameter("id", user.getId());
		query.setParameter("phoneNumber", user.getPhoneNumber());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer managerUpdateUCustomerDetails(CustomerDetail customerDetail) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerdetail set customerdetail.addressStreet = :street,customerdetail.addressCity = :city,customerdetail.addressState =:state,customerdetail.addressZipCode=:zipcode,customerdetail.dateOfBirth=:dob WHERE customerdetail.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("street", customerDetail.getAddressStreet());
		query.setParameter("city", customerDetail.getAddressCity());
		query.setParameter("state", customerDetail.getAddressState());
		query.setParameter("zipcode", customerDetail.getAddressZipCode());
		query.setParameter("dob", customerDetail.getDateOfBirth());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public User findInternalUser(Integer userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("id", userID));
		User user = (User) criteria.uniqueResult();
		if (user != null) {
			Hibernate.initialize(user.getInternalUserDetail());
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
		if (user == null) {
			return null;
		}
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
		case SYSTEM:
			userVO.setUserRole(UserRolesEnum.SYSTEM.toString());
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
			if (userId != null) {
				UserRoleNameImageVO imageVO = findUserDetails(userId.intValue());
				if (imageVO != null) {
					imageVOs.add(imageVO);
				}

			}

		}
		return imageVOs;
	}

	@Override
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
		criteria.add(Restrictions.eq("status", 1));
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
			List<InternalUserStateMapping> internalUsers = criteria.list();

			if (internalUsers == null || internalUsers.isEmpty()) {
				// No users found for this state
				return Collections.EMPTY_LIST;
			}
			// Check amongst the users who is free

			List<User> availableUsers = new ArrayList<User>();

			/*
			 * From the list available, consider only Loan advisors who have
			 * entered LQB credentials
			 */
			for (InternalUserStateMapping internalUser : internalUsers) {
				if (internalUser.getUser().getInternalUserDetail() != null
				        && internalUser.getUser().getInternalUserDetail()
				                .getLqbUsername() != null
				        && internalUser.getUser().getInternalUserDetail()
				                .getInternaUserRoleMaster().getRoleName()
				                .equals(UserRolesEnum.LM.getName())) {
					addIfUserIsEligible(internalUser.getUser(), availableUsers);
				}

			}
			return availableUsers;
		} catch (HibernateException exception) {
			LOG.warn("State not present in system: " + stateName);
			return Collections.EMPTY_LIST;
		}

	}

	@Override
	public List<User> getLoanManagerWithLeastWork() {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.isNotNull("internalUserDetail"));

		try {
			List<User> users = criteria.list();
			List<User> availableUsers = new ArrayList<User>();
			for (User user : users) {
				addIfUserIsEligible(user, availableUsers);
			}
			return availableUsers;
		} catch (HibernateException exception) {
			exception.printStackTrace();
			LOG.error("Exception, but not expected", exception);
			LOG.warn("No users available ");
			return Collections.EMPTY_LIST;
		}

	}

	private void addIfUserIsEligible(User user, List<User> availableUsers) {
		if (user.getInternalUserDetail().getActiveInternal() == ActiveInternalEnum.ACTIVE) {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(LoanTeam.class);
			criteria.add(Restrictions.eq("user.id", user.getId()));
			List<LoanTeam> loanTeamList = criteria.list();
			List<Loan> activeLoanList = new ArrayList<Loan>();
			user.setLoans(activeLoanList);

			if (loanTeamList == null || loanTeamList.isEmpty()) {
				// This means that the user is not part of any team. Hence has
				// no work
				availableUsers.add(user);
			}
			int todaysLoanCount = 0;
			for (LoanTeam loanTeam : loanTeamList) {
				if (loanTeam.getLoan().getLoanProgressStatus().getId() == LoanProgressStatusMasterEnum.IN_PROGRESS
				        .getStatusId()
				        || loanTeam.getLoan().getLoanProgressStatus().getId() == LoanProgressStatusMasterEnum.NEW_LOAN
				                .getStatusId()) {
					// It does not matter if the user is assigned a loan which
					// is not active. Hence, for computation, we are considering
					// only those loans which he is currently working on

					// Additional check, if the loan is created today
					if (loanWasCreatedToday(loanTeam.getLoan().getId(),
					        loanTeam.getLoan().getCreatedDate())) {
						todaysLoanCount = user.getTodaysLoansCount();
						todaysLoanCount++;
						user.setTodaysLoansCount(todaysLoanCount);
					}

					user.getLoans().add(loanTeam.getLoan());

				}
			}
			availableUsers.add(user);

		}
	}

	private boolean loanWasCreatedToday(int loanId, Date createdDate) {
		// TODO Auto-generated method stub
		if (createdDate == null) {
			// TODO: Cannot happen
			LOG.warn("There is a lone without created date: " + loanId);
			return false;
		}
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone(GMT));
		today.setTime(new Date(System.currentTimeMillis()));
		Calendar loanCreated = Calendar.getInstance(TimeZone.getTimeZone(GMT));
		loanCreated.setTime(createdDate);
		return (today.get(Calendar.ERA) == loanCreated.get(Calendar.ERA)
		        && today.get(Calendar.YEAR) == loanCreated.get(Calendar.YEAR) && today
		            .get(Calendar.DAY_OF_YEAR) == loanCreated
		        .get(Calendar.DAY_OF_YEAR));

	}

	@Override
	public UserVO getDefaultLoanManagerForRealtor(UserVO realtor,
	        String stateName) {
		User user = findByUserId(realtor.getId());
		User defaultLoanManager = user.getRealtorDetail()
		        .getDefaultLoanManager();

		return User.convertFromEntityToVO(defaultLoanManager);
	}

	@Override
	public UserVO getDefaultSalesManager() {
		// This is a temporary method only to meet an absurd requirement of
		// assigning all loans to Pat.

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);

		criteria.add(Restrictions.isNotNull("internalUserDetail"));
		criteria.createAlias("internalUserDetail", "userDetail");
		criteria.createAlias("userDetail.internaUserRoleMaster", "role");
		criteria.add(Restrictions.eq("role.id",
		        InternalUserRolesEum.SM.getRoleId()));
		List<User> users = criteria.list();
		if (users == null || users.isEmpty()) {
			LOG.error("This cannot happen, there has to be a sales manager in the system ");
			// TODO: Write to error table and email
			return null;

		}
		if (users.size() > 1) {
			LOG.warn("There are more than one sales manager in the system, which is not handled. Checking if user name contains pat, and returning that user. IF not found, then returning the first instance");
			for (User user : users) {
				if (user.getFirstName().contains("pat")) {
					return User.convertFromEntityToVO(user);
				}

			}

		}

		return User.convertFromEntityToVO(users.get(0));
	}

	@Override
	public void updateLoginTime(Date date, int userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.lastLoginDate=:DATE WHERE usr.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("ID", userId);
		query.setTimestamp("DATE", date);
		int result = query.executeUpdate();

	}

	@Override
	public Integer updateInternalUserDetail(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE InternalUserDetail internalusr set internalusr.activeInternal = :activeInternal WHERE internalusr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", user.getInternalUserDetail().getId());
		query.setParameter("activeInternal", user.getInternalUserDetail()
		        .getActiveInternal());
		int result = query.executeUpdate();
		LOG.info("updated Successfully");
		return result;
	}

	@Override
	public Integer updateNMLS(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE InternalUserDetail internalusr set internalusr.nmlsID = :nmlsID WHERE internalusr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", user.getInternalUserDetail().getId());
		query.setParameter("nmlsID", user.getInternalUserDetail().getNmlsID());
		int result = query.executeUpdate();
		LOG.info("updated Successfully");
		return result;
	}

	@Override
	public Integer updateLqbProfile(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE InternalUserDetail internalusr set internalusr.lqbUsername = :lqbUserName, internalusr.lqbPassword = :lqbPassword, "
		        + "internalusr.lqbAuthToken = :lqbAuthToken, internalusr.lqbExpiryTime= :lqbExpiryTime "
		        + "WHERE internalusr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", user.getInternalUserDetail().getId());
		query.setParameter("lqbUserName", user.getInternalUserDetail()
		        .getLqbUsername());
		query.setParameter("lqbPassword", user.getInternalUserDetail()
		        .getLqbPassword());
		query.setParameter("lqbAuthToken", user.getInternalUserDetail()
		        .getLqbAuthToken());
		query.setParameter("lqbExpiryTime", user.getInternalUserDetail()
		        .getLqbExpiryTime());
		int result = query.executeUpdate();
		LOG.info("updated Successfully");
		return result;
	}

	@Override
	public void updateTokenDetails(int internalUserId, String token,
	        long currentTimeMillis) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE InternalUserDetail internalusr set "
		        + "internalusr.lqbAuthToken = :lqbAuthToken, internalusr.lqbExpiryTime= :lqbExpiryTime "
		        + "WHERE internalusr.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", internalUserId);
		query.setParameter("lqbAuthToken", token);
		query.setParameter("lqbExpiryTime", currentTimeMillis);
		int result = query.executeUpdate();
		LOG.info("updated Successfully");

	}

	@Override
	public void updateLMID(Integer userId, int loanManagerId) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.load(User.class, userId);
		String hql = "UPDATE RealtorDetail realtor set realtor.defaultLoanManager = :LM "
		        + "WHERE realtor.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", user.getRealtorDetail().getId());
		query.setEntity("LM", new User(loanManagerId));
		query.executeUpdate();
	}

	@Override
	public List<String> getDefaultUsers(String userName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("username", userName));

		Object obj = criteria.uniqueResult();
		if (obj != null) {
			User user = (User) obj;
			if (user.getInternalUserDetail() != null) {
				List<String> emailIds = new ArrayList<String>();
				emailIds.add(user.getEmailId());
				return emailIds;
			} else if (user.getRealtorDetail() != null) {
				List<String> emailIds = new ArrayList<String>();
				if (user.getRealtorDetail().getDefaultLoanManager() != null) {
					emailIds.add(user.getRealtorDetail()
					        .getDefaultLoanManager().getEmailId());
				} else {
					emailIds.add("");
				}
				emailIds.add(user.getEmailId());

				return emailIds;
			}
		}
		return null;
	}

	@Override
	public Integer updateRealtorDetails(RealtorDetail realtor) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE RealtorDetail realtor set realtor.licenceInfo = :licenceInfo,realtor.profileUrl=:profileUrl,realtor.defaultLoanManager=:defaultLoanManager WHERE realtor.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("licenceInfo", realtor.getLicenceInfo());
		query.setParameter("profileUrl", realtor.getProfileUrl());
		query.setParameter("defaultLoanManager",
		        realtor.getDefaultLoanManager());
		query.setParameter("id", realtor.getId());
		int result = query.executeUpdate();
		return result;

	}

	@Override
	public Integer UpdateUserProfile(String phoneNumber, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.phoneNumber = :PHONENUMBER WHERE usr.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("PHONENUMBER", phoneNumber);
		query.setParameter("ID", userId);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public User findByToken(String token) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("emailEncryptionToken", token));
		User user = (User) criteria.uniqueResult();
		return user;

	}

	@Override
	public User getUserByUserName(String userName)
	        throws NoRecordsFetchedException {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(User.class);
			criteria.add(Restrictions.eq("username", userName));
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

	@Override
	public InternalUserStateMapping updateInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo) {

		InternalUserStateMapping internalUserStateMapping = InternalUserStateMapping
		        .convertFromVOToEntity(inputVo);
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(internalUserStateMapping);

		return internalUserStateMapping;
	}

	@Override
	public InternalUserStateMapping deleteInternalUserStateMapping(
	        InternalUserStateMappingVO inputVo) {

		InternalUserStateMapping internalUserStateMapping = InternalUserStateMapping
		        .convertFromVOToEntity(inputVo);
		Session session = sessionFactory.getCurrentSession();
		session.delete(internalUserStateMapping);

		return internalUserStateMapping;
	}

	@Override
	public Integer updateUserStatus(User user) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE User usr set usr.status = :status WHERE usr.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("status", user.getStatus());
		query.setParameter("ID", user.getId());
		int result = query.executeUpdate();
		return result;

	}

	@Override
	public Integer updateTutorialStatus(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CustomerDetail customerDetail set customerDetail.tutorialStatus = :tutorialStatus WHERE customerDetail.id = :ID";
		Query query = session.createQuery(hql);
		query.setParameter("ID", id);
		query.setParameter("tutorialStatus", true);
		int result = query.executeUpdate();
		return result;

	}

	@Override
	public List<User> getAllSalesManagers() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);

		criteria.add(Restrictions.isNotNull("internalUserDetail"));
		criteria.add(Restrictions.eq("status", 1));
		criteria.createAlias("internalUserDetail", "userDetail");
		
		criteria.createAlias("userDetail.internaUserRoleMaster", "role");
		criteria.add(Restrictions.eq("role.id",
		        InternalUserRolesEum.SM.getRoleId()));
		List<User> users = criteria.list();

		if (users == null || users.isEmpty()) {
			LOG.error("This cannot happen, there has to be a sales manager in the system ");
			// TODO: Write to error table and email
			return null;

		}
		for (User user : users) {
			Hibernate.initialize(user.getInternalUserDetail());
		}
		return users;

	}

	@Override
	public List<User> getUserBySecondaryMail(String emailAddress) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(CustomerDetail.class);
		criteria.add(Restrictions.eq("secEmailId", emailAddress));
		List<CustomerDetail> objList = criteria.list();
		List<User> userList = new ArrayList<User>();
		User user = null;
		if (objList != null) {
			for (CustomerDetail obj : objList) {
				Criteria userCriteria = session.createCriteria(User.class);
				userCriteria.add(Restrictions.eq("customerDetail", obj));
				user = (User) userCriteria.uniqueResult();
				if (user != null) {
					userList.add(user);
				}
			}

		}
		return userList;

	}
}
