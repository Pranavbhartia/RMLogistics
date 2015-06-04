package com.nexera.core.utility;

import java.util.HashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nexera.core.lqb.broker.LqbInvoker;

@Component
public class NexeraCacheableMethodInterfaceImpl implements
        NexeraCacheableMethodInterface {

	@Autowired
	private LqbInvoker lqbInvoker;

	@Value("${cryptic.key}")
	private String crypticKey;

	@Value("${muleUrlForLoan}")
	private String muleLoanUrl;

	@Autowired
	NexeraUtility nexeraUtility;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NexeraCacheableMethodInterfaceImpl.class);

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	@Override
	@Cacheable(value = "applicateRate")
	public HashMap<String, String> cacheInvokeRest(String loanNumber,
	        String appFormData) {
		HashMap<String, String> map = new HashMap<String, String>();
		LOGGER.info("Invoking rest Service with Input " + appFormData);
		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			String returnedResponse = restTemplate.postForObject(muleLoanUrl,
			        request, String.class);
			JSONObject jsonObject = new JSONObject(returnedResponse);
			LOGGER.info("Response Returned from Rest Service is"
			        + jsonObject.get("responseMessage").toString());
			// teaserRateList =
			// parseLqbResponse(jsonObject.get("responseMessage").toString());
			map.put("responseMessage", jsonObject.get("responseMessage")
			        .toString());
			map.put("responseTime", jsonObject.get("responseTime").toString());
			return map;
		} catch (Exception e) {

			LOGGER.debug("error in post entity. ", e);
			return null;
		}

	}

	@Override
	@CacheEvict(value = "applicateRate")
	public void invalidateApplicationRateCache(String loanNumber,
	        String appFormData) {
		LOGGER.info("Invalidatagin cache for loan: " + loanNumber);
	}
}
