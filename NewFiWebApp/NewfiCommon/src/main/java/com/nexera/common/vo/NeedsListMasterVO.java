package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NeedsListMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String label;
	private Date modifiedDate;
	private String needCategory;
	private List<LoanNeedsListVO> loanNeedsList;
	private UserVO modifiedBy;
	private String uploadedTo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getNeedCategory() {
		return needCategory;
	}
	public void setNeedCategory(String needCategory) {
		this.needCategory = needCategory;
	}
	public List<LoanNeedsListVO> getLoanNeedsList() {
		return loanNeedsList;
	}
	public void setLoanNeedsList(List<LoanNeedsListVO> loanNeedsList) {
		this.loanNeedsList = loanNeedsList;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getUploadedTo() {
	    return uploadedTo;
    }
	public void setUploadedTo(String uploadedTo) {
	    this.uploadedTo = uploadedTo;
    }

}