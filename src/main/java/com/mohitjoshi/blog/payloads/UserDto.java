package com.mohitjoshi.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohitjoshi.blog.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4, message = "Name should be minimum of 4 characters..")
	private String name;
	@Email(message = "Email address is not valid!!")
	private String email;
	@NotEmpty
	@Size(min = 6, message = "Password should be minimum of 6 characters and maximum of 16 characters..")
	private String password;
	@NotEmpty
	private String about;
	private Set<RoleDto> roles = new HashSet<>();
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}
