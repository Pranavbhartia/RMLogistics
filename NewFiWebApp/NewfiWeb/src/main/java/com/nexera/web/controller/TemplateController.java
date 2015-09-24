package com.nexera.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.util.Base64;
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.constants.JspLookup;

@Controller
public class TemplateController extends DefaultController {

	@Autowired
	LoanService loanService;

	@Autowired
	UserProfileService userProfileService;

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Value("${profile.url}")
	private String baseUrl;

	private static final Logger LOG = LoggerFactory
	        .getLogger(TemplateController.class);

	@RequestMapping(value = "calculator.do")
	public ModelAndView showCustomerPage1(HttpServletRequest req, Model model) {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("calculator");
		return mav;
	}

	@RequestMapping(value = "home.do")
	public ModelAndView showCustomerPage(HttpServletRequest req, Model model) {

		ModelAndView mav = new ModelAndView();

		try {

			User user = getUserObject();
			LOG.info("User has logged in successfully and has requested home Page"
			        + user.getUsername());
			if (UserRolesEnum.CUSTOMER.toString().equals(
			        user.getUserRole().getRoleCd())) {
				loadDefaultValuesForCustomer(model, req, user);
				UserVO userVO = userProfileService.loadInternalUser(user
				        .getId());
				mav.addObject("user", userVO);
				mav.setViewName(JspLookup.CUSTOMER_VIEW);
			} else {
				loadDefaultValuesForAgent(model, req, user);
				mav.setViewName(JspLookup.AGENT_VIEW);
			}

		} catch (Exception e) {

			LOG.error("Exception in Home page ", e);

		}
		return mav;
	}

