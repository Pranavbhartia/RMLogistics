package com.nexera.web.rest.util;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nexera.common.commons.Utils;
import com.nexera.common.vo.CustomerEmploymentIncomeVO;
import com.nexera.common.vo.CustomerSpouseEmploymentIncomeVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanLockRateVO;

@Component
public class LQBRequestUtil {

	private static final Logger LOG = LoggerFactory
	        .getLogger(LQBRequestUtil.class);
	@Value("${adv.credit.user}")
	private String advCreditUserName;
	@Value("${adv.credit.password}")
	private String advCreditPassword;

	public JSONObject prepareCreateLoanJson(String templateName, String sTicket) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sTemplateName", templateName);
			jsonChild.put("sTicket", sTicket);
			json.put("opName", "Create");

			json.put("loanVO", jsonChild);

			LOG.debug("jsonMapObject" + json);
		} catch (JSONException e) {
			LOG.error("Error prepareCreateLoanJson json for loan", e);
		}
		return json;
	}

	public JSONObject prepareLockLoanRateJson(LoanLockRateVO loanLockRateVO) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sLoanNumber", loanLockRateVO.getsLoanNumber());
			jsonChild.put("IlpTemplateId", loanLockRateVO.getIlpTemplateId());
			jsonChild.put("requestedFee", loanLockRateVO.getRequestedFee());
			jsonChild.put("requestedRate", loanLockRateVO.getRequestedRate());
			json.put("opName", "LockLoanProgram");

			json.put("loanVO", jsonChild);

			LOG.debug("jsonMapObject" + json);
		} catch (JSONException e) {
			LOG.error("Error prepareLockLoanRateJson json for loan", e);
		}
		return json;
	}

	public JSONObject saveLoan(String loanNumber, LoanAppFormVO loanAppFormVO,
	        String sTicket) {
		HashMap<String, String> hashmap = new HashMap();
		try {
			String condition = "";

			String loanPurpose = "";

			if ("PUR".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getLoanTypeCd())) {
				loanPurpose = "0";
				hashmap.put("loanPurchasePrice", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPurchaseDetails().getHousePrice()));
				hashmap.put("prodCashOut", "0");
			} else if ("REF".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getLoanTypeCd())) {
				// is it cashout
				if (loanAppFormVO.getRefinancedetails() != null
				        && ("REFCO").equalsIgnoreCase(loanAppFormVO
				                .getRefinancedetails().getRefinanceOption())) {
					loanPurpose = "2";
					hashmap.put("loanPurchasePrice", Utils
					        .unformatCurrencyField(loanAppFormVO
					                .getPropertyTypeMaster()
					                .getCurrentHomePrice()));

					hashmap.put("prodCashOut", Utils
					        .unformatCurrencyField(loanAppFormVO
					                .getRefinancedetails().getCashTakeOut()));
				} else {
					loanPurpose = "1";
					hashmap.put("loanPurchasePrice", Utils
					        .unformatCurrencyField(loanAppFormVO
					                .getPropertyTypeMaster()
					                .getCurrentHomePrice()));
					hashmap.put("prodCashOut", "0");
				}
			}
			hashmap.put("loanPurpose", loanPurpose);

			if ("Purchase".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getDescription())) {
				hashmap.put("loanApprovedValue", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPurchaseDetails().getHousePrice()));
				if (null != loanAppFormVO.getPropertyTypeMaster()) {
					hashmap.put("propertyState", loanAppFormVO
					        .getPropertyTypeMaster().getPropState());
					hashmap.put("propertyStreetAddress", loanAppFormVO
					        .getPropertyTypeMaster().getPropStreetAddress());
					hashmap.put("propertyCity", loanAppFormVO
					        .getPropertyTypeMaster().getPropCity());
					hashmap.put("propertyZip", loanAppFormVO
					        .getPropertyTypeMaster().getHomeZipCode());
					hashmap.put("applicantOccupancyType", loanAppFormVO
					        .getPropertyTypeMaster().getResidenceTypeCd());
				}
				hashmap.put("applicantAddrYrs", getYearsSpent(loanAppFormVO
				        .getUser().getCustomerDetail().getLivingSince()));

			} else {

				hashmap.put("applicantAddrYrs", getYearsSpent(loanAppFormVO
				        .getPropertyTypeMaster().getPropertyPurchaseYear()));
				hashmap.put("loanApprovedValue", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPropertyTypeMaster().getHomeWorthToday()));
				if (null != loanAppFormVO.getPropertyTypeMaster()
				        .getPropertyPurchaseYear()
				        && loanAppFormVO.getPropertyTypeMaster()
				                .getPropertyPurchaseYear().indexOf(" ") > 0)
					hashmap.put("propertyAcquiredYear", loanAppFormVO
					        .getPropertyTypeMaster().getPropertyPurchaseYear()
					        .split(" ")[1]);
				hashmap.put("mortageRemaining", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getRefinancedetails()
				                .getCurrentMortgageBalance()));
				if (null != loanAppFormVO.getPropertyTypeMaster()) {
					hashmap.put("applicantOccupancyType", loanAppFormVO
					        .getPropertyTypeMaster().getResidenceTypeCd());
				}

				if (null != loanAppFormVO.getPropertyTypeMaster()
				        && null != loanAppFormVO.getPropertyTypeMaster()
				                .getPropState()) {
					hashmap.put("propertyState", loanAppFormVO
					        .getPropertyTypeMaster().getPropState());
				} else {
					hashmap.put("propertyState", loanAppFormVO.getUser()
					        .getCustomerDetail().getAddressState());
				}
				if (null != loanAppFormVO.getPropertyTypeMaster()
				        && null != loanAppFormVO.getPropertyTypeMaster()
				                .getPropStreetAddress()) {
					hashmap.put("propertyStreetAddress", loanAppFormVO
					        .getPropertyTypeMaster().getPropStreetAddress());
				} else {
					hashmap.put("propertyStreetAddress", loanAppFormVO
					        .getUser().getCustomerDetail().getAddressStreet());
				}
				if (null != loanAppFormVO.getPropertyTypeMaster()
				        && null != loanAppFormVO.getPropertyTypeMaster()
				                .getPropCity()) {
					hashmap.put("propertyCity", loanAppFormVO
					        .getPropertyTypeMaster().getPropCity());
				} else {
					hashmap.put("propertyCity", loanAppFormVO.getUser()
					        .getCustomerDetail().getAddressCity());
				}
				if (null != loanAppFormVO.getPropertyTypeMaster()
				        && null != loanAppFormVO.getPropertyTypeMaster()
				                .getHomeZipCode()) {
					hashmap.put("propertyZip", loanAppFormVO
					        .getPropertyTypeMaster().getHomeZipCode());
				} else {
					hashmap.put("propertyZip", loanAppFormVO.getUser()
					        .getCustomerDetail().getAddressZipCode());
				}

			}

			hashmap.put("applicantId", loanAppFormVO.getUser()
			        .getCustomerDetail().getSsn());
			hashmap.put("firstName", loanAppFormVO.getLoan().getUser()
			        .getFirstName());
			hashmap.put("middleName", "");
			hashmap.put("lastName", loanAppFormVO.getLoan().getUser()
			        .getLastName());
			hashmap.put("applicantEmailAddress", loanAppFormVO.getLoan()
			        .getUser().getEmailId());
			hashmap.put("dateOfBirth", new SimpleDateFormat("yyyy-MM-dd")
			        .format(new Date(loanAppFormVO.getUser()
			                .getCustomerDetail().getDateOfBirth())));

			hashmap.put("applicantHomePhone", loanAppFormVO.getUser()
			        .getPhoneNumber());

			hashmap.put("alimonyName", "NONE");

			if (loanAppFormVO.getReceiveAlimonyChildSupport()) {
				hashmap.put("alimonyPayment",
				        loanAppFormVO.getChildSupportAlimony());
			} else {
				hashmap.put("alimonyPayment", "1000");

			}

			hashmap.put("jobExpenses", "1000");

			if (loanAppFormVO.getCustomerEmploymentIncome() != null
			        && loanAppFormVO.getCustomerEmploymentIncome().size() > 0) {
				hashmap.put(
				        "jobRelatedPayment",
				        (Integer.parseInt(Utils
				                .unformatCurrencyField(loanAppFormVO
				                        .getCustomerEmploymentIncome().get(0)
				                        .getCustomerEmploymentIncome()
				                        .getEmployedIncomePreTax())) * 12)
				                + "");

			} else {
				hashmap.put("jobRelatedPayment", "1000");
			}

			hashmap.put("userSSNnumber", loanAppFormVO.getUser()
			        .getCustomerDetail().getSsn());
			hashmap.put("baseIncome", CalculateBaseIncome(loanAppFormVO));
			hashmap.put("ProdLckdDays", "30");

			if (null == loanAppFormVO.getUser().getCustomerDetail().getSsn()
			        || "".equalsIgnoreCase(loanAppFormVO.getUser()
			                .getCustomerDetail().getSsn())) {

				loanAppFormVO.setSsnProvided(false);

			}

			if (null == loanAppFormVO.getCustomerSpouseDetail().getSpouseSsn()
			        || "".equalsIgnoreCase(loanAppFormVO
			                .getCustomerSpouseDetail().getSpouseSsn())) {

				loanAppFormVO.setCbSsnProvided(false);

			}

			if ("Purchase".equalsIgnoreCase(loanAppFormVO.getLoanType()
			        .getDescription())) {
				hashmap.put("loanAmount", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getPurchaseDetails().getLoanAmount()));
			} else {

				hashmap.put("loanAmount", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getRefinancedetails()
				                .getCurrentMortgageBalance()));
			}
			hashmap.put("applicantCity", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressCity());
			hashmap.put("applicantState", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressState());
			hashmap.put("applicantZipCode", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressZipCode());
			if (null == loanAppFormVO.getUser().getCustomerDetail()
			        .getAddressStreet()
			        || "".equalsIgnoreCase(loanAppFormVO.getUser()
			                .getCustomerDetail().getAddressStreet())) {
				hashmap.put("applicantAddress", "undisclosed");
			} else {
				hashmap.put("applicantAddress", loanAppFormVO.getUser()
				        .getCustomerDetail().getAddressStreet());
			}

			if (null != loanAppFormVO.getCustomerEmploymentIncome()
			        && loanAppFormVO.getCustomerEmploymentIncome().size() > 0) {
				hashmap.put("applicantEmplrNm", loanAppFormVO
				        .getCustomerEmploymentIncome().get(0)
				        .getCustomerEmploymentIncome().getEmployedAt());
				hashmap.put("applicantEmplrJobTitle", loanAppFormVO
				        .getCustomerEmploymentIncome().get(0)
				        .getCustomerEmploymentIncome().getJobTitle());
				// hashmap.put("borrowerEmplrMonthlyIncome",loanAppFormVO.getCustomerEmploymentIncome().get(0).getCustomerEmploymentIncome().getEmployedIncomePreTax());
				hashmap.put("applicantEmplrStartDate", loanAppFormVO
				        .getCustomerEmploymentIncome().get(0)
				        .getCustomerEmploymentIncome().getEmployedSince());
			}

			hashmap = getBorrowerGovernmentQuestion(hashmap, loanAppFormVO);

			// "condition": "coborrowerWithSSNBoth",

			if (loanAppFormVO.getIsCoborrowerPresent() == false
			        && loanAppFormVO.getSsnProvided() == true) {
				hashmap = getBorrowerCredit(hashmap);
				condition = "noCoBorrowerWithSSN";
			}

			if (loanAppFormVO.getIsCoborrowerPresent() == false
			        && loanAppFormVO.getSsnProvided() == false) {
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000");
				hashmap = appendBorrowerDefCredScore(hashmap);
				condition = "noCoBorrowerWithoutSSN";
			}

			if (loanAppFormVO.getIsCoborrowerPresent() == true
			        && loanAppFormVO.getIsSpouseOnLoan() == true
			        && loanAppFormVO.getSsnProvided() == true
			        && loanAppFormVO.getCbSsnProvided() == true) {
				hashmap = getBorrowerCredit(hashmap);
				hashmap = appendSpouseCoBorrowerDetails(hashmap, loanAppFormVO);
				condition = "coborrowerIsWifeWithSSNBoth";
			}

			if (loanAppFormVO.getIsCoborrowerPresent() == true
			        && loanAppFormVO.getIsSpouseOnLoan() == true
			        && loanAppFormVO.getSsnProvided() == false
			        && loanAppFormVO.getCbSsnProvided() == false) {

				hashmap = appendSpouseCoBorrowerDetails(hashmap, loanAppFormVO);
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000000");
				hashmap.put("userCoborrowerSSNnumber", "000000000");
				hashmap = appendBorrowerDefCredScore(hashmap);
				hashmap = appendSpCBDefCredScore(hashmap);
				condition = "coborrowerIsWifeWithoutSSNBoth";
			}

			if (loanAppFormVO.getIsCoborrowerPresent() == true
			        && loanAppFormVO.getIsSpouseOnLoan() == false
			        && loanAppFormVO.getSsnProvided() == true
			        && loanAppFormVO.getCbSsnProvided() == true) {
				hashmap = getBorrowerCredit(hashmap);
				hashmap = appendCoBorrowerDetails(hashmap, loanAppFormVO);
				hashmap = getCoBorrowerCredit(hashmap);
				condition = "coborrowerWithSSNBoth";
			}

			if (loanAppFormVO.getIsCoborrowerPresent() == true
			        && loanAppFormVO.getIsSpouseOnLoan() == false
			        && loanAppFormVO.getSsnProvided() == false
			        && loanAppFormVO.getCbSsnProvided() == false) {
				hashmap = appendCoBorrowerDetails(hashmap, loanAppFormVO);
				hashmap.put("applicantId", " ");
				hashmap.put("userSSNnumber", "000000000");
				hashmap.put("userCoborrowerSSNnumber", "000000000");
				hashmap.put("ApplicantCoBorrowerId", "000000000");
				hashmap = appendBorrowerDefCredScore(hashmap);
				hashmap = appendCBDefCredScore(hashmap);
				condition = "coborrowerWithoutSSNBoth";
			}
			HashMap<String, String> finalmap = new HashMap();
			for (String key : hashmap.keySet()) {
				
				String encodedValue = hashmap.get(key);
				if(!key.equalsIgnoreCase("applicantEmailAddress"))
					encodedValue =URLEncoder.encode(hashmap.get(key))
				        .replaceAll("[+]", " ");
				finalmap.put(key, encodedValue);

			}
			JSONObject jsonObject = new JSONObject(finalmap);

			JSONObject json = new JSONObject();
			JSONObject jsonChild = new JSONObject();

			jsonChild.put("condition", condition);
			jsonChild.put("sLoanNumber", loanNumber);
			jsonChild.put("sDataContentMap", jsonObject);
			jsonChild.put("format", "0");
			jsonChild.put("sTicket", sTicket);

			json.put("opName", "Save");
			json.put("loanVO", jsonChild);

			LOG.debug("jsonMapObject Save operation" + json);

			return json;

		} catch (JSONException e) {
			LOG.debug("JSONException ", e);
			return null;
		}

	}

	public String getYearsSpent(String purchaseTime) {
		int yeardiff = 0;
		int tempTime = 0;
		if (null != purchaseTime && purchaseTime.indexOf(" ") > 0) {
			tempTime = Integer.parseInt(purchaseTime.split(" ")[1]);
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		yeardiff = year - tempTime;

		return yeardiff + "";
	}

	HashMap<String, String> appendSpouseCoBorrowerDetails(
	        HashMap<String, String> hashmap, LoanAppFormVO loanAppFormVO) {
		if (loanAppFormVO.getCustomerSpouseDetail() != null) {
			hashmap.put("firstCoborrowerName", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseName());
			hashmap.put("middleCoborrowerName", "");
			hashmap.put("lastCoborrowerName", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseLastName());
			hashmap.put("dateOfCoborrowerBirth", new SimpleDateFormat(
			        "yyyy-MM-dd").format(new Date(loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseDateOfBirth())));
			hashmap.put("baseCoborrowerIncome",
			        CalculateCoborrowerBaseIncome(loanAppFormVO));
			hashmap.put("applicantCoborrowerAddress", loanAppFormVO
			        .getCustomerSpouseDetail().getStreetAddress());
			hashmap.put("applicantCoborrowerCity", loanAppFormVO
			        .getCustomerSpouseDetail().getCity());
			hashmap.put("applicantCoborrowerState", loanAppFormVO
			        .getCustomerSpouseDetail().getState());
			hashmap.put("applicantCoborrowerZip", loanAppFormVO
			        .getCustomerSpouseDetail().getZip());
			hashmap.put("userCoborrowerSSNnumber", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseSsn());
			hashmap.put("applicationCoborrowerHomePhone", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseSecPhoneNumber());
			if (null != loanAppFormVO.getCustomerSpouseEmploymentIncome()
			        && loanAppFormVO.getCustomerSpouseEmploymentIncome().size() > 0) {
				hashmap.put("applicationCoborrowerEmplrName", loanAppFormVO
				        .getCustomerSpouseEmploymentIncome().get(0)
				        .getCustomerSpouseEmploymentIncome().getEmployedAt());
				hashmap.put("applicationCoborrowerEmployementTitle",
				        loanAppFormVO.getCustomerSpouseEmploymentIncome()
				                .get(0).getCustomerSpouseEmploymentIncome()
				                .getJobTitle());
				// hashmap.put(
				// "applicationCoborrowerMontlyIncome",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedIncomePreTax());
				hashmap.put("applicationCoborrowerEmploymentStartDate",
				        loanAppFormVO.getCustomerSpouseEmploymentIncome()
				                .get(0).getCustomerSpouseEmploymentIncome()
				                .getEmployedSince());

			}

		}

		hashmap = getCoBorrowerGovernmentQuestion(hashmap, loanAppFormVO);
		return hashmap;
	}

	HashMap<String, String> appendBorrowerDefCredScore(
	        HashMap<String, String> hashmap) {

		hashmap.put("applicantExperianScore", "800");
		hashmap.put("applicantEquifaxScore", "800");
		hashmap.put("applicantTransUnionScore", "800");
		return hashmap;
	}

	HashMap<String, String> appendSpCBDefCredScore(
	        HashMap<String, String> hashmap) {

		hashmap.put("ExperianCoborrowerWifeScore", "800");
		hashmap.put("EquifaxCoborrowerWifeScore", "800");
		hashmap.put("TransUnionCoborrowerWifeScore", "800");
		return hashmap;
	}

	HashMap<String, String> appendCBDefCredScore(HashMap<String, String> hashmap) {

		hashmap.put("ExperianCoborrowerScore", "800");
		hashmap.put("EquifaxCoborrowerScore", "800");
		hashmap.put("TransUnionCoborrowerScore", "800");
		return hashmap;
	}

	HashMap<String, String> appendCoBorrowerDetails(
	        HashMap<String, String> hashmap, LoanAppFormVO loanAppFormVO) {
		if (loanAppFormVO.getCustomerSpouseDetail() != null) {
			hashmap.put("firstCoborrowerName", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseName());
			hashmap.put("middleCoborrowerName", "");
			hashmap.put("lastCoborrowerName", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseLastName());
			hashmap.put("dateOfCoborrowerBirth", new SimpleDateFormat(
			        "yyyy-MM-dd").format(new Date(loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseDateOfBirth())));
			hashmap.put("baseCoborrowerIncome",
			        CalculateCoborrowerBaseIncome(loanAppFormVO));
			hashmap.put("applicantCoborrowerAddress", loanAppFormVO
			        .getCustomerSpouseDetail().getStreetAddress());
			hashmap.put("applicantCoborrowerCity", loanAppFormVO
			        .getCustomerSpouseDetail().getCity());
			hashmap.put("applicantCoborrowerState", loanAppFormVO
			        .getCustomerSpouseDetail().getState());
			hashmap.put("applicantCoborrowerZipCode", loanAppFormVO
			        .getCustomerSpouseDetail().getCity());
			hashmap.put("userCoborrowerSSNnumber", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseSsn());
			hashmap.put("ApplicantCoBorrowerId", loanAppFormVO
			        .getCustomerSpouseDetail().getSpouseSsn());
			hashmap.put("alimonyCoborrowerName", "NONE");
			// hashmap.put("alimonyCoborrowerPayment","1000");
			if (loanAppFormVO.getCustomerSpouseDetail()
			        .getChildSupportAlimony() != null
			        && loanAppFormVO.getCustomerSpouseDetail()
			                .getChildSupportAlimony() != "") {
				hashmap.put("alimonyCoborrowerPayment", Utils
				        .unformatCurrencyField(loanAppFormVO
				                .getCustomerSpouseDetail()
				                .getChildSupportAlimony()));
			} else {
				hashmap.put("alimonyCoborrowerPayment", "1000");

			}

			hashmap.put("jobCoborrowerExpenses", "1000");
			// hashmap.put( "jobRelatedCoborrowerPayment","100000");

			if (loanAppFormVO.getCustomerSpouseEmploymentIncome() != null
			        && loanAppFormVO.getCustomerSpouseEmploymentIncome().size() > 0) {

				hashmap.put(
				        "jobRelatedCoborrowerPayment",
				        (Integer.parseInt(Utils
				                .unformatCurrencyField(loanAppFormVO
				                        .getCustomerSpouseEmploymentIncome()
				                        .get(0)
				                        .getCustomerSpouseEmploymentIncome()
				                        .getEmployedIncomePreTax())) * 12)
				                + "");
			} else {
				hashmap.put("jobRelatedCoborrowerPayment", "10000");
			}

		}

		hashmap.put("applicationCoborrowerHomePhone", loanAppFormVO
		        .getCustomerSpouseDetail().getSpouseSecPhoneNumber());

		if (null != loanAppFormVO.getCustomerSpouseEmploymentIncome()
		        && loanAppFormVO.getCustomerSpouseEmploymentIncome().size() > 0) {
			hashmap.put("applicationCoborrowerEmplrName", loanAppFormVO
			        .getCustomerSpouseEmploymentIncome().get(0)
			        .getCustomerSpouseEmploymentIncome().getEmployedAt());
			hashmap.put("applicationCoborrowerEmployementTitle", loanAppFormVO
			        .getCustomerSpouseEmploymentIncome().get(0)
			        .getCustomerSpouseEmploymentIncome().getJobTitle());
			// hashmap.put(
			// "applicationCoborrowerMontlyIncome",loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0).getCustomerSpouseEmploymentIncome().getEmployedIncomePreTax());
			hashmap.put("applicationCoborrowerEmploymentStartDate",
			        loanAppFormVO.getCustomerSpouseEmploymentIncome().get(0)
			                .getCustomerSpouseEmploymentIncome()
			                .getEmployedSince());

		}
		hashmap = getCoBorrowerGovernmentQuestion(hashmap, loanAppFormVO);
		return hashmap;

	}

	HashMap<String, String> getBorrowerCredit(HashMap<String, String> hashmap) {

		hashmap.put("creditCardId", "eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("LOGIN_NAME", advCreditUserName);
		hashmap.put("PASSWORD", advCreditPassword);
		hashmap.put("equifaxStatus", "N");
		hashmap.put("experianStatus", "N");
		hashmap.put("transunionStatus", "Y");

		return hashmap;
	}

	HashMap<String, String> getCoBorrowerCredit(HashMap<String, String> hashmap) {
		hashmap.put("creditCoborrowerCardId",
		        "eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("equifaxCoborrowerStatus", "N");
		hashmap.put("experianCoborrowerStatus", "N");
		hashmap.put("LOGIN_Coborrower_NAME", advCreditUserName);
		hashmap.put("PASS_COBORROWER_WORD", advCreditPassword);
		hashmap.put("transunionCoborrowerStatus", "Y");

		return hashmap;
	}

	public HashMap<String, String> getBorrowerGovernmentQuestion(
	        HashMap<String, String> hashmap, LoanAppFormVO loanAppFormVO) {

		if (null != loanAppFormVO.getGovernmentquestion()) {

			hashmap.put("applicantDecJudgment", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isOutstandingJudgments()));
			hashmap.put("applicantDecBankrupt", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isBankrupt()));
			hashmap.put("applicantDecForeclosure", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isPropertyForeclosed()));
			hashmap.put("applicantDecLawsuit", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isLawsuit()));
			hashmap.put("applicantDecObligated", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isObligatedLoan()));
			hashmap.put("applicantDecDelinquent", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isFederalDebt()));
			hashmap.put("applicantDecAlimony", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isObligatedToPayAlimony()));
			hashmap.put("applicantDecBorrowing", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().getIsDownPaymentBorrowed()));
			hashmap.put("applicantDecEndorser", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isEndorser()));
			hashmap.put("applicantDecCitizen", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isUSCitizen()));
			if (loanAppFormVO.getGovernmentquestion().isUSCitizen() == false) {
				hashmap.put("applicantDecResidency", returnYesNo(loanAppFormVO
				        .getGovernmentquestion().getPermanentResidentAlien()));
			} else {
				hashmap.put("applicationCoborrowerDecResidency", "No");
			}
			hashmap.put("applicantDecOcc", returnYesNo(loanAppFormVO
			        .getGovernmentquestion().isOccupyPrimaryResidence()));
			hashmap.put("applicantDecPastOwnedPropT",
			        getPropertyTypeEnum(loanAppFormVO.getGovernmentquestion()
			                .getTypeOfPropertyOwned()));
			hashmap.put("TitleTapplicantDecPastOwnedProperty",
			        getPropertyTitleEnum(loanAppFormVO.getGovernmentquestion()
			                .getPropertyTitleStatus()));
			hashmap.put("applicantNoFurnish", loanAppFormVO
			        .getGovernmentquestion().getSkipOptionalQuestion() + "");
			hashmap.put("applicantHispanicT", getEthnicityEnum(loanAppFormVO
			        .getGovernmentquestion().getEthnicity()));
			hashmap = getBorrowerRace(hashmap, loanAppFormVO);
			if ("male".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion()
			        .getSex()))
				hashmap.put("applicantGender", "1");
			else
				hashmap.put("applicantGender", "2");
		}

		return hashmap;
	}

	String getPropertyTypeEnum(String propertyType) {
		if (null != propertyType) {
			if ("Your Primary Residence".equalsIgnoreCase(propertyType))
				return "1";
			else if ("A Second Home".equalsIgnoreCase(propertyType))
				return "2";
			else if ("An Investment Property".equalsIgnoreCase(propertyType))
				return "3";
			else
				return "0";
		}
		return "0";

	}

	String getPropertyTitleEnum(String propertyTitle) {

		if (null != propertyTitle) {
			if ("I was the sole title holder".equalsIgnoreCase(propertyTitle))
				return "1";
			else if ("I held the title jointly with my spouse"
			        .equalsIgnoreCase(propertyTitle))
				return "2";
			else if ("I held title jointly with another person"
			        .equalsIgnoreCase(propertyTitle))
				return "3";
			else
				return "0";
		}

		return "0";
	}

	String getEthnicityEnum(String ethnicity) {
		if ("hispanic".equalsIgnoreCase(ethnicity))
			return "1";
		else if ("latino".equalsIgnoreCase(ethnicity))
			return "2";
		else
			return "0";
	}

	HashMap<String, String> getBorrowerRace(HashMap<String, String> hashmap,
	        LoanAppFormVO loanAppFormVO) {

		hashmap.put("applicantIsAmericanIndian", "false");
		hashmap.put("applicantIsAsian", "false");
		hashmap.put("applicantIsBlack", "false");
		hashmap.put("applicantIsPacificIslander", "false");
		hashmap.put("applicantIsWhite", "false");

		if ("americanIndian".equalsIgnoreCase(loanAppFormVO
		        .getGovernmentquestion().getRace()))
			hashmap.put("applicantIsAmericanIndian", "true");
		else if ("nativeHawaiian".equalsIgnoreCase(loanAppFormVO
		        .getGovernmentquestion().getRace()))
			hashmap.put("applicantIsPacificIslander", "true");
		else if ("black".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion()
		        .getRace()))
			hashmap.put("applicantIsBlack", "true");
		else if ("white".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion()
		        .getRace()))
			hashmap.put("applicantIsWhite", "true");
		else if ("asian".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion()
		        .getRace()))
			hashmap.put("applicantIsAsian", "true");
		else
			return hashmap;

		return hashmap;
	}

	String returnYesNo(boolean key) {
		if (key)
			return "Yes";
		else
			return "No";
	}

	HashMap<String, String> getCoBorrowerGovernmentQuestion(
	        HashMap<String, String> hashmap, LoanAppFormVO loanAppFormVO) {

		if (null != loanAppFormVO.getSpouseGovernmentQuestions()) {
			hashmap.put("applicationCoborrowerDecJudgment",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isOutstandingJudgments()));
			hashmap.put("applicationCoborrowerDecBankrupt",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isBankrupt()));
			hashmap.put("applicationCoborrowerDecForeclosure",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isPropertyForeclosed()));
			hashmap.put("applicationCoborrowerDecLawsuit",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isLawsuit()));
			hashmap.put("applicationCoborrowerDecObligated",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isObligatedLoan()));
			hashmap.put("applicationCoborrowerDecDelinquent",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isFederalDebt()));
			hashmap.put("applicationCoborrowerDecAlimony",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isObligatedToPayAlimony()));
			hashmap.put("applicationCoborrowerDecBorrowing",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .getIsDownPaymentBorrowed()));
			hashmap.put("applicationCoborrowerDecEndorser",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isEndorser()));
			hashmap.put("applicationCoborrowerDecCitizen",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isUSCitizen()));
			if (loanAppFormVO.getSpouseGovernmentQuestions().isUSCitizen() == false) {
				hashmap.put("applicationCoborrowerDecResidency",
				        returnYesNo(loanAppFormVO
				                .getSpouseGovernmentQuestions()
				                .isPermanentResidentAlien()));
			} else {
				hashmap.put("applicationCoborrowerDecResidency", "No");
			}
			hashmap.put("applicationCoborrowerDecOcc",
			        returnYesNo(loanAppFormVO.getSpouseGovernmentQuestions()
			                .isOccupyPrimaryResidence()));
			hashmap.put("applicationCoborrowerDecPastOwnedPropT",
			        getPropertyTypeEnum(loanAppFormVO
			                .getSpouseGovernmentQuestions()
			                .getTypeOfPropertyOwned()));
			hashmap.put("titleTApplicationCoborrowerDecPastOwnedProp",
			        getPropertyTitleEnum(loanAppFormVO
			                .getSpouseGovernmentQuestions()
			                .getPropertyTitleStatus()));
			hashmap.put("applicationCoborrowerNoFurnish", loanAppFormVO
			        .getSpouseGovernmentQuestions().getSkipOptionalQuestion()
			        + "");
			hashmap.put("applicantHispanicT", getEthnicityEnum(loanAppFormVO
			        .getSpouseGovernmentQuestions().getEthnicity()));
			hashmap = getBorrowerRace(hashmap, loanAppFormVO);
			if ("male".equalsIgnoreCase(loanAppFormVO.getGovernmentquestion()
			        .getSex()))
				hashmap.put("applicationCoborrowerGender", "1");
			else {
				hashmap.put("applicationCoborrowerGender", "2");
			}

		}

		return hashmap;
	}

	public JSONObject pullTrimergeCreditScore(String loanNumber,
	        LoanAppFormVO loanAppFormVO, String sTicket, String ssnNumber,
	        String reportId, boolean requestTrimerge) {

		HashMap<String, String> hashmap = new HashMap<>();
		hashmap.put("applicantId", loanAppFormVO.getUser().getCustomerDetail()
		        .getSsn());
		if (reportId != null && requestTrimerge) {
			hashmap.put("report_id", reportId);
		}
		hashmap.put("firstName", loanAppFormVO.getLoan().getUser()
		        .getFirstName());
		hashmap.put("lastName", loanAppFormVO.getLoan().getUser().getLastName());
		hashmap.put("userSSNnumber", ssnNumber);
		hashmap.put("applicantCity", loanAppFormVO.getUser()
		        .getCustomerDetail().getAddressCity());
		hashmap.put("applicantState", loanAppFormVO.getUser()
		        .getCustomerDetail().getAddressState());
		hashmap.put("applicantZipCode", loanAppFormVO.getUser()
		        .getCustomerDetail().getAddressZipCode());
		if (null == loanAppFormVO.getUser().getCustomerDetail()
		        .getAddressStreet()
		        || "".equalsIgnoreCase(loanAppFormVO.getUser()
		                .getCustomerDetail().getAddressStreet())) {
			hashmap.put("applicantAddress", "undisclosed");
		} else {
			hashmap.put("applicantAddress", loanAppFormVO.getUser()
			        .getCustomerDetail().getAddressStreet());
		}
		if (requestTrimerge) {
			hashmap = saveTrimergeStatus(hashmap);
		} else {
			hashmap = saveTransunionStatus(hashmap);
		}
		JSONObject jsonObject = new JSONObject(hashmap);
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();

		try {
			jsonChild.put("sLoanNumber", loanNumber);
			jsonChild.put("sDataContentMap", jsonObject);
			jsonChild.put("format", "0");
			jsonChild.put("sTicket", sTicket);

			json.put("opName", "SaveCreditScore");
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOG.error("Exception caught " + e.getMessage());
			json = null;
		}

		LOG.debug("jsonMapObject Save operation" + json);

		return json;

	}

	HashMap<String, String> saveTrimergeStatus(HashMap<String, String> hashmap) {

		hashmap.put("creditCardId", "eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("LOGIN_NAME", advCreditUserName);
		hashmap.put("PASSWORD", advCreditPassword);
		hashmap.put("equifaxStatus", "Y");
		hashmap.put("experianStatus", "Y");
		hashmap.put("transunionStatus", "N");

		return hashmap;
	}

	HashMap<String, String> saveTransunionStatus(HashMap<String, String> hashmap) {

		hashmap.put("creditCardId", "eb228885-b484-404a-99ff-b28511dd3e38");
		hashmap.put("LOGIN_NAME", advCreditUserName);
		hashmap.put("PASSWORD", advCreditPassword);
		hashmap.put("equifaxStatus", "N");
		hashmap.put("experianStatus", "N");
		hashmap.put("transunionStatus", "Y");

		return hashmap;
	}

	private String CalculateBaseIncome(LoanAppFormVO loanAppFormVO) {

		String baseIncome = null;
		int w2Icome = 0;
		int selfEmployedIncome = 0;
		int chileSupport = 0;
		int socialSecurityIncome = 0;
		int disabilityIncome = 0;
		int pensionIncome = 0;
		int retirementIncome = 0;

		if (null != loanAppFormVO.getCustomerEmploymentIncome()
		        && loanAppFormVO.getCustomerEmploymentIncome().size() > 0/*
																		 * .get(0
																		 * ).
																		 * getCustomerEmploymentIncome
																		 * () &&
																		 * null
																		 * !=
																		 * loanAppFormVO
																		 * .
																		 * getCustomerEmploymentIncome
																		 * (
																		 * ).get
																		 * (0).
																		 * getCustomerEmploymentIncome
																		 * ().
																		 * getEmployedIncomePreTax
																		 * ()
																		 */) {

			Iterator<CustomerEmploymentIncomeVO> it = loanAppFormVO
			        .getCustomerEmploymentIncome().iterator();
			while (it.hasNext()) {

				w2Icome = w2Icome
				        + Integer.parseInt(Utils.unformatCurrencyField((it
				                .next()).getCustomerEmploymentIncome()
				                .getEmployedIncomePreTax()));
			}

		}
		if (null != loanAppFormVO.getSelfEmployedMonthlyIncome()
		        && !loanAppFormVO.getSelfEmployedMonthlyIncome()
		                .equalsIgnoreCase("")) {
			selfEmployedIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getSelfEmployedMonthlyIncome()));
		}

		if (null != loanAppFormVO.getChildSupportAlimony()
		        && !loanAppFormVO.getChildSupportAlimony().equalsIgnoreCase("")) {
			chileSupport = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getChildSupportAlimony()));
		}

		if (null != loanAppFormVO.getSocialSecurityIncome()
		        && !loanAppFormVO.getSocialSecurityIncome()
		                .equalsIgnoreCase("")) {
			socialSecurityIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getSocialSecurityIncome()));
		}

		if (null != loanAppFormVO.getSsDisabilityIncome()
		        && !loanAppFormVO.getSsDisabilityIncome().equalsIgnoreCase("")) {
			disabilityIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getSsDisabilityIncome()));
		}
		if (null != loanAppFormVO.getMonthlyPension()
		        && !loanAppFormVO.getMonthlyPension().equalsIgnoreCase("")) {
			pensionIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO.getMonthlyPension()));
		}

		if (null != loanAppFormVO.getRetirementIncome()
		        && !loanAppFormVO.getRetirementIncome().equalsIgnoreCase("")) {
			retirementIncome = Integer
			        .parseInt(Utils.unformatCurrencyField(loanAppFormVO
			                .getRetirementIncome()));
		}

		baseIncome = (w2Icome + selfEmployedIncome + chileSupport
		        + socialSecurityIncome + disabilityIncome + pensionIncome + retirementIncome)
		        + "";
		return baseIncome;
	}

	private String CalculateCoborrowerBaseIncome(LoanAppFormVO loanAppFormVO) {

		String coBorrowerBaseIncome = null;
		int w2Icome = 0;
		int selfEmployedIncome = 0;
		int chileSupport = 0;
		int socialSecurityIncome = 0;
		int disabilityIncome = 0;
		int pensionIncome = 0;
		int retirementIncome = 0;

		if (null != loanAppFormVO.getCustomerSpouseEmploymentIncome()
		        && loanAppFormVO.getCustomerSpouseEmploymentIncome().size() > 0/*
																				 * .
																				 * get
																				 * (
																				 * 0
																				 * )
																				 * .
																				 * getCustomerEmploymentIncome
																				 * (
																				 * )
																				 * &&
																				 * null
																				 * !=
																				 * loanAppFormVO
																				 * .
																				 * getCustomerEmploymentIncome
																				 * (
																				 * )
																				 * .
																				 * get
																				 * (
																				 * 0
																				 * )
																				 * .
																				 * getCustomerEmploymentIncome
																				 * (
																				 * )
																				 * .
																				 * getEmployedIncomePreTax
																				 * (
																				 * )
																				 */) {

			Iterator<CustomerSpouseEmploymentIncomeVO> it = loanAppFormVO
			        .getCustomerSpouseEmploymentIncome().iterator();
			while (it.hasNext()) {

				w2Icome = w2Icome
				        + Integer.parseInt(Utils.unformatCurrencyField((it
				                .next()).getCustomerSpouseEmploymentIncome()
				                .getEmployedIncomePreTax()));
			}

		}
		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getSelfEmployedIncome()
		        && !loanAppFormVO.getCustomerSpouseDetail()
		                .getSelfEmployedIncome().equalsIgnoreCase("")) {
			selfEmployedIncome = Integer
			        .parseInt(Utils.unformatCurrencyField(loanAppFormVO
			                .getCustomerSpouseDetail().getSelfEmployedIncome()));
		}

		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getChildSupportAlimony()
		        && !loanAppFormVO.getCustomerSpouseDetail()
		                .getChildSupportAlimony().equalsIgnoreCase("")) {
			chileSupport = Integer
			        .parseInt(Utils
			                .unformatCurrencyField(loanAppFormVO
			                        .getCustomerSpouseDetail()
			                        .getChildSupportAlimony()));
		}

		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getSocialSecurityIncome()
		        && !loanAppFormVO.getCustomerSpouseDetail()
		                .getSocialSecurityIncome().equalsIgnoreCase("")) {
			socialSecurityIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getCustomerSpouseDetail()
			                .getSocialSecurityIncome()));
		}

		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getDisabilityIncome()
		        && !loanAppFormVO.getCustomerSpouseDetail()
		                .getDisabilityIncome().equalsIgnoreCase("")) {
			disabilityIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getCustomerSpouseDetail().getDisabilityIncome()));
		}
		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getMonthlyPension()
		        && !loanAppFormVO.getCustomerSpouseDetail().getMonthlyPension()
		                .equalsIgnoreCase("")) {
			pensionIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getCustomerSpouseDetail().getMonthlyPension()));
		}

		if (null != loanAppFormVO.getCustomerSpouseDetail()
		        && null != loanAppFormVO.getCustomerSpouseDetail()
		                .getRetirementIncome()
		        && !loanAppFormVO.getCustomerSpouseDetail()
		                .getRetirementIncome().equalsIgnoreCase("")) {
			retirementIncome = Integer.parseInt(Utils
			        .unformatCurrencyField(loanAppFormVO
			                .getCustomerSpouseDetail().getRetirementIncome()));
		}

		coBorrowerBaseIncome = (w2Icome + selfEmployedIncome + chileSupport
		        + socialSecurityIncome + disabilityIncome + pensionIncome + retirementIncome)
		        + "";

		return coBorrowerBaseIncome;
	}

}
