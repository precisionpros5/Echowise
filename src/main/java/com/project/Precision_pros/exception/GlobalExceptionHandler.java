package com.project.Precision_pros.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
//    }
    
    


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    	}


	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDenied(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
	}
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}




}
