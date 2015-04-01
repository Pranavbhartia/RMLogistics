package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.ZipCodeLookup;

@Component
public class StateLookupDaoImpl extends GenericDaoImpl implements
        StateLookupDao {

	@Override
	public List<ZipCodeLookup> findZipCodesForStateID(Integer stateID) {

		if (stateID == null || stateID < 1)
			return null;

		StateLookup state = new StateLookup();
		state.setId(stateID);

		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        ZipCodeLookup.class);

		criteria.add(Restrictions.eq("stateLookup", state));

		return criteria.list();
	}

}
