package com.mohitjoshi.blog.services.impl;


import java.io.Console;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.entities.User;
import com.mohitjoshi.blog.exceptions.ResourceNotFoundException;
import com.mohitjoshi.blog.payloads.CategoryDto;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostResponse;
import com.mohitjoshi.blog.payloads.PostTitleDto;
import com.mohitjoshi.blog.payloads.UserDto;
import com.mohitjoshi.blog.repositories.CategoryRepo;
import com.mohitjoshi.blog.repositories.PostRepo;
import com.mohitjoshi.blog.repositories.UserRepo;
import com.mohitjoshi.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, int userId, int categoryId) {
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		
		Post post = this.dtoToPost(postDto);
		post.setImage_url("default.png");
		post.setPost_timestamp(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.postToDto(newPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		post.setTitle(postDto.getTitle()==null?post.getTitle():postDto.getTitle());
		post.setContent(postDto.getContent()==null?post.getContent():postDto.getContent());
		
//		post.setCategory(postDto.getCategoryDto()==null?post.getCategory():postDto.getCategoryDto());
		post.setImage_url(postDto.getImage_url()==null?post.getImage_url():postDto.getImage_url());
		post.setPost_timestamp(new Date());
		
		Post newPost = this.postRepo.save(post); 
		
		return this.postToDto(newPost);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		this.postRepo.delete(post);
		
	}

	@Override
	public PostResponse getAllPost(int pageSize, int pageNumber, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
	
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> postPage= this.postRepo.findAll(p);
		List<Post> posts = postPage.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElement(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());
		postResponse.setElementPresent(postPage.getNumberOfElements());
		return postResponse;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		System.out.println("Post data : "+post.getUser().getId() + " " + post.getCategory());
		
		UserDto userDto =  modelMapper.map(post.getUser(),UserDto.class);
		CategoryDto categoryDto = modelMapper.map(post.getCategory(), CategoryDto.class);
		PostDto postToDto = this.postToDto(post);
		postToDto.setUser(userDto);
		postToDto.setCategoryDto(categoryDto);
		System.out.println("post dto : " + postToDto);
		return postToDto;
	}

	@Override
	public PostResponse getPostsByCategory(int categoryId, int pageSize, int pageNumber, String sortBy, String sortDir) {
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> postPage = this.postRepo.findByCategory(category, p);
		List<Post> posts = postPage.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setCategory(modelMapper.map(category, CategoryDto.class));
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElement(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());
		postResponse.setElementPresent(postPage.getNumberOfElements());
		return postResponse;
		
	}

	@Override
	public List<PostTitleDto> getPostsTitlesByCategory() {
		System.out.println("called : ");
		List<Category> categories = this.categoryRepo.findAll();
		
		List<PostTitleDto> postTitleWithCategoryList = new ArrayList<>();
		
		for(Category c:categories) {
			System.out.println("calling : " + c);
//			Map<Integer, String> cd = new HashMap<>();
//			cd.put(c.getId(),c.getTitle());
//			Map<Map<Integer, String>, List<PostTitleDto>> postTitleWithCategoryMap = new HashMap<>();
			List<Post> postPage = this.postRepo.findPostWithCategory(c.getId());
			PostTitleDto postTitleDto = new PostTitleDto(c.getId(),c.getTitle(),postPage);

			
			postTitleWithCategoryList.add(postTitleDto);
		}
		
		
		System.out.println("post : "+postTitleWithCategoryList);
		return postTitleWithCategoryList;
		
	}
	@Override
	public PostResponse getPostsByUser(int userId, int pageSize, int pageNumber, String sortBy, String sortDir) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));

		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> postPage= this.postRepo.findByUser(user,p);
		List<Post> posts = postPage.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElement(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setLastPage(postPage.isLast());
		postResponse.setElementPresent(postPage.getNumberOfElements());
		return postResponse;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos =  posts.stream().map(post->this.postToDto(post)).collect(Collectors.toList());
		
		return postDtos;
	}

	private PostDto postToDto(Post post) {
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}
	
	private Post dtoToPost(PostDto postDto) {
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}


}
