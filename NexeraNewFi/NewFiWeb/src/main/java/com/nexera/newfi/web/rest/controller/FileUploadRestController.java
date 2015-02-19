package com.nexera.newfi.web.rest.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.newfi.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.newfi.core.utility.NewfiUtility;

@Controller
@RequestMapping(value="/newfi")
@PropertySource("classpath:core-application.properties")
public class FileUploadRestController {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadService;

	@Value("${tempDir}")
	private String tempDir;

	@Value("${tempBlurDir}")
	private String tempBlurDir;

	@Value("${cropPathDir}")
	private String cropPathDir;

	@Value("${imageHeight}")
	private String imageHeight;

	@Value("${imageWidth}")
	private String imageWidth;
	
	@RequestMapping(value="/file-upload", method=RequestMethod.POST)
	public @ResponseBody Boolean fileUploader(
            @RequestParam("file") MultipartFile multipartFile){
		
		String orgName = multipartFile.getOriginalFilename();
		File dirPath = new File(tempDir);

		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		
		String filePath = tempDir + File.separator
				+ NewfiUtility.generateRandomString() + orgName;

		String bucketFolder = "UserImages" + "/" + "Nexera" + "Newfi";
		String s3Path = "error";
		try {
			InputStream is = multipartFile.getInputStream();
			byte[] b = multipartFile.getBytes();
			File dest = new File(filePath);
			multipartFile.transferTo(dest);
			s3Path = s3FileUploadService.uploadToS3(dest, bucketFolder, null);
			System.out.println("the s3 path is "+s3Path);
			if (s3Path == null) {
				throw new IOException();
			}
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		
		return true;
	}
		
	public void splitPDFIntoPages( File serverFile){
		
		
		 
		 System.out.println("File path to read pdf : "+serverFile.getAbsolutePath());
		 try {
		
			PDDocument  document = PDDocument.load(serverFile);
			List<PDPage> pages = document.getDocumentCatalog().getAllPages();
			for (int i = 0; i < pages.size(); i++) {
				  PDPage page = pages.get(i);
				  BufferedImage image = page.convertToImage(BufferedImage.TYPE_INT_RGB, 128);
				  ImageIO.write(image, "jpg", new File(serverFile.getAbsolutePath() + "_" + i + ".jpg"));
				}
		} catch (IOException e) {
			System.out.println("IOException "+e.getMessage());
		}
		
		 
	}
}
