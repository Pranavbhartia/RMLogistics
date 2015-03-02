package com.nexera.web.rest;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.UserRoleVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;
import com.nexera.web.rest.util.RestUtil;

@Controller
@RequestMapping("/userprofile")
public class UserProfileRest {

	@Autowired
	private UserProfileService userProfileService;

	private static final Logger LOG = LoggerFactory.getLogger(UserProfileRest.class);
	
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
	public @ResponseBody
	String getUserProfileWithUserId() {
		
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
			LOG.error("Error while getting the user datails ",  e.getMessage());

		}

		return userprofile;
	}

	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public @ResponseBody
	CommonResponseVO updateprofile(String updateUserInfo) {

		Gson gson = new Gson();
		UserVO userVO = null;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);
			
			Integer userUpdateCount = userProfileService.updateUser(userVO);
			Integer customerDetailsUpdateCount = userProfileService.updateCustomerDetails(userVO);
			
			if(userUpdateCount < 0 || customerDetailsUpdateCount < 0 ){
				LOG.error("Error while updataing the user datails ");
			}
			
		} catch (Exception e) {
			LOG.error("Error while updataing the user datails ::",  e.getMessage());
		}
		CommonResponseVO commonResponseVO = new CommonResponseVO ();
		commonResponseVO.setResultObject("success");
		return commonResponseVO;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody String searchUsers(
			@RequestParam(value = "name",required=false) String name,
			@RequestParam(value = "roleID",required=false) Integer roleID) {

		if (name == null)
			name = "";
		
		UserRoleVO roleVO=null;
		if(roleID!=null && roleID>0){
			roleVO=new UserRoleVO();
			roleVO.setId(roleID);
		}
		
		List<UserVO> userList = userProfileService
				.searchUsersByName(name, roleVO);

		CommonResponseVO responseVO = RestUtil.wrapObjectForSuccess(userList);

		return new Gson().toJson(responseVO);
	}

	@RequestMapping(value="/",method = RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody String userVOStr) {

		UserVO userVO = new Gson().fromJson(userVOStr, UserVO.class);
		if(userVO.getUsername()==null)
			userVO.setUsername(userVO.getEmailId());
		userVO = userProfileService.createUser(userVO);
		return new Gson().toJson(RestUtil.wrapObjectForSuccess(userVO));

	}
}
