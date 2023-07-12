package com.blog.service;

import java.util.List;

import com.blog.entity.User;
import com.blog.payload.CategoryDto;
import com.blog.payload.UserDto;

public interface CategoryService {
	
	// create add category
	public CategoryDto createCategory(CategoryDto categoryDto);
	// get all category 
	public List<CategoryDto> getallCategory();
	// get category by id
	public CategoryDto getCategoryById(int categoryId);
	// update category
	public CategoryDto updateCategory(int categoryId , CategoryDto categoryDto);
	// dlete category
	public void deleteCategory(int id);
	

}
