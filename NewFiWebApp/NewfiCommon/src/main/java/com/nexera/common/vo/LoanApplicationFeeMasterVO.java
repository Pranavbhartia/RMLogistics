package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;

public class LoanApplicationFeeMasterVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String comments;
	private Double fee;
	private Date modifiedDate;
	private LoanTypeMasterVO loanTypeMaster;
	private UserVO modifiedBy;
	private PropertyTypeMasterVO propertyTypeMaster;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public LoanTypeMasterVO getLoanTypeMaster() {
		return loanTypeMaster;
	}
	public void setLoanTypeMaster(LoanTypeMasterVO loanTypeMaster) {
		this.loanTypeMaster = loanTypeMaster;
	}
	public UserVO getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public PropertyTypeMasterVO getPropertyTypeMaster() {
		return propertyTypeMaster;
	}
	public void setPropertyTypeMaster(PropertyTypeMasterVO propertyTypeMaster) {
		this.propertyTypeMaster = propertyTypeMaster;
	}

}