package com.nexera.common.vo;

import java.io.Serializable;

public class RealtorDetailVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String licenceInfo;
	private String profileUrl;
	private UserVO user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLicenceInfo() {
		return licenceInfo;
	}
	public void setLicenceInfo(String licenceInfo) {
		this.licenceInfo = licenceInfo;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}


}