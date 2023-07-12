package com.blog.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.CommentDto;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentRepository commnetRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PostRepository postRepo;
	
	@Override
	public CommentDto addComment(CommentDto comDto, int postId) {
		Post post = this.postRepo
				.findById(postId)
				.orElseThrow(() ->new ResourceNotFoundException("Post", "ID", postId));
		Comment comment = this.modelMapper.map(comDto, Comment.class);
		comment.setPost(post);
		Comment save = this.commnetRepo.save(comment);
		
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(int commentId) {
		Comment comment = this.commnetRepo
				.findById(commentId)
				.orElseThrow(() ->new ResourceNotFoundException("Comment", "ID", commentId));
		this.commnetRepo.delete(comment);
	}

}
