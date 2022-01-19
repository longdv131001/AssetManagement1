package com.nashtech.rookie.assetmanagement.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.nashtech.rookie.assetmanagement.entity.Enum.State;

import lombok.Data;

@Data

public class AssetDTO {
	private String assetCode;
	private String assetName;
	private CategoryDTO category;
	private String specification;
	private LocalDate installed;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	private String location;
	
}
