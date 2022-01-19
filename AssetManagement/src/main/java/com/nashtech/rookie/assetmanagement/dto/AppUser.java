package com.nashtech.rookie.assetmanagement.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUser extends User implements UserDetails{


	private static final long serialVersionUID = 1L;

	private String location;
	
	public AppUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		// TODO Auto-generated constructor stub
	}

	public AppUser(String username, String password,boolean enabled, Collection<? extends GrantedAuthority> authorities,
			String location) {
		super(username, password, enabled, true, true, true, authorities);
		this.location = location;
	}

	public AppUser(String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	

}
