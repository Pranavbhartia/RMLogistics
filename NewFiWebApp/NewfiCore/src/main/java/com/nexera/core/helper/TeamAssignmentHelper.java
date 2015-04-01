package com.nexera.core.helper;

import java.util.List;

import com.nexera.common.vo.UserVO;

public interface TeamAssignmentHelper {

	public UserVO getDefaultLoanManager(String stateName);

	List<UserVO> getDefaultLoanManagerForRealtorUrl(UserVO realtor,
	        String stateName);

	List<UserVO> getDefaultLoanManagerForLoanManagerUrl(UserVO loanManager,
	        String stateName);

}
