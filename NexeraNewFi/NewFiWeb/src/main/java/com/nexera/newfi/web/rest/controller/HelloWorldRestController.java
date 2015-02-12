package com.nexera.newfi.web.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.newfi.common.model.UserModel;
import com.nexera.newfi.core.service.UserService;

@RestController
@RequestMapping(value="newfi/user")
public class HelloWorldRestController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/getByUserId/{userId}" , method = RequestMethod.GET)
	public @ResponseBody String getUserById(@PathVariable String userId){
		UserModel user = userService.findByUserId(Integer.parseInt(userId));
		return new Gson().toJson(user);
		
	}
}
