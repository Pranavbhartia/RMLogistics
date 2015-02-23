package com.nexera.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.core.utility.NexeraUtility;

@Controller
public class FileUploadController {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	@RequestMapping(value = "documentUpload.do" , method = RequestMethod.POST  )
	public @ResponseBody String  fileUploadToS3System( @RequestParam("file") MultipartFile file){
		String s3Path = null;
		 try{
			 
			 
			File serverFile = new File( uploadFileToLocal(file));
			//s3Path = s3FileUploadServiceImpl.uploadToS3(serverFile, "User" , "complete" );
			NexeraUtility.convertPDFToJPEG(serverFile);
			
		 }catch(Exception e){
			 LOG.info(" Exception uploading s3 :  "+e.getMessage());
		 }
		 LOG.info("file.getOriginalFilename() : "+file.getOriginalFilename());
		 
		 LOG.info("The s3 path is : "+s3Path);
		 return uploadFileToLocal(file);
	} 
	
	
	
	
	public String uploadFileToLocal(MultipartFile file){
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
              
                File dir = new File(NexeraUtility.tomcatDirectoryPath());
                if (!dir.exists())
                    dir.mkdirs();
 
                String filePath = dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename();
                // Create the file on server
                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                LOG.info("Server File Location="
                        + serverFile.getAbsolutePath());
 
                return filePath;
            } catch (Exception e) {
                LOG.info("Exception in uploading file in local "+e.getMessage());
                return null;
            }
        } else {
            return null;
        }
	}
}
