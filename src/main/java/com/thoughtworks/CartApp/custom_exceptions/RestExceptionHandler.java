package com.thoughtworks.CartApp.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<String> handleItemAlreadyExists(ItemAlreadyExistsException exception){
        return new ResponseEntity<String>("Item already exists.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleItemNotFound(ItemNotFoundException exception){
        return new ResponseEntity<String>("No such item found.", HttpStatus.NOT_FOUND);
    }
}
