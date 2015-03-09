package com.nexera.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.GenericDao;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.core.service.MasterDataService;

@Component
public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	@Qualifier("genericDaoImpl")
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<InternalUserRoleMaster> getInternalUserRoleMaster() {

		return genericDao.loadAll(InternalUserRoleMaster.class);

	}

	@Override
	@Transactional(readOnly = true)
	public List<NeedsListMaster> getNeedListMaster() {
		return genericDao.loadAll(NeedsListMaster.class);
	}

}
