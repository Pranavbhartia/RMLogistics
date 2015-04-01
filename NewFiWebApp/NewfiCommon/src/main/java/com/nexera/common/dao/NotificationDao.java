package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.Notification;
import com.nexera.common.entity.User;

public interface NotificationDao extends GenericDao {

	List<Notification> findActiveNotifications(Loan loan, User user);
	
	int updateNotificationReadStatus(Notification notification);

	Notification updateNotification(Notification notificationModel);
	
	List<Notification> findNotificationTypeListForUser(User user,String type);

	List<Notification> findNotificationTypeListForLoan(Loan loan, String type,
			Boolean isRead);
}
