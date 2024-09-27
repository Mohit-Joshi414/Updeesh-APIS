package com.mohitjoshi.blog.repositories;

import java.util.List;

import org.modelmapper.internal.bytebuddy.asm.Advice.OffsetMapping.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mohitjoshi.blog.entities.Category;
import com.mohitjoshi.blog.entities.Post;
import com.mohitjoshi.blog.entities.User;
import com.mohitjoshi.blog.payloads.PostDto;
import com.mohitjoshi.blog.payloads.PostTitleDto;

public interface PostRepo extends JpaRepository<Post, Integer>{

	Page<Post> findByUser(User user, Pageable p);
	Page<Post> findByCategory(Category category, Pageable p);
//	Page<Post> findByCategory(Category category, Sort s);

	@Query(value = "SELECT new com.mohitjoshi.blog.entities.Post(p.id, p.title) FROM Post p WHERE p.category.id = :category ORDER BY p.id desc limit 5")
	List<Post> findPostWithCategory(@Param("category") Integer category);

	List<Post> findByTitleContaining(String keyword);
}
