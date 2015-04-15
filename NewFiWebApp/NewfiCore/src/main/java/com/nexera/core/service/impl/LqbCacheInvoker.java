package com.nexera.core.service.impl;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.googlecode.ehcache.annotations.Cacheable;
import com.nexera.core.service.LqbInterface;

@Component
public class LqbCacheInvoker implements LqbInterface {

	@Value("${muleUrlForLoan}")
	private String muleUrlForLoan;

	@Cacheable(cacheName = "teaserRate")
	@Override
	public String invokeRest(String appFormData) {

		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity request = new HttpEntity(appFormData, headers);
			RestTemplate restTemplate = new RestTemplate();
			String returnedUser = restTemplate.postForObject(muleUrlForLoan,
			        request, String.class);
			JSONObject jsonObject = new JSONObject(returnedUser);

			return jsonObject.get("responseMessage").toString();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error in post entity");

		}

		return null;
	}

}
