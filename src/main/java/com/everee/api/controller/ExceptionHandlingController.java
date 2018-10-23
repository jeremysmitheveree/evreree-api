package com.everee.api.controller;


import com.everee.api.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
        import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
        import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/***
    Controller to catch exceptions thrown by the underlying system and return "user friendly" HTTP error responses, and
    where appropriate additional descriptive information to facilitate client side error correction.
 ***/

@ControllerAdvice
@RestController
public class ExceptionHandlingController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> resourceNotFound(ResourceNotFoundException ex) {
        Map<String,String> response = new HashMap<>();
        response.put("errorCode", HttpStatus.NOT_FOUND.toString());
        response.put("errorMessage", ex.getMessage());

        return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String,String> response = new HashMap<>();
        response.put("errorCode", HttpStatus.BAD_REQUEST.toString());
        response.put("errorMessage", "Validation Failed : " + ex.getBindingResult().toString());

        return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
    }

}
