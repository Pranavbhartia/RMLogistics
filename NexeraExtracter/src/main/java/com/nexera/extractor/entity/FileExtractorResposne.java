package com.nexera.extractor.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class FileExtractorResposne {

	private Map<String, List<UIEntity>> fileDetailList;
	private Long folderTimeStamp;
	private Date folderTSDtFormat;
	private String folderTSDsFormat;
	public Date getFolderTSDtFormat() {
		return folderTSDtFormat;
	}
	public void setFolderTSDtFormat(Date folderTSDtFormat) {
		this.folderTSDtFormat = folderTSDtFormat;
	}
	public String getFolderTSDsFormat() {
		return folderTSDsFormat;
	}
	public void setFolderTSDsFormat(String folderTSDsFormat) {
		this.folderTSDsFormat = folderTSDsFormat;
	}
	public Date getCurrentDate() {
		return folderTSDtFormat;
	}
	public void setCurrentDate(Date currentDate) {
		this.folderTSDtFormat = currentDate;
	}
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
