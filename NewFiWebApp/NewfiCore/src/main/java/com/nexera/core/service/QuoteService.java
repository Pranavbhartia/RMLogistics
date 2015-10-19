package com.nexera.core.service;

import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.QuoteDetailsVO;
import com.nexera.common.vo.UserVO;

public interface QuoteService {
	public boolean addQuoteDetails(GeneratePdfVO generatePdfVO);
	public boolean createNewUser(String userName, Integer internalUserId);
	public QuoteDetails getUserDetails(QuoteCompositeKey quoteCompositeKey);
	public String createRegistrationDetails(QuoteDetails quoteDetails);
	public String createTeaserRateData(QuoteDetails quoteDetails);
	public void updateCreatedUser(QuoteCompositeKey compKey);
	public void updateDeletedUser(QuoteCompositeKey compKey);
	public GeneratePdfVO convertToGeneratePdfVo(QuoteDetails quoteDetails);
	public String  getUniqueIdFromQuoteDetails(QuoteCompositeKey compKey);
}
