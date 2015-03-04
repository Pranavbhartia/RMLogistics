package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.NotificationDao;
import com.nexera.common.entity.LoanNotification;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanNotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.UserProfileService;

@Component
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationDao notificationDao;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LoanService loanService;

	@Override
	@Transactional(readOnly = true)
	public List<LoanNotificationVO> findActiveNotifications(LoanVO loanVO,
			UserVO userVO) {

		List<LoanNotificationVO> notList = buildLoanNotificationVOList(notificationDao
				.findActiveNotifications(loanService.parseLoanModel(loanVO),
						userProfileService.parseUserModel(userVO)));

		return notList;
	}

	private LoanNotificationVO buildLoanNotificationVO(
			LoanNotification loanNotification) {
		if (loanNotification == null)
			return null;

		LoanNotificationVO vo = new LoanNotificationVO();
		vo.setId(loanNotification.getId());
		vo.setContent(new String(loanNotification.getContent()));
		if (loanNotification.getCreatedBy() != null)
			vo.setCreatedByID(loanNotification.getCreatedBy().getId());
		if (loanNotification.getCreatedFor() != null)
			vo.setCreatedForID(loanNotification.getCreatedFor().getId());

		return vo;
	}

	private List<LoanNotificationVO> buildLoanNotificationVOList(
			List<LoanNotification> loanNotifications) {

		if (loanNotifications == null)
			return null;

		List<LoanNotificationVO> voList = new ArrayList<LoanNotificationVO>();

		for (LoanNotification not : loanNotifications) {
			voList.add(buildLoanNotificationVO(not));
		}
		return voList;
	}

	private LoanNotification parseLoanNotificationModel(
			LoanNotificationVO loanNotification) {
		if (loanNotification == null)
			return null;

		LoanNotification model = new LoanNotification();
		model.setId(loanNotification.getId());
		if (loanNotification.getContent() != null)
			model.setContent(loanNotification.getContent().getBytes());
		if (loanNotification.getCreatedByID() != null) {
			User createdBy = new User();
			createdBy.setId(loanNotification.getCreatedByID());
			model.setCreatedBy(createdBy);
		}

		if (loanNotification.getCreatedForID() != null) {
			User createdFor = new User();
			createdFor.setId(loanNotification.getCreatedForID());
			model.setCreatedBy(createdFor);
		}

		return model;
	}

	private List<LoanNotification> parseLoanNotificationModelList(
			List<LoanNotificationVO> loanNotifications) {

		if (loanNotifications == null)
			return null;

		List<LoanNotification> modelList = new ArrayList<LoanNotification>();

		for (LoanNotificationVO not : loanNotifications) {
			modelList.add(parseLoanNotificationModel(not));
		}
		return modelList;
	}

}
