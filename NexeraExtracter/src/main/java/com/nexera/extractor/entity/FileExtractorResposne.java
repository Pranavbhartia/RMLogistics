package com.nexera.extractor.entity;

import java.util.List;
import java.util.Map;

public class FileExtractorResposne {

	private Map<String, List<UIEntity>> fileDetailList;
	private Long folderTimeStamp;
	public Map<String, List<UIEntity>> getFileDetailList() {
		return fileDetailList;
	}
	public void setFileDetailList(Map<String, List<UIEntity>> fileDetailList) {
		this.fileDetailList = fileDetailList;
	}
	public Long getFolderTimeStamp() {
		return folderTimeStamp;
	}
	public void setFolderTimeStamp(Long folderTimeStamp) {
		this.folderTimeStamp = folderTimeStamp;
	}
	
	
	
	
}
