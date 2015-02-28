package com.nexera.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRoleVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private UserVO modifiedBy;
	private Date modifiedDate;
	private String roleCd;
	private String label;
	private String roleDescription;
	private Boolean visibleOnLoanTeam;
	private List<UiComponentPermissionVO> uiComponentPermissions;
	private List<UserVO> users;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserVO getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserVO modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Boolean getVisibleOnLoanTeam() {
		return visibleOnLoanTeam;
	}

	public void setVisibleOnLoanTeam(Boolean visibleOnLoanTeam) {
		this.visibleOnLoanTeam = visibleOnLoanTeam;
	}

	public List<UiComponentPermissionVO> getUiComponentPermissions() {
		return uiComponentPermissions;
	}

	public void setUiComponentPermissions(
			List<UiComponentPermissionVO> uiComponentPermissions) {
		this.uiComponentPermissions = uiComponentPermissions;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}