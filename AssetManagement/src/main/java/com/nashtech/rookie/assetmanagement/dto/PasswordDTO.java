package com.nashtech.rookie.assetmanagement.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.nashtech.rookie.assetmanagement.entity.Enum.Gender;
import com.nashtech.rookie.assetmanagement.entity.Enum.Type;

import lombok.Data;

@Data
public class PasswordDTO {

	private String oldPassword;
	
	@NotBlank
	private String newPassword;

}
