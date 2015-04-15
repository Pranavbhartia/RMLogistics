package com.nexera.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Decoder;

import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.constants.JspLookup;

@Controller
public class TemplateController extends DefaultController {

	@Autowired
	LoanService loanService;

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private NexeraUtility nexeraUtility;

	private static final Logger LOG = LoggerFactory
	        .getLogger(TemplateController.class);

	@RequestMapping(value = "calculator.do")
	public ModelAndView showCustomerPage1(HttpServletRequest req, Model model) {

		ModelAndView mav = new ModelAndView();

		try {

			mav.setViewName("calculator");

		} catch (Exception e) {
			// TODO: Handle exception scenario

			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "home.do")
	public ModelAndView showCustomerPage(HttpServletRequest req, Model model) {

		ModelAndView mav = new ModelAndView();

		try {

			User user = getUserObject();

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
			// TODO: Handle exception scenario

			e.printStackTrace();
		}
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

			BASE64Decoder decoder = new BASE64Decoder();
			byte[] decodedBytes = decoder.decodeBuffer(imageBase64
			        .substring("data:image/png;base64,".length()));
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(
			        decodedBytes));
			if (image == null) {
				LOG.error("Buffered Image is null");
			}

			File dir = new File(nexeraUtility.tomcatDirectoryPath());
			if (!dir.exists())
				dir.mkdirs();

			String filePath = dir.getAbsolutePath() + File.separator
			        + imageFileName;
			// Create the file on server

			File fileLocal = new File(filePath);
			ImageIO.write(image, "png", fileLocal);

			s3Path = s3FileUploadServiceImpl.uploadToS3(fileLocal, "User",
			        "complete");
			// Changed for loan profile bug fix
			editUserPhoto(s3Path, userid);
			// save the s3 url in the data base
			Integer num = userProfileService.updateUser(s3Path, userid);

			UserVO userVO = userProfileService.findUser(userid);
			if (userVO.getUserRole().getId() == 1) {
				if (userVO.getCustomerDetail().getProfileCompletionStatus() != null) {

					if (userVO.getCustomerDetail().getProfileCompletionStatus() <= 100) {

						userProfileService.updateCustomerDetails(userVO);
					}

				} else {

					if (userVO.getCustomerDetail().getMobileAlertsPreference() == null) {
						userVO.getCustomerDetail().setMobileAlertsPreference(
						        false);
					} else {
						userVO.getCustomerDetail().setMobileAlertsPreference(
						        true);
					}
					userProfileService.updateCustomerDetails(userVO);
				}
			}

			if (num < 0) {

				LOG.error("Error whiile saving s3 url in the data base");

			}
			LOG.error("S3 path-----" + s3Path);

		} catch (Exception e) {

			LOG.error("Exception whiile saving s3 url in the data base"
			        + e.getMessage());
			e.printStackTrace();
		}

		return s3Path;

	}

	@RequestMapping(value = "customerEngagement.do")
	public ModelAndView showCustomerEngagementPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customerEngagementTemplate");
		return mav;
	}

	@RequestMapping(value = "register.do")
	public ModelAndView showCustomerRegisterPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("register");
		return mav;
	}

	@RequestMapping(value = "registerNew.do")
	public ModelAndView showCustomerRegisterNewPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registerNew");
		return mav;
	}

	@RequestMapping(value = "forgotPassword.do")
	public ModelAndView showForgetPasswordPage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("forgotPassword");
		return mav;
	}
}