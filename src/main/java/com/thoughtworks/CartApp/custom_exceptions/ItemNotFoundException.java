package com.thoughtworks.CartApp.custom_exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(){
        super("No such item found in the cart.");
    }
}
