package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.NotificationDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNotification;
import com.nexera.common.entity.User;

@Component
public class NotificationDaoImpl extends GenericDaoImpl implements
		NotificationDao {

	@Override
	public List<LoanNotification> findActiveNotifications(Loan loan,
			User user) {
		// TODO Auto-generated method stub
		
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(LoanNotification.class);
		criteria.add(Restrictions.eq("loan", loan));
		
		
		return criteria.list();
	}
}
