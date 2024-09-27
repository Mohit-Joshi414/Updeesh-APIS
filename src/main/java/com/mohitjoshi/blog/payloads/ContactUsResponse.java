package com.mohitjoshi.blog.payloads;

import java.util.List;

import com.mohitjoshi.blog.entities.ContactUs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ContactUsResponse {


	private List<ContactUs> contactUs;
	private int pageNumber;
	private int pageSize;
	private int elementPresent;
	private long totalElement;
	private int totalPages;
	private boolean isLastPage;
}
