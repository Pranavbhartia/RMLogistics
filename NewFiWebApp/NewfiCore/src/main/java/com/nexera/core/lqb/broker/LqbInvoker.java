package com.nexera.core.lqb.broker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

	@Value("${muleUrlForDocs}")
	private String muleUrlForDocs;

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

	public InputStream invokeRestSpringParseStream(String formData)
	        throws IOException {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();
		byte[] returnedUser = restTemplate.postForObject(muleUrlForDocs,
		        request, byte[].class);

		InputStream content = new ByteArrayInputStream(returnedUser);
		return content;
	}

}