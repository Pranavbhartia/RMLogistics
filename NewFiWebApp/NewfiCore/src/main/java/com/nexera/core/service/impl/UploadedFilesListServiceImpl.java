package com.nexera.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDPage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.AssignedUserVO;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LQBDocumentVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.LQBXMLHandler;
import com.nexera.core.utility.NexeraUtility;

@Component
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UploadedFilesListServiceImpl.class);

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;

	@Autowired
	private UserProfileDao userProfileDao;

	@Autowired
	private NeedsListService needsListService;

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private UserProfileService userProfileService;

	@Autowired
	private NexeraUtility nexeraUtility;

	@Autowired
	private LqbInvoker lqbInvoker;

	@Autowired
	private LoanService loanService;

	@Override
	@Transactional
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.saveUploadedFile(uploadedFilesList);
	}

	@Override
	@Transactional
	public List<UploadedFilesListVO> fetchAll(Integer userId, Integer loanId) {
		List<UploadedFilesList> filesLists = uploadedFilesListDao.fetchAll(
		        userId, loanId);
		return buildUpdateFileVoList(filesLists);

	}

	public List<UploadedFilesListVO> buildUpdateFileVoList(
	        List<UploadedFilesList> filesLists) {

		List<UploadedFilesListVO> uploadedFilesListVOs = new ArrayList<UploadedFilesListVO>();
		for (UploadedFilesList uploadedFilesList : filesLists) {
			uploadedFilesListVOs.add(buildUpdateFileVo(uploadedFilesList));
		}
		return uploadedFilesListVOs;
	}

	public UploadedFilesListVO buildUpdateFileVo(
	        UploadedFilesList uploadedFilesList) {

		if (uploadedFilesList == null)
			return null;

		UploadedFilesListVO filesListVO = new UploadedFilesListVO();
		filesListVO.setId(uploadedFilesList.getId());
		filesListVO.setIsActivate(uploadedFilesList.getIsActivate());
		filesListVO.setIsAssigned(uploadedFilesList.getIsAssigned());
		filesListVO.setS3path(uploadedFilesList.getS3path());
		filesListVO.setUploadedDate(uploadedFilesList.getUploadedDate());
		filesListVO.setFileName(uploadedFilesList.getFileName());
		filesListVO.setS3ThumbPath(uploadedFilesList.getS3ThumbPath());
		filesListVO.setUuidFileId(uploadedFilesList.getUuidFileId());
		filesListVO.setTotalPages(uploadedFilesList.getTotalPages());

		AssignedUserVO assignedUserVo = new AssignedUserVO();
		assignedUserVo.setUserId(uploadedFilesList.getAssignedBy().getId());
		assignedUserVo.setUserRole(UserRole
		        .convertFromEntityToVO(uploadedFilesList.getAssignedBy()
		                .getUserRole()));

		filesListVO.setAssignedByUser(assignedUserVo);

		LoanVO loanVo = new LoanVO();
		loanVo.setId(uploadedFilesList.getLoan().getId());

		filesListVO.setLoan(loanVo);
		return filesListVO;
	}

	@Override
	@Transactional
	public void updateIsAssignedToTrue(Integer fileId) {
		uploadedFilesListDao.updateIsAssignedToTrue(fileId);
	}

	@Override
	@Transactional
	public void updateIsAssignedToTrue(List<Integer> fileIds) {
		for (Integer fileId : fileIds) {
			uploadedFilesListDao.updateIsAssignedToTrue(fileId);
		}
	}

	@Override
	@Transactional
	public void updateFileInLoanNeedList(Integer needId, Integer fileId) {
		uploadedFilesListDao.updateFileInLoanNeedList(needId, fileId);
	}

	@Override
	@Transactional
	public String findFileNameFromId(Integer fileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.findFileNameFromId(fileId);
	}

	@Override
	@Transactional
	public void deactivateFileUsingFileId(Integer fileId) {
		uploadedFilesListDao.deactivateFileUsingFileId(fileId);
		// TODO: Delete file reference from S3.

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

	@Override
	@Transactional
	public List<String> downloadFileFromS3Service(List<Integer> fileIds) {
		List<String> downloadFiles = new ArrayList<String>();
		for (Integer fileId : fileIds) {
			UploadedFilesList uploadedFilesList = uploadedFilesListDao
			        .fetchUsingFileId(fileId);
			try {

				String fileName = s3FileUploadServiceImpl.downloadFile(
				        uploadedFilesList.getS3path(),
				        nexeraUtility.tomcatDirectoryPath() + File.separator
				                + uploadedFilesList.getFileName());
				downloadFiles.add(fileName);
				LOG.info("s3 download returned  : " + fileName);
			} catch (Exception e) {
				LOG.info("Excepttion in downloading file : " + e.getMessage());

			}
		}
		return downloadFiles;
	}

	@Override
	public Integer mergeAndUploadFiles(List<Integer> fileIds, Integer loanId,
	        Integer userId, Integer assignedBy) throws IOException,
	        COSVisitorException {
		List<String> filePaths = downloadFileFromS3Service(fileIds);
		String newFilepath = null;
		newFilepath = nexeraUtility.joinPDDocuments(filePaths);
		Integer fileSavedId = addUploadedFilelistObejct(new File(newFilepath),
		        loanId, userId, assignedBy, null, null);
		for (Integer fileId : fileIds) {
			deactivateFileUsingFileId(fileId);
		}
		return fileSavedId;
	}

	@Override
	@Transactional
	public Integer addUploadedFilelistObejct(File file, Integer loanId,
	        Integer userId, Integer assignedBy, String lqbDocumentID,
	        String uuidValue) {

		/*
		 * commenting code for password protection
		 * 
		 * UserVO userVo = userProfileService.findUser(userId); PDDocument doc;
		 * try { doc = PDDocument.load(file); if(doc.isEncrypted()){
		 * doc.setAllSecurityToBeRemoved(true);
		 * doc.encrypt(null,userVo.getEmailId() ); } } catch (Exception e1) { //
		 * TODO Auto-generated catch block LOG.info("Error in encrypting file");
		 * e1.printStackTrace(); }
		 */

		// Upload file to S3, get S3 URL.
		/*
		 * String s3Path = s3FileUploadServiceImpl.uploadToS3(file, "document",
		 * "complete");
		 */

		LOG.info("File Path : " + file.getPath());
		String s3PathThumbNail = null;
		String thumbPath = null;
		try {
			// Create thumbnail of the file
			thumbPath = nexeraUtility.convertPDFToThumbnail(file.getPath(),
			        nexeraUtility.tomcatDirectoryPath());

		} catch (Exception e) {

			LOG.error("Exception in s3PathThumbNail : " + e.getMessage());
		}

		LOG.info("The thumbnail path for local  :  " + thumbPath);
		if (thumbPath != null) {
			// Upload thumbnail to S3
			File thumbpath = new File(thumbPath);
			s3PathThumbNail = s3FileUploadServiceImpl.uploadToS3(thumbpath,
			        "document", "image");

			if (thumbpath.exists()) {
				thumbpath.delete();
			}

		}

		LOG.info("The s3PathThumbNail path for   :  " + s3PathThumbNail);

		// Create entry for Uploaded file object and save in DB against the
		// user.
		User user = new User();
		user.setId(userId);
		Loan loan = new Loan();
		loan.setId(loanId);
		User assignByUser = new User();
		assignByUser.setId(assignedBy);

		// Retrieve the number of pages in the PDF file
		List<PDPage> splittedFiles = nexeraUtility.splitPDFTOPages(file);

		UploadedFilesList uploadedFilesList = new UploadedFilesList();
		uploadedFilesList.setIsActivate(true);
		uploadedFilesList.setIsAssigned(false);

		uploadedFilesList.setUploadedBy(user);
		uploadedFilesList.setUploadedDate(new Date());
		uploadedFilesList.setLoan(loan);
		uploadedFilesList.setFileName(file.getName());
		uploadedFilesList.setS3ThumbPath(s3PathThumbNail);
		uploadedFilesList.setAssignedBy(assignByUser);
		uploadedFilesList.setUuidFileId(uuidValue);
		uploadedFilesList.setTotalPages(splittedFiles.size());
		uploadedFilesList.setLqbFileID(lqbDocumentID);
		Integer fileSavedId = saveUploadedFile(uploadedFilesList);
		return fileSavedId;
	}

	@Override
	@Transactional
	public UploadedFilesList fetchUsingFileId(Integer fileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.fetchUsingFileId(fileId);
	}

	@Override
	@Transactional
	public UploadedFilesList fetchUsingFileUUID(String uuidFileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.fetchUsingFileUUID(uuidFileId);
	}

	@Override
	public CheckUploadVO uploadFile(File file, String contentType,
	        byte[] bytes, Integer userId, Integer loanId, Integer assignedBy) {
		String s3Path = null;

		LOG.info("File content type  : " + contentType);
		String lqbDocumentId = null;
		String localFilePath = null;
		Boolean fileUpload = false;
		CheckUploadVO checkVo = new CheckUploadVO();
		try {
			// Upload the file locally. If png, convert to PDF, else save
			// directly to local
			if (contentType.equalsIgnoreCase("image/png")
			        || contentType.equalsIgnoreCase("image/jpeg")
			        || contentType.equalsIgnoreCase("image/tiff")) {
				LOG.info("Received an image.converting to PDF");
				localFilePath = nexeraUtility.convertImageToPDF(file,
				        contentType);
				fileUpload = true;
			} else if (contentType.equalsIgnoreCase("application/pdf")) {
				localFilePath = nexeraUtility.uploadFileToLocal(file);
				fileUpload = true;
			}

			// Upload succesfull

			if (fileUpload) {

				String uuidValue = nexeraUtility.randomStringOfLength();

				// Send file to LQB
				LQBResponseVO lqbResponseVO = createLQBVO(userId, bytes,
				        loanId, uuidValue);
				if (lqbResponseVO.getResult().equalsIgnoreCase("OK")) {
					// TODO: Write logic to call LQB service to get the document
					// ID.
					// lqbDocumentId = "ae52da11-fbde-4057-83d4-28eecb6c9847";
					LQBResponseVO responseVO = getAllDocumentsFromLQBByUUID(loanService
					        .getLoanByID(loanId).getLqbFileId());

					lqbDocumentId = fetchDocumentIDByUUID(responseVO, uuidValue);

					// updateLQBDocumentInUploadNeededFile(lqbDocumentId ,
					// fileId);

				}

				File serverFile = new File(localFilePath);
				// Upload the file to S3. Insert in to File table
				Integer savedRowId = addUploadedFilelistObejct(serverFile,
				        loanId, userId, assignedBy, lqbDocumentId, uuidValue);
				LOG.info("Added File document row : " + savedRowId);
				checkVo.setUploadFileId(savedRowId);

				UploadedFilesList latestRow = fetchUsingFileId(savedRowId);

				checkVo.setUuid(latestRow.getUuidFileId());
				checkVo.setFileName(latestRow.getFileName());

				if (serverFile.exists()) {
					serverFile.delete();
				}
			}

		} catch (Exception e) {
			LOG.info(" Exception uploading s3 :  " + e.getMessage());
			e.printStackTrace();
			checkVo.setIsUploadSuccess(false);
			return checkVo;
		}
		checkVo.setIsUploadSuccess(fileUpload);
		LOG.info("file.getOriginalFilename() : " + file.getName());

		LOG.info("The s3 path is : " + s3Path);
		return checkVo;
	}

	@Override
	public LQBResponseVO uploadDocumentInLandingQB(LQBDocumentVO lqbDocumentVO) {
		LQBResponseVO lqbResponseVO = null;
		// TODO Auto-generated method stub

		if (lqbDocumentVO != null) {
			JSONObject uploadObject = createUploadPdfDocumentJsonObject(
			        WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT,
			        lqbDocumentVO);
			JSONObject receivedResponse = lqbInvoker
			        .invokeLqbService(uploadObject.toString());
			LOG.info(" receivedResponse while uploading LQB Document : "
			        + receivedResponse);

			lqbResponseVO = parseLQBXMLResponse(receivedResponse);

		}

		return lqbResponseVO;
	}

	@Override
	public LQBResponseVO createLQBVO(Integer usrId, byte[] bytes,
	        Integer loanId, String uuidValue) {
		UserVO user = userProfileService.findUser(usrId);
		LQBDocumentVO documentVO = new LQBDocumentVO();
		LQBResponseVO lqbResponseVO = null;
		try {
			// TODO: Hard coded value. Get it from DB.
			documentVO.setDocumentType("APPRAISAL DOCUMENT");

			// TODO: Add logic to uniquely identify the file
			documentVO.setNotes(nexeraUtility.getUUIDBasedNoteForLQBDocument(
			        uuidValue, user));
			// TODO: Change logic to receive hte file path / file contents from
			// invoker. We already have the stream.
			documentVO.setsDataContent(nexeraUtility.getContentFromFile(bytes));
			documentVO.setsLoanNumber(loanService.getLoanByID(loanId)
			        .getLqbFileId());

			lqbResponseVO = uploadDocumentInLandingQB(documentVO);

		} catch (Exception e) {

			// TODO Auto-generated catch block
			LOG.info("Exception in uploadDocumentInLandingQB : Saving exception in error table");
			throw new FatalException("LendinQB Exception");
		}

		LOG.info("Assignment : uploadDocumentInLandingQB " + documentVO);
		return lqbResponseVO;
	}

	@Transactional
	private void updateLQBDocumentInUploadNeededFile(String lqbDocumentId,
	        Integer rowId) {
		uploadedFilesListDao.updateLQBDocumentInUploadNeededFile(lqbDocumentId,
		        rowId);
	}

	public JSONObject createUploadPdfDocumentJsonObject(String opName,
	        LQBDocumentVO documentVO) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        documentVO.getsLoanNumber());
			jsonChild.put(WebServiceMethodParameters.PARAMETER_DOCUMENT_TYPE,
			        documentVO.getDocumentType());
			jsonChild.put(WebServiceMethodParameters.PARAMETER_NOTES,
			        documentVO.getNotes());
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_DATA_CONTENT,
			        documentVO.getsDataContent());

			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {

			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	public JSONObject createFetchPdfDocumentJsonObject(String opName,
	        LQBDocumentVO documentVO) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(
			        WebServiceMethodParameters.PARAMETER_S_XML_DOCUMENT_ID,
			        documentVO.getsLoanNumber());

			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {

			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	@Override
	public CheckUploadVO uploadFileByEmail(InputStream stream,
	        String contentType, Integer userId, Integer loanId,
	        Integer assignedBy) throws Exception {
		File file = nexeraUtility.convertInputStreamToFile(stream);
		CheckUploadVO checkUploadVO = null;
		if (file != null) {
			if (contentType.contains("application/pdf"))
				contentType = "application/pdf";
			else if (contentType.contains("image/jpeg"))
				contentType = "image/jpeg";
			else if (contentType.contains("image/png"))
				contentType = "image/png";
			else if (contentType.contains("image/tiff"))
				contentType = "image/tiff";

			checkUploadVO = uploadFile(file, contentType,
			        nexeraUtility.getContentFromStream(stream), userId, loanId,
			        assignedBy);
			return checkUploadVO;
		}

		return checkUploadVO;
	}

	@Override
	public LQBResponseVO fetchLQBDocument(LQBDocumentVO lqbDocumentVO) {
		LQBResponseVO lqbResponseVO = null;
		if (lqbDocumentVO != null) {
			JSONObject uploadObject = createUploadPdfDocumentJsonObject(
			        WebServiceOperations.OP_NAME_LIST_EDCOS_BY_LOAN_NUMBER,
			        lqbDocumentVO);
			JSONObject receivedResponse = lqbInvoker
			        .invokeLqbService(uploadObject.toString());
			LOG.info(" receivedResponse while uploading LQB Document : "
			        + receivedResponse);
			lqbResponseVO = parseLQBXMLResponse(receivedResponse);
		}

		return lqbResponseVO;
	}

	@Override
	public LQBResponseVO getAllDocumentsFromLQBByUUID(String loanNumber) {

		LQBDocumentVO lqbDocumentVO = new LQBDocumentVO();
		lqbDocumentVO.setsLoanNumber(loanNumber);
		return fetchLQBDocument(lqbDocumentVO);

	}

	@Override
	public LQBResponseVO parseLQBXMLResponse(JSONObject receivedResponse) {
		LQBResponseVO lqbResponseVO = null;
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			LQBXMLHandler handler = new LQBXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(
			        new InputSource(new StringReader(receivedResponse
			                .getString("responseMessage"))), handler);
			lqbResponseVO = handler.getLqbResponseVO();
			LOG.info(" parsed lqbResponseVO : " + lqbResponseVO.getResult());
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return lqbResponseVO;
	}

	@Override
	public String fetchDocumentIDByUUID(LQBResponseVO lqbResponseVO, String uuId) {

		if (lqbResponseVO == null) {
			return null;
		} else {
			List<LQBedocVO> lqBedocVOs = lqbResponseVO
			        .getDocumentResponseListVOs().getvBedocVO();
			for (LQBedocVO lqBedocVO : lqBedocVOs) {
				if (lqBedocVO.getDescription().startsWith(uuId)) {
					return lqBedocVO.getDocid();
				}
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void getFileContentFromLQBUsingUUID(String uuId) {
		UploadedFilesList filesList = uploadedFilesListDao
		        .fetchUsingFileUUID(uuId);
		String lqbDocID = filesList.getLqbFileID();

		LQBDocumentVO documentVO = new LQBDocumentVO();
		documentVO.setsLoanNumber(lqbDocID);
		JSONObject jsonObject = createFetchPdfDocumentJsonObject(
		        WebServiceOperations.OP_NAME_LOAN_DOWNLOAD_EDOCS_PDF_BY_DOC_ID,
		        documentVO);
		JSONObject receivedResponse = lqbInvoker.invokeLqbService(jsonObject
		        .toString());
		LOG.info(" receivedResponse while uploading LQB Document : "
		        + receivedResponse);
	}

	@Override
	@Transactional
	public void updateUploadedDocument(List<LQBedocVO> edocsList, Loan loan,
	        Date timeBeforeCallMade) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(timeBeforeCallMade);
		cal.add(Calendar.MINUTE, -5);
		Date modifiedDate = cal.getTime();
		List<UploadedFilesList> uploadList = loan.getUploadedFileList();
		List<UploadedFilesList> timeModifiedUploadedList = new ArrayList<UploadedFilesList>();
		List<String> uploadFileUUIDList = new ArrayList<String>();

		List<String> uuidEdocList = new ArrayList<String>();
		for (UploadedFilesList uploadFiles : uploadList) {
			uploadFileUUIDList.add(uploadFiles.getUuidFileId());
		}
		for (UploadedFilesList uploadFiles : uploadList) {
			if (uploadFiles.getUploadedDate().compareTo(modifiedDate) < 0)
				timeModifiedUploadedList.add(uploadFiles);
		}

		for (LQBedocVO edoc : edocsList) {
			String uuidDetails = edoc.getDescription();
			uuidEdocList.add(fetchUUID(uuidDetails));
		}

		for (LQBedocVO edoc : edocsList) {

			String uuidDetails = edoc.getDescription();
			String uuid = fetchUUID(uuidDetails);
			if (!uploadFileUUIDList.contains(uuid)) {

				LOG.debug("This uuid does not exist hence adding this record in newfi database ");
				UploadedFilesList fileUpload = new UploadedFilesList();
				fileUpload.setFileName(edoc.getDoc_type() + ".pdf");
				User user = userProfileDao
				        .findByUserId(CommonConstants.SYSTEM_USER_USERID);
				fileUpload.setAssignedBy(user);
				fileUpload.setUploadedBy(loan.getUser());
				fileUpload.setIsActivate(true);
				fileUpload.setIsAssigned(false);
				fileUpload.setLoan(loan);
				fileUpload.setLqbFileID(edoc.getDocid());
				fileUpload.setUploadedDate(new Date());
				fileUpload.setUuidFileId(uuid);
				// TODO hardcoded for now
				fileUpload.setTotalPages(2);

				int fileUploadId = uploadedFilesListDao
				        .saveUploadedFile(fileUpload);
				fileUpload.setId(fileUploadId);
			}

		}

		for (UploadedFilesList uploadFile : timeModifiedUploadedList) {
			if (!uuidEdocList.contains(uploadFile.getUuidFileId())) {
				LOG.debug("This uuid does not exist in the lqb list, hence removing this from newfi database ");
				UploadedFilesList fileToDelete = uploadedFilesListDao
				        .fetchUsingFileUUID(uploadFile.getUuidFileId());
				if (fileToDelete != null) {
					LoanNeedsList loanNeedList = loanService
					        .fetchLoanNeedByFileId(fileToDelete);
					if (loanNeedList != null) {
						loanNeedList.setUploadFileId(null);
						loanService.updateLoanNeedList(loanNeedList);
					}
					uploadedFilesListDao.remove(fileToDelete);
				}
			}

		}
	}

	private String fetchUUID(String uuidString) {
		String keyValuePair[] = uuidString.split(" ");
		Map<String, String> map = new HashMap<String, String>();
		for (String pair : keyValuePair) {
			String[] entry = pair.split(":");
			map.put(entry[0].trim(), entry[1].trim());
		}
		return map.get("UUID");

	}

}
