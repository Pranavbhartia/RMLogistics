package com.nexera.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.NotificationDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.Notification;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.UserRolesEnum;

@Component
public class NotificationDaoImpl extends GenericDaoImpl implements
		NotificationDao {

	@Autowired
	private UserProfileDao userProfileDao;

	@Override
	public List<Notification> findActiveNotifications(Loan loan, User user) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Notification.class);
		if (user.getUserRole() == null)
			user = userProfileDao.findInternalUser(user.getId());

		Criterion loanRest = Restrictions.eq("loan", loan);
		Criterion userRest = Restrictions.eq("createdFor", user);

		if (loan != null) {
			criteria.add(loanRest);

		}

		if (user != null) {

			if (user.getUserRole() != null) {
				UserRole role = user.getUserRole();
				if (role != null) {

					UserRolesEnum roleEnum = UserRolesEnum.valueOf(role
							.getRoleCd());
					switch (roleEnum) {
					case CUSTOMER:
						criteria.add(userRest);
						break;

					case REALTOR:
						criteria.add(Restrictions.or(userRest, Restrictions
								.and(Restrictions.isNull("createdFor"),
										Restrictions
												.ilike("visibleToUserRoles",
														UserRolesEnum.REALTOR
																.toString()))));
						break;

					case INTERNAL:
						criteria.add(Restrictions.or(
								userRest,
								Restrictions.and(
										Restrictions.isNull("createdFor"),
										Restrictions.and(
												Restrictions
														.ilike("visibleToUserRoles",
																UserRolesEnum.INTERNAL
																		.toString()),
												Restrictions.or(
														Restrictions
																.isNull("visibleToInternalUserRoles"),
														Restrictions
																.ilike("visibleToInternalUserRoles",
																		user.getInternalUserDetail()
																				.getInternaUserRoleMaster()
																				.getRoleName()))))));
						break;

					default:
						criteria.add(userRest);
						break;
					}

				}
			}else 
				criteria.add(userRest);
		}

		criteria.add(Restrictions.eq("read", false));

		Criterion remindOnIsNull = Restrictions.isNull("remindOn");
		Criterion remindOnIsNotNull = Restrictions.isNotNull("remindOn");
		Criterion remindDateReached = Restrictions.ge("remindOn", new Date());

		Criterion reminder = Restrictions.or(remindOnIsNull,
				Restrictions.and(remindOnIsNotNull, remindDateReached));

		criteria.add(reminder);

		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see com.nexera.common.dao.NotificationDao#updateNotificationReadStatus(com.nexera.common.entity.Notification)
	 */
	@Override
	public int updateNotificationReadStatus(Notification notification) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Notification set read=:read where id=:id";
		Query query = (Query) session.createQuery(hql);
		query.setParameter("read", true);
		query.setParameter("id", notification.getId());
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
		return result;
		
	}
}
