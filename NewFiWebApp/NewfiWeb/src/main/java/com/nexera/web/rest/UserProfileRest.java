package com.nexera.web.rest;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.nexera.common.entity.User;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;

@RestController
@RequestMapping("/userprofile")
public class UserProfileRest {

	@Autowired
	private UserProfileService userProfileService;

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

		Gson gson = new Gson();
		User user = getUserObject();
		//boolean error = false;

		Integer userid = user.getId();
		UserVO userVO = null;
		String userprofile = null;
		try {
			 userVO = userProfileService.findUser(userid);
			 userprofile = gson.toJson(userVO);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return userprofile;
		//return "Hello";
	}
	
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	public @ResponseBody String updateprofile(@RequestBody String updateUserInfo) {

		
		System.out.println(updateUserInfo);
		
		Gson gson = new Gson();
		UserVO userVO;
		try {
			userVO = gson.fromJson(updateUserInfo, UserVO.class);
			System.out.println("------------------"+userVO.getCustomerDetail().getAddressState());
		} catch (Exception e) {
			System.out.println("-----e---"+e.getMessage());
			e.printStackTrace();
		}
		

		return "Hello";
	}

}
