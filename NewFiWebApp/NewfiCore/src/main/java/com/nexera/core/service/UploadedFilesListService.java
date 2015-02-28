package com.nexera.core.service;

import java.util.List;

import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.UploadedFilesListVO;

public interface UploadedFilesListService {
	
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList); 
	
	public List<UploadedFilesListVO> fetchAll(Integer userId , Integer loanId);
	
	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);
}
