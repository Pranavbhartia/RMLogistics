package com.nexera.newfi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.newfi.core.service.UserService;

@Controller
public class HelloWorldController {

	 @Autowired
	 private UserService userService;
	
	 @RequestMapping(value="newIndex.do")
	 public ModelAndView showIndex1(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("display");
			mav.addObject("myName", userService.getName());
			return mav;
		}
	 @RequestMapping(value="customerPage.do")
	 public ModelAndView showCustomerPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("customerViewTemplate");
			mav.addObject("myName", userService.getName());
			return mav;
		}
}
