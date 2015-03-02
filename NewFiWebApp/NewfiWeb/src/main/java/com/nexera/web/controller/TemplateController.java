package com.nexera.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEum;
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
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private UserProfileService userProfileService;

	private static final Logger LOG = LoggerFactory
			.getLogger(TemplateController.class);

	@RequestMapping(value = "home.do")
	public String showCustomerPage(HttpServletRequest req, Model model) {

		try {
			User user = loadDefaultValues(model, req);
			if (UserRolesEum.CUSTOMER.toString().equals(user.getUserRole().getRoleCd())) {

				return JspLookup.CUSTOMER_VIEW;
			} else {
				return JspLookup.AGENT_VIEW;
			}

		} catch (IOException e) {

			e.printStackTrace();

		} catch (Exception e) {
			// TODO: Handle exception scenario

			e.printStackTrace();
		}
		return JspLookup.ERROR;
	}

	@RequestMapping(value = "/uploadCommonImageToS3.do", method = RequestMethod.POST)
	public @ResponseBody
	String uploadCommonImageToS3(
			@RequestParam("fileName") MultipartFile multipartFile,
			HttpServletRequest req, Model model)
	// @RequestParam(value = "fileName", required = true) MultipartFile
	// multipartFile)
			throws IOException {
		NexeraUtility.uploadFileToLocal(multipartFile);

		String s3Path = null;
		try {
			File serverFile = new File(
					NexeraUtility.uploadFileToLocal(multipartFile));
			s3Path = s3FileUploadServiceImpl.uploadToS3(serverFile, "User",
					"complete");

			LOG.info("The s3 path is : " + s3Path);

			// save image in the data base
			User user = loadDefaultValues(model, req);
			Integer userid = user.getId();
			Integer num = userProfileService.updateUser(s3Path, userid);

		} catch (Exception e) {

		}

		return s3Path;

	}

	//
	// @RequestMapping(value="customerLoanPage.do")
	// public ModelAndView showCustomerLaonPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("cutomerViewMainBody");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="messageDashboard.do")
	// public ModelAndView showCustomerMessageDashboard(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("customerViewMessageDashboard");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentPage.do")
	// public ModelAndView showAgentPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewTemplate");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentDashboard.do")
	// public ModelAndView agentDashboard(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewDashboard");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentLoanPage.do")
	// public ModelAndView agentMainBody(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewMainBody");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value = "customerUploadRequiredFiles.do")
	// public ModelAndView customerUploadRequiredFiles(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName(JspLookup.CUSTOMER_UPLOAD_DOCUMENT);
	// return mav;
	// }
	//
	//
	// @RequestMapping(value = "video.do")
	// public ModelAndView showVideoPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("videoTest");
	// return mav;
	// }
	//
	// @RequestMapping(value = "home.do")
	// public ModelAndView showLoginPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("login");
	// return mav;
	// }
	//
	//
	// @RequestMapping(value = "customerProfile.do")
	// public ModelAndView showCustomerProfilePage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("customerProfile");
	// return mav;
	// }
}
