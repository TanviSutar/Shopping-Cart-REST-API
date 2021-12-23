package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("cart/items")
    @ResponseStatus(OK)
    public ArrayList<Item> getAllItems(){
        return cartService.viewItems();
    }

    @PostMapping("/cart/items/{id}")
    @ResponseStatus(CREATED)
    public void addItem(@PathVariable int id, @RequestBody Item item){
        System.out.println("Hello");
        cartService.addItem(item);
    }
}
