package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
public class CartService {
    @Autowired
    private ItemRepository cartRepository;

    void addItem(Item item) {
        Item processedItem = new Item(item.getName().toLowerCase(), item.getCost());
        if (cartRepository.contains(processedItem)) {
            return;
        }
        cartRepository.add(processedItem);
    }

    void deleteItem(Item item) {
        Item processedItem = new Item(item.getName().toLowerCase(), item.getCost());
        if (!cartRepository.contains(processedItem)) {
            return;
        }
        cartRepository.remove(processedItem);
    }

    Cart viewItems() {
        return cartRepository.getCart();
    }
}
