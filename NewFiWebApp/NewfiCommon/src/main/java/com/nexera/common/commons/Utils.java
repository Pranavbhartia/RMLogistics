package com.nexera.common.commons;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
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

	@Value("${unprotected.urls}")
	private String unProtectedUrls;

	@Value("${restricted.folders}")
	private String restrictedFolders;

	@Value("${restricted.doc.types}")
	private String restrictedDocTypes;

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

	public static String unformatCurrencyField(String field) {
		String finalString = "";

		if (field == null) {
			return finalString;
		}
		if (field.contains("$") || field.contains(",")) {

			for (int i = 0; i < field.length(); i++) {
				if (field.charAt(i) != '$' && field.charAt(i) != ',')
					finalString += field.charAt(i);
			}
			return finalString;
		} else {
			return field;
		}

	}

	public String getJsonStringOfMap(Map<String, Object> map) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, map);
		} catch (JsonGenerationException e) {
			LOG.error("Exception Caught " + e.getMessage());
			return null;
		} catch (JsonMappingException e) {
			LOG.error("Exception Caught " + e.getMessage());
			return null;
		} catch (IOException e) {
			LOG.error("Exception Caught " + e.getMessage());
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
			LOG.error("Exception Caught " + e.getMessage());
		}
		return null;
	}

	public String getDateAndTimeForUserDashboard(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
		Date localeDate = null;
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone(GMT));

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);
			return dateFormat.format(localeDate);

		} catch (Exception e) {
			LOG.error("Exception Caught " + e.getMessage());
		}
		return null;

	}

	public String getDateAndTimeForUserManagement(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh:mm a");
		Date localeDate = null;
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone(GMT));

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);
			return dateFormat.format(localeDate);

		} catch (Exception e) {
			LOG.error("Exception Caught " + e.getMessage());
		}
		return null;

	}

	public String getMMDDForDisplay(Date date) {
		DateFormat format = new SimpleDateFormat("MM-dd");
		try {
			String stringdate = format.format(date);

			return stringdate;
		} catch (Exception e) {
			LOG.error("Exception Caught in getdatefordisplay method"
			        + e.getMessage());
		}
		return null;

	}

	public String getDateAndTimeForLQBUpdate(Date date) {

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		try {
			String stringdate = dateFormat.format(date);

			return stringdate;
		} catch (Exception e) {
			LOG.error("Exception Caught in getdatefordisplay method"
			        + e.getMessage());
		}
		return null;

	}

	public String getDateAndTimeForDisplay(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yy");
		Date localeDate = null;
		try {
			dateFormat.setTimeZone(TimeZone.getTimeZone(GMT));

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);
			return dateFormat.format(localeDate);

		} catch (Exception e) {
			LOG.error("Exception Caught " + e.getMessage());
		}
		return null;

	}

	public Date getDateInUserLocale(Date date) {

		Integer offSetFromUser = getOffsetFromUserObject();
		return getDateInUserLocale(date, offSetFromUser);
	}

	public Date getDateInUserLocale(Date date, Integer offSetFromUser) {
		if (offSetFromUser == null) {
			offSetFromUser = getOffsetFromUserObject();
		}

		Date localeDate = null;
		try {

			long offset = offSetFromUser * ONE_MINUTE_IN_MILLIS;
			localeDate = new Date(date.getTime() - offset);

			return localeDate;

		} catch (Exception e) {
			LOG.error("Exception Caught " + e.getMessage());
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
			LOG.error("Exception Caught " + e.getMessage());
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

	public boolean hasLinkExpired(Date inputTime, int rawOffSet) {

		Calendar tokenExpirationTime = Calendar.getInstance();
		tokenExpirationTime.setTimeInMillis(inputTime.getTime() + rawOffSet);
		tokenExpirationTime.add(Calendar.DAY_OF_MONTH, 3);
		Calendar today = Calendar.getInstance();
		today.setTimeInMillis(System.currentTimeMillis());
		LOG.debug("" + tokenExpirationTime.compareTo(today));
		LOG.debug("Has Token Expired" + tokenExpirationTime.before(today));
		return tokenExpirationTime.before(today);

	}

	public boolean hasTokenExpired(long tokenExpirationTime) {

		long currentExpirationTime = System.currentTimeMillis();
		long diffInMilliseconds = currentExpirationTime - tokenExpirationTime;
		if (diffInMilliseconds >= 14400000) {
			return true;
		} else {
			return false;
		}

	}

	public Date parseStringIntoDate(String dateTime) {
		Date date = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			date = simpleDateFormat.parse(dateTime);
		} catch (ParseException pe) {
			LOG.error("This format not supported for" + dateTime);
		}
		return date;
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

	public static Map<String, Object> convertJsonToMap(String jsonString) {
		if (jsonString != null) {
			LOG.debug("Inside method convertJsonToMap ");
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
			};
			HashMap<String, Object> map = null;
			try {
				map = mapper.readValue(jsonString, typeRef);
			} catch (JsonParseException e) {
				LOG.error("JsonParseException " + e.getMessage());
			} catch (JsonMappingException e) {
				LOG.error("JsonMappingException " + e.getMessage());
			} catch (IOException e) {
				LOG.error("IOException " + e.getMessage());
			}
			return map;
		}
		return null;
	}

	public String generateMessageIdFromAddress(String mongoMessageId, int loanId) {

		return MessageFormat.format("{0}-{1}{2}", mongoMessageId, loanId,
		        CommonConstants.SENDER_DOMAIN);
	}

	public String generateLoanEmail(String prefix) {
		return prefix + CommonConstants.SENDER_DOMAIN;
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

	public String constrtClickableCreditScore(CustomerDetail customerDetail,
	        int loanId) {
		// TODO Auto-generated method stub

		String creditScore = "";
		String equifaxScore = customerDetail.getEquifaxScore();
		if (equifaxScore != null && !equifaxScore.isEmpty()) {
			creditScore = CommonConstants.EQ + equifaxScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = "<span class='creditScoreClickableClass' loanId='"
			        + loanId + "'>" + CommonConstants.EQ
			        + CommonConstants.UNKNOWN_SCORE + "</span>"
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}
		String transunionScore = customerDetail.getTransunionScore();
		if (transunionScore != null && !transunionScore.isEmpty()) {
			creditScore = creditScore + CommonConstants.TU + transunionScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = creditScore
			        + "<span class='creditScoreClickableClass' loanId='"
			        + loanId + "'>" + CommonConstants.TU
			        + CommonConstants.UNKNOWN_SCORE + "</span>"
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}

		String experianScore = customerDetail.getExperianScore();
		if (experianScore != null && !experianScore.isEmpty()) {
			creditScore = creditScore + CommonConstants.EX + experianScore
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		} else {
			creditScore = creditScore
			        + "<span class='creditScoreClickableClass' loanId='"
			        + loanId + "'>" + CommonConstants.EX
			        + CommonConstants.UNKNOWN_SCORE + "</span>"
			        + CommonConstants.CREDIT_SCORE_SEPARATOR;
		}

		return creditScore;
	}

	public List<String> getUnprotectedUrls() {

		String[] unprotectedUrlsArray = unProtectedUrls.split(",");

		List<String> unprotectedUrls = new ArrayList<String>();
		for (String url : unprotectedUrlsArray) {
			unprotectedUrls.add(url);
		}
		return unprotectedUrls;
	}

	public List<String> getRestrictedFolders() {

		String[] restrictedFoldersArray = restrictedFolders.split(",");

		List<String> restrictedFolderList = new ArrayList<String>();
		for (String folderName : restrictedFoldersArray) {
			restrictedFolderList.add(folderName.toUpperCase());
		}
		return restrictedFolderList;
	}

	public List<String> getRestrictedDocTypes() {

		String[] restrictedDocTypesArray = restrictedDocTypes.split(",");

		List<String> restrictedDocTypes = new ArrayList<String>();
		for (String docType : restrictedDocTypesArray) {
			restrictedDocTypes.add(docType.toUpperCase());
		}
		return restrictedDocTypes;
	}

	/*
	 * public static void main(String[] args) { String a ="$100,2313";
	 * BigDecimal myBigNum = new BigDecimal(unformatCurrencyField(a));
	 * System.out.println(myBigNum);
	 * 
	 * }
	 */

	public static BigDecimal convertToBigDecimal(String input) {

		if (null != input) {
			BigDecimal myBigNum = new BigDecimal(unformatCurrencyField(input));
			return myBigNum;

		}

		return null;

	}

	public static Integer convertToInteger(String input) {

		if (null != input) {
			Integer myInt = Integer.parseInt(input);
			return myInt;

		}

		return null;

	}

	public static String convertToString(Integer input) {

		if (null != input) {
			return String.valueOf(input);
		}

		return null;
	}

	public static String convertToString(BigDecimal input) {

		if (null != input) {
			return String.valueOf(input);
		}

		return null;
	}

	public String getTimeInPST() {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
		        DateFormat.FULL);
		df.setTimeZone(TimeZone.getTimeZone("PST"));
		final String dateTimeString = df.format(new Date());
		return dateTimeString;
	}

	public String getTimeInUTC(long milliSeconds) {
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
		        DateFormat.FULL);
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		final String dateTimeString = df.format(new Date(milliSeconds));
		return dateTimeString;
	}

	public Date convertCurrentDateToUtc() {
		Date date = new Date();
		TimeZone timeZone = TimeZone.getDefault();
		int offset = timeZone.getOffset(date.getTime());
		long utcMilliSeconds = date.getTime() - offset;
		Date utcDate = new Date(utcMilliSeconds);
		return utcDate;
	}

	public String getFileUrl(String baseUrl, String uuID) {
		String fileURL = baseUrl + "readFileAsStream.do?uuid=" + uuID
		        + "&isThumb=0";
		return fileURL;
	}

	public BufferedImage resizeImage(BufferedImage originalImage, int type,
	        int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}

	public void resize(String inputImagePath, String outputImagePath,
	        int scaledWidth, int scaledHeight) throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth,
		        scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath
		        .lastIndexOf(".") + 1);

		// writes to output file
		ImageIO.write(outputImage, formatName, new File(outputImagePath));
	}

	public BufferedImage cropMyImage(BufferedImage img, int cropWidth,
	        int cropHeight, int cropStartX, int cropStartY) throws Exception {
		BufferedImage clipped = null;
		Dimension size = new Dimension(cropWidth, cropHeight);

		boolean isClipAreaAdjusted = false;
		Rectangle clip;

		/** Checking for negative X Co-ordinate **/
		if (cropStartX < 0) {
			cropStartX = 0;
			isClipAreaAdjusted = true;
		}
		/** Checking for negative Y Co-ordinate **/
		if (cropStartY < 0) {
			cropStartY = 0;
			isClipAreaAdjusted = true;
		}

		/**
		 * Checking if <span id="IL_AD1" class="IL_AD">the clip</span> area lies
		 * outside the rectangle
		 **/
		if ((size.width + cropStartX) <= img.getWidth()
		        && (size.height + cropStartY) <= img.getHeight()) {

			/**
			 * Setting up a clip rectangle when clip area lies within the image.
			 */

			clip = new Rectangle(size);
			clip.x = cropStartX;
			clip.y = cropStartY;
		} else {

			/**
			 * Checking if the width of the clip area lies outside the image. If
			 * so, making the image width boundary as the clip width.
			 */
			if ((size.width + cropStartX) > img.getWidth())
				size.width = img.getWidth() - cropStartX;

			/**
			 * Checking if the height of the clip area lies outside the image.
			 * If so, making the image height boundary as the clip height.
			 */
			if ((size.height + cropStartY) > img.getHeight())
				size.height = img.getHeight() - cropStartY;

			/** Setting up the clip are based on our clip area size adjustment **/
			clip = new Rectangle(size);
			clip.x = cropStartX;
			clip.y = cropStartY;

			isClipAreaAdjusted = true;

		}
		if (isClipAreaAdjusted)
			System.out.println("Crop Area Lied Outside The Image."
			        + " Adjusted The Clip Rectangle\n");

		try {
			int w = clip.width;
			int h = clip.height;

			System.out.println("Crop Width " + w);
			System.out.println("Crop Height " + h);
			System.out.println("Crop Location " + "(" + clip.x + "," + clip.y
			        + ")");

			clipped = img.getSubimage(clip.x, clip.y, w, h);

			System.out.println("Image Cropped. New Image Dimension: "
			        + clipped.getWidth() + "w X " + clipped.getHeight() + "h");
		} catch (RasterFormatException rfe) {
			System.out.println("Raster format error: " + rfe.getMessage());
			return null;
		}
		return clipped;
	}

	private static void createClip(BufferedImage img, Dimension size,
	        int cropStartX, int cropStartY) throws Exception {
		/**
		 * Some times clip area might lie outside the original image, fully or
		 * partially. In such cases, this program will adjust the crop area to
		 * fit within the original image.
		 *
		 * isClipAreaAdjusted flas is usded to denote if there was any
		 * adjustment made.
		 */
	}

	public int randomNumber() {
		// TODO Auto-generated method stub
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(1000);
	}
	
	public static String phoneNumberFormating(String phoneNumber) {
		if(phoneNumber == null || phoneNumber == ""){
			return "N/A";
		}else {
			return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
		}
		
	}
}