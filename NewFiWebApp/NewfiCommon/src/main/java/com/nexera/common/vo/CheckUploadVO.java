package com.nexera.common.vo;

public class CheckUploadVO {

	private Boolean isUploadSuccess;
	private Integer uploadFileId;
	private String uuid;
	private String fileName;
	
	
	
	public Boolean getIsUploadSuccess() {
		return isUploadSuccess;
	}
	public void setIsUploadSuccess(Boolean isUploadSuccess) {
		this.isUploadSuccess = isUploadSuccess;
	}
	public Integer getUploadFileId() {
		return uploadFileId;
	}
	public void setUploadFileId(Integer uploadFileId) {
		this.uploadFileId = uploadFileId;
	}
	@Override
	public String toString() {
		return "CheckUploadVO [isUploadSuccess=" + isUploadSuccess
				+ ", uploadFileId=" + uploadFileId + "]";
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
