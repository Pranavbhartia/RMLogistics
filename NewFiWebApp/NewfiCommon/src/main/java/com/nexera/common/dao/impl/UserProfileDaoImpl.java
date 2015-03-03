package com.nexera.common.dao.impl;

import java.util.List;

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

import com.nexera.common.commons.DisplayMessageConstants;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
@Transactional
public class UserProfileDaoImpl extends GenericDaoImpl implements
		UserProfileDao {

	private static final Logger LOG = LoggerFactory
			.getLogger(UserProfileDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

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
	public List<User> searchUsers(User user) {

		Session session = sessionFactory.getCurrentSession();
		String searchQuery = "FROM User where lower(concat( first_name,',',last_name) ) like '%"
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
		List<User> userList=query.list();
		
		for(User userObj:userList){
			Hibernate.initialize(user.getInternalUserDetail());
			if (userObj.getInternalUserDetail() != null)
				Hibernate.initialize(userObj.getInternalUserDetail()
						.getInternaUserRoleMaster());
		}
		
		return userList;
	}

	@Override
	public Integer saveInternalUser(User user) {
		if(null!=user.getInternalUserDetail()){
			this.save(user.getInternalUserDetail());
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


	public User loadInternalUser(Integer userID) {
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
}
