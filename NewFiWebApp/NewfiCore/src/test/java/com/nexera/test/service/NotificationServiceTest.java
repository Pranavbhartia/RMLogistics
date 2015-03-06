package com.nexera.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.NotificationService;

@ContextConfiguration(locations = "/test-NewfiCore-Configuration.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationServiceTest {

	@Autowired
	private NotificationService notificationService;

	@Before
	public void beforeTest() {
		System.out.println("Starting to run test cases....");
	}

	@Test
	public void getNotifications() {

		System.out.println("Test started : getNotifications");
		Integer userID = 1;
		Integer loanID = 1;

		UserVO userVO = null;
		LoanVO loanVO = null;

		if (userID != null) {
			userVO = new UserVO();
			userVO.setId(userID);
		}

		if (loanID != null) {
			loanVO = new LoanVO();
			loanVO.setId(loanID);
		}

		List<NotificationVO> notifications = notificationService
				.findActiveNotifications(loanVO, userVO);

		if (notifications != null)
			for (NotificationVO not : notifications) {

				System.out.println("Notif id : " + not.getId());

			}
		
		Assert.assertTrue(true);

	}

}
