package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNotification;
import com.nexera.common.entity.User;

public interface NotificationDao extends GenericDao {

	List<LoanNotification> findActiveNotifications(Loan loan, User user);

}
