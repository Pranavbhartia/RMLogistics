package com.nexera.common.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.ZipCodeLookup;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.NoRecordsFetchedException;

@Component
public class StateLookupDaoImpl extends GenericDaoImpl implements
        StateLookupDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(StateLookupDaoImpl.class);
	@Override
	@Transactional(readOnly = true)
	public StateLookup findStateLookupByStateCode(String stateCode)
	        throws NoRecordsFetchedException {
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(StateLookup.class);
			criteria.add(Restrictions.eq("statecode", stateCode));
			Object obj = criteria.uniqueResult();
			if (obj == null) {
				throw new NoRecordsFetchedException("Record for statecode : "
				        + stateCode + " not found in database");
			}
			StateLookup lookup = (StateLookup) obj;
			return lookup;
		} catch (HibernateException hibernateException) {
			LOG.error("Exception caught in findStateLookupByStateCode() ",
			        hibernateException);
			throw new DatabaseException(
			        "Exception caught in findStateLookupByStateCode() ",
			        hibernateException);
		}
	}

	@Override
	@Transactional(readOnly = true)
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

	@Override
	public String getStateCodeByZip(String addressZipCode) {
		LOG.info("Find state by zip code of zipcode Value = "+addressZipCode);
		ZipCodeLookup lookup = getStatelookupValues(addressZipCode);
		if(null != lookup)
		return lookup.getStateLookup().getStatecode();
		else{
			return CommonConstants.ZIPCODE_ISNOT_VALID;
		}
	}

	private ZipCodeLookup getStatelookupValues(String zipCode) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
		        ZipCodeLookup.class);
		criteria.add(Restrictions.eq("zipcode", zipCode));
		ZipCodeLookup lookup = (ZipCodeLookup) criteria.uniqueResult();
		if (lookup == null || lookup.getStateLookup() == null) {
			return null;
		}
		Hibernate.initialize(lookup.getStateLookup());
		return lookup;
	}

	@Override
	public HashMap<String, String> getZipCodeData(String zipCode) {
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		ZipCodeLookup lookup=getStatelookupValues(zipCode);
		if(lookup!=null){
			
			map.put("stateName", lookup.getStateLookup().getStatename());
			map.put("countyName", lookup.getCountyname());
			map.put("cityName", lookup.getCityname());
			return map;
		}
		return map;
	}

	@Override
	@Transactional(readOnly = true)
	public String validateZip(String zipCode) {
		
		
		int count = 0;
		String stateCode = getStateCodeByZip(zipCode);
		if(!stateCode.equalsIgnoreCase(CommonConstants.ZIPCODE_ISNOT_VALID)){
			for(String allowedStateCode : CommonConstants.allowedStates){
				if (allowedStateCode.equalsIgnoreCase(stateCode)){
					count ++;
					return CommonConstants.ZIPCODE_VALID;
				}
			}
			if(count == 0){
				return  CommonConstants.ZIPCODE_ISNOT_APPROVED;
			}
			
		}
		return CommonConstants.ZIPCODE_ISNOT_VALID;
	}
}
