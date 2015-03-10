package com.nexera.web.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;
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
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.rest.util.RestUtil;


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
	
	@RequestMapping(value="/split/{fileId}/{loadId}/{userId}" , method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO splitPDFDocument( @PathVariable ("fileId") Integer  fileId ,  @PathVariable ("loadId") Integer  loanId , @PathVariable ("userId") Integer  userId){
		LOG.info("File upload PDF split  service called");
		LOG.info("File upload   id "+fileId);
		CommonResponseVO commonResponseVO;
		UploadedFilesList uploadedFilesList = uploadedFilesListService.fetchUsingFileId(fileId);
		
		try {
			List<File> pdfPages =  splitPdfDocumentIntoMultipleDocs(uploadedFilesList.getS3path());
				for (File file : pdfPages) {
					Integer fileSavedId = uploadedFilesListService.addUploadedFilelistObejct(file, loanId , userId);
					LOG.info("New file saved with id "+fileSavedId);
				}
			
			uploadedFilesListService.deactivateFileUsingFileId(fileId);
			commonResponseVO = RestUtil.wrapObjectForSuccess(true);
		} catch (Exception e) {
			LOG.error("Exception in file split with fileId "+fileId);
			commonResponseVO = RestUtil.wrapObjectForSuccess(false);
		}
		
		return commonResponseVO;
	}
	
	@RequestMapping(value="/assignment/{loanId}/{userId}" , method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO setAssignmentToFiles(@RequestBody  String fileAssignMent , @PathVariable(value = "loanId") Integer loanId , @PathVariable(value = "userId") Integer userId ){
		
		ObjectMapper mapper=new ObjectMapper();
		TypeReference<List<FileAssignVO>> typeRef=new TypeReference<List<FileAssignVO>>() {};
		List<FileAssignVO> val = new ArrayList<FileAssignVO>();
		CommonResponseVO commonResponseVO=null;
		try {
			val = mapper.readValue(fileAssignMent, typeRef);
			Map<Integer ,List<Integer>> mapFileMappingToNeed = getmapFromFileAssignObj(val);
			
		    for (Integer key : mapFileMappingToNeed.keySet()){
		    		UploadedFilesList filesList = loanService.fetchUploadedFromLoanNeedId(key);
		    		LOG.info("fetchUploadedFromLoanNeedId returned : "+filesList);
		    		List<Integer> fileIds = mapFileMappingToNeed.get(key);
		    		
		    		if(filesList != null){
		    			fileIds.add(filesList.getId());
		    		}
		    		
		    		if(fileIds.size()>1){
		    				Integer newFileRowId = uploadedFilesListService.mergeAndUploadFiles(fileIds , loanId , userId);
		    				LOG.info("new file pdf path :: "+newFileRowId);
		    				uploadedFilesListService.updateFileInLoanNeedList(key, newFileRowId);
		    				uploadedFilesListService.updateIsAssignedToTrue(newFileRowId);
		    		}else{
		    				uploadedFilesListService.updateFileInLoanNeedList(key, fileIds.get(0));
		    				uploadedFilesListService.updateIsAssignedToTrue( fileIds.get(0));
		    		}
		    		
		    }
				
			commonResponseVO = RestUtil.wrapObjectForSuccess(true);
			
		} catch (Exception e) {
			LOG.error("exception in converting  : "+e.getMessage() ,e);
			e.printStackTrace();
			commonResponseVO = RestUtil.wrapObjectForSuccess(false);
		} 
	   return commonResponseVO;
	}
	
	
	
	
	public Map<Integer , List<Integer>> getmapFromFileAssignObj(List<FileAssignVO> fileAssignVO){
		Map<Integer , List<Integer>> mapFileAssign = new HashMap<Integer , List<Integer>>();
		for (FileAssignVO fileAssign : fileAssignVO) {
			List<Integer> tempFileList = mapFileAssign.get(fileAssign.getNeedListId());
			if(tempFileList == null){
				tempFileList = new ArrayList<Integer>();
				tempFileList.add(fileAssign.getFileId());
				mapFileAssign.put(fileAssign.getNeedListId() ,tempFileList );
			}else{
				tempFileList.add(fileAssign.getFileId());
			}
		}
		return mapFileAssign;
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
		
		Map<String, List<LoanNeedsListVO>> listLoanNeedsListMap;
		UploadFileScreenVO uploadFileScreenVO = new  UploadFileScreenVO();
		try {
			loanNeedsVO = needsListService.getLoanNeedsList(loanId);
			listLoanNeedsListMap = needsListService.getLoanNeedsMap(loanId);
			
			uploadedFilesList = uploadedFilesListService.fetchAll(userId, loanId);
			for (UploadedFilesListVO uploadedFilesListVO : uploadedFilesList) {
				Integer needType = needsListService.getLoanNeedListIdByFileId(uploadedFilesListVO.getId());
				LOG.info("The need type is : "+needType);
				uploadedFilesListVO.setNeedType(needType);
			}
			
			uploadFileScreenVO.setListLoanNeedsListVO(loanNeedsVO);
			uploadFileScreenVO.setListLoanNeedsListMap(listLoanNeedsListMap);
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
	
	private List<File> splitPdfDocumentIntoMultipleDocs(String s3path) throws Exception{
	
		File file = new File(s3FileUploadServiceImpl.downloadFile(s3path, NexeraUtility.tomcatDirectoryPath()+File.separator+(new File(s3path)).getName()));
		List<File> splittedFiles =  NexeraUtility.splitPDFPages(file);
		return splittedFiles;
		
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
