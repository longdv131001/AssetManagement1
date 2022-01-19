package com.nashtech.rookie.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nashtech.rookie.assetmanagement.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String>{
	List<Asset> findByLocation(String location);
	
	@Query(value="SELECT a FROM Asset a WHERE a.assetCode LIKE %:search% or a.assetName LIKE %:search%")
	List<Asset> searchAsset(@Param("search") String searchTerm);
}
