package com.mohitjoshi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mohitjoshi.blog.entities.ContactUs;

@Repository
public interface ContactUsRepo extends JpaRepository<ContactUs, Integer>{

}
