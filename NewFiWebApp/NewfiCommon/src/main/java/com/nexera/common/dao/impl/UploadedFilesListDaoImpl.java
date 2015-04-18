package com.nexera.common.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;

@Component
public class UploadedFilesListDaoImpl extends GenericDaoImpl implements
        UploadedFilesListDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UploadedFilesListDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		return (Integer) save(uploadedFilesList);
	}

	@Override
	public List<UploadedFilesList> fetchAll(Integer uesrId, Integer loanId) {

		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class);
		criteria.createAlias("uploadedBy", "upBy");
		criteria.add(Restrictions.eq("upBy.id", uesrId));
		criteria.createAlias("loan", "ls");
		criteria.add(Restrictions.eq("ls.id", loanId));
		criteria.add(Restrictions.eq("isActivate", true));
		criteria.add(Restrictions.disjunction().add(Restrictions.isNull("documentType")).add(Restrictions.ne("documentType", CommonConstants.LQB_DOC_TYPE_CR)));
		
		LOG.info("criteria :"+criteria.toString() );
		return criteria.list();
	}

	@Override
	public void updateIsAssignedToTrue(Integer fileId) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE UploadedFilesList set isAssigned = :isAssigned "
		        + "WHERE id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("isAssigned", true);
		query.setParameter("id", fileId);
		Integer result = query.executeUpdate();
		LOG.info("trying to update with file : " + result);

	}

	@Override
	public void updateFileInLoanNeedList(Integer needId, Integer fileId) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE LoanNeedsList  set uploadFileId = :uploadFileId "
		        + "WHERE id = :id";
		Query query = session.createQuery(hql);
		UploadedFilesList uploadedFilesList = new UploadedFilesList();
		uploadedFilesList.setId(fileId);
		query.setParameter("uploadFileId", uploadedFilesList);
		query.setParameter("id", needId);
		Integer result = query.executeUpdate();
		LOG.info("trying to update with loan need list : " + result);

	}

	@Override
	public String findFileNameFromId(Integer fileId) {
		Session session = sessionFactory.getCurrentSession();
		UploadedFilesList filesList = (UploadedFilesList) session.load(
		        UploadedFilesList.class, fileId);
		LOG.info("Receive file name with id : " + filesList.getFileName());
		return filesList.getFileName();
	}

	@Override
	public void deactivateFileUsingFileId(Integer fileId) {
		Session session = sessionFactory.getCurrentSession();
		UploadedFilesList filesList = (UploadedFilesList) session.load(
		        UploadedFilesList.class, fileId);
		filesList.setIsActivate(false);
		session.update(filesList);

	}

	@Override
	public UploadedFilesList fetchUsingFileId(Integer fileId) {
		Session session = sessionFactory.getCurrentSession();
		UploadedFilesList filesList = (UploadedFilesList) session.load(
		        UploadedFilesList.class, fileId);
		return filesList;
	}

	@Override
	public UploadedFilesList fetchUsingFileUUID(String uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class)
		        .add(Restrictions.eq("uuidFileId", uuid));
		UploadedFilesList filesList = (UploadedFilesList) criteria
		        .uniqueResult();
		return filesList;
	}

	@Override
	public void updateLQBDocumentInUploadNeededFile(String lqbDocumentId,
	        Integer rowId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		UploadedFilesList uploadedFilesList = (UploadedFilesList) session.load(
		        UploadedFilesList.class, rowId);
		uploadedFilesList.setLqbFileID(lqbDocumentId);
		session.update(uploadedFilesList);
	}

	@Override
	public List<UploadedFilesList> fetchFilesBasedOnTimeStamp(Loan loan,
	        Date timeBeforeCallMade) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class)
		        .add(Restrictions.le("uploadedDate", timeBeforeCallMade));
		return criteria.list();
	}

	@Override
	public void remove(UploadedFilesList uploadedFileList) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(uploadedFileList);
		session.flush();

	}

	@Override
	public UploadedFilesList fetchUsingFileLQBFieldId(String lqbfieldId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class)
		        .add(Restrictions.eq("lqbFileID", lqbfieldId));
		return (UploadedFilesList) criteria.uniqueResult();
	}

	@Override
	public UploadedFilesList fetchUsingFileLQBDocId(String lqbDocID) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(UploadedFilesList.class)
		        .add(Restrictions.eq("lqbFileID", lqbDocID));
		return (UploadedFilesList) criteria.uniqueResult();
	}
}
