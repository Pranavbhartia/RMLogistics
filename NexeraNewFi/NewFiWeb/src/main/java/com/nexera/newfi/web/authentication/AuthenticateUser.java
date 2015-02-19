package com.nexera.newfi.web.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nexera.newfi.common.model.UserModel;
import com.nexera.newfi.core.service.UserService;

public class AuthenticateUser implements UserDetailsService {

	@Autowired
	private UserService userService;
	
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserModel user = userService.loadUserByUsername(userName);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return buildUserForAuthentication(user, authorities);
	}

	
	private User buildUserForAuthentication(UserModel user, 
			List<GrantedAuthority> authorities) {
		System.out.println("buildUserForAuthentication method called: "+user.getEmail()+
				user.getPassword());
			return new User(user.getEmail(), 
				user.getPassword(), true, 
	                        true, true, true, authorities);
		}
 
}
