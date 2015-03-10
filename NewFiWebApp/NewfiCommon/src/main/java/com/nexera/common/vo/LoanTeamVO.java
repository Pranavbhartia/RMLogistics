package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class LoanTeamVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean active;
	private Date assignedOn;
	private String permissionType;
	private LoanVO loan;
	private UserVO assignedBy;
	private UserVO user;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Date getAssignedOn() {
		return assignedOn;
	}
	public void setAssignedOn(Date assignedOn) {
		this.assignedOn = assignedOn;
	}
	public String getPermissionType() {
		return permissionType;
	}
	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}
	public LoanVO getLoan() {
		return loan;
	}
	public void setLoan(LoanVO loan) {
		this.loan = loan;
	}
	public UserVO getAssignedBy() {
		return assignedBy;
	}
	public void setAssignedBy(UserVO assignedBy) {
		this.assignedBy = assignedBy;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
	

}