package com.nashtech.rookie.assetmanagement.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.nashtech.rookie.assetmanagement.entity.Enum.StateAssignment;

import lombok.Data;

@Data
public class AssignmentDTO {
	

	private Long assignmentId;
	
	@NotNull
	private AssetDTO asset;
	

	@NotNull
	private UserDTO assignedTo;
	
	private UserDTO assignedBy;

	
	@NotNull
	private LocalDate assignedDate;
	
	private StateAssignment state;

	private String note;
}
