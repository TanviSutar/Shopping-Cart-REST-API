package com.thoughtworks.CartApp;

import com.thoughtworks.CartApp.custom_exceptions.ItemAlreadyExistsException;
import com.thoughtworks.CartApp.custom_exceptions.ItemNotFoundException;
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

    @RequestMapping(value = "cart/items", method = RequestMethod.GET)
    public CartDTO viewAllItems(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "") String id) {
        if(name.trim().length() > 0){
            return searchByStringPattern(name);
        }

        if(id.trim().length() > 0){
            return getItemById(Integer.parseInt(id));
        }

        return cartService.getItemList();
    }

    public CartDTO searchByStringPattern(String name){
        return cartService.getItemListByNameBasedPattern(name);
    }

    public CartDTO getItemById(int id){
        Item requiredItem = cartService.getItemById(id);
        return new CartDTO(new ArrayList<Item>(){
            {
                add(requiredItem);
            }
        });
    }

    @RequestMapping(value = "/cart/items", method = RequestMethod.POST)
    public ResponseEntity<DTO> addItem(@RequestBody ItemDTO itemDTO) {

        if (itemDTO.getName().trim().length() == 0) {
            LOG.info("Attempt to enter invalidly named item.");
            return new ResponseEntity<>(new ErrorDTO(ErrorCode.INVALID_ITEM_NAME, "Item name should be non-empty string."), BAD_REQUEST);
        }

        int id = cartService.addItem(itemDTO);

        LOG.info(itemDTO.getName()+" has been added successfully.");

        return new ResponseEntity<>(new ResponseDTO(id, itemDTO.getName() + " added to the cart."), CREATED);
    }

    @RequestMapping(value = "/cart/items/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<DTO> deleteItem(@PathVariable int id) {
        cartService.deleteItemById(id);

        LOG.info("Item with id="+id+" has been deleted successfully.");

        return new ResponseEntity<>(new ResponseDTO(id, "Item deleted from the cart."), OK);
    }
}
