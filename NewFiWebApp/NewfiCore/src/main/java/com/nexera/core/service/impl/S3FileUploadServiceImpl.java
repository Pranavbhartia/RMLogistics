/**
 *  * Copyright (c) 2014 RareMile Technologies. 
 * All rights reserved. 
 * 
 * No part of this document may be reproduced or transmitted in any form or by 
 * any means, electronic or mechanical, whether now known or later invented, 
 * for any purpose without the prior and express written consent. 
 *
 */
package com.nexera.core.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import eu.medsea.mimeutil.MimeUtil;
/**
 * @author Balaji Mohan
 * @created 05-Aug-2014
 * 
 * Brief: Write a quick description of what the class is supposed to do.
 * 
 */
@Component
public class S3FileUploadServiceImpl implements InitializingBean {

	@Value("${bucketName}")
	private String fileBucket;

	@Value("${s3BaseUrl}")
	private String s3BaseUrl;

	@Value("${secretKey}")
	private String secretKey;

	@Value("${accessKey}")
	private String accessKey;

	
	//private static final String FILE_BUCKET = "x3-profile-img";
	private String uniqueBucketName;
	//private static final String S3_BASE_URL = "https://s3.amazonaws.com/";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(S3FileUploadServiceImpl.class);

	private static Calendar calendar = Calendar.getInstance();
	{
		calendar.add(Calendar.YEAR, 5);
	}

	public String uploadToS3(File file,String subfolderInBucket , String prefix ) {
		LOGGER.info("Trying to upload : +"+subfolderInBucket+" --- "+prefix+"  in document");
		LOGGER.info("fileBucket: +"+fileBucket+" s3BaseUrl-- "+s3BaseUrl+"  secretKey - "+secretKey  + " accessKey "+accessKey);
		Collection<?> mimeTypes = MimeUtil.getMimeTypes(file);
		/*if (mimeTypes == null || !mimeTypes.toString().contains("image/")) {

			LOGGER.error("Uploaded file was not a image. "+file.getName());
			return null;

		}
		String thumbnailName = "_thumb_" + file.getName().replaceAll(" ", "_");

		File thumbnail = new File(file.getParent() + System.getProperty("file.separator")
				+ thumbnailName);
		boolean conversion = false;
		try {
			Thumbnails.of(file).size(Integer.parseInt("300"),Integer.parseInt("300")).toFile(thumbnail);
			conversion = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File imageFile = null;
		if(!conversion){
			imageFile = file;
		}else{
			imageFile = thumbnail;
		}*/
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3 = new AmazonS3Client(awsCredentials);
		//s3.setEndpoint(s3BaseUrl);
		//s3.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
		uniqueBucketName = (accessKey + "-" + fileBucket).toLowerCase();
		boolean bucketExists = s3.doesBucketExist(uniqueBucketName);
		if (!bucketExists) {
			s3.createBucket(uniqueBucketName);
		}

		
		if(subfolderInBucket==null){
			long random =generateRandomString();
			subfolderInBucket=""+random;
		}
		String key = null;
		if(prefix != null){
			key = subfolderInBucket + "\\"+prefix +file.getName().replaceAll(" ", "_");
		}else{
			key = subfolderInBucket + "\\" +file.getName().replaceAll(" ", "_");
		}
	  

		PutObjectRequest putObjectRequest = new PutObjectRequest(
				uniqueBucketName, key, file);

		putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setCacheControl("public");
		metadata.setExpirationTime(getCacheExpiryDate());
		putObjectRequest.setMetadata(metadata);

		s3.putObject(putObjectRequest);

		return s3BaseUrl + uniqueBucketName + "/"+key;

	}
	
	public Boolean deleteImageFromS3(String url) {
		
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3 = new AmazonS3Client(awsCredentials);
		String uniqueBucketName = (accessKey + "-" + fileBucket).toLowerCase();
		String toRemove=s3BaseUrl+uniqueBucketName;
		String key=url.substring(toRemove.length()+1);
		try {
			s3.deleteObject(uniqueBucketName, key);
		} catch (Exception e) {
			return false;
		}
		return true;
		
	}

	public String uploadProtectedFileToS3(File file, String absolutePath) {

		
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 s3 = new AmazonS3Client(awsCredentials);

		uniqueBucketName = (accessKey + "-" + fileBucket).toLowerCase();
		boolean bucketExists = s3.doesBucketExist(uniqueBucketName);
		if (!bucketExists) {
			s3.createBucket(uniqueBucketName);
		}

		String key = absolutePath.substring(1, absolutePath.length());
		PutObjectRequest putObjectRequest = new PutObjectRequest(
				uniqueBucketName, key, file);

		putObjectRequest.setCannedAcl(CannedAccessControlList.Private);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setCacheControl("public");

		metadata.setExpirationTime(getCacheExpiryDate());
		putObjectRequest.setMetadata(metadata);

		s3.putObject(putObjectRequest);

		return s3BaseUrl + uniqueBucketName + absolutePath;

	}

	private Date getCacheExpiryDate() {
		// TODO Auto-generated method stub

		// date.
		return calendar.getTime();

	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");

	}

	public String downloadFile(String fileUrl, String fileName)
			throws Exception {
		ClasspathPropertiesFileCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
		String accessKey = credentialsProvider.getCredentials()
				.getAWSAccessKeyId();
		AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
		uniqueBucketName = (accessKey + "-" + fileBucket).toLowerCase();
		String key = fileUrl.replace(s3BaseUrl + uniqueBucketName, "");
		key = key.substring(1, key.length());
		GetObjectRequest getObjectRequest = new GetObjectRequest(
				uniqueBucketName, key);
		S3Object s3Object = s3.getObject(getObjectRequest);
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(s3Object.getObjectContent());

			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					fileName));
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0)
				out.write(buf, 0, len);
			out.flush();
			out.close();

			return fileName;
		}/* catch (IOException e) {

			throw new Exception(e);
		} */finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Error closing Amazon stream while downloading: "
							+ fileUrl);
				}
			}
		}

	}
	
	public static long generateRandomString() {
		// TODO Auto-generated method stub
		UUID uuid = UUID.randomUUID();
		return uuid.getMostSignificantBits();
	}
}
