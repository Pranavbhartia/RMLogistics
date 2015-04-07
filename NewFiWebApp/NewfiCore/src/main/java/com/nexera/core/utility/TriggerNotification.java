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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexera.common.vo.NotificationVO;



public class TriggerNotification extends Thread {
	private final String USER_AGENT = "Mozilla/5.0";
	private HashMap<String, String> params = new HashMap<String, String>();
	private String url = "http://localhost:8080/PushNotification/pushServlet/";
	public static void main(String[] args) {
		NotificationVO notificationVo = new NotificationVO();
		notificationVo.setId(2);
		notificationVo.setLoanID(1);
		TriggerNotification.triggerDismissNotofication(notificationVo);
		// TriggerNotification triggernotification = new TriggerNotification();
		// triggernotification.start();
	}

	private TriggerNotification(HashMap<String, String> params) {
		this.params = params;
	}
	public void run() {
		// String url = "http://localhost:8080/PushNotification/pushServlet/";
		// String urlParameters = "task=notification&taskId=1&data=tstin";
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
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + post.getEntity());
			System.out.println("Response Code : "
					+ response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void triggerNewNotofication(NotificationVO notificationVo) {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("action", "new");
		map.put("data", notificationVo);
		ObjectMapper mapper=new ObjectMapper();
		StringWriter sw=new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
				params);
		triggerNotification.start();

	}

	public static void triggerDismissNotofication(NotificationVO notificationVo) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
				params);
		triggerNotification.start();

	}

	public static void triggerSnoozeNotofication(NotificationVO notificationVo) {

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("task", "notification");
		params.put("taskId", notificationVo.getLoanID() + "");
		params.put("data", sw.toString());
		TriggerNotification triggerNotification = new TriggerNotification(
				params);
		triggerNotification.start();

	}
}
