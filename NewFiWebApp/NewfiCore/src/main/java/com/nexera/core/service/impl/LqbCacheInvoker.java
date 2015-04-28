package com.nexera.core.service.impl;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nexera.core.service.LqbInterface;

@Component
public class LqbCacheInvoker implements LqbInterface {

	@Value("${muleUrlForLoan}")
	private String muleUrlForLoan;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LqbCacheInvoker.class);

	// @Cacheable(cacheName = "teaserRate")
	@Override
	public String invokeRest(String appFormData) {

		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			Date date = new Date();
			LOGGER.debug("Time taken before invoking this rest call for teaser rate is "
			        + date);
			String returnedUser = restTemplate.postForObject(muleUrlForLoan,
			        request, String.class);
			Date date1 = new Date();
			LOGGER.debug("Time taken after invoking this rest call for teaser rate is "
			        + date1);
			JSONObject jsonObject = new JSONObject(returnedUser);

			return jsonObject.get("responseMessage").toString();

		} catch (Exception e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}

		return null;
	}

}
