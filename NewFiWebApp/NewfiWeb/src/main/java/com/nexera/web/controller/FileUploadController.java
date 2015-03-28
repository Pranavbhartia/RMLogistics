package com.nexera.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	/*@RequestMapping(value = "documentUpload.do" , method = RequestMethod.POST  )
	public @ResponseBody String  filesUploadToS3System( @RequestParam("file") MultipartFile[] file 
										, @RequestParam("userID") Integer userID 
										, @RequestParam("loanId") Integer loanId 
										, @RequestParam("assignedBy") Integer assignedBy){
		
		LOG.info("Checking for User Session : ");
		
		User user=utils.getLoggedInUser();
		if(user==null){
			
			return new Gson().toJson(RestUtil.wrapObjectForFailure(null, "403", "User Not Logged in."));
		}
		
		
		LOG.info("in document upload  wuth user id "+userID + " and loanId :"+loanId+" and assignedBy : "+assignedBy);
		List<String> unsupportedFile = new ArrayList<String>();
		for (MultipartFile multipartFile : file) {
			Boolean isFileUploaded = uploadFile(multipartFile, userID , loanId , assignedBy);
			if(!isFileUploaded){
				unsupportedFile.add( multipartFile.getOriginalFilename());
			}
			
		}
		return new Gson().toJson(unsupportedFile);
	} */
	
	
	
	
	
	@RequestMapping(value = "/readFileAsStream.do" , method = RequestMethod.GET) 
	public void doDownload(HttpServletRequest request, HttpServletResponse response 
				, @RequestParam ("uuid") String  uuid , @RequestParam ("isThumb") String   isThumb) throws Exception {
	      
		   String fileURL = null;
		   UploadedFilesList uplList = uploadedFilesListService.fetchUsingFileUUID(uuid);
		   if(isThumb.equals("0")){
			   fileURL = uplList.getS3path();
			   response.setContentType("application/pdf");
		   }else{
			   fileURL = uplList.getS3ThumbPath();
			   response.setContentType("image/jpeg");
		   }
		   
	       LOG.info("The s3path = "+fileURL);
	      
		  // File downloadFile = new File(s3FileUploadServiceImpl.downloadFile(s3FileURL , localFilePath));
		   InputStream inputStream = s3FileUploadServiceImpl.getInputStreamFromFile(fileURL , isThumb);

		  
		  

		   // get output stream of the response
		   OutputStream outStream = response.getOutputStream();

		   byte[] buffer = new byte[2048];
		   int bytesRead = -1;

		   // write bytes read from the input stream into the output stream
		   while ((bytesRead = inputStream.read(buffer)) != -1) {
		       outStream.write(buffer, 0, bytesRead);
		   }

		   inputStream.close();
		   outStream.close();
	}
	
	
}
