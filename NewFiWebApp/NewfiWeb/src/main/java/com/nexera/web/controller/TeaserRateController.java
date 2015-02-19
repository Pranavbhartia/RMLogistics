package com.nexera.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexera.web.constants.JspLookup;

@Controller
public class TeaserRateController {

	@RequestMapping(value = "teaserRateCalc.do")
	public String showOptions(Model map) {

		return JspLookup.TEASER_RATE_CALC;
	}
}
