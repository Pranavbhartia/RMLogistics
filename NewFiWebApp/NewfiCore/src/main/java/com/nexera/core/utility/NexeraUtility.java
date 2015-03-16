package com.nexera.core.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.mail.Multipart;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.PDFToImage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


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
	                
	                newDocument.save(filepath);
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
	
	public static String convertImageToPDF(MultipartFile multipartFile) {
		MultipartFile multipartPDF   = null;
		String filepath = null;
		try{
		File file = multipartToFile(multipartFile);
		
		PDDocument document = new PDDocument();
		
		InputStream in = new FileInputStream(file);
		BufferedImage bimg = ImageIO.read(in);
		
		float width = bimg.getWidth();
		float height = bimg.getHeight();
		
		PDPage page = new PDPage(new PDRectangle(width, height));
		document.addPage(page); 
		PDXObjectImage img = new PDJpeg(document, new FileInputStream(file));
		
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		contentStream.drawImage(img, 0, 0);
		
		contentStream.close();
		in.close();

	    filepath = tomcatDirectoryPath() + 
				File.separator +file.getName().replace(FilenameUtils.getExtension(file.getName()), "") +"pdf";
		
		
		LOGGER.info("filepath after convertin to PDF : "+filepath);
		
		
		File newFile = new File(filepath);
        newFile.createNewFile();
        
		document.save(filepath);
		document.close();
		
		}catch(Exception e){
			LOGGER.error("Exception in convertImageToPDF : "+e.getMessage());
			e.printStackTrace();
		}
		return filepath ;
		
	}
	
	public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
	        File convFile = new File( multipart.getOriginalFilename());
	        multipart.transferTo(convFile);
	        return convFile;
	}
	
	public static MultipartFile filePathToMultipart (String filePath) throws IOException{
		    File file = new File(filePath);
		    DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
		    fileItem.getOutputStream();
		    MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		    return multipartFile;
	}
	
	public static String convertImageToPDFDocument(MultipartFile multipartFile){
		
		File file = null;
		String filepath = null;
		PDDocument document = null;
        try
          {
        	  file = multipartToFile(multipartFile);
        	  document = PDDocument.load(file);
  
              //we will add the image to the first page.
              PDPage page = (PDPage)document.getDocumentCatalog().getAllPages().get( 0 );
  
              PDXObjectImage ximage = null;
              if(FilenameUtils.getExtension(file.getName()).toLowerCase().endsWith( ".jpg" ) )
              {
                  ximage = new PDJpeg(document, new FileInputStream( file ) );
              }
              else if (FilenameUtils.getExtension(file.getName()).toLowerCase().endsWith(".tif") || FilenameUtils.getExtension(file.getName()).toLowerCase().endsWith(".tiff"))
              {
                  ximage = new PDCcitt(document, new RandomAccessFile(file,"r"));
              }
              else
              {
                  //BufferedImage awtImage = ImageIO.read( new File( image ) );
                  //ximage = new PDPixelMap(doc, awtImage);
                  throw new IOException( "Image type not supported:" + FilenameUtils.getExtension(file.getName()) );
              }
              
              PDPageContentStream contentStream = new PDPageContentStream(document, page, true, true);
              float scale = 1f; // reduce this value if the image is too large
              contentStream.drawImage(ximage, ximage.getWidth()*scale, ximage.getHeight()*scale);
              filepath = tomcatDirectoryPath() + File.separator +file.getName().replace(FilenameUtils.getExtension(file.getName()), "") +".pdf";
              LOGGER.info("filepath after convertin to PDF : "+filepath);
     		
     		
     		  File newFile = new File(filepath);
              newFile.createNewFile();
             
     		  document.save(filepath);
     		  document.close();
          }catch(Exception e){
        	  LOGGER.error("Exception in convertImageToPDF : "+e.getMessage());
  			  e.printStackTrace();
          }
          finally
          {
              if( document != null )
              {
                 try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
          }
		return filepath;
        
	}
	
}