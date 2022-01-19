package com.nashtech.rookie.assetmanagement.service;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;

import org.springframework.stereotype.Service;

import com.nashtech.rookie.assetmanagement.entity.User;
import com.nashtech.rookie.assetmanagement.dto.AppUser;
import com.nashtech.rookie.assetmanagement.entity.Enum.Type;

import com.nashtech.rookie.assetmanagement.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private static final String USER_DEFAULT = "refresher";
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username);
		if (user != null) {

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getType().toString());
			ArrayList<SimpleGrantedAuthority> authorities = new ArrayList(Arrays.asList(authority));
			/*
			 * AppUser app = new AppUser(user.getUsername(), user.getPassword(),
			 * authorities, user.getLocation()); app.getAuthorities().stream().forEach(s ->
			 * System.out.println(s.toString()));
			 */

			return new AppUser(user.getUsername(), user.getPassword(), !user.isDisable(), authorities,
					user.getLocation());
//			return buildUserDetails(user.getUsername(), user.getPassword(), user.getType());
		} else {
			throw new UsernameNotFoundException("User not found with name: " + username);
		}
	}

	private UserDetails buildUserDetails(String username, String password, Type type) {
		UserBuilder builder = AppUser.withUsername(username);
		builder.password(password);
		builder.roles(type.toString());
		return builder.build();
	}
}
