package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Type;

/**
 * The persistent class for the uicomponentpermission database table.
 * 
 */
@Entity
@NamedQuery(name = "UiComponentPermission.findAll", query = "SELECT u FROM UiComponentPermission u")
public class UiComponentPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Boolean delete;
	private Boolean read;
	private Boolean write;
	private UiComponent uiComponent;
	private UserRole userRole;

	public UiComponentPermission() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getDelete() {
		return this.delete;
	}

	public void setDelete(Boolean delete) {
		this.delete = delete;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getRead() {
		return this.read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getWrite() {
		return this.write;
	}

	public void setWrite(Boolean write) {
		this.write = write;
	}

	// bi-directional many-to-one association to UiComponent
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_ui_component")
	public UiComponent getUiComponent() {
		return this.uiComponent;
	}

	public void setUiComponent(UiComponent uiComponent) {
		this.uiComponent = uiComponent;
	}

	// bi-directional many-to-one association to UserRole
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_role")
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}