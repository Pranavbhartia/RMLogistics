package com.nexera.common.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.dao.QuoteDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.QuoteDetails;

@Component
public class QuoteDaoImpl extends GenericDaoImpl implements QuoteDao {

	private static final Logger LOG = LoggerFactory
	        .getLogger(QuoteDaoImpl.class);

	public boolean addQuoteDetails(QuoteDetails quoteDetais) {
		boolean status = true;
		try {
			this.saveOrUpdate(quoteDetais);
		} catch (Exception e) {
			LOG.error("Error in inserting/updating quote details: " + e);
			status = false;
		} finally {
			return status;
		}

	}

	public QuoteDetails getUserDetails(QuoteCompositeKey compKey) {
		QuoteDetails quoteDetails = (QuoteDetails) this.load(QuoteDetails.class,
		        compKey);
		return quoteDetails;
	}

	public QuoteDetails findQuoteDetailsById(String id) {
		QuoteDetails quoteDetails = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery qry = session.createSQLQuery(
			        "select * from quotedetails where id = :id");
			qry.addEntity(QuoteDetails.class);
			qry.setParameter("id", id);
			// Here PRODUCTS is the table in the database...
			List<QuoteDetails> list = qry.list();
			for (QuoteDetails quote : list) {
				quoteDetails = quote;
			}

		} catch (Exception e) {
			LOG.error(
			        "Error in fetching quoteDetails on the basis of id: " + e);
		}
		return quoteDetails;
	}

	public String getUniqueIdFromQuoteDetails(QuoteCompositeKey compKey) {
		String id = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			SQLQuery qry = session.createSQLQuery(
			        "select id from quotedetails where prospect_username =  :username and internal_user_id = :internalUserId");
			qry.setParameter("username", compKey.getUserName());
			qry.setParameter("internalUserId", compKey.getInternalUserId());
			// Here PRODUCTS is the table in the database...
			List list = qry.list();
			Iterator it = list.iterator();

			if (it.hasNext()) {
				id = "" + it.next();
			}
		} catch (Exception e) {
			LOG.error("Error in fetching id from quotedetail table: " + e);
		}
		return id;
	}

	@Override
	public void updateCreatedUser(QuoteCompositeKey compKey) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE QuoteDetails quote set quote.isCreated = :isCreated WHERE quote.quoteCompositeKey = :quoteCompositeKey";
		Query query = session.createQuery(hql);
		query.setParameter("isCreated", true);
		query.setParameter("quoteCompositeKey", compKey);
		query.executeUpdate();
	}

	@Override
	public void updateLoanId(QuoteCompositeKey compKey, Loan loan) {

		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE QuoteDetails quote set quote.loan = :loan WHERE quote.quoteCompositeKey = :quoteCompositeKey";
		Query query = session.createQuery(hql);
		query.setParameter("loan", loan);
		query.setParameter("quoteCompositeKey", compKey);
		query.executeUpdate();
	}

	@Override
	public void updateDeletedUser(QuoteCompositeKey compKey) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE QuoteDetails quote set quote.isDeleted = :isDeleted WHERE quote.quoteCompositeKey = :quoteCompositeKey";
		Query query = session.createQuery(hql);
		query.setParameter("isDeleted", true);
		query.setParameter("quoteCompositeKey", compKey);
		query.executeUpdate();
	}

}
