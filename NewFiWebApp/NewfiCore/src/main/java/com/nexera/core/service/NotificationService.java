package com.nexera.core.service;

import java.util.List;

import com.nexera.common.vo.LoanNotificationVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserVO;

public interface NotificationService {

	List<LoanNotificationVO> findActiveNotifications(LoanVO loanVO,
			UserVO userVO);

}
