package com.nexera.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.QuoteDao;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateVO;
import com.nexera.core.service.QuoteService;

@Component("quoteServiceImpl")
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	private QuoteDao quoteDao;
	
	@Autowired
	private LoanDao loanDao;
	
	@Transactional
	public boolean addQuoteDetails(GeneratePdfVO generatePdfVO) {
		String username = "";
		Gson gson = new Gson();
		QuoteDetails quoteDetails = new QuoteDetails();
		QuoteCompositeKey quoteCompositeKey = new QuoteCompositeKey();
		
		quoteDetails.setProspectFirstName(generatePdfVO.getFirstName());
		quoteDetails.setProspectLastName(generatePdfVO.getLastName());
		quoteDetails.setEmailId(generatePdfVO.getEmailId());
		quoteDetails.setPhoneNo(generatePdfVO.getPhoneNo());
		quoteDetails.setRateAndApr(generatePdfVO.getRateAndApr());
		quoteDetails.setLoanProgram(generatePdfVO.getLoanProgram());
		quoteDetails.setPdfUrl(generatePdfVO.getPdfUrl());
		
		if (generatePdfVO.getEmailId() != null){
			username = generatePdfVO.getEmailId().split("@")[0];	
		}
		
		quoteCompositeKey.setUserName(username);
		quoteCompositeKey.setInternalUserId(generatePdfVO.getUserId());
		
		quoteDetails.setQuoteCompositeKey(quoteCompositeKey);
		
		LqbTeaserRateVo lqbTeaserRateVo = generatePdfVO.getLqbTeaserRateUnderQuickQuote();
		String lqbTeaserRateJson = gson.toJson(lqbTeaserRateVo);
		quoteDetails.setLqbRateJson(lqbTeaserRateJson);
		
		TeaserRateVO teaserRateVO = generatePdfVO.getInputCustmerDetailUnderQuickQuote();
		String teaserRateJson = gson.toJson(teaserRateVO);
		quoteDetails.setInputDetailsJson(teaserRateJson);
		
		quoteDetails.setCreatedDate(new Date(System.currentTimeMillis()));
		
		boolean status = quoteDao.addQuoteDetails(quoteDetails);
		return status;
	}
	

	
}
