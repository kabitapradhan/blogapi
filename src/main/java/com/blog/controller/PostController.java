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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.configuration.AppConstant;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostCustomView;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;
import com.blog.service.PostService;
import com.blog.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v3/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createUser(@Valid @RequestBody PostDto postDto,
			@PathVariable("userId") int userId, @PathVariable("categoryId") int categoryId
			) {
		PostDto createPost = this.postService.createPost(postDto ,userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	@GetMapping("")
	public ResponseEntity<PostCustomView> getAllPost(
		@RequestParam(value= "pageNumber" , defaultValue = AppConstant.PAGE_NUMBER , required = false) Integer pageNumber,
		@RequestParam(value="pageSize" ,  defaultValue = AppConstant.PAGE_SIZE , required = false) Integer pageSize ,
		@RequestParam(value="sortBy" , defaultValue = "postId" , required = false ) String sortBy,
		@RequestParam(value="sortDir" , defaultValue = "ASC" , required = false) String sortDir
	){
		PostCustomView customView = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostCustomView>(customView,HttpStatus.OK);
	}
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") int postId){
		PostDto dto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(dto,HttpStatus.CREATED);
	}
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable("userId") int userId){
		List<PostDto> list = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(list,HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("categoryId") int categoryId){
		List<PostDto> postByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.CREATED);
	}
	@DeleteMapping("/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable("postId") int postId){
		this.postService.deletePost(postId);
		ApiResponse apires = new ApiResponse("Post deleted succuessfilly", true);
		return new ResponseEntity<ApiResponse>(apires,HttpStatus.CREATED);
	}
	@PostMapping("/{postId}")
	public ResponseEntity<PostDto> createUser(@Valid @RequestBody PostDto postDto,
			@PathVariable("postId") int postId) 
	{
		PostDto updatePost = this.postService.updatePost(postId, postDto);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.CREATED);
	}
	// search
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> getAllPost(@PathVariable("keyword") String keyword){
		List<PostDto> list = this.postService.getPostsBySearch(keyword);
		return new ResponseEntity<List<PostDto>>(list,HttpStatus.OK);
	}

}
