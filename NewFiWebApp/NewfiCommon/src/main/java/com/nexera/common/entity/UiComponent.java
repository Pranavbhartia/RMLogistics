package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the uicomponent database table.
 * 
 */
@Entity
@Table(name = "uicomponent")
@NamedQuery(name="UiComponent.findAll", query="SELECT u FROM UiComponent u")
public class UiComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String componentDescription;
	private UiComponent uiComponent;
	private List<UiComponent> listUiComponents;
	private List<UiComponentPermission> uiComponentPermissions;

	public UiComponent() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(name="component_description")
	public String getComponentDescription() {
		return this.componentDescription;
	}

	public void setComponentDescription(String componentDescription) {
		this.componentDescription = componentDescription;
	}


	//bi-directional many-to-one association to UiComponent
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent_component")
	public UiComponent getUiComponent() {
		return this.uiComponent;
	}

	public void setUiComponent(UiComponent uiComponent) {
		this.uiComponent = uiComponent;
	}


	//bi-directional many-to-one association to UiComponent
	@OneToMany(mappedBy="uiComponent")
	public List<UiComponent> getListUiComponents() {
		return this.listUiComponents;
	}

	public void setListUiComponents(List<UiComponent> listUiComponents) {
		this.listUiComponents = listUiComponents;
	}

	public UiComponent addUicomponent(UiComponent uicomponent) {
		getListUiComponents().add(uicomponent);
		uicomponent.setUiComponent(this);

		return uicomponent;
	}

	public UiComponent removeUicomponent(UiComponent uicomponent) {
		getListUiComponents().remove(uicomponent);
		uicomponent.setUiComponent(null);

		return uicomponent;
	}


	//bi-directional many-to-one association to UiComponentPermission
	@OneToMany(mappedBy="uiComponent")
	public List<UiComponentPermission> getUiComponentPermissions() {
		return this.uiComponentPermissions;
	}

	public void setUiComponentPermissions(List<UiComponentPermission> uiComponentPermissions) {
		this.uiComponentPermissions = uiComponentPermissions;
	}

	public UiComponentPermission addUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUiComponentPermissions().add(uicomponentpermission);
		uicomponentpermission.setUiComponent(this);

		return uicomponentpermission;
	}

	public UiComponentPermission removeUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUiComponentPermissions().remove(uicomponentpermission);
		uicomponentpermission.setUiComponent(null);

		return uicomponentpermission;
	}

}