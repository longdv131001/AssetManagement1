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
import com.nashtech.rookie.assetmanagement.entity.Category;
import com.nashtech.rookie.assetmanagement.entity.Enum.State;
import com.nashtech.rookie.assetmanagement.repository.AssetRepository;
import com.nashtech.rookie.assetmanagement.repository.CategoryRepository;
import com.nashtech.rookie.assetmanagement.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {
	
	@Autowired
	private AssetRepository assetRepository;
	


	@Autowired
	private CategoryRepository categoryRepository;



	@Override
	public List<Asset> getAllAssets() {
		return assetRepository.findByLocation(getLocation());
	}

	@Override
	public Asset getDetailAsset(String assetCode) {
		return assetRepository.findById(assetCode).get();
	}

	@Override
	public Asset createAsset(Asset asset) {
		Optional<Category> cate = categoryRepository.findById(asset.getCategory().getId());
		if(cate.isPresent()) {
			asset.setCategory(cate.get());
			asset.setLocation(getLocation());
			return assetRepository.save(asset);
		}
		
		return null;
	}
	
	@Override
	public Asset updateAsset(Asset asset) {
		return assetRepository.save(asset);
	}
	
	@Override
	public List<Asset> searchAsset(String searchTerm) {
		return assetRepository.searchAsset(searchTerm.toLowerCase()).stream().filter(a -> a.getLocation().equals(getLocation())).collect(Collectors.toList());
	}

	@Override
	public void deleteAsset(String assetCode) {
		if(!assetRepository.findById(assetCode).get().getState().equals(State.Assigned)){
		assetRepository.deleteById(assetCode);
		}
	}

	String getLocation() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			AppUser principal = (AppUser) auth.getPrincipal();
		    return principal.getLocation();
		}
		return "";
	}
}

	