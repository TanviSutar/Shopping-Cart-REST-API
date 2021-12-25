package com.thoughtworks.CartApp;

import java.util.ArrayList;

public class Cart{
    private final ArrayList<Item> items;
    private double totalCost;

    public Cart(ArrayList<Item> items) {
        this.items = items;
        totalCost = 0.0;
        for(Item item: items){
            totalCost += item.getCost();
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
