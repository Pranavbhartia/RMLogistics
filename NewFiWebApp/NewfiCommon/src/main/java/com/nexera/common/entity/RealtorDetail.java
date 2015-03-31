package com.nexera.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.nexera.common.vo.RealtorDetailVO;

/**
 * The persistent class for the realtordetails database table.
 * 
 */
@Entity
@Table(name = "realtordetails")
@NamedQuery(name = "RealtorDetail.findAll", query = "SELECT r FROM RealtorDetail r")
public class RealtorDetail implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String licenceInfo;
	private String profileUrl;

	public RealtorDetail() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "licence_info")
	public String getLicenceInfo() {
		return this.licenceInfo;
	}

	public void setLicenceInfo(String licenceInfo) {
		this.licenceInfo = licenceInfo;
	}

	@Column(name = "profile_url")
	public String getProfileUrl() {
		return this.profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public static RealtorDetailVO convertFromEntityToVO(
	        final RealtorDetail inputEntity) {
		RealtorDetailVO realtorDetailVO = new RealtorDetailVO();
		if (inputEntity != null) {
			realtorDetailVO.setId(inputEntity.getId());
			realtorDetailVO.setLicenceInfo(inputEntity.getLicenceInfo());
			realtorDetailVO.setProfileUrl(inputEntity.getProfileUrl());
		}
		return realtorDetailVO;
	}

	public static RealtorDetail convertFromVOToEntity(
	        final RealtorDetailVO inputVO) {
		RealtorDetail realtor = new RealtorDetail();
		if (inputVO != null) {
			realtor.setId(inputVO.getId());
			realtor.setLicenceInfo(inputVO.getLicenceInfo());
			realtor.setProfileUrl(inputVO.getProfileUrl());
		}

		return realtor;
	}
}