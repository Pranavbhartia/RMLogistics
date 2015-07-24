package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanMilestoneDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.enums.Milestones;



@Component
public class LoanMilestoneDaoImpl extends GenericDaoImpl implements LoanMilestoneDao
{

	@Override
    public LoanMilestone getLqbLoanStatus(Loan loan) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from LoanMilestone where loan= :loan and loanMilestoneMaster IN ( :loanMilestoneMaster) order by statusUpdateTime desc, order desc";
		Query qry = session.createQuery(hql);
		qry.setParameter("loan", loan);		
		qry.setParameterList("loanMilestoneMaster",Milestones.getMileStoneListForLoanStatus());
		List<LoanMilestone> loanStatusList=qry.list();	
		if(loanStatusList.size()>0){
			return loanStatusList.get(0);
		}
		return null;

	   
    }

}
