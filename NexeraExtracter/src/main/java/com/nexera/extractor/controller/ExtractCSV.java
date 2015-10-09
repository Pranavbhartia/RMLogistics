package com.nexera.extractor.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nexera.extractor.constants.CommonConstant;
import com.nexera.extractor.entity.FileProductPointRate;
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
		mav.addObject("baseURL", CommonConstant.BASE_URL);
		mav.setViewName("rates");
		return mav;
	}

	@RequestMapping(value = "/downloadXLS/{timezone}")
	public void downloadXLSFile(HttpServletRequest request,
	        HttpServletResponse response,@PathVariable String timezone) {
		File file = createXLSFile(timezone);
		OutputStream outStream = null;
		InputStream is = null;
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			response.setContentType("application/xlsx");
			response.setHeader("Content-Disposition", "attachment; filename="
			        + file.getName());

			ServletOutputStream out = response.getOutputStream();
			IOUtils.copy(in, out);
			response.flushBuffer();
		} catch (Exception e) {

		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (file.exists()) {
				file.delete();
			}

			File tempfile = new File(CommonConstant.COPIED_TEMPELATE);
			if (tempfile.exists()) {
				tempfile.delete();
			}
			File tempDir = new File(
			        CommonConstant.TEMP_PATH_FOR_COPIED_TEMPELATE);
			if (tempDir.exists()) {
				tempDir.delete();
			}
		}

	}

	public File createXLSFile(String timezone) {

		FileOutputStream out = null;
		File file = null;
		HSSFWorkbook workbook = null;

		final File folder = new File(commonConstant.FOLDER_PATH);
		// final File folder = new File("/apps/tmp/RateSheet Files/Price/");
		List<FileProductPointRate> list = utility.getFileProductlist(folder);
		Long folderLastModfied = folder.lastModified();

		// utility.writeResponseDataSetToSheet(sheet, restResponse);
		workbook = utility.buildUIComponent(list, folderLastModfied, workbook,timezone);
		
		
		try {
			/*
			 * file = new File(tomcatDirectoryPath() +
			 * commonConstant.DEFAULT_FILE_NAME);
			 */
			file = new File(CommonConstant.COPIED_TEMPELATE);
			out = new FileOutputStream(file);
			if (null != workbook) {
				workbook.write(out);

			}
			if (null != out) {
				out.flush();
				out.close();
			}

			System.out.println("Excel written successfully..");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
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
		return rootPath + File.separator;
	}
}
