package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.Loan;
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
	public LoanAppForm findByLoan(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		criteria.add(Restrictions.eq("loan", loan));
		LoanAppForm loalAppForm=(LoanAppForm) criteria.uniqueResult();
		return loalAppForm;
	}
	@Override
    public LoanAppForm saveLoanAppFormWithDetails(LoanAppForm loanAppForm) {
		
		
		
		if (null != loanAppForm.getUser()) {
			
			if (null != loanAppForm.getUser().getCustomerDetail()){
				
				if (null != loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail()) {
					System.out.println("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail()"+loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail());
					this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail());
					System.out.println("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail()"+loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail());
					sessionFactory.getCurrentSession().flush();
				}
				if (null != loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()) {
					System.out.println("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()"+loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome());
					this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome());
					System.out.println("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()"+loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome());
					sessionFactory.getCurrentSession().flush();
				}
				if (null != loanAppForm.getUser().getCustomerDetail().getCustomerBankAccountDetails()) {
					this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerBankAccountDetails());
					sessionFactory.getCurrentSession().flush();
				}
				if (null != loanAppForm.getUser().getCustomerDetail().getCustomerRetirementAccountDetails()) {
					this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerOtherAccountDetails());
					sessionFactory.getCurrentSession().flush();
				}
				if (null != loanAppForm.getUser().getCustomerDetail().getCustomerBankAccountDetails()) {
					this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerBankAccountDetails());
					sessionFactory.getCurrentSession().flush();
				}
				
				
				System.out.println("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail()"+loanAppForm.getUser().getCustomerDetail().getId());
				this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail());
				System.out.println("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail()"+loanAppForm.getUser().getCustomerDetail().getId());
				sessionFactory.getCurrentSession().flush();
				
				
			}
			System.out.println("Before saveOrUpdate(loanAppForm.getUser()"+loanAppForm.getUser().getId());
			//this.saveOrUpdate(loanAppForm.getUser());
			System.out.println("After saveOrUpdate(loanAppForm.getUser()"+loanAppForm.getUser().getId());
			//sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getGovernmentquestion()) {
			System.out.println("Before saveOrUpdate(loanAppForm.getGovernmentquestion()"+loanAppForm.getGovernmentquestion().getId());
			this.saveOrUpdate(loanAppForm.getGovernmentquestion());
			System.out.println("After saveOrUpdate(loanAppForm.getGovernmentquestion()"+loanAppForm.getGovernmentquestion().getId());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getPropertyTypeMaster()) {
			System.out.println("Before saveOrUpdate(loanAppForm.getPropertyTypeMaster()"+loanAppForm.getPropertyTypeMaster().getId());
			this.saveOrUpdate(loanAppForm.getPropertyTypeMaster());
			System.out.println("After saveOrUpdate(loanAppForm.getPropertyTypeMaster()"+loanAppForm.getPropertyTypeMaster().getId());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getRefinancedetails()) {
			System.out.println("Before saveOrUpdate(loanAppForm.getRefinancedetails()"+loanAppForm.getRefinancedetails().getId());
			this.saveOrUpdate(loanAppForm.getRefinancedetails());
			System.out.println("After saveOrUpdate(loanAppForm.getRefinancedetails()"+loanAppForm.getRefinancedetails().getId());
			sessionFactory.getCurrentSession().flush();
		}if (null != loanAppForm.getPurchaseDetails()) {
			System.out.println("Before saveOrUpdate(loanAppForm.getPurchaseDetails()"+loanAppForm.getPurchaseDetails().getId());
			this.saveOrUpdate(loanAppForm.getPurchaseDetails());
			System.out.println("After saveOrUpdate(loanAppForm.getPurchaseDetails()"+loanAppForm.getPurchaseDetails().getId());
			sessionFactory.getCurrentSession().flush();
		}
		
 
		this.saveOrUpdate(loanAppForm);
		
		
		
		return  loanAppForm;
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
	@Override
    public LoanAppForm find(LoanAppForm loaAppForm) {
	  
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		User user= loaAppForm.getUser();
		Loan loan = loaAppForm.getLoan();
		
		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("loan", loan));
		LoanAppForm loanAppForm=(LoanAppForm) criteria.uniqueResult();
		
		
		Hibernate.initialize(loanAppForm.getGovernmentquestion());
		Hibernate.initialize(loanAppForm.getRefinancedetails());
		Hibernate.initialize(loanAppForm.getPropertyTypeMaster());
		Hibernate.initialize(loanAppForm.getPurchaseDetails());
		
		Hibernate.initialize(loanAppForm.getLoan());
		Hibernate.initialize(loanAppForm.getUser());
		User temp = loanAppForm.getUser();
		
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail());
		CustomerDetail cdTemp = loanAppForm.getUser().getCustomerDetail();
		temp.setCustomerDetail(cdTemp);
		loanAppForm.setUser(temp);
		Hibernate.initialize(loanAppForm.getLoanTypeMaster());
		
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail().getCustomerSpouseDetail());
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome());
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail().getCustomerBankAccountDetails());
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail().getCustomerRetirementAccountDetails());
		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail().getCustomerOtherAccountDetails());
		
		return loanAppForm;
    }
}
