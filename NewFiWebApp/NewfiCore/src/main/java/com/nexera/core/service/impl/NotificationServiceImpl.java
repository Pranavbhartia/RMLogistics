package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.commons.Utils;
import com.nexera.common.dao.NotificationDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.Notification;
import com.nexera.common.entity.User;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.NotificationVO;
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

	@Autowired
	private Utils utils;

	@Override
	@Transactional(readOnly = true)
	public List<NotificationVO> findActiveNotifications(LoanVO loanVO,
	        UserVO userVO) {

		List<NotificationVO> notList = buildNotificationVOList(notificationDao
		        .findActiveNotifications(loanService.parseLoanModel(loanVO),
		                User.convertFromVOToEntity(userVO)));

		return notList;
	}

	@Override
	@Transactional
	public NotificationVO createNotification(NotificationVO notificationVO) {

		Notification notification = parseNotificationModel(notificationVO);
		Integer id = (Integer) notificationDao.save(notification);
		notificationVO.setId(id);
		return notificationVO;

	}

	@Override
	@Transactional
	@Async
	public NotificationVO createNotificationAsync(NotificationVO notificationVO) {

		Notification notification = parseNotificationModel(notificationVO);
		Integer id = (Integer) notificationDao.save(notification);
		notificationVO.setId(id);
		return notificationVO;

	}

	@Override
	@Transactional
	public NotificationVO createRoleBasedNotification(
	        NotificationVO notificationVO, List<UserRolesEnum> userRoles,
	        List<InternalUserRolesEum> internalUserRoles) {

		Notification notification = parseNotificationModel(notificationVO);

		if (userRoles != null && userRoles.size() > 0) {

			StringBuilder userRolesVisible = new StringBuilder("");
			for (UserRolesEnum rolesEnum : userRoles) {
				userRolesVisible.append(rolesEnum.toString() + ",");
			}

			notification.setVisibleToUserRoles(userRolesVisible.toString());
		}

		if (internalUserRoles != null && internalUserRoles.size() > 0) {

			notification.setVisibleToUserRoles(UserRolesEnum.INTERNAL
			        .toString());
			StringBuilder internalUserRolesVisible = new StringBuilder("");
			for (InternalUserRolesEum rolesEnumInternal : internalUserRoles) {
				internalUserRolesVisible.append(rolesEnumInternal.toString()
				        + ",");
			}

			notification.setVisibleToInternalUserRoles(internalUserRolesVisible
			        .toString());
		}

		Integer id = (Integer) notificationDao.save(notification);
		notificationVO.setId(id);
		return notificationVO;

	}

	private NotificationVO buildNotificationVO(Notification notification) {
		if (notification == null)
			return null;

		NotificationVO vo = new NotificationVO();
		vo.setId(notification.getId());
		if (notification.getContent() != null)
			vo.setContent(new String(notification.getContent()));
		if (notification.getCreatedBy() != null)
			vo.setCreatedByID(notification.getCreatedBy().getId());
		if (notification.getCreatedFor() != null)
			vo.setCreatedForID(notification.getCreatedFor().getId());
		if (notification.getLoan() != null)
			vo.setLoanID(notification.getLoan().getId());

		if (notification.getCreatedDate() != null)
			vo.setCreatedDate(utils.getDateInUserLocale(
			        notification.getCreatedDate()).getTime());
		vo.setRead(notification.getRead());
		vo.setDismissable(notification.getDismissable());
		vo.setTitle(notification.getTitle());
		vo.setPriority(notification.getPriority());
		if (notification.getRemindOn() != null)
			vo.setRemindOn(utils
			        .getDateInUserLocale(notification.getRemindOn()).getTime());
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

	private Notification parseNotificationModel(NotificationVO loanNotification) {
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
			model.setCreatedFor(createdFor);
		}

		if (loanNotification.getLoanID() != null) {
			Loan loan = new Loan();
			loan.setId(loanNotification.getLoanID());
			model.setLoan(loan);
		}
		if (loanNotification.getRead() != null) {
			model.setRead(loanNotification.getRead());
		} else {
			model.setRead(false);
		}
		if (loanNotification.getDismissable() == null) {
			model.setDismissable(true);
		} else
			model.setDismissable(loanNotification.getDismissable());
		model.setTitle(loanNotification.getTitle());
		if (loanNotification.getPriority() == null) {
			model.setPriority("HIGH");
		} else
			model.setPriority(loanNotification.getPriority());
		if (loanNotification.getRemindOn() != null)
			model.setRemindOn(utils.getUserDateInGMT(new Date(loanNotification
			        .getRemindOn())));
		if (loanNotification.getCreatedDate() == null) {
			model.setCreatedDate(new Date());
		} else {
			model.setCreatedDate(utils.getUserDateInGMT(new Date(
			        loanNotification.getCreatedDate())));
		}
		if (loanNotification.getNotificationType() == null) {
			model.setNotificationType("Sample");
		} else
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nexera.core.service.NotificationService#dismissNotification(int)
	 */
	@Override
	public int dismissNotification(int notificationId) {
		int result = 0;
		Notification notification = new Notification();
		notification.setId(notificationId);
		notification.setRead(true);
		result = notificationDao.updateNotificationReadStatus(notification);
		return result;
	}

	@Override
	public NotificationVO updateNotification(NotificationVO notificationVO) {
		return buildNotificationVO(notificationDao
		        .updateNotification(parseNotificationModel(notificationVO)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.core.service.NotificationService#findNotificationTypeListForUser
	 * (com.nexera.common.vo.UserVO)
	 */
	@Override
	public List<NotificationVO> findNotificationTypeListForUser(int userId,
	        String type) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setId(userId);
		List<NotificationVO> notList = buildNotificationVOList(notificationDao
		        .findNotificationTypeListForUser(user, type));
		return notList;
	}

}
