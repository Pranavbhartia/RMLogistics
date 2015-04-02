package com.nexera.core.lqb.broker;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
		((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory())
		        .setConnectTimeout(1000 * 30);
		((HttpComponentsClientHttpRequestFactory) restTemplate
		        .getRequestFactory()).setReadTimeout(300 * 1000);
		String returnedUser = restTemplate.postForObject(muleUrl, request,
		        String.class);
		JSONObject jsonObject = new JSONObject(returnedUser);
		return jsonObject;
	}

}