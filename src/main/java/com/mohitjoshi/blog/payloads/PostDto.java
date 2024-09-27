package com.mohitjoshi.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Comment;
import com.mohitjoshi.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDto {

	private int id;
	private String title;
	private String content;
	private String image_url;
	private Date post_timestamp;
	
	private CategoryDto categoryDto;
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
	
	
}
