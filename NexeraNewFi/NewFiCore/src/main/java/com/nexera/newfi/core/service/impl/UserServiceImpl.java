package com.nexera.newfi.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexera.newfi.common.model.UserModel;
import com.nexera.newfi.core.service.UserService;
import com.nexera.newfi.persistence.dao.UserDao;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	public String getName() {
		UserModel user = userDao.findByUserName("akash@raremile.com");
		System.out.println("The User is : "+user);
		return user.getFirstName()+""+user.getLastName()+""+user.getEmail();
		//return "akash";
	}
	
		public UserModel findByUserId(Integer Id) {
		UserModel user = userDao.findByUserId(Id);
		return user;
	}

}
