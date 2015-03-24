package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.User;

@Component
public class LoanAppFormDaoImpl extends GenericDaoImpl implements
		LoanAppFormDao {

	@Override
	public LoanAppForm findById(int appFormId) {
		// TODO Auto-generated method stub
		return null;
	}
	public LoanAppForm findByuserID(int userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		User user=new User();
		user.setId(userID);
		criteria.add(Restrictions.eq("user", user));
		LoanAppForm loalAppForm=(LoanAppForm) criteria.uniqueResult();
		return loalAppForm;
	}
	
	@Override
    public Integer saveLoanAppFormWithDetails(LoanAppForm loanAppForm) {
		if (null != loanAppForm.getGovernmentquestion()) {
			this.save(loanAppForm.getGovernmentquestion());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getPropertyTypeMaster()) {
			this.save(loanAppForm.getPropertyTypeMaster());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getRefinancedetails()) {
			this.save(loanAppForm.getRefinancedetails());
			sessionFactory.getCurrentSession().flush();
		}
 
		return (Integer) this.save(loanAppForm);
    }
	
	
	@Override
    public LoanAppForm findLoanAppForm(Integer loanAppFormID) {
		
		LoanAppForm loanAppForm = (LoanAppForm) this.load(LoanAppForm.class, loanAppFormID);
			if (loanAppForm != null) {
				Hibernate.initialize(loanAppForm.getGovernmentquestion());
				Hibernate.initialize(loanAppForm.getRefinancedetails());
				Hibernate.initialize(loanAppForm.getPropertyTypeMaster());
			}
			return loanAppForm;

	
	}
}
