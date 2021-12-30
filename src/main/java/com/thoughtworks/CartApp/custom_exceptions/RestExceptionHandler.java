package com.thoughtworks.CartApp.custom_exceptions;

import com.thoughtworks.CartApp.ErrorCode;
import com.thoughtworks.CartApp.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ItemAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleItemAlreadyExists(ItemAlreadyExistsException exception){
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ErrorCode.ITEM_ALREADY_EXISTS, "A similar item already exists in the cart."), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleItemNotFound(ItemNotFoundException exception){
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ErrorCode.ITEM_NOT_FOUND, "No such item present in the cart."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception exception){
        return new ResponseEntity<ErrorDTO>(new ErrorDTO(ErrorCode.UNKNOWN_EXCEPTION, "Unknown exception occurred."), HttpStatus.NOT_FOUND);
    }
}
