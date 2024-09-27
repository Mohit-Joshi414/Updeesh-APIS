package com.mohitjoshi.blog.services;

import java.util.List;

import com.mohitjoshi.blog.entities.ContactUs;
import com.mohitjoshi.blog.payloads.ContactUsResponse;

public interface ContactUsService {

	ContactUs createContactUs(ContactUs c);
	ContactUsResponse getAllContactUs(int pageSize, int pageNumber, String sortBy, String sortDir);
}
