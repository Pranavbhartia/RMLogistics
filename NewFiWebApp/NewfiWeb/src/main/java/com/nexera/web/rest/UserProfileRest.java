package com.nexera.web.rest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nexera.common.entity.LoanTeam;
import com.nexera.common.entity.User;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.InputValidationException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.LoanTeamListVO;
import com.nexera.common.vo.LoanTeamVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.web.rest.util.RestUtil;

@Controller
@RequestMapping("/userprofile")
public class UserProfileRest {

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	private static final Logger LOG = LoggerFactory
	        .getLogger(UserProfileRest.class);

	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
		        .getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/completeprofile", method = RequestMethod.GET)
	public @ResponseBody String getUserProfileWithUserId() {

		LOG.info("completeprofile profile get call : ");
		Gson gson = new Gson();
		User user = getUserObject();

		Integer userid = user.getId();
		UserVO userVO = null;
		String userprofile = null;
		try {
			userVO = userProfileService.findUser(userid);
			userprofile = gson.toJson(userVO);

		} catch (Exception e) {
			LOG.error("Error while getting the user datails ", e.getMessage());

		}

		return userprofile;
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateprofile(String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);

			Integer userUpdateCount = userProfileService.updateUser(userVO);

			UserVO user = userProfileService.findUser(userVO.getId());
			if (userVO.getCustomerDetail() != null) {
				userVO.getCustomerDetail().setProfileCompletionStatus(
				        user.getCustomerDetail().getProfileCompletionStatus());
			}
			if (user.getPhotoImageUrl() != null) {
				userVO.setPhotoImageUrl(user.getPhotoImageUrl());
			}
			Integer customerDetailsUpdateCount = userProfileService
			        .updateCustomerDetails(userVO);

			if (userUpdateCount < 0 || customerDetailsUpdateCount < 0) {
				LOG.error("Error while updataing the user datails ");
			}

		} catch (Exception e) {
			LOG.error("Error while updataing the user datails ::",
			        e.getMessage());
		}
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String searchUsers(
	        @RequestParam(value = "name", required = false) String name,
	        @RequestParam(value = "roleID", required = false) Integer roleID,
	        @RequestParam(value = "internalRoleID", required = false) Integer internalRoleID) {

		if (name == null)
			name = "";

		UserVO userVO = new UserVO();
		userVO.setFirstName(name);
		UserRoleVO roleVO = null;
		InternalUserDetailVO internalUserVO = null;
		if (roleID != null && roleID > 0) {
			roleVO = new UserRoleVO();
			roleVO.setId(roleID);

			if (internalRoleID != null) {
				internalUserVO = new InternalUserDetailVO();
				InternalUserRoleMasterVO internaUserRoleMasterVO = new InternalUserRoleMasterVO();
				internaUserRoleMasterVO.setId(internalRoleID);
				internalUserVO
				        .setInternalUserRoleMasterVO(internaUserRoleMasterVO);
				userVO.setInternalUserDetail(internalUserVO);
			}
			userVO.setUserRole(roleVO);
		}

		List<UserVO> userList = userProfileService.searchUsers(userVO);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(userList);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value = "/searchUserName", method = RequestMethod.GET)
	public @ResponseBody String searchUsers(
	        @RequestParam(value = "name", required = false) String name) {

		if (name == null)
			name = "";

		UserVO userVO = new UserVO();
		userVO.setFirstName(name);

		userVO.setEmailId(name);

		List<UserVO> userList = userProfileService.searchUsers(userVO);
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(userList);

		return new Gson().toJson(responseVO);

	}

	@RequestMapping(value = "/getUsersList", method = RequestMethod.GET)
	public @ResponseBody String getUsersList() {
		UserVO userVO = new UserVO();
		List<UserVO> userList = new ArrayList<UserVO>();
		userList = userProfileService.getUsersList();
		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(userList);
		return new Gson().toJson(responseVO);

	}

