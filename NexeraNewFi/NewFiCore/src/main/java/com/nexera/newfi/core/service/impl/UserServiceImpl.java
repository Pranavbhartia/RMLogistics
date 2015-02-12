package com.nexeracore.newfi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexeracommon.newfi.dao.UserDao;
import com.nexeracommon.newfi.model.UserModel;
import com.nexeracore.newfi.service.UserService;

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
