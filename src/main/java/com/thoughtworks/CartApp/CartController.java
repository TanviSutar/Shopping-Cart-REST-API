package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("cart/items")
    @ResponseStatus(OK)
    public Cart getAllItems() { return cartService.viewItems(); }

    @PostMapping("/cart/items/{id}")
    public ResponseEntity<String> addItem(@PathVariable int id, @RequestBody Item item) {
        if(item.getName().equals("") || item.getName().trim().length() == 0) {
            return new ResponseEntity<>("Invalid Item: Item should have a valid name.", BAD_REQUEST);
        }

        Pattern specialCharacters = Pattern.compile("[^A-Za-z0-9]");
        if(specialCharacters.matcher(item.getName()).find()){
            return new ResponseEntity<>("Invalid Item: Item name should not contain special characters(Eg: ?, !).", BAD_REQUEST);
        }

        cartService.addItem(item);
        return new ResponseEntity<>(CREATED);
    }

    @DeleteMapping("/cart/items/{id}")
    @ResponseStatus(OK)
    public void deleteItem(@PathVariable int id, @RequestBody Item item) {
        cartService.deleteItem(item);
    }
}
