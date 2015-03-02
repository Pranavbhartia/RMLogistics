package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.UploadedFilesList;

public interface UploadedFilesListDao {

	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList);

	public List<UploadedFilesList> fetchAll(Integer uesrId , Integer loanId);

	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);
	
	
}
