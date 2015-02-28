package com.nexera.test.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class LoanServiceTest {

	@Autowired
	private LoanService loanService;

	@Test
	public void test() {
		UserVO user = new UserVO();
		user.setId(1);
		loanService.retrieveDashboard(user);
	}

}
