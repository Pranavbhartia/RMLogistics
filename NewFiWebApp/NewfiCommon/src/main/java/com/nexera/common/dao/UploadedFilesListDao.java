package com.nexera.common.dao;

import java.util.Date;
import java.util.List;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.UploadedFilesList;

public interface UploadedFilesListDao {

	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList);

	public List<UploadedFilesList> fetchAll(Integer uesrId, Integer loanId);

	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);

	public String findFileNameFromId(Integer fileId);

	public void deactivateFileUsingFileId(Integer fileId);

	public UploadedFilesList fetchUsingFileId(Integer fileId);

	public UploadedFilesList fetchUsingFileUUID(String uuid, Loan loan);

	public void updateLQBDocumentInUploadNeededFile(String lqbDocumentId,
	        Integer rowId);

	public List<UploadedFilesList> fetchFilesBasedOnTimeStamp(Loan loan,
	        Date timeBeforeCallMade);

	public void remove(UploadedFilesList uploadedFileList);

	public UploadedFilesList fetchUsingFileLQBFieldId(String lqbfieldId);

	public UploadedFilesList fetchUsingFileLQBDocId(String lqbDocID);

}
