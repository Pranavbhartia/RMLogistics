package com.nexera.common.dao;

import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.QuoteDetailsVO;

public interface QuoteDao extends GenericDao {
	public boolean addQuoteDetails(QuoteDetails quoteDetais);
	public QuoteDetails getUserDetails(QuoteCompositeKey compKey);
	public void updateCreatedUser(QuoteCompositeKey compKey);
	public void updateDeletedUser(QuoteCompositeKey compKey);
	public String  getUniqueIdFromQuoteDetails(QuoteCompositeKey compKey);
	public QuoteDetails findQuoteDetailsById(String id);
}
