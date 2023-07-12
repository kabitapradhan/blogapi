package com.blog.service;

import java.util.List;

import com.blog.payload.CategoryDto;
import com.blog.payload.PostCustomView;
import com.blog.payload.PostDto;

public interface PostService {
	
	// create add post
	public PostDto createPost(PostDto PostDto,int userId, int categoryId);
	// get all post 
	public PostCustomView getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);
	// get post by id
	public PostDto getPostById(int postId);
	// update post
	public PostDto updatePost(int postId , PostDto PostDto);
	// delete post
	public void deletePost(int id);
	// public Post get post by user
	public List<PostDto> getPostsByUser(int userId);
	// get post by category
	public List<PostDto> getPostsByCategory(int categoryId);
	// public List<PostDto> getPostsBySearch()
	public List<PostDto> getPostsBySearch(String searchText);
}
