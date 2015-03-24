package com.nexera.core.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.springframework.web.multipart.MultipartFile;

import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.lqb.LQBDocumentVO;

public interface UploadedFilesListService {
	
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList); 
	
	public List<UploadedFilesListVO> fetchAll(Integer userId , Integer loanId);
	
	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);

	public String findFileNameFromId(Integer fileId);
	
	public void deactivateFileUsingFileId(Integer fileId);

	public List<String> downloadFileFromS3Service(List<Integer> fileIds);
	
	public Integer mergeAndUploadFiles(List<Integer> fileIds , Integer loanId, Integer userId , Integer assignedBy) throws  IOException, COSVisitorException;

	public Integer addUploadedFilelistObejct(File file, Integer loanId ,  Integer userId , Integer assignedBy);

	void updateIsAssignedToTrue(List<Integer> fileIds);

	public UploadedFilesList fetchUsingFileId(Integer fileId);

	public UploadedFilesList fetchUsingFileUUID(String uuidFileId);

	public CheckUploadVO uploadFile(MultipartFile file, Integer userId, Integer loanId,
			Integer assignedBy);

	public void uploadDocumentInLandingQB(LQBDocumentVO lqbDocumentVO);
	
}
