package com.nashtech.rookie.assetmanagement.service;

import java.util.List;

import com.nashtech.rookie.assetmanagement.entity.User;

public interface UserService {
	List<User> getAllUsers();

	User getUserById(String staffCode);

	User createUser(User user);

	User updateUser(String staffCode, User userData);

	User disableUser(String staffCode);

	List<User> searchUser(String search);

	User changePassword(String username, String oldPassword, String newPassword);

	String getLocationByUsername(String username);

	User findByUsername(String username);

}