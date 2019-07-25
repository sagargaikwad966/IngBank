package com.ingbank.banking.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ingbank.banking.model.ResponseData;



@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		List<String> errorList = new ArrayList<>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors())
		{
			errorList.add(error.getDefaultMessage());
		}
		
		ResponseData response = new ResponseData(ex.getMessage(), status, errorList);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApplicationException.class)
	 public final ResponseEntity<Object> handleAllExceptions(ApplicationException ex, WebRequest request) {
		 List<String> errorList = new ArrayList<>();
		 errorList.add(ex.getMessage());
		 ResponseData response = new ResponseData(ex.getMessage(), HttpStatus.BAD_REQUEST, errorList);
		 return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		 
	 }
 

}
