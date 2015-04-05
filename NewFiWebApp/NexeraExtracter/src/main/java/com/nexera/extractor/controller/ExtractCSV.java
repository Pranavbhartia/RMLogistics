package com.nexera.extractor.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.nexera.extractor.entity.ProductPointRate;
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
		final File folder = new File("C:\\Users\\Akash\\Downloads\\RateSheetFiles\\RateSheet Files\\Price");
		List<Map<String, List<YearBasedRate>>> map = utility.readFilesFromDestination(folder);
		
		mav.setViewName("extract");
		return mav;
	}
	
	
	@RequestMapping("/rates.do")
	 public ModelAndView rates() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rates");
		return mav;
	}
	
	
	
}
