package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("cart/items")
    @ResponseStatus(OK)
    public CartDTO viewAllItems() {
        return cartService.viewItems();
    }

    @GetMapping("/cart/items/{id}")
    public ResponseEntity<String> getItemById(@PathVariable int id){
        Item requiredItem = cartService.getItemById(id);
        if(requiredItem == null){
            return new ResponseEntity<>("No item with the given id found", BAD_REQUEST);
        }
        return new ResponseEntity<>("Item: "+requiredItem.getName()+"\nCost: "+requiredItem.getCost(), OK);
    }

    @PostMapping("/cart/items")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO item) {
        if (item.getName().equals("") || item.getName().trim().length() == 0) {
            return new ResponseEntity<>("Invalid Item: Item should have a valid name.", BAD_REQUEST);
        }

        try{
            cartService.addItem(item);
            return new ResponseEntity<>(item.getName() + " added to the cart.", CREATED);
        }
        catch (ItemAlreadyExistsException exception){
            return new ResponseEntity<>(item.getName() + " already present in the cart.", BAD_REQUEST);
        }
    }

    @DeleteMapping("/cart/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        cartService.deleteItem(id);
        return new ResponseEntity<>("Item deleted from the cart.", OK);
    }
}
