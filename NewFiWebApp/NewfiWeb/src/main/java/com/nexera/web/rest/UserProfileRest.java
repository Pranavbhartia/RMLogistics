package com.nexera.web.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.commons.PropertyFileReader;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.entity.User;
import com.nexera.common.enums.MilestoneNotificationTypes;
import com.nexera.common.enums.MobileCarriersEnum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.DatabaseException;
import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.InputValidationException;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.NoRecordsFetchedException;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;
import com.nexera.common.vo.InternalUserStateMappingVO;
import com.nexera.common.vo.UpdatePasswordVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.InternalUserStateMappingService;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.StateLookupService;
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

	@Autowired
	private InternalUserStateMappingService internalUserStateMappingService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private Utils utils;
	@Autowired
	private StateLookupService stateLookupService;
	@Autowired
	private PropertyFileReader propertyFileReader;

	@Autowired
	protected AuthenticationManager authenticationManager;
	@Value("${referal.url}")
	private String referalUrl;
	@Value("${profile.url}")
	private String profileUrl;

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

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO getNewPassword(
	        @RequestParam(value = "resend", required = false) String resend,
	        @RequestBody String user) {
		LOG.info("Forget password call");
		LOG.info("To know if there a user exsists for the emailID");
		UserVO userVO = new Gson().fromJson(user, UserVO.class);
		User userDetail = userProfileService
		        .findUserByMail(userVO.getEmailId());
		CommonResponseVO commonResponse = new CommonResponseVO();
		ErrorVO errors = new ErrorVO();
		if (userDetail != null) {
			try {

				String successMessage = "";
				if (resend != null) {
					userProfileService.resendRegistrationDetails(userDetail);
					successMessage = "Reminder sent. We’ve sent an email to "
					        + userVO.getEmailId()
					        + ". It contains a link to verify your email and reset your password";
				}

				else {
					userProfileService.resetPassword(userDetail);
					successMessage = "Reminder sent. We’ve sent an email to "
					        + userVO.getEmailId()
					        + ". It contains a link with instructions to reset your password";
				}

				commonResponse.setResultObject(successMessage);
			} catch (InvalidInputException | UndeliveredEmailException e) {
				LOG.error("Error in forget password", e.getMessage());
				errors.setMessage(e.getMessage());
				commonResponse.setError(errors);
			}
		} else {
			errors.setMessage(ErrorConstants.FORGET_PASSWORD_USER_EMPTY);
			commonResponse.setError(errors);
		}
		return commonResponse;
	}

	@RequestMapping(value = "/completeprofile", method = RequestMethod.GET)
	public @ResponseBody String getUserProfileWithUserId() {

		String userprofile = null;
		try {
			LOG.info("completeprofile profile get call : ");
			Gson gson = new Gson();
			User user = getUserObject();

			Integer userid = user.getId();

			UserVO userVO = null;
			userVO = userProfileService.findUser(userid);
			if (userVO.getUserRole().getId() == UserRolesEnum.REALTOR
			        .getRoleId()) {
				if (userVO.getRealtorDetail() != null) {
					if (userVO.getRealtorDetail().getUser() != null) {
						userVO.setLoanManagerEmail(userVO.getRealtorDetail()
						        .getUser().getEmailId());
					}

				}

			}
			userVO.setUserProfileBaseUrl(referalUrl);

			userprofile = gson.toJson(userVO);

		} catch (Exception e) {
			LOG.error("Error while getting the user datails ", e);

		}

		return userprofile;
	}
	
	@RequestMapping(value = "/getProfile/{userId}", method = RequestMethod.GET)
	public @ResponseBody String getUserProfileWithUserIdAdminUserList(@PathVariable Integer userId) {

		String userprofile = null;
		try {
			LOG.info("completeprofile profile get call : ");
			Gson gson = new Gson();
/*			User user = getUserObject();

			Integer userid = user.getId();*/

			UserVO userVO = null;
			userVO = userProfileService.findUser(userId);
			if (userVO.getUserRole().getId() == UserRolesEnum.REALTOR
			        .getRoleId()) {
				if (userVO.getRealtorDetail() != null) {
					if (userVO.getRealtorDetail().getUser() != null) {
						userVO.setLoanManagerEmail(userVO.getRealtorDetail()
						        .getUser().getEmailId());
					}

				}

			}
			userVO.setUserProfileBaseUrl(referalUrl);

			userprofile = gson.toJson(userVO);

		} catch (Exception e) {
			LOG.error("Error while getting the user datails ", e);

		}

		return userprofile;
	}


	@RequestMapping(value = "/getMobileCarriers", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO getMobileCarriers() {

		List<MobileCarriersEnum> mobileCarriers = Arrays
		        .asList(MobileCarriersEnum.values());
		List<String> names = new ArrayList<String>();
		for (MobileCarriersEnum carrierNames : mobileCarriers) {
			names.add(carrierNames.getCarrierName());
		}
		CommonResponseVO responseVO = null;
		responseVO = RestUtil.wrapObjectForSuccess(names);

		return responseVO;
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateprofile(String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		ErrorVO error = new ErrorVO();

		userVO = gson.fromJson(updateUserInfo, UserVO.class);

		Integer userUpdateCount = null;
		try {
			userUpdateCount = userProfileService.updateUser(userVO);
			if (userUpdateCount < 0) {
				error.setMessage(ErrorConstants.UPDATE_ERROR_USER);
			}
			commonResponseVO.setResultObject("success");
		} catch (InputValidationException e) {
			error.setMessage(e.getDebugMessage());
			commonResponseVO.setError(error);
		} catch (NonFatalException e) {
			error.setMessage(e.getMessage());
			commonResponseVO.setError(error);
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public @ResponseBody String changeUserPassword(
	        @RequestParam String changePasswordData,
	        HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Resetting the Password");
		boolean passwordChanged = false;
		Gson gson = new Gson();
		UpdatePasswordVO updatePassword = gson.fromJson(changePasswordData,
		        UpdatePasswordVO.class);
		try {
			passwordChanged = userProfileService
			        .changeUserPassword(updatePassword);
			if (updatePassword.isVerifyEmailPath()) {
				LOG.info("The user is verifying his email: set his verified to true");
				userProfileService.verifyEmail(updatePassword.getUserId());
				LOG.info("Also dismiss alert for Verification");
			}
			if (passwordChanged == true) {
				String emailId = updatePassword.getEmailID();
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				        emailId, updatePassword.getNewPassword());
				token.setDetails(new WebAuthenticationDetails(request));
				token.setDetails("Change Password");
				Authentication authenticatedUser = authenticationManager
				        .authenticate(token);
				SecurityContextHolder.getContext().setAuthentication(
				        authenticatedUser);
				// TODO link expiration
				UserVO userVOUpdate = new UserVO();
				userVOUpdate.setId(updatePassword.getUserId());
				userVOUpdate.setEmailEncryptionToken(null);
				userProfileService.updateTokenDetails(User
				        .convertFromVOToEntity(userVOUpdate));

				// Update his login time here
			}
		} catch (InputValidationException inputValidation) {
			throw inputValidation;
		}

		catch (Exception inputValidation) {
			throw new FatalException("Could not login user");

		}
		return profileUrl + "home.do";

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

			LOG.error("Error while updataing the user datails ::", e);
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/managerupdateprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO managerUpdateprofile(
	        String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		ErrorVO error = new ErrorVO();
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);

			/*
			 * Integer userUpdateCount = userProfileService
			 * .managerUpdateUserProfile(userVO); Integer
			 * customerDetailsUpdateCount = userProfileService
			 * .managerUpdateUCustomerDetails(userVO);
			 */
			Integer userUpdateCount = userProfileService.updateUser(userVO);

			if (userUpdateCount < 0) {
				LOG.error("Error while updataing the user datails ");
				error.setMessage("Error while updataing the user datails ");
				commonResponseVO.setError(error);

			}

		} catch (Exception e) {
			LOG.error("Error while updataing the user datails ::",
			        e.getMessage());
			error.setMessage("Error while updataing the user datails ");
			commonResponseVO.setError(error);
		}

		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody String userVOStr) {

		UserVO userVO = new Gson().fromJson(userVOStr, UserVO.class);

		try {
			if (userVO.getUsername() == null)
				userVO.setUsername(userVO.getEmailId());

			User newUser = userProfileService.createNewUser(userVO);
			userProfileService.sendEmailToCustomer(newUser);
			if (userVO.getUserRole().getId() == UserRolesEnum.REALTOR
			        .getRoleId()) {
				/*
				 * This is the case when the loan manager adds a realtor, the
				 * default assignment should happen now.
				 */
				if (UserRolesEnum.LM.getName().equals(
				        utils.getLoggedInUserRole().getName())) {
					// User is Loan Manager
					userProfileService.addDefaultLM(userVO);
				}

			}
		} catch (DatabaseException dbe) {
			LOG.error("Error while saveing user with same email");
			return new Gson().toJson(RestUtil.wrapObjectForFailure(null, "522",
			        "User Already Present"));
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			LOG.error("Error while saveing user with same email", e);
		} catch (UndeliveredEmailException e) {
			// TODO Auto-generated catch block
			LOG.error("Error while saveing user with same email", e);
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

		}
		LOG.debug("Parsed the json string");
		if (userVO.getUsername() == null)
			userVO.setUsername(userVO.getEmailId());
		try {
			LOG.info("TO check whether user exsists");
			User user = userProfileService.findUserByMail(userVO.getEmailId());
			if (user != null) {
				ErrorVO error = new ErrorVO();
				error.setMessage(ErrorConstants.ADMIN_CREATE_USER_ERROR);
				return new Gson().toJson(RestUtil.wrapObjectForFailure(error,
				        "522", ""));

			}
			LOG.debug("Creating the new user");
			User newUser = userProfileService.createNewUser(userVO);
			userProfileService.sendEmailToCustomer(newUser);
			LOG.debug("Created new user and email sent!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error message : " + e.getMessage(), e);

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
			LOG.error(
			        "disableUser : NoRecordsFetchedException thrown, message : ",
			        e);

			result.addProperty("success", 0);
			result.addProperty("message", "User not found in database");
		} catch (DatabaseException e) {
			LOG.error("disableUser : DatabaseException thrown, message : ", e);

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
			LOG.error(
			        "disableUser : NoRecordsFetchedException thrown, message : ",
			        e);

			result.addProperty("success", 0);
			result.addProperty("message", "User not found in database");
		} catch (DatabaseException e) {
			LOG.error("disableUser : DatabaseException thrown, message : ", e);

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
		ErrorVO error = new ErrorVO();
		try {
			UserVO userVO = userProfileService.findUser(userId);
			if (userVO.getUserRole().getId() == UserRolesEnum.CUSTOMER
			        .getRoleId()||userVO.getUserRole().getId() == UserRolesEnum.REALTOR
					        .getRoleId()) {

				userVO.setStatus(-1);
				Integer result = userProfileService.updateUserStatus(userVO);
				if (result > 0) {
					response.setResultObject(userVO);
				} else {
					error.setMessage("Error while deletion.Please try again later");
					response.setError(error);
				}

			} else {
				userProfileService.deleteUser(userVO);
				response.setResultObject(userVO);
			}

		} catch (InputValidationException e) {
			LOG.error("error and message is : " + e.getDebugMessage());

			error.setMessage(e.getDebugMessage());
			response.setError(error);
			e.getDebugMessage();

		} catch (Exception e) {
			LOG.error("error and message is : " + e.getMessage());

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
		JsonObject result = new JsonObject();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("file");
		try {
			result = userProfileService.parseCsvAndAddUsers(file);

		} catch (Exception e) {
			// TODO: handle exception
			LOG.error("Issue in addusersfrom csv", e);
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
	@RequestMapping(value = "/updateLqbprofile", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateLqbprofile(String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);
			userProfileService.updateLQBUsercred(userVO);

		}
		catch (InvalidInputException e) {
			LOG.error("Error while updataing the user datails ::", e);
			return RestUtil.wrapObjectForFailure(null,
			       e.getMessage(),
			       e.getMessage());
		}
		catch (Exception e) {
			LOG.error("Error while updataing the user datails ::", e);
			return RestUtil.wrapObjectForFailure(null,
			        ErrorConstants.LQB_SAVE_FAILED,
			        ErrorConstants.LQB_SAVE_FAILED);
		}
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/nmls", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateNMLSId(String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);
			userProfileService.updateNMLSId(userVO);
		} catch (Exception e) {
			LOG.error("Error while updataing the user datails ::",
			        e.getMessage());
		}
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/internaluserstatemapping", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateInternalUserStateMapping(
	        String internaluserstatemapping) {

		Gson gson = new Gson();
		InternalUserStateMappingVO internalUserStateMappingVO = null;
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		ErrorVO error = new ErrorVO();

		internalUserStateMappingVO = gson.fromJson(internaluserstatemapping,
		        InternalUserStateMappingVO.class);

		try {
			internalUserStateMappingVO = userProfileService
			        .updateInternalUserStateMapping(internalUserStateMappingVO);
			if (null == internalUserStateMappingVO) {
				error.setMessage(ErrorConstants.UPDATE_ERROR_USER);
			}
			commonResponseVO.setResultObject(internalUserStateMappingVO);
		} catch (InputValidationException e) {
			error.setMessage(e.getDebugMessage());
			commonResponseVO.setError(error);
		} catch (Exception e) {
			error.setMessage(e.getMessage());
			commonResponseVO.setError(error);
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/deleteStatelicensemapping", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO deleteInternalUserStateMapping(
	        String internaluserstatemapping) {

		Gson gson = new Gson();
		InternalUserStateMappingVO internalUserStateMappingVO = null;
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		ErrorVO error = new ErrorVO();

		internalUserStateMappingVO = gson.fromJson(internaluserstatemapping,
		        InternalUserStateMappingVO.class);

		try {
			internalUserStateMappingVO = userProfileService
			        .deleteInternalUserStateMapping(internalUserStateMappingVO);
			if (null == internalUserStateMappingVO) {
				error.setMessage(ErrorConstants.UPDATE_ERROR_USER);
			}
			commonResponseVO.setResultObject("success");
		} catch (InputValidationException e) {
			error.setMessage(e.getDebugMessage());
			commonResponseVO.setError(error);
		} catch (Exception e) {
			error.setMessage(e.getMessage());
			commonResponseVO.setError(error);
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/updatetutorialstatus", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO updateTutorialStatus(String inputData) {

		CommonResponseVO commonResponseVO = new CommonResponseVO();
		ErrorVO error = new ErrorVO();
		try {

			JSONObject jsonObject = new JSONObject(inputData);
			Integer id = (Integer) jsonObject.get("id");
			Integer loanId = (Integer) jsonObject.get("loanId");

			Integer resultRow = userProfileService.updateTutorialStatus(id);
			userProfileService
			        .dismissAlert(
			                MilestoneNotificationTypes.WATCH_ALERT_NOTIFICATION_TYPE,
			                loanId,
			                WorkflowConstants.WATCH_TUTORIAL_ALERT_NOTIFICATION_CONTENT);
			if (resultRow < 0) {
				commonResponseVO.setError(error);
			}
		} catch (Exception e) {

			error.setMessage(e.getMessage());
			commonResponseVO.setError(error);
		}

		return commonResponseVO;

	}

}
