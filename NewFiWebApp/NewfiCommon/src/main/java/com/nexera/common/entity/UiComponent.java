package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the uicomponent database table.
 * 
 */
@Entity
@NamedQuery(name="UiComponent.findAll", query="SELECT u FROM UiComponent u")
public class UiComponent implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String componentDescription;
	private UiComponent uicomponent;
	private List<UiComponent> uicomponents;
	private List<UiComponentPermission> uicomponentpermissions;

	public UiComponent() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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
	public UiComponent getUicomponent() {
		return this.uicomponent;
	}

	public void setUicomponent(UiComponent uicomponent) {
		this.uicomponent = uicomponent;
	}


	//bi-directional many-to-one association to UiComponent
	@OneToMany(mappedBy="uicomponent")
	public List<UiComponent> getUicomponents() {
		return this.uicomponents;
	}

	public void setUicomponents(List<UiComponent> uicomponents) {
		this.uicomponents = uicomponents;
	}

	public UiComponent addUicomponent(UiComponent uicomponent) {
		getUicomponents().add(uicomponent);
		uicomponent.setUicomponent(this);

		return uicomponent;
	}

	public UiComponent removeUicomponent(UiComponent uicomponent) {
		getUicomponents().remove(uicomponent);
		uicomponent.setUicomponent(null);

		return uicomponent;
	}


	//bi-directional many-to-one association to UiComponentPermission
	@OneToMany(mappedBy="uicomponent")
	public List<UiComponentPermission> getUicomponentpermissions() {
		return this.uicomponentpermissions;
	}

	public void setUicomponentpermissions(List<UiComponentPermission> uicomponentpermissions) {
		this.uicomponentpermissions = uicomponentpermissions;
	}

	public UiComponentPermission addUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUicomponentpermissions().add(uicomponentpermission);
		uicomponentpermission.setUicomponent(this);

		return uicomponentpermission;
	}

	public UiComponentPermission removeUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUicomponentpermissions().remove(uicomponentpermission);
		uicomponentpermission.setUicomponent(null);

		return uicomponentpermission;
	}

}