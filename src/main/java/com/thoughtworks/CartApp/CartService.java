package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    @Autowired
    private ItemRepository cartRepository;

    void addItem(Item item){
        if(cartRepository.contains(item)){
            return;
        }
        cartRepository.add(item);
    }

    void deleteItem(Item item){
        if(!cartRepository.contains(item)){
            return;
        }
        cartRepository.remove(item);
    }

    ArrayList<Item> viewItems(){
        return cartRepository.cartItems();
    }

    double totalCost() {
        return cartRepository.totalCost();
    }
}
