package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
	public void testGetDefaultLoanManager() {
		userProfileService.getUsersList();
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
