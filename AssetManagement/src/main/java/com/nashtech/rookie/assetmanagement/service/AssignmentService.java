package com.nashtech.rookie.assetmanagement.service;

import java.util.List;

import com.nashtech.rookie.assetmanagement.entity.Assignment;
import com.nashtech.rookie.assetmanagement.entity.Enum.StateAssignment;

public interface AssignmentService {



	Assignment createAssignment(Assignment assignment);

	Assignment updateAssignment(Assignment assignment);

	void deleteAssignment(Long id);

	List<Assignment> getAllAssignment();

	Assignment getDetailAssignment(Long id);

	List<Assignment> findByUser();

	List<Assignment> searchByAssignment(String searchTerm);


	Assignment updateState(String id, StateAssignment state);

	List<Assignment> getAssignmentsByLocation(String location);


}
