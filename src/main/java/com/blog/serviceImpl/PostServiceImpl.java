package com.blog.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.entity.Post;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CategoryDto;
import com.blog.payload.PostCustomView;
import com.blog.payload.PostDto;
import com.blog.payload.PostDto;
import com.blog.payload.PostDto;
import com.blog.repository.CategoryRepo;
import com.blog.repository.PostRepository;
import com.blog.repository.UserRepository;
import com.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto PostDto , int userId, int categoryId) {
		Post post = this.modelMapper.map(PostDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		
		User user = this.userRepo
				.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepo
				.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		post.setUser(user);
		post.setCategory(category);
		
		Post save = this.postRepo.save(post);
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public PostCustomView getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {
		// soring and paggination
//		int pageSize = 5;
//		int pageNumber = 1;
		
		Sort sort =(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
//		Sort srt = Sort.by(sortBy).ascending();;
//		if(sortDir.equalsIgnoreCase("ASC")) {
//			srt = Sort.by(sortBy).ascending();
//		} else {
//			srt = Sort.by(sortBy).descending();
//		}
		
		
		
		Pageable pg = PageRequest.of(pageNumber, pageSize,sort );
		
		Page<Post> pagePost = this.postRepo.findAll(pg);
		List<Post> list = pagePost.getContent();
		
		List<PostDto> listt2 = list.stream()
				.map(l -> this.modelMapper.map(l, PostDto.class))
				.collect(Collectors.toList());
		PostCustomView postres = new PostCustomView();
		
		postres.setContent(listt2);
		postres.setPageNumber(pagePost.getNumber());
		postres.setPageSize(pagePost.getSize());
		postres.setTotalElements(pagePost.getTotalElements());
		postres.setTotalPages(pagePost.getTotalPages());
		postres.setLastPage(pagePost.isLast());
		
		return postres;
	}

	@Override
	public PostDto getPostById(int postId) {
		Post Post = this.postRepo
				.findById(postId)
				.orElseThrow(() ->new ResourceNotFoundException("Post", "ID", postId));
		return this.modelMapper.map(Post, PostDto.class);
	}

	@Override
	public PostDto updatePost(int postId, PostDto postDto) {
		Post post = this.postRepo
				.findById(postId)
				.orElseThrow(() ->new ResourceNotFoundException("Post", "ID", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public void deletePost(int id) {
		Post Post = this.postRepo
				.findById(id)
				.orElseThrow(() ->new ResourceNotFoundException("Post", "ID", id));
				this.postRepo.delete(Post);
		
	}

	@Override
	public List<PostDto> getPostsByUser(int userId) {
		User user = this.userRepo
				.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		List<Post> list = this.postRepo.findByUser(user);
		List<PostDto> list2 = list.stream()
		.map(l -> this.modelMapper.map(l, PostDto.class))
		.collect(Collectors.toList());
		return list2;
	}

	@Override
	public List<PostDto> getPostsByCategory(int categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
		List<Post> list = this.postRepo.findByCategory(category);
		List<PostDto> list2 = list.stream()
				.map(l -> this.modelMapper.map(l, PostDto.class))
				.collect(Collectors.toList());
		return list2;
	}

	@Override
	public List<PostDto> getPostsBySearch(String searchText) {
		List<Post> list = this.postRepo.findByTitleContaining(searchText);
		List<PostDto> list2 = list.stream()
				.map(l -> this.modelMapper.map(l, PostDto.class))
				.collect(Collectors.toList());
		return list2;
	}

}
