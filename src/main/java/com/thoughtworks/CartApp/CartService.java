package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

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
}
