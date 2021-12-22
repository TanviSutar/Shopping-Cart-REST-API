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
        if(contains(newItem)){
            return;
        }
        cartItems.add(newItem);
    }

    public boolean contains(Item item){
        for(Item items: cartItems){
            if(items.equals(item)){
                return true;
            }
        }
        return false;
    }

    public void remove(Item givenItem) {
        for(Item item : cartItems){
            if(item.equals(givenItem)){
                cartItems.remove(givenItem);
                break;
            }
        }
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
