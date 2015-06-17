package com.nexera.core.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.StateLookupDao;
import com.nexera.common.entity.StateLookup;
import com.nexera.common.entity.ZipCodeLookup;
import com.nexera.common.vo.StateLookupVO;
import com.nexera.common.vo.ZipCodeLookupVO;
import com.nexera.core.service.StateLookupService;

@Component
public class StateLookupServiceImpl implements StateLookupService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(StateLookupServiceImpl.class);

	@Autowired
	private StateLookupDao stateLookupDao;

	@Override
	@Transactional(readOnly = true)
	public List<StateLookupVO> getStatesList() {

		LOG.info("Method getStatesList() called to fetch the list of states of US");

		List<StateLookup> list = stateLookupDao.loadAll(StateLookup.class);
		Collections.sort(list, new Comparator<StateLookup>() {
			public int compare(StateLookup o1, StateLookup o2) {

				return o1.getStatecode().compareTo(o2.getStatecode());
			}
		});
		LOG.info("Method getStatesList() finished");
		return StateLookup.convertToVo(list);

	}

	@Override
	@Transactional(readOnly = true)
	public List<ZipCodeLookupVO> findZipCodesForStateID(Integer stateID) {
		return ZipCodeLookup.convertToVo(stateLookupDao
		        .findZipCodesForStateID(stateID));
	}

	@Override
	@Transactional(readOnly = true)
	public String getStateCodeByZip(String addressZipCode) {
		if (addressZipCode == null || addressZipCode.isEmpty()) {
			return null;
		}
		// TODO Auto-generated method stub
		return stateLookupDao.getStateCodeByZip(addressZipCode);
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean validateZipCode(String zipCode) {
		return stateLookupDao.validateZip(zipCode);
	}
}
