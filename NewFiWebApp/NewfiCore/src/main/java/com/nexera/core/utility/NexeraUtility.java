package com.nexera.core.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class NexeraUtility {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(NexeraUtility.class);
	
	private static final String IMAGE_FORMAT="jpg";
    private static final String COLOR="rgb";
    private static final Integer RESOLUTION=256;
    private static final Integer IMAGETYPE=24;
	
	
	@SuppressWarnings("unchecked")
	public static List<PDPage> splitPDFPages(File file){
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
	
	public static String convertPDFToJPEG(File file ){
		
		List<PDPage> pdfPages = splitPDFPages(file);
		Integer pageNum = 0 ;
		for (PDPage pdPage : pdfPages) {
			
			try {
				 	PDDocument newDocument = new PDDocument();
	                newDocument.addPage(pdPage);

	                File newFile = new File(tomcatDirectoryPath() + File.separator + "_"+ pageNum +".pdf");
	                newFile.createNewFile();

	                newDocument.save(newFile);
	                newDocument.close();
	                pageNum++;
				   /* BufferedImage image = pdPage.convertToImage(BufferedImage.TYPE_INT_RGB,400);
				    ImageIO.write(image, "png", new File(tomcatDirectoryPath()+ File.separator + ((pageNum++) + 1) + ".jpg"));*/
			} catch (Exception e) {
				LOGGER.info("Exception in converting pdf pages document : "+e.getMessage());
			}
		}
		
		return "true";
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
 
                filePath = dir.getAbsolutePath()+ File.separator + file.getOriginalFilename();
                // Create the file on server
                File serverFile = new File(filePath);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                LOGGER.info("Server File Location="+ serverFile.getAbsolutePath());
 
               
            } catch (Exception e) {
            	LOGGER.info("Exception in uploading file in local "+e.getMessage());
                return null;
            }
        } else {
        	 return null;
        }
		return filePath;
	}
}
