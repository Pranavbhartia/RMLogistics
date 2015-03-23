package com.nexera.core.lqb.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LqbInvoker {

	private void invokeRest(String urlStr) {

		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException ex) {

			ex.printStackTrace();

		} catch (IOException ex2) {

			ex2.printStackTrace();

		}

	}

	private void invokeRestSpring(String urlStr) {

		System.out.println("Call from Spring rest invoker.");
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(urlStr, String.class);

		System.out.println("Response : " + response);

		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = gson.fromJson(response, stringStringMap);
		System.out.println("Response map : " + map);

	}

	private void invokeRestSpringParseObj(String urlStr) {

		System.out
				.println("Call from Spring rest invoker. Parsing into object.");
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(urlStr, String.class);

		System.out.println("Response : " + response);

		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> object = gson.fromJson(response, stringStringMap);
		System.out.println("Response  : " + object);

	}

	public static void main(String args[]) {

		System.out.println("Invoking rest...");

		LqbInvoker invoker=new LqbInvoker();
		//invoker.invokeRest("http://graph.facebook.com/balaji.mx");
		//LqbAuth.invokeRestSpring("http://graph.facebook.com/infosys");
		//invoker.invokeRestSpring("http://graph.facebook.com/balaji.mx");
		invoker.invokeRestSpringParseObj("http://graph.facebook.com/balaji.mx");

	}

}