package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entity.User;
import com.blog.payload.ApiResponse;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;
import com.blog.repository.UserRepositoryImpl;
import com.blog.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto ) {
		UserDto createUser = this.userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createUser,HttpStatus.CREATED);
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		List<UserDto> list = this.userService.getallUser();
		return new ResponseEntity<List<UserDto>>(list,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userid") int userid){
		UserDto userDto = this.userService.getUserById(userid);
		return new ResponseEntity<UserDto>(userDto ,HttpStatus.OK);
	}
	// ADMIN user can only delete
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userid") int userid){
		this.userService.deleteUser(userid);
		ApiResponse res = new ApiResponse("User Deleteed SuccessFully" , true);
		return new ResponseEntity<ApiResponse>(res ,HttpStatus.OK);
	}
	@PostMapping("/{userid}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto ,
			@PathVariable("userid") int userid) {
		UserDto updateUser = this.userService.updateUser(userid, userDto);
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.CREATED);
	}
	@GetMapping("/prc/")
	public List<User> getAllUserByProcedure(){
		List<User> list = this.userService.getAllUserByProcedure();
		System.out.println(list);
		return list;
	}
	@GetMapping("/{userid}/posts")
	public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable("userid") int userid){
		List<PostDto> list = this.userService.getAllPostByUser(userid);
		return new ResponseEntity<List<PostDto>>(list ,HttpStatus.OK);
	}
	

}
