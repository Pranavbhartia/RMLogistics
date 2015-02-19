package com.nexera.newfi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TeaserRateController {

	@RequestMapping(value = "/teaserRateCalc")
	public String showOptions(Model map) {

		return "teaserRateCalc";
	}
}
