package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.payload.PostDto;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	public List<Post> findByUser(User user);
	public List<Post> findByCategory(Category category);
	
	
	public List<Post> findByTitleContaining(String title);
	
	@Query("select p from Post p where p.title like : key")
	public List<Post> searchByTitle(@Param("key") String title);

}
