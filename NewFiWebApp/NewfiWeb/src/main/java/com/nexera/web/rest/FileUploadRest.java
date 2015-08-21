package com.nexera.web.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nexera.common.commons.ErrorConstants;
import com.nexera.common.commons.Utils;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.enums.ServiceCodes;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.CommonResponseVO;
import com.nexera.common.vo.ErrorVO;
import com.nexera.common.vo.FileAssignVO;
import com.nexera.common.vo.FileAssignmentMappingVO;
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
import com.nexera.workflow.exception.FatalException;

@Controller
@RequestMapping("/fileupload")
public class FileUploadRest {

	private static final Logger LOG = LoggerFactory
	        .getLogger(FileUploadRest.class);

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private NeedsListService needsListService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private UploadedFilesListService uploadedFilesListService;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	private Utils utils;

	@RequestMapping(value = "/upload", method = RequestMethod.POST, headers = "Accept=*")
	public @ResponseBody String uploadFileToS3Service(
	        @RequestParam(value = "file", required = true) MultipartFile multipartFile,
	        HttpServletRequest request, HttpServletResponse response) {
		LOG.info("File upload Rest service called");
		return "true";
	}

	@RequestMapping(value = "/deactivate/file/{fileId}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO deactivateFileUsingFileId(
	        @PathVariable("fileId") Integer fileId) {
		CommonResponseVO commonResponseVO = null;
		try {
			// Update in DB
			uploadedFilesListService.deactivateFileUsingFileId(fileId);
			commonResponseVO = RestUtil.wrapObjectForSuccess(true);
		} catch (Exception e) {
			commonResponseVO = RestUtil.wrapObjectForSuccess(false);
		}
		return commonResponseVO;
	}

	@RequestMapping(value = "{loanId}/score/get", method = RequestMethod.GET)
	public @ResponseBody String getLoanNeedRequirementScore(
	        @PathVariable("loanId") Integer loanId) {
		return new Gson().toJson(needsListService.getNeededItemsScore(loanId));
	}

	@RequestMapping(value = "/loadneedlist/get", method = RequestMethod.GET)
	public @ResponseBody String getLoanNeedList() {
		return new Gson().toJson(needsListService.getLoanNeedsList(1));
	}

	@RequestMapping(value = "/split/{fileId}/{loadId}/{userId}/{assignedBy}", method = RequestMethod.GET)
	public @ResponseBody CommonResponseVO splitPDFDocument(
	        @PathVariable("fileId") Integer fileId,
	        @PathVariable("loadId") Integer loanId,
	        @PathVariable("userId") Integer userId,
	        @PathVariable("assignedBy") Integer assignedBy) {
		LOG.info("File upload PDF split  service called");
		LOG.info("File upload   id " + fileId);
		CommonResponseVO commonResponseVO;
		// Get information of the files that is saved in DB for this file ID.
		UploadedFilesList uploadedFilesList = uploadedFilesListService
		        .fetchUsingFileId(fileId);
		Integer id = uploadedFilesList.getId();
		Boolean isAssignedToNeed = false;
		try {
			// Pass the S3 path, to get the document from S3, and convert itto
			// PDObject
			List<File> pdfPages = splitPdfDocumentIntoMultipleDocs(uploadedFilesList
			        .getLqbFileID());
			for (File file : pdfPages) {
				Path path = Paths.get(file.getAbsolutePath());
				byte[] data = Files.readAllBytes(path);
				// Create a new row in DB and upload file to S3.
				String contentType = "application/pdf";

				CheckUploadVO checkUploadVO = uploadedFilesListService
				        .uploadFile(file, contentType, userId, loanId,
				                assignedBy, isAssignedToNeed, null,null);

				// Integer fileSavedId =
				// uploadedFilesListService.addUploadedFilelistObejct( file,
				// loanId, userId, assignedBy , null , null );
				LOG.info("New file saved with id "
				        + checkUploadVO.getIsUploadSuccess());
				if (file.exists()) {
					file.delete();
				}
			}

			// Deactive the old file, i.e the file which was split.
			uploadedFilesListService.deactivateFileUsingFileId(fileId);
			commonResponseVO = RestUtil.wrapObjectForSuccess(true);

		} catch (Exception e) {
			LOG.error("Exception in file split with fileId " + fileId);
			commonResponseVO = RestUtil.wrapObjectForSuccess(false);
		}

		return commonResponseVO;
	}

