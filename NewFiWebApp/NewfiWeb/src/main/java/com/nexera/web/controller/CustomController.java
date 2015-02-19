package com.nexera.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexera.web.constants.JspLookup;

@Controller
public class CustomController extends DefaultController {

	@RequestMapping(value = "userlogin.do")
	public String showCustomerPage(HttpServletRequest req, Model model) {

		try {

			loadDefaultValues(model, req);

			return JspLookup.CUSTOM;

		} catch (IOException e) {
			// TODO: Handle exception scenario
			e.printStackTrace();
		}
		return JspLookup.ERROR;
	}

}
