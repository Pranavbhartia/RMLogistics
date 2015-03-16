/**
 * 
 */
package com.nexera.workflow.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Utsav
 *
 */
public class Util {

	private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

	public static Map<String, Object> convertJsonToMap(String jsonString) {
		if (jsonString != null) {
			LOGGER.debug("Inside method convertJsonToMap ");
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			HashMap<String, Object> map = null;
			try {
				map = mapper.readValue(jsonString, typeRef);
			} catch (JsonParseException e) {
				LOGGER.error("JsonParseException " + e.getMessage());
			} catch (JsonMappingException e) {
				LOGGER.error("JsonMappingException " + e.getMessage());
			} catch (IOException e) {
				LOGGER.error("IOException " + e.getMessage());
			}
			return map;
		}
		return null;
	}

}
