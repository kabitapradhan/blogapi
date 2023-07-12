package com.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payload.ApiResponse;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
		String errmsg = ex.getMessage();
		ApiResponse apires = new ApiResponse(errmsg, false);
		return new ResponseEntity<ApiResponse>(apires ,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String ,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String , String> mp = new HashMap<>();
		//String msg = ex.getMessage();
		ex.getBindingResult().getAllErrors().forEach(error-> {
			String field = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			mp.put(field, message);
		});
		return new ResponseEntity<Map<String,String>>(mp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
		String msg = ex.getMessage();
		ApiResponse apires = new ApiResponse(msg, false);
		
		return new ResponseEntity<ApiResponse>(apires ,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(CustomBadCredentialsException.class)
	public ResponseEntity<ApiResponse> handleCustomBadCredentialsException(CustomBadCredentialsException ex){
		String msg = ex.getMessage();
		ApiResponse apires = new ApiResponse(msg, false);
		return new ResponseEntity<ApiResponse>(apires ,HttpStatus.BAD_REQUEST);
	}

}
