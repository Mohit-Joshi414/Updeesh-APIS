package com.mohitjoshi.blog.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.mohitjoshi.blog.payloads.ApiResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String msg = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(msg,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handlingConstraintViolationException(ConstraintViolationException ex){
		
		Map<String, String> resp = new HashMap<>();
		
		ex.getConstraintViolations().forEach((er)->{
			String msg = er.getMessage();
			String fieldName = er.getPropertyPath().toString();
			System.out.println("Const viol : " + er.getPropertyPath().toString());
			resp.put(fieldName,msg);
		});
//		ex.getBindingResult().getAllErrors().forEach((error)->{
//			String fieldName = ((FieldError)error).getField();
//			String msg = error.getDefaultMessage();
//			resp.put(fieldName, msg);
//		});
		System.out.println("Const viol : " + resp);
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String, String> resp = new HashMap<>();
		
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String msg = error.getDefaultMessage();
			resp.put(fieldName, msg);
		});
		System.out.println("Const viol : " + resp);
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handlingDatabaseError(SQLIntegrityConstraintViolationException ex){
		String msg = ex.getMessage();
		System.out.println("sql excp : " + ex);
		Map<String, String> map = new HashMap<>();
		map.put("db", msg);
		return new ResponseEntity<>(map,HttpStatus.CONFLICT);
	}

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}


}
