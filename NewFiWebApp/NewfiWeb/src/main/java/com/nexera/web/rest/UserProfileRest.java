package com.nexera.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
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

		try {
			//user = userProfileService.findUser(userid);
			gson.toJson(user);

		} catch (Exception e) {
			System.out.println(e.getMessage());

		}

		return gson.toJson(user);
		//return "Hello";
	}

}
