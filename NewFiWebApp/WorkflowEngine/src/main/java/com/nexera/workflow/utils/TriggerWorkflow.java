package com.nexera.workflow.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
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

public class TriggerWorkflow extends Thread {
	private final String USER_AGENT = "Mozilla/5.0";
	private HashMap<String, Object> params = new HashMap<String, Object>();
	private String url;
	private String appUrl;
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(TriggerWorkflow.class);

	private TriggerWorkflow(HashMap<String, Object> params, String url,
	        String appUrl) {
		this.params = params;
		this.url = url;
		this.appUrl = appUrl;
	}

	@Override
	public void run() {
		HttpClient client = new DefaultHttpClient();
		try {

			int wfID = Integer.parseInt(params.get("wfID").toString());
			String loanId = getLoanIdForWorkFlowExecId(wfID);
			if (loanId.equals("error"))
				return;
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("task", "milestone");
			map.put("taskId", loanId);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
				mapper.writeValue(sw, params);
			} catch (JsonGenerationException e) {
				LOGGER.error("Exception caught while triggering workflow change"
				        + e.getMessage());
			} catch (JsonMappingException e) {
				LOGGER.error("Exception caught while triggering workflow change "
				        + e.getMessage());
			} catch (IOException e) {
				LOGGER.error("Exception caught while triggering workflow change "
				        + e.getMessage());
			}
			map.put("data", sw.toString());

			HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("User-Agent", USER_AGENT);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			for (String key : map.keySet()) {
				urlParameters.add(new BasicNameValuePair(key, map.get(key)));
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
		} finally {
			client.getConnectionManager().closeExpiredConnections();
		}

	}

	public static void triggerMilestoneStatusChange(int milestoneId,
	        String status, String url, String appUrl) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("wfID", milestoneId);
		map.put("status", status);
		map.put("task", "milestone");
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			LOGGER.error("Exception caught while triggering workflow change "
			        + e.getMessage());
		} catch (JsonMappingException e) {
			LOGGER.error("Exception caught while triggering workflow change "
			        + e.getMessage());
		} catch (IOException e) {
			LOGGER.error("Exception caught while triggering workflow change "
			        + e.getMessage());
		}
		appUrl = appUrl + "rest/workflow/loan";
		TriggerWorkflow triggerWorkflow = new TriggerWorkflow(map, url, appUrl);
		triggerWorkflow.start();

	}

	// For Testing Purpose
	/*
	 * public static void main(String[] args) { TriggerWorkflow tWF = new
	 * TriggerWorkflow( new HashMap<String, Object>(), "",
	 * "http://localhost:8080/NewfiWeb/rest/workflow/loan");
	 * tWF.getLoanIdForWorkFlowExecId(112); }
	 */

	public String getLoanIdForWorkFlowExecId(int milestoneId) {

		StringBuffer outBuffer = new StringBuffer();
		HttpClient httpClient = new DefaultHttpClient();
		try {
			/*
			 * HttpGet httpGetRequest = new HttpGet(appUrl + milestoneId);
			 * HttpResponse httpResponse = httpClient.execute(httpGetRequest);
			 */

			HttpPost httpPostReq = new HttpPost(appUrl);
			httpPostReq.setHeader("contentType", "application/json");
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("milestoneID", milestoneId
			        + ""));
			httpPostReq.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse httpResponse = httpClient.execute(httpPostReq);

			LOGGER.debug("----------------------------------------");
			LOGGER.debug(httpResponse.getStatusLine().toString());
			LOGGER.debug("----------------------------------------");

			HttpEntity entity = httpResponse.getEntity();

			byte[] buffer = new byte[1024];
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					int bytesRead = 0;
					BufferedInputStream bis = new BufferedInputStream(
					        inputStream);
					while ((bytesRead = bis.read(buffer)) != -1) {
						String chunk = new String(buffer, 0, bytesRead);
						outBuffer.append(chunk);
						LOGGER.debug(chunk);
					}
				} catch (Exception e) {
					LOGGER.error("Error while triggering workflow change", e);
				} finally {
					try {
						inputStream.close();
					} catch (Exception ignore) {
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while triggering workflow change", e);
		} finally {
			httpClient.getConnectionManager().closeExpiredConnections();
		}
		return outBuffer.toString();
	}
}
