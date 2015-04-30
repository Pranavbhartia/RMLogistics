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
import javax.persistence.Table;

import com.nexera.common.vo.InternalUserDetailVO;
import com.nexera.common.vo.InternalUserStateMappingVO;

@Entity
@Table(name = "internaluserstatemapping")
public class InternalUserStateMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private User user;
	private StateLookup stateLookup;
	private String licenseNumber;

	public InternalUserStateMapping(){
		
	}
	public InternalUserStateMapping(Integer id){
		this.id=id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "state_id")
	public StateLookup getStateLookup() {
		return stateLookup;
	}

	public void setStateLookup(StateLookup stateLookup) {
		this.stateLookup = stateLookup;
	}

	
	
	@Column(name = "license_number")
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	
	
	public static InternalUserStateMappingVO convertFromEntityToVO(
			InternalUserStateMapping internalUserStateMapping) {

		if (internalUserStateMapping == null)
			return null;

		InternalUserStateMappingVO detailVO = new InternalUserStateMappingVO();
		detailVO.setId(internalUserStateMapping.getId());
		
		detailVO.setIsChecked(true);
		detailVO.setStateId(internalUserStateMapping.getStateLookup().getId());
		detailVO.setUserId(internalUserStateMapping.getUser().getId());
		detailVO.setLicense(internalUserStateMapping.getLicenseNumber());
		return detailVO;
	}
	
	
	public static InternalUserStateMapping convertFromVOToEntity(InternalUserStateMappingVO inputVo) {

		if (inputVo == null)
			return null;

		InternalUserStateMapping internalUserStateMapping = new InternalUserStateMapping();
		
		internalUserStateMapping.setId(inputVo.getId());
		internalUserStateMapping.setStateLookup(new StateLookup(inputVo.getStateId()));
		internalUserStateMapping.setUser(new User(inputVo.getUserId()));
		internalUserStateMapping.setLicenseNumber(inputVo.getLicense());
		return internalUserStateMapping;
	}
	
	
}
