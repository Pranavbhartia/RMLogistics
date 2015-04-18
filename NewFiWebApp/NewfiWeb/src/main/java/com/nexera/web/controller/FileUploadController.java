package com.nexera.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nexera.common.commons.Utils;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;

@Controller
public class FileUploadController {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private UploadedFilesListService uploadedFilesListService;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	private Utils utils;

	private static final Logger LOG = LoggerFactory
	        .getLogger(FileUploadController.class);

	@RequestMapping(value = "/readFileAsStream.do", method = RequestMethod.GET)
	public void doDownload(HttpServletRequest request,
	        HttpServletResponse response, @RequestParam("uuid") String uuid,
	        @RequestParam("isThumb") String isThumb) throws Exception {

		String fileURL = null;
		UploadedFilesList uplList = uploadedFilesListService
		        .fetchUsingFileUUID(uuid);
		fileURL = uplList.getS3path();
		response.setContentType("application/pdf");
		uploadedFilesListService.getFileContentFromLQBUsingUUID(response,
			        uuid);
			
				
	}
		
	@RequestMapping(value = "/readImageAsStream.do", method = RequestMethod.GET)
	public @ResponseBody byte[] showImage(HttpServletRequest request,
		        HttpServletResponse response, @RequestParam("uuid") String uuid,
		        @RequestParam("isThumb") String isThumb) throws Exception {

			String fileURL = null;
			UploadedFilesList uplList = uploadedFilesListService
			        .fetchUsingFileUUID(uuid);
			
			fileURL = uplList.getS3ThumbPath();
			if(fileURL == null)
				return null;
			byte[] bytes = s3FileUploadServiceImpl.getInputStreamOfFile(fileURL);
			return bytes;
			

	}

}
