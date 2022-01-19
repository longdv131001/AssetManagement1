package com.nashtech.rookie.assetmanagement.service.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nashtech.rookie.assetmanagement.entity.Asset;
import com.nashtech.rookie.assetmanagement.entity.Assignment;
import com.nashtech.rookie.assetmanagement.repository.AssetRepository;
import com.nashtech.rookie.assetmanagement.repository.AssignmentRepository;
import com.nashtech.rookie.assetmanagement.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {
	
	@Mock
	public AssignmentRepository assignmentRepository;

	@Mock
	public AssetRepository assetRepository;

	@Mock
	public UserRepository userRepository;

	public AssignmentServiceImpl assignmentService;

	@BeforeEach
	public void init() {
		this.assignmentService = new AssignmentServiceImpl(assignmentRepository, assetRepository, userRepository);
	}

	
	@Test
	void whenGetAll_shouldReturnList() {
		List<Assignment> mockAssignments = new ArrayList<Assignment>();
		for (int i = 0; i < 10; i++) {
			mockAssignments.add(new Assignment((long) 1, null, null, null, null, null, null));
		}
		when(assignmentRepository.findByAsset(assignmentService.getLocation())).thenReturn(mockAssignments);

		List<Assignment> actualAssignments = assignmentService.getAllAssignment();

		assertThat(actualAssignments.size()).isEqualTo(mockAssignments.size());

		verify(assignmentRepository).findByAsset(assignmentService.getLocation());
	}
	@Test
	void whenGetById_shouldReturnAssignment() {
		Assignment mockAssignment = new Assignment();
		mockAssignment.setAssignmentId(1L);
		Optional<Assignment> mockAssignmentOptional = Optional.of(mockAssignment);

		when(assignmentRepository.findById(1L)).thenReturn(mockAssignmentOptional);

		Optional<Assignment> actualAssignment = Optional.of(assignmentService.getDetailAssignment(1L));
		assertThat(actualAssignment).isEqualTo(mockAssignmentOptional);
		verify(assignmentRepository).findById(Mockito.anyLong());
	}

	
	@Test
	public void whenGetAssignmentByLocation_shouldReturnListAssignment() {
		List<Assignment> mockAssignmentList = new ArrayList<>();
		List<Asset> mockAssets = new ArrayList<Asset>();
		for (int i = 0; i < 10; i++) {
			Assignment assignment = new Assignment();
			Asset newAsset = new Asset();
			if (i % 2 == 0) {
				newAsset.setLocation("HN");
				mockAssets.add(newAsset);
			} else {
				newAsset.setLocation("HCM");
			}

			assignment.setAsset(newAsset);
			mockAssignmentList.add(assignment);
		}
		when(assetRepository.findByLocation(Mockito.anyString())).thenReturn(mockAssets);
		when(assignmentService.getAllAssignment()).thenReturn(mockAssignmentList);
		List<Assignment> actualResult = assignmentService.getAssignmentsByLocation("HN");
		assertEquals(actualResult.size(), mockAssets.size());
	}

	

}
