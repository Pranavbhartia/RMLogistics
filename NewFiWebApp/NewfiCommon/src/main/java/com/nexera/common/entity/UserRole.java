package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the userrole database table.
 * 
 */
@Entity
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int modifiedBy;
	private Date modifiedDate;
	private String roleCd;
	private String roleDescription;
	private byte visibleOnLoanTeam;
	private List<UiComponentPermission> uicomponentpermissions;
	private List<User> users;

	public UserRole() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="modified_by")
	public int getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	@Column(name="role_cd")
	public String getRoleCd() {
		return this.roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}


	@Column(name="role_description")
	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}


	@Column(name="visible_on_loan_team")
	public byte getVisibleOnLoanTeam() {
		return this.visibleOnLoanTeam;
	}

	public void setVisibleOnLoanTeam(byte visibleOnLoanTeam) {
		this.visibleOnLoanTeam = visibleOnLoanTeam;
	}


	//bi-directional many-to-one association to UiComponentPermission
	@OneToMany(mappedBy="userrole")
	public List<UiComponentPermission> getUicomponentpermissions() {
		return this.uicomponentpermissions;
	}

	public void setUicomponentpermissions(List<UiComponentPermission> uicomponentpermissions) {
		this.uicomponentpermissions = uicomponentpermissions;
	}

	public UiComponentPermission addUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUicomponentpermissions().add(uicomponentpermission);
		uicomponentpermission.setUserrole(this);

		return uicomponentpermission;
	}

	public UiComponentPermission removeUicomponentpermission(UiComponentPermission uicomponentpermission) {
		getUicomponentpermissions().remove(uicomponentpermission);
		uicomponentpermission.setUserrole(null);

		return uicomponentpermission;
	}


	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userrole")
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserrole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserrole(null);

		return user;
	}

}