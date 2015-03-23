package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.User;

@Component
public class LoanAppFormDaoImpl extends GenericDaoImpl implements
		LoanAppFormDao {

	@Override
	public LoanAppForm findById(int appFormId) {
		// TODO Auto-generated method stub
		return null;
	}
	public LoanAppForm findByuserID(int userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		User user=new User();
		user.setId(userID);
		criteria.add(Restrictions.eq("user", user));
		LoanAppForm loalAppForm=(LoanAppForm) criteria.uniqueResult();
		return loalAppForm;
	}
	
	

}
