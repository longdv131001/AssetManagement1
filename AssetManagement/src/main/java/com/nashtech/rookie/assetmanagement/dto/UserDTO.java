package com.nashtech.rookie.assetmanagement.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nashtech.rookie.assetmanagement.dto.validater.BirthdateConstraint;
import com.nashtech.rookie.assetmanagement.dto.validater.JoinedDateConstraint;
import com.nashtech.rookie.assetmanagement.entity.Enum.Gender;
import com.nashtech.rookie.assetmanagement.entity.Enum.Type;

import lombok.Data;

@Data
public class UserDTO {
	private String staffCode;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	@BirthdateConstraint
	private LocalDate birthDate;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Gender gender;
	
	@JoinedDateConstraint
	@NotNull
	private LocalDate joinedDate;
	
	private String username;
	
	private String location;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Type type;
	
	private boolean isDisable = false;
	
	private boolean isNewUser;
	
}