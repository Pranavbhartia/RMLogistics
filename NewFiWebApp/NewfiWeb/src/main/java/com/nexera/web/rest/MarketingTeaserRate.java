package com.nexera.web.rest;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;
import com.nexera.common.vo.lqb.MarketingPageRateVo;
import com.nexera.common.vo.lqb.TeaserRateResponseVO;
import com.nexera.core.service.LqbInterface;
import com.nexera.web.rest.util.TeaserRateHandler;

@RestController
@RequestMapping(value = "/teaserRate")
public class MarketingTeaserRate {

	private static final Logger LOG = LoggerFactory
	        .getLogger(MarketingTeaserRate.class);

	@Autowired
	LqbInterface lqbCacheInvoker;

	@RequestMapping(value = "/marketingTeaserRate", method = RequestMethod.POST)
	public List<MarketingPageRateVo> findMarkeingTeaseRates(String teaserRate) {
		LOG.info(" Inside findMarkeingTeaseRates method");
		Gson gson = new Gson();
		String lockRateData = null;

		List<MarketingPageRateVo> marketingPageRateVo = null;
		try {
			HashMap<String, String> map = lqbCacheInvoker
			        .invokeRest(teaserRate);
			String responseTime = map.get("responseTime");
			String lqbResponse = map.get("responseMessage");

			if (null != lqbResponse) {
				List<TeaserRateResponseVO> teaserRateList = parseLqbResponse(lqbResponse);
				for (TeaserRateResponseVO responseVo : teaserRateList) {
					responseVo.setResponseTime(responseTime);
				}
				lockRateData = gson.toJson(teaserRateList);

				marketingPageRateVo = thirtyYearRateVoDataSet(lockRateData);
			} else {
				LOG.info("lqbResponse is not correct");
				lqbCacheInvoker.invalidateTeaserRateCache(teaserRate);
				lockRateData = "error";
			}
			return marketingPageRateVo;
		} catch (Exception e) {
			LOG.error("Error in retrieving market date", e);
			lqbCacheInvoker.invalidateTeaserRateCache(teaserRate);
		}
		return null;
	}

	private static JSONObject createRequestPayload() {

		LOG.info(" inside create Request Payload method");

		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();

		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("homeWorthToday", CommonConstants.HOMEWORTHTODAY);
		hashmap.put("loanAmount", CommonConstants.LOANAMOUNT);
		hashmap.put("stateFromAPI", CommonConstants.STATEFROMAPI);
		hashmap.put("city", CommonConstants.CITY);
		hashmap.put("zipCode", CommonConstants.ZIPCODE);
		hashmap.put("OccType", CommonConstants.OCCTYPE);
		hashmap.put("subPropType", CommonConstants.SUBPROPTYPE);
		hashmap.put("loanPurpose", CommonConstants.LOANPURPOSE);

		JSONObject jsonObject = new JSONObject(hashmap);

		try {
			jsonChild.put("sXmlDataMap", jsonObject);
			json.put("opName", "RunQuickPricer");
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {

			LOG.error("error while  inside create Request Payload method ", e);

		}

		return json;
	}

	public List<TeaserRateResponseVO> parseLqbResponse(
	        String lqbTeaserRateResponse) {
		LOG.info("Inside parseLqbResponse method ");

		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

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
			LOG.error(" error while parseLqbResponse", se);

		} catch (ParserConfigurationException pce) {
			LOG.error(" error while parseLqbResponse", pce);

		} catch (IOException ie) {
			LOG.error(" error while parseLqbResponse", ie);

		}

		return null;
	}

	public List<MarketingPageRateVo> thirtyYearRateVoDataSet(String lockRateData) {

		List<MarketingPageRateVo> marketingPageRatelist = new ArrayList<MarketingPageRateVo>();

		org.json.JSONObject thirtyYearRateVoDataSet = null;
		JSONArray jsonArray = new JSONArray(lockRateData);
		Gson gson = new Gson();

		for (int i = 0; i < jsonArray.length(); i++) {
			org.json.JSONObject item = jsonArray.getJSONObject(i);
			String loanDuration = item.getString("loanDuration");
			if (loanDuration.indexOf("30") == 0
			        || loanDuration.indexOf("15") == 0) {
				JSONArray rateVOArray = item.getJSONArray("rateVO");
				boolean found = false;
				for (int j = 0; j < rateVOArray.length(); j++) {
					thirtyYearRateVoDataSet = rateVOArray.getJSONObject(j);
					LqbTeaserRateVo LqbTeaserRateVo = gson.fromJson(
					        thirtyYearRateVoDataSet.toString(),
					        LqbTeaserRateVo.class);
					if (LqbTeaserRateVo.getClosingCost().equals("$0.00")
					        && (j + 1) < rateVOArray.length()) {
						thirtyYearRateVoDataSet = rateVOArray
						        .getJSONObject(j + 1);
						LqbTeaserRateVo = gson.fromJson(
						        thirtyYearRateVoDataSet.toString(),
						        LqbTeaserRateVo.class);
						MarketingPageRateVo marketingPageRateVo = new MarketingPageRateVo();
						marketingPageRateVo.setLoanDuration(loanDuration
						        .split(" ")[0] + "Year Fixed");
						marketingPageRateVo.setLqbTeaserRateVo(LqbTeaserRateVo);
						marketingPageRatelist.add(marketingPageRateVo);
						found = true;
						break;
					}
				}
				if (!found) {
					thirtyYearRateVoDataSet = rateVOArray
					        .getJSONObject(rateVOArray.length() / 2);
					LqbTeaserRateVo LqbTeaserRateVo = gson.fromJson(
					        thirtyYearRateVoDataSet.toString(),
					        LqbTeaserRateVo.class);
					MarketingPageRateVo marketingPageRateVo = new MarketingPageRateVo();
					marketingPageRateVo
					        .setLoanDuration(loanDuration.split(" ")[0]
					                + "Year Fixed");
					marketingPageRateVo.setLqbTeaserRateVo(LqbTeaserRateVo);
					marketingPageRatelist.add(marketingPageRateVo);
				}
			}

		}

		return marketingPageRatelist;
	}

}
