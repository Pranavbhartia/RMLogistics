package com.nexera.common.dao;

import com.nexera.common.entity.QuoteDetails;

public interface QuoteDao extends GenericDao {
	public boolean addQuoteDetails(QuoteDetails quoteDetais);
}
