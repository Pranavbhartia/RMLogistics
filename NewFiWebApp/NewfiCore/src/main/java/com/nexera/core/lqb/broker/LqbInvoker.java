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

	public JSONObject invokeLqbService(String formData) {
		return invokeRestSpringParseObj(formData);
	}

	private JSONObject invokeRestSpringParseObj(String formData) {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();
		String returnedUser = restTemplate.postForObject(muleUrl, request,
		        String.class);
		JSONObject jsonObject = new JSONObject(returnedUser);
		return jsonObject;
	}

}