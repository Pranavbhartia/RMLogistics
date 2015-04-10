package com.nexera.common.vo;

import java.util.List;

public class FileAssignmentMappingVO {

	private List<Integer> fileIds;
	private Boolean isMiscellaneous;
	
	
	public List<Integer> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Integer> fileIds) {
		this.fileIds = fileIds;
	}
	public Boolean getIsMiscellaneous() {
		return isMiscellaneous;
	}
	public void setIsMiscellaneous(Boolean isMiscellaneous) {
		this.isMiscellaneous = isMiscellaneous;
	}
	
	
}
