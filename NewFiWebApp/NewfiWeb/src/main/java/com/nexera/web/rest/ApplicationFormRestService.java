package com.nexera.web.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.amazonaws.services.ec2.model.transform.PurchaseReservedInstancesOfferingResultStaxUnmarshaller;
import com.google.gson.Gson;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanLockRateVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.lqb.CreditScoreResponseVO;
import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.LqbInterface;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.QuoteService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.CoreCommonConstants;
import com.nexera.core.utility.LoadXMLHandler;
import com.nexera.core.utility.NexeraCacheableMethodInterface;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.rest.util.ApplicationPathUtil;
import com.nexera.web.rest.util.GeneratePdfForQuickQuote;
import com.nexera.web.rest.util.LQBRequestUtil;
import com.nexera.web.rest.util.LQBResponseMapping;
import com.nexera.web.rest.util.PreQualificationletter;
import com.nexera.web.rest.util.GeneratePdfForQuickQuote;
import com.nexera.web.rest.util.RestUtil;

@RestController
@RequestMapping(value = "/application/")
public class ApplicationFormRestService {
	private static final String SESSION_CACHE_KEY = "Cache-key";
	private static final Logger LOG = LoggerFactory
	        .getLogger(ApplicationFormRestService.class);
	@Autowired
	private LoanAppFormService loanAppFormService;

	@Autowired
	private LoanService loanService;
	@Autowired
	private NeedsListService needsListService;
	@Autowired
	private MessageServiceHelper messageServiceHelper;
	@Value("${muleUrlForLoan}")
	private String muleLoanUrl;
	@Autowired
	private Utils utils;
	@Autowired
	private ApplicationPathUtil appPathUtil;
	@Autowired
	private LQBRequestUtil lQBRequestUtil;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private LqbInvoker lqbInvoker;

	@Autowired
	private LqbInterface lqbCacheInvoker;

	@Autowired
	private NexeraCacheableMethodInterface cacheableMethodInterface;

	@Autowired
	NexeraUtility nexeraUtility;

	@Autowired
	LQBResponseMapping lQBResponseMapping;

	@Value("${cryptic.key}")
	private String crypticKey;

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	@Autowired
	private PreQualificationletter preQualificationletter;
	
	@Autowired
	private GeneratePdfForQuickQuote generatePdfForQuickQuote;
	
	@Autowired
	private QuoteService quoteService;

