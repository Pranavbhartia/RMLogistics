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
    @Override
    public List<WorkflowItemMaster> getWorkflowItemMasterListByWorkflowMaster( WorkflowMaster workflowMaster )
    {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( WorkflowItemMaster.class );
        criteria.add( Restrictions.eq( "parentWorkflowMaster", workflowMaster ) );
        return criteria.list();
    }

}
