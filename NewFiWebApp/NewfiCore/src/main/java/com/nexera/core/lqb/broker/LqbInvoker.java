package com.nexera.core.lqb.broker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LqbInvoker {

	@Value("${muleUrlForLoan}")
	private String muleUrlForLoan;

	@Value("${muleUrlForDocs}")
	private String muleUrlForDocs;

	@Value("${muleUrlForAuth}")
	private String muleUrlForAuth;

	@Value("${muleUrlForAppView}")
	private String muleUrlForAppView;

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(LqbInvoker.class);

	public JSONObject invokeLqbService(String formData) {
		try {
			return invokeRestSpringParseObj(formData);
		} catch (Exception e) {
			return null;
		}
	}

	private JSONObject invokeRestSpringParseObj(String formData) {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();
		Date date = new Date();
		LOGGER.debug("Time taken before invoking this rest call for teaser rate is "
		        + date);
		String returnedUser = restTemplate.postForObject(muleUrlForLoan,
		        request, String.class);
		Date date1 = new Date();
		LOGGER.debug("Time taken before invoking this rest call for teaser rate is "
		        + date1);
		JSONObject jsonObject = new JSONObject(returnedUser);
		LOGGER.debug("jsonObject"
		        + jsonObject);
		return jsonObject;
	}

	public String invokeRestSpringParseObjForAuth(String formData) {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();

		String returnedUser = restTemplate.postForObject(muleUrlForAuth,
		        request, String.class);

		return returnedUser;
	}

	public JSONObject invokeRestSpringParseObjForAppView(String formData) {

		HttpHeaders headers = new HttpHeaders();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		HttpEntity request = new HttpEntity(formData, headers);
		RestTemplate restTemplate = new RestTemplate();

		String returnedUser = restTemplate.postForObject(muleUrlForAppView,
		        request, String.class);
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