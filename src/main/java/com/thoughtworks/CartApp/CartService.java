package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    @Autowired
    ItemRepository cartRepository;

    public void addItem(Item item){
        if(cartRepository.contains(item)){
            return;
        }
        cartRepository.add(item);
    }

    public void deleteItem(Item item){
        if(!cartRepository.contains(item)){
            return;
        }
        cartRepository.remove(item);
    }

    public ArrayList<Item> getItems(){
        return cartRepository.cartItems();
    }


}
