package com.nexera.workflow.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.dao.WorkflowItemMasterDao;


@Component
@Transactional
public class WorkflowItemMasterDaoImpl extends GenericDaoImpl implements WorkflowItemMasterDao
{

    /* (non-Javadoc)
     * @see com.nexera.workflow.dao.WorkflowItemMasterDao#getWorkflowItemMasterListByWorkflowMaster(com.nexera.workflow.bean.WorkflowMaster)
     */
    @SuppressWarnings ( "unchecked")
    @Override
    public List<WorkflowItemMaster> getWorkflowItemMasterListByWorkflowMaster( WorkflowMaster workflowMaster )
    {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( WorkflowItemMaster.class );
        criteria.add( Restrictions.eq( "parentWorkflowMaster", workflowMaster ) );
        return criteria.list();
    }


    /* (non-Javadoc)
     * @see com.nexera.workflow.dao.WorkflowItemMasterDao#checkIfOnSuccessOfAnotherItem(com.nexera.workflow.bean.WorkflowItemMaster)
     */
    @SuppressWarnings ( "rawtypes")
    @Override
    public Boolean checkIfOnSuccessOfAnotherItem( WorkflowItemMaster workflowItemMaster )
    {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( WorkflowItemMaster.class );
        criteria.add( Restrictions.eq( "onSuccess", workflowItemMaster ) );
        List list = criteria.list();
        if ( list == null )
            return false;
        else {
            if ( list.isEmpty() )
                return false;
            else
                return true;
        }
    }

}
