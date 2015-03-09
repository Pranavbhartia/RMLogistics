package com.nexera.workflow.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.dao.WorkflowItemExecDao;


@Component
@Transactional
public class WorkflowItemExecDaoImpl extends GenericDaoImpl implements WorkflowItemExecDao
{

    /* (non-Javadoc)
     * @see com.nexera.workflow.dao.WorkflowItemExecDao#getWorkflowItemListByparentWorkflowExecItem(com.nexera.workflow.bean.WorkflowItemExec)
     */
    @Override
    public List<WorkflowItemExec> getWorkflowItemListByparentWorkflowExecItem( WorkflowItemExec workflowItemExecution )
    {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( WorkflowItemExec.class );
        criteria.add( Restrictions.eq( "parentWorkflowItemExec", workflowItemExecution ) );
        return criteria.list();
    }


}
