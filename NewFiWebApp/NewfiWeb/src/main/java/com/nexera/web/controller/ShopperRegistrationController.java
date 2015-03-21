package com.nexera.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nexera.core.service.UserProfileService;

@RestController
@RequestMapping(value = "/shopper")
public class ShopperRegistrationController {

	@Autowired
	private UserProfileService userProfileService;
	
	private static final Logger LOG = LoggerFactory.getLogger(ShopperRegistrationController.class);
	
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public @ResponseBody String shopperRegistration(String registrationDetails) throws IOException {
		
		Gson gson = new Gson();
		LOG.info("registrationDetails - inout xml is"+registrationDetails);
		
		return gson.toJson(registrationDetails);
	}
	
}
