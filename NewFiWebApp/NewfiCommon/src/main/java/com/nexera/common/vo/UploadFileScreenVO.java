package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

public class UploadFileScreenVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<LoanNeedsListVO> listLoanNeedsListVO;
	List<UploadedFilesListVO> listUploadedFilesListVO;
	
	
	public List<LoanNeedsListVO> getListLoanNeedsListVO() {
		return listLoanNeedsListVO;
	}
	public void setListLoanNeedsListVO(List<LoanNeedsListVO> listLoanNeedsListVO) {
		this.listLoanNeedsListVO = listLoanNeedsListVO;
	}
	public List<UploadedFilesListVO> getListUploadedFilesListVO() {
		return listUploadedFilesListVO;
	}
	public void setListUploadedFilesListVO(
			List<UploadedFilesListVO> listUploadedFilesListVO) {
		this.listUploadedFilesListVO = listUploadedFilesListVO;
	}
	
	

}
