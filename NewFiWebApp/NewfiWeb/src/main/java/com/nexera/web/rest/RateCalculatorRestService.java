package com.nexera.web.rest;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

import com.google.gson.Gson;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.common.vo.lqb.TeaserRateVO;
import com.nexera.core.service.LqbInterface;
import com.nexera.core.service.StateLookupService;
import com.nexera.web.rest.util.TeaserRateHandler;

@RestController
@RequestMapping(value = "/calculator")
public class RateCalculatorRestService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(RateCalculatorRestService.class);

	@Value("${muleUrlForLoan}")
	private String muleLoanUrl;

	@Autowired
	LqbInterface lqbCacheInvoker;

	@Autowired
	StateLookupService stateLookupService;
	@RequestMapping(value = "/findteaseratevalue", method = RequestMethod.POST)
	public @ResponseBody String getTeaserRate(String teaseRate) {
		Gson gson = new Gson();
		String lockRateData = null;
		try {
			LOG.info("findteaseratevalue - inout xml is" + teaseRate);
			TeaserRateVO teaserRateVO = gson.fromJson(teaseRate,
			        TeaserRateVO.class);
			LOG.info("teaserRateVO" + teaserRateVO.getCity());
			LOG.info("teaserRateVO" + teaserRateVO.getCurrentAddress());
			LOG.info("teaserRateVO" + teaserRateVO.getCurrentMortgageBalance());
			LOG.info("teaserRateVO" + teaserRateVO.getCashTakeOut());
			LOG.info("teaserRateVO" + teaserRateVO.getCreditscore());

			// String requestXML = CreateXmlForTeaserRate(teaserRateVO);
			// LOG.info("Invoking rest service with with Json Input "+CreateTeaserRateJson(requestXML,"RunQuickPricer"));
			// List<TeaserRateResponseVO> teaserRateResponseVO =
			// invokeRest(CreateTeaserRateJson(requestXML,"RunQuickPricer").toString());
			JSONObject jsonObject = createMapforJson(teaserRateVO);
			String appFormData = CreateTeaserRateJson(jsonObject,
			        "RunQuickPricer").toString();
			try {
				HashMap<String, String> map = lqbCacheInvoker
				        .invokeRest(appFormData);
				String responseTime = map.get("responseTime");
				String lqbResponse = map.get("responseMessage");
				if (null != lqbResponse) {
					List<TeaserRateResponseVO> teaserRateList = parseLqbResponse(lqbResponse);
					if (teaserRateList != null) {
						for (TeaserRateResponseVO responseVo : teaserRateList) {
							responseVo.setResponseTime(responseTime);
						}
						lockRateData = gson.toJson(teaserRateList);
					} else {
						lockRateData = "error";
						lqbCacheInvoker.invalidateTeaserRateCache(appFormData);
					}

				} else {
					lockRateData = "error";
					lqbCacheInvoker.invalidateTeaserRateCache(appFormData);
				}
			} catch (Exception e) {
				LOG.error("Error in getTeaserRate", e);

				lockRateData = "error";
				lqbCacheInvoker.invalidateTeaserRateCache(appFormData);
			}

		} catch (Exception e) {
			LOG.error("Error in getTeaserRate", e);
			lockRateData = "error";

		}

		LOG.info("Json resonse returned to JSP is" + lockRateData);
		return lockRateData;
	}

	private JSONObject createMapforJson(TeaserRateVO teaserRateVO) {

		String loanAmount;
		String loanPurpose;
		String shopperCounty = "";
		String stateFromAPI = "";
		String sOccTPe = "";
		String subPropType = "";
		String loanType = "";

		if (teaserRateVO.getPropertyType() != null) {
			sOccTPe = teaserRateVO.getResidenceType();
		}

		if (teaserRateVO.getResidenceType() != null) {

			if ("1".equalsIgnoreCase(teaserRateVO.getPropertyType())) {

				subPropType = "2";
			} else if ("2".equalsIgnoreCase(teaserRateVO.getPropertyType())) {
				subPropType = "8";
			} else {
				subPropType = "0";
			}
		}

		if ("".equalsIgnoreCase(teaserRateVO.getCurrentMortgageBalance())
		        && "".equalsIgnoreCase(teaserRateVO.getCashTakeOut())) {

			loanAmount = "280000";
			LOG.info("setting hardcoded calue for loan amount ");
		}

		if ("REFCO".equalsIgnoreCase(teaserRateVO.getRefinanceOption())) {

			loanAmount = Integer.parseInt(unformatCurrencyField(teaserRateVO
			        .getCashTakeOut()))
			        + Integer.parseInt(unformatCurrencyField(teaserRateVO
			                .getCurrentMortgageBalance())) + "";
			LOG.info("Inside cash takeout , total loan amount is " + loanAmount);
		} else {
			loanAmount = unformatCurrencyField(teaserRateVO
			        .getCurrentMortgageBalance());
		}

		if (teaserRateVO.getLoanType() != null) {
			loanType = teaserRateVO.getLoanType();
			if ("PUR".equalsIgnoreCase(loanType)) {
				if (teaserRateVO.getPurchaseDetails() != null) {

					loanAmount = ""
					        + Integer
					                .parseInt(unformatCurrencyField(teaserRateVO
					                        .getPurchaseDetails()
					                        .getLoanAmount()));

				}

			}
		}

		if ("REFCO".equalsIgnoreCase(teaserRateVO.getRefinanceOption())) {

			loanPurpose = "2";
			LOG.info("Inside loan purpose " + loanPurpose);
		} else if ("REFLMP".equalsIgnoreCase(teaserRateVO.getRefinanceOption())
		        || "REFMF".equalsIgnoreCase(teaserRateVO.getRefinanceOption())) {
			loanPurpose = "1";
		} else {
			loanPurpose = "0";
			LOG.info("Inside loan purpose " + loanPurpose);
		}

		if ("".equalsIgnoreCase(teaserRateVO.getHomeWorthToday())) {
			LOG.info("Setting hardcoded homeworth to 350000 ");
			teaserRateVO.setHomeWorthToday("350000");
		}

		//JSONObject geoFromAPI = getStateUtlity(teaserRateVO.getZipCode());
		HashMap<String, String> stateLookup = stateLookupService.getZipCodeData(teaserRateVO.getZipCode());
		stateFromAPI = stateLookup.get("stateName");
		shopperCounty = stateLookup.get("countyName");
		
		/*try {

			stateFromAPI = geoFromAPI.getString("state");
			shopperCounty = geoFromAPI.getString("county");

			if ("".equalsIgnoreCase(stateFromAPI)) {
				stateFromAPI = "CA";
			}

			if ("".equalsIgnoreCase(shopperCounty)) {
				shopperCounty = "Santa Clara";
			}
		} catch (Exception e) {
			LOG.error("Error in createMapforJson", e);
		}*/

		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("homeWorthToday",
		        unformatCurrencyField(teaserRateVO.getHomeWorthToday()));
		hashmap.put("loanAmount", loanAmount);
		hashmap.put("stateFromAPI", stateFromAPI);
		hashmap.put("city", shopperCounty);
		hashmap.put("zipCode", teaserRateVO.getZipCode());
		hashmap.put("OccType", sOccTPe);
		hashmap.put("subPropType", subPropType);
		hashmap.put("loanPurpose", loanPurpose);

		JSONObject jsonObject = new JSONObject(hashmap);
		return jsonObject;
	}

	public JSONObject CreateTeaserRateJson(JSONObject jsonMapObject,
	        String opName) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put("sXmlDataMap", jsonMapObject);
			json.put("opName", opName);

			json.put("loanVO", jsonChild);

			LOG.debug("Json string is: ", json);
		} catch (JSONException e) {
			LOG.error("Error in CreateTeaserRateJson", e);
		}
		return json;
	}

	JSONObject getStateUtlity(String zipCode) {
		// String geoServiceAPI =
		// "http://www.webservicex.net/uszip.asmx/GetInfoByZIP?USZip="+zipCode;

		String geoServiceAPI = "http://zip.getziptastic.com/v2/US/" + zipCode;
		try {

			RestTemplate restTemplate = new RestTemplate();
			String geoResponse = restTemplate.getForObject(geoServiceAPI,
			        String.class);
			LOG.info("State Utility Response is " + geoResponse);
			// String state = parseXML(geoResponse);
			JSONObject json = new JSONObject(geoResponse);
			LOG.debug(json.get("county").toString());
			return json;
		} catch (Exception e) {
			LOG.error("Error in getStateUtlity", e);
		}

		return null;

	}

	public List<TeaserRateResponseVO> parseLqbResponse(
	        String lqbTeaserRateResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			TeaserRateHandler handler = new TeaserRateHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(lqbTeaserRateResponse)),
			        handler);
			List<TeaserRateResponseVO> teaserRateList = handler
			        .getTeaserRateList();
			for (TeaserRateResponseVO teaserRateResponseVO : teaserRateList) {
				LOG.info("Program Name "
				        + teaserRateResponseVO.getLoanDuration());

				Iterator<LqbTeaserRateVo> itr = teaserRateResponseVO
				        .getRateVO().iterator();
				while (itr.hasNext()) {
					LqbTeaserRateVo resultVo = itr.next();
					LOG.info("Teaser Rate " + resultVo.getTeaserRate()
					        + "  Closing Cost is " + resultVo.getClosingCost());
				}
				return teaserRateList;
			}

		} catch (SAXException se) {
			LOG.error("Error in parseLqbResponse", se);
		} catch (ParserConfigurationException pce) {
			LOG.error("Error in parseLqbResponse", pce);
		} catch (IOException ie) {
			LOG.error("Error in parseLqbResponse", ie);
		}

		return null;
	}

	private String parseXML(String xml) {
		String state = "";
		LOG.debug("xml is" + xml);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
			        .newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			// Load and Parse the XML document
			// document contains the complete XML as a Tree.
			Document document = builder.parse(new InputSource(new StringReader(
			        xml)));
			NodeList nodeList = document.getDocumentElement().getChildNodes();

			LOG.debug("document is" + document);
			LOG.debug("nodeList is" + nodeList);

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				// node.getAttributes().getNamedItem("STATE").getNodeValue();

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					LOG.debug("State : "
					        + eElement.getElementsByTagName("STATE").item(0)
					                .getTextContent());
					state = eElement.getElementsByTagName("STATE").item(0)
					        .getTextContent();
					// shopperCity =
					// eElement.getElementsByTagName("CITY").item(0).getTextContent();
				}

			}

		} catch (Exception e) {
			LOG.error("Error in parseXML", e);
		}

		return state;

	}

	private String unformatCurrencyField(String field) {
		if (null == field)
			return "";
		field = field.replaceAll("[$,]", "");
		return field;
	}

}
