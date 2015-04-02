package com.nexera.common.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.UploadedFilesList;

@Component
public class LoanNeedListDaoImpl extends GenericDaoImpl implements
        LoanNeedListDao {

	@Override
	public LoanNeedsList findLoanNeedsListByFile(
	        UploadedFilesList uploadedFileList) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(LoanNeedsList.class);
		criteria.add(Restrictions.eq("uploadFileId", uploadedFileList));

		return (LoanNeedsList) criteria.uniqueResult();
	}
}
