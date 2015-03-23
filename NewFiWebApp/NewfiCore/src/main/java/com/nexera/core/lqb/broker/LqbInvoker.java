package com.nexera.core.lqb.broker;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LqbInvoker {
	
	@Value("${muleUrl}")
	private String muleUrl;

	public JSONObject invokeLqbService(String serviceEndpoint, String formData) {
		return invokeRestSpringParseObj(serviceEndpoint, formData);
	}

	private JSONObject invokeRestSpringParseObj(String serviceEndpoint, String formData) {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings("rawtypes")
        HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();
		String returnedUser = restTemplate.postForObject(
				muleUrl+serviceEndpoint, request, String.class);
		JSONObject jsonObject = new JSONObject(returnedUser);
		return jsonObject;
	}

}