	@RequestMapping(value = "/completeprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO completeProfile(
	        String completeUserInfo) {

		CommonResponseVO commonResponseVO = new CommonResponseVO();
		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(completeUserInfo, UserVO.class);

			Integer userUpdateCount = userProfileService
			        .competeUserProfile(userVO);
			Integer customerDetailsUpdateCount = userProfileService
			        .completeCustomerDetails(userVO);

			if (userUpdateCount < 0 || customerDetailsUpdateCount < 0) {
				LOG.error("Error while updataing the user datails ");
			}

			commonResponseVO.setResultObject("success");

		} catch (Exception e) {
			commonResponseVO.setResultObject("error");
			e.printStackTrace();
			LOG.error("Error while updataing the user datails ::",
			        e.getMessage());
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/managerupdateprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO managerUpdateprofile(
	        String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);

			Integer userUpdateCount = userProfileService
			        .managerUpdateUserProfile(userVO);
			Integer customerDetailsUpdateCount = userProfileService
			        .managerUpdateUCustomerDetails(userVO);

			if (userUpdateCount < 0 || customerDetailsUpdateCount < 0) {
				LOG.error("Error while updataing the user datails ");
			}

		} catch (Exception e) {
			LOG.error("Error while updataing the user datails ::",
			        e.getMessage());
		}
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody String userVOStr) {

		UserVO userVO = new Gson().fromJson(userVOStr, UserVO.class);
		if (userVO.getUsername() == null)
			userVO.setUsername(userVO.getEmailId());
		try {
			userVO = userProfileService.createNewUserAndSendMail(userVO);
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndeliveredEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Gson().toJson(RestUtil.wrapObjectForSuccess(userVO));

	}

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public @ResponseBody String createNewUser(@RequestBody String userVOStr) {

		LOG.info("Adduser called to add user details : " + userVOStr);
		UserVO userVO = null;
		try {
			userVO = new Gson().fromJson(userVOStr, UserVO.class);
		} catch (Exception e) {
			LOG.error("Error message : " + e.getMessage(), e);
			e.printStackTrace();
		}
		LOG.debug("Parsed the json string");
		if (userVO.getUsername() == null)
			userVO.setUsername(userVO.getEmailId());
		try {
			LOG.debug("Creating the new user");
			userVO = userProfileService.createNewUserAndSendMail(userVO);
			LOG.debug("Created new user and email sent!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error message : " + e.getMessage(), e);
			e.printStackTrace();
		}

		LOG.info("Returning the json : "
		        + new Gson().toJson(RestUtil.wrapObjectForSuccess(userVO)));
		return new Gson().toJson(RestUtil.wrapObjectForSuccess(userVO));

	}

	@RequestMapping(value = "/disable/{userId}", method = RequestMethod.GET)
	public @ResponseBody String disableUser(@PathVariable Integer userId) {

		LOG.info("Disabling user with user id : " + userId);

		JsonObject result = new JsonObject();
		try {
			userProfileService.disableUser(userId);
			result.addProperty("success", 1);
		} catch (NoRecordsFetchedException e) {
			LOG.error("disableUser : NoRecordsFetchedException thrown, message : "
			        + e.getMessage());
			e.printStackTrace();
			result.addProperty("success", 0);
			result.addProperty("message", "User not found in database");
		} catch (DatabaseException e) {
			LOG.error("disableUser : DatabaseException thrown, message : "
			        + e.getMessage());
			e.printStackTrace();
			result.addProperty("success", 0);
			result.addProperty("message", "Technical issue has occurred!");
		}

		LOG.info("Returning the json" + result.toString());
		return result.toString();

	}

	@RequestMapping(value = "/enable/{userId}", method = RequestMethod.GET)
	public @ResponseBody String enableUser(@PathVariable Integer userId) {

		LOG.info("Enabling user with user id : " + userId);

		JsonObject result = new JsonObject();
		try {
			userProfileService.enableUser(userId);
			result.addProperty("success", 1);
		} catch (NoRecordsFetchedException e) {
			LOG.error("disableUser : NoRecordsFetchedException thrown, message : "
			        + e.getMessage());
			e.printStackTrace();
			result.addProperty("success", 0);
			result.addProperty("message", "User not found in database");
		} catch (DatabaseException e) {
			LOG.error("disableUser : DatabaseException thrown, message : "
			        + e.getMessage());
			e.printStackTrace();
			result.addProperty("success", 0);
			result.addProperty("message", "Technical issue has occurred!");
		}

		LOG.info("Returning the json" + result.toString());
		return result.toString();

	}

	@RequestMapping(value = "/deleteUser/{userId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO deleteUser(
	        @PathVariable Integer userId) {

		LOG.info("Delete user with user id : " + userId);
		CommonResponseVO response = new CommonResponseVO();

		try {
			UserVO userVO = userProfileService.findUser(userId);

			userProfileService.deleteUser(userVO);
			response.setResultObject(userVO);

		} catch (InputValidationException e) {
			LOG.error("error and message is : " + e.getMessage());
			ErrorVO error = new ErrorVO();
			error.setMessage(e.getMessage());
			response.setError(error);

		} catch (Exception e) {
			LOG.error("error and message is : " + e.getMessage());
			ErrorVO error = new ErrorVO();
			error.setMessage(e.getMessage());
			response.setError(error);
        }
		return response;

	}

	@RequestMapping(value = "/addusersfromcsv", method = RequestMethod.POST, headers = "Accept=*")
	public @ResponseBody String registerUsersFromCsv(
	        @RequestParam(value = "file", required = true) MultipartFile multipartFile,
	        HttpServletRequest request, HttpServletResponse response) {
		LOG.info("File upload Rest service called");
		JsonObject result = null;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		try {
			result = userProfileService.parseCsvAndAddUsers(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndeliveredEmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result.toString();
	}

	/*
	 * @RequestMapping(value = "/findteaserate", method = RequestMethod.POST)
	 * 
	 * public @ResponseBody String getTeaserRate(String teaseRate) {
	 * 
	 * System.out.println("teaseRate ..."+teaseRate); return null;
	 * 
	 * }
	 */

}
