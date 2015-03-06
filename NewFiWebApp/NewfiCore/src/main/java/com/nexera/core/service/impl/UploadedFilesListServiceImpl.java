package com.nexera.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.metrics.S3ServiceMetric;
import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.entity.User;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;
import com.nexera.core.utility.NexeraUtility;

@Component
@Transactional
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	private static final Logger LOG = LoggerFactory.getLogger(UploadedFilesListServiceImpl.class);
	
	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;
	
	@Autowired
	private NeedsListService needsListService;
	
	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	@Override
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.saveUploadedFile(uploadedFilesList);
	}

	@Override
	public List<UploadedFilesListVO> fetchAll(Integer userId ,  Integer loanId) {
		List<UploadedFilesList> filesLists = uploadedFilesListDao.fetchAll(userId , loanId);
		return buildUpdateFileVoList(filesLists);
		
	}

	
	public static List<UploadedFilesListVO> buildUpdateFileVoList(List<UploadedFilesList> filesLists){
		
		List<UploadedFilesListVO> uploadedFilesListVOs = new ArrayList<UploadedFilesListVO>();
		for (UploadedFilesList uploadedFilesList : filesLists) {
			uploadedFilesListVOs.add(buildUpdateFileVo(uploadedFilesList));
		}
		return uploadedFilesListVOs;
	}
	
	
	public static UploadedFilesListVO buildUpdateFileVo(UploadedFilesList uploadedFilesList){
		
		if(uploadedFilesList == null)
			return null;
		
		UploadedFilesListVO filesListVO = new UploadedFilesListVO();
		filesListVO.setId(uploadedFilesList.getId());
		filesListVO.setIsActivate(uploadedFilesList.getIsActivate());
		filesListVO.setIsAssigned(uploadedFilesList.getIsAssigned());
		filesListVO.setS3path(uploadedFilesList.getS3path());
		filesListVO.setUploadedDate(uploadedFilesList.getUploadedDate());
		filesListVO.setFileName(uploadedFilesList.getFileName());
		
		
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
	public void updateFileInLoanNeedList( Integer needId , Integer fileId) {
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
			 UploadedFilesList uploadedFilesList = uploadedFilesListDao.fetchUsingFileId(fileId);
			 try {
				 
				String fileName = s3FileUploadServiceImpl.downloadFile(uploadedFilesList.getS3path(),NexeraUtility.tomcatDirectoryPath()+File.separator+ uploadedFilesList.getFileName());
				downloadFiles.add(fileName);
				LOG.info("s3 download returned  : "+fileName);
			 } catch (Exception e) {
				LOG.info("Excepttion in downloading file : "+e.getMessage());
				
			}
		}
		return downloadFiles;
	}
	
	public Integer mergeAndUploadFiles ( List<Integer> fileIds , Integer loanId) throws IOException, COSVisitorException {
		List<String> filePaths = downloadFileFromS3Service(fileIds);
		String newFilepath = null;
		newFilepath = NexeraUtility.joinPDDocuments(filePaths);
		Integer fileSavedId = addUploadedFilelistObejct(new File(newFilepath) ,loanId );
		for (Integer fileId : fileIds) {
			deactivateFileUsingFileId(fileId);
		}
		return fileSavedId;
	}
	
	public Integer addUploadedFilelistObejct(File file  , Integer loanId){
		String s3Path = s3FileUploadServiceImpl.uploadToS3(file, "User" , "complete" );
		User user  = getUserObject();
		Loan loan  = new Loan();
		loan.setId(loanId);
		
		UploadedFilesList uploadedFilesList = new UploadedFilesList();
		uploadedFilesList.setIsActivate(true);
		uploadedFilesList.setIsAssigned(false);
		uploadedFilesList.setS3path(s3Path);
		uploadedFilesList.setUploadedBy( user);
		uploadedFilesList.setUploadedDate(new Date());
		uploadedFilesList.setLoan(loan);
		uploadedFilesList.setFileName(file.getName());
		Integer fileSavedId = saveUploadedFile(uploadedFilesList);
		return fileSavedId;
	}

	@Override
	public UploadedFilesList fetchUsingFileId(Integer fileId) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.fetchUsingFileId(fileId);
	}
	
	
	
}
