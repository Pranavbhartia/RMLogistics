package com.nexera.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.commons.WebServiceOperations;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.exception.FatalException;
import com.nexera.common.vo.AssignedUserVO;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LQBDocumentVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.LoanService;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.utility.NexeraUtility;

@Component
@Transactional
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	private static final Logger LOG = LoggerFactory
	        .getLogger(UploadedFilesListServiceImpl.class);

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;

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
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.saveUploadedFile(uploadedFilesList);
	}

	@Override
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
		assignedUserVo.setUserRole(userProfileService
		        .buildUserRoleVO(uploadedFilesList.getAssignedBy()
		                .getUserRole()));

		filesListVO.setAssignedByUser(assignedUserVo);

		LoanVO loanVo = new LoanVO();
		loanVo.setId(uploadedFilesList.getLoan().getId());

		filesListVO.setLoan(loanVo);
		return filesListVO;
	}

	@Override
	public void updateIsAssignedToTrue(Integer fileId) {
		uploadedFilesListDao.updateIsAssignedToTrue(fileId);
	}

	@Override
	public void updateIsAssignedToTrue(List<Integer> fileIds) {
		for (Integer fileId : fileIds) {
			uploadedFilesListDao.updateIsAssignedToTrue(fileId);
		}
	}

	@Override
	public void updateFileInLoanNeedList(Integer needId, Integer fileId) {
		uploadedFilesListDao.updateFileInLoanNeedList(needId, fileId);
	}

	@Override
	public String findFileNameFromId(Integer fileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.findFileNameFromId(fileId);
	}

	@Override
	public void deactivateFileUsingFileId(Integer fileId) {
		uploadedFilesListDao.deactivateFileUsingFileId(fileId);

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

	public Integer mergeAndUploadFiles(List<Integer> fileIds, Integer loanId,
	        Integer userId, Integer assignedBy) throws IOException,
	        COSVisitorException {
		List<String> filePaths = downloadFileFromS3Service(fileIds);
		String newFilepath = null;
		newFilepath = nexeraUtility.joinPDDocuments(filePaths);
		Integer fileSavedId = addUploadedFilelistObejct(new File(newFilepath),
		        loanId, userId, assignedBy);
		for (Integer fileId : fileIds) {
			deactivateFileUsingFileId(fileId);
		}
		return fileSavedId;
	}

	
	
	
	public Integer addUploadedFilelistObejct(File file, Integer loanId,
	        Integer userId, Integer assignedBy) {
		
		
	/*
	 * commenting code for password protection
	 * 
	 * 	UserVO userVo = userProfileService.findUser(userId);
		PDDocument doc;
		try {
			doc = PDDocument.load(file);
			if(doc.isEncrypted()){
				doc.setAllSecurityToBeRemoved(true);
				doc.encrypt(null,userVo.getEmailId() );
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			LOG.info("Error in encrypting file");
			e1.printStackTrace();
		}*/
		
		
		
		
		String s3Path = s3FileUploadServiceImpl.uploadToS3(file, "document",
		        "complete");
		LOG.info("File Path : " + file.getPath());
		String s3PathThumbNail = null;
		String thumbPath = null;
		try {
			thumbPath = nexeraUtility.convertPDFToThumbnail(file.getPath(),
			        nexeraUtility.tomcatDirectoryPath());

		} catch (Exception e) {

			LOG.error("Exception in s3PathThumbNail : " + e.getMessage());
		}

		LOG.info("The thumbnail path for local  :  " + thumbPath);
		if (thumbPath != null) {
			File thumbpath = new File(thumbPath);
			s3PathThumbNail = s3FileUploadServiceImpl.uploadToS3(thumbpath, "document", "image");
			
			if(thumbpath.exists()){
				thumbpath.delete();
			}
			
		}

		LOG.info("The s3PathThumbNail path for   :  " + s3PathThumbNail);
		
		

		User user = new User();
		user.setId(userId);
		Loan loan = new Loan();
		loan.setId(loanId);
		User assignByUser = new User();
		assignByUser.setId(assignedBy);

		List<PDPage> splittedFiles = nexeraUtility.splitPDFTOPages(file);

		UploadedFilesList uploadedFilesList = new UploadedFilesList();
		uploadedFilesList.setIsActivate(true);
		uploadedFilesList.setIsAssigned(false);
		uploadedFilesList.setS3path(s3Path);
		uploadedFilesList.setUploadedBy(user);
		uploadedFilesList.setUploadedDate(new Date());
		uploadedFilesList.setLoan(loan);
		uploadedFilesList.setFileName(file.getName());
		uploadedFilesList.setS3ThumbPath(s3PathThumbNail);
		uploadedFilesList.setAssignedBy(assignByUser);
		uploadedFilesList.setUuidFileId(nexeraUtility.randomStringOfLength());
		uploadedFilesList.setTotalPages(splittedFiles.size());
		Integer fileSavedId = saveUploadedFile(uploadedFilesList);
		return fileSavedId;
	}

	@Override
	public UploadedFilesList fetchUsingFileId(Integer fileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.fetchUsingFileId(fileId);
	}

	@Override
	public UploadedFilesList fetchUsingFileUUID(String uuidFileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.fetchUsingFileUUID(uuidFileId);
	}

	@Override
	public CheckUploadVO uploadFile(File file, String contentType,
	        Integer userId, Integer loanId, Integer assignedBy) {
		String s3Path = null;

		LOG.info("File content type  : " + contentType);
		String localFilePath = null;
		Boolean fileUpload = false;
		CheckUploadVO checkVo = new CheckUploadVO();
		try {
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
			checkVo.setIsUploadSuccess(fileUpload);
			if (fileUpload) {

				File serverFile = new File(localFilePath);
				Integer savedRowId = addUploadedFilelistObejct(serverFile,
				        loanId, userId, assignedBy);
				LOG.info("Added File document row : " + savedRowId);
				checkVo.setUploadFileId(savedRowId);
				
				UploadedFilesList latestRow = fetchUsingFileId(savedRowId);
				
				checkVo.setUuid(latestRow.getUuidFileId());
				checkVo.setFileName(latestRow.getFileName());
				
				if(serverFile.exists()){
					serverFile.delete();
				}
			}

		} catch (Exception e) {
			LOG.info(" Exception uploading s3 :  " + e.getMessage());
			return checkVo;
		}
		LOG.info("file.getOriginalFilename() : " + file.getName());

		LOG.info("The s3 path is : " + s3Path);
		return checkVo;
	}

	@Override
	public void uploadDocumentInLandingQB(LQBDocumentVO lqbDocumentVO) {
		// TODO Auto-generated method stub
		 if ( lqbDocumentVO != null ) {
             JSONObject uploadObject = createUploadPdfDocumentJsonObject(
                 WebServiceOperations.OP_NAME_LOAN_UPLOAD_PDF_DOCUMENT, lqbDocumentVO );
             JSONObject receivedResponse = lqbInvoker.invokeLqbService( uploadObject.toString() );
             LOG.info(" receivedResponse while uploading LQB Document : "+receivedResponse);
		 }
	}
	
	
	

    @Async
    public void createLQBVO(Integer fileId , Integer loanId) {
    	 User user = getUserObject();
         LQBDocumentVO documentVO = new LQBDocumentVO();
         try {
			 documentVO.setDocumentType( "application/pdf" );
			 StringBuffer stringBuf = new StringBuffer();
			
			 stringBuf.append( " uploaded by : " );

			 stringBuf.append( user.getFirstName() ).append( "-" ).append( user.getLastName() );
			 documentVO.setNotes( stringBuf.toString() );
			 documentVO.setsDataContent( nexeraUtility.getContentFromFile( fileId ) );
			 documentVO.setsLoanNumber( loanService.getLoanByID( loanId ).getLqbFileId() );

			 uploadDocumentInLandingQB( documentVO );
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.info("Exception in uploadDocumentInLandingQB : Saving exception in error table");
			// TODO save exception in error block
		}
         LOG.info( "Assignment : uploadDocumentInLandingQB " + documentVO );
    }

	
	public JSONObject createUploadPdfDocumentJsonObject( String opName, LQBDocumentVO documentVO )
    {
        JSONObject json = new JSONObject();
        JSONObject jsonChild = new JSONObject();
        try {
            jsonChild.put( WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER, documentVO.getsLoanNumber() );
            jsonChild.put( WebServiceMethodParameters.PARAMETER_DOCUMENT_TYPE, documentVO.getDocumentType() );
            jsonChild.put( WebServiceMethodParameters.PARAMETER_NOTES, documentVO.getNotes() );
            jsonChild.put( WebServiceMethodParameters.PARAMETER_S_DATA_CONTENT, documentVO.getsDataContent() );

            json.put( "opName", opName );
            json.put( "loanVO", jsonChild );
        } catch ( JSONException e ) {

            throw new FatalException( "Could not parse json " + e.getMessage() );
        }
        return json;
    }


	@Override
	public CheckUploadVO uploadFileByEmail(InputStream stream,
	        String contentType, Integer userId, Integer loanId,
	        Integer assignedBy) throws IOException, COSVisitorException {
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

			checkUploadVO = uploadFile(file, contentType, userId, loanId,
			        assignedBy);
			return checkUploadVO;
		}

		return checkUploadVO;
	}
}
