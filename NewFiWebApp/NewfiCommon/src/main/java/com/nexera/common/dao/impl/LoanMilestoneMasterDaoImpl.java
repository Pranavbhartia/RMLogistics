package com.nexera.common.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanMilestoneMasterDao;
import com.nexera.common.entity.LoanMilestoneMaster;
import com.nexera.common.entity.LoanTypeMaster;


@Component
public class LoanMilestoneMasterDaoImpl extends GenericDaoImpl implements LoanMilestoneMasterDao
{

    @SuppressWarnings ( "unchecked")
    @Override
    public List<LoanMilestoneMaster> findByLoanType( LoanTypeMaster loanTypeMaster )
    {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( LoanMilestoneMaster.class );
        criteria.add( Restrictions.eq( "loanTypeMaster", loanTypeMaster ) );

        return criteria.list();
    }

}
