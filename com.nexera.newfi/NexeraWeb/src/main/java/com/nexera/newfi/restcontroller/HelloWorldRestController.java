package com.nexera.newfi.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexeracommon.newfi.model.UserModel;
import com.nexeracore.newfi.service.UserService;

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
