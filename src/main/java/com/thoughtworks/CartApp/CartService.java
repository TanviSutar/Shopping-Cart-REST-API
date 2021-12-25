package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ItemRepository cartRepository;

    void addItem(Item item) {
        if (cartRepository.contains(item)) {
            return;
        }
        cartRepository.add(item);
    }

    void deleteItem(Item item) {
        if (!cartRepository.contains(item)) {
            return;
        }
        cartRepository.remove(item);
    }

    Cart viewItems() {
        return cartRepository.getCart();
    }
}
