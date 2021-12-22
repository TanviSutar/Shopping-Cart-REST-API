package com.thoughtworks.CartApp;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;

@Repository
public class CartRepository {

    private final ArrayList<Item> cartItems;

    public CartRepository(){
        this.cartItems = new ArrayList<>();
    }

    public void add(Item newItem) {
        cartItems.add(newItem);
    }

    public boolean contains(Item item){
        return cartItems.contains(item);
    }

    public void remove(Item givenItem) {
        cartItems.remove(givenItem);
    }

    public double totalCost() {
        double total = 0;
        for(Item item : cartItems){
            total += item.getCost();
        }
        return total;
    }

    public ArrayList<Item> cartItems(){
        return cartItems;
    }
}
