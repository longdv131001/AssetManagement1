package com.nashtech.rookie.assetmanagement.service;

import java.util.List;

import com.nashtech.rookie.assetmanagement.entity.Category;

public interface CategoryService {
	public List<Category> getAllCategory();
	public Category createCategory(Category category);
	
}
