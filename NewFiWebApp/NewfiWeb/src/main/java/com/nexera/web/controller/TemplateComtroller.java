package com.nexera.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.web.constants.JspLookup;

@Controller
public class TemplateComtroller {

	 @RequestMapping(value="customerViewTemplate.do")
	 public ModelAndView showCustomerPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName(JspLookup.CUSTOMER_VIEW);
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="customerLoanPage.do")
	 public ModelAndView showCustomerLaonPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("cutomerViewMainBody");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="messageDashboard.do")
	 public ModelAndView showCustomerMessageDashboard(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("customerViewMessageDashboard");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="agentPage.do")
	 public ModelAndView showAgentPage(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("agentViewTemplate");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
	 
	 @RequestMapping(value="agentDashboard.do")
	 public ModelAndView agentDashboard(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("agentViewDashboard");
			//mav.addObject("myName", userService.getName());
			return mav;
		}
}
