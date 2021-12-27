package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    //TODO itemDTO
    @GetMapping("cart/items")
    @ResponseStatus(OK)
    public Cart viewAllItems() {
        return cartService.viewItems();
    }

    @PostMapping("/cart/items")
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        if (item.getName().equals("") || item.getName().trim().length() == 0) {
            return new ResponseEntity<>("Invalid Item: Item should have a valid name.", BAD_REQUEST);
        }

        Pattern specialCharacters = Pattern.compile("[^A-Za-z0-9\\s\\t]");
        if (specialCharacters.matcher(item.getName()).find()) {
            return new ResponseEntity<>("Invalid Item: Item name should not contain special characters(Eg: ?, @, etc).", BAD_REQUEST);
        }

        cartService.addItem(item);
        return new ResponseEntity<>(item.getName() + " added to the cart.", CREATED);
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        cartService.deleteItem(id);
        return new ResponseEntity<>("Item deleted from the cart.", OK);
    }
}