	@RequestMapping(value = "checkMongoService.do")
	public ModelAndView checkMongoService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		request.getSession(true);
		mav.addObject("baseUrl", baseUrl);
		mav.setViewName("checkMongoService");
		return mav;
	}
	
	@RequestMapping(value = "/uploadCommonImageToS3.do", method = RequestMethod.POST)
	public @ResponseBody String uploadCommonImageToS3(HttpServletRequest req,
	        Model model)

	throws IOException {

		String s3Path = null;
		try {

			String imageBase64 = req.getParameter("imageBase64");
			Integer userid = Integer.parseInt(req.getParameter("userid"));
			String imageFileName = req.getParameter("imageFileName");

			int selectedX = Integer.parseInt(req.getParameter("selected_x"));
			int selectedY = Integer.parseInt(req.getParameter("selected_y"));
			int selectedW = Integer.parseInt(req.getParameter("selected_w"));
			int selectedH = Integer.parseInt(req.getParameter("selected_h"));

			int resizeWidth = Integer.parseInt(req.getParameter("width"));
			int resizeHeight = Integer.parseInt(req.getParameter("height"));

			LOG.debug("Selected co-ordinates for cropping: x: " + selectedX
			        + " y: " + selectedY + " h: " + selectedH + " w: "
			        + selectedW);
			// BASE64Decoder decoder = new BASE64Decoder();
			// byte[] decodedBytes = decoder.decodeBuffer(imageBase64
			// .substring("data:image/png;base64,".length()));

			byte[] decodedBytes = Base64.decode(imageBase64.split(",")[1]);
			File dir = new File(nexeraUtility.tomcatDirectoryPath()
			        + File.separator + nexeraUtility.randomStringOfLength());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			String filePathSrc = dir.getAbsolutePath() + File.separator
			        + utils.randomNumber() + ".png";
			String filePathDest = dir.getAbsolutePath() + File.separator
			        + utils.randomNumber() + ".png";
			ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
			BufferedImage bufferedImage = ImageIO.read(bis);
			FileOutputStream fileOuputStream = new FileOutputStream(filePathSrc);

			ImageIO.write(bufferedImage, "png", fileOuputStream);

			fileOuputStream.close();

			utils.resize(filePathSrc, filePathDest, resizeWidth, resizeHeight);
			String filePath = dir.getAbsolutePath() + File.separator
			        + imageFileName;

			BufferedImage resized = ImageIO.read(new File(filePathDest));
			BufferedImage croppedImage = utils.cropMyImage(resized, selectedW,
			        selectedH, selectedX, selectedY);
			fileOuputStream = new FileOutputStream(filePath);
			ImageIO.write(croppedImage, "png", fileOuputStream);
			fileOuputStream.close();
			// Create the file on server

			File fileLocal = new File(filePath);
			// ImageIO.write(image, "png", fileLocal);

			s3Path = s3FileUploadServiceImpl.uploadToS3(fileLocal, "User",
			        "complete");
			// Changed for loan profile bug fix
			editUserPhoto(s3Path, userid);
			// save the s3 url in the data base
			Integer num = userProfileService.updatePhotoURL(s3Path, userid);

			UserVO userVO = userProfileService.findUser(userid);
			if (userVO.getUserRole().getId() == 1) {
				if (userVO.getCustomerDetail().getProfileCompletionStatus() != null) {

					if (userVO.getCustomerDetail().getProfileCompletionStatus() <= 100) {

						userProfileService.updateCustomerDetails(userVO);
					}

				} else {

					userProfileService.updateCustomerDetails(userVO);
				}
			}

			if (num < 0) {

				LOG.error("Error whiile saving s3 url in the data base");

			}
			LOG.debug("S3 path-----" + s3Path);

		} catch (Exception e) {

			LOG.error("Exception whiile saving s3 url in the data base", e);

		}

		return s3Path;

	}

	@RequestMapping(value = "customerEngagement.do")
	public ModelAndView showCustomerEngagementPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();

		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		request.getSession(true);
		mav.addObject("baseUrl", baseUrl);
		mav.setViewName("customerEngagementTemplate");
		return mav;
	}

	@RequestMapping(value = "register.do")
	public ModelAndView showCustomerRegisterPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		mav.addObject("baseUrl", baseUrl);
		mav.setViewName("register");
		return mav;
	}

	@RequestMapping(value = "registerNew.do")
	public ModelAndView showCustomerRegisterNewPage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		mav.addObject("baseUrl", baseUrl);
		mav.setViewName("registerDirect");

		return mav;
	}

	@RequestMapping(value = "{userName}")
	public ModelAndView referrerRegistration(@PathVariable String userName,
	        HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		request.getSession(true);
		mav.addObject("baseUrl", baseUrl);
		LOG.info("Url referer from" + userName);
		try {
			UserVO userVO = userProfileService.findByUserName(userName);
			mav.addObject("userObject", userVO);
			String userRole = userVO.getRoleName();
			mav.addObject("userRole", userRole);
			mav.addObject("baseUrl", baseUrl);
			mav.setViewName("registerNew");
		} catch (DatabaseException | NoRecordsFetchedException e) {
			// TODO Auto-generated catch block
			LOG.error("Error retrieving information related to username"
			        + userName, e);
			mav.setViewName("404");
		}

		return mav;
	}

	@RequestMapping(value = "reset.do")
	public ModelAndView resetPassword(
	        @RequestParam(value = "reference", required = false) String identifier,
	        @RequestParam(value = "verifyEmailPath", required = false) String verifyEmailPath,
	        HttpServletRequest request) throws InvalidInputException {
		ModelAndView mav = new ModelAndView();
		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		request.getSession(true);
		LOG.info("Resettting password for" + identifier);
		mav.addObject("baseUrl", baseUrl);
		try {
			// Getting user locale
			Locale clientLocale = request.getLocale();
			Calendar calendar = Calendar.getInstance(clientLocale);
			TimeZone clientTimeZone = calendar.getTimeZone();
			int rawOffSet = clientTimeZone.getRawOffset();
			User userDetail = userProfileService.validateRegistrationLink(
			        identifier, rawOffSet);
			if (verifyEmailPath != null && !verifyEmailPath.isEmpty()) {
				// Update the flag for Email Verified Here

				mav.addObject("verifyEmailPath", "verifyEmail");
			}
			if (userDetail == null) {
				// Re direct to error page
				throw new InvalidInputException("Invalid URL");
			} else if(userDetail.getStatus() == -1){
				mav.addObject("status_error", ErrorConstants.USER_STATUS_INACTIVE);
				mav.setViewName("login");
			}else {
				// Show him the change password page and auto login him
				UserVO userVO = User.convertFromEntityToVO(userDetail);
				mav.addObject("userVO", userVO);
				mav.setViewName("changePassword");
			}
			
		} catch (InvalidInputException invalid) {
			// Stay on same page
			mav.addObject("error", ErrorConstants.LINK_EXPIRED_ERROR);

			mav.setViewName("forgotPassword");
		}
		return mav;
	}

	@RequestMapping(value = "forgotPassword.do")
	public ModelAndView showForgetPasswordPage(
	        @RequestParam(value = "resend", required = false) String resend,
	        HttpServletRequest request) {
		// Check if the user has session now. If yes, force invalidate it.
		Object session = request.getSession(false);
		if (session != null) {
			request.getSession(false).invalidate();
		}
		// Create a new session now.
		LOG.debug("Creating a new session for the user");
		request.getSession(true);
		ModelAndView mav = new ModelAndView();
		mav.addObject("baseUrl", baseUrl);
		mav.setViewName("forgotPassword");
		return mav;
	}
}