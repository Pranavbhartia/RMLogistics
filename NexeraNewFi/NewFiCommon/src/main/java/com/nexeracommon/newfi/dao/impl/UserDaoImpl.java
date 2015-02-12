package com.nexeracommon.newfi.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexeracommon.newfi.dao.UserDao;
import com.nexeracommon.newfi.model.UserModel;

@Component
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public UserModel findByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class);
		criteria.add(Restrictions.eq("email", userName));
		return (UserModel) criteria.uniqueResult();
	}

	public UserModel findByUserId(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UserModel.class);
		criteria.add(Restrictions.eq("Id", userId));
		return (UserModel) criteria.uniqueResult();
	}

}
