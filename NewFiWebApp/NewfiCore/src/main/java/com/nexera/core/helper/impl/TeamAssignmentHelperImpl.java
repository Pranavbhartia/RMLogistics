package com.nexera.core.helper.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger LOG = LoggerFactory
	        .getLogger(TeamAssignmentHelperImpl.class);

	@Override
	public UserVO getDefaultLoanManager(String stateName) {

		/*
		 * Get the loan manager based on statename who has least work. i.e who
		 * has least number of loans operating with loan inprogress status and
		 * is active
		 */
		List<User> userList = null;
		if (stateName != null && !stateName.isEmpty()) {
			userList = userProfileDao.getLoanManagerForState(stateName);

			if (userList != null && !userList.isEmpty()) {
				return pickTheChosenOne(userList);
			}

		}

		/*
		 * If there is none available, then remove the state criteria and return
		 * the loan manager.
		 */
		userList = null;
		userList = userProfileDao.getLoanManagerWithLeastWork();
		if (!userList.isEmpty()) {
			return pickTheChosenOne(userList);
		}
		// This cannot happen.
		return null;
	}

	private UserVO pickTheChosenOne(List<User> userList) {
		LOG.debug("So many users are eligible: " + userList.size());
		LOG.debug("Sorting by assending order of loan size and returning the one with least work");
		Collections.sort(userList, new Comparator<User>() {
			public int compare(User o1, User o2) {
				if (o1.getLoans() == null || o2.getLoans() == null)
					return 0;
				return Integer.compare(o1.getLoans().size(), o2.getLoans()
				        .size());
			}
		});
		// Now we have a sorted list return the
		// first element
		UserVO chosenUser = User.convertFromEntityToVO(userList.get(0));
		LOG.debug("The chosen one is: " + chosenUser);
		return chosenUser;
	}

	@Override
	public UserVO getDefaultLoanManagerForRealtorUrl(UserVO realtor,
	        String stateName) {

		/*
		 * Get the default loan manager for this realtor, if there is more than
		 * one available, then retrieve the one with matching state. If none
		 * available, return random
		 */

		if (stateName == null || stateName.isEmpty()) {
			return this.getDefaultLoanManager(null);
		}
		UserVO userVO = userProfileDao.getDefaultLoanManagerForRealtor(realtor,
		        stateName);
		if (userVO == null || userVO.getId() == 0) {
			// The realtor does not have default loan manager. Hence return the
			// one with least work
			return this.getDefaultLoanManager(stateName);
		}
		return userVO;
	}
}
