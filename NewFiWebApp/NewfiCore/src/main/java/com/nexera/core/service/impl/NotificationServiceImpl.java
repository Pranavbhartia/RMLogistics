package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.http11.NpnHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.NotificationDao;
import com.nexera.common.entity.Notification;
import com.nexera.common.entity.User;
import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NotificationService;
import com.nexera.core.service.UserProfileService;

@Component
@Transactional
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationDao notificationDao;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LoanService loanService;

	@Override
	@Transactional(readOnly = true)
	public List<NotificationVO> findActiveNotifications(LoanVO loanVO,
			UserVO userVO) {

		List<NotificationVO> notList = buildNotificationVOList(notificationDao
				.findActiveNotifications(loanService.parseLoanModel(loanVO),
						userProfileService.parseUserModel(userVO)));

		return notList;
	}

	private NotificationVO buildNotificationVO(
			Notification notification) {
		if (notification == null)
			return null;

		NotificationVO vo = new NotificationVO();
		vo.setId(notification.getId());
		vo.setContent(new String(notification.getContent()));
		if (notification.getCreatedBy() != null)
			vo.setCreatedByID(notification.getCreatedBy().getId());
		if (notification.getCreatedFor() != null)
			vo.setCreatedForID(notification.getCreatedFor().getId());
		if (notification.getLoan() != null)
			vo.setLoanID(notification.getLoan().getId());
		
		vo.setRead(notification.getRead());
		vo.setDismissable(notification.getDismissable());
		vo.setTitle(notification.getTitle());
		vo.setPriority(notification.getPriority());
		vo.setRemindOn(notification.getRemindOn());
		vo.setNotificationType(notification.getNotificationType());

		
		return vo;
	}

	private List<NotificationVO> buildNotificationVOList(
			List<Notification> loanNotifications) {

		if (loanNotifications == null)
			return null;

		List<NotificationVO> voList = new ArrayList<NotificationVO>();

		for (Notification not : loanNotifications) {
			voList.add(buildNotificationVO(not));
		}
		return voList;
	}

	private Notification parseNotificationModel(
			NotificationVO loanNotification) {
		if (loanNotification == null)
			return null;

		Notification model = new Notification();
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
		

		model.setRead(loanNotification.getRead());
		model.setDismissable(loanNotification.getDismissable());
		model.setTitle(loanNotification.getTitle());
		model.setPriority(loanNotification.getPriority());
		model.setRemindOn(loanNotification.getRemindOn());
		model.setNotificationType(loanNotification.getNotificationType());

		return model;
	}

	private List<Notification> parseNotificationModelList(
			List<NotificationVO> loanNotifications) {

		if (loanNotifications == null)
			return null;

		List<Notification> modelList = new ArrayList<Notification>();

		for (NotificationVO not : loanNotifications) {
			modelList.add(parseNotificationModel(not));
		}
		return modelList;
	}

	/* (non-Javadoc)
	 * @see com.nexera.core.service.NotificationService#dismissNotification(int)
	 */
	@Override
	public int dismissNotification(int notificationId) {
		int result=0;
		Notification notification=new Notification();
		notification.setId(notificationId);
		notification.setRead(true);
		result= notificationDao.updateNotificationReadStatus(notification);
		return result;
	}

}
