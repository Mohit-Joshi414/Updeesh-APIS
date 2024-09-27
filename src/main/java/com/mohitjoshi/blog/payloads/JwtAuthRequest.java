package com.mohitjoshi.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtAuthRequest {

	@NotEmpty
	@Email(message = "Email address is not valid!!")
	private String username;
	@NotEmpty
	@Size(min = 6,max = 16, message = "Password should be minimum of 6 characters and maximum of 16 characters..")
	private String password;
}
