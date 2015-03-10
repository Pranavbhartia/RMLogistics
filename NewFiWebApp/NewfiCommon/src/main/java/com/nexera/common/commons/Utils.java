package com.nexera.common.commons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.nexera.common.entity.User;

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
		Date gmt = null;
		try {

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			gmt = new Date(date.getTime() + offset);
			return gmt;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Integer getOffsetFromUserObject() {
		
		if(SecurityContextHolder.getContext()==null || SecurityContextHolder.getContext()
				.getAuthentication()==null)
			return 0;
		
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal!=null && (principal instanceof User)) {
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

	//To be used by all modules to fetch the currently logged in user
	public User getLoggedInUser() {
		
		if(SecurityContextHolder.getContext()==null || SecurityContextHolder.getContext()
				.getAuthentication()==null)
			return null;
		
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal!=null && principal instanceof User) {
			User user = (User) principal;
			return user;
		}
		return null;
	}

}
