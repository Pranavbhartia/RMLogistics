package com.nexera.web.controller;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.constants.JspLookup;

@Controller
public class TemplateComtroller {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl; 
	
	@Autowired
	private UserProfileService userProfileService;
	
	private static final Logger LOG = LoggerFactory.getLogger(TemplateComtroller.class);
	
	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}
	
	@RequestMapping(value="customerViewTemplate.do")
	 public ModelAndView showCustomerPage(){
			ModelAndView mav = new ModelAndView();
			
			User user = getUserObject();
			Integer userid = user.getId();
			UserVO userVO = null;
			try {
				userVO = userProfileService.findUser(userid);

			} catch (Exception e) {
				System.out.println(e.getMessage());

			}
			mav.setViewName(JspLookup.CUSTOMER_VIEW);
			mav.addObject("user", userVO);
		
			return mav;
		}
	 
	 @RequestMapping(value = "/uploadCommonImageToS3.do", method = RequestMethod.POST)
		public @ResponseBody
		String uploadCommonImageToS3(
				 @RequestParam("fileName") MultipartFile multipartFile)
				//@RequestParam(value = "fileName", required = true) MultipartFile multipartFile)
				throws IOException {
		  	NexeraUtility.uploadFileToLocal(multipartFile);
			Gson gson = new Gson();
			CommonResponseVO responseVO = new CommonResponseVO();
			String s3Path = null;
			try {
				File serverFile = new File( NexeraUtility.uploadFileToLocal(multipartFile));
				s3Path = s3FileUploadServiceImpl.uploadToS3(serverFile, "User" , "complete");

				LOG.info("The s3 path is : "+s3Path);
				
				// save image in the data base
				User user = getUserObject();
				Integer userid = user.getId();
				Integer num = userProfileService.updateUser(s3Path ,userid);
				
			} catch (Exception e) {
				
			}

			return s3Path;

		}

	 
	 @RequestMapping(value="customerLoanPage.do")
	 public ModelAndView showCustomerLaonPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("cutomerViewMainBody");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="messageDashboard.do")
	 public ModelAndView showCustomerMessageDashboard(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("customerViewMessageDashboard");
			//mav.addObject("myName", userService.getName());
			return mav;
	 }
	 
	 @RequestMapping(value="agentPage.do")
	 public ModelAndView showAgentPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("agentViewTemplate");
			//mav.addObject("myName", userService.getName());
			return mav;
	}
	 
	 @RequestMapping(value="agentDashboard.do")
	 public ModelAndView agentDashboard(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("agentViewDashboard");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="agentLoanPage.do")
	 public ModelAndView agentMainBody(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("agentViewMainBody");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value = "customerUploadRequiredFiles.do")
	 public ModelAndView customerUploadRequiredFiles(){
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName(JspLookup.CUSTOMER_UPLOAD_DOCUMENT);
		 return mav;
	 }
	 
	 
	 @RequestMapping(value = "video.do")
	 public ModelAndView showVideoPage(){
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("videoTest");
		 return mav;
	 }
	 
	 @RequestMapping(value = "customerProfile.do")
	 public ModelAndView showCustomerProfilePage(){
		 ModelAndView mav = new ModelAndView();
		 mav.setViewName("customerProfile");
		 return mav;
	 }
}
