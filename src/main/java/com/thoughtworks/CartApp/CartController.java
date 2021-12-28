package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("cart/items")
    public CartDTO viewAllItems(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String id) {
        if(name.trim().length() > 0 ){
            ArrayList<Item> arr = new ArrayList<>(){
                {
                    add(new Item("Pencil", 23));
                    add(new Item("Eraser", 10));
                }
            };
            return new CartDTO(arr);
        }
        if(id.trim().length() > 0 ){
            return getItemById(Integer.parseInt(id));
        }
        return cartService.viewItems();
    }

    public CartDTO getItemById(int id){
        Item requiredItem = cartService.getItemById(id);
        return new CartDTO(new ArrayList<Item>(){
            {
                add(requiredItem);
            }
        });
    }

    @PostMapping("/cart/items")
    public ResponseEntity<String> addItem(@RequestBody ItemDTO item) {
        if (item.getName().equals("") || item.getName().trim().length() == 0) {
            return new ResponseEntity<>("Invalid Item: Item should have a valid name.", BAD_REQUEST);
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
