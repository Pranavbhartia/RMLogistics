package com.nexera.extractor.rest;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.ProductPointRate;
import com.nexera.extractor.utility.Utility;

@Controller
@RequestMapping("/rest")
public class ExtractorRateRest {
	
	@Autowired
	private Utility utility;

	@RequestMapping("/rates")
	public @ResponseBody String readFilesFromDestinationRest(){
		final File folder = new File("C:\\Users\\Akash\\Downloads\\RateSheetFiles\\RateSheet Files\\Price");
		List<FileProductPointRate> list = utility.getFileProductlist(folder);
		
		//List<Map<String, List<ProductPointRate>>> map= utility.getCompleteProductRateList(list);
		
		Gson gson = new Gson();
		return gson.toJson(list);
	}
}
