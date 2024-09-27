package com.mohitjoshi.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohitjoshi.blog.entities.Comment;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.entities.User;
import com.mohitjoshi.blog.exceptions.ResourceNotFoundException;
import com.mohitjoshi.blog.payloads.CommentDto;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.repositories.CommentRepo;
import com.mohitjoshi.blog.repositories.PostRepo;
import com.mohitjoshi.blog.repositories.UserRepo;
import com.mohitjoshi.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		
		Comment comment = this.dtoToComment(commentDto);
		comment.setPost(post);
		comment.setUser(user);
		Comment savedComment = commentRepo.save(comment);
		
		return this.commentToDto(savedComment);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "id", commentId));
		this.commentRepo.delete(comment);

	}

	private CommentDto commentToDto(Comment comment) {
		CommentDto commentDto = mapper.map(comment, CommentDto.class);
		return commentDto;
	}
	

	private Comment dtoToComment(CommentDto commentDto) {
		Comment comment = this.mapper.map(commentDto, Comment.class);
		return comment;
	}
}