	@RequestMapping(value = "/assignment/{loanId}/{userId}/{assignedBy}", method = RequestMethod.POST)
	public @ResponseBody CommonResponseVO setAssignmentToFiles(
	        @RequestBody String fileAssignMent,
	        @PathVariable(value = "loanId") Integer loanId,
	        @PathVariable(value = "userId") Integer userId,
	        @PathVariable(value = "assignedBy") Integer assignedBy) {
		CommonResponseVO commonResponseVO = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			TypeReference<List<FileAssignVO>> typeRef = new TypeReference<List<FileAssignVO>>() {
			};
			List<FileAssignVO> val = new ArrayList<FileAssignVO>();
			val = mapper.readValue(fileAssignMent, typeRef);
			Map<Integer, FileAssignmentMappingVO> mapFileMappingToNeed = getmapFromFileAssignObj(val);
			Boolean isSuccess = uploadedFilesListService.assignFileToNeeds(
			        mapFileMappingToNeed, loanId, userId, assignedBy);

			if (isSuccess) {
				commonResponseVO = RestUtil.wrapObjectForSuccess(true);
				needsListService.createOrDismissNeedsAlert(loanId);
			} else {
				throw new FatalException("Problem in assigning needs to files");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("setAssignmentToFiles failed", e);
			commonResponseVO = RestUtil.wrapObjectForFailure(null,
			        ErrorConstants.UPLOADFILEFAILEDCODE,
			        ErrorConstants.UPLOADFILEFAILEDMESSAGE);
		}

		return commonResponseVO;
	}

	private Map<Integer, FileAssignmentMappingVO> getmapFromFileAssignObj(
	        List<FileAssignVO> fileAssignVO) {
		Map<Integer, FileAssignmentMappingVO> mapFileAssign = new HashMap<Integer, FileAssignmentMappingVO>();
		for (FileAssignVO fileAssign : fileAssignVO) {
			List<Integer> tempFileList = mapFileAssign.get(fileAssign
			        .getNeedListId()) == null ? null : mapFileAssign.get(
			        fileAssign.getNeedListId()).getFileIds();
			FileAssignmentMappingVO mapping = new FileAssignmentMappingVO();
			if (tempFileList == null) {
				tempFileList = new ArrayList<Integer>();
				tempFileList.add(fileAssign.getFileId());
				mapping.setFileIds(tempFileList);
				mapping.setIsMiscellaneous(fileAssign.getIsMiscellanous());
				mapFileAssign.put(fileAssign.getNeedListId(), mapping);
			} else {
				tempFileList.add(fileAssign.getFileId());
			}
		}
		return mapFileAssign;
	}

	@RequestMapping(value = "/uploadedFile/get/{userId}/{loadId}", method = RequestMethod.GET)
	public @ResponseBody String getUserUploadedFileList(
	        @PathVariable("userId") Integer userId,
	        @PathVariable("loadId") Integer loadId) {
		LOG.info("getUserUploadedFileList called");
		List<UploadedFilesListVO> listUploadedFileVO = null;
		try {
			listUploadedFileVO = uploadedFilesListService.fetchAll(userId,
			        loadId);
		} catch (Exception e) {
			LOG.info("getUserUploadedFileList exception  called"
			        + e.getMessage());
			listUploadedFileVO = Collections.EMPTY_LIST;
		}
		return new Gson().toJson(listUploadedFileVO);
	}

	@RequestMapping(value = "/needlist/get/{userId}/{loanId}", method = RequestMethod.GET)
	public @ResponseBody String getNeedList(
	        @PathVariable("userId") Integer userId,
	        @PathVariable("loanId") Integer loanId) {
		LOG.info("File upload Rest service called");

		UserVO userVo = new UserVO();
		userVo.setId(userId);

		CommonResponseVO commonResponseVO = new CommonResponseVO();
		List<LoanNeedsListVO> loanNeedsVO;
		List<UploadedFilesListVO> uploadedFilesList;
		Map<String, List<LoanNeedsListVO>> listLoanNeedsListMap;
		UploadFileScreenVO uploadFileScreenVO = new UploadFileScreenVO();

		try {

			uploadFileScreenVO.setLoanEmailID(loanService.getLoanByID(loanId)
			        .getLoanEmailId());

			loanNeedsVO = needsListService.getLoanNeedsList(loanId);
			listLoanNeedsListMap = needsListService.getLoanNeedsMap(loanId);

			uploadedFilesList = uploadedFilesListService.fetchAll(userId,
			        loanId);
			for (UploadedFilesListVO uploadedFilesListVO : uploadedFilesList) {
				Integer needType = needsListService
				        .getLoanNeedListIdByFileId(uploadedFilesListVO.getId());
				LOG.info("The need type is : " + needType);
				uploadedFilesListVO.setNeedType(needType);
			}

			uploadFileScreenVO.setListLoanNeedsListVO(loanNeedsVO);
			uploadFileScreenVO.setListLoanNeedsListMap(listLoanNeedsListMap);
			uploadFileScreenVO.setListUploadedFilesListVO(uploadedFilesList);
			uploadFileScreenVO.setNeededItemScoreVO(needsListService
			        .getNeededItemsScore(loanId));
			commonResponseVO.setResultObject(uploadFileScreenVO);
		} catch (Exception e) {
			LOG.info("Exception in needlist/get service " + e.getMessage());
			ErrorVO errorVo = new ErrorVO();
			errorVo.setCode("500");
			errorVo.setMessage("Error in service");
			commonResponseVO.setError(errorVo);
		}

		Gson gson = new Gson();
		return gson.toJson(commonResponseVO);

	}

