package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.NotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;

public interface NotificationService {

	List<NotificationVO> findActiveNotifications(LoanVO loanVO,
			UserVO userVO);
	int dismissNotification(int notificationId);
}
