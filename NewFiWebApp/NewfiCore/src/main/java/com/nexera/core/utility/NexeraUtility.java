package com.nexera.core.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.PDFToImage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class NexeraUtility {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NexeraUtility.class);
	
	private static final String OUTPUT_FILENAME_EXT = "jpg";
	private static final String OUTPUT_PREFIX = "-outputPrefix";
	private static final String END_PAGE = "-endPage";

	private static final String PAGE_NUMBER = "1";
	private static final String START_PAGE = "-startPage";
	
	@SuppressWarnings("unchecked")
	public static List<PDPage> splitPDFTOPages(File file){
		PDDocument document = null;
		List<PDPage> pdfPages = null;
		// Create a Splitter object
       try {
			document = new PDDocument();
			document = PDDocument.loadNonSeq(file, null);
			return document.getDocumentCatalog().getAllPages();
		} catch (IOException e) {
			LOGGER.info("Exception in splitting pdf document : "+e.getMessage());
		}
		return pdfPages;
	}
	
	public static List<File> splitPDFPages(File file ){
		
		List<PDPage> pdfPages = splitPDFTOPages(file);
		List<File> newPdfpages = new ArrayList<File>();
		Integer pageNum = 0 ;
		for (PDPage pdPage : pdfPages) {
			
			try {
				 	PDDocument newDocument = new PDDocument();
	                newDocument.addPage(pdPage);
	                String filepath = tomcatDirectoryPath() + File.separator +file.getName().replace(".pdf", "") +"_"+ pageNum +".pdf";
	               
	                File newFile = new File(filepath);
	                newFile.createNewFile();
	                
	                newDocument.save(newFile);
	                newDocument.close();
	                pageNum++;
	                
	                newPdfpages.add(newFile);
			} catch (Exception e) {
				LOGGER.info("Exception in converting pdf pages document : "+e.getMessage());
			}
		}
		
		return newPdfpages;
	}
	
	
	
	public static String tomcatDirectoryPath(){
		String rootPath = System.getProperty("catalina.home");
		return rootPath + File.separator + "tmpFiles";
	}
	
	
	public static String uploadFileToLocal(MultipartFile file){
		 String filePath = null;
		if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
              
                File dir = new File(NexeraUtility.tomcatDirectoryPath());
                if (!dir.exists())
                    dir.mkdirs();
 
                String fileName =  file.getOriginalFilename();
                
                filePath = dir.getAbsolutePath()
                        + File.separator + fileName;
                // Create the file on server
                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                LOGGER.info("Server File Location="
                        + serverFile.getAbsolutePath());
 
               
            } catch (Exception e) {
            	LOGGER.info("Exception in uploading file in local "+e.getMessage());
                return null;
            }
        } else {
        	 return null;
        }
		return filePath;
	}
	
	
	public static String joinPDDocuments(List<String> fileUrls) throws IOException, COSVisitorException{
		PDFMergerUtility mergePDF = new PDFMergerUtility();
		String newFilePath = NexeraUtility.tomcatDirectoryPath()+File.separator+randomStringOfLength()+".pdf";
		
		for (String fileUrl : fileUrls) {
			LOGGER.info("Adding File with URL" + fileUrl);
			mergePDF.addSource(new File(fileUrl));
		}
		mergePDF.setDestinationFileName(newFilePath);
		mergePDF.mergeDocuments();
		return newFilePath;
	}
	
	public static String randomStringOfLength() {
		Integer length = 10;
	    StringBuffer buffer = new StringBuffer();
	    while (buffer.length() < length) {
	        buffer.append(uuidString());
	    }

	    //this part controls the length of the returned string
	    return buffer.substring(0, length);  
	}

	
	public static String convertPDFToThumbnail(String pdfFile, String imageFilePath)
			throws Exception {

		String[] args = new String[7];
		args[0] = START_PAGE;
		args[1] = PAGE_NUMBER;
		args[2] = END_PAGE;
		args[3] = PAGE_NUMBER;
		args[4] = OUTPUT_PREFIX;
		args[5] = imageFilePath+File.separator;
		args[6] = pdfFile;

		try {
			File file = new File(pdfFile);
			String fileName = file.getName().replace(FilenameUtils.getExtension(file.getName()), "");
			PDFToImage.main(args);
			String imageFile = imageFilePath+File.separator + PAGE_NUMBER+"." +OUTPUT_FILENAME_EXT;
			LOGGER.info("Image path for thumbnail : "+imageFile);
			return imageFile;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	private static String uuidString() {
	    return UUID.randomUUID().toString().replaceAll("-", "");
	}
}