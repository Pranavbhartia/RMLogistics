package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the uicomponentpermission database table.
 * 
 */
@Entity
@NamedQuery(name="UiComponentPermission.findAll", query="SELECT u FROM UiComponentPermission u")
public class UiComponentPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private byte delete;
	private byte read;
	private byte write;
	private UiComponent uicomponent;
	private UserRole userrole;

	public UiComponentPermission() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public byte getDelete() {
		return this.delete;
	}

	public void setDelete(byte delete) {
		this.delete = delete;
	}


	public byte getRead() {
		return this.read;
	}

	public void setRead(byte read) {
		this.read = read;
	}


	public byte getWrite() {
		return this.write;
	}

	public void setWrite(byte write) {
		this.write = write;
	}


	//bi-directional many-to-one association to UiComponent
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="app_ui_component")
	public UiComponent getUicomponent() {
		return this.uicomponent;
	}

	public void setUicomponent(UiComponent uicomponent) {
		this.uicomponent = uicomponent;
	}


	//bi-directional many-to-one association to UserRole
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_role")
	public UserRole getUserrole() {
		return this.userrole;
	}

	public void setUserrole(UserRole userrole) {
		this.userrole = userrole;
	}

}