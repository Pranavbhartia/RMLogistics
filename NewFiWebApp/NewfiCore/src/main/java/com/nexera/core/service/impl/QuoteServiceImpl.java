package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.nexera.common.compositekey.QuoteCompositeKey;
import com.nexera.common.dao.LoanDao;
import com.nexera.common.dao.QuoteDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.PurchaseDetails;
import com.nexera.common.entity.QuoteDetails;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.QuoteDetailsVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
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

		if (generatePdfVO.getEmailId() != null) {
			username = generatePdfVO.getEmailId().split("@")[0];
		}

		quoteCompositeKey.setUserName(username);
		quoteCompositeKey.setInternalUserId(generatePdfVO.getUserId());

		quoteDetails.setQuoteCompositeKey(quoteCompositeKey);

		LqbTeaserRateVo lqbTeaserRateVo = generatePdfVO
		        .getLqbTeaserRateUnderQuickQuote();
		String lqbTeaserRateJson = gson.toJson(lqbTeaserRateVo);
		quoteDetails.setLqbRateJson(lqbTeaserRateJson);

		TeaserRateVO teaserRateVO = generatePdfVO
		        .getInputCustmerDetailUnderQuickQuote();
		String teaserRateJson = gson.toJson(teaserRateVO);
		quoteDetails.setInputDetailsJson(teaserRateJson);

		quoteDetails.setCreatedDate(new Date(System.currentTimeMillis()));

		boolean status = quoteDao.addQuoteDetails(quoteDetails);
		return status;
	}

	@Transactional
	public String getUniqueIdFromQuoteDetails(QuoteCompositeKey compKey) {
		return quoteDao.getUniqueIdFromQuoteDetails(compKey);
	}

	@Transactional
	public QuoteDetails findQuoteDetailsById(String id) {
		return quoteDao.findQuoteDetailsById(id);
	}

	@Transactional
	public QuoteDetails getUserDetails(QuoteCompositeKey quoteCompositeKey) {
		QuoteDetails quoteDetails = quoteDao.getUserDetails(quoteCompositeKey);
		return quoteDetails;
	}

	@Transactional
	public boolean createNewUser(String userName, Integer internalUserId) {
		QuoteCompositeKey key = new QuoteCompositeKey();
		key.setInternalUserId(internalUserId);
		key.setUserName(userName);
		QuoteDetails quoteDetails = getUserDetails(key);

		return true;
	}

	@Transactional
	public void updateCreatedUser(QuoteCompositeKey compKey) {
		quoteDao.updateCreatedUser(compKey);
	}

	@Transactional
	public void updateLoanId(QuoteCompositeKey compKey, Loan loan) {
		quoteDao.updateLoanId(compKey, loan);
	}

	@Transactional
	public void updateDeletedUser(QuoteCompositeKey compKey) {
		quoteDao.updateDeletedUser(compKey);
	}

	public String createTeaserRateData(QuoteDetails quoteDetails) {
		Gson gson = new Gson();
		String teaserRate = quoteDetails.getLqbRateJson();
		return teaserRate;
	}

	public String createRegistrationDetails(QuoteDetails quoteDetails) {
		Gson gson = new Gson();
		JSONObject mainJson = new JSONObject();
		JSONObject personalDetails = new JSONObject();
		JSONObject loanType = new JSONObject();
		JSONObject purchaseDetails = new JSONObject();
		JSONObject propertyTypeMaster = new JSONObject();
		JSONObject refinancedetails = new JSONObject();

		personalDetails.put("firstName", quoteDetails.getProspectFirstName());
		personalDetails.put("lastName", quoteDetails.getProspectLastName());
		personalDetails.put("emailId", quoteDetails.getEmailId());

		mainJson.put("user", personalDetails);

		TeaserRateVO teaserRateVO = gson.fromJson(
		        quoteDetails.getInputDetailsJson(), TeaserRateVO.class);

		if (teaserRateVO.getLoanType().equals("REFCO")) {
			loanType.put("loanTypeCd", "REF");
			refinancedetails.put("cashTakeOut", teaserRateVO.getCashTakeOut());
		} else {
			loanType.put("loanTypeCd", teaserRateVO.getLoanType());
		}

		mainJson.put("loanType", loanType);

		if (teaserRateVO.getLoanType().equals("PUR")) {
			propertyTypeMaster.put("propertyTypeCd",
			        teaserRateVO.getPropertyType());
			propertyTypeMaster.put("residenceTypeCd",
			        teaserRateVO.getResidenceType());
			propertyTypeMaster.put("homeZipCode", teaserRateVO.getZipCode());

			mainJson.put("propertyTypeMaster", propertyTypeMaster);

			PurchaseDetails purchase = teaserRateVO.getPurchaseDetails();
			String purchaseJsonString = gson.toJson(purchase);
			JSONObject purchaseJson = new JSONObject(purchaseJsonString);

			mainJson.put("purchaseDetails", purchaseJson);
		} else {
			propertyTypeMaster.put("propertyTaxesPaid",
			        teaserRateVO.getPropertyTaxesPaid());
			propertyTypeMaster.put("propertyInsuranceCost",
			        teaserRateVO.getAnnualHomeownersInsurance());
			propertyTypeMaster.put("homeWorthToday",
			        teaserRateVO.getHomeWorthToday());
			propertyTypeMaster.put("homeZipCode", teaserRateVO.getZipCode());
			propertyTypeMaster.put("propTaxMonthlyOryearly", "Year");
			propertyTypeMaster.put("propInsMonthlyOryearly", "Year");
			propertyTypeMaster.put("propertyTypeCd",
			        teaserRateVO.getPropertyType());
			propertyTypeMaster.put("residenceTypeCd",
			        teaserRateVO.getResidenceType());

			mainJson.put("propertyTypeMaster", propertyTypeMaster);

			refinancedetails.put("refinanceOption",
			        teaserRateVO.getRefinanceOption());
			refinancedetails.put("currentMortgageBalance",
			        teaserRateVO.getCurrentMortgageBalance());
			refinancedetails.put("includeTaxes",
			        teaserRateVO.getPrivateincludeTaxes());

			mainJson.put("refinancedetails", refinancedetails);

		}
		return mainJson.toString();

	}

	public GeneratePdfVO convertToGeneratePdfVo(QuoteDetails quoteDetails) {
		Gson gson = new Gson();
		GeneratePdfVO generatePdfVO = new GeneratePdfVO();
		generatePdfVO.setFirstName(quoteDetails.getProspectFirstName());
		generatePdfVO.setLastName(quoteDetails.getProspectLastName());
		generatePdfVO.setEmailId(quoteDetails.getEmailId());
		generatePdfVO.setPdfUrl(quoteDetails.getPdfUrl());
		generatePdfVO.setLoanProgram(quoteDetails.getLoanProgram());
		generatePdfVO.setRateAndApr(quoteDetails.getRateAndApr());
		generatePdfVO.setPhoneNo(quoteDetails.getPhoneNo());
		generatePdfVO.setUserId(
		        quoteDetails.getQuoteCompositeKey().getInternalUserId());

		TeaserRateVO teaserRateVO = gson.fromJson(
		        quoteDetails.getInputDetailsJson(), TeaserRateVO.class);
		generatePdfVO.setInputCustmerDetailUnderQuickQuote(teaserRateVO);

		LqbTeaserRateVo lqbTeaserRateVo = gson
		        .fromJson(quoteDetails.getLqbRateJson(), LqbTeaserRateVo.class);
		generatePdfVO.setLqbTeaserRateUnderQuickQuote(lqbTeaserRateVo);

		TeaserRateResponseVO teaserRateResponseVO = new TeaserRateResponseVO();

		String loanProgram = generatePdfVO.getLqbTeaserRateUnderQuickQuote()
		        .getYearData();
		if (loanProgram.equals("5") || loanProgram.equals("7")) {
			loanProgram = loanProgram + " - Year ARM";
		} else {
			loanProgram = loanProgram + " - Year Fixed";
		}

		ArrayList<LqbTeaserRateVo> rateVO = new ArrayList<LqbTeaserRateVo>();
		rateVO.add(lqbTeaserRateVo);
		teaserRateResponseVO.setLoanDuration(loanProgram);
		teaserRateResponseVO.setRateVO(rateVO);

		generatePdfVO.setTeaserRateVO(teaserRateResponseVO);

		return generatePdfVO;
	}

	public QuoteDetailsVO convertEntityToVO(QuoteDetails quoteDetails) {
		QuoteDetailsVO quoteDetailsVO = new QuoteDetailsVO();
		quoteDetailsVO.setCreatedDate(quoteDetails.getCreatedDate());
		quoteDetailsVO.setEmailId(quoteDetails.getEmailId());
		quoteDetailsVO.setInputDetailsJson(quoteDetails.getInputDetailsJson());
		quoteDetailsVO.setInternalUserId(
		        quoteDetails.getQuoteCompositeKey().getInternalUserId());
		quoteDetailsVO.setLqbRateJson(quoteDetails.getLqbRateJson());
		quoteDetailsVO.setPdfUrl(quoteDetails.getPdfUrl());
		quoteDetailsVO.setPhoneNo(quoteDetails.getPhoneNo());
		quoteDetailsVO
		        .setProspectFirstName(quoteDetails.getProspectFirstName());
		quoteDetailsVO.setProspectLastName(quoteDetails.getProspectLastName());
		quoteDetailsVO.setIsCreated(quoteDetails.getIsCreated());
		quoteDetailsVO.setIsDeleted(quoteDetails.getIsDeleted());
		quoteDetailsVO.setProspectUsername(
		        quoteDetails.getQuoteCompositeKey().getUserName());
		quoteDetailsVO.setId(quoteDetails.getId());
		return quoteDetailsVO;
	}

	/**
	 * @param quoteDetailsVO
	 * @return
	 */
	public QuoteDetails convertVOToEntity(QuoteDetailsVO quoteDetailsVO) {

		QuoteDetails quoteDetails = new QuoteDetails();
		quoteDetails.setCreatedDate(quoteDetailsVO.getCreatedDate());
		quoteDetails.setEmailId(quoteDetailsVO.getEmailId());
		quoteDetails.setInputDetailsJson(quoteDetailsVO.getInputDetailsJson());
		QuoteCompositeKey quoteCompositeKey = new QuoteCompositeKey();
		quoteCompositeKey.setInternalUserId(quoteDetailsVO.getInternalUserId());
		quoteCompositeKey.setUserName(quoteDetailsVO.getProspectUsername());
		quoteDetails.setQuoteCompositeKey(quoteCompositeKey);
		quoteDetails.setLqbRateJson(quoteDetailsVO.getLqbRateJson());
		quoteDetails.setPdfUrl(quoteDetailsVO.getPdfUrl());
		quoteDetails.setPhoneNo(quoteDetailsVO.getPhoneNo());
		quoteDetails
		        .setProspectFirstName(quoteDetailsVO.getProspectFirstName());
		quoteDetails.setProspectLastName(quoteDetailsVO.getProspectLastName());
		quoteDetails.setIsCreated(quoteDetailsVO.getIsCreated());
		quoteDetails.setIsDeleted(quoteDetailsVO.getIsDeleted());
		quoteDetails.setId(quoteDetailsVO.getId());
		return quoteDetails;
	}

}
