/**
 * 
 */
package com.nexera.workflow.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Utsav
 *
 */
public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	public static Map<String, Object> convertJsonToMap(String jsonString) {

		Gson gson = new Gson();
		LOGGER.debug("Inside method convertJsonToMap ");
		Map<String, Object> map = gson.fromJson(jsonString,
		        new TypeToken<HashMap<String, Object>>() {
		        }.getType());
		return map;
	}

}
