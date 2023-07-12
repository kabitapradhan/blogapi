package com.blog.service;

import java.util.List;
import java.util.Set;

import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;

public interface UserService {
	// register user
	public UserDto registerNewUser(UserDto dto , Set<Role> roles);
	// create add
	public UserDto createUser(UserDto userDto);
	// get all suer 
	public List<UserDto> getallUser();
	// get user by id
	public UserDto getUserById(int UserId);
	// update user
	public UserDto updateUser(int userId , UserDto userDto);
	// dlete user
	public void deleteUser(int id);

	// procedure call
	public List<User> getAllUserByProcedure();
	
	// get all post by user
	public List<PostDto> getAllPostByUser(int userId);

}
