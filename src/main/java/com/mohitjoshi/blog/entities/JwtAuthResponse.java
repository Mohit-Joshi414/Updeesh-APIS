package com.mohitjoshi.blog.entities;

import com.mohitjoshi.blog.payloads.UserDto;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	private UserDto user;
}
