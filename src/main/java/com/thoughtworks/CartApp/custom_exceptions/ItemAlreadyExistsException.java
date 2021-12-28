package com.thoughtworks.CartApp.custom_exceptions;

public class ItemAlreadyExistsException extends Exception{
    public ItemAlreadyExistsException(){
        super("Item already exists");
    }
}
