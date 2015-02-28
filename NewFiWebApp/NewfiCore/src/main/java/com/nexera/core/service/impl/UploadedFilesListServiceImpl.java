package com.nexera.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.vo.LoanVO;
import com.nexera.common.vo.UploadedFilesListVO;
import com.nexera.core.service.NeedsListService;
import com.nexera.core.service.UploadedFilesListService;

@Component
@Transactional
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;
	
	@Autowired
	private NeedsListService needsListService;
	
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
	public void updateFileInLoanNeedList( Integer needId , Integer fileId) {
			uploadedFilesListDao.updateFileInLoanNeedList(needId, fileId);
	}
}
