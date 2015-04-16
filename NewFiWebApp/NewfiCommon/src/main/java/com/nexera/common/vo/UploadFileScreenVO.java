package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UploadFileScreenVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Map<String, List<LoanNeedsListVO>> listLoanNeedsListMap;
	List<LoanNeedsListVO> listLoanNeedsListVO;
	List<UploadedFilesListVO> listUploadedFilesListVO;
	private NeededItemScoreVO neededItemScoreVO;
	private String loanEmailID;
	
	
	
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
	public Map<String, List<LoanNeedsListVO>> getListLoanNeedsListMap() {
		return listLoanNeedsListMap;
	}
	public void setListLoanNeedsListMap(
			Map<String, List<LoanNeedsListVO>> listLoanNeedsListMap) {
		this.listLoanNeedsListMap = listLoanNeedsListMap;
	}

	public NeededItemScoreVO getNeededItemScoreVO() {
		return neededItemScoreVO;
	}

	public void setNeededItemScoreVO(NeededItemScoreVO neededItemScoreVO) {
		this.neededItemScoreVO = neededItemScoreVO;
	}

	public String getLoanEmailID() {
	    return loanEmailID;
    }

	public void setLoanEmailID(String loanEmailID) {
	    this.loanEmailID = loanEmailID;
    }

}
