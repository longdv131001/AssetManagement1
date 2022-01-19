package com.nashtech.rookie.assetmanagement.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookie.assetmanagement.dto.InfoDTO;

@RestController
@RequestMapping("")
public class ApplicationInfoController {
	
	@Value("${spring.datasource.url}")
	private String dataSourceUrl;
	
	@GetMapping
	public InfoDTO healthCheck(){
		InfoDTO infoDTO = new InfoDTO();
		infoDTO.setStatus("Active");
		infoDTO.setDataSource(dataSourceUrl);
		return infoDTO;
	}

}
