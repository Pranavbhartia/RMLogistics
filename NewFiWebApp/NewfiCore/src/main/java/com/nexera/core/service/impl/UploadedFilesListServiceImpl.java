package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.core.service.UploadedFilesListService;

@Component
@Transactional
public class UploadedFilesListServiceImpl implements UploadedFilesListService {

	@Autowired
	private UploadedFilesListDao uploadedFilesListDao;
	
	@Override
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		// TODO Auto-generated method stub
		return uploadedFilesListDao.saveUploadedFile(uploadedFilesList);
	}

}
