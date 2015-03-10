package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.InternalUserRoleMaster;
import com.nexera.common.entity.NeedsListMaster;

public interface MasterDataService {

	public List<InternalUserRoleMaster> getInternalUserRoleMaster();
	public List<NeedsListMaster> getNeedListMaster();
}
