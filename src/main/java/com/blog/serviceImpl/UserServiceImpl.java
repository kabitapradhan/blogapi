package com.blog.serviceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.configuration.AppConstant;
import com.blog.entity.Post;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.exception.ResourceNotFoundException;
import com.blog.payload.PostDto;
import com.blog.payload.UserDto;
import com.blog.repository.ReloRepository;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;

import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ReloRepository roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		User saveUser = this.userRepo.save(user);
		return this.modelMapper.map(saveUser, UserDto.class);
	}

	@Override
	public List<UserDto> getallUser() {
		List<User> list = this.userRepo.findAll();
		List<UserDto> userDtoList = list.stream()
				.map(p -> this.modelMapper.map(p, UserDto.class)).collect(Collectors.toList());
		
		return userDtoList;
		
		// strored procedure call
		//StoredProcedureQuery call = this.userRepo.findAll();
	}

	@Override
	public UserDto getUserById(int UserId) {
		User user = this.userRepo.findById(UserId)
			.orElseThrow(()->new ResourceNotFoundException("User","Id",UserId));
		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(int userId, UserDto userDto) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		User saveUser = this.userRepo.save(user);
		return this.modelMapper.map(saveUser, UserDto.class);
	}

	@Override
	public void deleteUser(int id) {
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		this.userRepo.delete(user);
		
	}

	@Override
	@Transactional
	public List<User> getAllUserByProcedure() {
		List<User> allByProcedure = this.userRepo.getAllByProcedure();
		return allByProcedure;
	}

	@Override
	public List<PostDto> getAllPostByUser(int userId) {
		User user = this.userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		List<Post> posts = user.getPosts();
		List<PostDto> list = posts.stream()
			.map(mp-> this.modelMapper.map(mp, PostDto.class))
			.collect(Collectors.toList());
		return list;
	}

	@Override
	public UserDto registerNewUser(UserDto dto , Set<Role> roles) {
		User user = this.modelMapper.map(dto, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// get role
//		Role role = this.roleRepo.findById(AppConstant.ADMIN_USER).get();
//		user.getRoles().add(role);
		
		User save = this.userRepo.save(user);
		return this.modelMapper.map(save, UserDto.class);
	}

}
