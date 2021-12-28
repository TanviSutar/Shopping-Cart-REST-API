package com.thoughtworks.CartApp;

public class CartDTO {
    private final Iterable<Item> items;
    private double totalCost;

    public CartDTO(Iterable<Item> items) {
        this.items = items;
        totalCost = 0.0;
        for(Item item: items){
            totalCost += item.getCost();
        }
    }

    public Iterable<Item> getItems() {
        return items;
    }

    public double getTotalCost() {
        return totalCost;
    }
}
