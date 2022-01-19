package com.nashtech.rookie.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookie.assetmanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Query("SELECT u.username FROM User u where u.username LIKE :prefix%")
	List<String> getAllUsername(@Param("prefix") String prefix);
	
	@Query(value = "Select u FROM User u where u.isDisable=0 and u.location = :location and (lower(u.username) LIKE %:searchTerm% or concat(lower(u.lastName), ' ' ,lower(u.firstName)) like %:searchTerm% or lower(u.staffCode) like %:searchTerm%) ")
	List<User> searchUser(@Param("searchTerm") String searchTerm, @Param("location") String location);
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	User findUserByUsername(String username);

	@Query("SELECT u.location from User u WHERE u.username=?1")
	String findLocationByUsername(String username);
	
	@Query("SELECT u from User u WHERE u.isDisable=0")
	List<User> getAllActiveUser();


	User findByUsername(String username);

}