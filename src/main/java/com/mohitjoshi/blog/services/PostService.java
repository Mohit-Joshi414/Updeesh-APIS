package com.mohitjoshi.blog.services;

import java.util.List;
import java.util.Map;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.payloads.CategoryDto;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostResponse;
import com.mohitjoshi.blog.payloads.PostTitleDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto, int userId, int categoryId);
	PostDto updatePost(PostDto postDto, int postId);
	void deletePost(int postId);
	PostResponse getAllPost(int pageSize, int pageNumber, String sortBy, String sortDir);
	PostDto getPostById(int postId);
	PostResponse getPostsByCategory(int categoryId, int pageSize, int pageNumber, String sortBy, String sortDir);
	List<PostTitleDto>  getPostsTitlesByCategory();
	PostResponse getPostsByUser(int userId, int pageSize, int pageNumber, String sortBy, String sortDir);
	List<PostDto> searchPost(String keyword);
}
