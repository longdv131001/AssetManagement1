package com.nashtech.rookie.assetmanagement.rest;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookie.assetmanagement.dto.AppException;
import com.nashtech.rookie.assetmanagement.dto.AppUser;
import com.nashtech.rookie.assetmanagement.dto.JwtRequest;
import com.nashtech.rookie.assetmanagement.dto.JwtResponse;
import com.nashtech.rookie.assetmanagement.dto.PasswordDTO;
import com.nashtech.rookie.assetmanagement.dto.UserDTO;
import com.nashtech.rookie.assetmanagement.dto.UserInfo;
import com.nashtech.rookie.assetmanagement.entity.User;
import com.nashtech.rookie.assetmanagement.service.JwtUserDetailsService;
import com.nashtech.rookie.assetmanagement.service.UserService;
import com.nashtech.rookie.assetmanagement.utils.JwtTokenUtil;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtAuthenticationController {

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		UserInfo user = modelMapper.map(userService.findByUsername(username), UserInfo.class);
		return ResponseEntity.ok(new JwtResponse(token, user));
	}

	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public UserInfo changePassword(@RequestBody PasswordDTO passwordDTO) throws Exception {
		AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String oldPassword = passwordDTO.getOldPassword() == null ? "" : passwordDTO.getOldPassword();
		User user = userService.changePassword(principal.getUsername(), oldPassword,
				passwordDTO.getNewPassword());
		return modelMapper.map(user, UserInfo.class);
	}

	private void authenticate(String username, String password) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);

		}
	}
}
