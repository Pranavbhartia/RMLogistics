package com.nexera.common.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the realtordetails database table.
 * 
 */
@Entity
@Table(name="realtordetails")
@NamedQuery(name="RealtorDetail.findAll", query="SELECT r FROM RealtorDetail r")
public class RealtorDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String licenceInfo;
	private String profileUrl;
	private User userBean;

	public RealtorDetail() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Column(name="licence_info")
	public String getLicenceInfo() {
		return this.licenceInfo;
	}

	public void setLicenceInfo(String licenceInfo) {
		this.licenceInfo = licenceInfo;
	}


	@Column(name="profile_url")
	public String getProfileUrl() {
		return this.profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}


	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user")
	public User getUserBean() {
		return this.userBean;
	}

	public void setUserBean(User userBean) {
		this.userBean = userBean;
	}

}