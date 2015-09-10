package com.nexera.common.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nexera.common.dao.QuoteDao;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.QuoteDetails;

@Component
public class QuoteDaoImpl extends GenericDaoImpl implements QuoteDao{

	private static final Logger LOG = LoggerFactory
	        .getLogger(QuoteDaoImpl.class);
	
	public boolean addQuoteDetails(QuoteDetails quoteDetais) {
		boolean status = true;
		try{
			this.saveOrUpdate(quoteDetais);
		}
		catch(Exception e){
			LOG.error("Error in inserting/updating quote details: "+e);
			status = false;
		}
		finally{
			return status;
		}

	}
	
	

}
