package com.nexera.common.vo;

import java.io.Serializable;

public class InternalUserDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Boolean activeInternal;
	private UserVO user;
	private UserVO manager;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getActiveInternal() {
		return activeInternal;
	}

	public void setActiveInternal(Boolean activeInternal) {
		this.activeInternal = activeInternal;
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

}