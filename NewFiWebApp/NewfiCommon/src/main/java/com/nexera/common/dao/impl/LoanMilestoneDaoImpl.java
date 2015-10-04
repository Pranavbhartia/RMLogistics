package com.nexera.common.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanMilestoneDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanMilestone;
import com.nexera.common.entity.LoanMilestoneMaster;
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
		qry.setParameterList("loanMilestoneMaster",getMileStoneListForLoanStatus());
		List<LoanMilestone> loanStatusList=qry.list();	
		if(loanStatusList.size()>0){
			return loanStatusList.get(0);
		}
		return null;

	   
    }

	private List<LoanMilestoneMaster> getMileStoneListForLoanStatus(){
		
		List<LoanMilestoneMaster> reqdMSs = new ArrayList<LoanMilestoneMaster>();
		
		List<Integer> filteredList = new ArrayList<Integer>(Arrays.asList(Milestones.QC.getMilestoneID(),Milestones.LM_DECISION.getMilestoneID(),Milestones.OTHER.getMilestoneID(),Milestones.APP_FEE.getMilestoneID(),Milestones.APPRAISAL.getMilestoneID(),Milestones.DISCLOSURE.getMilestoneID()));
		
		for (Milestones ms : Milestones.values())
		{
			if (!filteredList.contains(ms.getMilestoneID()))
			{
				LoanMilestoneMaster master=new LoanMilestoneMaster();
				master.setId(ms.getMilestoneID());
				reqdMSs.add(master);
			}
		}	
		return reqdMSs;
	}

	
	private Milestones getMileStones(int inputID) {
		Milestones mileStone = Milestones.App1003;
		for (Milestones ms : Milestones.values()) {
			if (ms.getMilestoneID() == inputID) {
				mileStone = ms;
				break;
			}
		}
		return mileStone;
	}
	
	

}