	// @RequestBody
	@RequestMapping(value = "/applyloan", method = RequestMethod.POST)
	public @ResponseBody String createApplication(String appFormData,
	        HttpServletRequest httpServletRequest) {

		Gson gson = new Gson();

		try {
			LOG.debug("appFormData is" + appFormData);

			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);
			HashMap<String, Integer> cache = new HashMap<String, Integer>();

			HttpSession httpSession = httpServletRequest.getSession();
			if (httpSession != null) {
				Object map = httpSession.getAttribute(SESSION_CACHE_KEY);
				if (map == null) {
					httpSession.setAttribute(SESSION_CACHE_KEY,
					        new HashMap<String, Integer>());
					map = httpSession.getAttribute(SESSION_CACHE_KEY);
				}
				cache = (HashMap<String, Integer>) map;
			}

			LoanAppForm loanAppForm = loanAppFormService.create(appPathUtil
			        .setEntityIdFromCache(loaAppFormVO, cache));
			appPathUtil.setLocalCache(loanAppForm, cache);
			return new Gson().toJson(loaAppFormVO);

		} catch (Exception e) {
			LOG.debug("applyloan faile ", e);
		}
		// CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(null);
		return null;
	}

	@RequestMapping(value = "{appFormId}", method = RequestMethod.POST)
	public @ResponseBody String updateApplication(@PathVariable int appFormId,
	        @RequestBody String appFormData) {

		Gson gson = new Gson();

		LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
		        LoanAppFormVO.class);

		loaAppFormVO.setId(appFormId);
		loanAppFormService.save(loaAppFormVO);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(null);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/fetchLockRateData/{loanID}", method = RequestMethod.GET)
	public @ResponseBody String getLockRateData(@PathVariable int loanID,
	        HttpServletRequest httpServletRequest) {

		LOG.debug("Inside method getLockRateData for loan " + loanID);

		/*
		 * List<TeaserRateResponseVO> teaserRateList = new
		 * ArrayList<TeaserRateResponseVO>(); ArrayList<LqbTeaserRateVo>
		 * lqbTeaserRateVolist = new ArrayList<LqbTeaserRateVo>();
		 */
		Gson gson = new Gson();

		String status = null;

		LoanVO loanVO = loanService.getLoanByID(loanID);
		Loan loan = new LoanAppFormVO().parseVOtoEntityLoan(loanVO);
		LoanAppFormVO loaAppFormVO = loanAppFormService
		        .getLoanAppFormByLoan(loan);
		String lqbFileId = loanVO.getLqbFileId();

		if (loanVO != null && lqbFileId != null) {

			if (!loanVO.getLockStatus().equals("1")) {
				LOG.debug("loan not locked...");
				return gson.toJson(loaAppFormVO);
			}
			if (loaAppFormVO != null) {
				if (loanVO.getLqbFileId() != null) {
					LOG.debug("Getting token for loan manager");
					String sTicket = null;
					try {

						sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
					} catch (Exception e) {
						LOG.error("Exception caught while generating ticket "
						        + e.getMessage());
						status = "Unable to fetch authentication ticket, please try after sometime";
						sTicket = null;
					}
					if (sTicket != null) {
						LOG.debug("Token retrieved for loan manager" + sTicket);

						org.json.JSONObject loadOperationObject = nexeraUtility
						        .createLoadJson(lqbFileId, sTicket);
						if (loadOperationObject != null) {
							org.json.JSONObject loadJSONResponse = lqbInvoker
							        .invokeLqbService(loadOperationObject
							                .toString());
							if (loadJSONResponse != null) {
								if (!loadJSONResponse
								        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {

									String loadResponse = loadJSONResponse
									        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
									loadResponse = nexeraUtility
									        .removeBackSlashDelimiter(loadResponse);
									List<LoadResponseVO> loadResponseList = parseLqbResponse(loadResponse);

									if (loadResponseList != null) {
										LqbTeaserRateVo lqbTeaserRateVo = new LqbTeaserRateVo();
										for (LoadResponseVO loadResponseVO : loadResponseList) {
											lQBResponseMapping
											        .setLqbTeaserRateVo(
											                lqbTeaserRateVo,
											                loadResponseVO);
										}
										lqbTeaserRateVo
										        .setTeaserRate(loaAppFormVO
										                .getLoan()
										                .getLockedRate());
										loaAppFormVO
										        .getLoan()
										        .setLockedRateData(
										                new Gson()
										                        .toJson(lqbTeaserRateVo));
										String loanAppFrm = gson
										        .toJson(loaAppFormVO);
										loanAppFormService
										        .updatelockedLoanData(
										                loaAppFormVO.getLoan()
										                        .getId(),
										                loaAppFormVO
										                        .getLoan()
										                        .getLockedRateData());
									}
								}
							}
						} else {
							LOG.error("Unable to create request object ");
							status = "Unable to connect to lqb, please try after some time";
						}

					} else {
						LOG.error("Unable to get token for this loan " + loanID);
						status = "Unable to authenticate the user right now, please try again after sometime";
					}
				} else {
					LOG.error("This loan doesnt have an LQB ID");
					status = "Please complete your application first ";
				}
			} else {
				LOG.error("Loan Application info not found for this loanID "
				        + loanID);
				status = "Unable to fetch loan information";
			}

		} else {
			LOG.error("Loan not found for this loanID " + loanID);
			status = "Unable to fetch loan information";
		}

		return gson.toJson(loaAppFormVO);
	}

	@RequestMapping(value = "/pullScore/{loanID}/{trimerge}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getTrimergeScore(
	        @PathVariable int loanID, @PathVariable String trimerge) {
		LOG.debug("Inside pullTrimergeScore");
		boolean requestTrimerge = false;
		if (trimerge != null && trimerge.equalsIgnoreCase("Y")) {
			requestTrimerge = true;
		}
		String status = null;
		LoanVO loanVO = loanService.getLoanByID(loanID);
		if (loanVO != null) {
			Loan loan = new LoanAppFormVO().parseVOtoEntityLoan(loanVO);
			LoanAppFormVO loaAppFormVO = loanAppFormService
			        .getLoanAppFormByLoan(loan);

			if (loaAppFormVO != null) {
				if (loanVO.getLqbFileId() != null) {
					LOG.debug("Getting token for loan manager");
					String sTicket = null;
					try {
						sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
					} catch (Exception e) {
						LOG.error("Exception caught while generating ticket "
						        + e.getMessage());
						status = "Unable to fetch authentication ticket, please try after sometime";
						sTicket = null;
					}
					if (sTicket != null) {
						LOG.debug("Token retrieved for loan manager" + sTicket);
						Map<String, String> hashmap = new HashMap<String, String>();
						String userSSN = null;
						String reportId = null;
						String digitRegex = "\\d+";
						org.json.JSONObject loadOperationObject = nexeraUtility
						        .createLoadJsonObject(
						                hashmap,
						                WebServiceOperations.OP_NAME_LOAN_BATCH_LOAD,
						                loan.getLqbFileId(), 0, null);
						if (loadOperationObject != null) {
							org.json.JSONObject loadJSONResponse = lqbInvoker
							        .invokeLqbService(loadOperationObject
							                .toString());
							if (loadJSONResponse != null) {
								if (!loadJSONResponse
								        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {
									String loadResponse = loadJSONResponse
									        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
									loadResponse = nexeraUtility
									        .removeBackSlashDelimiter(loadResponse);

									List<LoadResponseVO> loadResponseList = parseLqbResponse(loadResponse);
									if (loadResponseList != null) {
										for (LoadResponseVO loadResponseVO : loadResponseList) {
											String fieldId = loadResponseVO
											        .getFieldId();
											if (fieldId
											        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_USER_SSN_NUMBER)) {
												if (loadResponseVO
												        .getFieldValue()
												        .contains("-")) {
													userSSN = loadResponseVO
													        .getFieldValue();
												}
											}
											if (fieldId
											        .equalsIgnoreCase(CoreCommonConstants.SOAP_XML_CREDIT_REPORT_FIELD)) {
												reportId = loadResponseVO
												        .getFieldValue();
											}
										}
									}
								}
							}
						}
						if (userSSN != null && requestTrimerge
						        && reportId == null) {
							status = "Unable to find credit report for this user, hence cannot upgrade. Please contact your System Admin ";
						} else if (userSSN != null) {
							JSONObject requestObject = lQBRequestUtil
							        .pullTrimergeCreditScore(
							                loanVO.getLqbFileId(),
							                loaAppFormVO, sTicket, userSSN,
							                reportId, requestTrimerge);
							if (requestObject != null) {
								String response = "error";
								HashMap<String, String> map = invokeRest(requestObject
								        .toString());
								if (map.get("responseMessage") != null) {
									response = map.get("responseMessage");
								} else if (map
								        .get(CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION) != null) {
									String errorDescription = CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION;
									if (errorDescription
									        .equalsIgnoreCase("Incoming portion of HTML stream")) {
										LOG.error("Issue occured again, hence generating fresh token");
										try {
											sTicket = lqbCacheInvoker
											        .findSticket(loaAppFormVO);
											String errorMessage = "Your token has expired, please try again ";
											CommonResponseVO responseVO = RestUtil
											        .wrapObjectForFailure(null,
											                "500", errorMessage);
											return responseVO;
										} catch (Exception e) {
											LOG.error("Unable to generate ticket "
											        + e.getMessage());
										}

									}
								}
								LOG.debug("Response for pulltrimergescore is "
								        + response);
								if (response != null
								        && !response.contains("error")) {

									org.json.JSONObject creditScoreJSONObject = nexeraUtility
									        .createCreditScoreJSONObject(
									                WebServiceOperations.OP_NAME_GET_CREDIT_SCORE,
									                sTicket,
									                loan.getLqbFileId(), 0);
									if (creditScoreJSONObject != null) {
										org.json.JSONObject creditScoreJSONResponse = lqbInvoker
										        .invokeLqbService(creditScoreJSONObject
										                .toString());
										if (creditScoreJSONResponse != null) {
											if (!creditScoreJSONResponse
											        .isNull(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)) {
												status = "Trimerge requested, your score would be updated shortly";
												String creditScores = creditScoreJSONResponse
												        .getString(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE);
												List<CreditScoreResponseVO> creditScoreResponseList = nexeraUtility
												        .parseCreditScoresResponse(creditScores);
												Map<String, String> creditScoreMap = nexeraUtility
												        .fillCreditScoresInMap(creditScoreResponseList);
												loanService
												        .saveCreditScoresForBorrower(
												                creditScoreMap,
												                loan, null);
												creditScoreMap.clear();
											} else {
												status = "Unable to authenticate user for this loan";
											}
										}
									}
								} else {
									status = "error while saving your request to pull trimerge score, please make sure your SSN is valid";
								}
							}
						} else if (userSSN == null) {
							status = "Unable to fetch user  ssn number from lqb ";
						}

					}
				} else {
					status = "LQB Information Not Present";
					LOG.error(status);
				}
			} else {
				status = "Unable to fetch the loanappform";
				LOG.error(status);
			}
		} else {
			status = "Unable to find the loan for this id";
			LOG.error(status);
		}
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(status);
		return responseVO;
	}

	@RequestMapping(value = "/createLoan", method = RequestMethod.POST)
	public @ResponseBody String createLoan(String appFormData,
	        HttpServletRequest httpServletRequest) {
		LOG.debug("Inside createLoan" + appFormData);
		Gson gson = new Gson();
		String lockRateData = "error";
		LoanAppFormVO loaAppFormVO = null;
		boolean loanCreatedNSaved = false;
		try {
			loaAppFormVO = gson.fromJson(appFormData, LoanAppFormVO.class);
			LOG.debug("Getting token for loan manager");
			String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
			LOG.debug("Token retrieved for loan manager" + sTicket);
			if (sTicket != null) {
				String loanNumber = null;
				HashMap<String, String> map = invokeRest((lQBRequestUtil
				        .prepareCreateLoanJson(
				                (CommonConstants.CREATET_LOAN_TEMPLATE),
				                sTicket)).toString());
				if (map.get("responseMessage") != null) {
					loanNumber = map.get("responseMessage");
				} else if (map
				        .get(CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION) != null) {
					String errorDescription = CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION;
					if (errorDescription
					        .equalsIgnoreCase("Incoming portion of HTML stream")) {
						LOG.error("Issue occured again, hence generating fresh token");
						sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
						return "error";// "Your token has expired, please try again ";

					}
				}

				LOG.debug("createLoanResponse is" + loanNumber);

				if (loanNumber != null && !"".equalsIgnoreCase(loanNumber)) {

					String response = null;
					map = invokeRest((lQBRequestUtil.saveLoan(loanNumber,
					        loaAppFormVO, sTicket)).toString());
					if (map.get("responseMessage") != null) {
						response = map.get("responseMessage");
					} else if (map
					        .get(CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION) != null) {
						String errorDescription = CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION;
						if (errorDescription
						        .equalsIgnoreCase("Incoming portion of HTML stream")) {
							LOG.error("Issue occured again, hence generating fresh token");
							sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
							return "error";// "Your token has expired, please try again ";

						}
					}
					LOG.debug("Save Loan Response is " + response);
					if (null != loaAppFormVO.getLoan()
					        && !loanNumber.equals("error") && !response.contains("status=\"Error\"")) {
						LoanVO loan = loaAppFormVO.getLoan();
						loan.setLqbFileId(loanNumber);
						String loanAppFrm = gson.toJson(loaAppFormVO);
						createApplication(loanAppFrm, httpServletRequest);
						loanCreatedNSaved = true;
					}
					if(response.contains("status=\"Error\"")){
						lockRateData = response;
					}

					// Code for automating Needs List creation

					if (response != null && !response.equalsIgnoreCase("error") && !response.contains("status=\"Error\"")) {
						try {
							lockRateData = null;
							Integer loanId = loaAppFormVO.getLoan().getId();
							// needsListService.createInitilaNeedsList(loanId);
							userProfileService
							        .dismissAlert(
							                MilestoneNotificationTypes.COMPLETE_APPLICATION_NOTIFICATION_TYPE,
							                loanId,
							                WorkflowConstants.COMPLETE_YOUR_APPLICATION_NOTIFICATION_CONTENT);

							lockRateData = loadLoanRateData(loanNumber, sTicket);
							LOG.debug("lockRateData" + lockRateData);

							// in case of Purchase send a mail with PDF
							// attachement
							// Comment Reason : NEXNF-407
							// if (null != loaAppFormVO.getLoanType()
							// && loaAppFormVO.getLoanType()
							// .getLoanTypeCd()
							// .equalsIgnoreCase("PUR")) {
							//
							// String thirtyYearRateVoDataSet =
							// preQualificationletter
							// .thirtyYearRateVoDataSet(lockRateData);
							// preQualificationletter
							// .sendPreQualificationletter(
							// loaAppFormVO,
							// thirtyYearRateVoDataSet,
							// httpServletRequest);
							//
							// }
						} catch (Exception e) {
							LOG.debug("Load rate data failed ", e);
							lockRateData = "";
						}
					}
				}
			} else {
				LOG.error("Unable to generate authentication token, please try after sometime");
			}
		} catch (Exception e) {
			LOG.error("lockRateData failed ", e);
			lockRateData = "error";
		}
	/*	if ((lockRateData == null || lockRateData.equals("error") || lockRateData
		        .equals("")) && loanCreatedNSaved) {
			// code to send mail to user and loan manager
			if (loaAppFormVO != null && loaAppFormVO.getLoan() != null) {
				loanService.sendApplicationSubmitConfirmationMail(loaAppFormVO.getLoan()
				        .getId());
				// messageServiceHelper.generatePrivateMessage(loaAppFormVO
				// .getLoan().getId(), LoanStatus.ratesLocked, utils
				// .getLoggedInUser(), false);
			}
		}*/
		boolean sendMailToLM = false;
		if (loanCreatedNSaved) {
			if ((lockRateData == null || lockRateData.equals("error") || lockRateData
			        .equals("")) && loanCreatedNSaved) {
				sendMailToLM = true;
			}
			loanService.sendApplicationSubmitConfirmationMail(loaAppFormVO.getLoan()
			        .getId(),sendMailToLM);
		}
		
		// if ((lockRateData == null || lockRateData.equals("error") ||
		// lockRateData
		// .equals("")) && loanCreatedNSaved) {
		// sendMailToLM = true;
		// }
		// if (loaAppFormVO != null && loaAppFormVO.getLoan() != null) {
		// loanService.sendApplicationSubmitConfirmationMail(loaAppFormVO.getLoan()
		// .getId(),sendMailToLM);
		// // messageServiceHelper.generatePrivateMessage(loaAppFormVO
		// // .getLoan().getId(), LoanStatus.ratesLocked, utils
		// // .getLoggedInUser(), false);
		// }
		return lockRateData;

	}

	@RequestMapping(value = "/sendPurchasePdfUnderQuickQuote", method = RequestMethod.POST)
	public CommonResponseVO sendPurchasePdfUnderQuickQuote(String loanPurchaseDetailsUnderQuickQuote,
			HttpServletRequest httpServletRequest) {

		Gson gson = new Gson();
		CommonResponseVO responseVO = null;
		GeneratePdfVO generatePdfVO = gson.fromJson(loanPurchaseDetailsUnderQuickQuote,
		        GeneratePdfVO.class);
		try {
			String s3Path = generatePdfForQuickQuote.sendPurchasePdf(generatePdfVO, httpServletRequest);
			if(s3Path != "" && s3Path != null){
				generatePdfVO.setPdfUrl(s3Path);
			}
				
			boolean status = quoteService.addQuoteDetails(generatePdfVO);
			if(!status)
				LOG.error("Error in inserting quick quote details: ");
			responseVO = RestUtil.wrapObjectForSuccess("success");
		} catch (Exception e) {

			LOG.error("Error in generating quote pdf or inserting quote details, under quick quote: ", e);
		}
		return responseVO;
	}
	
	@RequestMapping(value = "/sendPreQualiticationLatter", method = RequestMethod.POST)
	public CommonResponseVO sendPreQualificationLatter(String appFormData,
	        String rateDataSet, HttpServletRequest httpServletRequest) {

		Gson gson = new Gson();
		CommonResponseVO responseVO = null;
		LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
		        LoanAppFormVO.class);
		try {
			preQualificationletter.sendPreQualificationletter(loaAppFormVO,
			        rateDataSet, httpServletRequest);
			responseVO = RestUtil.wrapObjectForSuccess("success");
		} catch (Exception e) {

			LOG.error(" sendPreQualificationLatter failed", e);
		}
		return responseVO;
	}

	@RequestMapping(value = "/changeLoanAmount", method = RequestMethod.POST)
	public @ResponseBody String changeLoanAmount(String appFormData,
	        HttpServletRequest httpServletRequest) {
		Gson gson = new Gson();
		String lockRateData = null;
		LoanAppFormVO loaAppFormVO = null;
		try {
			loaAppFormVO = gson.fromJson(appFormData, LoanAppFormVO.class);
			String response = null;
			String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
			if (sTicket != null) {
				String loanNumber = loaAppFormVO.getLoan().getLqbFileId();
				LOG.debug("createLoanResponse is" + loanNumber);
				if (loanNumber != null && !"".equalsIgnoreCase(loanNumber)) {

					HashMap<String, String> map = invokeRest((lQBRequestUtil
					        .updateLoanAmount(loanNumber, loaAppFormVO, sTicket))
					        .toString());
					if (map.get("responseMessage") != null) {
						response = map.get("responseMessage");
						invalidateCache(loanNumber, sTicket);
					} else if (map
					        .get(CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION) != null) {
						String errorDescription = CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION;
						if (errorDescription
						        .equalsIgnoreCase("Incoming portion of HTML stream")) {
							LOG.error("Issue occured again, hence generating fresh token");
							sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
							return "Your token has expired, please try again ";

						}
					}

					LOG.debug("Save Loan Response is " + response);
					String loanAppFrm = gson.toJson(loaAppFormVO);
					createApplication(loanAppFrm, httpServletRequest);

					if (response != null) {
						lockRateData = loadLoanRateData(loanNumber, sTicket);
						LOG.debug("lockRateData" + lockRateData);
					} else {
						LOG.error("Unable to receive a valid response from LQB");
						response = "Unable to receive a valid response from LQB";
					}
				} else {
					response = "Unable to find lqbfileid for this loan";
					LOG.error("Unable to find lqbfileid for this loan");
				}
			} else {
				LOG.error("Unable to generate authentication ticket, please try after some time");
				response = "Unable to generate authentication ticket, please try after some time";
			}
		} catch (Exception e) {
			LOG.error(" changeLoanAmount failed", e);
			lockRateData = "error";
		}
		if (lockRateData == null || lockRateData == "error") {
			// code to send mail to user and loan manager
			if (loaAppFormVO != null && loaAppFormVO.getLoan() != null) {
				/*
				 * loanService.sendNoproductsAvailableEmail(loaAppFormVO.getLoan(
				 * ) .getId());
				 * messageServiceHelper.generatePrivateMessage(loaAppFormVO
				 * .getLoan().getId(), LoanStatus.noProductFound, utils
				 * .getLoggedInUser(), false);
				 */
			}
		}
		return lockRateData;

	}

	@RequestMapping(value = "/fetchLockRatedata/{loanNumber}", method = RequestMethod.POST)
	public @ResponseBody String fetchLockRatedata(
	        @PathVariable String loanNumber, String appFormData) {
		LOG.debug("Inside fetchLockRatedata loanNumber" + loanNumber);

		String lockRateData = null;
		Gson gson = new Gson();
		try {

			LOG.debug("appFormData is" + appFormData);
			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);

			int loanId = loaAppFormVO.getLoan().getId();
			LoanVO loanVO = loanService.getLoanByID(loanId);
			if (null != loanVO.getLockStatus()
			        && loanVO.getLockStatus().equalsIgnoreCase("1")) {
				/*
				 * this is the case when lock status is 1 in the loan table and
				 * we have to by pass the below request
				 */
				LOG.debug("Lock status is 1");

			} else {

				String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
				if (sTicket != null) {
					lockRateData = loadLoanRateData(loanNumber, sTicket);
					LOG.debug("lockRateData" + lockRateData);
				} else {
					LOG.error("Unable to generate authentication token, please try after some time");
					return "Unable to generate authentication toke, please try after some time";
				}
			}

		} catch (Exception e) {
			LOG.error(" fetchLockRatedata failed; " + loanNumber, e);
			return "error while fetching locked rate, please try after some time";
		}
		return lockRateData;

	}

	@RequestMapping(value = "/lockLoanRate", method = RequestMethod.POST)
	public @ResponseBody String lockLoanRateLoan(String appFormData) {
		LOG.debug("lockLoanRateLoan" + appFormData);
		Gson gson = new Gson();
		String lockRateData = "";
		try {
			LoanLockRateVO loanLockRateVO = gson.fromJson(appFormData,
			        LoanLockRateVO.class);
			LOG.debug("lockLoanRate is" + loanLockRateVO.getIlpTemplateId());
			// lockRateData =
			// invokeRest(lQBRequestUtil.prepareLockLoanRateJson(loanLockRateVO)
			// .toString());
			LOG.debug("lockLoanRate is" + lockRateData);
			if (!lockRateData.contains("status=\"Error\"")) {
				loanService.updateLoan(loanLockRateVO.getLoanId(), true,
				        loanLockRateVO.getRateVo());
				loanService.sendRateLockRequested(loanLockRateVO.getLoanId(),
				        loanLockRateVO);
			}

		} catch (Exception e) {
			LOG.error("lockLoanRateLoan failed", e);
			return "error";
		}
		return lockRateData;

	}

	private String loadLoanRateData(String loanNumber, String sTicket) {
		Gson gson = new Gson();
		List<TeaserRateResponseVO> teaserRateList = null;
		RateCalculatorRestService rateService = new RateCalculatorRestService();
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(CommonConstants.SLOANNUMBER, loanNumber);
			jsonChild.put(CommonConstants.SXMLQUERYMAP, new JSONObject("{}"));
			jsonChild.put(CommonConstants.FORMAT, 0);
			jsonChild.put(CommonConstants.STICKET, sTicket);

			json.put(CommonConstants.OPNAME, "Load");
			json.put(CommonConstants.LOANVO, jsonChild);
			LOG.debug("jsonMapObject load Loandata" + json);
			try {
				HashMap<String, String> lqbResponse = cacheableMethodInterface
				        .cacheInvokeRest(loanNumber, json.toString());
				String responseMessage = "error";

				if (lqbResponse != null
				        && lqbResponse.containsKey("responseMessage")) {
					responseMessage = lqbResponse.get("responseMessage");
				} else {
					LOG.debug("Invalidating cache since response contains error");
					cacheableMethodInterface.invalidateApplicationRateCache(
					        loanNumber, json.toString());
				}
				teaserRateList = rateService
				        .parseLqbResponse(retrievePricingDetails(responseMessage));

				String responseTime = "";
				if (lqbResponse != null
				        && lqbResponse.containsKey("responseTime")) {
					responseTime = lqbResponse.get("responseTime");
				}

				TeaserRateResponseVO teaserRateResponseVO = new TeaserRateResponseVO();
				teaserRateResponseVO.setLoanDuration("sample");
				teaserRateResponseVO.setLoanNumber(loanNumber);

				if (teaserRateList != null) {
					teaserRateList.add(0, teaserRateResponseVO);
					for (TeaserRateResponseVO responseVo : teaserRateList) {
						responseVo.setResponseTime(responseTime);
					}
				} else {
					LOG.warn("teaserRateList is null for: " + loanNumber);
					cacheableMethodInterface.invalidateApplicationRateCache(
					        loanNumber, json.toString());
				}

			} catch (Exception e) {
				LOG.error(
				        "Invalidating cache since there was a run time error",
				        e);
				cacheableMethodInterface.invalidateApplicationRateCache(
				        loanNumber, json.toString());
			}

		} catch (JSONException e) {
			LOG.error("JSON Exception for application rate of loanNumber: "
			        + loanNumber, e);
		}

		LOG.debug("loadLoanRateData" + gson.toJson(teaserRateList));
		return gson.toJson(teaserRateList);

	}

	private String invalidateCache(String loanNumber, String sTicket) {
		Gson gson = new Gson();
		List<TeaserRateResponseVO> teaserRateList = null;
		RateCalculatorRestService rateService = new RateCalculatorRestService();
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(CommonConstants.SLOANNUMBER, loanNumber);
			jsonChild.put(CommonConstants.SXMLQUERYMAP, new JSONObject("{}"));
			jsonChild.put(CommonConstants.FORMAT, 0);
			jsonChild.put(CommonConstants.STICKET, sTicket);

			json.put(CommonConstants.OPNAME, "Load");
			json.put(CommonConstants.LOANVO, jsonChild);
			LOG.debug("jsonMapObject load Loandata" + json);

			cacheableMethodInterface.invalidateApplicationRateCache(loanNumber,
			        json.toString());

		} catch (JSONException e) {
			LOG.error("JSON Exception for application rate of loanNumber: "
			        + loanNumber, e);
		}

		LOG.debug("loadLoanRateData" + gson.toJson(teaserRateList));
		return gson.toJson(teaserRateList);

	}

	private String retrievePricingDetails(String xml) {
		String pricingResultXml = null;
		LOG.debug("Inside retrievePricingDetails method with xml data value : "
		        + xml);

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
			        .newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
			        xml)));
			NodeList nodeList = document.getDocumentElement().getChildNodes();

			NodeList nList = document.getElementsByTagName("field");

			for (int j = 0; j < nList.getLength(); j++) {
				Node nNode = nList.item(j);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					if ("PricingResult".equalsIgnoreCase(eElement
					        .getAttribute("id"))) {

						if (eElement.getChildNodes().item(0) != null) {
							LOG.debug("Pricing result is"
							        + eElement.getChildNodes().item(0)
							                .getTextContent());
							pricingResultXml = eElement.getChildNodes().item(0)
							        .getTextContent();
							break;
						}

					}

				}
			}

		} catch (Exception e) {
			LOG.error("retrievePricingDetails failed", e);
		}

		return pricingResultXml;

	}

	private List<LoadResponseVO> parseLqbResponse(String loadResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			LoadXMLHandler handler = new LoadXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(loadResponse)), handler);
			return handler.getLoadResponseVOList();

		} catch (SAXException se) {
			LOG.error("Exception caught" + se.getMessage());
		} catch (ParserConfigurationException pce) {
			LOG.error("Exception caught" + pce.getMessage());
		} catch (IOException ie) {
			LOG.error("Exception caught" + ie.getMessage());
		}

		return null;
	}

	private HashMap<String, String> invokeRest(String appFormData) {
		HashMap<String, String> map = new HashMap<String, String>();
		LOG.info("Invoking rest Service with Input " + appFormData);
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			String returnedResponse = restTemplate.postForObject(muleLoanUrl,
			        request, String.class);
			JSONObject jsonObject = new JSONObject(returnedResponse);
			LOG.info("Response Returned from Rest Service is"
			        + jsonObject.get("responseMessage").toString());

			if (jsonObject.get(CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE) != null) {
				map.put("responseMessage",
				        jsonObject.get(
				                CoreCommonConstants.SOAP_XML_RESPONSE_MESSAGE)
				                .toString());
				map.put("responseTime",
				        jsonObject.get(
				                CoreCommonConstants.SOAP_XML_RESPONSE_TIME)
				                .toString());
			} else {
				String errorDescription = jsonObject.get(
				        CoreCommonConstants.SOAP_XML_ERROR_DESCRIPTION)
				        .toString();
				map.put("errorDescription", errorDescription);

			}
			return map;
		} catch (Exception e) {
			LOG.error("invokeRest failed", e);

			return null;
		}
	}

	@RequestMapping(value = "/savetaxandinsurance", method = RequestMethod.POST)
	public @ResponseBody void saveTaxAndInsurance(String appFormData,
	        HttpServletRequest httpServletRequest) {
		Gson gson = new Gson();

		try {
			LoanAppFormVO loaAppFormVO = gson.fromJson(appFormData,
			        LoanAppFormVO.class);

			String loanAppFrm = gson.toJson(loaAppFormVO);
			createApplication(loanAppFrm, httpServletRequest);

		} catch (Exception e) {
			LOG.error("saveTaxAndInsurance failed", e);

		}

	}

}
