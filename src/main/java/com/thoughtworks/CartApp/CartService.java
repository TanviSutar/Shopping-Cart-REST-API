package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ItemRepository itemRepository;

    //TODO add exception
    void addItem(Item item) {
        if (itemRepository.existsById(item.getId())) {
            return;
        }
        itemRepository.save(item);
    }

    void deleteItem(int itemId) {
        if (!itemRepository.existsById(itemId)) {
            return;
        }
        itemRepository.deleteById(itemId);
    }

    Cart viewItems() {
        return new Cart(itemRepository.findAll());
    }
}
