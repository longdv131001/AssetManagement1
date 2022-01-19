package com.nashtech.rookie.assetmanagement.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.nashtech.rookie.assetmanagement.dto.AssetDTO;
import com.nashtech.rookie.assetmanagement.entity.Asset;
import com.nashtech.rookie.assetmanagement.service.AssetService;

@CrossOrigin("*")
@RestController
@RequestMapping("/assets")
public class AssetRestController {
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AssetService assetService;
	
	
	@GetMapping("{assetCode}")
	public AssetDTO getDetailAsset(@PathVariable("assetCode") String assetCode) {
		Asset asset = assetService.getDetailAsset(assetCode);
		return modelMapper.map(asset, AssetDTO.class);
	}
	
	@PostMapping
	public AssetDTO createAsset(@RequestBody AssetDTO assetDTO) {
		Asset asset = modelMapper.map(assetDTO, Asset.class);
		return modelMapper.map(assetService.createAsset(asset), AssetDTO.class);
	}
	
	@PutMapping
	public AssetDTO updateAsset(@RequestBody AssetDTO assetDTO) {
		Asset asset = modelMapper.map(assetDTO, Asset.class);
		return modelMapper.map(assetService.updateAsset(asset), AssetDTO.class);
	}
	
	@DeleteMapping("{assetCode}")
	public void deleteAsset(@PathVariable("assetCode") String assetCode) {
		assetService.deleteAsset(assetCode);
	}
	
	@GetMapping({"/search"})
	public List<AssetDTO> searchAsset(@RequestParam("searchTerm") String searchTerm){
		if(!(searchTerm.equals("")&&(searchTerm != null))) {
			return assetService.searchAsset(searchTerm).stream().map(a -> modelMapper.map(a, AssetDTO.class)).collect(Collectors.toList());
		}else {
			return assetService.getAllAssets().stream().map(a -> modelMapper.map(a, AssetDTO.class)).collect(Collectors.toList());
		}
		
		
	}
	
	@GetMapping 
	public List<AssetDTO> getAllAssets(){
		return assetService.getAllAssets().stream().map(a -> modelMapper.map(a, AssetDTO.class)).collect(Collectors.toList());
	}
	
}
