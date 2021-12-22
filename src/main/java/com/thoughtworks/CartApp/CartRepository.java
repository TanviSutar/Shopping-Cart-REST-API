package com.thoughtworks.CartApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;

@Repository
public class CartRepository {

    private ArrayList<Item> cartItems;

    public CartRepository(){
        this.cartItems = new ArrayList<>();
    }

    public void add(Item newItem) {
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
}
