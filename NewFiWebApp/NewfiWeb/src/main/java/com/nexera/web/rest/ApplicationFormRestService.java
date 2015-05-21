package com.nexera.web.rest;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

import com.google.gson.Gson;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.entity.LoanAppForm;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.LoanLockRateVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.helper.MessageServiceHelper;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanAppFormService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.LqbInterface;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.rest.util.ApplicationPathUtil;
import com.nexera.web.rest.util.LQBRequestUtil;
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
	NexeraUtility nexeraUtility;

	@Value("${cryptic.key}")
	private String crypticKey;

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

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
			e.printStackTrace();
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

	@RequestMapping(value = "/createLoan", method = RequestMethod.POST)
	public @ResponseBody String createLoan(String appFormData,
	        HttpServletRequest httpServletRequest) {
		LOG.debug("Inside createLoan" + appFormData);
		Gson gson = new Gson();
		String lockRateData = null;
		LoanAppFormVO loaAppFormVO = null;
		try {
			loaAppFormVO = gson.fromJson(appFormData, LoanAppFormVO.class);
			LOG.debug("Getting token for loan manager");
			String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
			LOG.debug("Token retrieved for loan manager" + sTicket);
			String loanNumber = invokeRest((lQBRequestUtil
			        .prepareCreateLoanJson(
			                (CommonConstants.CREATET_LOAN_TEMPLATE), sTicket))
			        .toString());

			LOG.debug("createLoanResponse is" + loanNumber);

			if (!"".equalsIgnoreCase(loanNumber)) {

				String response = invokeRest((lQBRequestUtil.saveLoan(
				        loanNumber, loaAppFormVO, sTicket)).toString());
				LOG.debug("Save Loan Response is " + response);
				if (null != loaAppFormVO.getLoan()
				        && !loanNumber.equals("error")) {
					LoanVO loan = loaAppFormVO.getLoan();
					loan.setLqbFileId(loanNumber);
					String loanAppFrm = gson.toJson(loaAppFormVO);
					createApplication(loanAppFrm, httpServletRequest);
				}

				// Code for automating Needs List creation

				if (response != null && !response.equalsIgnoreCase("error")) {

					Integer loanId = loaAppFormVO.getLoan().getId();
					// needsListService.createInitilaNeedsList(loanId);
					userProfileService
					        .dismissAlert(
					                MilestoneNotificationTypes.COMPLETE_APPLICATION_NOTIFICATION_TYPE,
					                loanId,
					                WorkflowConstants.COMPLETE_YOUR_APPLICATION_NOTIFICATION_CONTENT);

					lockRateData = loadLoanRateData(loanNumber, sTicket);
					LOG.debug("lockRateData" + lockRateData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			lockRateData = "error";
		}
		if (lockRateData == null || lockRateData.equals("error")) {
			// code to send mail to user and loan manager
			if (loaAppFormVO != null && loaAppFormVO.getLoan() != null) {
				loanService.sendNoproductsAvailableEmail(loaAppFormVO.getLoan()
				        .getId());
				messageServiceHelper.generatePrivateMessage(loaAppFormVO
				        .getLoan().getId(), LoanStatus.ratesLocked, utils
				        .getLoggedInUser(), false);
			}
		}
		return lockRateData;

	}

	@RequestMapping(value = "/changeLoanAmount", method = RequestMethod.POST)
	public @ResponseBody String changeLoanAmount(String appFormData,
	        HttpServletRequest httpServletRequest) {
		Gson gson = new Gson();
		String lockRateData = null;
		LoanAppFormVO loaAppFormVO = null;
		try {
			loaAppFormVO = gson.fromJson(appFormData, LoanAppFormVO.class);

			String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
			String loanNumber = loaAppFormVO.getLoan().getLqbFileId();
			LOG.debug("createLoanResponse is" + loanNumber);
			if (!"".equalsIgnoreCase(loanNumber)) {

				String response = invokeRest((lQBRequestUtil.saveLoan(
				        loanNumber, loaAppFormVO, sTicket)).toString());
				LOG.debug("Save Loan Response is " + response);
				String loanAppFrm = gson.toJson(loaAppFormVO);
				createApplication(loanAppFrm, httpServletRequest);

				if (response != null) {
					lockRateData = loadLoanRateData(loanNumber, sTicket);
					LOG.debug("lockRateData" + lockRateData);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			lockRateData = "error";
		}
		if (lockRateData == null || lockRateData == "error") {
			// code to send mail to user and loan manager
			if (loaAppFormVO != null && loaAppFormVO.getLoan() != null) {
				loanService.sendNoproductsAvailableEmail(loaAppFormVO.getLoan()
				        .getId());
				messageServiceHelper.generatePrivateMessage(loaAppFormVO
				        .getLoan().getId(), LoanStatus.noProductFound, utils
				        .getLoggedInUser(), false);
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
			String sTicket = lqbCacheInvoker.findSticket(loaAppFormVO);
			lockRateData = loadLoanRateData(loanNumber, sTicket);
			LOG.debug("lockRateData" + lockRateData);

		} catch (Exception e) {
			e.printStackTrace();
			return "error";
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
				// loanService.updateLoan(loanLockRateVO.getLoanId(), true,
				// loanLockRateVO.getRateVo());
				loanService.sendRateLockRequested(loanLockRateVO.getLoanId(),
				        loanLockRateVO);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
			teaserRateList = rateService
			        .parseLqbResponse(retrievePricingDetails(invokeRest(json
			                .toString())));

			TeaserRateResponseVO teaserRateResponseVO = new TeaserRateResponseVO();
			teaserRateResponseVO.setLoanDuration("sample");
			teaserRateResponseVO.setLoanNumber(loanNumber);

			if (teaserRateList != null)
				teaserRateList.add(0, teaserRateResponseVO);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		LOG.debug("loadLoanRateData" + gson.toJson(teaserRateList));
		return gson.toJson(teaserRateList);

	}

	private String retrievePricingDetails(String xml) {
		String pricingResultXml = null;
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
			e.printStackTrace();
		}

		return pricingResultXml;

	}

	private String invokeRest(String appFormData) {

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
			// teaserRateList =
			// parseLqbResponse(jsonObject.get("responseMessage").toString());
			return jsonObject.get("responseMessage").toString();

		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug("error in post entity");
			return "error";
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
			e.printStackTrace();

		}

	}

}
