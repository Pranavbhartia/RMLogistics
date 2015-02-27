package com.nexera.common.dao.impl;

import org.springframework.stereotype.Component;

import com.nexera.common.dao.UploadedFilesListDao;
import com.nexera.common.entity.UploadedFilesList;

@Component
public class UploadedFilesListDaoImpl extends GenericDaoImpl implements UploadedFilesListDao {

	@Override
	public Integer saveUploadedFile(UploadedFilesList uploadedFilesList) {
		return (Integer)save(uploadedFilesList);
	}

}
