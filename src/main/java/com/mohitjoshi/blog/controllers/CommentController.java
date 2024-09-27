package com.mohitjoshi.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohitjoshi.blog.entities.Comment;
import com.mohitjoshi.blog.payloads.ApiResponse;
import com.mohitjoshi.blog.payloads.CommentDto;
import com.mohitjoshi.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;

	@PostMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable int userId, @PathVariable int postId){
		
		 CommentDto createdComment = this.commentService.createComment(comment, postId, userId);
		 return new ResponseEntity<CommentDto>(createdComment,HttpStatus.OK);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable int commentId){
		
		 this.commentService.deleteComment(commentId);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}
}
