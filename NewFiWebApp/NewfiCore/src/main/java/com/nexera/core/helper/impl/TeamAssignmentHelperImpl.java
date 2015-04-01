package com.nexera.core.helper.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.User;
import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.TeamAssignmentHelper;

@Component
public class TeamAssignmentHelperImpl implements TeamAssignmentHelper {

	@Autowired
	UserProfileDao userProfileDao;

	@Override
	public UserVO getDefaultLoanManager(String stateName) {

		/*
		 * Get the loan manager based on statename who has least work. i.e who
		 * has least number of loans operating with loan inprogress status and
		 * is active
		 */

		List<User> userList = userProfileDao.getLoanManagerForState(stateName);
		if (!userList.isEmpty()) {
			// Collections.sort(userList, new Comparator<User>() {
			// public int compare(User o1, User o2) {
			// if (o1.getlo() == null
			// || o2.getSortOrderDate() == null)
			// return 0;
			// return o2.getSortOrderDate().compareTo(
			// o1.getSortOrderDate());
			// }
			// });
		}

		/*
		 * If there is none available, then remove the state criteria and return
		 * the loan manager.
		 */

		return null;
	}

	@Override
	public List<UserVO> getDefaultLoanManagerForRealtorUrl(UserVO realtor,
	        String stateName) {

		/*
		 * Get the default loan manager for this realtor, if there is more than
		 * one available, then retrieve the one with matching state. If none
		 * available, return random
		 */

		return null;
	}

	@Override
	public List<UserVO> getDefaultLoanManagerForLoanManagerUrl(
	        UserVO loanManager, String stateName) {

		/*
		 * Assign this loan manager to the team. IF the loan manager does not
		 * operate in the state, then add another one from the state if any
		 * available.
		 */

		return null;
	}

}
