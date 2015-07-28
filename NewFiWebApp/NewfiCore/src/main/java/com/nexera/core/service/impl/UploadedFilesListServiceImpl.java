package com.nexera.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.LoanStatus;
import com.nexera.common.commons.Utils;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.commons.WorkflowConstants;
import com.nexera.common.commons.WorkflowDisplayConstants;
import com.nexera.common.dao.LoanNeedListDao;
import com.nexera.common.dao.NeedsDao;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.Template;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.enums.MasterNeedsEnum;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.AssignedUserVO;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.FileAssignmentMappingVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.common.vo.lqb.LQBDocumentVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.LQBXMLHandler;
import com.nexera.core.utility.NexeraUtility;
import com.nexera.workflow.bean.WorkflowExec;
import com.nexera.workflow.bean.WorkflowItemExec;
import com.nexera.workflow.bean.WorkflowItemMaster;
import com.nexera.workflow.engine.EngineTrigger;
import com.nexera.workflow.service.WorkflowService;

@Component
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UploadedFilesListServiceImpl.class);

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;

	@Autowired
	private Utils utils;

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
	private SendEmailService sendEmailService;

	@Autowired
	private LqbInvoker lqbInvoker;

	@Autowired
	private LoanService loanService;

	@Value("${un_assigned_folder}")
	private String unAssignedFolder;

	@Value("${assigned_folder}")
	private String assignedFolder;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private LoanNeedListDao loanNeedListDAO;
	
	@Autowired
	private NeedsDao needsDao;

	@Autowired
	private ApplicationContext applicationContext;

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

	@Override
	public List<UploadedFilesListVO> buildUpdateFileVoList(
	        List<UploadedFilesList> filesLists) {

		List<UploadedFilesListVO> uploadedFilesListVOs = new ArrayList<UploadedFilesListVO>();
		for (UploadedFilesList uploadedFilesList : filesLists) {
			uploadedFilesListVOs.add(buildUpdateFileVo(uploadedFilesList));
		}
		return uploadedFilesListVOs;
	}

	@Override
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
		filesListVO.setLqbFileID(uploadedFilesList.getLqbFileID());
		filesListVO.setIsMiscellaneous(uploadedFilesList.getIsMiscellaneous());

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
	public List<File> downloadFileFromService(List<Integer> fileIds) {
		List<File> downloadFiles = new ArrayList<File>();
		for (Integer fileId : fileIds) {
			UploadedFilesList uploadedFilesList = uploadedFilesListDao
			        .fetchUsingFileId(fileId);
			try {

				InputStream inputStream = createLQBObjectToReadFile(uploadedFilesList
				        .getLqbFileID());
				File file = nexeraUtility.copyInputStreamToFile(inputStream);
				downloadFiles.add(file);
			} catch (Exception e) {
				LOG.info("Excepttion in downloading file : " + e.getMessage());

			}
		}
		return downloadFiles;
	}

	@Override
	@Transactional
	public Integer mergeAndUploadFiles(List<Integer> fileIds, Integer loanId,
	        Integer userId, Integer assignedBy,Integer needId) throws IOException,
	        COSVisitorException, FatalException {

		List<File> filePaths = downloadFileFromService(fileIds);

		File newFile = nexeraUtility.joinPDDocuments(filePaths);

		Boolean isAssignedToNeed = true;
		CheckUploadVO checkUploadVO = uploadFile(newFile, "application/pdf",
		        userId, loanId, assignedBy, isAssignedToNeed, null, needId);
		if (!checkUploadVO.getIsUploadSuccess()) {
			throw new FatalException("Upload to LQB failed");
		}
		if (newFile.exists()) {
			newFile.delete();
		}

		return checkUploadVO.getUploadFileId();
	}

	@Override
	@Transactional
	public Integer addUploadedFilelistObejct(File file, Integer loanId,
	        Integer userId, Integer assignedBy, String lqbDocumentID,
	        String uuidValue, String fileName) {

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
			        file.getAbsolutePath());

		} catch (Exception e) {

			LOG.error("Exception in s3PathThumbNail : " + e.getMessage(), e);
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
		if (fileName == null) {
			uploadedFilesList.setFileName(file.getName());
		} else {
			uploadedFilesList.setFileName(fileName);
		}
		uploadedFilesList.setS3ThumbPath(s3PathThumbNail);
		uploadedFilesList.setAssignedBy(assignByUser);
		uploadedFilesList.setUuidFileId(uuidValue);
		uploadedFilesList.setTotalPages(splittedFiles.size());
		uploadedFilesList.setLqbFileID(lqbDocumentID);
		uploadedFilesList.setIsMiscellaneous(true);
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
		return uploadedFilesListDao.fetchUsingFileUUID(uuidFileId, null);
	}

	@Override
	@Transactional
	public CheckUploadVO uploadFile(File file, String contentType,
	        Integer userId, Integer loanId, Integer assignedBy,
	        Boolean isNeedAssigned, String fileName, Integer needId) {
		String s3Path = null;

		LOG.info("File content type  : " + contentType);
		String lqbDocumentId = null;
		String localFilePath = null;
		Boolean fileUpload = false;
		File serverFile = null;
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
				// localFilePath = nexeraUtility.uploadFileToLocal(file);
				localFilePath = file.getAbsolutePath();
				fileUpload = true;
			}

			// Upload succesfull

			if (fileUpload) {
				serverFile = new File(localFilePath);
				// Path path = Paths.get(serverFile.getAbsolutePath());
				byte[] data = readContentIntoByteArray(serverFile);
				String uuidValue = nexeraUtility.randomStringOfLength();

				// Send file to LQB
				LQBResponseVO lqbResponseVO = createLQBVO(userId, data, loanId,
				        uuidValue, isNeedAssigned, needId);
				if (lqbResponseVO.getResult().equalsIgnoreCase("OK")) {
					// TODO: Write logic to call LQB service to get the document
					// ID.
					// lqbDocumentId = "ae52da11-fbde-4057-83d4-28eecb6c9847";
					LQBResponseVO responseVO = getAllDocumentsFromLQBByUUID(loanService
					        .getLoanByID(loanId).getLqbFileId());

					lqbDocumentId = fetchDocumentIDByUUID(responseVO, uuidValue);

					// Upload the file to S3. Insert in to File table
					Integer savedRowId = addUploadedFilelistObejct(serverFile,
					        loanId, userId, assignedBy, lqbDocumentId,
					        uuidValue, fileName);
					LOG.info("Added File document row : " + savedRowId);
					checkVo.setUploadFileId(savedRowId);

					UploadedFilesList latestRow = fetchUsingFileId(savedRowId);

					checkVo.setUuid(latestRow.getUuidFileId());
					checkVo.setFileName(latestRow.getFileName());
					checkVo.setLqbFileId(latestRow.getLqbFileID());

				} else {

					throw new FatalException(
					        "Error saving document. LQB result was not OK: "
					                + lqbResponseVO.getResult());
				}

			} else {
				nexeraUtility.deleteFileFolderFromLocalDirectory(file);
			}

		} catch (Exception e) {

			nexeraUtility.deleteFileFolderFromLocalDirectory(file);
			LOG.error(" Exception uploading s3 :  ", e);
			checkVo.setIsUploadSuccess(false);
			return checkVo;
		} finally {
			if (serverFile != null && serverFile.exists()) {
				serverFile.delete();
			}
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
			        + receivedResponse.getString("responseMessage"));
			
			//Condition to check if there is any error in doctype
			if(receivedResponse.getString("responseMessage").contains("Cannot find document type")){
				LOG.info(" Error recived from LQB that document type not found hence changing the doc type to  INITIAL LOAN PACKAGE: "
				        + receivedResponse);
				lqbDocumentVO.setDocumentType(assignedFolder);
				uploadObject = createUploadPdfDocumentJsonObject(
				        WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT,
				        lqbDocumentVO);
				receivedResponse = lqbInvoker
				        .invokeLqbService(uploadObject.toString());
				lqbResponseVO = parseLQBXMLResponse(receivedResponse);
				return lqbResponseVO;
				
			}
			
				lqbResponseVO = parseLQBXMLResponse(receivedResponse);
			
			
			

		}

		return lqbResponseVO;
	}

	@Override
	public LQBResponseVO createLQBVO(Integer usrId, byte[] bytes,
	        Integer loanId, String uuidValue, Boolean isNeedAssigned,
	        Integer needId) {
		UserVO user = userProfileService.findUser(usrId);
		LQBDocumentVO documentVO = new LQBDocumentVO();
		LQBResponseVO lqbResponseVO = null;
		String folderName = null;
		try {

			// Uploading documents to the specific folder in lqb
			/*
			 * if (isNeedAssigned) { folderName = assignedFolder; } else {
			 * folderName = unAssignedFolder; }
			 */
			if (needId != null) {
				NeedsListMaster needsListMaster = getNeedFromMaster(needId);
				//Check if it is a valid need
				if(needsListMaster!=null){
					if (isNeedAssigned) {
						if (needsListMaster.getUploadedTo() == null) {
							folderName = assignedFolder;
						} else {
							folderName = needsListMaster.getUploadedTo();
						}

					} else {

						folderName = unAssignedFolder;
					}
				}else{
					//For invalid needid
					if (isNeedAssigned) {
						folderName = assignedFolder;
					} else {
						folderName = unAssignedFolder;
					}
				}
			} else {

				if (isNeedAssigned) {
					folderName = assignedFolder;
				} else {
					folderName = unAssignedFolder;
				}
			}

			// TODO: Hard coded value. Get it from DB.
			documentVO.setDocumentType(folderName);

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
	public void updateLQBDocumentInUploadNeededFile(String lqbDocumentId,
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
			        nexeraUtility.asciiTrim(documentVO.getsLoanNumber()));
			jsonChild.put(WebServiceMethodParameters.PARAMETER_DOCUMENT_TYPE,
			        nexeraUtility.asciiTrim(documentVO.getDocumentType()));
			jsonChild.put(WebServiceMethodParameters.PARAMETER_NOTES,
			        nexeraUtility.asciiTrim(documentVO.getNotes()));
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_DATA_CONTENT,
			        nexeraUtility.asciiTrim(documentVO.getsDataContent()));

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
	@Transactional
	public CheckUploadVO uploadFileByEmail(InputStream stream,
	        String contentType, Integer userId, Integer loanId,
	        Integer assignedBy, String fileName) throws Exception {
		File file = nexeraUtility.convertInputStreamToFile(stream);
		CheckUploadVO checkUploadVO = null;
		Boolean isAssignedToNeed = false;
		if (file != null) {
			if (contentType.contains("application/pdf"))
				contentType = "application/pdf";
			else if (contentType.contains("image/jpeg"))
				contentType = "image/jpeg";
			else if (contentType.contains("image/png"))
				contentType = "image/png";
			else if (contentType.contains("image/tiff"))
				contentType = "image/tiff";

			checkUploadVO = uploadFile(file, contentType, userId, loanId,
			        assignedBy, isAssignedToNeed, fileName, null);

			if (file.exists()) {
				file.delete();
			}
			return checkUploadVO;
		}

		return checkUploadVO;
	}

	private static byte[] readContentIntoByteArray(File file) {
		FileInputStream fileInputStream = null;
		byte[] bFile = new byte[(int) file.length()];
		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

		} catch (Exception e) {
			LOG.error("Exception caught " + e.getMessage());
		}
		return bFile;
	}

	@Override
	public LQBResponseVO fetchLQBDocument(LQBDocumentVO lqbDocumentVO)
	        throws IOException {
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
	public LQBResponseVO getAllDocumentsFromLQBByUUID(String loanNumber)
	        throws IOException {

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
			LOG.error("Exception caught " + se.getMessage());
		} catch (ParserConfigurationException pce) {
			LOG.error("Exception caught " + pce.getMessage());
		} catch (IOException ie) {
			LOG.error("Exception caught " + ie.getMessage());
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
				if (uuId.equals(nexeraUtility.fetchUUID(lqBedocVO
				        .getDescription()))) {
					return lqBedocVO.getDocid();
				}
			}
		}
		return null;
	}

	@Override
	@Transactional
	public void getFileContentFromLQBUsingUUID(HttpServletResponse response,
	        String uuId) {
		UploadedFilesList filesList = uploadedFilesListDao.fetchUsingFileUUID(
		        uuId, null);

		InputStream inputStream = null;
		try {
			inputStream = createLQBObjectToReadFile(filesList.getLqbFileID());

			// File file = nexeraUtility.convertInputStreamToFile(inputStream);

			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[2048];
			int length = -1;

			while ((length = inputStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}
			outStream.flush();

			inputStream.close();
			outStream.close();
			response.setContentLength(buffer.length);
			response.setHeader("Content-Disposition",
			        "attachment; filename=\"download.pdf\"");

		} catch (Exception e) {
			LOG.info("Exception File servlet");
			LOG.error("Exception caught " + e.getMessage());
		}

	}

	@Override
	public InputStream createLQBObjectToReadFile(String lqbDocID)
	        throws IOException {
		LQBDocumentVO documentVO = new LQBDocumentVO();
		documentVO.setsLoanNumber(lqbDocID);

		JSONObject jsonObject = createFetchPdfDocumentJsonObject(
		        WebServiceOperations.OP_NAME_LOAN_DOWNLOAD_EDOCS_PDF_BY_DOC_ID,
		        documentVO);

		InputStream inputStream = lqbInvoker
		        .invokeRestSpringParseStream(jsonObject.toString());
		return inputStream;
	}

	@Override
	@Transactional
	public void updateUploadedDocument(List<LQBedocVO> edocsList, Loan loan,
	        Date timeBeforeCallMade) {
		int insertFile = 0;
		int removedFile = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeBeforeCallMade);
		cal.add(Calendar.MINUTE, -5);
		Date modifiedDate = cal.getTime();

		List<UploadedFilesList> timeModifiedUploadedList = new ArrayList<UploadedFilesList>();

		List<String> uploadFileUUIDList = nexeraUtility.getFileUUIDList(loan
		        .getUploadedFileList());
		List<String> uuidEdocList = nexeraUtility.getEdocsUUIDList(edocsList);
		List<String> docIdList = nexeraUtility.getEdocsDocList(edocsList);

		for (UploadedFilesList uploadFiles : loan.getUploadedFileList()) {
			if (loan.getUploadedFileList() != null) {
				if (uploadFiles.getUploadedDate() != null) {
					if (uploadFiles.getUploadedDate().compareTo(modifiedDate) < 0)
						timeModifiedUploadedList.add(uploadFiles);
				} else {
					timeModifiedUploadedList.add(uploadFiles);
				}
			}
		}
		List<String> restrictedFolders = utils.getRestrictedFolders();
		List<String> restrictedDocTypes = utils.getRestrictedDocTypes();
		for (LQBedocVO edoc : edocsList) {
			// edoc.getFolder_name()
			LOG.debug("Trying to upload file for Loan " + loan.getId()
			        + " Doc Type : " + edoc.getDoc_type() + " Folder : "
			        + edoc.getFolder_name());
			if (edoc.getDoc_type() != null
			        && restrictedDocTypes.contains(edoc.getDoc_type()
			                .toUpperCase())) {
				LOG.debug("Ignoring files for this doc type "
				        + edoc.getDoc_type());
				continue;
			} else if (edoc.getFolder_name() != null
			        && restrictedFolders.contains(edoc.getFolder_name()
			                .toUpperCase())) {
				LOG.debug("Ignoring files for this Folder Name "
				        + edoc.getFolder_name());
				continue;
			}
			String uuidDetails = edoc.getDescription();
			String uuid = nexeraUtility.fetchUUID(uuidDetails);
			if (uuid != null) {
				if (!uploadFileUUIDList.contains(uuid)) {
					insertFile = insertFile + 1;
					insertFileIntoNewFi(edoc, loan, uuid);
				}
			} else {
				LOG.debug("This file might have been manually entered in LQB by loan manager ");
				LOG.debug("Checking on the basis of LQB file id whether the file exist or not ");
				String lqbFileId = edoc.getDocid();
				if (getUploadedFileByLQBFieldId(lqbFileId) == null) {
					LOG.debug("Inserting this manually entered doc by Loan Manager in Newfi");
					insertFile = insertFile + 1;
					insertFileIntoNewFi(edoc, loan, null);
				}
			}

		}

		List<UploadedFilesList> filesToDelete = filesToDeleteList(
		        timeModifiedUploadedList, uuidEdocList);

		for (UploadedFilesList file : filesToDelete) {
			if (!docIdList.contains(file.getLqbFileID())) {
				LOG.debug("Can delete the file, lqbfileid also doesnt exist ");
				UploadedFilesList fileToDelete = uploadedFilesListDao
				        .fetchUsingFileUUID(file.getUuidFileId(), loan);
				if (fileToDelete != null) {
					LoanNeedsList loanNeedList = loanService
					        .fetchLoanNeedByFileId(fileToDelete);
					if (loanNeedList != null) {
						loanNeedList.setUploadFileId(null);
						loanService.updateLoanNeedList(loanNeedList);
					}
					removedFile = removedFile + 1;
					uploadedFilesListDao.remove(fileToDelete);
				}
			}
		}
		LOG.debug("Send Email For File Sync");
		sendEmailForFileSync(insertFile, removedFile, loan);
	}

	private void sendEmailForFileSync(int insertFileCount, int removeFileCount,
	        Loan loan) {
		EmailVO emailVO = constructEmail(loan);
		Template template = templateService
		        .getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_NEEDS_LIST_UPDATED);
		if (insertFileCount > 0) {
			LOG.debug("New Files have been inserted ");
			emailVO.setTemplateId(template.getValue());
			// sendEmail(emailVO, loan);
		}

		if (removeFileCount > 0) {
			LOG.debug("Files have been removed ");
			emailVO.setTemplateId(template.getValue());
			// sendEmail(emailVO, loan);
		}
	}

	private EmailVO constructEmail(Loan loan) {
		EmailVO emailEntity = new EmailVO();
		emailEntity.setSenderEmailId(loan.getUser().getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject("Needs list has been updated");
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-",
		        new String[] { WorkflowDisplayConstants.EMAIL_RECPIENT_NAME });
		emailEntity.setTokenMap(substitutions);
		return emailEntity;
	}

	/*
	 * private void sendEmail(EmailVO emailVO, Loan loan) {
	 * LOG.debug("Sending Email "); try {
	 * sendEmailService.sendEmailForTeam(emailVO, loan.getId()); } catch
	 * (InvalidInputException e) { LOG.error("Exception caught " +
	 * e.getMessage()); } catch (UndeliveredEmailException e) {
	 * LOG.error("Exception caught " + e.getMessage()); }
	 * 
	 * }
	 */

	private List<UploadedFilesList> filesToDeleteList(
	        List<UploadedFilesList> list, List<String> uuidEdocList) {
		List<UploadedFilesList> filesToDelete = new ArrayList<UploadedFilesList>();
		for (UploadedFilesList uploadFile : list) {
			if (!uuidEdocList.contains(uploadFile.getUuidFileId())) {
				{
					LOG.debug("Checking whether the file exist with LQB File Id ");
					filesToDelete.add(uploadFile);

				}
			}
		}
		return filesToDelete;
	}

	@Transactional
	public UploadedFilesList getUploadedFileByLQBFieldId(String lqbfieldId) {
		return uploadedFilesListDao.fetchUsingFileLQBFieldId(lqbfieldId);
	}

	private Date convertLQBStringToDate(String mixedDate) {
		Date parsedDate = null;
		if (mixedDate != null) {
			String[] dateTime = mixedDate.split("T");
			String date = dateTime[0];
			String time = dateTime[1];
			SimpleDateFormat finalFormat = new SimpleDateFormat(
			        "HH:mm:ss.SSSS yyyy-MM-dd");
			mixedDate = time + " " + date;
			parsedDate = parseDateInFormat(finalFormat, mixedDate);
		}
		return parsedDate;
	}

	private Date parseDateInFormat(SimpleDateFormat simpleDateFormat,
	        String time) {
		Date date = null;
		try {
			date = simpleDateFormat.parse(time);
		} catch (ParseException e) {
			LOG.error("Unable to parse date " + e.getMessage());
		}
		return date;
	}

	@Override
	@Transactional
	public void insertFileIntoNewFi(LQBedocVO edoc, Loan loan, String uuid) {
		LOG.debug("This uuid does not exist hence adding this record in newfi database ");
		UploadedFilesList fileUpload = new UploadedFilesList();
		fileUpload.setFileName(edoc.getDoc_type() + ".pdf");
		User user = userProfileDao
		        .findByUserId(CommonConstants.SYSTEM_USER_USERID);
		fileUpload.setAssignedBy(user);
		fileUpload.setUploadedBy(loan.getUser());
		fileUpload.setIsActivate(true);
		fileUpload.setIsAssigned(false);
		fileUpload.setIsMiscellaneous(false);
		fileUpload.setLoan(loan);
		fileUpload.setDocumentType(edoc.getDoc_type());
		fileUpload.setLqbFileID(edoc.getDocid());
		Date date = convertLQBStringToDate(edoc.getCreated_date());
		fileUpload.setUploadedDate(date);
		if (uuid != null) {
			fileUpload.setUuidFileId(uuid);
		} else {
			fileUpload.setUuidFileId(nexeraUtility.randomStringOfLength());
		}
		fileUpload.setTotalPages(2);
		int fileUploadId = saveUploadedFile(fileUpload);
		fileUpload.setId(fileUploadId);
	}

	@Override
	public UploadedFilesList fetchUsingFileLQBDocId(String lqbDocID) {
		return uploadedFilesListDao.fetchUsingFileLQBDocId(lqbDocID);
	}

	@Override
	@Transactional
	public Boolean assignFileToNeeds(
	        Map<Integer, FileAssignmentMappingVO> mapFileMappingToNeed,
	        Integer loanId, Integer userId, Integer assignedBy) {
		Boolean isSuccess = false;

		try {

			Map<Integer, FileAssignmentMappingVO> filteredMapping = new HashMap<Integer, FileAssignmentMappingVO>();
			// First do the Extra Assignment and remove it from HashSet
			for (Integer key : mapFileMappingToNeed.keySet()) {
				LoanNeedsList loanNeed = loanNeedListDAO.findById(key);

				NeedsListMaster needMaster = loanNeed.getNeedsListMaster();
				LOG.info("Need Master label: " + needMaster.getLabel());
				if (needMaster.getLabel()
				        .equals(CommonConstants.EXTRA_DOCUMENT)) {
					FileAssignmentMappingVO mapping = mapFileMappingToNeed
					        .get(key);
					List<Integer> fileIds = mapping.getFileIds();
					for (Integer fileId : fileIds) {
						deactivateFileUsingFileId(fileId);
					}
				} else {
					filteredMapping.put(key, mapFileMappingToNeed.get(key));
				}
			}
			for (Integer key : filteredMapping.keySet()) {
				LoanNeedsList loanNeed = loanNeedListDAO.findById(key);

				NeedsListMaster needMaster = loanNeed.getNeedsListMaster();
				LOG.info("Need Master label: " + needMaster.getLabel());
				System.out.println("Need Master label: "
				        + needMaster.getLabel());

				FileAssignmentMappingVO mapping = filteredMapping.get(key);
				List<Integer> fileIds = mapping.getFileIds();
				Integer newFileRowId = null;
				if (mapping.getIsMiscellaneous()) {
					UploadedFilesList filesList = loanService
					        .fetchUploadedFromLoanNeedId(key);
					LOG.info("fetchUploadedFromLoanNeedId returned : "
					        + filesList);
					if (filesList != null) {
						fileIds.add(filesList.getId());
					}
					Integer needId=needMaster.getId();
					newFileRowId = mergeAndUploadFiles(fileIds, loanId, userId,
					        assignedBy,needId);

					for (Integer fileId : fileIds) {
						deactivateFileUsingFileId(fileId);
					}
				} else {
					newFileRowId = fileIds.get(0);
				}
				LOG.info("new file pdf path :: " + newFileRowId);
				updateFileInLoanNeedList(key, newFileRowId);
				updateIsAssignedToTrue(newFileRowId);
			}
			changeWorkItem(filteredMapping, loanId);
			isSuccess = true;
			// commonResponseVO = RestUtil.wrapObjectForSuccess( true );

		} catch (Exception e) {
			LOG.error("exception in converting  : " + e.getMessage(), e);

			isSuccess = false;
		}

		return isSuccess;

	}

	private void changeWorkItem(
	        Map<Integer, FileAssignmentMappingVO> mapFileMappingToNeed,
	        int loanID) {
		// Start
		// Loan ID : get LoanManagerWFID from the loan object
		//
		for (Integer keyID : mapFileMappingToNeed.keySet()) {
			LoanNeedsList needListItem = loanNeedListDAO.findById(keyID);
			if (needListItem.getNeedsListMaster() != null
			        && MasterNeedsEnum.getNeedReference(needListItem
			                .getNeedsListMaster().getId()) == MasterNeedsEnum.Disclsoure_Available
			        || MasterNeedsEnum.getNeedReference(needListItem
			                .getNeedsListMaster().getId()) == MasterNeedsEnum.Signed_Disclosure
			        || MasterNeedsEnum.getNeedReference(needListItem
			                .getNeedsListMaster().getId()) == MasterNeedsEnum.Appraisal_Report) {
				changeItemStatus(loanID,
				        MasterNeedsEnum.getNeedReference(needListItem
				                .getNeedsListMaster().getId()));
			}

		}

	}

	private void changeItemStatus(int loanID, MasterNeedsEnum masterNeed) {

		LoanVO loanVO = loanService.getLoanByID(loanID);
		int wfExecID = loanVO.getLoanManagerWorkflowID();
		WorkflowExec workflowExec = new WorkflowExec();
		workflowExec.setId(wfExecID);
		String masterNeedKey = null;
		String statusToBeSent = null;
		if (masterNeed == MasterNeedsEnum.Disclsoure_Available) {
			masterNeedKey = WorkflowConstants.WORKFLOW_ITEM_DISCLOSURE_STATUS;
			statusToBeSent = LoanStatus.disclosureAvail;
		} else if (masterNeed == MasterNeedsEnum.Signed_Disclosure) {
			masterNeedKey = WorkflowConstants.WORKFLOW_ITEM_DISCLOSURE_STATUS;
			statusToBeSent = LoanStatus.disclosureSigned;
		} else if (masterNeed == MasterNeedsEnum.Appraisal_Report) {
			masterNeedKey = WorkflowConstants.WORKFLOW_ITEM_APPRAISAL_STATUS;
			statusToBeSent = LoanStatus.appraisalAvailable;
		}
		if (masterNeedKey != null) {
			WorkflowItemMaster workflowItemMaster = workflowService
			        .getWorkflowByType(masterNeedKey);
			WorkflowItemExec workItemRef = workflowService
			        .getWorkflowItemExecByType(workflowExec, workflowItemMaster);
			// Create Map here.
			Map<String, Object> map = new HashMap<String, Object>();
			if (masterNeed != null && statusToBeSent != null) {
				map.put(WorkflowDisplayConstants.WORKITEM_STATUS_KEY_NAME,
				        statusToBeSent);
			}
			map.put(WorkflowDisplayConstants.LOAN_ID_KEY_NAME, loanVO.getId());
			String params = Utils.convertMapToJson(map);
			workflowService.saveParamsInExecTable(workItemRef.getId(), params);
			EngineTrigger engineTrigger = applicationContext
			        .getBean(EngineTrigger.class);
			engineTrigger.startWorkFlowItemExecution(workItemRef.getId());
		}
	}

	@Override
	@Transactional
	public void updateAssignments(Integer needId, Integer fileId, Integer loanId) {
		LoanNeedsList loanNeed = loanService.fetchByNeedId(needId, loanId);
		updateFileInLoanNeedList(loanNeed.getId(), fileId);
		updateIsAssignedToTrue(fileId);
		Map<Integer, FileAssignmentMappingVO> map = new HashMap<Integer, FileAssignmentMappingVO>();
		map.put(loanNeed.getId(), new FileAssignmentMappingVO());
		changeWorkItem(map, loanId);
	}

	@Override
	public NeedsListMaster getNeedFromMaster(Integer needId) {

		NeedsListMaster masterData=needsDao.getNeed(needId);
		return masterData;
	}

}
