package com.blog.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.entity.User;
import com.blog.exception.CustomBadCredentialsException;
import com.blog.models.JwtRequest;
import com.blog.models.Jwtresponse;
import com.blog.payload.UserDto;
import com.blog.security.JwtHelper;
import com.blog.service.UserService;

import jakarta.validation.Valid;

import com.blog.models.JwtRequest;
import com.blog.models.Jwtresponse;
import com.blog.security.JwtHelper;



@RestController
@RequestMapping("/api/v1/auth")

@CrossOrigin
public class AuthController {
	
//	@Autowired
//	private UserService userService;
	
	@Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    //// login new user //( /api/v1/auth/login )
    @PostMapping("/login")
    public ResponseEntity<Jwtresponse> login(@RequestBody JwtRequest request) {
//    	System.out.println("userDetails");
//        System.out.println(request.getEmail()+ "&" + request.getPassword());
    	
        this.doAuthenticate(request.getEmail(), request.getPassword());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        
        Jwtresponse response = new Jwtresponse();
        response.setJwtToken(token);
        response.setUser(this.mapper.map((User)userDetails, UserDto.class));
        
        return new ResponseEntity<Jwtresponse>(response, HttpStatus.OK);
    }
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
    
    // register new user //( /api/v1/auth/register )
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto dto){
    	UserDto userDto = this.userService.registerNewUser(dto, null);
    	return new ResponseEntity<UserDto>(userDto , HttpStatus.CREATED);
    }


}
