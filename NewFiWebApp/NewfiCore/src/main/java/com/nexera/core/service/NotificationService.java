package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;

public interface NotificationService {

	NotificationVO createNotification(NotificationVO notificationVO);

	List<NotificationVO> findActiveNotifications(LoanVO loanVO, UserVO userVO);

	int dismissNotification(int notificationId);

	NotificationVO updateNotification(NotificationVO notificationVO);
}
