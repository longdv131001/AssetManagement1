package com.nashtech.rookie.assetmanagement.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nashtech.rookie.assetmanagement.entity.Asset;
import com.nashtech.rookie.assetmanagement.entity.Category;
import com.nashtech.rookie.assetmanagement.repository.CategoryRepository;

class CategoryServiceImplTest {
	
	
	CategoryRepository categoryRepository =  Mockito.mock(CategoryRepository.class) ;
	
	CategoryServiceImpl categoryService ;
	
	@BeforeEach
	public void init () {this.categoryService = new CategoryServiceImpl(categoryRepository);}


	@Test
	void testGetAllCategory() {
		List<Category> mockCategories = new ArrayList<>();
		mockCategories.add(new Category((long) 1, "LA", "Laptop", null));
		when(categoryRepository.findAll()).thenReturn(mockCategories);
		List<Category> actualCategories = categoryService.getAllCategory();
		assertThat(actualCategories.size()).isEqualTo(mockCategories.size());
		verify(categoryRepository).findAll();
	}

	@Test
    @DisplayName("Should Return Category When Create Category")
    void whenCreateCategory_shouldReturnCategory() {

        Category category = new Category();
        List<Asset> assetList = new ArrayList<>();
        assetList.add(new Asset());
        category.setCategoryName("Haha");
        category.setCategoryCode("HH");
        category.setAssets(assetList);
        Random r = new Random();
        long preGeneratedId = r.nextLong();
        category.setId(preGeneratedId);
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        Category actualCategory = categoryService.createCategory(category);
        assertNotNull(actualCategory);
        assertEquals(category,actualCategory);

    }


}
