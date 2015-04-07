package com.nexera.core.utility;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nexera.workflow.utils.Util;

@Component
public class GenerateDynamicString {
	public String generate(String initialString,Date startedDate) {

		while (initialString.indexOf("{") >= 0) {
			int startIndx = initialString.indexOf("{");
			int endIndx = initialString.indexOf("}") + 1;
			if (startedDate == null)
				startedDate = new Date();
			String subStr = initialString.substring(startIndx, endIndx);
			Map<String, Object> map = Util.convertJsonToMap(subStr);
			StringBuffer resultStr = new StringBuffer();
			for (String key : map.keySet()) {
				String type = map.get(key).toString();
				switch (type) {
				case "elapsed":
					Date dateNow = new Date();
					long elapsed = (dateNow.getTime() - startedDate.getTime())
							/ (1000 * 60 * 60);
					long timeLeft = Long.parseLong(key) - elapsed;
					resultStr.append(timeLeft);
					resultStr.append(" ");
					break;
				default:
					break;
				}
			}
			initialString = initialString.replace(subStr, resultStr.toString());
		}
		return initialString;
	}

	/*
	 * public static void main(String[] args) { String demoStr =
	 * "Disclosures are due in {\"72\":\"elapsed\"} hrs. {\"50\":\"elapsed\"}";
	 * 
	 * GenerateDynamicString dynamicStrGenerator = new GenerateDynamicString();
	 * dynamicStrGenerator.generate(demoStr, new Date()); }
	 */
}
