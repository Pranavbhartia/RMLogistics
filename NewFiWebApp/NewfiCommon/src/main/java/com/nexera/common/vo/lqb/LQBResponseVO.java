package com.nexera.common.vo.lqb;

import java.util.List;

public class LQBResponseVO {

	private String result ;
	private List<LQBDocumentResponseListVO> documentResponseListVOs;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<LQBDocumentResponseListVO> getDocumentResponseListVOs() {
		return documentResponseListVOs;
	}

	public void setDocumentResponseListVOs(List<LQBDocumentResponseListVO> documentResponseListVOs) {
		this.documentResponseListVOs = documentResponseListVOs;
	}
	
}
