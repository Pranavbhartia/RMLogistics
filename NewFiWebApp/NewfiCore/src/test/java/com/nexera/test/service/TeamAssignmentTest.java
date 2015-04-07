package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.TeamAssignmentHelper;
import com.nexera.core.service.UserProfileService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamAssignmentTest {

	@Autowired
	TeamAssignmentHelper teamAssignmentHelper;

	@Autowired
	UserProfileService userProfileService;

	@Test
	public void testGetSearch() {

		UserVO userVO = new UserVO();
		userVO.setFirstName("n");
		InternalUserDetailVO internalUserVO = new InternalUserDetailVO();
		UserRoleVO roleVO = new UserRoleVO();
		roleVO.setId(1);
		InternalUserRoleMasterVO internaUserRoleMasterVO = new InternalUserRoleMasterVO();
		internaUserRoleMasterVO.setId(3);
		internalUserVO.setInternalUserRoleMasterVO(internaUserRoleMasterVO);
		userVO.setInternalUserDetail(internalUserVO);
		userVO.setUserRole(roleVO);
		userProfileService.searchUsers(userVO);
	}

	// @Test
	public void testGetDefaultLoanManager() {
		UserVO userVO = teamAssignmentHelper.getDefaultLoanManager("RI");
		System.out.println("Loan manager will be: " + userVO);
	}

	// @Test
	public void testGetDefaultLoanManagerForRealtorUrl() {
		UserVO realtor = new UserVO();
		realtor.setId(12);
		UserVO userVO = teamAssignmentHelper
		        .getDefaultLoanManagerForRealtorUrl(realtor, "RI");
		System.out.println("Loan manager will be: " + userVO);
	}
}
