package com.nexera.web.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.FileAssignVO;
import com.nexera.common.vo.LoanNeedsListVO;
import com.nexera.common.vo.UploadFileScreenVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;


@RestController
@RequestMapping("/fileupload")
public class FileUploadRest {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileUploadRest.class);
	
	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	@Autowired
	private NeedsListService needsListService;
	
	@Autowired 
	private LoanService loanService;
	
	@Autowired
	private UploadedFilesListService uploadedFilesListService;
	
	
	@RequestMapping(value="/upload" , method = RequestMethod.POST ,  headers = "Accept=*")
	public @ResponseBody String uploadFileToS3Service(@RequestParam(value="file" , required = true) MultipartFile multipartFile , HttpServletRequest request , HttpServletResponse response){
		LOG.info("File upload Rest service called");
		return "true";
	}
	
	
	@RequestMapping(value="/loadneedlist/get" , method = RequestMethod.GET)
	public @ResponseBody String getLoanNeedList(){
		return new Gson().toJson(needsListService.getLoanNeedsList(1));
	}
	
	@RequestMapping(value="/assignment" , method = RequestMethod.POST)
	public @ResponseBody String setAssignmentToFiles(@RequestBody  String fileAssignMent){
		
		ObjectMapper mapper=new ObjectMapper();
		TypeReference<List<FileAssignVO>> typeRef=new TypeReference<List<FileAssignVO>>() {};
		List<FileAssignVO> val = new ArrayList<FileAssignVO>();
		try {
			val = mapper.readValue(fileAssignMent, typeRef);
			for (FileAssignVO fileAssignVO2 : val) {
				LOG.info("The file is : "+fileAssignVO2.getFileId()+"  nedd id "+fileAssignVO2.getNeedListId());
				uploadedFilesListService.updateIsAssignedToTrue( fileAssignVO2.getFileId());
				uploadedFilesListService.updateFileInLoanNeedList(fileAssignVO2.getNeedListId(), fileAssignVO2.getFileId());
			}
		} catch (Exception e) {
			LOG.info("exception in converting  : " + e.getMessage());
		} 
	   return "true";
	}
	
	@RequestMapping(value="/uploadedFile/get/{userId}/{loadId}" , method = RequestMethod.GET)
	public @ResponseBody String getUserUploadedFileList(@PathVariable ("userId") Integer userId , @PathVariable ("loadId") Integer loadId){
		LOG.info("getUserUploadedFileList called");
		List<UploadedFilesListVO> listUploadedFileVO = null;
		try {
			listUploadedFileVO = uploadedFilesListService.fetchAll(userId ,  loadId);
		} catch (Exception e) {
			LOG.info("getUserUploadedFileList exception  called"+e.getMessage());
			listUploadedFileVO = Collections.EMPTY_LIST;
		}
		return new Gson().toJson(listUploadedFileVO);
	}
	
	
	@RequestMapping(value="/needlist/get/{userId}/{loanId}" , method = RequestMethod.GET)
	public @ResponseBody String getNeedList(@PathVariable ("userId") Integer userId ,
													@PathVariable ("loanId") Integer loanId){
		LOG.info("File upload Rest service called");
		
		UserVO userVo = new UserVO();
		userVo.setId(userId);
		
		
		CommonResponseVO commonResponseVO = new CommonResponseVO();
		List<LoanNeedsListVO> loanNeedsVO;
		List<UploadedFilesListVO> uploadedFilesList;
		UploadFileScreenVO uploadFileScreenVO = new  UploadFileScreenVO();
		try {
			loanNeedsVO = needsListService.getLoanNeedsList(loanId);
			
			uploadedFilesList = uploadedFilesListService.fetchAll(userId, loanId);
			for (UploadedFilesListVO uploadedFilesListVO : uploadedFilesList) {
				Integer needType = needsListService.getLoanNeedListIdByFileId(uploadedFilesListVO.getId());
				LOG.info("The need type is : "+needType);
				uploadedFilesListVO.setNeedType(needType);
				
			}
			
			uploadFileScreenVO.setListLoanNeedsListVO(loanNeedsVO);
			uploadFileScreenVO.setListUploadedFilesListVO(uploadedFilesList);
			commonResponseVO.setResultObject(uploadFileScreenVO);
		} catch (Exception e) {
			LOG.info("Exception in needlist/get service "+e.getMessage());
			ErrorVO errorVo = new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage("Error in service");
			commonResponseVO.setError(errorVo);
		}
		
		Gson gson = new Gson();
		return gson.toJson(commonResponseVO);
		
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

}
