package com.nexera.extractor.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.extractor.constants.CommonConstant;
import com.nexera.extractor.entity.FileProductPointRate;
import com.nexera.extractor.entity.RestResponse;
import com.nexera.extractor.utility.Utility;

@Controller
public class ExtractCSV {

	@Autowired
	private Utility utility;
	
	@Autowired
	private CommonConstant commonConstant;

	@RequestMapping("/")
	public ModelAndView rates() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("rates");
		return mav;
	}
	
	@RequestMapping(value="/downloadXLS" )
	public void downloadXLSFile(HttpServletRequest request ,HttpServletResponse response ){
		File file = createXLSFile();
		OutputStream outStream = null;
		InputStream is = null;
		try 
		{
			 InputStream in = new BufferedInputStream(new FileInputStream(file));
			 response.setContentType("application/xlsx");
		     response.setHeader("Content-Disposition", "attachment; filename="+file.getName()); 


		     ServletOutputStream out = response.getOutputStream();
		     IOUtils.copy(in, out);
		     response.flushBuffer();
		}catch(Exception e){
			
		}finally{
			if(outStream!= null){
				try {
	                outStream.close();
                } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                }
			}
			if(is!= null){
				try {
	                is.close();
                } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                }
			}
			
			if(file.exists()){
				file.delete();
			}
		}
		
		
	}

	public File createXLSFile(){
		
		FileOutputStream out = null;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");
		
		final File folder = new File(commonConstant.FOLDER_PATH);
		// final File folder = new File("/apps/tmp/RateSheet Files/Price/");
		List<FileProductPointRate> list = utility.getFileProductlist(folder);
		Long folderLastModfied = folder.lastModified();
		RestResponse restResponse = utility.buildUIMap(list, folderLastModfied);
		
		utility.writeResponseDataSetToSheet(sheet, restResponse);
		File file = null;
		try {
			file = new File(tomcatDirectoryPath()+commonConstant.DEFAULT_FILE_NAME);
			out = new FileOutputStream(file);
            workbook.write(out);
            out.close();
            System.out.println("Excel written successfully..");
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(workbook!= null){
        		try {
	                workbook.close();
                } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
                }
        	}
        }
		return file;
		 
	}
	
	public String tomcatDirectoryPath() {
		String rootPath = System.getProperty("catalina.home");
		return rootPath + File.separator ;
	}
}
