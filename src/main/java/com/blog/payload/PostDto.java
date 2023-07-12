package com.blog.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Comments;

import com.blog.entity.Category;
import com.blog.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private CategoryDto category;
	
	
	private UserDto user;
	
	private List<CommentDto> comments = new ArrayList<>();

}
