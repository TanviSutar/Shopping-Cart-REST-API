package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private ItemRepository itemRepository;

    //TODO add exception
    boolean addItem(Item item) {
        if(itemRepository.existsByName(item.getName())){
            return false;
        }
        itemRepository.save(item);
        return true;
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
