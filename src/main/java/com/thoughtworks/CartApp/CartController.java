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
    public Cart getAllItems() {
        return cartService.viewItems();
    }

//    @GetMapping("/cart/total-item-cost")
//    @ResponseStatus(OK)
//    public double totalCostOfItemsInCart() {
//        return cartService.totalCost();
//    }

    @PostMapping("/cart/items/{id}")
    @ResponseStatus(CREATED)
    public void addItem(@PathVariable int id, @RequestBody Item item) {
        cartService.addItem(item);
    }

    @DeleteMapping("/cart/items/{id}")
    @ResponseStatus(OK)
    public void deleteItem(@PathVariable int id, @RequestBody Item item) {
        cartService.deleteItem(item);
    }
}
