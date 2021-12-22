package com.thoughtworks.CartApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class CartController {
    private final Cart cart = new Cart();

    @GetMapping("/cart")
    @ResponseStatus(OK)
    public Cart getCart(){
        return cart;
    }

}