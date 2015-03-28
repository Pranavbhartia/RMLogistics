package com.nexera.common.dao;

import java.util.List;

import com.nexera.common.entity.UploadedFilesList;

public interface UploadedFilesListDao {

	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList);

	public List<UploadedFilesList> fetchAll(Integer uesrId , Integer loanId);

	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);

	public String findFileNameFromId(Integer fileId);

	public void deactivateFileUsingFileId(Integer fileId);

	public UploadedFilesList fetchUsingFileId(Integer fileId);
	
	public UploadedFilesList fetchUsingFileUUID(String uuid);

	public void updateLQBDocumentInUploadNeededFile(String lqbDocumentId,
			Integer rowId);
	
}
