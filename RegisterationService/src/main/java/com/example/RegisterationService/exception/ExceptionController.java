package com.example.RegisterationService.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Error> resolveAndWriteException(Exception e) throws Exception {
		Error error = new Error("Error occurred", e.getMessage());
		error.setMessage("Oops something went wrong : " + e.getMessage());
		List<String> list = new ArrayList<String>();
		list.add("Application is facing some issue");
		error.setDate(new Date());
		error.setErrors(list);
		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
	}
}
