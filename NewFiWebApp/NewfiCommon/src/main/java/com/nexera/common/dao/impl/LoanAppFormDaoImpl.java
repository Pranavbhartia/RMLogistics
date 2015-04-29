package com.nexera.common.dao.impl;

import java.util.Iterator;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanAppFormDao;
import com.nexera.common.entity.CustomerBankAccountDetails;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.CustomerEmploymentIncome;
import com.nexera.common.entity.CustomerOtherAccountDetails;
import com.nexera.common.entity.CustomerRetirementAccountDetails;
import com.nexera.common.entity.CustomerSpouseBankAccountDetails;
import com.nexera.common.entity.CustomerSpouseEmploymentIncome;
import com.nexera.common.entity.CustomerSpouseOtherAccountDetails;
import com.nexera.common.entity.CustomerSpouseRetirementAccountDetails;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.entity.User;

@Component
public class LoanAppFormDaoImpl extends GenericDaoImpl implements
        LoanAppFormDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(LoanAppFormDaoImpl.class);

	@Override
	public LoanAppForm findById(int appFormId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public LoanAppForm findByuserID(int userID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		User user = new User();
		user.setId(userID);
		criteria.add(Restrictions.eq("user", user));
		LoanAppForm loalAppForm = (LoanAppForm) criteria.uniqueResult();
		return loalAppForm;
	}

	@Override
    public LoanAppForm findByLoan(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanAppForm.class);
		criteria.add(Restrictions.eq("loan", loan));
		LoanAppForm loalAppForm = (LoanAppForm) criteria.uniqueResult();
		return loalAppForm;
	}

	@Override
	public LoanAppForm saveLoanAppFormWithDetails(LoanAppForm loanAppForm) {

		if (null != loanAppForm.getUser()) {

			if (null != loanAppForm.getUser().getCustomerDetail()) {

				LOG.info("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail()"
				                + loanAppForm.getUser().getCustomerDetail()
				                        .getId());
				LOG.info("loanAppForm.getUser().getCustomerDetail().getAddressCity()"
				                + loanAppForm.getUser().getCustomerDetail()
				                        .getAddressCity());

				/*
				 * if(!"NONE".equalsIgnoreCase(loanAppForm.getUser().
				 * getCustomerDetail().getAddressCity()) ) {
				 */

				this.saveOrUpdate(loanAppForm.getUser().getCustomerDetail());

				// }
				LOG.info("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail()"
				                + loanAppForm.getUser().getCustomerDetail()
				                        .getId());
				sessionFactory.getCurrentSession().flush();

			}
			LOG.info("Before saveOrUpdate(loanAppForm.getUser()"
			        + loanAppForm.getUser().getId());
			// this.saveOrUpdate(loanAppForm.getUser());
			LOG.info("After saveOrUpdate(loanAppForm.getUser()"
			        + loanAppForm.getUser().getId());
			// sessionFactory.getCurrentSession().flush();
		}
		
		
		LOG.info("Before saveOrUpdate(loanAppForm.loanAppForm.getLoan()"+loanAppForm.getLoan());
		if(loanAppForm.getLoan() != null){
			
			LOG.info("Before saveOrUpdate(loanAppForm.loanAppForm.getLoan().getLqbFileId()"+loanAppForm.getLoan().getLqbFileId());
			
		}
		
		if (null != loanAppForm.getLoan() && null != loanAppForm.getLoan().getLqbFileId() ) {
			
			LOG.info("Before saveOrUpdate(loanAppForm.loanAppForm.getLoan().getLqbFileId()"
	                + loanAppForm.getLoan().getLqbFileId());
			LOG.info("Before saveOrUpdate(loanAppForm.loanAppForm.getLoan().getId()"
	                + loanAppForm.getLoan().getId());
			
			
			try {

		
			this.update(loanAppForm.getLoan());
		
	} catch (Exception e) {
				LOG.error("Exception caught " + e.getMessage());
	}
			LOG.info("After saveOrUpdate(loanAppForm.loanAppForm.getLoan().getId()"
	                + loanAppForm.getLoan().getId());

	sessionFactory.getCurrentSession().flush();
			
			
		
		}
		

		if (null != loanAppForm.getCustomerspousedetail()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerspousedetail"
			                + loanAppForm.getCustomerspousedetail().getId());
			try {

				if (loanAppForm.getCustomerspousedetail().getId() == 0) {
					this.save(loanAppForm.getCustomerspousedetail());
				} else {
					this.saveOrUpdate(loanAppForm.getCustomerspousedetail());
				}
			} catch (Exception e) {
				LOG.error("Exception caught " + e.getMessage());
			}
			LOG.info("After saveOrUpdate(loanAppForm.getCustomerspousedetail"
			                + loanAppForm.getCustomerspousedetail().getId());

			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getGovernmentquestion()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getGovernmentquestion()"
			                + loanAppForm.getGovernmentquestion().getId());
			this.saveOrUpdate(loanAppForm.getGovernmentquestion());
			LOG.info("After saveOrUpdate(loanAppForm.getGovernmentquestion()"
			                + loanAppForm.getGovernmentquestion().getId());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getSpouseGovernmentQuestions()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getSpouseGovernmentQuestions()"
			                + loanAppForm.getSpouseGovernmentQuestions()
			                        .getId());
			this.saveOrUpdate(loanAppForm.getSpouseGovernmentQuestions());
			LOG.info("After saveOrUpdate(loanAppForm.getSpouseGovernmentQuestions()"
			                + loanAppForm.getSpouseGovernmentQuestions()
			                        .getId());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getPropertyTypeMaster()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getPropertyTypeMaster()"
			                + loanAppForm.getPropertyTypeMaster().getId());
			this.saveOrUpdate(loanAppForm.getPropertyTypeMaster());
			LOG.info("After saveOrUpdate(loanAppForm.getPropertyTypeMaster()"
			                + loanAppForm.getPropertyTypeMaster().getId());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getRefinancedetails()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getRefinancedetails()"
			                + loanAppForm.getRefinancedetails().getId());
			this.saveOrUpdate(loanAppForm.getRefinancedetails());
			LOG.info("After saveOrUpdate(loanAppForm.getRefinancedetails()"
			                + loanAppForm.getRefinancedetails().getId());
			sessionFactory.getCurrentSession().flush();
		}
		if (null != loanAppForm.getPurchaseDetails()) {
			LOG.info("Before saveOrUpdate(loanAppForm.getPurchaseDetails()"
			                + loanAppForm.getPurchaseDetails().getId());
			this.saveOrUpdate(loanAppForm.getPurchaseDetails());
			LOG.info("After saveOrUpdate(loanAppForm.getPurchaseDetails()"
			                + loanAppForm.getPurchaseDetails().getId());
			sessionFactory.getCurrentSession().flush();
		}

		this.saveOrUpdate(loanAppForm);

		
		//Customer EmploymentIncome : Start
		
		if (null != loanAppForm.getCustomerEmploymentIncome() && loanAppForm.getCustomerEmploymentIncome().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()"
			                + loanAppForm.getCustomerEmploymentIncome());

			LOG.info("loanAppForm.getCustomerEmploymentIncome().get(0).getEmployedAt()" + loanAppForm.getCustomerEmploymentIncome().get(0).getEmployedAt());
			
			Iterator<CustomerEmploymentIncome> itr = loanAppForm.getCustomerEmploymentIncome().iterator();
            int counter = 0 ;
			while (itr.hasNext()) {
				CustomerEmploymentIncome cei = itr.next();
				LOG.info("cei.getEmployedAt()" + cei.getEmployedAt());
				LOG.info("cei.getId()" + cei.getId());
				CustomerEmploymentIncome customeremploymentIncome = new CustomerEmploymentIncome();
				customeremploymentIncome.setId(cei.getId());
				customeremploymentIncome.setJobTitle(cei.getJobTitle());
				customeremploymentIncome.setEmployedAt(cei.getEmployedAt());
				customeremploymentIncome.setEmployedIncomePreTax(cei.getEmployedIncomePreTax());
				customeremploymentIncome.setEmployedSince(cei.getEmployedSince());
				customeremploymentIncome.setLoanAppForms(loanAppForm);
				
				this.saveOrUpdate(customeremploymentIncome);
				
				LOG.info("custom engagement id is "+ customeremploymentIncome.getId());
				LOG.info("custom engagement id is "+counter+":::"+ loanAppForm.getCustomerEmploymentIncome().get(counter).getId());
				loanAppForm.getCustomerEmploymentIncome().get(counter).setId(customeremploymentIncome.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()"
			                + loanAppForm.getCustomerEmploymentIncome());
			sessionFactory.getCurrentSession().flush();
		}

		//Customer EmploymentIncome : Ends
		
		
		if (null != loanAppForm.getCustomerSpouseEmploymentIncome() && loanAppForm.getCustomerSpouseEmploymentIncome().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerSpouseEmploymentIncome()"
			                + loanAppForm.getCustomerSpouseEmploymentIncome());
			Iterator<CustomerSpouseEmploymentIncome> itr = loanAppForm
			        .getCustomerSpouseEmploymentIncome().iterator();

			
			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerSpouseEmploymentIncome cei = itr.next();
				LOG.info("cei.getEmployedAt()" + cei.getEmployedAt());
				LOG.info("cei.cei.getId()()" + cei.getId());
				CustomerSpouseEmploymentIncome customerSpouseEmploymentIncome = new CustomerSpouseEmploymentIncome();
				customerSpouseEmploymentIncome.setId(cei.getId());
				customerSpouseEmploymentIncome.setJobTitle(cei.getJobTitle());
				customerSpouseEmploymentIncome.setEmployedAt(cei
				        .getEmployedAt());
				customerSpouseEmploymentIncome.setEmployedIncomePreTax(cei
				        .getEmployedIncomePreTax());
				customerSpouseEmploymentIncome.setEmployedSince(cei
				        .getEmployedSince());
				customerSpouseEmploymentIncome.setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerSpouseEmploymentIncome);
				
				LOG.info("custom spouse engagement id is "
				        + customerSpouseEmploymentIncome.getId());
				LOG.info("custom spouse engagement id is "+counter+":::"
				        + loanAppForm.getCustomerSpouseEmploymentIncome().get(counter)
				                .getId());
				loanAppForm.getCustomerSpouseEmploymentIncome().get(counter)
				        .setId(customerSpouseEmploymentIncome.getId());
				counter++;
				
			}

			LOG.info("After saveOrUpdate(loanAppForm.getUser().getCustomerDetail().getCustomerEmploymentIncome()"
			                + loanAppForm.getCustomerSpouseEmploymentIncome());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerBankAccountDetails() && loanAppForm.getCustomerBankAccountDetails().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerBankAccountDetails()"
			                + loanAppForm.getCustomerBankAccountDetails());
			Iterator<CustomerBankAccountDetails> itr = loanAppForm
			        .getCustomerBankAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerBankAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()"
				        + cei.getAccountSubType());
				CustomerBankAccountDetails customerBankAccountDetails = new CustomerBankAccountDetails();
				
				customerBankAccountDetails.setId(cei.getId());
				customerBankAccountDetails.setAccountSubType(cei.getAccountSubType());
				customerBankAccountDetails.setCurrentaccountbalance(cei.getCurrentaccountbalance());
				customerBankAccountDetails.setAmountfornewhome(cei.getAmountfornewhome());
				customerBankAccountDetails.setLoanAppForms(loanAppForm);
				
				this.saveOrUpdate(customerBankAccountDetails);
				
				loanAppForm.getCustomerBankAccountDetails().get(counter).setId(customerBankAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerBankAccountDetails()"
			                + loanAppForm.getCustomerBankAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerRetirementAccountDetails() && loanAppForm.getCustomerRetirementAccountDetails().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerRetirementAccountDetails()"
			                + loanAppForm.getCustomerRetirementAccountDetails());
			Iterator<CustomerRetirementAccountDetails> itr = loanAppForm
			        .getCustomerRetirementAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerRetirementAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()" + cei.getAccountSubType());
				CustomerRetirementAccountDetails customerRetirementAccountDetails = new CustomerRetirementAccountDetails();
				
				customerRetirementAccountDetails.setId(cei.getId());
				customerRetirementAccountDetails.setAccountSubType(cei.getAccountSubType());
				customerRetirementAccountDetails.setCurrentaccountbalance(cei.getCurrentaccountbalance());
				customerRetirementAccountDetails.setAmountfornewhome(cei.getAmountfornewhome());
				customerRetirementAccountDetails.setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerRetirementAccountDetails);
				
				loanAppForm.getCustomerRetirementAccountDetails().get(counter).setId(customerRetirementAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerRetirementAccountDetails()"
			                + loanAppForm.getCustomerRetirementAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerOtherAccountDetails() && loanAppForm.getCustomerOtherAccountDetails().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerOtherAccountDetails()"
			                + loanAppForm.getCustomerOtherAccountDetails());
			Iterator<CustomerOtherAccountDetails> itr = loanAppForm
			        .getCustomerOtherAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerOtherAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()"+ cei.getAccountSubType());
				CustomerOtherAccountDetails customerOtherAccountDetails = new CustomerOtherAccountDetails();
				
				customerOtherAccountDetails.setId(cei.getId());
				customerOtherAccountDetails.setAccountSubType(cei.getAccountSubType());
				customerOtherAccountDetails.setCurrentaccountbalance(cei.getCurrentaccountbalance());
				customerOtherAccountDetails.setAmountfornewhome(cei.getAmountfornewhome());
				customerOtherAccountDetails.setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerOtherAccountDetails);
				
				loanAppForm.getCustomerOtherAccountDetails().get(counter).setId(customerOtherAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerOtherAccountDetails()"
			                + loanAppForm.getCustomerOtherAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerSpouseBankAccountDetails() && loanAppForm.getCustomerSpouseBankAccountDetails().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerSpouseBankAccountDetails()"
			                + loanAppForm.getCustomerSpouseBankAccountDetails());
			Iterator<CustomerSpouseBankAccountDetails> itr = loanAppForm
			        .getCustomerSpouseBankAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerSpouseBankAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()"
				        + cei.getAccountSubType());
				CustomerSpouseBankAccountDetails customerSpouseBankAccountDetails = new CustomerSpouseBankAccountDetails();
				
				customerSpouseBankAccountDetails.setId(cei.getId());
				customerSpouseBankAccountDetails.setAccountSubType(cei.getAccountSubType());
				customerSpouseBankAccountDetails.setCurrentaccountbalance(cei.getCurrentaccountbalance());
				customerSpouseBankAccountDetails.setAmountfornewhome(cei.getAmountfornewhome());
				customerSpouseBankAccountDetails.setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerSpouseBankAccountDetails);
				
				loanAppForm.getCustomerSpouseBankAccountDetails().get(counter).setId(customerSpouseBankAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerSpouseBankAccountDetails()"
			                + loanAppForm.getCustomerSpouseBankAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerSpouseRetirementAccountDetails() && loanAppForm.getCustomerSpouseRetirementAccountDetails().size() > 0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerSpouseRetirementAccountDetails()"
			                + loanAppForm
			                        .getCustomerSpouseRetirementAccountDetails());
			Iterator<CustomerSpouseRetirementAccountDetails> itr = loanAppForm
			        .getCustomerSpouseRetirementAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerSpouseRetirementAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()"
				        + cei.getAccountSubType());
				CustomerSpouseRetirementAccountDetails customerSpouseRetirementAccountDetails = new CustomerSpouseRetirementAccountDetails();
				
				customerSpouseRetirementAccountDetails.setId(cei.getId());
				customerSpouseRetirementAccountDetails.setAccountSubType(cei.getAccountSubType());
				customerSpouseRetirementAccountDetails
				        .setCurrentaccountbalance(cei
				                .getCurrentaccountbalance());
				customerSpouseRetirementAccountDetails.setAmountfornewhome(cei
				        .getAmountfornewhome());
				customerSpouseRetirementAccountDetails
				        .setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerSpouseRetirementAccountDetails);
				
				loanAppForm.getCustomerSpouseRetirementAccountDetails().get(counter).setId(customerSpouseRetirementAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerSpouseRetirementAccountDetails()"
			                + loanAppForm
			                        .getCustomerSpouseRetirementAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		if (null != loanAppForm.getCustomerSpouseOtherAccountDetails() && loanAppForm.getCustomerSpouseOtherAccountDetails().size() >0) {
			LOG.info("Before saveOrUpdate(loanAppForm.getCustomerSpouseOtherAccountDetails()"
			                + loanAppForm
			                        .getCustomerSpouseOtherAccountDetails());
			Iterator<CustomerSpouseOtherAccountDetails> itr = loanAppForm
			        .getCustomerSpouseOtherAccountDetails().iterator();

			int counter = 0 ;
			while (itr.hasNext()) {
				CustomerSpouseOtherAccountDetails cei = itr.next();
				LOG.info("cei.getAccountSubType()"
				        + cei.getAccountSubType());
				CustomerSpouseOtherAccountDetails customerSpouseOtherAccountDetails = new CustomerSpouseOtherAccountDetails();
				
				customerSpouseOtherAccountDetails.setId(cei.getId());
				customerSpouseOtherAccountDetails.setAccountSubType(cei
				        .getAccountSubType());
				customerSpouseOtherAccountDetails.setCurrentaccountbalance(cei
				        .getCurrentaccountbalance());
				customerSpouseOtherAccountDetails.setAmountfornewhome(cei
				        .getAmountfornewhome());
				customerSpouseOtherAccountDetails.setLoanAppForms(loanAppForm);
				this.saveOrUpdate(customerSpouseOtherAccountDetails);
				
				loanAppForm.getCustomerSpouseOtherAccountDetails().get(counter).setId(customerSpouseOtherAccountDetails.getId());
				counter++;
			}

			LOG.info("After saveOrUpdate(loanAppForm.getCustomerOtherAccountDetails()"
			                + loanAppForm
			                        .getCustomerSpouseOtherAccountDetails());
			sessionFactory.getCurrentSession().flush();
		}

		return loanAppForm;
	}

	@Override
	public LoanAppForm findLoanAppForm(Integer loanAppFormID) {

		LoanAppForm loanAppForm = (LoanAppForm) this.load(LoanAppForm.class,
		        loanAppFormID);
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
		User user = loaAppForm.getUser();
		Loan loan = loaAppForm.getLoan();

		criteria.add(Restrictions.eq("user", user));
		criteria.add(Restrictions.eq("loan", loan));
		LoanAppForm loanAppForm = (LoanAppForm) criteria.uniqueResult();

		Hibernate.initialize(loanAppForm.getGovernmentquestion());
		Hibernate.initialize(loanAppForm.getRefinancedetails());
		Hibernate.initialize(loanAppForm.getPropertyTypeMaster());
		Hibernate.initialize(loanAppForm.getPurchaseDetails());
		Hibernate.initialize(loanAppForm.getSpouseGovernmentQuestions());
		Hibernate.initialize(loanAppForm.getLoan());
		Hibernate.initialize(loanAppForm.getUser());
		User temp = loanAppForm.getUser();

		Hibernate.initialize(loanAppForm.getUser().getCustomerDetail());
		CustomerDetail cdTemp = loanAppForm.getUser().getCustomerDetail();
		temp.setCustomerDetail(cdTemp);
		loanAppForm.setUser(temp);
		Hibernate.initialize(loanAppForm.getLoanTypeMaster());

		Hibernate.initialize(loanAppForm.getCustomerspousedetail());
		Hibernate.initialize(loanAppForm.getCustomerSpouseEmploymentIncome());
		Hibernate.initialize(loanAppForm.getCustomerSpouseBankAccountDetails());
		Hibernate.initialize(loanAppForm.getCustomerSpouseOtherAccountDetails());
		Hibernate.initialize(loanAppForm.getCustomerSpouseRetirementAccountDetails());
		
		Hibernate.initialize(loanAppForm.getCustomerEmploymentIncome());
		Hibernate.initialize(loanAppForm.getCustomerBankAccountDetails());

		Hibernate.initialize(loanAppForm.getCustomerOtherAccountDetails());
		Hibernate.initialize(loanAppForm.getCustomerRetirementAccountDetails());

		if (loanAppForm.getUser().getCustomerDetail() != null) {
			Hibernate.initialize(loanAppForm.getUser().getCustomerDetail()
			        .getCustomerRetirementAccountDetails());
			Hibernate.initialize(loanAppForm.getUser().getCustomerDetail()
			        .getCustomerOtherAccountDetails());
		}


		return loanAppForm;
	}
	
	
	
}
