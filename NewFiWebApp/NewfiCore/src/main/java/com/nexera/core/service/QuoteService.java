package com.nexera.core.service;

import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.UserVO;

public interface QuoteService {
	public boolean addQuoteDetails(GeneratePdfVO generatePdfVO);
}
