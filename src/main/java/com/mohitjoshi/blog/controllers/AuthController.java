package com.mohitjoshi.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mohitjoshi.blog.entities.JwtAuthResponse;
import com.mohitjoshi.blog.entities.User;
import com.mohitjoshi.blog.payloads.JwtAuthRequest;
import com.mohitjoshi.blog.payloads.UserDto;
import com.mohitjoshi.blog.security.JwtTokenHelper;
import com.mohitjoshi.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@Valid @RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		System.out.println("hello 1 " + userDetails);
		String token = this.jwtTokenHelper.genrateToken(userDetails);
		System.out.println("hello 2 " + token);
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.mapper.map((User)userDetails, UserDto.class));
		System.out.println("user");
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
		UserDto registerUser = this.userService.registerUser(userDto);
		return new ResponseEntity<UserDto>(registerUser,HttpStatus.CREATED);
	}

	private void authenticate(String username, String password) throws Exception {
		System.out.println("hello 66 " + username +" " + password);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		System.out.println("hello 68" + authenticationToken);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (AuthenticationException e) {
			System.err.println(e);
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		
		
	}
}
