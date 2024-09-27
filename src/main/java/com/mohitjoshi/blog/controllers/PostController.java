package com.mohitjoshi.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.payloads.ApiResponse;
import com.mohitjoshi.blog.payloads.CategoryDto;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostResponse;
import com.mohitjoshi.blog.payloads.PostTitleDto;
import com.mohitjoshi.blog.services.FileService;
import com.mohitjoshi.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable int userId, @PathVariable int categoryId){
		System.out.println("post controller : "+postDto);
		PostDto createdPost =  this.postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createdPost,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostByUser(@PathVariable int userId,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			) {
		PostResponse postDtos =  this.postService.getPostsByUser(userId,pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable int categoryId,
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		PostResponse postDtos = this.postService.getPostsByCategory(categoryId,pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/category/postsList")
	public ResponseEntity<List<PostTitleDto> > getPostsTitlesByCategory(){
		List<PostTitleDto>   postDtos = this.postService.getPostsTitlesByCategory();
		return new ResponseEntity<>(postDtos,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable int postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable int postId){
		PostDto postDto2 = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(postDto2,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer pid){
		this.postService.deletePost(pid);
		return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("keyword") String keyword){
		
		List<PostDto> postDtos = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
		
	}
	
	@PostMapping("/post/image-upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable int postId
			) throws IOException{
		PostDto postDto =  this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImage_url(fileName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream inputStream = this.fileService.getSource(path, imageName);
		
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
		StreamUtils.copy(inputStream, response.getOutputStream());
		}
}
