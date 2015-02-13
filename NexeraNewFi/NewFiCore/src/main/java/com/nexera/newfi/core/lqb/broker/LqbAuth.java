package com.nexera.newfi.core.lqb.broker;

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

public class LqbAuth {

	public static void invokeRest(String urlStr) {

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

	public static void invokeRestSpring(String urlStr) {

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

	public static void invokeRestSpringParseObj(String urlStr) {

		System.out
				.println("Call from Spring rest invoker. Parsing into object.");
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(urlStr, String.class);

		System.out.println("Response : " + response);

		Gson gson = new Gson();

		GraphObject object = gson.fromJson(response, GraphObject.class);
		System.out.println("Response  : " + object);

	}

	public static void main(String args[]) {

		System.out.println("Invoking rest...");

		LqbAuth.invokeRest("http://graph.facebook.com/balaji.mx");
		//LqbAuth.invokeRestSpring("http://graph.facebook.com/infosys");
		LqbAuth.invokeRestSpring("http://graph.facebook.com/balaji.mx");
		LqbAuth.invokeRestSpringParseObj("http://graph.facebook.com/balaji.mx");

	}

}

class GraphObject {
	private String id;

	private String first_name;

	private String username;

	private String name;

	private String locale;

	private String link;

	private String last_name;

	private String gender;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "GraphObject [id=" + id + ", first_name=" + first_name
				+ ", username=" + username + ", name=" + name + ", locale="
				+ locale + ", link=" + link + ", last_name=" + last_name
				+ ", gender=" + gender + "]";
	}

}