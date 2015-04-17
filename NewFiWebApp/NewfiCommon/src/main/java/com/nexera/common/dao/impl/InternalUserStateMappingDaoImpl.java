package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.InternalUserStateMappingDao;
import com.nexera.common.entity.InternalUserStateMapping;
import com.nexera.common.entity.MileStoneTurnAroundTime;

@Component
@Transactional
public class InternalUserStateMappingDaoImpl extends GenericDaoImpl implements InternalUserStateMappingDao {

	@Override
	public void saveOrUpdateUserStates(
			List<InternalUserStateMapping> internalUserStateMappings) {
			for (InternalUserStateMapping internalUserStateMapping : internalUserStateMappings) {
				this.saveOrUpdate(internalUserStateMapping);
			}
	}

	@Override
	public List<InternalUserStateMapping> retrieveStatesForUser(Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session
				.createCriteria(InternalUserStateMapping.class);
		criteria.add(Restrictions.eq("user.id",
				userId));
		return criteria.list();
	}

	@Override
	public void deleteObj(InternalUserStateMapping stateMapping) {
		Session session = sessionFactory.getCurrentSession();
		InternalUserStateMapping internalUserStateMapping=new InternalUserStateMapping();
		internalUserStateMapping=(InternalUserStateMapping) session.load(InternalUserStateMapping.class, stateMapping.getId());
		session.delete(internalUserStateMapping);
	}

}
