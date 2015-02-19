package com.nexera.common.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	
	/**
	 * Formatted string to be used for UI purpose. If input is null, the function returns an empty string 
	 * to have a better display.
	 * @param inputStr
	 * @return
	 */
	public String getDefaultString(String inputStr){
		if(inputStr==null){
			return "";
		}
		return inputStr;
	}
	
}
