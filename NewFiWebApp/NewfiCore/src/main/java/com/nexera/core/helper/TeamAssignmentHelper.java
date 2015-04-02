package com.nexera.core.helper;

import com.nexera.common.vo.UserVO;

public interface TeamAssignmentHelper {

	public UserVO getDefaultLoanManager(String stateName);

	UserVO getDefaultLoanManagerForRealtorUrl(UserVO realtor, String stateName);

}
