package com.practice.CartApp;

import java.util.Objects;

public class CartDTO implements DTO{
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDTO)) return false;
        CartDTO cartDTO = (CartDTO) o;
        return Objects.equals(items, cartDTO.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, totalCost);
    }
}
