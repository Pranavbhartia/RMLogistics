package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.dao.NotificationDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.Notification;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;

@Component
public class NotificationDaoImpl extends GenericDaoImpl implements
        NotificationDao {

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private Utils utils;

	@Override
	public List<Notification> findActiveNotifications(Loan loan, User user) {

		if (user == null)
			return null;

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Notification.class);

		user = userProfileDao.findInternalUser(user.getId());

		Criterion loanRest = Restrictions.eq("loan", loan);
		Criterion userRest = Restrictions.eq("createdFor", user);
		// If no roles are assinged, notification to be displayed to all
		Criterion noRolesAssigned = Restrictions.and(
		        Restrictions.isNull("createdFor"),
		        Restrictions.isNull("visibleToUserRoles"));
		if (loan != null) {
			criteria.add(loanRest);

		}
		Criterion userRoleBased = null;
		if (user != null) {

			if (user.getUserRole() != null) {
				UserRole role = user.getUserRole();
				if (role != null) {

					UserRolesEnum roleEnum = UserRolesEnum.valueOf(role
					        .getRoleCd());
					switch (roleEnum) {
					case CUSTOMER:
						userRoleBased = userRest;
						break;

					case REALTOR:
						userRoleBased = Restrictions.or(userRest, Restrictions
						        .and(Restrictions.isNull("createdFor"),
						                Restrictions.ilike(
						                        "visibleToUserRoles",
						                        "%"
						                                + UserRolesEnum.REALTOR
						                                        .toString()
						                                + "%")));
						break;

					case INTERNAL:
						userRoleBased = Restrictions
						        .or(userRest,
						                Restrictions.and(
						                        Restrictions
						                                .isNull("createdFor"),
						                        Restrictions.and(
						                                Restrictions
						                                        .ilike("visibleToUserRoles",
						                                                "%"
						                                                        + UserRolesEnum.INTERNAL
						                                                                .toString()
						                                                        + "%"),
						                                Restrictions.or(
						                                        Restrictions
						                                                .isNull("visibleToInternalUserRoles"),
						                                        Restrictions
						                                                .ilike("visibleToInternalUserRoles",
						                                                        "%"
						                                                                + user.getInternalUserDetail()
						                                                                        .getInternaUserRoleMaster()
						                                                                        .getRoleName()
						                                                                + "%")))));
						break;

					default:
						userRoleBased = userRest;
						break;
					}

				}

			} else
				userRoleBased = userRest;
			
			if (loan == null) {
				List<Loan> loanList = getLoanListForUser(user);
				criteria.add(Restrictions.in("loan", loanList));
			}
			
		}
		if (userRoleBased != null)
			criteria.add(Restrictions.or(noRolesAssigned, userRoleBased));
		else {
			criteria.add(noRolesAssigned);
		}
		// Fetch only unread notifications
		criteria.add(Restrictions.eq("read", false));

		Criterion remindOnIsNull = Restrictions.isNull("remindOn");
		Criterion remindOnIsNotNull = Restrictions.isNotNull("remindOn");
		Criterion remindDateReached = Restrictions.le("remindOn",
		        utils.getDateInUserLocale(new Date()));

		Criterion reminder = Restrictions.or(remindOnIsNull,
		        Restrictions.and(remindOnIsNotNull, remindDateReached));

		criteria.addOrder(Order.desc("remindOn"));
		criteria.addOrder(Order.desc("createdDate"));

		criteria.add(reminder);

		List<Notification> notifications = criteria.list();

		return notifications;
	}

	private List<Loan> getLoanListForUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanTeam.class);
		criteria.add(Restrictions.eq("user", user));
		List<LoanTeam> team = (List<LoanTeam>) criteria.list();
		List<Loan> loanList = new ArrayList<Loan>();
		for (LoanTeam loanTeam : team) {
			Loan loan = loanTeam.getLoan();
			loanList.add(loan);
		}
		return loanList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.common.dao.NotificationDao#updateNotificationReadStatus(com
	 * .nexera.common.entity.Notification)
	 */
	@Override
	public int updateNotificationReadStatus(Notification notification) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Notification set read=:read where id=:id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("read", true);
		query.setParameter("id", notification.getId());
		int result = query.executeUpdate();
		return result;

	}

	@Override
	public Notification updateNotification(Notification notificationModel) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Notification set read=:read, remindOn=:remindOn where id=:id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("read", notificationModel.getRead());
		query.setParameter("remindOn", notificationModel.getRemindOn());
		query.setParameter("id", notificationModel.getId());
		Integer result = query.executeUpdate();
		if (result != null && result > 0)
			return notificationModel;
		else
			return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nexera.common.dao.NotificationDao#fincNotificationTypeListForUser
	 * (com.nexera.common.entity.User)
	 */
	@Override
	public List<Notification> findNotificationTypeListForUser(User user,
	        String type) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Notification.class);
			criteria.add(Restrictions.eq("createdFor", user));
			criteria.add(Restrictions.eq("notificationType", type));
			criteria.add(Restrictions.or(Restrictions.eq("read", false),
			        Restrictions.isNull("read")));
			List<Notification> notifications = (List<Notification>) criteria
			        .list();
			return notifications;
		} catch (HibernateException hibernateException) {
			// LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			// hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
	}

	@Override
	public List<Notification> findNotificationTypeListForLoan(Loan loan,
	        String type, Boolean isRead) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Notification.class);
			criteria.add(Restrictions.eq("loan", loan));
			criteria.add(Restrictions.eq("notificationType", type));
			if (isRead != null)
				criteria.add(Restrictions.or(Restrictions.eq("read", false),
				        Restrictions.isNull("read")));
			List<Notification> notifications = (List<Notification>) criteria
			        .list();
			return notifications;
		} catch (HibernateException hibernateException) {
			// LOG.error("Exception caught in fetchUsersBySimilarEmailId() ",
			// hibernateException);
			throw new DatabaseException(
			        "Exception caught in fetchUsersBySimilarEmailId() ",
			        hibernateException);
		}
	}
}
