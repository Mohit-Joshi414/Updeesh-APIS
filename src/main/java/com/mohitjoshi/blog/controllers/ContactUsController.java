package com.mohitjoshi.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mohitjoshi.blog.entities.ContactUs;
import com.mohitjoshi.blog.payloads.ContactUsResponse;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostResponse;
import com.mohitjoshi.blog.services.ContactUsService;

@RestController
@RequestMapping("/api/contactUs")
public class ContactUsController {
	@Autowired
	private ContactUsService contactUsService;
	
	@GetMapping("/all")
	public ResponseEntity<ContactUsResponse> getAllContactUs(
			@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		ContactUsResponse contactUsResponse = this.contactUsService.getAllContactUs(pageSize,pageNumber,sortBy,sortDir);
		return new ResponseEntity<ContactUsResponse>(contactUsResponse,HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<ContactUs> createContactUs(@RequestBody ContactUs contactUs){
		ContactUs createdContactUs =  this.contactUsService.createContactUs(contactUs);
		
		return new ResponseEntity<ContactUs>(createdContactUs,HttpStatus.CREATED);
		
	}
	
}
