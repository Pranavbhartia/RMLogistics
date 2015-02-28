package com.nexera.common.dao.impl;


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
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;


@Component
@Transactional
public class UserProfileDaoImpl extends GenericDaoImpl implements UserProfileDao {

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
		query.setParameter("profileStatus", customerDetail.getProfileCompletionStatus());
		query.setParameter("id", customerDetail.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
	}

}
