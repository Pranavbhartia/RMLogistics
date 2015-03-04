package com.nexera.web.controller;

import java.awt.image.BufferedImage;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEum;
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
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private UserProfileService userProfileService;

	private static final Logger LOG = LoggerFactory
			.getLogger(TemplateController.class);

	@RequestMapping(value = "home.do")
	public ModelAndView showCustomerPage(HttpServletRequest req, Model model) {

		ModelAndView mav = new ModelAndView();

		try {

			User user = getUserObject();

			if (UserRolesEum.CUSTOMER.toString().equals(
					user.getUserRole().getRoleCd())) {
				loadDefaultValuesForCustomer(model, req,user);
				UserVO userVO = userProfileService.findUser(user.getId());
				mav.addObject("user", userVO);
				mav.setViewName(JspLookup.CUSTOMER_VIEW);
			} else {
				loadDefaultValuesForAgent(model, req,user);
				mav.setViewName(JspLookup.AGENT_VIEW);
			}

		} catch (Exception e) {
			// TODO: Handle exception scenario

			e.printStackTrace();
		}
		return mav;
	}


	@RequestMapping(value = "/uploadCommonImageToS3.do", method = RequestMethod.POST)
	public @ResponseBody String uploadCommonImageToS3(
			@RequestParam("fileName") MultipartFile multipartFile,@RequestParam int userId,
			HttpServletRequest req, Model model)

			throws IOException {
		NexeraUtility.uploadFileToLocal(multipartFile);

		String s3Path = null;
		try {
			File serverFile = new File(NexeraUtility.uploadFileToLocal(multipartFile));
			s3Path = s3FileUploadServiceImpl.uploadToS3(serverFile, "User","complete");
			
			LOG.info("The s3 path is : " + s3Path);


		} catch (Exception e) {

		}

		return s3Path;

	}

	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadProfilePicture.do")
	public @ResponseBody
	String uploadProfilePicture(
	@RequestParam(value = "fileName", required = true) MultipartFile multipartFile,
	@RequestParam(required = true) Integer xAxis,
	@RequestParam(required = true) Integer yAxis,
	@RequestParam(required = true) Integer width,
	@RequestParam(required = true) Integer height,
	@RequestParam(required = true) Integer userId)
	throws IOException {
	LOG.info("*******FILE UPLOAD**********");
	Gson gson = new Gson();
	User user = null;
	String s3PathCrop = "error";

	try {
	
	String orgName = multipartFile.getOriginalFilename();
	
	LOG.info("fileName=" + orgName+" xAxis="+xAxis+" yAxis="+yAxis+" width="+width+" height="+height+" userId="+userId);
	
String tempDir="D:\\temp";
String cropPathDir="D:\\temp\\crop";
	File dirPath = new File(tempDir);
	File cropPath = new File(cropPathDir);
	
	if (!dirPath.exists()) {
	dirPath.mkdirs();
	}

	if (!cropPath.exists()) {
	cropPath.mkdir();
	}
//change
	String randomString = String.valueOf(System.currentTimeMillis());
	
	String filePath = tempDir + File.separator + randomString
	+ orgName;

	String croppathImage = cropPathDir + File.separator
	+ randomString + orgName;

	LOG.info("the File path is : " + filePath);
	LOG.info(tempDir + ":file name=" + orgName);

	
	File dest = new File(filePath);
	
	multipartFile.transferTo(dest);
	
	
	
	/*try {
	Thumbnails.of(dest).size(Integer.parseInt(imageWidth),Integer.parseInt(imageHeight)).toFile(dest);
	} catch (IOException e) {
	e.printStackTrace();
	}*/

	// code to crop image using
	BufferedImage tempCrop = ImageIO.read(dest);

	int widthI          = tempCrop.getWidth();
	int heightI         = tempCrop.getHeight();
	System.out.println(widthI+"-Sizi--"+heightI);

	BufferedImage cropped = tempCrop.getSubimage(xAxis, yAxis,
	width, height);
	File newCFile = new File(croppathImage);
	Boolean cropUpload = ImageIO.write(cropped, "jpg", newCFile);

	s3PathCrop = s3FileUploadServiceImpl.uploadToS3(newCFile, "User","complete");
	LOG.info(cropUpload + "the cropped image path " + s3PathCrop);
	//userService.changeCropPhoto(user.getId(), s3PathCrop);

	LOG.info("Uploaded to S3 with url: " + s3PathCrop);
	editUserPhoto(s3PathCrop);
	if(userId != 0){
		Integer num = userProfileService.updateUser(s3PathCrop, userId);
	}
	

	} catch (Exception e) {
	LOG.error("REQUEST_FAILED", e);
	e.printStackTrace();
	return s3PathCrop;
	} 
	return s3PathCrop;
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