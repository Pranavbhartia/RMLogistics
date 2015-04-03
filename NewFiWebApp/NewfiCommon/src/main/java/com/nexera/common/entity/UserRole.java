package com.nexera.common.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.vo.UserRoleVO;

/**
 * The persistent class for the userrole database table.
 * 
 */
@Entity
@Table(name = "userrole")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private User modifiedBy;
	private Date modifiedDate;
	private String roleCd;
	private String label;
	private String roleDescription;
	private Boolean visibleOnLoanTeam;
	private List<UiComponentPermission> uiComponentPermissions;
	private List<User> users;

	public UserRole() {
	}

	public UserRole(UserRolesEnum internal) {
		this.id = internal.getRoleId();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "modified_by")
	public User getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_date")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "role_cd")
	public String getRoleCd() {
		return this.roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	@Column(name = "label")
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Column(name = "role_description")
	public String getRoleDescription() {
		return this.roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@Column(name = "visible_on_loan_team", columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public Boolean getVisibleOnLoanTeam() {
		return this.visibleOnLoanTeam;
	}

	public void setVisibleOnLoanTeam(Boolean visibleOnLoanTeam) {
		this.visibleOnLoanTeam = visibleOnLoanTeam;
	}

	// bi-directional many-to-one association to UiComponentPermission
	@OneToMany(mappedBy = "userRole")
	public List<UiComponentPermission> getUiComponentPermissions() {
		return this.uiComponentPermissions;
	}

	public void setUiComponentPermissions(
	        List<UiComponentPermission> uicomponentpermissions) {
		this.uiComponentPermissions = uicomponentpermissions;
	}

	public UiComponentPermission addUiComponentPermission(
	        UiComponentPermission uicomponentpermission) {
		getUiComponentPermissions().add(uicomponentpermission);
		uicomponentpermission.setUserRole(this);

		return uicomponentpermission;
	}

	public UiComponentPermission removeUiComponentPermission(
	        UiComponentPermission uicomponentpermission) {
		getUiComponentPermissions().remove(uicomponentpermission);
		uicomponentpermission.setUserRole(null);

		return uicomponentpermission;
	}

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "userRole")
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserRole(null);

		return user;
	}

	public static UserRoleVO convertFromEntityToVO(final UserRole role) {

		if (role == null)
			return null;

		UserRoleVO roleVO = new UserRoleVO();

		roleVO.setId(role.getId());
		roleVO.setRoleCd(role.getRoleCd());
		roleVO.setLabel(role.getLabel());
		roleVO.setRoleDescription(role.getRoleDescription());

		return roleVO;

	}

	public static UserRole convertFromVOToEntity(UserRoleVO roleVO) {

		UserRole role = new UserRole();

		if (roleVO == null) {
			return role;
		} else {

			role.setId(roleVO.getId());
			role.setRoleCd(roleVO.getRoleCd());
			role.setLabel(roleVO.getLabel());
			role.setRoleDescription(roleVO.getRoleDescription());
		}

		return role;

	}

}