package com.urlshortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredential(BadCredentialsException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("status",HttpStatus.UNAUTHORIZED.value());
        errors.put("error","Bad Credential");
        errors.put("message",ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> duplicateUserException(DuplicateUserException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("status",HttpStatus.CONFLICT.value());
        errors.put("error","Duplicate User");
        errors.put("message",ex.getMessage());
        return new ResponseEntity<>(errors,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ErrorsException.class)
    public ResponseEntity<?> errorsException(ErrorsException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("status",HttpStatus.NOT_ACCEPTABLE.value());
        errors.put("error","Not Acceptable");
        errors.put("message",ex.getErrors());
        return new ResponseEntity<>(ex.getErrors(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UnAuthorizedUrlAnalyticsAccessException.class)
    public ResponseEntity<?> unAuthorizedUrlAnalyticsAccessException(UnAuthorizedUrlAnalyticsAccessException ex){
        Map<String,Object> errors = new HashMap<>();
        errors.put("message","Your are unauthorized for this URL");
        errors.put("status",HttpStatus.UNAUTHORIZED);
        errors.put("error","UnAuthorized");
        return new ResponseEntity<>(errors,HttpStatus.UNAUTHORIZED);
    }
}
