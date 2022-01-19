package com.nashtech.rookie.assetmanagement.rest;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.context.SecurityContextHolder;
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

import com.nashtech.rookie.assetmanagement.dto.AssignmentDTO;
import com.nashtech.rookie.assetmanagement.entity.Assignment;
import com.nashtech.rookie.assetmanagement.entity.Enum.StateAssignment;
import com.nashtech.rookie.assetmanagement.service.AssetService;
import com.nashtech.rookie.assetmanagement.service.AssignmentService;
import com.nashtech.rookie.assetmanagement.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/assignments")
public class AssignmentRestController {
 
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private AssetService assetService;
	
	@Autowired 
	private UserService userService;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping
	public AssignmentDTO createNewAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
		Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
        return modelMapper.map(assignmentService.createAssignment(assignment), AssignmentDTO.class);

	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping
	public AssignmentDTO updateAssignment(@Valid  @RequestBody AssignmentDTO assignmentDTO) {
		Assignment assignment = modelMapper.map(assignmentDTO, Assignment.class);
		String location = userService
				.getLocationByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if (assetService.getDetailAsset(assignment.getAsset().getAssetCode()).getLocation().equals(location)) {
			return modelMapper.map(assignmentService.updateAssignment(assignment), AssignmentDTO.class);
		}
		return null;
		
	}

	@PreAuthorize("hasAuthority('Admin')")
	@PutMapping("/{id}")
	public AssignmentDTO updateAssignmentState(@PathVariable String id, @RequestParam StateAssignment state) {
		return modelMapper.map(assignmentService.updateState(id, state),AssignmentDTO.class);	

	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@DeleteMapping("{id}")
	public void deleteAssignment(@PathVariable Long id) {
		assignmentService.deleteAssignment(id);
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping
	public List<AssignmentDTO> getAll(){
		return assignmentService.getAllAssignment().stream().map(as -> modelMapper.map(as, AssignmentDTO.class)).collect(Collectors.toList());
	}
	
	
	@GetMapping("{id}")
	public AssignmentDTO getDetail(@PathVariable Long id) {
		return modelMapper.map(assignmentService.getDetailAssignment(id),AssignmentDTO.class);

	}
	@GetMapping("/searchuser")
	public List<AssignmentDTO> findByUser(){
		return assignmentService.findByUser().stream().map(a -> modelMapper.map(a, AssignmentDTO.class)).collect(Collectors.toList());
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/search")
	public List<AssignmentDTO> searchAssignment(@RequestParam(value = "searchTerm", required = false) String searchTerm){
		if(searchTerm != null && !searchTerm.equals("")) {
		return assignmentService.searchByAssignment(searchTerm).stream().map(a ->  modelMapper.map(a, AssignmentDTO.class)).collect(Collectors.toList());
	}
		else {
			return assignmentService.getAllAssignment().stream().map(as -> modelMapper.map(as, AssignmentDTO.class)).collect(Collectors.toList());
		}
	}

}

