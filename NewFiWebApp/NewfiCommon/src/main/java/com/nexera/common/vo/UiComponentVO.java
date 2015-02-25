package com.nexera.common.vo;

import java.io.Serializable;
import java.util.List;

public class UiComponentVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String componentDescription;
	private UiComponentVO uiComponent;
	private List<UiComponentVO> listUiComponents;
	private List<UiComponentPermissionVO> uiComponentPermissions;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComponentDescription() {
		return componentDescription;
	}
	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}
	public UiComponentVO getUiComponent() {
		return uiComponent;
	}
	public void setUiComponent(UiComponentVO uiComponent) {
		this.uiComponent = uiComponent;
	}
	public List<UiComponentVO> getListUiComponents() {
		return listUiComponents;
	}
	public void setListUiComponents(List<UiComponentVO> listUiComponents) {
		this.listUiComponents = listUiComponents;
	}
	public List<UiComponentPermissionVO> getUiComponentPermissions() {
		return uiComponentPermissions;
	}
	public void setUiComponentPermissions(
			List<UiComponentPermissionVO> uiComponentPermissions) {
		this.uiComponentPermissions = uiComponentPermissions;
	}

}