package com.blog.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.repository.CategoryRepo;
import com.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category save = this.categoryRepo.save(category);
		return this.modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getallCategory() {
		List<Category> list = this.categoryRepo.findAll();
		List<CategoryDto> lit2 = list.stream()
				.map(l -> this.modelMapper.map(l, CategoryDto.class))
				.collect(Collectors.toList());
		return lit2;
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category category = this.categoryRepo
		.findById(categoryId)
		.orElseThrow(() ->new ResourceNotFoundException("Category", "ID", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
		Category category = this.categoryRepo
				.findById(categoryId)
				.orElseThrow(() ->new ResourceNotFoundException("Category", "ID", categoryId));
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		Category save = this.categoryRepo.save(category);
		return this.modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public void deleteCategory(int id) {
		Category category = this.categoryRepo
		.findById(id)
		.orElseThrow(() ->new ResourceNotFoundException("Category", "ID", id));
		this.categoryRepo.delete(category);
		
	}

}
