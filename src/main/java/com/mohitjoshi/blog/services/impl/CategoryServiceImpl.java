package com.mohitjoshi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.exceptions.ResourceNotFoundException;
import com.mohitjoshi.blog.payloads.CategoryDto;
import com.mohitjoshi.blog.payloads.UserDto;
import com.mohitjoshi.blog.repositories.CategoryRepo;
import com.mohitjoshi.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.dtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepo.save(category);
		
		return categoryToDto(savedCategory);
	}

	@Override
	public CategoryDto updaCategory(CategoryDto categoryDto, Integer categoryId) {

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		
		return categoryToDto(updatedCategory);
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		
		return categoryToDto(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
	
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Id", categoryId));
		this.categoryRepo.delete(category);
		
	}

	@Override
	public List<CategoryDto> getAllCategories() {

		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map(category->this.categoryToDto(category)).collect(Collectors.toList());
		return categoryDtos;
	}

	private Category dtoToCategory(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	private CategoryDto categoryToDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
}
