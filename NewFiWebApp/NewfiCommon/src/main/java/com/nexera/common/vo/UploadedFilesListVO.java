package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class UploadedFilesListVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Boolean isAssigned;
	private Boolean isActivate;
	private String s3path;
	private LoanVO loan;
	private UserVO uploadedBy;
	private Date uploadedDate;
	private String fileName;
	private Integer needType;
	private String s3ThumbPath;
	private AssignedUserVO assignedByUser;
	private String uuidFileId;
	private Integer totalPages;
	private String lqbFileID;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getIsAssigned() {
		return isAssigned;
	}
	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	public Boolean getIsActivate() {
		return isActivate;
	}
	public void setIsActivate(Boolean isActivate) {
		this.isActivate = isActivate;
	}
	public String getS3path() {
		return s3path;
	}
	public void setS3path(String s3path) {
		this.s3path = s3path;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public UserVO getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(UserVO uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getNeedType() {
		return needType;
	}
	public void setNeedType(Integer needType) {
		this.needType = needType;
	}
	public String getS3ThumbPath() {
		return s3ThumbPath;
	}
	public void setS3ThumbPath(String s3ThumbPath) {
		this.s3ThumbPath = s3ThumbPath;
	}
	public AssignedUserVO getAssignedByUser() {
		return assignedByUser;
	}
	public void setAssignedByUser(AssignedUserVO assignedByUser) {
		this.assignedByUser = assignedByUser;
	}
	public String getUuidFileId() {
		return uuidFileId;
	}
	public void setUuidFileId(String uuidFileId) {
		this.uuidFileId = uuidFileId;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public String getLqbFileID() {
		return lqbFileID;
	}
	public void setLqbFileID(String lqbFileID) {
		this.lqbFileID = lqbFileID;
	}
	
	
	
}
