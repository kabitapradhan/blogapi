package com.blog.payload;

import com.blog.entity.Post;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class CommentDto {
	private int id;
	private String content;
}
