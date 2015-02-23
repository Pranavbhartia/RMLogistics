package com.nexera.newfi.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.newfi.common.model.User;
import com.nexera.newfi.core.service.UserService;
import com.nexera.newfi.exception.UserNotFound;

@Controller
public class UserProfile {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "generalInfo.do")
	public ModelAndView showCustomerPage() {
		ModelAndView mav = new ModelAndView();
		User User = new User();
		mav.addObject("user", User);
		mav.setViewName("generalInfo");
		return mav;
	}

	@RequestMapping(value = "save.do", method = RequestMethod.POST)
	public String saveGeneralinfo(@ModelAttribute("user") User user,
			BindingResult result) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", user);
		try {
			userService.addUser(user);
		} catch (Exception e) {
			return "exceptions/error";
		}
		return "customerViewTemplate";
	}
	
	@ExceptionHandler(UserNotFound.class)
    public ModelAndView UserNotFoundException(HttpServletRequest request, Exception ex){
       // logger.error("Requested URL="+request.getRequestURL());
        //logger.error("Exception Raised="+ex);
         
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", ex);
        modelAndView.addObject("url", request.getRequestURL());
         
        modelAndView.setViewName("exceptions/error");
        return modelAndView;
    }   

}
