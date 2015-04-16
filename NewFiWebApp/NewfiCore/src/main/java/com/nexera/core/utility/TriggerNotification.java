package com.nexera.core.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.vo.NotificationVO;

public class TriggerNotification extends Thread {
	private final String USER_AGENT = "Mozilla/5.0";
	private HashMap<String, String> params = new HashMap<String, String>();
	private String url;
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TriggerNotification.class);

	private TriggerNotification(HashMap<String, String> params, String url) {
		this.params = params;
		this.url = url;
	}

	@Override
	public void run() {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("User-Agent", USER_AGENT);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				urlParameters.add(new BasicNameValuePair(key, params.get(key)));
			}
			post.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse response = client.execute(post);

			LOGGER.debug("Post parameters : " + post.getEntity());
			LOGGER.debug("Response Code : "
			        + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
			        response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			LOGGER.debug("Result is " + result.toString());
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (ClientProtocolException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}

	}

	public static void triggerNewNotofication(NotificationVO notificationVo,
	        String url) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("action", "new");
		map.put("data", notificationVo);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
		        params, url);
		triggerNotification.start();

	}

	public static void triggerDismissNotofication(
	        NotificationVO notificationVo, String url) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("action", "remove");
		map.put("notificationID", "LNDI" + notificationVo.getId());
		map.put("id", notificationVo.getId());
		map.put("loanId", notificationVo.getLoanID());
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
		        params, url);
		triggerNotification.start();

	}

	public static void triggerSnoozeNotofication(NotificationVO notificationVo,
	        String url) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("action", "snooze");
		map.put("notificationID", "LNDI" + notificationVo.getId());
		map.put("id", notificationVo.getId());
		map.put("loanId", notificationVo.getLoanID());
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught " + e.getMessage());
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
		        params, url);
		triggerNotification.start();

	}
}
