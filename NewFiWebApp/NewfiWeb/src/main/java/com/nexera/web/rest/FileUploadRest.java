package com.nexera.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.NeedsListMasterVO;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.web.controller.FileUploadController;

@RestController
@RequestMapping("/fileupload")
public class FileUploadRest {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	
	@RequestMapping(value="/upload" , method = RequestMethod.POST ,  headers = "Accept=*")
	public @ResponseBody String uploadFileToS3Service(@RequestParam(value="file" , required = true) MultipartFile multipartFile , HttpServletRequest request , HttpServletResponse response){
		LOG.info("File upload Rest service called");
		return "true";
	}
	
	@RequestMapping(value="/needlist" , method = RequestMethod.GET)
	public @ResponseBody String uploadFileToS3Service(){
		LOG.info("File upload Rest service called");
		List<LoanNeedsListVO> listLoanNeedVo = new ArrayList<LoanNeedsListVO>();
		LoanNeedsListVO loanNeedsListVO1 = new LoanNeedsListVO();
		NeedsListMasterVO needsListMasterVO = new NeedsListMasterVO();
		needsListMasterVO.setId(1);
		needsListMasterVO.setDescription("Salaried-W-2 forms for the most recent 2 years");
		loanNeedsListVO1.setNeedsListMaster(needsListMasterVO);
		listLoanNeedVo.add(loanNeedsListVO1);
		
		LoanNeedsListVO loanNeedsListVO2 = new LoanNeedsListVO();
		NeedsListMasterVO needsListMasterVO2 = new NeedsListMasterVO();
		needsListMasterVO2.setId(2);
		needsListMasterVO2.setDescription("Payroll stubs for the past 30 days (showing YTD earnings)");
		loanNeedsListVO2.setNeedsListMaster(needsListMasterVO2);
		listLoanNeedVo.add(loanNeedsListVO2);
		
		LoanNeedsListVO loanNeedsListVO3 = new LoanNeedsListVO();
		NeedsListMasterVO needsListMasterVO3 = new NeedsListMasterVO();
		needsListMasterVO3.setId(3);
		needsListMasterVO3.setDescription("Refinance - Copy of property tax bill");
		loanNeedsListVO3.setNeedsListMaster(needsListMasterVO3);
		listLoanNeedVo.add(loanNeedsListVO3);
		
		LoanNeedsListVO loanNeedsListVO4 = new LoanNeedsListVO();
		NeedsListMasterVO needsListMasterVO4 = new NeedsListMasterVO();
		needsListMasterVO4.setId(4);
		needsListMasterVO4.setDescription("Refinance - Copy of homeowner's hazard insurance policy");
		loanNeedsListVO4.setNeedsListMaster(needsListMasterVO4);
		listLoanNeedVo.add(loanNeedsListVO4);
		
		
		Gson gson = new Gson();
		return gson.toJson(listLoanNeedVo);
		
	}

}
