package com.mohitjoshi.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohitjoshi.blog.payloads.ApiResponse;
import com.mohitjoshi.blog.payloads.CategoryDto;
import com.mohitjoshi.blog.services.CategoryService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	private ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	private ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer cid){
		CategoryDto updateCategoryDto = this.categoryService.updaCategory(categoryDto, cid);
		return new ResponseEntity<CategoryDto>(updateCategoryDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/{categoryId}")
	private ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer cid){
		this.categoryService.deleteCategory(cid);
		return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/")
	private ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categoryDtos = this.categoryService.getAllCategories();
		return new ResponseEntity<List<CategoryDto>>(categoryDtos,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}")
	private ResponseEntity<CategoryDto> getcategoryById(@PathVariable("categoryId") Integer cid){
		CategoryDto categoryDto = this.categoryService.getCategoryById(cid);
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
	}

}
