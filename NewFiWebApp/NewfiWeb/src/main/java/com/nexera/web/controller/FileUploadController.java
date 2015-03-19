package com.nexera.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.nexera.common.entity.User;
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
	
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "documentUpload.do" , method = RequestMethod.POST  )
	public @ResponseBody String  filesUploadToS3System( @RequestParam("file") MultipartFile[] file 
										, @RequestParam("userID") Integer userID 
										, @RequestParam("loanId") Integer loanId 
										, @RequestParam("assignedBy") Integer assignedBy){
		LOG.info("in document upload  wuth user id "+userID + " and loanId :"+loanId+" and assignedBy : "+assignedBy);
		List<String> s3paths = new ArrayList<String>();
		for (MultipartFile multipartFile : file) {
			s3paths.add(uploadFile(multipartFile, userID , loanId , assignedBy) );
		}
		return new Gson().toJson(s3paths);
	} 
	
	public String uploadFile(MultipartFile file , Integer userId , Integer loanId , Integer assignedBy){
		String s3Path = null;
		
		LOG.info("File content type  : "+file.getContentType());
		String localFilePath = null;
		try{
			if(file.getContentType().equalsIgnoreCase("image/png") || file.getContentType().equalsIgnoreCase("image/jpeg") || file.getContentType().equalsIgnoreCase("image/tiff")){
				LOG.info("Received an image.converting to PDF");
				localFilePath = nexeraUtility.convertImageToPDF(file);
			}else{
				localFilePath = nexeraUtility.uploadFileToLocal(file);
			}
			 nexeraUtility.uploadFileToLocal(file);
			File serverFile = new File(localFilePath );
			Integer savedRowId = uploadedFilesListService.addUploadedFilelistObejct(serverFile , loanId , userId , assignedBy);
			LOG.info("Added File document row : "+savedRowId);
		 }catch(Exception e){
			 LOG.info(" Exception uploading s3 :  "+e.getMessage());
		 }
		 LOG.info("file.getOriginalFilename() : "+file.getOriginalFilename());
		 
		 LOG.info("The s3 path is : "+s3Path);
		 return s3Path;
	}
	
	private User getUserObject() {
		final Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		if (principal instanceof User) {
			return (User) principal;
		} else {
			return null;
		}

	}
	
	
	@RequestMapping(value = "/readFileAsStream.do" , method = RequestMethod.GET) 
	public void doDownload(HttpServletRequest request, HttpServletResponse response , @RequestParam ("s3FileId") String  s3FileId) throws Exception {
	       String s3FileURL = uploadedFilesListService.fetchUsingFileUUID(s3FileId).getS3path();
	       LOG.info("The s3path = "+s3FileURL);
	       String localFilePath = nexeraUtility.tomcatDirectoryPath()+File.separator+nexeraUtility.randomStringOfLength()+".pdf";
	       
		   File downloadFile = new File(s3FileUploadServiceImpl.downloadFile(s3FileURL , localFilePath));
		   FileInputStream inputStream = new FileInputStream(downloadFile);

		  
		   response.setContentType("application/pdf");

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
