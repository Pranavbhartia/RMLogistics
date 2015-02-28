package com.nexera.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexera.common.entity.User;
import com.nexera.common.enums.UserRolesEum;
import com.nexera.common.vo.LoansProgressStatusVO;
import com.nexera.core.service.LoanService;
import com.nexera.web.constants.JspLookup;

@Controller
public class TemplateController extends DefaultController {

	@Autowired
	LoanService loanService;
	
	@RequestMapping(value = "home.do")
	public String showCustomerPage(HttpServletRequest req, Model model) {

		
		try {

			User user = loadDefaultValues(model, req);

			if (UserRolesEum.CUSTOMER.toString().equals(user.getUserRole().getRoleCd())) {
				return JspLookup.CUSTOMER_VIEW;
			}else{
				LoansProgressStatusVO loansProgressStatusVO =loanService.getLoansProgressForUser(Integer.valueOf(user.getId()));
				model.addAttribute("progressVO",loansProgressStatusVO);
				return JspLookup.AGENT_VIEW;
			}

			//return JspLookup.CUSTOM;

		} catch (IOException e) {
			// TODO: Handle exception scenario
			e.printStackTrace();
		}
		return JspLookup.ERROR;
	}
	//
	// @RequestMapping(value="customerLoanPage.do")
	// public ModelAndView showCustomerLaonPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("cutomerViewMainBody");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="messageDashboard.do")
	// public ModelAndView showCustomerMessageDashboard(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("customerViewMessageDashboard");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentPage.do")
	// public ModelAndView showAgentPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewTemplate");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentDashboard.do")
	// public ModelAndView agentDashboard(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewDashboard");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value="agentLoanPage.do")
	// public ModelAndView agentMainBody(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("agentViewMainBody");
	// //mav.addObject("myName", userService.getName());
	// return mav;
	// }
	//
	// @RequestMapping(value = "customerUploadRequiredFiles.do")
	// public ModelAndView customerUploadRequiredFiles(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName(JspLookup.CUSTOMER_UPLOAD_DOCUMENT);
	// return mav;
	// }
	//
	//
	// @RequestMapping(value = "video.do")
	// public ModelAndView showVideoPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("videoTest");
	// return mav;
	// }
	//
	// @RequestMapping(value = "home.do")
	// public ModelAndView showLoginPage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("login");
	// return mav;
	// }
	//
	//
	// @RequestMapping(value = "customerProfile.do")
	// public ModelAndView showCustomerProfilePage(){
	// ModelAndView mav = new ModelAndView();
	// mav.setViewName("customerProfile");
	// return mav;
	// }
}
