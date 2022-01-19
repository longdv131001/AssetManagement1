package com.nashtech.rookie.assetmanagement.dto;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.nashtech.rookie.assetmanagement.entity.Enum.Type;

import lombok.Data;

@Data
public class UserInfo {

	private String username;

	private String location;
	
	private boolean isNewUser;
	
	@Enumerated(EnumType.STRING)
	private Type type;


}
