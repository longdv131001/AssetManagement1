package com.nashtech.rookie.assetmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nashtech.rookie.assetmanagement.entity.Category;
import com.nashtech.rookie.assetmanagement.repository.CategoryRepository;
import com.nashtech.rookie.assetmanagement.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepo = categoryRepository;
	}

	@Override
	public List<Category> getAllCategory() {
		return categoryRepo.findAll();
	}

	@Override
	public Category createCategory(Category category) {
		return categoryRepo.save(category);
	}

	

}
