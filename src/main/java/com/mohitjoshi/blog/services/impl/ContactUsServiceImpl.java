package com.mohitjoshi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mohitjoshi.blog.entities.ContactUs;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.payloads.ContactUsResponse;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostResponse;
import com.mohitjoshi.blog.repositories.ContactUsRepo;
import com.mohitjoshi.blog.services.ContactUsService;

@Service
public class ContactUsServiceImpl implements ContactUsService{

	@Autowired
	private ContactUsRepo contactUsRepo;
	@Override
	public ContactUs createContactUs(ContactUs c) {
		ContactUs contactUs = contactUsRepo.save(c);
		return contactUs;
	}

	@Override
	public ContactUsResponse getAllContactUs(int pageSize, int pageNumber, String sortBy, String sortDir) {
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<ContactUs> contactUsPage = contactUsRepo.findAll(p);
		List<ContactUs> contactUs = contactUsPage.getContent();
	
		ContactUsResponse contactUsResponse = new ContactUsResponse();
		
		contactUsResponse.setContactUs(contactUs);
		contactUsResponse.setPageNumber(contactUsPage.getNumber());
		contactUsResponse.setPageSize(contactUsPage.getSize());
		contactUsResponse.setTotalElement(contactUsPage.getTotalElements());
		contactUsResponse.setTotalPages(contactUsPage.getTotalPages());
		contactUsResponse.setLastPage(contactUsPage.isLast());
		contactUsResponse.setElementPresent(contactUsPage.getNumberOfElements());
		return contactUsResponse;
	}

}
