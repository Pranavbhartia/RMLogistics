package com.nexera.extractor.rest;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.nexera.extractor.constants.CommonConstant;
import com.nexera.extractor.entity.FileExtractorResposne;
import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.RestResponse;
import com.nexera.extractor.utility.Utility;

@Controller
@RequestMapping("/rest")
public class ExtractorRateRest {

	@Autowired
	private Utility utility;
	
	@Autowired
	private CommonConstant commonConstant;
	private static final Log LOG = LogFactory.getLog(ExtractorRateRest.class);
	        
	@RequestMapping("/rates")
	public @ResponseBody String readFilesFromDestinationRest() {

		final File folder = new File(commonConstant.FOLDER_PATH);
		// final File folder = new File("/apps/tmp/RateSheet Files/Price/");
		List<FileProductPointRate> list = utility.getFileProductlist(folder);
		Long folderLastModfied = folder.lastModified();
		System.out.println("Folder TimeStamp before builUIMap......................" + folderLastModfied);
		LOG.info("Folder TimeStamp before builUIMap......................" + folderLastModfied);
		System.out.println("The last modified folder time stampe in LONG" + folderLastModfied);
		System.out.println("The last modified folder time stampe in UTC " + showUTCTime(folderLastModfied));
		RestResponse restResponse = utility.buildUIMap(list, folderLastModfied);
		System.out.println("response......................" + restResponse.getTimestamp());
		LOG.info("response......................" + restResponse.getTimestamp());
		// utility.getCompleteProductRateList(list);
		FileExtractorResposne fileResponse = new FileExtractorResposne();
		fileResponse.setFileDetailList(restResponse.getData());
		fileResponse.setFolderTimeStamp(restResponse.getTimestamp());
		System.out.println("FolderTimeStamp is in long is......................" + fileResponse.getFolderTimeStamp());
		LOG.info("FolderTimeStamp is in long is......................" + fileResponse.getFolderTimeStamp());
		Date date = new Date(restResponse.getTimestamp());
		fileResponse.setFolderTSDtFormat(date);
		System.out.println("FolderTimeStamp is in date format is......................" + fileResponse.getFolderTSDtFormat());
		LOG.info("FolderTimeStamp is in date format is......................" + fileResponse.getFolderTSDtFormat());
		fileResponse.setFolderTSDsFormat(String.valueOf(restResponse.getTimestamp()));
		LOG.info("FolderTimeStamp is in date format is......................" + fileResponse.getFolderTSDtFormat());
		Gson gson = new Gson();
		
		return gson.toJson(fileResponse);
	}
	 public String showUTCTime(long millis)
	    {
	        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
	        cal.setTimeInMillis(0);
	        //System.out.println( );
	        return "(UTC):" + cal.getTime();
	    } 

}
