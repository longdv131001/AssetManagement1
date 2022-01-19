package com.nashtech.rookie.assetmanagement.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nashtech.rookie.assetmanagement.dto.AppUser;
import com.nashtech.rookie.assetmanagement.entity.Asset;
import com.nashtech.rookie.assetmanagement.entity.Assignment;
import com.nashtech.rookie.assetmanagement.entity.Enum.State;
import com.nashtech.rookie.assetmanagement.entity.Enum.StateAssignment;
import com.nashtech.rookie.assetmanagement.entity.User;
import com.nashtech.rookie.assetmanagement.repository.AssetRepository;
import com.nashtech.rookie.assetmanagement.repository.AssignmentRepository;
import com.nashtech.rookie.assetmanagement.repository.UserRepository;
import com.nashtech.rookie.assetmanagement.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {
	@Autowired
	private AssignmentRepository assignmentRepository;

	@Autowired
	private AssetRepository assetRepository;

	@Autowired

	private UserRepository userRepository;

	public AssignmentServiceImpl(AssignmentRepository assignmentRepository2, AssetRepository assetRepository2, UserRepository userRepository2) {
		this.assignmentRepository = assignmentRepository2;
		this.assetRepository = assetRepository2;
		this.userRepository  = userRepository2;
	}

	@Override
	public Assignment createAssignment(Assignment assignment) {

		Optional<Asset> asset = assetRepository.findById(assignment.getAsset().getAssetCode());
		Optional<User> userTo = userRepository.findById(assignment.getAssignedTo().getStaffCode());

		if (asset.isPresent() && userTo.isPresent() && asset.get().getState().equals(State.Available)) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				AppUser principal = (AppUser) auth.getPrincipal();
				String username = principal.getUsername();
				assignment.setAsset(asset.get());
				assignment.setAssignedBy(userRepository.findUserByUsername(username));
				assignment.setAssignedTo(userTo.get());
				assignment.setState(StateAssignment.Waiting_for_acceptance);
				Assignment result = assignmentRepository.save(assignment);
				Asset assett = assetRepository.findById(assignment.getAsset().getAssetCode()).orElse(null);
				assett.setState(State.Not_Available);
				assetRepository.save(assett);
				return result;
			}

		}
		return null;

	}

	@Override

	public Assignment updateAssignment(Assignment assignmentData) {
		Assignment assignment = assignmentRepository.getById(assignmentData.getAssignmentId());
		Asset oldAsset = assignment.getAsset();
		oldAsset.setState(State.Available);
		assetRepository.save(oldAsset);
		assignment.setAsset(assetRepository.getById(assignmentData.getAsset().getAssetCode()));
		assignment.setAssignedBy(assignmentData.getAssignedBy());
		assignment.setAssignedDate(assignmentData.getAssignedDate());
		assignment.setAssignedTo(userRepository.findUserByUsername(assignmentData.getAssignedTo().getUsername()));
		assignment.setState(assignmentData.getState());
		assignment.setNote(assignmentData.getNote());
		Asset newAssetRecord = assetRepository.findById(assignmentData.getAsset().getAssetCode()).orElse(null);
		newAssetRecord.setState(State.Not_Available);
		assetRepository.save(newAssetRecord);
		return assignmentRepository.save(assignment);

	}



	@Override
	public List<Assignment> getAllAssignment() {
		return assignmentRepository.findByAsset(getLocation());
	}

	@Override
	public List<Assignment> getAssignmentsByLocation(String location) {
		List<Asset> assetsByLocation = assetRepository.findByLocation(location);
		return getAllAssignment().stream().filter(a -> assetsByLocation.contains(a.getAsset()))
				.collect(Collectors.toList());

	}

	@Override
	public Assignment updateState(String id, StateAssignment state) {
		Assignment record = assignmentRepository.getById(Long.valueOf(id));
		Asset assetOfRecord = record.getAsset();
		switch (state) {
		case Accepted:
			assetOfRecord.setState((State.Assigned));
			break;

		case Declined:
			assetOfRecord.setState((State.Available));
			break;

		default:
			System.out.println("INVALID STATE");
			break;
		}
		assetRepository.save(assetOfRecord);
		record.setState(state);
		return assignmentRepository.save(record);
	}

	@Override
	public void deleteAssignment(Long id) {
		if(!assignmentRepository.findById(id).get().getState().equals(StateAssignment.Accepted)) {
			Asset asset = assignmentRepository.findById(id).get().getAsset();
			asset.setState(State.Available);
			assetRepository.save(asset);
			assignmentRepository.deleteById(id);
		}	
	}

	@Override
	public Assignment getDetailAssignment(Long id) {
		return assignmentRepository.findById(id).get();
	}

	@Override
	public List<Assignment> findByUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			AppUser principal = (AppUser) auth.getPrincipal();
			String assignedTo = principal.getUsername();
		return assignmentRepository.findByAssignedTo(assignedTo);
		}
		return null;
	}

	@Override
	public List<Assignment> searchByAssignment(String searchTerm) {
		String lowSearch= searchTerm.toLowerCase();
		return assignmentRepository.searchAssignment(lowSearch).stream().filter(a ->
		a.getAsset().getLocation().equals(getLocation())).collect(Collectors.toList());
	}
	
	String getLocation() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken) && auth!=null) {
			AppUser principal = (AppUser) auth.getPrincipal();
		    return principal.getLocation();
		}
		return "";
	}

	

}
