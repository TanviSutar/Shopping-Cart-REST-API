package com.thoughtworks.CartApp;

import java.util.ArrayList;

public class Cart{
    private final ArrayList<Item> items;
    private final double totalCost;

    public Cart(ArrayList<Item> items, double totalCost) {
        this.items = items;
        this.totalCost = totalCost;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
