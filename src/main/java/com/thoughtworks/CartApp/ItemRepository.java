package com.thoughtworks.CartApp;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ItemRepository {
    private final ArrayList<Item> cartItems;

    ItemRepository() {
        this.cartItems = new ArrayList<>();
    }

    void add(Item newItem) {
        cartItems.add(newItem);
    }

    boolean contains(Item item) {
        return cartItems.contains(item);
    }

    void remove(Item givenItem) {
        cartItems.remove(givenItem);
    }

    Cart getCart() {
        return new Cart(cartItems);
    }
}
