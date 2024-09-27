package com.mohitjoshi.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Comment;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class PostTitleDto {

	private int categoryId;
	private String categoryName;
	private List<Post> posts; 

	
}
