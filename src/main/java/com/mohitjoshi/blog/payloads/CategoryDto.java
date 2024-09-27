package com.mohitjoshi.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	private int id;
	@NotEmpty
	@Size(min = 3, max = 100)
	private String title;
	@NotEmpty
	@Size(min = 10, max=200)
	private String description;
	public CategoryDto(int id, @NotEmpty @Size(min = 3, max = 100) String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	
}
