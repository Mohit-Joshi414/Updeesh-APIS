package com.mohitjoshi.blog.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="contact_us")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactUs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	@Size(min = 3, message = "Name should be minimum of 3 characters..")
	@Column(name="user_name",nullable = false, length = 100)
	private String name;
	
	@NotEmpty
	@Email(message = "Email address is not valid!!")
	@Column(name="user_email",nullable = false, length = 100)
	private String email;
	
	@NotEmpty
	@Column(name="subject",nullable = false, length = 5000)
	private String subject;
	
	@NotEmpty
	@Column(name="message", length = 50000)
	private String message;
}
