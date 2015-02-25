package com.nexera.common.vo;

import java.io.Serializable;
public class UiComponentPermissionVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean delete;
	private Boolean read;
	private Boolean write;
	private UiComponentVO uiComponent;
	private UserRoleVO userRole;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getDelete() {
		return delete;
	}
	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Boolean getWrite() {
		return write;
	}
	public void setWrite(Boolean write) {
		this.write = write;
	}
	public UiComponentVO getUiComponent() {
		return uiComponent;
	}
	public void setUiComponent(UiComponentVO uiComponent) {
		this.uiComponent = uiComponent;
	}
	public UserRoleVO getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoleVO userRole) {
		this.userRole = userRole;
	}

}