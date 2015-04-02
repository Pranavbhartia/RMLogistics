package com.nexera.common.dao;

import com.nexera.common.entity.LoanNeedsList;
import com.nexera.common.entity.UploadedFilesList;



public interface LoanNeedListDao extends GenericDao
{

	LoanNeedsList findLoanNeedsListByFile(UploadedFilesList uploadedFileList);

}
