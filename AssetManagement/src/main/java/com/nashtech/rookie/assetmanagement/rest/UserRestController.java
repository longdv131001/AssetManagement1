package com.nashtech.rookie.assetmanagement.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookie.assetmanagement.dto.UserDTO;
import com.nashtech.rookie.assetmanagement.entity.User;
import com.nashtech.rookie.assetmanagement.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserRestController {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/{staffcode}")
	public UserDTO getUserById(@PathVariable("staffcode") String staffCode) {
		User user = userService.getUserById(staffCode);
		return modelMapper.map(user, UserDTO.class);
	}
	
	@PostMapping
	public UserDTO createUser(@Valid @RequestBody  UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		return modelMapper.map(userService.createUser(user), UserDTO.class);
	}
	
	@PutMapping("{staffcode}")
	public UserDTO updateUser(@PathVariable("staffcode") String staffcode,@Valid @RequestBody UserDTO userDTO) {
		User user = modelMapper.map(userDTO, User.class);
		return modelMapper.map(userService.updateUser(staffcode, user), UserDTO.class);
	}
	
	@DeleteMapping("{staffcode}")
	public UserDTO deleteUser(@PathVariable("staffcode") String staffCode) {
		User user = userService.disableUser(staffCode);
		return modelMapper.map(user, UserDTO.class);
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping({"","/search"})
	public List<UserDTO> searchUser(@RequestParam("searchTerm") Optional<String> searchTerm){
		if(searchTerm.isPresent()) {
			return userService.searchUser(searchTerm.get()).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
		}
		else {
			return userService.getAllUsers().stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
		}
	}
	
	@GetMapping({"/list"})
	public List<UserDTO> listUser(@RequestParam(value = "searchTerm", required = false) String searchTerm){
		return userService.searchUser(searchTerm).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
	}
	
	
}
			
		
	


