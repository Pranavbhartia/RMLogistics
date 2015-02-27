package com.nexera.core.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.vo.CustomerDetailVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;

@Component
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileDao userProfileDao;
	
	@Override
	public UserVO findUser(Integer userid) {
		
		User user = userProfileDao.findByUserId(userid);
		
		
		
		UserVO userVO = new UserVO();
		
		userVO.setId(user.getId());
		userVO.setFirstName(user.getFirstName());
		userVO.setLastName(user.getLastName());
		userVO.setEmailId(user.getEmailId());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setPhotoImageUrl(user.getPhotoImageUrl());
		
		//userVO.setUsername(user.getUsername());
		//userVO.setPassword(user.getPassword());
		/*userVO.setStatus(user.getStatus());
		userVO.setStatus(user.getStatus());*/
		
		
		
		
		CustomerDetail customerDetail =  user.getCustomerDetail();
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		
		customerDetailVO.setId(customerDetail.getId());
		customerDetailVO.setAddressCity(customerDetail.getAddressCity());
		customerDetailVO.setAddressState(customerDetail.getAddressState());
		customerDetailVO.setAddressZipCode(customerDetail.getAddressZipCode());
		customerDetailVO.setSecPhoneNumber(customerDetail.getSecPhoneNumber());
		customerDetailVO.setSecEmailId(customerDetail.getSecEmailId());
		if(customerDetail.getDateOfBirth() != null ){
			customerDetailVO.setDateOfBirth(customerDetail.getDateOfBirth().getTime());
		}
		
		customerDetailVO.setProfileCompletionStatus(customerDetail.getProfileCompletionStatus());
		
		
		
		
		
		userVO.setCustomerDetail(customerDetailVO);
		
		return userVO;
	}

	@Override
	public Integer updateUser(UserVO userVO) {
		
		User user = new User();
		
		user.setId(userVO.getId());
		user.setFirstName(userVO.getFirstName());
		user.setLastName(userVO.getLastName());
		user.setEmailId(userVO.getEmailId());
		user.setPhoneNumber(userVO.getPhoneNumber());
		user.setPhotoImageUrl(userVO.getPhotoImageUrl());
		
		Integer userVOObj = userProfileDao.updateUser(user);
		
		return userVOObj;
	}

	@Override
	public Integer updateCustomerDetails(UserVO userVO) {
		
		CustomerDetailVO customerDetailVO =  userVO.getCustomerDetail();
		CustomerDetail customerDetail = new CustomerDetail();
		
		customerDetail.setId(customerDetailVO.getId());
		customerDetail.setAddressCity(customerDetailVO.getAddressCity());
		customerDetail.setAddressState(customerDetailVO.getAddressState());
		customerDetail.setAddressZipCode(customerDetailVO.getAddressZipCode());
		customerDetail.setSecPhoneNumber(customerDetailVO.getSecPhoneNumber());
		customerDetail.setSecEmailId(customerDetailVO.getSecEmailId());
		if(customerDetailVO.getDateOfBirth() != null){
			customerDetail.setDateOfBirth(new Date(customerDetailVO.getDateOfBirth()));
		}else{
			customerDetail.setDateOfBirth(null);
		}
		customerDetail.setProfileCompletionStatus(customerDetailVO.getProfileCompletionStatus());
		
		Integer customerDetailVOObj = userProfileDao.updateCustomerDetails(customerDetail);
		return customerDetailVOObj;
	}

	
	
}
