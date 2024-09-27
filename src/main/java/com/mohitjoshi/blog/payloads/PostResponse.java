package com.mohitjoshi.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostResponse {

	private List<PostDto> content;
	private int pageNumber;
	private CategoryDto category;
	private int pageSize;
	private int elementPresent;
	private long totalElement;
	private int totalPages;
	private boolean isLastPage;
	
}
