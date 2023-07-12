package com.blog.service;

import com.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto addComment(CommentDto comDto, int postId);
	void deleteComment(int commentId);
	

}
