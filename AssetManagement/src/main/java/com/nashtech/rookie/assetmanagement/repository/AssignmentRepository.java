package com.nashtech.rookie.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookie.assetmanagement.entity.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

	
		@Query(value = "select a from Assignment a where a.assignedTo.username =  :assigned_To")
		List<Assignment> findByAssignedTo(@Param("assigned_To") String assignedTo);
		
		@Query(value ="select a from Assignment a where lower(a.asset.assetCode) like %:find% "
				+ "or lower(a.asset.assetName) like %:find% or lower(a.assignedTo.username) like %:find% "
				+ "or lower(a.assignedBy.username) like %:find% ")
		List<Assignment> searchAssignment(@Param("find") String find);
		
		@Query(value = "select a from Assignment a where a.asset.location =  :locate")
		List<Assignment> findByAsset(@Param("locate") String location);
}