	private List<File> splitPdfDocumentIntoMultipleDocs(String lqbDocId)
	        throws Exception {

		InputStream inputStream = uploadedFilesListService
		        .createLQBObjectToReadFile(lqbDocId);
		File file = nexeraUtility.copyInputStreamToFile(inputStream);
		List<File> splittedFiles = nexeraUtility.splitPDFPagesUsingIText(file);

		if (file.exists()) {
			file.delete();
		}
		return splittedFiles;

	}

	@RequestMapping(value = "documentUpload", method = RequestMethod.POST)
	public @ResponseBody String filesUploadToS3System(
	        @RequestParam(value = "file") MultipartFile[] file,
	        @RequestParam(value = "userID") Integer userID,
	        @RequestParam(value = "loanId") Integer loanId,
	        @RequestParam(value = "assignedBy") Integer assignedBy) {
		return filesUploadToS3SystemAndAssign(file, userID, loanId, assignedBy,
		        null);

	}

	@RequestMapping(value = "documentUploadWithNeed", method = RequestMethod.POST)
	public @ResponseBody String filesUploadToS3SystemAndAssign(
	        @RequestParam(value = "file") MultipartFile[] file,
	        @RequestParam(value = "userID") Integer userID,
	        @RequestParam(value = "loanId") Integer loanId,
	        @RequestParam(value = "assignedBy") Integer assignedBy,
	        @RequestParam(value = "needId") Integer needId) {

		LOG.info("Checking for User Session : ");

		User user = utils.getLoggedInUser();
		if (user == null) {

			return new Gson().toJson(RestUtil.wrapObjectForFailure(null, "403",
			        "User Not Logged in."));
		}

		LOG.info("in document upload  with user id " + userID + " and loanId :"
		        + loanId + " and assignedBy : " + assignedBy
		        + " and need id : " + needId);
		List<String> unsupportedFile = new ArrayList<String>();
		Boolean isAssignedToNeed = true;
		if (needId == null || needId.equals(0)) {
			isAssignedToNeed = false;
		}

		for (MultipartFile multipartFile : file) {
			CheckUploadVO checkFileUploaded = null;

			try {

				// Upload the file locally and returns the response of file
				// upload

				checkFileUploaded = uploadedFilesListService.uploadFile(
				        nexeraUtility.multipartToFile(multipartFile),
				        multipartFile.getContentType(), userID, loanId,
				        assignedBy, isAssignedToNeed, null,needId);
			} catch (IllegalStateException | IOException e) {
				// If file conversion or saving fails, set upload status to
				// false.
				
				//check if type of exception is muleexception 
				/*if(muleexcetpion is true){
					return new Gson().toJson(RestUtil.wrapObjectForFailure(null, "418",
					        "Mule not working"));
				}*/
				checkFileUploaded.setIsUploadSuccess(false);
			}

			if (checkFileUploaded.getIsUploadSuccess()) {

				if (needId == null) {
					LOG.info("Needs is null");
				} else {
					LOG.info("Assigning needs");

					uploadedFilesListService.updateAssignments(needId,
					        checkFileUploaded.getUploadFileId(), loanId);
					needsListService.createOrDismissNeedsAlert(loanId);
				}

			} else if(!checkFileUploaded.getIsUploadSuccess() && checkFileUploaded.getCode() != null && checkFileUploaded.getCode() == ServiceCodes.MULE_SERVICE.getServiceID()){
				unsupportedFile.add(multipartFile.getOriginalFilename());
				unsupportedFile.add(checkFileUploaded.getCode().toString());
				
			}else {
				unsupportedFile.add(multipartFile.getOriginalFilename());
			}

		}
		return new Gson().toJson(unsupportedFile);
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
