package com.mohitjoshi.blog.services;

import java.util.List;

import com.mohitjoshi.blog.payloads.CategoryDto;

public interface CategoryService {

	CategoryDto createCategory(CategoryDto categoryDto);
	
	CategoryDto updaCategory(CategoryDto categoryDto, Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	
	void deleteCategory(Integer categoryId);
	
	List<CategoryDto> getAllCategories();
}
