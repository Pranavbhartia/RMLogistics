package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.nexera.common.enums.ActiveInternalEnum;
import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserRoleMasterVO;

/**
 * The persistent class for the internaluserdetails database table.
 * 
 */
@Entity
@Table(name = "internaluserdetails")
@NamedQuery(name = "InternalUserDetail.findAll", query = "SELECT i FROM InternalUserDetail i")
public class InternalUserDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	
	private ActiveInternalEnum activeInternal;
	private User manager;

	private InternalUserRoleMaster internaUserRoleMaster;

	public InternalUserDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}	
	
	@Column(name = "active_internal")
	@Type(type = "com.nexera.common.enums.helper.ActiveInternalType")
			public ActiveInternalEnum getActiveInternal() {
		return this.activeInternal;
	}

	public void setActiveInternal(ActiveInternalEnum activeInternal) {
		this.activeInternal = activeInternal;
	}

	// bi-directional many-to-one association to UserRole
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_role")
	public InternalUserRoleMaster getInternaUserRoleMaster() {
		return internaUserRoleMaster;
	}

	public void setInternaUserRoleMaster(
	        InternalUserRoleMaster internaUserRoleMaster) {
		this.internaUserRoleMaster = internaUserRoleMaster;
	}

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager")
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public static InternalUserDetailVO convertFromEntityToVO(
	        InternalUserDetail internalUserDetail) {

		if (internalUserDetail == null)
			return null;

		InternalUserDetailVO detailVO = new InternalUserDetailVO();
		detailVO.setId(internalUserDetail.getId());
		if (internalUserDetail != null) {
			detailVO.setActiveInternal(internalUserDetail.getActiveInternal());
		}
		detailVO.setInternalUserRoleMasterVO(buildInternalUserRoleMasterVO(internalUserDetail
		        .getInternaUserRoleMaster()));

		return detailVO;
	}

	public static InternalUserDetail convertFromVOToEntity(
	        InternalUserDetailVO internalUserDetailVO) {
		// TODO Auto-generated method stub

		if (internalUserDetailVO == null)
			return null;

		InternalUserDetail detail = new InternalUserDetail();
		detail.setInternaUserRoleMaster(parseInternalUserRoleMasterModel(internalUserDetailVO
		        .getInternalUserRoleMasterVO()));
		detail.setId(internalUserDetailVO.getId());
		if (internalUserDetailVO.getActiveInternal() != null) {
			detail.setActiveInternal(internalUserDetailVO.getActiveInternal());
		}
		return detail;
	}

	private static InternalUserRoleMasterVO buildInternalUserRoleMasterVO(
	        InternalUserRoleMaster internal) {
		if (internal == null)
			return null;

		InternalUserRoleMasterVO masterVO = new InternalUserRoleMasterVO();
		masterVO.setId(internal.getId());
		masterVO.setRoleName(internal.getRoleName());
		masterVO.setRoleDescription(internal.getRoleDescription());

		return masterVO;
	}

	private static InternalUserRoleMaster parseInternalUserRoleMasterModel(
	        InternalUserRoleMasterVO internalVO) {
		if (internalVO == null)
			return null;

		InternalUserRoleMaster master = new InternalUserRoleMaster();
		master.setId(internalVO.getId());
		master.setRoleDescription(internalVO.getRoleDescription());

		return master;
	}

}