package com.nashtech.rookie.assetmanagement.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nashtech.rookie.assetmanagement.dto.AppException;
import com.nashtech.rookie.assetmanagement.dto.AppUser;
import com.nashtech.rookie.assetmanagement.entity.User;
import com.nashtech.rookie.assetmanagement.repository.AssignmentRepository;
import com.nashtech.rookie.assetmanagement.repository.UserRepository;
import com.nashtech.rookie.assetmanagement.service.UserService;
import com.nashtech.rookie.assetmanagement.utils.DateUtils;
import com.nashtech.rookie.assetmanagement.utils.UserUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserUtils userUtil;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	UserRepository userRepository;

	@Autowired
	private AssignmentRepository assignmentRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.getAllActiveUser().stream().filter(u ->
		u.getLocation().equals(getLocation())).collect(Collectors.toList());
	}

	String getLocation() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			AppUser principal = (AppUser) auth.getPrincipal();
		    return principal.getLocation();
		}
		return "Unknow";
	}
	
	@Override
	public User createUser(User user) {
		if (user.getJoinedDate().isBefore(user.getBirthDate())) {
			throw new IllegalArgumentException("Joined date must be later than birthdate");
		}
		
		String username = userUtil.createUsername(user.getFirstName(), user.getLastName());
		user.setLocation(getLocation());
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(getPasswordFromUser(user)));
		user.setNewUser(true);
		return userRepository.save(user);
	}


	@Override
	public User updateUser(String staffCode, User userData) {
		User user = userRepository.findById(staffCode).orElseThrow(() -> new AppException("User not found"));
		if(user.isDisable()) {
			throw new AppException("User was disable");
		}
		if (!user.getFirstName().toLowerCase().equals(userData.getFirstName().toLowerCase())
				|| !user.getLastName().toLowerCase().equals(userData.getLastName().toLowerCase())) {
			throw new IllegalArgumentException("Must not update firstname and lastname!!!");
		}
		if (user.getJoinedDate().isBefore(user.getBirthDate())) {
			throw new IllegalArgumentException("Joined date must be later than birthdate");
		}
		
		user.setBirthDate(userData.getBirthDate());
		user.setGender(userData.getGender());
		user.setJoinedDate(userData.getJoinedDate());
		user.setType(userData.getType());
		return userRepository.save(user);
	}

	@Override
	public User getUserById(String staffCode) {
		return userRepository.findById(staffCode).get();
	}

	@Override
	public User disableUser(String staffCode) {
		User user = userRepository.findById(staffCode).orElseThrow(() -> new AppException("User not found"));
		if(user.isDisable()) {
			throw new AppException("User was already disable");
		}
		if(!user.getAssignedAssignments().isEmpty()) {
			throw new AppException("Can not disable user that still have assignment");
		}
		user.setDisable(true);
		return userRepository.save(user);

	}

	@Override
	public List<User> searchUser(String search) {
		List<User> result = userRepository.searchUser(search.toLowerCase(), getLocation());
		return result;
	}

	@Override
	public User changePassword(String username, String oldPassword, String newPassword) {
		User user = userRepository.findByUsername(username) ;
		if(user == null) {
			throw new AppException("User not found");
		}
		if(user.isDisable()) {
			throw new AppException("User was disable");
		}
		if(!user.isNewUser() && !passwordEncoder.matches(oldPassword, user.getPassword())) {
			throw new IllegalArgumentException("Old password is incorrect!");
		}
		if(passwordEncoder.matches(newPassword, user.getPassword())) {
			throw new IllegalArgumentException("New password must not be the same with current password");
		}
		user.setPassword(passwordEncoder.encode(newPassword));
		if(user.isNewUser()) {
			user.setNewUser(false);
		}
		return userRepository.save(user);
	}
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public String generatePassword(String username, LocalDate dob) {
		return username + "@" + DateUtils.dateToString("ddMMyyyy", dob);
	}

	public String getUsernameFromUser(User user) {
		return userUtil.createUsername(user.getFirstName(), user.getLastName());
	}

	public String getPasswordFromUser(User user) {
		return generatePassword(getUsernameFromUser(user), user.getBirthDate());
	}

	@Override
	public String getLocationByUsername(String username) {
		return userRepository.findLocationByUsername(username);
	}

}
