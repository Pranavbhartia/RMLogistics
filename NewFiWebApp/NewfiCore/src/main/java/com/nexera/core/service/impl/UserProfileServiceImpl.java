package com.nexera.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.nexera.common.dao.UserProfileDao;
import com.nexera.common.entity.CustomerDetail;
import com.nexera.common.entity.User;
import com.nexera.common.entity.UserRole;
import com.nexera.common.vo.UserRoleVO;
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
		
		CustomerDetail customerDetails =  user.getCustomerDetail();
		
		UserVO userVO = new UserVO();
		userVO.setId(user.getId());
		userVO.setFirstName(user.getFirstName());
		userVO.setLastName(user.getLastName());
		userVO.setEmailId(user.getEmailId());
		userVO.setUsername(user.getUsername());
		userVO.setPassword(user.getPassword());
		userVO.setStatus(user.getStatus());
		
		UserRoleVO userRole = new UserRoleVO();
		userRole.setId(user.getUserRole().getId());
		userVO.setUserRole(userRole);
		
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setPhoneNumber(user.getPhoneNumber());
		
		return userVO;
	}

}
