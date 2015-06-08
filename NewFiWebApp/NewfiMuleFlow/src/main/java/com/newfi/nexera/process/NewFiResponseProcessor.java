/**
 * 
 */
package com.newfi.nexera.process;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mule.api.MuleEventContext;
import org.mule.api.MuleMessage;
import org.mule.api.lifecycle.Callable;

import com.google.gson.Gson;
import com.newfi.nexera.vo.ResponseVO;

/**
 * @author Utsav
 *
 */
public class NewFiResponseProcessor implements Callable {

	private static final Logger LOG = Logger
	        .getLogger(NewFiResponseProcessor.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.api.lifecycle.Callable#onCall(org.mule.api.MuleEventContext)
	 */
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		LOG.info("Inside method onCall");
		Gson gson = new Gson();
		MuleMessage message = eventContext.getMessage();
		String payload = message.getPayloadAsString();
		ResponseVO responseVO = new ResponseVO();
		responseVO.setResponseCode("200");
		responseVO.setStatus("0");
		responseVO.setResponseTime(System.currentTimeMillis() + "");
		LOG.info("VALID RESPONSE RECEIVED FROM LQB ");
		responseVO.setResponseMessage(payload);
		String jsonString = gson.toJson(responseVO);
		jsonString = removeUTFCharacters(jsonString);
		message.setPayload(jsonString);
		return message;
	}

	public String getFormattedDate() {
		String dateFormat = "MM/dd/YYYY hh:mm a";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		Date date = new Date();
		String stringDate = simpleDateFormat.format(date);
		return stringDate;

	}

	public static String removeUTFCharacters(String data) {
		Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
		Matcher m = p.matcher(data);
		StringBuffer buf = new StringBuffer(data.length());
		while (m.find()) {
			String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
			m.appendReplacement(buf, Matcher.quoteReplacement(ch));
		}
		m.appendTail(buf);
		return buf.toString();
	}

}
