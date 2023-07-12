package com.blog.exception;


public class CustomBadCredentialsException extends RuntimeException {

	
	public CustomBadCredentialsException(String message) {
		super(message);
		
	}
	public CustomBadCredentialsException() {
		super();
		
	}

}
