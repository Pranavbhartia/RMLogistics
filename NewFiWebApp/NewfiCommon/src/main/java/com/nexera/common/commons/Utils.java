package com.nexera.common.commons;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;

@Component
public class Utils {

	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

	private static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static String GMT = "GMT";
	static final long ONE_MINUTE_IN_MILLIS = 60000;

	/**
	 * Formatted string to be used for UI purpose. If input is null, the
	 * function returns an empty string to have a better display.
	 * 
	 * @param inputStr
	 * @return
	 */
	public String getDefaultString(String inputStr) {
		if (inputStr == null) {
			return "";
		}
		return inputStr;
	}

	public String getJsonStringOfMap(HashMap<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sw.toString();
	}

	public String getDateInUserLocaleFormatted(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		Date localeDate = null;
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone(GMT));

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);
			return dateFormat.format(localeDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date getDateInUserLocale(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		Date localeDate = null;
		try {

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);

			return localeDate;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date getUserDateInGMT(Date date) {
		Integer offSetFromUser = getOffsetFromUserObject();
		return generateDateUsingOffset(date, offSetFromUser);
	}

	public Date generateDateUsingOffset(Date date, Integer offset) {
		Date gmt = null;
		try {

			long offsetinMiliSec = offset * ONE_MINUTE_IN_MILLIS;
			gmt = new Date(date.getTime() + offsetinMiliSec);
			return gmt;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date getSystemDateInGMT(Date date) {
		Integer offSetFromSystem = new Date().getTimezoneOffset();
		return generateDateUsingOffset(date, offSetFromSystem);
	}

	private Integer getOffsetFromUserObject() {

		if (SecurityContextHolder.getContext() == null
		        || SecurityContextHolder.getContext().getAuthentication() == null)
			return 0;

		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal != null && (principal instanceof User)) {
			User user = (User) principal;
			if (user.getMinutesOffset() == null) {
				return 0;
			} else {
				return Integer.parseInt(user.getMinutesOffset());
			}
		} else {
			return 0;
		}

	}

	// To be used by all modules to fetch the currently logged in user
	public User getLoggedInUser() {

		if (SecurityContextHolder.getContext() == null
		        || SecurityContextHolder.getContext().getAuthentication() == null)
			return null;

		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal != null && principal instanceof User) {
			User user = (User) principal;
			return user;
		}
		return null;
	}

	public static String convertMapToJson(Map<String, Object> map) {
		Gson gson = new Gson();
		String jsonString = gson.toJson(map);
		return jsonString;
	}

	public String generateMessageIdFromAddress(String mongoMessageId, int loanId) {

		return MessageFormat.format("{0}-{1}{2}", mongoMessageId, loanId,
		        CommonConstants.SENDER_DOMAIN);
	}

	public String generateLoanEmail(int loanId) {
		return loanId + CommonConstants.SENDER_DOMAIN;
	}

	public UserRolesEnum getLoggedInUserRole() {
		User loggedInUser = getLoggedInUser();
		if (null != loggedInUser.getInternalUserDetail()) {

			return UserRolesEnum.valueOf(loggedInUser.getInternalUserDetail()
			        .getInternaUserRoleMaster().getRoleName());
		}
		return UserRolesEnum.valueOf(loggedInUser.getUserRole().getRoleCd());

	}

	public String constrtCreditScore(CustomerDetail customerDetail) {
		// TODO Auto-generated method stub

		String creditScore = "";
		String equifaxScore = customerDetail.getEquifaxScore();
		if (equifaxScore != null && !equifaxScore.isEmpty()) {
			creditScore = CommonConstants.EQ + equifaxScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = CommonConstants.EQ + CommonConstants.UNKNOWN_SCORE
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}
		String transunionScore = customerDetail.getTransunionScore();
		if (transunionScore != null && !transunionScore.isEmpty()) {
			creditScore = creditScore + CommonConstants.TU + transunionScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = creditScore + CommonConstants.TU
			        + CommonConstants.UNKNOWN_SCORE
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}

		String experianScore = customerDetail.getExperianScore();
		if (experianScore != null && !experianScore.isEmpty()) {
			creditScore = creditScore + CommonConstants.EX + experianScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = creditScore + CommonConstants.EX
			        + CommonConstants.UNKNOWN_SCORE
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}

		return creditScore;
	}
}
