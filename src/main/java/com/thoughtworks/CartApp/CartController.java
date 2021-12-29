package com.thoughtworks.CartApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    private static final Logger LOG = LoggerFactory.getLogger(CartController.class);

    //TODO use regular expression to validate name and id strings
    @RequestMapping(value = "cart/items", method = RequestMethod.GET)
    public CartDTO viewAllItems(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String id) {
        if(name.trim().length() > 0){
            return searchByStringPattern(name);
        }
        if(id.trim().length() > 0){
            return getItemById(Integer.parseInt(id));
        }
        LOG.info("Item has been added successfully.");
        return cartService.viewItems();
    }

    public CartDTO searchByStringPattern(String name){
        return cartService.searchByStringPattern(name);
    }

    public CartDTO getItemById(int id){
        Item requiredItem = cartService.getItemById(id);
        return new CartDTO(new ArrayList<Item>(){
            {
                add(requiredItem);
            }
        });
    }

    @RequestMapping(value = "/cart/items", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO> addItem(@RequestBody ItemDTO item) {
        if (item.getName().equals("") || item.getName().trim().length() == 0) {
            return new ResponseEntity<>(new ErrorDTO(ErrorCode.INVALID_ITEM_NAME, "Item name is invalid."), BAD_REQUEST);
        }
        int id = cartService.addItem(item);
        return new ResponseEntity<>(new ResponseDTO(id, item.getName() + " added to the cart."), CREATED);
    }

    @RequestMapping(value = "/cart/items/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DTO> deleteItem(@PathVariable int id) {
        cartService.deleteItemById(id);
        return new ResponseEntity<>(new ResponseDTO(id, "Item deleted from the cart."), OK);
    }
}
