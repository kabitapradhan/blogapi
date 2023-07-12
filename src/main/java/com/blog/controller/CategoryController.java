package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto ) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> list = this.categoryService.getallCategory();
		return new ResponseEntity<List<CategoryDto>>(list,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryid}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryid") int categoryid){
		CategoryDto CategoryDto = this.categoryService.getCategoryById(categoryid);
		return new ResponseEntity<CategoryDto>(CategoryDto ,HttpStatus.OK);
	}
	@DeleteMapping("/{categoryid}")
	public ResponseEntity<ApiResponse> deletecategory(@PathVariable("categoryid") int categoryid){
		this.categoryService.deleteCategory(categoryid);
		ApiResponse res = new ApiResponse("Category Deleteed SuccessFully" , true);
		return new ResponseEntity<ApiResponse>(res ,HttpStatus.OK);
	}
	@PostMapping("/{categoryid}")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto CategoryDto ,
			@PathVariable("categoryid") int categoryid) {
		CategoryDto updatecategory = this.categoryService.updateCategory(categoryid, CategoryDto);
		return new ResponseEntity<CategoryDto>(updatecategory,HttpStatus.CREATED);
	}
	
}
