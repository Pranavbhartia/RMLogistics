package com.nexera.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nexera.common.dao.GenericDao;
import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.core.service.MasterDataService;

public class MasterDataServiceImpl implements MasterDataService {

	@Autowired
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	@Override
	public List<InternalUserRoleMaster> getInternalUserRoleMaster() {

		return genericDao.loadAll(InternalUserRoleMaster.class);

	}

}
