package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CommentDto;
import com.blog.payload.PostDto;
import com.blog.service.CommentService;
import com.blog.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v3/comments")
public class CommentController {
	
	@Autowired
	private CommentService commService;
	
	@PostMapping("/posts/{postId}")
	public ResponseEntity<CommentDto> createUser(@Valid @RequestBody CommentDto commentDto,
			@PathVariable("postId") int postId) {
		CommentDto addComment = this.commService.addComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(addComment,HttpStatus.CREATED);
	}
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable("commentId") int commentId){
		this.commService.deleteComment(commentId);
		ApiResponse apires = new ApiResponse("Comment deleted succuessfilly", true);
		return new ResponseEntity<ApiResponse>(apires,HttpStatus.CREATED);
	}

}
