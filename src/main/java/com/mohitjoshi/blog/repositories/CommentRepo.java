package com.mohitjoshi.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohitjoshi.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
