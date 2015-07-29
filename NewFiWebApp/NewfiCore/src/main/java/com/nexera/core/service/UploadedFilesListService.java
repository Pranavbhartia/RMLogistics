package com.nexera.core.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.json.JSONObject;

import com.nexera.common.entity.Loan;
import com.nexera.common.entity.NeedsListMaster;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.CheckUploadVO;
import com.nexera.common.vo.FileAssignmentMappingVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.LQBDocumentVO;
import com.nexera.common.vo.lqb.LQBResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;

public interface UploadedFilesListService {

	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList);

	public List<UploadedFilesListVO> fetchAll(Integer userId, Integer loanId);

	public void updateIsAssignedToTrue(Integer fileId);

	public void updateFileInLoanNeedList(Integer needId, Integer fileId);

	public String findFileNameFromId(Integer fileId);

	public void deactivateFileUsingFileId(Integer fileId);

	public List<File> downloadFileFromService(List<Integer> fileIds);

	public Integer mergeAndUploadFiles(List<Integer> fileIds, Integer loanId,
	        Integer userId, Integer assignedBy,Integer needId) throws IOException,
	        COSVisitorException;

	public Integer addUploadedFilelistObejct(File file, Integer loanId,
	        Integer userId, Integer assignedBy, String lqbDocumentID,
	        String uuidValue, String fileName);

	void updateIsAssignedToTrue(List<Integer> fileIds);

	public UploadedFilesList fetchUsingFileId(Integer fileId);

	public UploadedFilesList fetchUsingFileUUID(String uuidFileId);

	public UploadedFilesList fetchUsingFileLQBDocId(String lqbDocID);

	public LQBResponseVO uploadDocumentInLandingQB(LQBDocumentVO lqbDocumentVO,Integer loanId,UserVO user,String fileName);

	public CheckUploadVO uploadFileByEmail(InputStream stream, String content,
	        Integer userId, Integer loanId, Integer assignedBy, String fileName)
	        throws IOException, COSVisitorException, Exception;

	public CheckUploadVO uploadFile(File file, String contentType,
	        Integer userId, Integer loanId, Integer assignedBy,
	        Boolean isNeedAssigned, String fileName,Integer needId);

	public LQBResponseVO createLQBVO(Integer userID, byte[] bytes,
	        Integer loanId, String createLQBVO, Boolean isNeedAssigned,Integer needId,String fileName);

	public LQBResponseVO fetchLQBDocument(LQBDocumentVO lqbDocumentVO)
	        throws IOException;

	public LQBResponseVO getAllDocumentsFromLQBByUUID(String loanNumber)
	        throws IOException;

	public String fetchDocumentIDByUUID(LQBResponseVO lqbResponseVO, String uuId);

	public LQBResponseVO parseLQBXMLResponse(JSONObject receivedResponse);

	public void updateUploadedDocument(List<LQBedocVO> edocsList, Loan loan,
	        Date timeBeforeCallMade);

	public void getFileContentFromLQBUsingUUID(HttpServletResponse response,
	        String uuid);

	public InputStream createLQBObjectToReadFile(String lqbDocID)
	        throws IOException;

	public Boolean assignFileToNeeds(

	Map<Integer, FileAssignmentMappingVO> mapFileMappingToNeed, Integer loanId,
	        Integer userId, Integer assignedBy);

	public void insertFileIntoNewFi(LQBedocVO edoc, Loan loan, String uuid);

	void updateAssignments(Integer loanNeedId, Integer fileId, Integer loanId);

	public UploadedFilesListVO buildUpdateFileVo(UploadedFilesList file);

	List<UploadedFilesListVO> buildUpdateFileVoList(
	        List<UploadedFilesList> filesLists);
	
	public NeedsListMaster getNeedFromMaster(Integer needId);

}
