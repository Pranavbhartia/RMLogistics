package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
public class LoanNeedsListVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean active;
	private String comments;
	private Boolean deleted;
	private String fileId;
	private String fileUrl;
	private Boolean mandatory;
	private Boolean systemAction;
	private Date uploadedDate;
	private LoanVO loan;
	private NeedsListMasterVO needsListMaster;
	private UserVO uploadedBy;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Boolean getMandatory() {
		return mandatory;
	}
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	public Boolean getSystemAction() {
		return systemAction;
	}
	public void setSystemAction(Boolean systemAction) {
		this.systemAction = systemAction;
	}
	public Date getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public NeedsListMasterVO getNeedsListMaster() {
		return needsListMaster;
	}
	public void setNeedsListMaster(NeedsListMasterVO needsListMaster) {
		this.needsListMaster = needsListMaster;
	}
	public UserVO getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(UserVO uploadedBy) {
		this.uploadedBy = uploadedBy;
	}


}