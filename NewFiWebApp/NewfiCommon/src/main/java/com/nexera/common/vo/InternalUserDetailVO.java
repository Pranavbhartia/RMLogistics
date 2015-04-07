package com.nexera.common.vo;

import java.io.Serializable;

import com.nexera.common.enums.ActiveInternalEnum;

public class InternalUserDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private ActiveInternalEnum activeInternal;
	private UserVO user;
	private UserVO manager;
	private InternalUserRoleMasterVO internalUserRoleMasterVO;
	private String lqbUsername;
	private String lqbPassword;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ActiveInternalEnum getActiveInternal() {
		return activeInternal;
	}

	public void setActiveInternal(ActiveInternalEnum activeInternalEnum) {
		this.activeInternal = activeInternalEnum;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public UserVO getManager() {
		return manager;
	}

	public void setManager(UserVO manager) {
		this.manager = manager;
	}

	public InternalUserRoleMasterVO getInternalUserRoleMasterVO() {
		return internalUserRoleMasterVO;
	}

	public void setInternalUserRoleMasterVO(
	        InternalUserRoleMasterVO internalUserRoleMasterVO) {
		this.internalUserRoleMasterVO = internalUserRoleMasterVO;
	}

	public String getLqbUsername() {
		return lqbUsername;
	}

	public void setLqbUsername(String lqbUsername) {
		this.lqbUsername = lqbUsername;
	}

	public String getLqbPassword() {
		return lqbPassword;
	}

	public void setLqbPassword(String lqbPassword) {
		this.lqbPassword = lqbPassword;
	}

}