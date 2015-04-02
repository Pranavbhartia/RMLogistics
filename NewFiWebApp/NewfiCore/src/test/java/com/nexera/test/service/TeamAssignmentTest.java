package com.nexera.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.UserVO;
import com.nexera.core.helper.TeamAssignmentHelper;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamAssignmentTest {

	@Autowired
	TeamAssignmentHelper teamAssignmentHelper;

	@Test
	public void testGetDefaultLoanManager() {
		UserVO userVO = teamAssignmentHelper.getDefaultLoanManager("AB");
		System.out.println("Loan manager will be: " + userVO);
	}

	@Test
	public void testGetDefaultLoanManagerForRealtorUrl() {
		// fail("Not yet implemented");
	}

	@Test
	public void testGetDefaultLoanManagerForLoanManagerUrl() {
		// fail("Not yet implemented");
	}

}
