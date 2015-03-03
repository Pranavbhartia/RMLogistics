package com.nexera.workflow.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.workflow.bean.WorkflowMaster;
import com.nexera.workflow.dao.WorkflowMasterDao;


@Component
@Transactional
public class WorkflowMasterDaoImpl extends GenericDaoImpl implements WorkflowMasterDao
{

    private static final Logger LOGGER = LoggerFactory.getLogger( WorkflowMasterDaoImpl.class );


    /* (non-Javadoc)
     * @see com.nexera.workflow.dao.WorkflowMasterDao#findWorkflowByType(int)
     */
    @Override
    public WorkflowMaster findWorkflowByType( String workflowType )
    {
        LOGGER.debug( "Inside method findWorkflowByType " );
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria( WorkflowMaster.class );
        criteria.add( Restrictions.eq( "workflowType", workflowType ) );
        WorkflowMaster workflowMaster = (WorkflowMaster) criteria.uniqueResult();
        return workflowMaster;
    }


}
