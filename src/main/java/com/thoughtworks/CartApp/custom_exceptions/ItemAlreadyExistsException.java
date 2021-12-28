package com.thoughtworks.CartApp.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ItemAlreadyExistsException extends RuntimeException{
    public ItemAlreadyExistsException(){
        super("Item already exists");
    }
}
