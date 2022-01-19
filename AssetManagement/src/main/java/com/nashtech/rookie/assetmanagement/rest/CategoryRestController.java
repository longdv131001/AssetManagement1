package com.nashtech.rookie.assetmanagement.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookie.assetmanagement.dto.CategoryDTO;
import com.nashtech.rookie.assetmanagement.entity.Category;
import com.nashtech.rookie.assetmanagement.service.CategoryService;

@CrossOrigin("*")
@RestController
@RequestMapping("/categories")
public class CategoryRestController {
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public List<CategoryDTO> getAll(){
		return categoryService.getAllCategory().stream().map(c -> modelMapper.map(c, CategoryDTO.class)).collect(Collectors.toList());
	}
	
	@PostMapping
	public CategoryDTO createCategory(@RequestBody CategoryDTO cateogryDTO) {
		Category category = modelMapper.map(cateogryDTO, Category.class);
		return modelMapper.map(categoryService.createCategory(category), CategoryDTO.class);
	}
	
	
	
}
