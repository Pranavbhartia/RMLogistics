package com.nexera.extractor.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.extractor.entity.YearBasedRate;
import com.nexera.extractor.utility.Utility;

@Controller
public class ExtractCSV {

	@Autowired
	private Utility utility;

	@RequestMapping("/extract.do")
	public ModelAndView extract() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("myName", "Hello !! Akash here ");
		final File folder = new File("/apps/tmp/RateSheet Files/Price");
		List<Map<String, List<YearBasedRate>>> map = utility
		        .readFilesFromDestination(folder);

		mav.setViewName("extract");
		return mav;
	}

	@RequestMapping("/")
	public ModelAndView rates() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rates");
		return mav;
	}
	
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rates");
		return mav;
	}

}
