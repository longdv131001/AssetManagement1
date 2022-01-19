package com.nashtech.rookie.assetmanagement.service;

import java.util.List;

import com.nashtech.rookie.assetmanagement.entity.Asset;

public interface AssetService {
	List<Asset> getAllAssets();
	
	Asset getDetailAsset(String assetCode);
	
	Asset createAsset(Asset asset);
	
	Asset updateAsset(Asset asset);
	
	void deleteAsset(String assetCode);
	
	List<Asset> searchAsset(String searchTerm);
}
