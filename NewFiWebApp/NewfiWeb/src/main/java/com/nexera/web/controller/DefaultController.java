package com.nexera.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.amazonaws.util.json.JSONException;
import com.google.gson.Gson;
import com.google.gson.JsonSerializer;
import com.mongodb.util.JSON;
import com.nexera.common.commons.PropertyFileReader;
import com.nexera.common.commons.Utils;
import com.nexera.common.entity.User;
import com.nexera.common.vo.UserVO;

@Controller
public class DefaultController {

	@Autowired
	protected Utils utils;

	private static final Logger LOG = LoggerFactory
			.getLogger(DefaultController.class);

	// Contains the lookup for all the key value pairs to be used in UI for
	// internationalization purpose.
	protected HashMap<String, String> propMap = null;

	protected HashMap<String, HashMap<String, String>> languageMap = new HashMap<String, HashMap<String, String>>();

	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}

	/**
	 * Loads all the default elements required for the UI. Common method to be
	 * called from all controllers
	 * 
	 * @param model
	 * @param req
	 * @throws JSONException
	 * @throws IOException
	 */
	public User loadDefaultValues(Model model, HttpServletRequest req)
			throws IOException {
		User user = getUserObject();
		JSONObject newfi = new JSONObject();

		try {

			Locale locale = req.getLocale();
			String suffix = locale.toString();

			if (!languageMap.containsKey(suffix)) {
				loadLanguageMap(suffix);
			}
			UserVO userVO = new UserVO();
			userVO.setForView(user);
		
			Gson gson = new Gson();
			
			newfi.put("user",gson.toJson(userVO));
		
			newfi.put("i18n", new JSONObject(languageMap.get(suffix)));
		} catch (org.codehaus.jettison.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute(
				"username",
				utils.getDefaultString(user.getFirstName()) + " "
						+ utils.getDefaultString(user.getLastName()));

		model.addAttribute("newfi", newfi);
		return user;
	}

	private void loadLanguageMap(String suffix) throws IOException {
		// TODO Auto-generated method stub

		Properties properties = new Properties();
		properties.load(PropertyFileReader.class.getClassLoader()
				.getResourceAsStream("messages_" + suffix + ".properties"));
		languageMap.put(suffix, new HashMap<String, String>((Map) properties));

	}
}
